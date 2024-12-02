package edu.uncc.evaluation04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import edu.uncc.evaluation04.R;
import edu.uncc.evaluation04.databinding.CourseRowItemBinding;
import edu.uncc.evaluation04.databinding.FragmentSelectCourseBinding;
import edu.uncc.evaluation04.models.Course;
import edu.uncc.evaluation04.models.DataSource;

public class SelectCourseFragment extends Fragment {
    public SelectCourseFragment() {
        // Required empty public constructor
    }

    FragmentSelectCourseBinding binding;
    CoursesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Course");
        mAdapter = new CoursesAdapter(requireContext(), R.layout.course_row_item, DataSource.getCourses());
        binding.listView.setAdapter(mAdapter);
        binding.listView.setOnItemClickListener((parent, view1, position, id) -> {
            Course course = mAdapter.getItem(position);
            mListener.onCourseSelected(course);
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelection();
            }
        });
    }

    class CoursesAdapter extends ArrayAdapter<Course>{
        public CoursesAdapter(@NonNull Context context, int resource, @NonNull List<Course> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            CourseRowItemBinding rowBinding;
            if (convertView == null){
                rowBinding = CourseRowItemBinding.inflate(getLayoutInflater(), parent, false);
                convertView = rowBinding.getRoot();
                convertView.setTag(rowBinding);
            } else {
                rowBinding = (CourseRowItemBinding) convertView.getTag();
            }
            Course course = getItem(position);
            rowBinding.textViewCourseName.setText(course.getName());
            rowBinding.textViewCourseNumber.setText(course.getNumber());
            rowBinding.textViewCreditHours.setText(String.format("%.2f", course.getHours()) + " Credit Hours");
            return convertView;
        }
    }

    SelectCourseListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectCourseListener){
            mListener = (SelectCourseListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectCourseListener");
        }
    }

    public interface SelectCourseListener {
        void onCourseSelected(Course course);
        void onCancelSelection();
    }
}