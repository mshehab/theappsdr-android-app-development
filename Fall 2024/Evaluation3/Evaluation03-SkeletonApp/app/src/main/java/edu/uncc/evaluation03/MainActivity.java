package edu.uncc.evaluation03;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import edu.uncc.evaluation03.fragments.AddExpenseFragment;
import edu.uncc.evaluation03.fragments.BudgetListFragment;
import edu.uncc.evaluation03.fragments.SelectCategoryFragment;
import edu.uncc.evaluation03.fragments.SelectPriorityFragment;
import edu.uncc.evaluation03.models.Data;
import edu.uncc.evaluation03.models.Expense;
import edu.uncc.evaluation03.models.Priority;

public class MainActivity extends AppCompatActivity implements BudgetListFragment.BudgetListListener, SelectCategoryFragment.SelectCategoryListener, SelectPriorityFragment.SelectPriorityListener, AddExpenseFragment.AddExpenseListener {
    ArrayList<Expense> mExpenses = new ArrayList<>();
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

        //added some test expenses for testing
        mExpenses.addAll(Data.getTestExpenses());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new BudgetListFragment())
                .commit();
    }

    @Override
    public void onCategorySelected(String category) {
        AddExpenseFragment addExpenseFragment = (AddExpenseFragment) getSupportFragmentManager().findFragmentByTag("add-expense-fragment");
        if(addExpenseFragment != null){
            addExpenseFragment.setSelectedCategory(category);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onPrioritySelected(Priority priority) {
        AddExpenseFragment addExpenseFragment = (AddExpenseFragment) getSupportFragmentManager().findFragmentByTag("add-expense-fragment");
        if(addExpenseFragment != null){
            addExpenseFragment.setSelectedPriority(priority);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancelSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onAddExpense(Expense expense) {

    }

    @Override
    public void gotoSelectCategory() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new SelectCategoryFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectPriority() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new SelectPriorityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCancelAddExpense() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoAddExpense() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new AddExpenseFragment(), "add-expense-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteExpense(Expense expense) {

    }

    @Override
    public void clearAllExpenses() {

    }

    @Override
    public ArrayList<Expense> getAllExpenses() {
        return this.mExpenses;
    }

    @Override
    public void gotoExpenseSummary(Expense expense) {

    }
}