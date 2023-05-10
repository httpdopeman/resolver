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

public class Aparat {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){

        String fixurl = "https://www.aparat.com/api/fa/v1/video/video/show/videohash/"+url.substring(url.lastIndexOf('/') + 1);
        AndroidNetworking.get(fixurl)
                .setUserAgent(agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final String regex = "profile.?:.?\"(.*?)\"(?:.).*?urls.?:.*?\"(.*?)\"";
                            final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
                            final Matcher matcher = pattern.matcher(response);

                            ArrayList<Jmodel> jModels = new ArrayList<>();

                            while(matcher.find()){

                                Jmodel jModel = new Jmodel();
                                jModel.setUrl(matcher.group(2).replace("\\/", "/"));
                                jModel.setQuality(matcher.group(1));
                                jModels.add(jModel);
                            }

                            if(jModels.size() > 1){
                                onComplete.onTaskCompleted(jModels, true);
                            } else {
                                onComplete.onTaskCompleted(jModels, false);
                            }
                        } catch (Exception A){
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
