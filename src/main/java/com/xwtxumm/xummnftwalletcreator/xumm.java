package com.xwtxumm.xummnftwalletcreator;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;


public class xumm implements Facade{
    private String issued_user_token;
    private OkHttpClient okHttpClient;
    private JSONObject jsonObject;

    private void jsonObject(String... json){
        this.jsonObject = new JSONObject(json[0]);
    }

    private OkHttpClient okHttpClient(){
        if(this.okHttpClient == null){
            return this.okHttpClient = new OkHttpClient().newBuilder()
                    .build();
        }
        return this.okHttpClient;
    }

    private String postRequest(String... payload) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, payload[0]);
        Request request = new Request.Builder()
                .url(Facade.URLpostPayload)
                .addHeader("X-API-Key", System.getenv("apiKey"))
                .addHeader("X-API-Secret", System.getenv("xApi"))
                .post(body)
                .build();
        Response response = okHttpClient().newCall(request).execute();
        return response.body().string();
    }

    public String getPayload_uuid(String... payload_uuid) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, payload_uuid[0]);
        Request request = new Request.Builder()
                .url(Facade.payload_uuid.append("test").toString())
                .addHeader("X-API-Key", System.getenv("apiKey"))
                .addHeader("X-API-Secret", System.getenv("xApi"))
                .post(body)
                .build();
        Response response = okHttpClient().newCall(request).execute();
        return response.body().string();
    }

    public Object getLoginURL(String... json){
        jsonObject(json[0]);
        return jsonObject.getJSONObject("next").get("always");
    }

    public Object getUserToken(String... json){
        JSONObject jsonObject = new JSONObject(json[0].toString());
        return jsonObject.getJSONObject("next").get("always");
    }

    @Override
    public String signIn(String... payload) {
        String response = null;
        try {
            response = postRequest(payload[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String payloadStatus(String... uuid) {
        return null;
    }
}
