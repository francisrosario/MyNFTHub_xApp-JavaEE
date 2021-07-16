package com.xwtxumm.xummnftwalletcreator;

public interface Facade {
    //////////////
    //Endpoints
    String URLpostPayload = "https://xumm.app/api/v1/platform/payload";
    StringBuilder payload_uuid = new StringBuilder("https://xumm.app/api/v1/platform/payload/");

    //////////////
    //JSON String format
    String signIn = "{\"txjson\":{\"TransactionType\":\"SignIn\"}}";

    String signIn(String... payload);
    String payloadStatus(String... uuid);
}
