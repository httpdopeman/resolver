package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Jresolver.agent;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Voxzer {

    private static String mediaID;

    public static void fetch(final String url, final Jresolver.OnTaskCompleted onComplete) {

        //https://player.voxzer.org/view/691379d68d354fe6e71265e1

        AndroidNetworking.get(url)
                .setUserAgent(agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        String listuri = url.replace("/view/", "/list/");
                        AndroidNetworking.get(listuri)
                                .setUserAgent(agent)
                                .addHeaders("Referer", url)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            Iterator<String> keys = response.keys();
                                            ArrayList<Jmodel> jModels = new ArrayList<>();
                                            while(keys.hasNext()) {
                                                String key = keys.next();
                                                Jmodel jModel = new Jmodel();
                                                jModel.setUrl(response.getString(key));
                                                jModel.setQuality("Normal");
                                                jModels.add(jModel);
                                            }

                                            if(jModels.size() > 1){
                                                onComplete.onTaskCompleted(jModels,true);
                                            } else {
                                                onComplete.onTaskCompleted(jModels,false);
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

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }
}
