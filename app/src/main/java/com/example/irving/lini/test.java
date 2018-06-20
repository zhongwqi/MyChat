package com.example.irving.lini;

import android.util.Log;

import com.example.irving.lini.utils.HttpUtils;

/**
 * Created by Irving on 2018/5/3.
 */

public class test extends Thread {
    private String result = "";

    @Override
    public void run() {
        result = HttpUtils.doGet("你好");
        result = result + HttpUtils.doGet("给我讲个笑话吧");
        Log.i("TAG", "run: "+result);
    }

    public  String getResult(){
        Log.i("TAG", "getResult: "+"这是result的内容；"+result);
        return result;
    }
}
