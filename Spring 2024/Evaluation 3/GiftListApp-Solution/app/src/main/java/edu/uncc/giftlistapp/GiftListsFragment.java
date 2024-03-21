package edu.uncc.giftlistapp;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.giftlistapp.databinding.FragmentGiftListsBinding;
import edu.uncc.giftlistapp.databinding.ListItemGiftlistBinding;
import edu.uncc.giftlistapp.models.GiftList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GiftListsFragment extends Fragment {
    public GiftListsFragment() {
        // Required empty public constructor
    }

    FragmentGiftListsBinding binding;

    ArrayList<GiftList> giftLists = new ArrayList<>();
    GiftListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGiftListsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    String mToken;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Gift Lists");
        mToken = mListener.getAuthToken(); //token authorization

        adapter = new GiftListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        getGiftLists();
    }
    private final OkHttpClient client = new OkHttpClient();

    private void getGiftLists(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/giftlists/lists")
                .header("Authorization", "BEARER " + mToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String body = response.body().string();
                    giftLists.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArrayLists = jsonObject.getJSONArray("lists");
                        for (int i = 0; i < jsonArrayLists.length(); i++) {
                            JSONObject jsonListObject = jsonArrayLists.getJSONObject(i);
                            GiftList giftList = new GiftList(jsonListObject);
                            giftLists.add(giftList);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //notify the adapter..
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });


    }


    private void deleteGiftLists(GiftList giftList){
        RequestBody formBody = new FormBody.Builder()
                .add("gid",giftList.getGid())
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/giftlists/delete")
                .header("Authorization", "BEARER " + mToken)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //notify the adapter..
                            getGiftLists();
                        }
                    });

                }
            }
        });


    }

    class GiftListAdapter extends RecyclerView.Adapter<GiftListAdapter.GiftListViewHolder>{

        @NonNull
        @Override
        public GiftListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemGiftlistBinding itemBinding = ListItemGiftlistBinding.inflate(getLayoutInflater(), parent, false);
            return new GiftListViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GiftListViewHolder holder, int position) {
            holder.setupUI(giftLists.get(position));
        }

        @Override
        public int getItemCount() {
            return giftLists.size();
        }

        class GiftListViewHolder extends RecyclerView.ViewHolder{
            ListItemGiftlistBinding itemBinding;
            GiftList mGiftList;
            public GiftListViewHolder(ListItemGiftlistBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(GiftList giftList){
                this.mGiftList = giftList;
                itemBinding.textViewName.setText(giftList.getName());
                itemBinding.textViewTotalCost.setText("$" + giftList.getTotalCost());
                itemBinding.textViewTotalItems.setText(giftList.getTotalCount() + " items");
                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteGiftLists(mGiftList);
                    }
                });

                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoGiftListDetails(mGiftList);
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
        if (item.getItemId() == R.id.action_logout){
            mListener.performLogout();
            return true;
        } else if (item.getItemId() == R.id.action_add){
            mListener.gotoAddNewGiftList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    GiftListsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GiftListsListener){
            mListener = (GiftListsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GiftListsListener");
        }
    }

    interface GiftListsListener{
        String getAuthToken();
        void gotoAddNewGiftList();
        void performLogout();
        void gotoGiftListDetails(GiftList giftList);
    }
}