package me.cunzai.bilibilichecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.locks.ReentrantLock;

public class UpdateCheck implements Runnable{
    public static boolean haveNewVersion;
    public static ReentrantLock lockNewVersion;

    public void run() {

        URL url = null;
        try {
            url = new URL("https://raw.githubusercontent.com/EmptyIrony/BiliBiliCheckerUpdate/master/update.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection urlConnection = null;
        try {
            urlConnection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inputStream = null;
        try {
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer bs = new StringBuffer();
        String l = null;
        while (true) {
            try {
                if (!((l = buffer.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            bs.append(l);
        }
        try {
            lockNewVersion.lock();
            String version;
            version = bs.toString();
            if (version.equals(BiliBiliChecker.VERSION)) {
                haveNewVersion = false;
            } else {
                BiliBiliChecker.logger.info("yes");
                BiliBiliChecker.logger.info(version);
                BiliBiliChecker.logger.info(BiliBiliChecker.VERSION);
                haveNewVersion = true;
            }
        }catch (Exception e){
            haveNewVersion = false;
        }finally {
            lockNewVersion.unlock();
        }
    }
}