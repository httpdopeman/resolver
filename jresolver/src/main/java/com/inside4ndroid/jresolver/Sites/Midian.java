package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Utils.Utils.sortMe;

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

public class Midian {
    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){

        if(url.endsWith("avi")){
            onComplete.onError();
        } else {
            ArrayList<Jmodel> jModels = new ArrayList<>();

            Jmodel jModel = new Jmodel();
            jModel.setUrl(url);
            jModel.setQuality("Normal");
            jModels.add(jModel);

            onComplete.onTaskCompleted(jModels,false);
        }


    }
}
