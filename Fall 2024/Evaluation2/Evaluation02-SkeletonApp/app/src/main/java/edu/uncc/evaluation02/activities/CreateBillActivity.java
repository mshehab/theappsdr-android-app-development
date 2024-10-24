package edu.uncc.evaluation02.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.fragments.BillSummaryFragment;
import edu.uncc.evaluation02.fragments.CreateBillFragment;
import edu.uncc.evaluation02.fragments.SelectBillDateFragment;
import edu.uncc.evaluation02.fragments.SelectDiscountFragment;
import edu.uncc.evaluation02.model.Bill;

public class CreateBillActivity extends AppCompatActivity implements CreateBillFragment.CreateBillListener,
        SelectDiscountFragment.SelectDiscountListener, SelectBillDateFragment.SelectDateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_bill);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateBillFragment(), "create-bill-fragment")
                .commit();
    }

    @Override
    public void gotoSelectDiscount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectDiscountFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectDate() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectBillDateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendSelectedBill(Bill bill) {
        Intent intent = new Intent();
        intent.putExtra(BillSummaryFragment.BILL_KEY, bill);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCancelCreateBill() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onDiscountSelected(double discount) {
        CreateBillFragment createBillFragment = (CreateBillFragment) getSupportFragmentManager().findFragmentByTag("create-bill-fragment");
        if (createBillFragment != null) {
            createBillFragment.setSelectedDiscount(discount);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDateSelected(Date date) {
        CreateBillFragment createBillFragment = (CreateBillFragment) getSupportFragmentManager().findFragmentByTag("create-bill-fragment");
        if (createBillFragment != null) {
            createBillFragment.setSelectedDate(date);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSelectionCanceled() {
        getSupportFragmentManager().popBackStack();
    }
}