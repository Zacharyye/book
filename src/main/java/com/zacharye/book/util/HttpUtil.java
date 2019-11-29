package com.zacharye.book.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;


public class HttpUtil {

    public static String get(String httpUrl) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();// 正常访问

            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");

            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (SocketException e) {
            System.out.println("Connection timed out: connect");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result:" + result);
        return result;
    }


    public static String post(String httpUrl) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();// 正常访问

            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");

            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (SocketException e) {
            System.out.println("Connection timed out: connect");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result:" + result);
        return result;
    }

}
