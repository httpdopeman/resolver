package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Jresolver.agent;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dood {

    static String urlt;
    static String token;
    static boolean isLong;
    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete) {
        String ref = url;

        if(url.contains("LONG")){
            isLong = true;
            url = url.replace("LONG", "");
        } else {
            isLong = false;
            url = url.replace("/d/", "/e/");
        }

        final String finalUrl = url;
        AndroidNetworking.get(url)
                .setUserAgent(agent)
                .addHeaders("Referer", ref)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        if (isLong) {
                            Pattern pattern = Pattern.compile("<iframe src=\"(.*?)\"");
                            Matcher matcher = pattern.matcher(response);
                            if(matcher.find()){

                                try {
                                    AndroidNetworking.get("https://" + getHost(matcher.group(1)))
                                            .setUserAgent(agent)
                                            .build()
                                            .getAsString(new StringRequestListener() {
                                                @Override
                                                public void onResponse(String response) {

                                                    Pattern pattern = Pattern.compile("dsplayer\\.hotkeys[^']+'([^']+).+?function");
                                                    Matcher matcher = pattern.matcher(response);
                                                    if (matcher.find()) {
                                                        try {
                                                            urlt = "https://" + getHost(finalUrl) + matcher.group(1);
                                                        } catch (MalformedURLException a) {
                                                            a.printStackTrace();
                                                        }

                                                        pattern = Pattern.compile("makePlay.+?return[^?]+([^\"]+)");
                                                        matcher = pattern.matcher(response);
                                                        if (matcher.find()) {
                                                            token = matcher.group(1);

                                                            AndroidNetworking.get(urlt)
                                                                    .setUserAgent(agent)
                                                                    .addHeaders("Referer", finalUrl)
                                                                    .build()
                                                                    .getAsString(new StringRequestListener() {
                                                                        @Override
                                                                        public void onResponse(String response) {
                                                                            String test = response + randomStr(10) + token + System.currentTimeMillis() / 1000L;
                                                                            Jmodel jModel = new Jmodel();
                                                                            jModel.setUrl(test);
                                                                            jModel.setQuality("Normal");
                                                                            ArrayList<Jmodel> jModels = new ArrayList<>();
                                                                            jModels.add(jModel);
                                                                            onComplete.onTaskCompleted(jModels, false);
                                                                        }

                                                                        @Override
                                                                        public void onError(ANError anError) {
                                                                            onComplete.onError();
                                                                        }
                                                                    });
                                                        }

                                                    }



                                                }

                                                @Override
                                                public void onError(ANError anError) {
                                                    onComplete.onError();
                                                }
                                            });
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }


                        } else {
                            Pattern pattern = Pattern.compile("dsplayer\\.hotkeys[^']+'([^']+).+?function");
                            Matcher matcher = pattern.matcher(response);
                            if (matcher.find()) {
                                try {
                                    urlt = "https://" + getHost(finalUrl) + matcher.group(1);
                                } catch (MalformedURLException a) {
                                    a.printStackTrace();
                                }

                                pattern = Pattern.compile("makePlay.+?return[^?]+([^\"]+)");
                                matcher = pattern.matcher(response);
                                if (matcher.find()) {
                                    token = matcher.group(1);

                                    AndroidNetworking.get(urlt)
                                            .setUserAgent(agent)
                                            .addHeaders("Referer", finalUrl)
                                            .build()
                                            .getAsString(new StringRequestListener() {
                                                @Override
                                                public void onResponse(String response) {
                                                    String test = response + randomStr(10) + token + System.currentTimeMillis() / 1000L;

                                                    Jmodel jModel = new Jmodel();
                                                    jModel.setUrl(test);
                                                    jModel.setQuality("Normal");
                                                    ArrayList<Jmodel> jModels = new ArrayList<>();
                                                    jModels.add(jModel);
                                                    onComplete.onTaskCompleted(jModels, false);
                                                }

                                                @Override
                                                public void onError(ANError anError) {
                                                    onComplete.onError();
                                                }
                                            });
                                }

                            }
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }

    private static String randomStr(int len) {
        final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom rnd = new SecureRandom();


        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();

    }

    private static String getHost(String uri) throws MalformedURLException {
        URL url = new URL(uri);

        return url.getHost();
    }
}
