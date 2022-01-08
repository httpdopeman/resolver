package com.inside4ndroid.jresolver.Sites;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.inside4ndroid.jresolver.Jresolver.agent;

public class SolidFiles {
    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){
        AndroidNetworking.get(url)
                .addHeaders("User-Agent",agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Jmodel> jModels = parse(response);
                        if (jModels!=null){
                            onComplete.onTaskCompleted(jModels,false);
                        }else onComplete.onError();
                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }

    private static ArrayList<Jmodel> parse(String response){
        String src = getUrl(response);
        if (null!=src){
            Jmodel jModel = new Jmodel();
            jModel.setUrl(src);
            jModel.setQuality("Normal");
            ArrayList<Jmodel> jModels = new ArrayList<>();
            jModels.add(jModel);
            return jModels;
        }
        return null;
    }

    private static String getUrl(String html){
        final String regex = "downloadUrl\":\"(.*?)\"";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
