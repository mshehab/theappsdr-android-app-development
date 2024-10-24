package edu.uncc.evaluation02.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.databinding.FragmentSelectBillDateBinding;

public class SelectBillDateFragment extends Fragment {
    public SelectBillDateFragment() {
        // Required empty public constructor
    }

    FragmentSelectBillDateBinding binding;
    Date selectedDate = new Date();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectBillDateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.calendarView.setMaxDate(new Date().getTime());
        binding.calendarView.setDate(new Date().getTime());

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //get the selected date from the calendar view
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                selectedDate = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Log.d("demo", "onSelectedDayChange: " + sdf.format(selectedDate));
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectionCanceled();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDateSelected(selectedDate);
            }
        });
    }

    SelectDateListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (SelectDateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SelectDateListener");
        }
    }

    public interface SelectDateListener {
        void onDateSelected(Date date);
        void onSelectionCanceled();
    }
}