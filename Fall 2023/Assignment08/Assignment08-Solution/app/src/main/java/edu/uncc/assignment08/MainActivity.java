package edu.uncc.assignment08;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, RegisterFragment.RegisterListener, ContactsFragment.ContactsListener,
        CreateContactFragment.CreateContactListener, AddPhoneNumberFragment.AddPhoneNumberListener,
        ContactDetailsFragment.ContactDetailsListener, EditContactFragment.EditContactListener {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(mAuth.getCurrentUser() == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootView, new LoginFragment())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootView, new ContactsFragment())
                    .commit();
        }
    }


    @Override
    public void gotoCreateAccount() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new RegisterFragment())
                .commit();
    }

    @Override
    public void gotoLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void authSuccessful() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new ContactsFragment())
                .commit();
    }

    @Override
    public void gotoCreateContact() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new CreateContactFragment(), "create-contact")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logout() {
        mAuth.signOut();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoContactDetails(Contact contact) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, ContactDetailsFragment.newInstance(contact))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendCreatedPhonNumber(PhoneNumber phoneNumber) {
        CreateContactFragment createContactFragment = (CreateContactFragment) getSupportFragmentManager().findFragmentByTag("create-contact");
        if (createContactFragment != null){
            createContactFragment.addToPhoneNumbers(phoneNumber);
            getSupportFragmentManager().popBackStack();
            return;
        }

        EditContactFragment editContactFragment = (EditContactFragment) getSupportFragmentManager().findFragmentByTag("edit-contact");
        if(editContactFragment != null){
            editContactFragment.addNewPhoneNumber(phoneNumber);
            getSupportFragmentManager().popBackStack();
            return;
        }
    }

    @Override
    public void cancelCreatePhoneNumber() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoAddPhoneNumber() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView, new AddPhoneNumberFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void doneEditingContact() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelEditingContact() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelCreateContact() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void completedCreateContact() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void editContact(Contact contact) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootView,EditContactFragment.newInstance(contact), "edit-contact")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void cancelContactDetails() {
        getSupportFragmentManager().popBackStack();
    }
}