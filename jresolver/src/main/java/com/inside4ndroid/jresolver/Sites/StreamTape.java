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

        if(url.contains("/e/")){
            url = url.replace("/e/", "/v/");
        }

        Log.d("STREAMTAPE URL ", url);

        AndroidNetworking.get(url)
                .setUserAgent(agent)
                .addHeaders("Referer", "https://streamtape.com/")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        Pattern pattern = null;
                        Pattern pattern1 = null;
                        String code = null;

                        pattern = Pattern.compile("norobotlink.*streamtape.+com|to(.*?)&token");
                        pattern1 = Pattern.compile("ideoooolink.*token=(.*?)'\\)");

                        Matcher matcher = pattern.matcher(response);
                        Matcher matcher2 = pattern1.matcher(response);
                        if (matcher.find()) {

                            String match1 = matcher.group(1);
                            //String code = matcher2.group(1);

                            Log.d("MATCH1 ", match1);
                            //Log.d("MATCH2 ", code);

                            if (matcher2.find()) {
                                code = matcher2.group(1);
                                Log.d("MATCH2 ", code);
                            }

                            String src = "https://streamtape.com"+match1+"&token="+code+"&stream=1";

                            Log.d("THE SRC ", src);

                            Log.d("THE SRC URL ", src);

                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            try {
                                URL url = new URL(src.replaceAll("\\s+", ""));
                                HttpURLConnection ucon = null;
                                ucon = (HttpURLConnection) url.openConnection();
                                ucon.setConnectTimeout(6000);
                                ucon.setRequestProperty("User-Agent", agent);
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
                        Log.d("AN ERROR ", anError.getErrorBody());
                        onComplete.onError();
                    }
                });
    }
}