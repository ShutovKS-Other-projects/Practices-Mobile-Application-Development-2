package ru.mirea.shutov.data.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.mirea.shutov.data.network.dto.WalletCheckDto;

public interface AmlApiService {

    @GET("wallet_history")
    Call<List<WalletCheckDto>> getHistory();

    @POST("wallet_history")
    Call<WalletCheckDto> checkWallet(@Body WalletCheckDto checkRequest);
}