package edu.uncc.hw06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import edu.uncc.hw06.databinding.FragmentCreateForumBinding;
import edu.uncc.hw06.models.User;

public class CreateForumFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CreateForumFragment() {
        // Required empty public constructor
    }

    FragmentCreateForumBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateForumBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Forum");

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelCreateForum();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.editTextForumTitle.getText().toString();
                String desc = binding.editTextForumDesc.getText().toString();
                if(title.isEmpty()){
                    Toast.makeText(getActivity(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
                } else if(desc.isEmpty()){
                    Toast.makeText(getActivity(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                } else {

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("title", title);
                    data.put("description", desc);
                    data.put("ownerId", mAuth.getCurrentUser().getUid());
                    data.put("ownerName", mAuth.getCurrentUser().getDisplayName());
                    data.put("createdAt", FieldValue.serverTimestamp());
                    data.put("likes", new ArrayList<String>());
                    data.put("status", "ACTIVE");
                    data.put("users", new ArrayList<User>());


                    DocumentReference docRef = db.collection("forums").document();
                    data.put("docId", docRef.getId());

                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mListener.doneCreateForum();
                            } else{
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    CreateForumListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreateForumListener) context;
    }

    interface CreateForumListener{
        void cancelCreateForum();
        void doneCreateForum();
    }

}