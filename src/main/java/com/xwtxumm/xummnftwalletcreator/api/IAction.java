package com.xwtxumm.xummnftwalletcreator.api;

import java.io.IOException;

public interface IAction {
    //////////////
    //Endpoints
    String payload_post = "https://xumm.app/api/v1/platform/payload";
    String payload_uuid = "https://xumm.app/api/v1/platform/payload/";

    //////////////
    //JSON String format
    String signIn_Desktop = "{\"options\":{\"submit\":\"true\",\"multisign\":\"false\",\"expire\":\"1440\",\"return_url\":{\"web\":\"https://xls19d-xumm-dev.herokuapp.com/\"}},\"txjson\":{\"TransactionType\":\"SignIn\"}}";
    String signIn_Phone = "{\"options\":{\"submit\":\"true\",\"multisign\":\"false\",\"expire\":\"1440\",\"return_url\":{\"app\":\"https://xls19d-xumm-dev.herokuapp.com/\"}},\"txjson\":{\"TransactionType\":\"SignIn\"}}";

    void processAuthentication();
    void checkAuthentication() throws IOException;
    void createNFTWallet(byte[] imageByte, String nftName, String nftAuthor, String nftEmail, String nftTwitter, String nftDescription) throws IOException;
}
