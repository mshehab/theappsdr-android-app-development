package edu.uncc.posts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.posts.databinding.FragmentPostsBinding;
import edu.uncc.posts.databinding.PostRowItemBinding;
import edu.uncc.posts.models.AuthResponse;
import edu.uncc.posts.models.Post;
import edu.uncc.posts.models.PostResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostsFragment extends Fragment {
    public PostsFragment() {
        // Required empty public constructor
    }

    FragmentPostsBinding binding;
    PostsAdapter postsAdapter;
    ArrayList<Post> mPosts = new ArrayList<>();
    AuthResponse mAuthResponse;
    int currentPage = 1;
    int totalCount = 0;
    int pageSize = 10;
    int maxPage = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuthResponse = mListener.getAuthResponse();

        if(mAuthResponse.getUser_fullname() != null){
            binding.textViewTitle.setText("Welcome " + mAuthResponse.getUser_fullname());
        } else {
            binding.textViewTitle.setText("Welcome N/A");
        }

        binding.buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createPost();
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.logout();
            }
        });

        binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        postsAdapter = new PostsAdapter();
        binding.recyclerViewPosts.setAdapter(postsAdapter);

        binding.textViewPaging.setText("Loading ...");

        binding.imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage > 1){
                    currentPage--;
                    getPostsByPage(currentPage);
                }
            }
        });

        binding.imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage < maxPage){
                    currentPage++;
                    getPostsByPage(currentPage);
                }
            }
        });

        getActivity().setTitle(R.string.posts_label);
        currentPage = 1;
        getPostsByPage(currentPage);
    }
    private final OkHttpClient client = new OkHttpClient();

    void getPostsByPage(int page){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts?page=" + page)
                .addHeader("Authorization", "BEARER " + mAuthResponse.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Gson gson = new Gson();
                    PostResponse postResponse = gson.fromJson(body, PostResponse.class);
                    totalCount = postResponse.getTotalCount();
                    pageSize = postResponse.getPageSize();

                    maxPage = (int) Math.ceil((double)totalCount / ((double) pageSize));
                    currentPage = postResponse.getPage();
                    if(currentPage > maxPage){
                        currentPage = maxPage;
                    }

                    mPosts.clear();
                    mPosts.addAll(postResponse.getPosts());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postsAdapter.notifyDataSetChanged();
                            binding.textViewPaging.setText("Showing Page " + currentPage + " out of " + maxPage);
                        }
                    });
                    Log.d("demo", "onResponse: "+ body);
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Unable to load posts !!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void deletePost(String postId){
        RequestBody formBody = new FormBody.Builder()
                .add("post_id", postId)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts/delete")
                .addHeader("Authorization", "BEARER " + mAuthResponse.getToken())
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    getPostsByPage(currentPage);
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Unable to delete post !!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
        @NonNull
        @Override
        public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PostRowItemBinding binding = PostRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new PostsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
            Post post = mPosts.get(position);
            holder.setupUI(post);
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }

        class PostsViewHolder extends RecyclerView.ViewHolder {
            PostRowItemBinding mBinding;
            Post mPost;
            public PostsViewHolder(PostRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Post post){
                mPost = post;
                mBinding.textViewPost.setText(post.getPost_text());
                mBinding.textViewCreatedBy.setText(post.getCreated_by_name());
                mBinding.textViewCreatedAt.setText(post.getCreated_at());

                if(mAuthResponse.getUser_id().equals(post.getCreated_by_uid())){
                    mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                } else {
                    mBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }


                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletePost(mPost.getPost_id());
                    }
                });
            }
        }

    }

    PostsListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (PostsListener) context;
    }

    interface PostsListener{
        void logout();
        void createPost();
        AuthResponse getAuthResponse();
    }
}