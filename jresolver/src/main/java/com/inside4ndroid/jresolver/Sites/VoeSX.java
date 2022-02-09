package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Jresolver.agent;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoeSX {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){
        AndroidNetworking.get(url)
                .setUserAgent(agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Jmodel> xModels = parse(response);
                        if (xModels!=null){
                            onComplete.onTaskCompleted(xModels,false);
                        }else onComplete.onError();
                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }

    private static ArrayList<Jmodel> parse(String response) {
        String src = getSrc(response);
        if (src != null && src.length() > 0) {
            Jmodel jModel = new Jmodel();
            jModel.setUrl(src);
            jModel.setQuality("Normal");

            ArrayList<Jmodel> xModels = new ArrayList<>();
            xModels.add(jModel);
            return xModels;

        }
        return null;
    }


    private static String getSrc(String code){
        final String regex = "hls\": \"(.*?)\"";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(code);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
