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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.giftlistapp.databinding.FragmentCreateGiftListBinding;
import edu.uncc.giftlistapp.databinding.FragmentGiftListBinding;
import edu.uncc.giftlistapp.databinding.ListItemProductBinding;
import edu.uncc.giftlistapp.models.GiftList;
import edu.uncc.giftlistapp.models.Product;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateGiftListFragment extends Fragment {
    ArrayList<Product> mProducts = new ArrayList<>();
    ProductsAdapter adapter;
    double overAllCost = 0.0;
    int overAllCount = 0;
    public CreateGiftListFragment() {
        // Required empty public constructor
    }

    FragmentCreateGiftListBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateGiftListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    String mToken;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Gift List");
        mToken = mListener.getAuthToken(); //token authorization
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductsAdapter();
        binding.recyclerView.setAdapter(adapter);

        getProducts();

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextGiftListName.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_SHORT).show();
                } else if(overAllCount == 0){
                    Toast.makeText(getActivity(), "Please add products to the list", Toast.LENGTH_SHORT).show();
                } else {
                    //lets compute the list of productIds
                    ArrayList<String> ids = new ArrayList<>();
                    for (Product product : mProducts) {
                        for (int i = 0; i < product.getCount() ; i++) {
                            ids.add(product.getPid());
                        }
                    }
                    String productIds = String.join(",", ids);
                    submitAddNewGiftList(name, productIds);
                }
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelCreatingGiftList();
            }
        });
    }

    private final OkHttpClient client = new OkHttpClient();

    private void getProducts(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/giftlists/products")
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
                    mProducts.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArrayProducts = jsonObject.getJSONArray("products");
                        for (int i = 0; i < jsonArrayProducts.length(); i++) {
                            JSONObject jsonProductObject = jsonArrayProducts.getJSONObject(i);
                            Product product = new Product(jsonProductObject);
                            mProducts.add(product);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //notify the adapter..
                                adapter.notifyDataSetChanged();
                                computeOverallCost();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });


    }

    private void submitAddNewGiftList(String name, String productIds){
        RequestBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("productIds", productIds)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/giftlists/new")
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
                            mListener.doneCreatingGiftList();
                        }
                    });
                }
            }
        });
    }

    class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>{
        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemProductBinding itemBinding = ListItemProductBinding.inflate(getLayoutInflater(), parent, false);
            return new ProductViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            Product product = mProducts.get(position);
            holder.setupUI(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }

        class ProductViewHolder extends RecyclerView.ViewHolder{
            ListItemProductBinding itemBinding;
            Product mProduct;
            public ProductViewHolder(ListItemProductBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            private void setupUI(Product product){
                mProduct = product;
                itemBinding.textViewName.setText(product.getName());
                itemBinding.textViewCostPerItem.setText("$" + product.getPrice());
                itemBinding.textViewItemCount.setText(String.valueOf(product.getCount()));

                Picasso.get().load(product.getImg_url()).into(itemBinding.imageViewIcon);

                itemBinding.imageViewMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProduct.decrementCount();
                        notifyDataSetChanged();
                        computeOverallCost();
                    }
                });

                itemBinding.imageViewPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProduct.incrementCount();
                        notifyDataSetChanged();
                        computeOverallCost();
                    }
                });
            }
        }
    }

    private void computeOverallCost(){
        overAllCost = 0.0;
        overAllCount = 0;
        for (Product product : mProducts) {
            overAllCost = overAllCost + (product.getPrice() * product.getCount());
            overAllCount = overAllCount + product.getCount();
        }
        binding.textViewOverallCost.setText(String.format("$%.2f", overAllCost));
    }

    CreateGiftListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (CreateGiftListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CreateGiftListListener");
        }
    }

    interface CreateGiftListListener{
        String getAuthToken();
        void doneCreatingGiftList();
        void cancelCreatingGiftList();
    }
}