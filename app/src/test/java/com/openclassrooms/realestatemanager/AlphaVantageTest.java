package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.models.remote.AlphaVantageResponse;
import com.openclassrooms.realestatemanager.utils.api.AlphaVantageService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;

public class AlphaVantageTest {

    private MockWebServer mockWebServer;

    @Before
    public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @After
    public void shutDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void AlphaVantageTest() throws IOException{

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mockWebServer.url("").toString())
                .build();

        mockWebServer.enqueue(new MockResponse().setBody(loadJSONFromResources("cer.json")));

        final String[] exchangeRate = new String[1];
        final String[] lastTimeRefreshed = new String[1];
        final String[] timeZone = new String[1];

        AlphaVantageService service = retrofit.create(AlphaVantageService.class);

        CountDownLatch latch = new CountDownLatch(1);
        service.getExchangeCurrencyRate("USD", "JPY").enqueue(new Callback<AlphaVantageResponse>() {
            @Override
            public void onResponse(Call<AlphaVantageResponse> call, Response<AlphaVantageResponse> response) {
                exchangeRate[0] = response.body().getCurrencyExchangeRate().getExchangeRate();
                lastTimeRefreshed[0] = response.body().getCurrencyExchangeRate().getLastRefreshed();
                timeZone[0] = response.body().getCurrencyExchangeRate().getTimeZone();
                latch.countDown();
            }

            @Override
            public void onFailure(Call<AlphaVantageResponse> call, Throwable t) {
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("112.96800000", exchangeRate[0]);
        assertEquals("2018-11-21 14:46:43", lastTimeRefreshed[0]);
        assertEquals("UTC", timeZone[0]);
    }

    public String loadJSONFromResources(String fileName) {
        String json = null;
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}