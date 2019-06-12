package pl.edu.wat.spz.http;

import java.util.List;

import pl.edu.wat.spz.entity.DoctorTimetable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TimetableService {

    @GET("doctor/timetable/fetch")
    Call<List<List<DoctorTimetable>>> fetchDoctorTimetable(@Header("Authorization") String authorization, @Query("doctorId") String doctorId, @Query("dates") String dates);

}
