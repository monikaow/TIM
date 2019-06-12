package pl.edu.wat.spz.http;

import pl.edu.wat.spz.entity.Page;
import pl.edu.wat.spz.entity.RecipeHistory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RecipeService {

    @GET("patient/recipe-history/fetch")
    Call<Page<RecipeHistory>> fetchRecipeHistory(
            @Header("Authorization") String authorization,
            @Query("patientId") String patientId,
            @Query("page") String page,
            @Query("size") String size,
            @Query("sort") String sort
    );
}
