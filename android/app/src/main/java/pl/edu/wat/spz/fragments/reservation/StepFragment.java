package pl.edu.wat.spz.fragments.reservation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.List;

import pl.edu.wat.spz.dto.MedicalVisitDto;
import pl.edu.wat.spz.R;
import pl.edu.wat.spz.activities.NavigationActivity;
import pl.edu.wat.spz.entity.MedicalVisit;
import pl.edu.wat.spz.http.HttpClient;
import pl.edu.wat.spz.http.VisitService;
import pl.edu.wat.spz.preferences.LoginPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class StepFragment extends Fragment {

    private StepperLayout stepperLayout;
    private CardView successCard;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step, container, false);

        FragmentManager fragmentManager = getFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            for (Fragment f : fragments) {
                if (f instanceof Step) {
                    ft.remove(f);
                }
            }
            ft.commitAllowingStateLoss();
        }

        successCard = view.findViewById(R.id.success_card);

        stepperLayout = view.findViewById(R.id.stepperLayout);
        stepperLayout.setAdapter(new MyStepperAdapter(fragmentManager, getContext()), 0);
        stepperLayout.setListener(new StepperListenerImpl());

        return view;
    }

    public static class MyStepperAdapter extends AbstractFragmentStepAdapter {//zlicza klikiecia dalej i wywoluje/obsluguje odpowiednie fragemtnty

        public MyStepperAdapter(FragmentManager fm, Context context) {
            super(fm, context);
        }

        @Override
        public Step createStep(int position) {
            Step step = null;
            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    step = new SpecializationStepFragment();
                    break;
                case 1:
                    step = new DoctorStepFragment();
                    break;
                case 2:
                    step = new VisitStepFragment();
                    break;
            }

            return step;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @NonNull
        @Override
        public StepViewModel getViewModel(@IntRange(from = 0) int position) {
            //Override this method to set Step title for the Tabs, not necessary for other stepper types
            return new StepViewModel.Builder(context)
                    .setBackButtonLabel("Wstecz")
                    .setNextButtonLabel("Dalej")
                    .create();
        }
    }

    class StepperListenerImpl implements StepperLayout.StepperListener {

        VisitService visitService;


        public StepperListenerImpl() {
            this.visitService = HttpClient.getInstance().getVisitService();
        }

        @Override
        public void onCompleted(View completeButton) {
            String timetableId = viewModel.getTimetableId();
            String specializationId = viewModel.getSpecializationId();

            SharedPreferences prefs = getContext().getSharedPreferences(LoginPreferences.PREFERENCE_NAME, MODE_PRIVATE);
            final String userId = prefs.getString(LoginPreferences.ID_KEY, null);
            final String authorizationHeader = prefs.getString(LoginPreferences.AUTHORIZATION_HEADER_KEY, null);

            MedicalVisitDto medicalVisitDto = new MedicalVisitDto(userId, specializationId, timetableId);

            stepperLayout.setVisibility(View.GONE);


            Call<MedicalVisit> medicalVisitCall = visitService.registerVisit(authorizationHeader, medicalVisitDto);
            toggleLoading(true);
            medicalVisitCall.enqueue(new Callback<MedicalVisit>() {
                @Override
                public void onResponse(Call<MedicalVisit> call, Response<MedicalVisit> response) {
                    MedicalVisit medicalVisit = response.body();
                    successCard.setVisibility(View.VISIBLE);
                    toggleLoading(false);
                }

                @Override
                public void onFailure(Call<MedicalVisit> call, Throwable t) {
                    stepperLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Wystąpił błąd", Toast.LENGTH_SHORT).show();
                    toggleLoading(false);
                }
            });

        }

        @Override
        public void onError(VerificationError verificationError) {

        }

        @Override
        public void onStepSelected(int newStepPosition) {

        }

        @Override
        public void onReturn() {

        }
    }


}
