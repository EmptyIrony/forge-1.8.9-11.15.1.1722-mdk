package me.cunzai.bilibilichecker;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import scala.util.parsing.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Checker implements Runnable{
    public static int fans;
    public static ReentrantLock lockGetFans = new ReentrantLock();


    public static int getUid(){
        return ConfigLoader.uid;
    }

    @Override
    public void run(){
        refresh();
    }

    public static void refresh(){
        URL url = null;
        try {
            url = new URL("https://api.bilibili.com/x/relation/stat?vmid=" + getUid() );
            URLConnection urlConnection = url.openConnection();
            InputStream  inputStream = urlConnection.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer bs = new StringBuffer();
            String l;
            while (true) {
                if (!((l = buffer.readLine()) != null)) break;
                bs.append(l);
            }
            String resp =  bs.toString();
            lockGetFans.lock();
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(resp);
            if (json.get("code").getAsInt() == 0) {
                BiliBiliChecker.logger.info(json);
                fans = json.get("data").getAsJsonObject().get("follower").getAsInt();
            } else {
                fans = -1;
            }
        }catch (Exception e){
            fans = -1;
            e.printStackTrace();

        }finally {
            lockGetFans.unlock();
        }

    }



}
