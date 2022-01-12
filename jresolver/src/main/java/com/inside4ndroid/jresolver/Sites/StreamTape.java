package com.inside4ndroid.jresolver.Sites;


import static com.inside4ndroid.jresolver.Jresolver.agent;

import android.os.StrictMode;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamTape {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){
        AndroidNetworking.get(url)
                .setUserAgent(agent)
                .addHeaders("Referer", "https://streamtape.com/")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        Pattern pattern = Pattern.compile("ById\\('.+?=.*([\"']//[^;+]+).*'(.*?)'");
                        Matcher matcher = pattern.matcher(response);
                        if (matcher.find()) {

                            String match1 = matcher.group(1);
                            String match2 = matcher.group(2);

                            match1 = match1.replace("'", "");
                            match2 = match2.substring(3);

                            String src = "https:"+match1+match2+"&stream=1";

                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            try {
                                URL url = new URL(src.replaceAll("\\s+", ""));
                                HttpURLConnection ucon = null;
                                ucon = (HttpURLConnection) url.openConnection();
                                ucon.setConnectTimeout(6000);
                                ucon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0");
                                ucon.setRequestProperty("Referer", "https://streamtape.com/");
                                ucon.setInstanceFollowRedirects(false);
                                URL secondURL = new URL(ucon.getHeaderField("Location"));
                                String linkF = secondURL.toString();

                                Jmodel jModel = new Jmodel();
                                jModel.setUrl(linkF);
                                jModel.setQuality("Normal");
                                ArrayList<Jmodel> jModels = new ArrayList<>();
                                jModels.add(jModel);
                                onComplete.onTaskCompleted(jModels,false);

                            } catch (Exception A) {
                                onComplete.onError();
                                A.printStackTrace();
                            }


                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }
}