package pl.edu.wat.spz.fragments.reservation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.edu.wat.spz.R;
import pl.edu.wat.spz.activities.NavigationActivity;
import pl.edu.wat.spz.entity.Doctor;
import pl.edu.wat.spz.entity.DoctorSpecialization;
import pl.edu.wat.spz.entity.Specialization;
import pl.edu.wat.spz.http.DoctorService;
import pl.edu.wat.spz.http.HttpClient;
import pl.edu.wat.spz.preferences.LoginPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class DoctorStepFragment extends Fragment implements BlockingStep {

    private DoctorService doctorService;

    RadioGroup radioGroup;

    private ReservationViewModel viewModel;

    private Context fragmentContext;

    private void toggleLoading(boolean loading) {
        if (fragmentContext instanceof NavigationActivity) {
            NavigationActivity activity = (NavigationActivity) fragmentContext;
            activity.getProgressBar().setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ReservationViewModel.class);
        fragmentContext = getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_doctor, container, false);
        doctorService = HttpClient.getInstance().getDoctorService();
        radioGroup = view.findViewById(R.id.radio_group);
        return view;
    }

    @Override
    public VerificationError verifyStep() {

        if (viewModel.getDoctor() != null) {
            return null;
        }

        return new VerificationError("Wybierz lekarza");
    }

    @Override
    public void onSelected() {
        radioGroup.clearCheck();

        SharedPreferences prefs = getContext().getSharedPreferences(LoginPreferences.PREFERENCE_NAME, MODE_PRIVATE);
        final String authorizationHeader = prefs.getString(LoginPreferences.AUTHORIZATION_HEADER_KEY, null);

        Call<List<DoctorSpecialization>> listCall = doctorService.fetchDoctorSpecialization(authorizationHeader, viewModel.getSpecializationId());
        viewModel.setDoctor(null);
        toggleLoading(true);
        listCall.enqueue(new Callback<List<DoctorSpecialization>>() {
            @Override
            public void onResponse(Call<List<DoctorSpecialization>> call, Response<List<DoctorSpecialization>> response) {

                final List<DoctorSpecialization> doctorSpecializations = response.body();

                radioGroup.removeAllViews();

                for (int i = 0; i < doctorSpecializations.size(); i++) {
                    final RadioButton radioButton = new RadioButton(getContext());
                    radioButton.setId(i);
                    radioButton.setText(doctorSpecializations.get(i).getDoctor().getFullTitle());
                    radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT));

                    radioButton.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           viewModel.setDoctor(doctorSpecializations.get(radioButton.getId()).getDoctor());
                                                       }
                                                   }
                    );

                    radioGroup.addView(radioButton);
                }
                toggleLoading(false);
            }

            @Override
            public void onFailure(Call<List<DoctorSpecialization>> call, Throwable t) {
                toggleLoading(false);
            }
        });
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.makeText(getContext(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        callback.goToNextStep();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }
}
