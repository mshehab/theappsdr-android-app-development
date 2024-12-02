package edu.uncc.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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

import edu.uncc.weatherapp.R;
import edu.uncc.weatherapp.databinding.FragmentCitiesBinding;
import edu.uncc.weatherapp.models.City;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CitiesFragment extends Fragment {
    public CitiesFragment() {
        // Required empty public constructor
    }

    FragmentCitiesBinding binding;
    ArrayList<City> mCities = new ArrayList<>();
    ArrayAdapter<City> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCitiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mCities);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = mCities.get(position);
                mListener.onCitySelected(city);
            }
        });
        getAllCities();
    }
    private final OkHttpClient client = new OkHttpClient();

    private void getAllCities(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/cities")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Unable to complete request!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    try {
                        JSONObject jsonRoot = new JSONObject(body);
                        JSONArray citiesJsonArray = jsonRoot.getJSONArray("cities");
                        mCities.clear();
                        for (int i = 0; i < citiesJsonArray.length(); i++) {
                            JSONObject cityJson = citiesJsonArray.getJSONObject(i);
                            City city = new City(cityJson);
                            mCities.add(city);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Unable to complete request!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    CitiesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CitiesListener){
            mListener = (CitiesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CitiesListener");
        }
    }

    public interface CitiesListener{
        void onCitySelected(City city);
    }

}