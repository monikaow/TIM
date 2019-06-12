package pl.edu.wat.spz.http;

import pl.edu.wat.spz.dto.MedicalVisitDto;
import pl.edu.wat.spz.entity.MedicalVisit;
import pl.edu.wat.spz.entity.Page;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VisitService {

    @GET("patient/visits/fetch")
    Call<Page<MedicalVisit>> fetchVisits(
            @Header("Authorization") String authorization,
            @Query("patientId") String patientId,
            @Query("page") String page,
            @Query("size") String size,
            @Query("sort") String sort
    );

    @POST("patient/register/visit")
    Call<MedicalVisit> registerVisit(
            @Header("Authorization") String authorization,
            @Body MedicalVisitDto medicalVisit
    );

    @POST("patient/visit/update")
    Call<MedicalVisit> updateVisit(
            @Header("Authorization") String authorization,
            @Query("medicalVisitId") String medicalVisitId,
            @Query("timetableId") String timetableId
    );

    @POST("patient/visit/cancel")
    Call<MedicalVisit> cancelVisit(
            @Header("Authorization") String authorization,
            @Query("medicalVisitId") String medicalVisitId
    );
}
