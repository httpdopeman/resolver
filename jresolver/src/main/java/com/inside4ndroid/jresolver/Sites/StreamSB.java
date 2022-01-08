package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Jresolver.agent;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Utils.Utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamSB {

    private static String host;

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete) {

        url = url.replace("/e/", "/d/");

        host = Utils.getDomainFromURL(url);

        AndroidNetworking.get(url)
                .setUserAgent(agent)
                .addHeaders("Referer", host)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        final String regex = "download_video.'(.*?)'.*'h'.*'(.*?)'";
                        final Pattern pattern = Pattern.compile(regex);
                        final Matcher matcher = pattern.matcher(response);
                        if (matcher.find()) {
                            AndroidNetworking.get(host + "/dl?op=download_orig&id=" + matcher.group(1) + "&mode=h&hash=" + matcher.group(2))
                                    .setUserAgent(agent)
                                    .addHeaders("Referer", host)
                                    .build()
                                    .getAsString(new StringRequestListener() {
                                        @Override
                                        public void onResponse(String response) {
                                            final String regex = "href=\"([^\"]+)\">Direct";
                                            final Pattern pattern = Pattern.compile(regex);
                                            final Matcher matcher = pattern.matcher(response);
                                            if (matcher.find()) {
                                                Jmodel jModel = new Jmodel();
                                                jModel.setUrl(matcher.group(1));
                                                jModel.setQuality("Normal");
                                                ArrayList<Jmodel> jModels = new ArrayList<>();
                                                jModels.add(jModel);
                                                onComplete.onTaskCompleted(jModels,false);
                                            } else {
                                                onComplete.onError();
                                            }
                                        }

                                        @Override
                                        public void onError(ANError anError) {
                                            onComplete.onError();
                                        }
                                    });
                        } else {
                            onComplete.onError();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }
}
