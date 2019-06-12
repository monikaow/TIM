package pl.edu.wat.spz.http;

import java.util.List;

import pl.edu.wat.spz.entity.Specialization;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SpecializationService {

    @GET("patient/specializations/fetch")
    Call<List<Specialization>> fetchSpecializations(@Header("Authorization") String authorization);

}
