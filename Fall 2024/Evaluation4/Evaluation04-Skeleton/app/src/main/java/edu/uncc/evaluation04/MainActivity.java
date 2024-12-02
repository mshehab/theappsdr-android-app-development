package edu.uncc.evaluation04;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import edu.uncc.evaluation04.fragments.AddGradeFragment;
import edu.uncc.evaluation04.fragments.MyGradesFragment;
import edu.uncc.evaluation04.fragments.PinCodeCheckFragment;
import edu.uncc.evaluation04.fragments.PinCodeSetupFragment;
import edu.uncc.evaluation04.fragments.PinCodeUpdateFragment;
import edu.uncc.evaluation04.fragments.SelectCourseFragment;
import edu.uncc.evaluation04.fragments.SelectLetterGradeFragment;
import edu.uncc.evaluation04.fragments.SelectSemesterFragment;
import edu.uncc.evaluation04.models.Course;
import edu.uncc.evaluation04.models.Grade;
import edu.uncc.evaluation04.models.LetterGrade;
import edu.uncc.evaluation04.models.Semester;

public class MainActivity extends AppCompatActivity implements SelectSemesterFragment.SelectSemesterListener, SelectLetterGradeFragment.SelectLetterGradeListener,
        SelectCourseFragment.SelectCourseListener, AddGradeFragment.AddGradeListener, MyGradesFragment.MyGradesListener,
        PinCodeCheckFragment.PinCodeCheckListener, PinCodeUpdateFragment.PinCodeUpdateListener, PinCodeSetupFragment.PinCodeSetupListener {

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

        //need to do the check related to the pin code

    }

    @Override
    public boolean checkPinCode(String pinCode) {

        return false;
    }

    @Override
    public void onPinCodeCheckSuccessful() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new MyGradesFragment())
                .commit();
    }

    @Override
    public void onPinCodeSetup(String pinCode) {

    }

    @Override
    public void onPinCodeUpdate(String pinCode) {

    }

    @Override
    public void onPinCodeUpdateCancel() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new MyGradesFragment())
                .commit();
    }

    @Override
    public void onLetterGradeSelected(LetterGrade letterGrade) {
        AddGradeFragment addGradeFragment = (AddGradeFragment) getSupportFragmentManager().findFragmentByTag("add-grade-fragment");
        if (addGradeFragment != null) {
            addGradeFragment.setSelectedLetterGrade(letterGrade);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSemesterSelected(Semester semester) {
        AddGradeFragment addGradeFragment = (AddGradeFragment) getSupportFragmentManager().findFragmentByTag("add-grade-fragment");
        if (addGradeFragment != null) {
            addGradeFragment.setSelectedSemester(semester);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCourseSelected(Course course) {
        AddGradeFragment addGradeFragment = (AddGradeFragment) getSupportFragmentManager().findFragmentByTag("add-grade-fragment");
        if (addGradeFragment != null) {
            addGradeFragment.setSelectedCourse(course);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancelSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onAddGrade(Grade grade) {
        //store to database
        //pop the back stack.
    }

    @Override
    public void onCancelAddGrade() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectLetterGrade() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectLetterGradeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectCourse() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectCourseFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectSemester() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectSemesterFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoAddGrade() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new AddGradeFragment(), "add-grade-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoUpdatePin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new PinCodeUpdateFragment())
                .commit();
    }

    @Override
    public ArrayList<Grade> getAllGrades() {
        return null;
    }

    @Override
    public void deleteGrade(Grade grade) {

    }

}