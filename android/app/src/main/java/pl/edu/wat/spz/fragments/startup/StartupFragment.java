package pl.edu.wat.spz.fragments.startup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pl.edu.wat.spz.R;
import pl.edu.wat.spz.activities.LoginActivity;
import pl.edu.wat.spz.activities.NavigationActivity;
import pl.edu.wat.spz.preferences.LoginPreferences;

import static android.content.Context.MODE_PRIVATE;

public class StartupFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_startup, container, false);
        TextView textView = view.findViewById(R.id.username);

        Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(LoginPreferences.PREFERENCE_NAME, MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();

                Activity activity = getActivity();
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                activity.finish();

            }
        });

        SharedPreferences prefs = getContext().getSharedPreferences(LoginPreferences.PREFERENCE_NAME, MODE_PRIVATE);
        textView.setText(prefs.getString(LoginPreferences.USERNAME_KEY, ""));

        return view;
    }




}
