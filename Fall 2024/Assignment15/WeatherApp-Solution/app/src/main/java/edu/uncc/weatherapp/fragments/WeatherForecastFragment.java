package edu.uncc.weatherapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.weatherapp.R;
import edu.uncc.weatherapp.databinding.ForecastListItemBinding;
import edu.uncc.weatherapp.databinding.FragmentWeatherForecastBinding;
import edu.uncc.weatherapp.models.City;
import edu.uncc.weatherapp.models.Forecast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherForecastFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";

    private City mCity;
    ArrayList<Forecast> mForecasts = new ArrayList<>();
    ForecastAdapter adapter;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    FragmentWeatherForecastBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewCityName.setText(mCity.getName() + ", " + mCity.getState());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ForecastAdapter();
        binding.recyclerView.setAdapter(adapter);
        getStep1();
    }
    private final OkHttpClient client = new OkHttpClient();

    private void getStep1(){
        String url = "https://api.weather.gov/points/" + mCity.getLat() + "," + mCity.getLng();
        Request request = new Request.Builder()
                .url(url)
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
                        JSONObject jsonRoot = new JSONObject(body);
                        JSONObject properties = jsonRoot.getJSONObject("properties");
                        String forecastUrl = properties.getString("forecast");
                        getStep2(forecastUrl);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Unable to get forecast!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void getStep2(String url){
        Request request = new Request.Builder()
                .url(url)
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
                    Log.d("demo", "onResponse: " + body);
                    try {
                        JSONObject jsonRoot = new JSONObject(body);
                        JSONObject properties = jsonRoot.getJSONObject("properties");
                        JSONArray periods = properties.getJSONArray("periods");
                        for (int i = 0; i < periods.length(); i++) {
                            JSONObject forecastJson = periods.getJSONObject(i);
                            Forecast forecast = new Forecast(forecastJson);
                            mForecasts.add(forecast);
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
                            Toast.makeText(getActivity(), "Unable to get forecast!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>{
        @NonNull
        @Override
        public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ForecastListItemBinding itemBinding = ForecastListItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ForecastViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
            Forecast forecast = mForecasts.get(position);
            holder.bind(forecast);
        }

        @Override
        public int getItemCount() {
            return mForecasts.size();
        }

        class ForecastViewHolder extends RecyclerView.ViewHolder{
            ForecastListItemBinding itemBinding;
            public ForecastViewHolder(ForecastListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(Forecast forecast){
                Picasso.get().load(forecast.getIcon()).into(itemBinding.imageView);
                itemBinding.textViewDateTime.setText(forecast.getStartTime());
                itemBinding.textViewForecast.setText(forecast.getShortForecast());
                itemBinding.textViewHumidity.setText("Precipitation: " + forecast.getProbOfPrecipitation() + "%");
                itemBinding.textViewWindSpeed.setText("Wind Speed: " + forecast.getWindSpeed());
                itemBinding.textViewTemperature.setText(forecast.getTemperature() + " F");
            }
        }
    }
}