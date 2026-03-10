package edu.charlotte.evaluation02;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;

import edu.charlotte.evaluation02.fragments.DepartmentSelectFilterFragment;
import edu.charlotte.evaluation02.fragments.EmployeesFragment;
import edu.charlotte.evaluation02.fragments.PurchaseDetailsFragment;
import edu.charlotte.evaluation02.fragments.PurchasesFragment;
import edu.charlotte.evaluation02.models.Employee;
import edu.charlotte.evaluation02.models.Purchase;

public class MainActivity extends AppCompatActivity implements EmployeesFragment.EmployeesListener, DepartmentSelectFilterFragment.DepartmentsSelectListener, PurchasesFragment.PurchasesListener, PurchaseDetailsFragment.DetailsListener {

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

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new EmployeesFragment(), "EmployeesFragment")
                .commit();

    }

    @Override
    public void gotoSelectDeptFilter() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new DepartmentSelectFilterFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoPurchases(Employee employee) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, PurchasesFragment.newInstance(employee))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void doneSelectingDepts(HashSet<String> depts) {
        EmployeesFragment employeesFragment = (EmployeesFragment) getSupportFragmentManager().findFragmentByTag("EmployeesFragment");
        if(employeesFragment != null){
            employeesFragment.setDeptFilter(depts);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelSelectDepts() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancelPurchases() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoPurchaseDetails(Purchase purchase) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, PurchaseDetailsFragment.newInstance(purchase))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCancelDetails() {
        getSupportFragmentManager().popBackStack();
    }
}