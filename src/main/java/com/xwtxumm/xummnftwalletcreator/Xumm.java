package com.xwtxumm.xummnftwalletcreator;

import com.xwtxumm.xummnftwalletcreator.api.Facade;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import okhttp3.*;
import org.json.JSONObject;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Optional;


public class Xumm implements Facade {
    private String uuid;
    private String deviceType;
    private String loginURL_Redirect;
    private String signInURL_Device;

    private String payloadDomain;
    private String domainValue = "";
    private String nftName;
    private String nftAuthor;
    private String nftEmail;
    private String nftTwitter;
    private String nftDescription;

    private String payload_Data;
    private String payload_uuid_Data;
    private String issued_user_token;

    private OkHttpClient okHttpClient;
    private JSONObject jsonObject;


    public String getPayloadDomain() {
        return payloadDomain;
    }

    public void setPayloadDomain(String payloadDomain) {
        this.payloadDomain = payloadDomain;
    }

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

    private String getPayload_UUID(String payload_uuid) throws IOException {
        Request request = new Request.Builder()
                .url(Facade.payload_uuid+payload_uuid)
                .addHeader("X-API-Key", System.getenv("apiKey"))
                .addHeader("X-API-Secret", System.getenv("xApi"))
                .build();
        Response response = okHttpClient().newCall(request).execute();
        return response.body().string();
    }

    private String getLogin_URL(String json){
        jsonObject(json);
        return jsonObject.getJSONObject("next").get("always").toString();
    }

    private String getUUID(String json){
        jsonObject(json);
        return jsonObject.get("uuid").toString();
    }

    private void createSignIn_URL(){
        if(deviceType.equals("Smartphone")){
            signInURL_Device = Facade.signIn_Phone;
        }else if(deviceType.equals("Personal computer")){
            signInURL_Device = Facade.signIn_Desktop;
        }else{

        }
    }
    private String getUserToken(String json){
        jsonObject(json);
        return jsonObject.getJSONObject("application").get("issued_user_token").toString();
    }

    //////////////////////
    //NFT Creator

    private Multihash createIPFS(byte[] dataByte) throws IOException {
        MerkleNode addResult = null;
        IPFS ipfs = new IPFS(System.getenv("IPFS_Multiaddress"));
        ipfs.timeout(1);
        if (dataByte != null) {
            NamedStreamable.ByteArrayWrapper byteArrayWrapper = new NamedStreamable.ByteArrayWrapper(" ", dataByte);
            addResult = ipfs.add(byteArrayWrapper).get(0);
        }
        return addResult.hash;
    }

    private String createDomainValue(int modeValue, Optional<String> protocolValue, Optional<String> pointerValue, String... groupresourceValue){
        StringBuilder sb = new StringBuilder();

        if(modeValue == 1 || domainValue.equals("")){
            if(domainValue.equals("") && groupresourceValue.length == 0){
                sb.append("@xnft:\n");
            }else if(domainValue.equals("") && groupresourceValue.length == 1 || !domainValue.equals("") && groupresourceValue.length == 1){
                sb.append("@").append(groupresourceValue[0]).append(":\n");
            }
        }

        if(modeValue == 2){
            sb.append(protocolValue.get()).append(":").append(pointerValue.get()).append("\n");
        }else if(modeValue == 10){
            sb.setLength(0);
            return domainValue = sb.toString();
        }
        return domainValue += sb.toString();
    }

    public String sanitizeHTMLInput(String string){
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
        return policy.sanitize(string);
    }

    private String createPayload(String json){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"options\": {");
        sb.append("\"submit\": \"true\",");
        sb.append("\"multisign\": \"false\",");
        sb.append("\"expire\": \"1440\"");
        sb.append("},");
        sb.append("     \"custom_meta\": {\n" +
                "          \"instruction\": \"By approving this transaction, Your wallet will be indexed to NFT Wallet Indexer... The Domain changes is provided below including it's base58 hash.\"\n" +
                "     },");
        sb.append("\"txjson\":");
        sb.append(json);
        sb.append("\"user_token\": \"");
        sb.append(issued_user_token).append("\"");
        sb.append("}");
        return sb.toString();
    }

    private String sbDomainBuilder(String domainValue) {
        String hex = DatatypeConverter.printHexBinary(domainValue.getBytes());
        StringBuilder sb = new StringBuilder();

        sb.append("{\n" +
                "    \"TransactionType\": \"AccountSet\",\n" +
                "    \"Fee\": \"12\",\n" +
                "    \"Domain\": \""+hex+"\"," +
                "    \"SetFlag\": 5\n" +
                "},");
        return sb.toString();
    }

    private String createHTML(byte[] item, String nftName, String nftAuthor, String nftEmail, String nftTwitter, String nftDescription) throws IOException {
        StringBuilder htmlBuilder = new StringBuilder();

        this.nftName = sanitizeHTMLInput(nftName);
        this.nftAuthor = sanitizeHTMLInput(nftAuthor);
        this.nftEmail = sanitizeHTMLInput(nftEmail);
        this.nftTwitter = sanitizeHTMLInput(nftTwitter);
        this.nftDescription = sanitizeHTMLInput(nftDescription);

        Multihash nftItem = createIPFS(item);
        String Website = "null";
        String CreatedAt = "https://xls19d-xumm-dev.herokuapp.com/";
        String NFTBuilder = "https://github.com/francisrosario/";

        String photo = "<div id=\"card\">\n" +
                "            <img src=\"https://gateway.pinata.cloud/ipfs/"+nftItem+"\" width=\"620\">\n" +
                "        </div>";

        htmlBuilder.append("<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <title>XRP NFT:"+ this.nftName +"</title>\n" +
                "    </head>\n" +
                "    <style>\n" +
                "        body,html {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            font-family: \"Helvetica\", sans-serif;\n" +
                "            background: linear-gradient(-45deg, #d34d24, #ac1d54, #2e8fb3, #40b499);\n" +
                "            background-size: 500% 500%;\n" +
                "            animation: gradient 60s ease infinite;\n" +
                "            height: 100%;\n" +
                "        }\n" +
                "        @keyframes gradient {\n" +
                "            0% {\n" +
                "                background-position: 0% 50%;\n" +
                "            }\n" +
                "            50% {\n" +
                "                background-position: 100% 50%;\n" +
                "            }\n" +
                "            100% {\n" +
                "                background-position: 0% 50%;\n" +
                "            }\n" +
                "        }\n" +
                "        h1 {\n" +
                "            font-size: 36px;\n" +
                "            margin: 10px 0 0 0;\n" +
                "            font-weight: lighter;\n" +
                "            text-transform: uppercase;\n" +
                "            color: #ffffff;\n" +
                "        }\n" +
                "        p {\n" +
                "            font-size: 16px;\n" +
                "            font-family: sans-serif;\n" +
                "            font-weight: bolder;\n" +
                "            color: #f8f8f8;\n" +
                "            max-width: 50%;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "        #header {\n" +
                "            position: absolute;\n" +
                "            left: 0;\n" +
                "            top: 0;\n" +
                "            padding:10px 20px;\n" +
                "            font-size: 24px;\n" +
                "            width: 100%;\n" +
                "            background-color: #ffffff;\n" +
                "            background: #ffff;\n" +
                "            border-bottom: 1px solid #1f1209;\n" +
                "            box-shadow: 0 10px 20px -10px rgb(0, 0, 0);\n" +
                "            box-sizing:border-box;\n" +
                "            -moz-box-sizing:border-box;\n" +
                "            -webkit-box-sizing:border-box;\n" +
                "            -ms-box-sizing:border-box; \n" +
                "        }\n" +
                "        #canvas {\n" +
                "            min-height: 100%;\n" +
                "            position: absolute;\n" +
                "        }\n" +
                "        #container {\n" +
                "            padding-top: 120px;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            flex-direction: column;\n" +
                "            align-items: center;\n" +
                "            transform: translatey(0px);\n" +
                "        }\n" +
                "        #card {\n" +
                "            transform: translatey(0px);\n" +
                "            animation: float 5s ease-in-out infinite;\n" +
                "            text-align: center;\n" +
                "            padding: 100px;\n" +
                "            border-radius: 10px;\n" +
                "            background: -webkit-radial-gradient(center center, rgb(35, 37, 38, 0.85) 0%, rgb(65, 67, 69, 0.75) 100%);\n" +
                "        }\n" +
                "        @keyframes float {\n" +
                "            0% {\n" +
                "                box-shadow: 0 5px 15px 0px rgba(0, 0, 0, 0.6);\n" +
                "                transform: translatey(0px);\n" +
                "            }\n" +
                "            50% {\n" +
                "                box-shadow: 0 25px 15px 0px rgba(0, 0, 0, 0.2);\n" +
                "                transform: translatey(-20px);\n" +
                "            }\n" +
                "            100% {\n" +
                "                box-shadow: 0 5px 15px 0px rgba(0, 0, 0, 0.6);\n" +
                "                transform: translatey(0px);\n" +
                "            }\n" +
                "        }\n" +
                "        #content {\n" +
                "            padding-top: 20px;\n" +
                "            text-align: center;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            flex-direction: column;\n" +
                "            align-items: center;\n" +
                "        }\n" +
                "        .styled-table {\n" +
                "            border-collapse:collapse;\n" +
                "            margin: 25px 0;\n" +
                "            font-size: 16px;\n" +
                "            font-family: sans-serif;\n" +
                "            box-shadow: 0 25px 15px 0px rgba(0, 0, 0, 0.2);\n" +
                "            max-width: 75%;\n" +
                "        }\n" +
                "        .styled-table thead tr {\n" +
                "            background-color: #030833;\n" +
                "            color: #ffffff;\n" +
                "            text-align: center;\n" +
                "            font-size: 18px;\n" +
                "        }\n" +
                "        .styled-table th, .styled-table td {\n" +
                "            padding: 14px 16px;\n" +
                "        }\n" +
                "        .styled-table tbody tr {\n" +
                "            border-bottom: 1px solid #dddddd;\n" +
                "        }\n" +
                "        .styled-table tbody tr:nth-of-type(even) {\n" +
                "            background-color: rgb(255, 255, 255, 0.75);\n" +
                "        }\n" +
                "        .styled-table tbody tr:nth-of-type(odd) {\n" +
                "            background-color: rgb(255, 255, 255, 0.85);\n" +
                "        }\n" +
                "        .styled-table tbody tr.active-row {\n" +
                "            font-weight: bold;\n" +
                "            color: #180141;\n" +
                "\n" +
                "        }\n" +
                "        .styled-table thead {\n" +
                "            display: table-header-group;\n" +
                "            vertical-align: middle;\n" +
                "            border-color: inherit;\n" +
                "            border-collapse: separate;\n" +
                "        }\n" +
                "        th:first-of-type {\n" +
                "            border-top-left-radius: 10px;\n" +
                "        }\n" +
                "        th:last-of-type {\n" +
                "            border-top-right-radius: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <canvas id=\"canvas\"></canvas>\n" +
                "    <div id=\"header\">\n" +
                "        <svg height=\"36\" viewBox=\"0 0 512 143\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "            <g id=\"Canvas\" fill=\"none\">\n" +
                "                <g id=\"xrp-text-mark-black\">\n" +
                "                    <g id=\"Group\">\n" +
                "                        <path id=\"Vector\" d=\"M 143.928 0L 168.372 0L 117.518 51.4245C 99.1086 70.0395 69.2627 70.0395 50.8541 51.4245L 8.30456e-09 0L 24.4431 0L 63.0757 39.0659C 74.7345 50.8554 93.6366 50.8554 105.295 39.0659L 143.928 0Z\" transform=\"translate(0.311646 0)\" fill=\"#23292F\"/>\n" +
                "                        <path id=\"Vector_2\" d=\"M 24.4432 65.7034L 0 65.7034L 51.1682 13.9612C 69.5768 -4.65374 99.4227 -4.65374 117.832 13.9612L 169 65.7034L 144.557 65.7034L 105.61 26.3196C 93.9508 14.53 75.0486 14.53 63.3899 26.3196L 24.4432 65.7034Z\" transform=\"translate(0 77.2965)\" fill=\"#23292F\"/>\n" +
                "                    </g>\n" +
                "                    <path id=\"Union\" fill-rule=\"evenodd\" clip-rule=\"evenodd\" d=\"M 73.1365 0L 73.1637 0.000372426C 79.4083 0.129969 85.2723 1.35704 90.7291 3.69537C 96.275 5.8933 101.147 8.93696 105.324 12.8252C 109.518 16.7277 112.793 21.3388 115.154 26.6325C 117.528 31.8339 118.71 37.3548 118.71 43.1721C 118.71 49.1231 117.457 54.7736 114.946 60.102C 112.58 65.2803 109.236 69.8798 104.928 73.8884C 100.742 77.7837 95.8004 80.8874 90.1272 83.2056L 90.1005 83.2164C 84.5016 85.4307 78.4991 86.5289 72.1193 86.5289L 18.7701 86.5289L 18.7701 142.515L 0 142.515L 0 0.000372426L 73.1365 0ZM 72.1193 69.2464C 75.8827 69.2464 79.4447 68.5705 82.8275 67.2212C 86.2414 65.8594 89.2106 63.9962 91.7548 61.6288C 94.2985 59.2618 96.3007 56.4982 97.7642 53.3212C 99.2095 50.1826 99.9402 46.8071 99.9402 43.1721C 99.9402 39.6704 99.2138 36.356 97.7642 33.2077C 96.3007 30.0307 94.2985 27.2671 91.7548 24.9001C 89.2106 22.5331 86.2414 20.6696 82.8275 19.3077C 79.4447 17.9588 75.8827 17.2829 72.1193 17.2829L 18.7701 17.2829L 18.7701 69.2464L 72.1193 69.2464Z\" transform=\"translate(393.29 0)\" fill=\"#23292F\"/>\n" +
                "                    <path id=\"Union_2\" d=\"M 45.7768 86.8257C 55.1828 86.8257 64.0833 90.7999 69.9857 97.6349L 108.742 142.515L 132.871 142.515L 79.8436 80.2913C 73.9448 73.3694 64.9858 69.3359 55.5094 69.3359L 18.7701 69.3359L 18.7701 17.4902L 72.1198 17.4902C 75.8827 17.4902 79.4447 18.167 82.8275 19.5176C 86.2419 20.8809 89.2111 22.7468 91.7548 25.1172L 91.7976 25.156C 94.315 27.3903 96.3055 30.0792 97.7642 33.2513C 99.2095 36.394 99.9401 39.7731 99.9401 43.4129C 99.9401 47.0474 99.2116 50.422 97.7706 53.5606C 96.8962 55.3918 95.8268 57.0993 94.5605 58.6863L 106.774 72.3171C 106.927 72.1564 107.078 71.9946 107.229 71.8316C 110.84 67.9484 113.623 63.6069 115.572 58.8164C 117.669 53.9875 118.71 48.8462 118.71 43.4129C 118.71 37.5873 117.528 32.06 115.154 26.8519C 112.794 21.5506 109.518 16.9342 105.324 13.026C 101.151 9.13794 96.2879 6.03255 90.7526 3.71021C 85.2895 1.36215 79.418 0.130137 73.1637 0.000745722L 73.1365 0L 9.3853 0C 6.81059 0 4.55556 0.969876 2.69921 2.69932C 0.924157 4.35344 0 6.40953 0 8.74492L 0 142.515L 18.7701 142.515L 18.7701 86.8257L 45.7768 86.8257Z\" transform=\"translate(211.163 0)\" fill=\"#23292F\"/>\n" +
                "                </g>\n" +
                "            </g>\n" +
                "        </svg> NFT\n" +
                "    </div><br><br><br><br><br><br><br><br>" +
                "    \n" + photo +
                "        <div id=\"content\">\n" +
                "            <h1>"+ this.nftName +"</h1>\n" +
                "             <p>"+this.nftDescription+"</p>\n" +
                "                    <table class=\"styled-table\">\n" +
                "                <thead>\n" +
                "                    <tr>\n" +
                "                        <th>Name</th>\n" +
                "                        <th>Value</th>\n" +
                "                    </tr>\n" +
                "                </thead>\n" +
                "                <tbody>\n" +
                "                    <tr>\n" +
                "                        <td>Author</td>\n" +
                "                        <td>"+ this.nftAuthor +"</td>\n" +
                "                    </tr>\n" +
                /**
                 "                    <tr>\n" +
                 "                        <td>Author Wallet</td>\n" +
                 "                        <td>"+wallet.classicAddress()+"</td>\n" +
                 "                    </tr>\n" +
                 **/
                "                    <tr>\n" +
                "                        <td>Author Email</td>\n" +
                "                        <td>"+ this.nftEmail +"</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>Author Twitter</td>\n" +
                "                        <td>"+ this.nftTwitter +"</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>Author Website</td>\n" +
                "                        <td>"+Website+"</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>Created at</td>\n" +
                "                        <td>"+CreatedAt+"</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>NFT Builder</td>\n" +
                "                        <td>"+NFTBuilder+"</td>\n" +
                "                    </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <script>\n" +
                "        //Stop video moving on play\n" +
                "        const video = document.querySelector('video');\n" +
                "        video.addEventListener('play', (event) => {\n" +
                "            document.getElementById(\"card\").getAnimations()[0].pause();\n" +
                "        });\n" +
                "        //Start video moving on pause\n" +
                "        video.addEventListener('pause', (event) => {\n" +
                "            document.getElementById(\"card\").getAnimations()[0].play();\n" +
                "        });\n" +
                "\n" +
                "        //Floaters\n" +
                "        var canvas = document.getElementById(\"canvas\");\n" +
                "        var ctx = canvas.getContext(\"2d\");\n" +
                "        canvas.width = window.innerWidth;\n" +
                "        canvas.height = window.innerHeight;\n" +
                "        var particles = [];\n" +
                "\n" +
                "        var Particle = function () {\n" +
                "            this.x = canvas.width * Math.random();\n" +
                "            this.y = canvas.height * Math.random();\n" +
                "            this.vx = 4 * Math.random() - 2;\n" +
                "            this.vy = 4 * Math.random() - 2;\n" +
                "            this.Color = \"rgb(255, 255, 255)\";\n" +
                "        }\n" +
                "        Particle.prototype.Draw = function (ctx) {\n" +
                "            ctx.fillStyle = this.Color;\n" +
                "            ctx.fillRect(this.x, this.y, 2, 2);\n" +
                "        }\n" +
                "        Particle.prototype.Update = function () {\n" +
                "            this.x += this.vx;\n" +
                "            this.y += this.vy;\n" +
                "        \n" +
                "            if (this.x<0 || this.x > canvas.width)\n" +
                "                this.vx = -this.vx;\n" +
                "        \n" +
                "            if (this.y < 0 || this.y > canvas.height)\n" +
                "                this.vy = -this.vy;\n" +
                "        }\n" +
                "        function loop() {\n" +
                "            ctx.clearRect(0, 0, canvas.width, canvas.height);\n" +
                "            for (var i = 0; i < 45; i++) {\n" +
                "                particles[i].Update();\n" +
                "                particles[i].Draw(ctx);\n" +
                "            }\n" +
                "            requestAnimationFrame(loop);\n" +
                "        }\n" +
                "        //Create particles\n" +
                "        for (var i = 0; i < 45; i++)\n" +
                "            particles.push(new Particle());\n" +
                "        loop();\n" +
                "    </script>\n" +
                "</body></html>");
        htmlBuilder.toString();
        Multihash nftHtml = createIPFS(htmlBuilder.toString().getBytes());
        createDomainValue(2, Optional.of("ipfs"), Optional.of(String.valueOf(nftHtml)));
        createDomainValue(2, Optional.of("ipfs"), Optional.of(String.valueOf(nftItem)));
        createDomainValue(2, Optional.of("http"), Optional.of("https://xrptools-web-dev.herokuapp.com/"));

        return domainValue;
    }

    @Override
    public void processAuthorization() {
        try {
            createSignIn_URL();
            payload_Data = postPayload(signInURL_Device);
            loginURL_Redirect = getLogin_URL(payload_Data);
            uuid = getUUID(payload_Data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkAuthorization() {
        try {
            payload_uuid_Data = getPayload_UUID(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
            issued_user_token = getUserToken(payload_uuid_Data);
        }

    @Override
    public void createNFTWallet(byte[] imageByte, String nftName, String nftAuthor, String nftEmail, String nftTwitter, String nftDescription) {
        try {
            System.out.println(nftName);
            createHTML(imageByte, nftName, nftAuthor, nftEmail, nftTwitter, nftDescription);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String JSON = sbDomainBuilder(domainValue);
        createDomainValue(10, Optional.empty(), Optional.empty());
        payloadDomain = createPayload(JSON);
        try {
            postPayload(payloadDomain);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
