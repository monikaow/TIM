package pl.edu.wat.spz.fragments.visit;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.wat.spz.entity.MedicalVisit;
import pl.edu.wat.spz.entity.Page;
import pl.edu.wat.spz.fragments.PageFragment;
import pl.edu.wat.spz.http.HttpClient;
import pl.edu.wat.spz.http.VisitService;
import pl.edu.wat.spz.preferences.LoginPreferences;
import retrofit2.Call;

import static android.content.Context.MODE_PRIVATE;

public class VisitFragment extends PageFragment<MedicalVisit> { //scrollowanie w dol

    private VisitService visitService;

    public VisitFragment() {
        this.visitService = HttpClient.getInstance().getVisitService();
    }

    @Override
    protected Call<Page<MedicalVisit>> makePageCall(int page, int size) {
        SharedPreferences prefs = getFragmentContext().getSharedPreferences(LoginPreferences.PREFERENCE_NAME, MODE_PRIVATE);
        final String userId = prefs.getString(LoginPreferences.ID_KEY, null);
        final String authorizationHeader = prefs.getString(LoginPreferences.AUTHORIZATION_HEADER_KEY, null);
        return visitService.fetchVisits(authorizationHeader, userId, String.valueOf(page), String.valueOf(size), "doctorTimetable.date,desc"); //kolejna strona
    }

    @Override
    protected RecyclerView.Adapter generateAdapter() {
        return new VisitRecyclerViewAdapter(getList(), getFragmentContext());
    }
}
