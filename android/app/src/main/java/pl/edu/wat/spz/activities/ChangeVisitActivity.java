package pl.edu.wat.spz.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import lombok.Getter;
import pl.edu.wat.spz.R;
import pl.edu.wat.spz.entity.MedicalVisit;
import pl.edu.wat.spz.fragments.changevisit.ChangeVisitFragment;

public class ChangeVisitActivity extends AppCompatActivity {

    @Getter
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_visit_activity);
        if (savedInstanceState == null) {
            MedicalVisit medicalVisit = (MedicalVisit) getIntent().getSerializableExtra("MEDICAL_VISIT"); //nowe okno szczegolow wizyty
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ChangeVisitFragment.getInstance(medicalVisit))
                    .commitNow();

            progressBar = findViewById(R.id.progressBar);
        }
    }
}
