package com.inside4ndroid.jresolver.sites;

import static com.inside4ndroid.jresolver.Jresolver.agent;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.model.Jmodel;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitChute {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){
        AndroidNetworking.get(url)
                .setUserAgent(agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Pattern pattern = Pattern.compile("<source src=\"(.*?)\"");
                        Matcher matcher = pattern.matcher(response);
                        if(matcher.find()) {
                            Jmodel xModel = new Jmodel();
                            xModel.setUrl(matcher.group(1));
                            xModel.setQuality("Normal");
                            ArrayList<Jmodel> xModels = new ArrayList<>();
                            xModels.add(xModel);
                            onComplete.onTaskCompleted(xModels,false);
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
