package edu.uncc.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.uncc.assignment08.databinding.ContactListItemBinding;
import edu.uncc.assignment08.databinding.FragmentContactsBinding;

public class ContactsFragment extends Fragment {
    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    FragmentContactsBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ListenerRegistration contactsListener;
    ArrayList<Contact> mContacts = new ArrayList<>();
    ContactsAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Contacts");

        adapter = new ContactsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
        contactsListener = db.collection("contacts").whereEqualTo("uid", mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null){
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(value != null){
                    mContacts.clear();
                    for (QueryDocumentSnapshot doc : value) {
                        Contact contact = doc.toObject(Contact.class);
                        mContacts.add(contact);
                    }
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(contactsListener != null){
            contactsListener.remove();
            contactsListener = null;
        }
    }

    class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>{

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ContactListItemBinding itemBinding = ContactListItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ContactViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
            holder.setupUI(mContacts.get(position));
        }

        @Override
        public int getItemCount() {
            return mContacts.size();
        }

        class ContactViewHolder extends RecyclerView.ViewHolder{
            ContactListItemBinding itemBinding;
            Contact mContact;
            public ContactViewHolder(ContactListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(Contact contact){
                mContact = contact;
                itemBinding.textViewName.setText(contact.getName());
                itemBinding.textViewEmail.setText(contact.getEmail());
                int size = mContact.getPhoneNumbers().size();

                if(size == 0){
                    itemBinding.textViewPhoneNumber.setText("No Phone Numbers !!");
                } else {
                    PhoneNumber phoneNumber = mContact.getPhoneNumbers().get(0);
                    if(size == 1){
                        itemBinding.textViewPhoneNumber.setText(phoneNumber.getNumber() + "(" + phoneNumber.getType() + "), 0 other numbers");
                    } else {
                        itemBinding.textViewPhoneNumber.setText(phoneNumber.getNumber() + "(" + phoneNumber.getType() + "), " + (size - 1) + " other numbers");
                    }
                }

                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("contacts").document(mContact.getDocId()).delete();
                    }
                });

                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoContactDetails(mContact);
                    }
                });
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_new_item) {
            mListener.gotoCreateContact();
            return true;
        } else if(item.getItemId() == R.id.logout_item){
            mListener.logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ContactsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ContactsListener){
            mListener = (ContactsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ContactsListener");
        }
    }

    interface ContactsListener {
        void gotoCreateContact();
        void logout();
        void gotoContactDetails(Contact contact);
    }
}