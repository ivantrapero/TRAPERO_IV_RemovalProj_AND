package com.trapero.removalproject.viewmodel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.trapero.removalproject.network.ApiInterface;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://127.0.0.1:8000/index";

    // Using volatile to ensure thread safety
    private static volatile ApiInterface instance;

    private RetrofitClient() {
        // Private constructor to prevent instantiation
    }

    public static ApiInterface getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    // Initialize Retrofit instance if it's not already created
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)  // Base URL for your API
                            .addConverterFactory(GsonConverterFactory.create())  // Gson converter for JSON parsing
                            .build();

                    // Create the instance of ApiInterface
                    instance = retrofit.create(ApiInterface.class);
                }
            }
        }
        return instance;  // Return the instance of the API interface
    }
}
