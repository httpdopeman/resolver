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

public class HDVid {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){
        if(!url.contains("embed-")){
            String host = Utils.getDomainFromURL(url);
            String id = Utils.getID(url);

            url = host+ "/embed-" + id + ".html";
        }

        AndroidNetworking.get(url)
                .setUserAgent(agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replace(" ", "").replace("\n", "");
                        Pattern pattern = Pattern.compile("file:\"(.*?)\",");
                        Matcher matcher = pattern.matcher(response);
                        if(matcher.find()) {
                            Jmodel jModel = new Jmodel();
                            jModel.setUrl(matcher.group(1));
                            pattern = Pattern.compile("label:\"(.*?)\"");
                            matcher = pattern.matcher(response);
                            if(matcher.find()){
                                jModel.setQuality(matcher.group(1)+"p");
                            } else {
                                jModel.setQuality("Normal");
                            }

                            ArrayList<Jmodel> xModels = new ArrayList<>();
                            xModels.add(jModel);
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
