package com.sample.toolbar.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/6/8.
 */
public class HttpUtil {

    public InputStream get(String urlStr){
        HttpURLConnection conn =null;
        InputStream is = null;
        try {
            URL url = new URL(urlStr);

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setIfModifiedSince(6*60*1000);

            int responseCode = conn.getResponseCode();

            if (responseCode==HttpURLConnection.HTTP_OK){
                is = conn.getInputStream();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (conn!=null){
                conn.disconnect();
            }
        }

        return is;

    }

}
