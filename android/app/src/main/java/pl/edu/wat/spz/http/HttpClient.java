package pl.edu.wat.spz.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    @Getter
    private LoginService loginService;

    @Getter
    private RecipeService recipeService;

    @Getter
    private VisitService visitService;

    @Getter
    private SpecializationService specializationService;

    @Getter
    private DoctorService doctorService;

    @Getter
    private TimetableService timetableService;

    private static HttpClient INSTANCE = null;

    public static synchronized HttpClient getInstance() {
        if (INSTANCE == null)
            INSTANCE = new HttpClient();

        return INSTANCE;
    }

    private HttpClient() {
        final String BASE_URL = "https://spz-app.herokuapp.com/api/";

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.loginService = retrofit.create(LoginService.class);
        this.recipeService = retrofit.create(RecipeService.class);
        this.visitService = retrofit.create(VisitService.class);
        this.specializationService = retrofit.create(SpecializationService.class);
        this.doctorService = retrofit.create(DoctorService.class);
        this.timetableService = retrofit.create(TimetableService.class);
    }

}
