package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Utils.Utils.sortMe;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Response;

public class EplayVid {
    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete) {
        AndroidNetworking.get(url)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        final String regex = "<source.*\"(.*?)\"";
                        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                        final Matcher matcher = pattern.matcher(response.toString());
                        if (matcher.find()) {
                            Jmodel jModel = new Jmodel();
                            jModel.setUrl(matcher.group(1));
                            jModel.setQuality("Normal");
                            ArrayList<Jmodel> jModels = new ArrayList<>();
                            jModels.add(jModel);
                            onComplete.onTaskCompleted(jModels,false);
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
