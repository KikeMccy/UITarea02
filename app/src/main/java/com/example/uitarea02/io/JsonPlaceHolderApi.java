package com.example.uitarea02.io;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface JsonPlaceHolderApi {

    @Headers({"public-merchant-id: bbff765883ae4a80b3bb1b25627abc97"})
    @GET("bankList")
    Call<List<Bancos>> getBancos();

}
