package com.ftn.uns.travelplaner.auth;

import android.content.Context;
import android.content.SharedPreferences;

import com.ftn.uns.travelplaner.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class AuthInterceptor implements Interceptor {
    private Context context;

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.PREFS_NAME), MODE_PRIVATE);
        String token = prefs.getString("token", "TOKEN_DEFAULT_VALUE");
        System.out.println("\n\nFrom AuthInterceptor:");
        System.out.println(token);

        // Remove it so we don’t send it over the network.
        request = request.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = chain.proceed(request); //perform request, here original request will be executed

        return response;
    }
}
