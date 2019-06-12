package pl.edu.wat.spz.fragments.changevisit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.edu.wat.spz.R;
import pl.edu.wat.spz.activities.ChangeVisitActivity;
import pl.edu.wat.spz.components.DataTextView;
import pl.edu.wat.spz.entity.DoctorTimetable;
import pl.edu.wat.spz.entity.MedicalVisit;
import pl.edu.wat.spz.http.HttpClient;
import pl.edu.wat.spz.http.TimetableService;
import pl.edu.wat.spz.http.VisitService;
import pl.edu.wat.spz.preferences.LoginPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ChangeVisitFragment extends Fragment {

    TextView firstDayTitle, secondDayTitle, thirdDayTitle;
    LinearLayout firstDayTimetable, secondDayTimetable, thirdDayTimetable;

    List<TextView> textViews;
    Button saveChange;

    Date date;

    String[] shortMonths = new DateFormatSymbols().getShortMonths();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    TimetableService timetableService = HttpClient.getInstance().getTimetableService();
    String timetableId;
    private MedicalVisit medicalVisit;

    VisitService visitService;

    private Context fragmentContext;

    public static ChangeVisitFragment getInstance(MedicalVisit medicalVisit) {
        ChangeVisitFragment changeVisitFragment = new ChangeVisitFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("MEDICAL_VISIT", medicalVisit);
        changeVisitFragment.setArguments(bundle);

        return changeVisitFragment;
    }

    private String titleName(Calendar calendar) {
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);

        return String.format(Locale.getDefault(), "%d %s", day, shortMonths[month]);
    }

    private String[][] prepareDates(Calendar calendar) {

        String[][] dates = new String[3][2];

        for (int i = 0; i < 3; i++) {
            dates[i][0] = dateFormat.format(calendar.getTime());
            dates[i][1] = titleName(calendar);
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }

    private String timeLabel(String date) {
        try {
            Date parse = dateTimeFormat.parse(date);
            return timeFormat.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void prepareTitleNames() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String[][] dates = prepareDates(calendar);

        firstDayTitle.setText(dates[0][1]);
        secondDayTitle.setText(dates[1][1]);
        thirdDayTitle.setText(dates[2][1]);

        firstDayTimetable.removeAllViews();
        secondDayTimetable.removeAllViews();
        thirdDayTimetable.removeAllViews();

        fetchTimetables(dates);
    }

    private void fetchTimetables(String[][] datesTable) {
        String[] dates = new String[3];

        for (int i = 0; i < 3; i++) {
            dates[i] = datesTable[i][0];
        }

        SharedPreferences prefs = getContext().getSharedPreferences(LoginPreferences.PREFERENCE_NAME, MODE_PRIVATE);
        final String authorizationHeader = prefs.getString(LoginPreferences.AUTHORIZATION_HEADER_KEY, null);

        Call<List<List<DoctorTimetable>>> listCall = timetableService.fetchDoctorTimetable(authorizationHeader, medicalVisit.getDoctorSpecialization().getDoctor().getId(), TextUtils.join(",", dates));
        listCall.enqueue(new Callback<List<List<DoctorTimetable>>>() {
            @Override
            public void onResponse(Call<List<List<DoctorTimetable>>> call, Response<List<List<DoctorTimetable>>> response) {

                List<List<DoctorTimetable>> body = response.body();

                List<DoctorTimetable> first = body.get(0);
                List<DoctorTimetable> second = body.get(1);
                List<DoctorTimetable> third = body.get(2);

                for (DoctorTimetable s : first) {
                    DataTextView<DoctorTimetable> textView = new DataTextView<>(getContext(), s);
                    textView.setText(timeLabel(s.getDate()));
                    textViews.add(textView);
                    firstDayTimetable.addView(textView);
                }

                for (DoctorTimetable s : second) {
                    DataTextView<DoctorTimetable> textView = new DataTextView<>(getContext(), s);
                    textView.setText(timeLabel(s.getDate()));
                    textViews.add(textView);
                    secondDayTimetable.addView(textView);
                }

                for (DoctorTimetable s : third) {
                    DataTextView<DoctorTimetable> textView = new DataTextView<>(getContext(), s);
                    textView.setText(timeLabel(s.getDate()));
                    textViews.add(textView);
                    thirdDayTimetable.addView(textView);
                }

                for (TextView textView : textViews) {
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    llp.setMargins(0, 2, 2, 0);
                    textView.setLayoutParams(llp);
                    addClickEvent(textView);
                }
            }

            @Override
            public void onFailure(Call<List<List<DoctorTimetable>>> call, Throwable t) {

            }
        });
    }

    private void toggleLoading(boolean loading) {
        if (fragmentContext instanceof ChangeVisitActivity) {
            ChangeVisitActivity activity = (ChangeVisitActivity) fragmentContext;
            activity.getProgressBar().setVisibility(loading ? View.VISIBLE : View.GONE);
        }

        saveChange.setEnabled(!loading);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        medicalVisit = (MedicalVisit) getArguments().getSerializable("MEDICAL_VISIT");

        View view = inflater.inflate(R.layout.fragment_changevisit, container, false);

        date = new Date();

        firstDayTitle = view.findViewById(R.id.first_day_title);
        secondDayTitle = view.findViewById(R.id.second_day_title);
        thirdDayTitle = view.findViewById(R.id.third_day_title);

        firstDayTimetable = view.findViewById(R.id.first_day_timetable);
        secondDayTimetable = view.findViewById(R.id.second_day_timetable);
        thirdDayTimetable = view.findViewById(R.id.third_day_timetable);

        Button dayBack = view.findViewById(R.id.day_back);
        Button dayNext = view.findViewById(R.id.day_next);

        dayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timetableId = null;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, -3);
                date = calendar.getTime();
                prepareTitleNames();
            }
        });

        dayNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timetableId = null;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, 3);
                date = calendar.getTime();
                prepareTitleNames();
            }
        });

        textViews = new ArrayList<>();

        timetableId = null;
        ((TextView) view.findViewById(R.id.dr_full_name)).setText(medicalVisit.getDoctorSpecialization().getDoctor().getFullTitle());
        prepareTitleNames();

        visitService = HttpClient.getInstance().getVisitService();

        saveChange = view.findViewById(R.id.saveChange);
        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timetableId == null) {
                    Toast.makeText(getContext(), "Wybierz termin", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences prefs = getContext().getSharedPreferences(LoginPreferences.PREFERENCE_NAME, MODE_PRIVATE);
                final String authorizationHeader = prefs.getString(LoginPreferences.AUTHORIZATION_HEADER_KEY, null);

                Call<MedicalVisit> medicalVisitCall = visitService.updateVisit(authorizationHeader, medicalVisit.getId(), timetableId);
                toggleLoading(true);
                medicalVisitCall.enqueue(new Callback<MedicalVisit>() {
                    @Override
                    public void onResponse(Call<MedicalVisit> call, Response<MedicalVisit> response) {
                        MedicalVisit medicalVisit = response.body();
                        toggleLoading(false);
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure(Call<MedicalVisit> call, Throwable t) {
                        Toast.makeText(getActivity(), "Wystąpił błąd", Toast.LENGTH_SHORT).show();
                        toggleLoading(false);
                    }
                });

            }
        });

        return view;
    }

    private void addClickEvent(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataTextView<DoctorTimetable> dayTextView = (DataTextView<DoctorTimetable>) v;
                timetableId = dayTextView.getItem().getId();
                for (TextView view : textViews) {
                    view.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
                }
                dayTextView.setBackground(getResources().getDrawable(R.drawable.rounded_corner_active));
            }
        });
    }
}
