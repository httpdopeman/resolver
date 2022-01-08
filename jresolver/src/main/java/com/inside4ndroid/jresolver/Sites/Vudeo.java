package com.inside4ndroid.jresolver.Sites;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vudeo {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onTaskCompleted){
        AndroidNetworking.get(url)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Jmodel> jModels = parseVideo(response);
                        if (jModels.isEmpty()){
                            onTaskCompleted.onError();
                        }else onTaskCompleted.onTaskCompleted(jModels, false);
                    }

                    @Override
                    public void onError(ANError anError) {
                        onTaskCompleted.onError();
                    }
                });
    }

    private static ArrayList<Jmodel> parseVideo(String html){
        final String regex = "sources: ?\\[\"(.*?)\"";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(html);
        ArrayList<Jmodel> jModels = new ArrayList<>();
        if (matcher.find()) {
            Jmodel jModel = new Jmodel();
            jModel.setQuality("Normal");
            jModel.setUrl(matcher.group(1));
            jModels.add(jModel);
        }
        return jModels;
    }
}
