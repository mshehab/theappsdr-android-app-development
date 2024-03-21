package edu.uncc.giftlistapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.giftlistapp.databinding.FragmentGiftListBinding;
import edu.uncc.giftlistapp.databinding.ListItemProductBinding;
import edu.uncc.giftlistapp.models.GiftList;
import edu.uncc.giftlistapp.models.GiftListProduct;
import edu.uncc.giftlistapp.models.Product;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GiftListFragment extends Fragment {
    public GiftListFragment() {
        // Required empty public constructor
    }

    FragmentGiftListBinding binding;
    private static final String ARG_PARAM_GIFTLIST = "ARG_PARAM_GIFTLIST";
    private GiftList mGiftList;
    ProductsAdapter adapter;
    double overAllCost = 0.0;
    int overAllCount = 0;

    public static GiftListFragment newInstance(GiftList giftList) {
        GiftListFragment fragment = new GiftListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_GIFTLIST, giftList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGiftList = (GiftList) getArguments().getSerializable(ARG_PARAM_GIFTLIST);
            mProducts = mGiftList.getItems();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGiftListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<GiftListProduct> mProducts = new ArrayList<>();

    String mToken;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Gift List");
        mToken = mListener.getAuthToken(); //token authorization

        adapter = new ProductsAdapter();
        mProducts = mGiftList.getItems();
        computeOverallCost();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.doneWithGiftList();
            }
        });
    }

    private final OkHttpClient client = new OkHttpClient();
    private void addProductToList(String gid, String pid){
        RequestBody formBody = new FormBody.Builder()
                .add("gid",gid)
                .add("pid",pid)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/giftlists/add-item")
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

                        }
                    });

                }
            }
        });


    }

    private void removeProductFromList(String gid, String pid){
        RequestBody formBody = new FormBody.Builder()
                .add("gid",gid)
                .add("pid",pid)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/giftlists/remove-item")
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

                        }
                    });

                }
            }
        });


    }

    class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>{
        @NonNull
        @Override
        public ProductsAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemProductBinding itemBinding = ListItemProductBinding.inflate(getLayoutInflater(), parent, false);
            return new ProductsAdapter.ProductViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductsAdapter.ProductViewHolder holder, int position) {
            GiftListProduct product = mProducts.get(position);
            holder.setupUI(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }

        class ProductViewHolder extends RecyclerView.ViewHolder{
            ListItemProductBinding itemBinding;
            GiftListProduct mProduct;
            public ProductViewHolder(ListItemProductBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            private void setupUI(GiftListProduct product){
                mProduct = product;
                itemBinding.textViewName.setText(product.getName());
                itemBinding.textViewCostPerItem.setText("$" + product.getPrice_per_item());
                itemBinding.textViewItemCount.setText(String.valueOf(product.getCount()));

                Picasso.get().load(product.getImg_url()).into(itemBinding.imageViewIcon);

                itemBinding.imageViewMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mProduct.getCount() > 0){
                            mProduct.decrementCount();
                            notifyDataSetChanged();
                            computeOverallCost();
                            removeProductFromList(mGiftList.getGid(), mProduct.getPid());
                        }

                    }
                });

                itemBinding.imageViewPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProduct.incrementCount();
                        notifyDataSetChanged();
                        computeOverallCost();
                        addProductToList(mGiftList.getGid(), mProduct.getPid());
                    }
                });
            }
        }
    }

    private void computeOverallCost(){
        overAllCost = 0.0;
        overAllCount = 0;
        for (GiftListProduct product : mProducts) {
            overAllCost = overAllCost + (product.getPrice_per_item() * product.getCount());
            overAllCount = overAllCount + product.getCount();
        }
        binding.textViewOverallCost.setText(String.format("$%.2f", overAllCost));
    }

    GiftListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GiftListListener){
            mListener = (GiftListListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GiftListListener");
        }
    }

    interface GiftListListener{
        String getAuthToken();
        void doneWithGiftList();
    }
}