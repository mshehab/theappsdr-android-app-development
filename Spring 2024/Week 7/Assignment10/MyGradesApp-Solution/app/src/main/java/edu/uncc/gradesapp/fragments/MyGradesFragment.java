package edu.uncc.gradesapp.fragments;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentMyGradesBinding;
import edu.uncc.gradesapp.databinding.GradeRowItemBinding;
import edu.uncc.gradesapp.models.Grade;

public class MyGradesFragment extends Fragment {
    public MyGradesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_add){
            mListener.gotoAddGrade();
            return true;
        } else if(item.getItemId() == R.id.action_logout) {
            mListener.logout();
            return true;
        } else if(item.getItemId() == R.id.action_reviews){
            mListener.gotoViewReviews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    FragmentMyGradesBinding binding;
    ArrayList<Grade> mGrades = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    GradesAdapter adapter;
    ListenerRegistration listenerRegistration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Grades");
        adapter = new GradesAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        listenerRegistration = db.collection("grades").whereEqualTo("uid", mAuth.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mGrades.clear();
                if (value != null && !value.isEmpty()) {
                    for (DocumentSnapshot doc: value.getDocuments()) {
                        Grade grade = doc.toObject(Grade.class);
                        mGrades.add(grade);
                    }
                }
                adapter.notifyDataSetChanged();
                setupGPA();
            }
        });
    }

    private void setupGPA(){
        double totalCredits = 0;
        double totalGradePoints = 0;
        for(Grade grade: mGrades){
            totalCredits = totalCredits + grade.getNumberOfCredits();
            totalGradePoints = totalGradePoints + (grade.getNumericGrade() * grade.getNumberOfCredits());
        }

        if(totalCredits > 0){
            double gpa = totalGradePoints / totalCredits;
            binding.textViewHours.setText("Hours: " + String.format("%.2f", totalCredits));
            binding.textViewGPA.setText("GPA: " + String.format("%.2f", gpa));
        } else {
            binding.textViewHours.setText("Hours: 0.0");
            binding.textViewGPA.setText("GPA: 0.0");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration != null){
            listenerRegistration.remove();
            mGrades.clear();
        }
    }

    class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradeViewHolder>{
        @NonNull
        @Override
        public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GradeRowItemBinding itemBinding = GradeRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new GradeViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
            holder.setupUI(mGrades.get(position));
        }

        @Override
        public int getItemCount() {
            return mGrades.size();
        }

        class GradeViewHolder extends RecyclerView.ViewHolder{
            GradeRowItemBinding itemBinding;
            Grade mGrade;
            public GradeViewHolder(GradeRowItemBinding itemBinding ) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(Grade grade){
                this.mGrade = grade;
                itemBinding.textViewCourseName.setText(grade.getCourseName());
                itemBinding.textViewCourseNumber.setText(grade.getCourseNumber());
                itemBinding.textViewLetterGrade.setText(grade.getLetterGrade());
                itemBinding.textViewSemester.setText(grade.getSemester());
                itemBinding.textViewCreditHours.setText(grade.getNumberOfCredits() + " credit hours");
                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("grades").document(mGrade.getDocId()).delete();
                    }
                });

            }
        }
    }

    MyGradesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (MyGradesListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement MyGradesListener");
        }
    }

    public interface MyGradesListener {
        void gotoAddGrade();
        void logout();
        void gotoViewReviews();
    }
}