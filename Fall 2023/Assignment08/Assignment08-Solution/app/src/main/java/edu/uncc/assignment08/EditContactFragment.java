package edu.uncc.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import edu.uncc.assignment08.databinding.FragmentEditContactBinding;
import edu.uncc.assignment08.databinding.PhoneListItemBinding;

public class EditContactFragment extends Fragment {

    private static final String ARG_PARAM_CONTACT = "ARG_PARAM_CONTACT";
    private Contact mContact;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<PhoneNumber> mPhoneNumbers = new ArrayList<>();
    public void addNewPhoneNumber(PhoneNumber phoneNumber){
        HashMap<String, Object> data = new HashMap<>();
        data.put("phoneNumbers", FieldValue.arrayUnion(phoneNumber));
        db.collection("contacts").document(mContact.getDocId()).update(data);
    }

    public static EditContactFragment newInstance(Contact contact) {
        EditContactFragment fragment = new EditContactFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContact = (Contact) getArguments().getSerializable(ARG_PARAM_CONTACT);
        }
    }

    public EditContactFragment() {
        // Required empty public constructor
    }

    FragmentEditContactBinding binding;
    ListenerRegistration contactListener;
    PhoneNumbersAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Edit Contact");

        adapter = new PhoneNumbersAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelEditingContact();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmailAddress.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("name", name);
                    data.put("email", email);
                    db.collection("contacts").document(mContact.getDocId()).update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mListener.doneEditingContact();
                        }
                    });
                }

            }
        });

        binding.imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddPhoneNumber();
            }
        });

        binding.editTextName.setText(mContact.getName());
        binding.editTextEmailAddress.setText(mContact.getEmail());

        contactListener = db.collection("contacts").document(mContact.getDocId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(value != null){
                    mContact = value.toObject(Contact.class);
                    mPhoneNumbers.clear();
                    mPhoneNumbers.addAll(mContact.getPhoneNumbers());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(contactListener != null){
            contactListener.remove();
            contactListener = null;
        }
    }

    class PhoneNumbersAdapter extends RecyclerView.Adapter<PhoneNumbersAdapter.PhoneNumberViewHolder>{

        @NonNull
        @Override
        public PhoneNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PhoneListItemBinding itemBinding = PhoneListItemBinding.inflate(getLayoutInflater(), parent, false);
            return new PhoneNumberViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull PhoneNumberViewHolder holder, int position) {
            holder.setupUI(mPhoneNumbers.get(position));
        }

        @Override
        public int getItemCount() {
            return mPhoneNumbers.size();
        }

        class PhoneNumberViewHolder extends RecyclerView.ViewHolder{
            PhoneListItemBinding itemBinding;
            PhoneNumber mPhoneNumber;
            public PhoneNumberViewHolder(PhoneListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(PhoneNumber phoneNumber){
                mPhoneNumber = phoneNumber;
                itemBinding.textViewType.setText(phoneNumber.getType());
                itemBinding.textViewPhoneNumber.setText(phoneNumber.getNumber());
                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> data = new HashMap<>();
                        data.put("phoneNumbers", FieldValue.arrayRemove(phoneNumber));
                        db.collection("contacts").document(mContact.getDocId()).update(data);
                    }
                });
            }
        }
    }

    EditContactListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof EditContactListener){
            mListener = (EditContactListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement EditContactListener");
        }
    }

    interface EditContactListener {
        void gotoAddPhoneNumber();
        void doneEditingContact();
        void cancelEditingContact();
    }
}