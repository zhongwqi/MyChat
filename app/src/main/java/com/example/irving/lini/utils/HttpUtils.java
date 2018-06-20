package com.example.irving.lini.utils;

import com.example.irving.lini.bean.ChatMessage;
import com.example.irving.lini.bean.Result;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by Irving on 2018/5/3.
 * 工具类   发送消息和接收消息
 */

public class HttpUtils {

    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "502b2c440b8b4ec4ae584c8f5084f420";
    public static final String TAG = HttpUtils.class.getSimpleName();

    private static InputStream is = null;
    private static ByteArrayOutputStream baos = null;

    /**
     * 发送信息并解析返回的json数据
     * @param msg
     * @return
     */
    public static ChatMessage sendMessage(String msg){

        ChatMessage chatMessage = new ChatMessage();

        String jsonResult = HttpUtils.doGet(msg);
        Gson gson = new Gson();
        Result result = null;

        try{
            result = gson.fromJson(jsonResult,Result.class);
            chatMessage.setMsg(result.getText());
        }catch (Exception e){
            System.out.println("服务器繁忙，请稍后再试！");
        }

        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }


    /**
     * 得到返回的消息字符串
     * @param msg
     * @return
     */
    public static String doGet(String msg) {

        String result = "";
        String url = setParams(msg);

        try {
            java.net.URL urlNet = new java.net.URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");

            is = conn.getInputStream();
            int len = -1;
            byte[] buf = new byte[128];
            baos = new ByteArrayOutputStream();
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            baos.flush();
            result = new String(baos.toByteArray());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
return result;
    }
    public static String setParams(String msg) {
        String url = "";
        try {
            url = URL+"?key="+API_KEY+"&info="+ URLEncoder.encode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
