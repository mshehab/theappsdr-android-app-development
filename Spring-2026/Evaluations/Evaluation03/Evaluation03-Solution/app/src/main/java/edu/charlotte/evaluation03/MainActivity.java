package edu.charlotte.evaluation03;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import edu.charlotte.evaluation03.fragments.AnswerPollFragment;
import edu.charlotte.evaluation03.fragments.CreateAccountFragment;
import edu.charlotte.evaluation03.fragments.CreatePollFragment;
import edu.charlotte.evaluation03.fragments.LoginFragment;
import edu.charlotte.evaluation03.fragments.PollResultsFragment;
import edu.charlotte.evaluation03.fragments.PollsFragment;
import edu.charlotte.evaluation03.models.Poll;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, CreateAccountFragment.RegisterListener, PollsFragment.PollsListener, CreatePollFragment.CreatePollListener,
        AnswerPollFragment.AnswerPollListener, PollResultsFragment.PollStatsListener {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(mAuth.getCurrentUser() != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new PollsFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new LoginFragment())
                    .commit();
        }
    }

    @Override
    public void onCreateAccountSuccess() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new PollsFragment())
                .commit();
    }

    @Override
    public void gotoLoginAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void onLoginSuccess() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new PollsFragment())
                .commit();
    }

    @Override
    public void gotoCreateAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateAccountFragment())
                .commit();
    }

    @Override
    public void logout() {
        mAuth.signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoAddPoll() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreatePollFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoAnswerPoll(Poll poll) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, AnswerPollFragment.newInstance(poll))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoPollResults(Poll poll) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, PollResultsFragment.newInstance(poll))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreatePollDone() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void doneAnsweringPoll() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void donePollStats() {
        getSupportFragmentManager().popBackStack();
    }
}