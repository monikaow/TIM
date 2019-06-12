package pl.edu.wat.spz.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ProgressBar;

import lombok.Getter;
import pl.edu.wat.spz.R;
import pl.edu.wat.spz.fragments.recipe.RecipeFragment;
import pl.edu.wat.spz.fragments.reservation.StepFragment;
import pl.edu.wat.spz.fragments.startup.StartupFragment;
import pl.edu.wat.spz.fragments.visit.VisitFragment;

public class NavigationActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment startupFragment = new StartupFragment();
    Fragment stepFragment = new StepFragment();

    @Getter
    ProgressBar progressBar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = startupFragment;
                    break;
                case R.id.navigation_my_visits:
                    fragment = new VisitFragment();
                    break;
                case R.id.navigation_my_recipes:
                    fragment = new RecipeFragment();
                    break;
                case R.id.navigation_reservation:
                    fragment = stepFragment;
                    break;
            }
            if (fragment != null) {
                replaceView(fragment);
                return true;
            }

            return false;
        }
    };

    private void replaceView(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        progressBar = findViewById(R.id.progressBar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        replaceView(startupFragment);
    }

}
