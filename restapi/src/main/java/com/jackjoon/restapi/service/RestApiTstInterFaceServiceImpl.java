package com.jackjoon.restapi.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestApiTstInterFaceServiceImpl implements RestApiTstInterFaceService {

    public String sendRest(String sendUrl, String jsonValue) throws IllegalStateException {
        String inputLine = null;
        StringBuffer outResult = new StringBuffer();

        try {
            System.out.println("REST API Start");
            URL url = new URL(sendUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            /**
             * **** 인증이 있는경우
             String id_pass = "id:password";
             String base64Credentials = new String(Base64.getEncoder().encode(id_pass.getBytes()));
             conn.setRequestProperty("Authorization", "Basic " + base64Credentials);
             */

            OutputStream os = conn.getOutputStream();
            os.write(jsonValue.getBytes("UTF-8"));
            os.flush();

            // 리턴된 결과 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((inputLine = in.readLine()) != null) {
                outResult.append(inputLine);
            }

            conn.disconnect();
            System.out.println("REST API End");

        }catch(Exception e) {
            e.printStackTrace();
        }
        return outResult.toString();
    }
}
