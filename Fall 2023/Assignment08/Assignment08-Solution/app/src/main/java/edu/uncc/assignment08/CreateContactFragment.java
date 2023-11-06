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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import edu.uncc.assignment08.databinding.FragmentCreateContactBinding;
import edu.uncc.assignment08.databinding.PhoneListItemBinding;

public class CreateContactFragment extends Fragment {
    public CreateContactFragment() {
        // Required empty public constructor
    }

    FragmentCreateContactBinding binding;
    ArrayList<PhoneNumber> mPhoneNumbers = new ArrayList<>();
    PhoneNumbersAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void addToPhoneNumbers(PhoneNumber phoneNumber){
        mPhoneNumbers.add(phoneNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Contact");

        binding.imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddPhoneNumber();
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelCreateContact();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmailAddress.getText().toString();
                if(name.isEmpty() || email.isEmpty()){
                    Toast.makeText(getActivity(), "Name and Email are required", Toast.LENGTH_SHORT).show();
                } else if(mPhoneNumbers.size() == 0){
                    Toast.makeText(getActivity(), "At least one Phone number is required!", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> data = new HashMap<>();

                    data.put("name", name);
                    data.put("email", email);
                    data.put("phoneNumbers", mPhoneNumbers);
                    DocumentReference docRef = db.collection("contacts").document();
                    data.put("docId", docRef.getId());
                    data.put("uid", mAuth.getCurrentUser().getUid());

                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mListener.completedCreateContact();
                            } else {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PhoneNumbersAdapter();
        binding.recyclerView.setAdapter(adapter);

    }

    private class PhoneNumbersAdapter extends RecyclerView.Adapter<PhoneNumbersAdapter.PhoneNumbersViewHolder> {
        @NonNull
        @Override
        public PhoneNumbersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PhoneListItemBinding itemBinding = PhoneListItemBinding.inflate(getLayoutInflater(), parent, false);
            return new PhoneNumbersViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull PhoneNumbersViewHolder holder, int position) {
            holder.setupUI(mPhoneNumbers.get(position));
        }

        @Override
        public int getItemCount() {
            return mPhoneNumbers.size();
        }

        class PhoneNumbersViewHolder extends RecyclerView.ViewHolder {
            PhoneListItemBinding itemBinding;
            PhoneNumber mPhoneNumber;
            public PhoneNumbersViewHolder(PhoneListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(PhoneNumber phoneNumber){
                this.mPhoneNumber = phoneNumber;
                itemBinding.textViewPhoneNumber.setText(phoneNumber.getNumber());
                itemBinding.textViewType.setText(phoneNumber.getType());

                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPhoneNumbers.remove(mPhoneNumber);
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }


    CreateContactListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CreateContactListener) {
            mListener = (CreateContactListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CreateContactListener");
        }
    }

    interface CreateContactListener {
        void gotoAddPhoneNumber();
        void cancelCreateContact();
        void completedCreateContact();
    }

}