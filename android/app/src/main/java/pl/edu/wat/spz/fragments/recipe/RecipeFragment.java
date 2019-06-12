package pl.edu.wat.spz.fragments.recipe;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.wat.spz.entity.Page;
import pl.edu.wat.spz.entity.RecipeHistory;
import pl.edu.wat.spz.fragments.PageFragment;
import pl.edu.wat.spz.http.HttpClient;
import pl.edu.wat.spz.http.RecipeService;
import pl.edu.wat.spz.preferences.LoginPreferences;
import retrofit2.Call;

import static android.content.Context.MODE_PRIVATE;

public class RecipeFragment extends PageFragment<RecipeHistory> {

    private RecipeService recipeService;

    public RecipeFragment() {
        this.recipeService = HttpClient.getInstance().getRecipeService();
    }

    @Override
    protected Call<Page<RecipeHistory>> makePageCall(int page, int size) {
        SharedPreferences prefs = getFragmentContext().getSharedPreferences(LoginPreferences.PREFERENCE_NAME, MODE_PRIVATE);
        final String userId = prefs.getString(LoginPreferences.ID_KEY, null);
        final String authorizationHeader = prefs.getString(LoginPreferences.AUTHORIZATION_HEADER_KEY, null);
        return recipeService.fetchRecipeHistory(authorizationHeader, userId, String.valueOf(page), String.valueOf(size), "receiptDate,desc");
    }

    @Override
    protected RecyclerView.Adapter generateAdapter() {
        return new RecipeRecyclerViewAdapter(getList(), getFragmentContext());
    }
}
