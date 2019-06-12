package pl.edu.wat.spz.http;

import java.util.List;

import pl.edu.wat.spz.entity.DoctorSpecialization;
import pl.edu.wat.spz.entity.Specialization;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface DoctorService {

    @GET("doctor/specialization")
    Call<List<DoctorSpecialization>> fetchDoctorSpecialization(@Header("Authorization") String authorization, @Query("specializationId") String specializationId);

}
