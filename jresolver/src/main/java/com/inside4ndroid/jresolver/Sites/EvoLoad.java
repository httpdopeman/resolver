package com.inside4ndroid.jresolver.Sites;
import static com.inside4ndroid.jresolver.Jresolver.agent;
import static com.inside4ndroid.jresolver.Utils.Utils.putModel;
import static com.inside4ndroid.jresolver.Utils.Utils.sortMe;

import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Utils.Utils;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvoLoad {

    private static String web_url;
    private static String UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0";
    private static String surl = "https://evoload.io/SecurePlayer";
    private static String HOST;
    private static String passe;
    private static String MEDIAID;
    public static void fetch(final String uri, final Jresolver.OnTaskCompleted onTaskCompleted) {

        web_url = uri;

        if(web_url.contains("/d/")){
            web_url = uri.replace("/d/", "/e/");
        }
        HOST = Utils.getDomainFromURL(web_url);
        MEDIAID = Utils.getID(web_url);

        AndroidNetworking.get(web_url)
                .addHeaders("User-Agent", UserAgent)
                .addHeaders("Referer", HOST)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Pattern pattern = Pattern.compile("<div\\s*id=\"captcha_pass\"\\s*value=\"(.+?)\"></div>");
                        Matcher matcher = pattern.matcher(response);
                        if (matcher.find()) {
                            passe = matcher.group(1);

                            AndroidNetworking.get("https://csrv.evosrv.com/captcha?m412548")
                                    .addHeaders("User-Agent", UserAgent)
                                    .addHeaders("Referer", HOST)
                                    .addHeaders("Origin", HOST)
                                    .build()
                                    .getAsString(new StringRequestListener() {
                                        @Override
                                        public void onResponse(String response) {
                                            AndroidNetworking.post(surl)
                                                    .addHeaders("User-Agent", UserAgent)
                                                    .addHeaders("Referer", HOST)
                                                    .addHeaders("Origin", HOST)
                                                    .addBodyParameter("code", MEDIAID)
                                                    .addBodyParameter("csrv_token", response)
                                                    .addBodyParameter("pass", passe)
                                                    .addBodyParameter("token", "ok")
                                                    .build()
                                                    .getAsString(new StringRequestListener() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Pattern pattern = Pattern.compile("src\":\"(.*?)\"");
                                                            Matcher matcher = pattern.matcher(response);
                                                            if (matcher.find()) {
                                                                ArrayList<Jmodel> videoModels = new ArrayList<>();
                                                                putModel(matcher.group(1),"Normal", videoModels);
                                                                onTaskCompleted.onTaskCompleted(videoModels, false);
                                                            }

                                                        }

                                                        @Override
                                                        public void onError(ANError anError) {
                                                            onTaskCompleted.onError();
                                                        }
                                                    });
                                        }

                                        @Override
                                        public void onError(ANError anError) {
                                            onTaskCompleted.onError();
                                        }
                                    });

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        onTaskCompleted.onError();
                    }
                });

    }
}
