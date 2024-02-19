package edu.uncc.assignment04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.uncc.assignment04.fragments.DemographicFragment;
import edu.uncc.assignment04.fragments.IdentificationFragment;
import edu.uncc.assignment04.fragments.MainFragment;
import edu.uncc.assignment04.fragments.ProfileFragment;
import edu.uncc.assignment04.fragments.SelectMaritalStatusFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, IdentificationFragment.IndentificationListener,
        DemographicFragment.DemographicListener, SelectMaritalStatusFragment.MaritalStatusListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new MainFragment())
                .commit();

    }

    @Override
    public void gotoIdentificationFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new IdentificationFragment())
                .commit();
    }

    @Override
    public void gotoDemographicFromIdentification(Response response) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, DemographicFragment.newInstance(response), "demographicFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoProfile(Response response) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(response))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void selectEducation() {

    }

    @Override
    public void selectMaritalStatus() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectMaritalStatusFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void selectLivingStatus() {

    }

    @Override
    public void selectIncome() {

    }

    @Override
    public void sendMaritalStatus(String maritalStatus) {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographicFragment");
        if(fragment != null) {
            fragment.setMaritalStatus(maritalStatus);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelSelection() {
        getSupportFragmentManager().popBackStack();
    }
}