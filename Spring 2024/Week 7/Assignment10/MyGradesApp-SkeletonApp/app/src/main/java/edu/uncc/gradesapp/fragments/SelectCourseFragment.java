
package edu.uncc.gradesapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.CourseRowItemBinding;
import edu.uncc.gradesapp.databinding.FragmentSelectCourseBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.LetterGrade;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//
public class SelectCourseFragment extends Fragment {
    public SelectCourseFragment() {
        // Required empty public constructor
    }

    ArrayList<Course> mCourses = new ArrayList<>();
    CoursesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    FragmentSelectCourseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Course");
        adapter = new CoursesAdapter(getActivity(), R.layout.course_row_item, mCourses);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course course = mCourses.get(position);
                mListener.onCourseSelected(course);
            }
        });
        getCourses();
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

    private final OkHttpClient client = new OkHttpClient();

    private void getCourses(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/cci-courses")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    try {
                        mCourses.clear();
                        JSONObject json = new JSONObject(body);
                        JSONArray courses = json.getJSONArray("courses");
                        for (int i = 0; i < courses.length(); i++) {
                            Course course = new Course(courses.getJSONObject(i));
                            mCourses.add(course);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Failed to get courses", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    class CoursesAdapter extends ArrayAdapter<Course> {
        public CoursesAdapter(@NonNull Context context, int resource, @NonNull List<Course> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            CourseRowItemBinding itemBinding;

            if(convertView == null){
                itemBinding = CourseRowItemBinding.inflate(getLayoutInflater(), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            } else {
                itemBinding = (CourseRowItemBinding) convertView.getTag();
            }

            Course course = getItem(position);
            itemBinding.textViewCourseName.setText(course.getName());
            itemBinding.textViewCourseNumber.setText(course.getNumber());
            itemBinding.textViewCreditHours.setText(course.getHours() + " Credit Hours");
            return convertView;
        }
    }

    CoursesListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CoursesListener){
            mListener = (CoursesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CoursesListener");
        }
    }

    public interface CoursesListener {
        void onCourseSelected(Course course);
        void onSelectionCanceled();
    }
}