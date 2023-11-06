package edu.uncc.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.assignment08.databinding.FragmentContactDetailsBinding;
import edu.uncc.assignment08.databinding.PhoneListItemBinding;

public class ContactDetailsFragment extends Fragment {
    private static final String ARG_PARAM_CONTACT = "ARG_PARAM_CONTACT";
    private Contact mContact;

    public ContactDetailsFragment() {
        // Required empty public constructor
    }

    public static ContactDetailsFragment newInstance(Contact contact) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
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
        setHasOptionsMenu(true);
    }

    FragmentContactDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    void displayContact(){
        binding.textViewName.setText(mContact.getName());
        binding.textViewEmail.setText(mContact.getEmail());

        mPhoneNumbers.clear();
        mPhoneNumbers.addAll(mContact.getPhoneNumbers());
    }


    PhoneNumbersAdapter adapter;
    ArrayList<PhoneNumber> mPhoneNumbers = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListenerRegistration contactListener;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Contact Details");

        displayContact();
        adapter = new PhoneNumbersAdapter(getActivity(), R.layout.phone_list_item, mPhoneNumbers);
        binding.listView.setAdapter(adapter);

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelContactDetails();
            }
        });


        contactListener = db.collection("contacts").document(mContact.getDocId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(value != null && value.exists()) {
                    mContact = value.toObject(Contact.class);
                    displayContact();
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

    class PhoneNumbersAdapter extends ArrayAdapter<PhoneNumber>{
        public PhoneNumbersAdapter(@NonNull Context context, int resource, @NonNull List<PhoneNumber> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            PhoneListItemBinding itemBinding;
            if(convertView == null) {
                itemBinding = PhoneListItemBinding.inflate(getLayoutInflater(), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            } else {
                itemBinding = (PhoneListItemBinding) convertView.getTag();
            }

            PhoneNumber phoneNumber = getItem(position);
            itemBinding.textViewType.setText(phoneNumber.getType());
            itemBinding.textViewPhoneNumber.setText(phoneNumber.getNumber());
            itemBinding.imageViewDelete.setVisibility(View.INVISIBLE);
            return convertView;
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.edit_item) {
            mListener.editContact(mContact);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ContactDetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ContactDetailsListener) {
            mListener = (ContactDetailsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ContactDetailsListener");
        }
    }

    interface ContactDetailsListener {
        void editContact(Contact contact);
        void cancelContactDetails();
    }
}