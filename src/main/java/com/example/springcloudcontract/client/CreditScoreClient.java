package com.example.springcloudcontract.client;

import com.example.springcloudcontract.config.RetrofitClient;
import com.example.springcloudcontract.resource.CreditCheckRequest;
import com.example.springcloudcontract.resource.CreditCheckResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

@RetrofitClient(value = "credit-score", url = "")
public interface CreditScoreClient {

    @POST("/credit-scores")
    public Call<CreditCheckResponse> creditScore(@Body CreditCheckRequest creditCheckRequest);

}
