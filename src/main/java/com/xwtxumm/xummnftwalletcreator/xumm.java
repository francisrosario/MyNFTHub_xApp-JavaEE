package com.xwtxumm.xummnftwalletcreator;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;


public class xumm implements Facade{
    private String uuid;
    private String deviceType;
    private String loginURL_Redirect;
    private String signInURL_Device;

    private String payload_Data;
    private String payload_uuid_Data;
    private String issued_user_token;

    private OkHttpClient okHttpClient;
    private JSONObject jsonObject;

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getLoginURL_Redirect() {
        return loginURL_Redirect;
    }

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

    private String postPayload(String payload) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, payload);
        Request request = new Request.Builder()
                .url(Facade.payload_post)
                .addHeader("X-API-Key", System.getenv("apiKey"))
                .addHeader("X-API-Secret", System.getenv("xApi"))
                .post(body)
                .build();
        Response response = okHttpClient().newCall(request).execute();
        return response.body().string();
    }

    public String getPayload_uuid(String payload_uuid) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(Facade.payload_uuid+payload_uuid)
                .addHeader("X-API-Key", System.getenv("apiKey"))
                .addHeader("X-API-Secret", System.getenv("xApi"))
                .build();
        Response response = okHttpClient().newCall(request).execute();
        return response.body().string();
    }

    public String getLoginURL(String... json){
        jsonObject(json[0]);
        return jsonObject.getJSONObject("next").get("always").toString();
    }

    public String getUUID(String... json){
        jsonObject(json[0]);
        return jsonObject.get("uuid").toString();
    }

    public void createSignIn_URL(){
        if(deviceType.equals("Smartphone")){
            signInURL_Device = Facade.signIn_Phone;
        }else if(deviceType.equals("Personal computer")){
            signInURL_Device = Facade.signIn_Desktop;
        }else{

        }
    }
    public String getUserToken(String... json){
        jsonObject(json[0]);
        return jsonObject.getJSONObject("application").get("issued_user_token").toString();
    }

    @Override
    public void processAuthorization() {
        try {
            //Detect if Desktop / Mobile.
            createSignIn_URL();
            //Get JSON of postRequest
            payload_Data = postPayload(signInURL_Device);
            //Get login URL from resonse JSON
            loginURL_Redirect = getLoginURL(payload_Data);
            //Get UUID from postRequest response.
            uuid = getUUID(payload_Data);
            //System.out.println("UUID FROM payload_data: " + uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkAuthorization() {
        try {
            payload_uuid_Data = getPayload_uuid(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
            //System.out.println("Payload uuid data: " + payload_uuid_Data);
            issued_user_token = getUserToken(payload_uuid_Data);
            //System.out.println("Issued User Token: " + issued_user_token);
        }
}
