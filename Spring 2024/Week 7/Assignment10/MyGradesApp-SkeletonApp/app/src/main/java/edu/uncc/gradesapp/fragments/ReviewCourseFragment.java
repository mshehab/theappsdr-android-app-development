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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentReviewCourseBinding;
import edu.uncc.gradesapp.databinding.ReviewRowItemBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.CourseReview;
import edu.uncc.gradesapp.models.Review;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReviewCourseFragment extends Fragment {
    private static final String ARG_PARAM_COURSE= "ARG_PARAM_COURSE";
    private Course mCourse;
    ArrayList<Review> mReviews = new ArrayList<>();
    ReviewsAdapter adapter;

    public ReviewCourseFragment() {
        // Required empty public constructor
    }

    public static ReviewCourseFragment newInstance(Course course) {
        ReviewCourseFragment fragment = new ReviewCourseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_COURSE, course);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourse = (Course) getArguments().getSerializable(ARG_PARAM_COURSE);
        }
        setHasOptionsMenu(true);
    }

    FragmentReviewCourseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Review Course");

        binding.textViewCourseName.setText(mCourse.getName());
        binding.textViewCourseNumber.setText(mCourse.getNumber());
        binding.textViewCreditHours.setText(String.valueOf(mCourse.getHours()) + " Credit Hours");

        adapter = new ReviewsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewText = binding.editTextReview.getText().toString();
                if(reviewText.isEmpty()){
                    Toast.makeText(getActivity(), "Review cannot be empty", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });
    }



    class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
        @NonNull
        @Override
        public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ReviewRowItemBinding itemBinding = ReviewRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ReviewViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
            Review review = mReviews.get(position);
            holder.setupUI(review);
        }

        @Override
        public int getItemCount() {
            return mReviews.size();
        }

        class ReviewViewHolder extends RecyclerView.ViewHolder {
            ReviewRowItemBinding itemBinding;
            Review mReview;
            public ReviewViewHolder(ReviewRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }


            private void setupUI(Review review){
                this.mReview = review;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_cancel){
            mListener.onSelectionCanceled();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ReviewCourseListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (ReviewCourseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ReviewCourseListener");
        }
    }

    public interface ReviewCourseListener{
        void onSelectionCanceled();
    }
}