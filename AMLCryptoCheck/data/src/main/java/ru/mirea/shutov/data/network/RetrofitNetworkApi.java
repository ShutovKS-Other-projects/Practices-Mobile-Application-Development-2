package ru.mirea.shutov.data.network;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.shutov.data.network.dto.WalletCheckDto;
import ru.mirea.shutov.domain.models.WalletCheck;
import ru.mirea.shutov.domain.network.NetworkApi;

public class RetrofitNetworkApi implements NetworkApi {

    private static final String BASE_URL = "https://68fa9255ef8b2e621e805a85.mockapi.io/api/v1/";
    private final AmlApiService apiService;

    public RetrofitNetworkApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.apiService = retrofit.create(AmlApiService.class);
    }

    @Override
    public WalletCheck checkAddress(String address) {
        WalletCheckDto requestDto = new WalletCheckDto(address);

        Call<WalletCheckDto> call = apiService.checkWallet(requestDto);

        try {
            Response<WalletCheckDto> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return mapDtoToDomain(response.body());
            } else {
                System.err.println("Network error: " + response.code());
                return createErrorWalletCheck(address);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return createErrorWalletCheck(address);
        }
    }

    @Override
    public List<WalletCheck> getHistory() {
        Call<List<WalletCheckDto>> call = apiService.getHistory();
        try {
            Response<List<WalletCheckDto>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().stream()
                        .map(this::mapDtoToDomain)
                        .collect(Collectors.toList());
            } else {
                System.err.println("Network error: " + response.code());
                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private WalletCheck mapDtoToDomain(WalletCheckDto dto) {
        return new WalletCheck(
                dto.getAddress(),
                dto.getRiskScore(),
                dto.getCheckDate(),
                dto.getCurrencyIconUrl()
        );
    }

    private WalletCheck createErrorWalletCheck(String address) {
        return new WalletCheck(address, -1, System.currentTimeMillis(), null);
    }
}