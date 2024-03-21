package edu.uncc.giftlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import edu.uncc.giftlistapp.models.GiftList;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, RegisterFragment.RegisterListener,
        GiftListsFragment.GiftListsListener,
        GiftListFragment.GiftListListener,
        CreateGiftListFragment.CreateGiftListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if (sharedPref.contains("token")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.rootView, new GiftListsFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.rootView, new LoginFragment()).commit();
        }
    }


    @Override
    public void onAuthSuccess(String token) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply();
        getSupportFragmentManager().beginTransaction().replace(R.id.rootView, new GiftListsFragment()).commit();
    }

    @Override
    public void gotoLogin() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new RegisterFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public String getAuthToken() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString("token", null);
    }

    @Override
    public void doneWithGiftList() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void doneCreatingGiftList() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelCreatingGiftList() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoAddNewGiftList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new CreateGiftListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void performLogout() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("token");
        editor.apply();
        getSupportFragmentManager().beginTransaction().replace(R.id.rootView, new LoginFragment()).commit();
    }

    @Override
    public void gotoGiftListDetails(GiftList giftList) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, GiftListFragment.newInstance(giftList))
                .addToBackStack(null)
                .commit();
    }
}