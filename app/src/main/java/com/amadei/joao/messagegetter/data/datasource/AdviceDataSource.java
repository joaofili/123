package com.amadei.joao.messagegetter.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.amadei.joao.messagegetter.data.Result;
import com.amadei.joao.messagegetter.data.datasource.model.AdviceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdviceDataSource {
    private AdviceModel currentAdvice;

    private Result result;

    public Result<AdviceModel> getAdvice() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.flutter-community.de/api/v1/advice")
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    result = new Result.Error(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.isSuccessful() && response.code() == 200){
                        String body = response.body().string();

                        try {
                            JSONObject object = new JSONObject(body);
                            currentAdvice = new AdviceModel(object);

                            result = new Result.Success<>(currentAdvice);
                        } catch (JSONException e) {
                        Log.i("Error advice => ", e.getMessage());
                    }
                    }
                }
            });

            while(result == null){}

            return result;
        } catch(Exception e){
            return new Result.Error(new IOException("Error on get advice"));
        }
    }
}
