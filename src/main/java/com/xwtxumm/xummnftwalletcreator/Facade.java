package com.xwtxumm.xummnftwalletcreator;

public interface Facade {
    //////////////
    //Endpoints
    String payload_post = "https://xumm.app/api/v1/platform/payload";
    String payload_uuid = "https://xumm.app/api/v1/platform/payload/";

    //////////////
    //JSON String format
    String signIn_Desktop = "{\"options\":{\"submit\":\"true\",\"multisign\":\"false\",\"expire\":\"1440\",\"return_url\":{\"web\":\"http://localhost:8080/XUMM_NFTWalletCreator_war_exploded/\"}},\"txjson\":{\"TransactionType\":\"SignIn\"}}";
    String signIn_Phone = "{\"options\":{\"submit\":\"true\",\"multisign\":\"false\",\"expire\":\"1440\",\"return_url\":{\"app\":\"http://localhost:8080/XUMM_NFTWalletCreator_war_exploded/\"}},\"txjson\":{\"TransactionType\":\"SignIn\"}}";

    void processAuthorization();
    void checkAuthorization();
}
