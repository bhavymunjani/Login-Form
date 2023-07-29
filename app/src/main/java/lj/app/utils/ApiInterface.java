package lj.app.utils;

import lj.app.GetData.GetLoginData;
import lj.app.GetData.GetSignupData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("singup.php")
    Call<GetSignupData> getsignupdata(
            @Field("name") String name,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("gender") String gender,
            @Field("city") String city,
            @Field("password") String password,
            @Field("dob") String dob);

    @FormUrlEncoded
    @POST("login.php")
    Call<GetLoginData> getlogindata(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("update.php")
    Call<GetSignupData> getupdatedata(
            @Field("userid") String id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("gender") String gender,
            @Field("city") String city,
            @Field("password") String password,
            @Field("dob") String dob);
}