package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Jresolver.agent;
import static com.inside4ndroid.jresolver.Utils.Utils.putModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Anavidz_dead {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){
        AndroidNetworking.get(url)
                .setUserAgent(agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replace(" ", "").replace("\n", "");
                        Pattern pattern = Pattern.compile("setup\\((.*?),image");
                        Matcher matcher = pattern.matcher(response);
                        if(matcher.find()) {
                            String tmpJsonString = matcher.group(1);
                            String Json = tmpJsonString.replace("sources", "\"sources\"").replace("file", "\"file\"").replace("label", "\"label\"") + "}";
                            try {
                                JSONObject jsonObj = new JSONObject(Json);
                                ArrayList<Jmodel> jModels = new ArrayList<>();
                                JSONArray array = jsonObj.getJSONArray("sources");
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject A = array.getJSONObject(i);
                                    String source = A.getString("file");
                                    String quality = A.getString("label");
                                    putModel(source,quality,jModels);

                                }

                                onComplete.onTaskCompleted(jModels, jModels.size() > 1);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }
}
