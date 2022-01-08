package com.inside4ndroid.jresolver.Sites;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.inside4ndroid.jresolver.Jresolver.agent;
import static com.inside4ndroid.jresolver.Utils.Utils.putModel;
import static com.inside4ndroid.jresolver.Utils.Utils.sortMe;

public class OKRU {
    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){

        String metaUri = "http://www.ok.ru/dk";

        AndroidNetworking.post(metaUri)
                .addHeaders("User-agent", agent)
                .addBodyParameter("cmd", "videoPlayerMetadata")
                .addBodyParameter("mid", getMediaID(url))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray contacts = response.getJSONArray("videos");
                            ArrayList<Jmodel> models = new ArrayList<>();
                            for (int i = 0; i < contacts.length(); i++) {
                                String url = contacts.getJSONObject(i).getString("url");
                                String name = contacts.getJSONObject(i).getString("name");
                                if (name.equals("mobile")) {
                                    putModel(url, "144p", models);
                                } else if (name.equals("lowest")) {
                                    putModel(url, "240p", models);
                                } else if (name.equals("low")) {
                                    putModel(url, "360p", models);
                                } else if (name.equals("sd")) {
                                    putModel(url, "480p", models);
                                } else if (name.equals("hd")) {
                                    putModel(url, "720p", models);
                                } else if (name.equals("full")) {
                                    putModel(url, "1080p", models);
                                } else if (name.equals("quad")) {
                                    putModel(url, "2000p", models);
                                } else if (name.equals("ultra")) {
                                    putModel(url, "4000p", models);
                                } else {
                                    putModel(url, "Default", models);
                                }
                            }
                            onComplete.onTaskCompleted(sortMe(models), true);
                        } catch (Exception Error){
                            onComplete.onError();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }

    private static String getMediaID(String url){
        url = url.substring(url.lastIndexOf("/") + 1);
        return url;
    }
}
