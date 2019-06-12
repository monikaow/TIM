package pl.edu.wat.spz.http;

import pl.edu.wat.spz.auth.UserDetails;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

    @POST("auth")
    Call<UserDetails> login(@Query("username") String username, @Query("password") String password);
}