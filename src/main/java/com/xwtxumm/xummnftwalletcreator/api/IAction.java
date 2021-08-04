package com.xwtxumm.xummnftwalletcreator.api;

import java.io.IOException;

public interface IAction {
    void processAuthentication();
    void checkAuthentication();
    void createNFTWallet(byte[] imageByte, String nftName, String nftAuthor, String nftEmail, String nftTwitter, String nftDescription) throws IOException;
}
