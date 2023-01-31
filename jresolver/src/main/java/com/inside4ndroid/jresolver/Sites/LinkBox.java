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
import com.inside4ndroid.jresolver.Utils.JSUnpacker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkBox {

    static String apiUri = "https://www.linkbox.to/api/file/detail?itemId=URLID&needUser=1&needTpInfo=1&token=&lan=en";

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){
        String ID = getID(url);

        if(!(ID == null)){
            url = apiUri.replace("URLID", ID);

            AndroidNetworking.get(url)
                    .setUserAgent(agent)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json = new JSONObject(response);
                                JSONObject obj  = json.getJSONObject("data");
                                JSONObject obj1 = obj.getJSONObject("itemInfo");
                                JSONArray contacts = obj1.getJSONArray("resolutionList");
                                ArrayList<Jmodel> models = new ArrayList<>();
                                for (int i = 0; i < contacts.length(); i++) {
                                    String url = contacts.getJSONObject(i).getString("url");
                                    String quality = contacts.getJSONObject(i).getString("resolution");
                                    putModel(url, quality, models);
                                }

                                if(models.size() > 1){
                                    onComplete.onTaskCompleted(sortMe(models), true);
                                } else {
                                    onComplete.onTaskCompleted(models, false);
                                }
                            } catch (Exception Error){
                                Error.printStackTrace();
                                onComplete.onError();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            onComplete.onError();
                        }
                    });
        } else {
            //url id is null
            onComplete.onError();
        }


    }

    private static String getID(String url){
        if(url.contains("ch=")){
            Pattern pattern = Pattern.compile("file/(.*?)\\?ch=");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                return matcher.group(1);
            } else {
                return null;
            }
        } else {
            Pattern pattern = Pattern.compile("file/(.*)");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                return matcher.group(1);
            } else {
                return null;
            }
        }
    }
}