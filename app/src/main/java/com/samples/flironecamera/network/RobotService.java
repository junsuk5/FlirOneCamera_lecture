package com.samples.flironecamera.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RobotService {
    String BASE_URL = "http://192.168.0.13:8000";

    @GET("robot")
    Call<ResponseBody> sendData(
            @Query("face_id") int faceId,
            @Query("temp") double temp
    );
}
