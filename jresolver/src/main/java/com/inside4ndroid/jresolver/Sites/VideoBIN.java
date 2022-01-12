package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Jresolver.agent;
import static com.inside4ndroid.jresolver.Utils.Utils.getDomainFromURL;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoBIN {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onTaskCompleted){
        AndroidNetworking.get(url)
                .addHeaders("User-Agent", agent)
                .addHeaders("content-type", "application/json")
                .addHeaders("Connection","close")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Jmodel> jModels = parseVideo(response);
                        if (jModels.isEmpty()) {
                            onTaskCompleted.onError();
                        } else onTaskCompleted.onTaskCompleted(Utils.sortMe(jModels), true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        onTaskCompleted.onError();
                    }
                });
    }

    private static String quality(int size,int index){
        List<String> quality = new ArrayList<>();
        switch (size){
            case 1:quality.add("480p");break;
            case 2:quality.add("720p");quality.add("480p");break;
            case 3:quality.add("1080p");quality.add("720p");quality.add("480p");break;
            case 4:quality.add("Higher");quality.add("1080p");quality.add("720p");quality.add("480p");break;
        }
        return quality.get(index);
    }

    private static ArrayList<Jmodel> parseVideo(String html){
        ArrayList<Jmodel> jModels = new ArrayList<>();
        try {
            String regex = "sources:(.*),";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(html);
            if (matcher.find()){
                String source = matcher.group(1).trim();
                JSONArray array = new JSONArray(source);
                List<String> list = new ArrayList<>();
                for (int i=0;i<array.length();i++){
                    String src = array.getString(i);
                    if (!src.endsWith(".m3u8")){
                        list.add(src);
                    }
                }

                for (int i=0;i<list.size();i++){
                    String label = quality(list.size(),i);
                    Jmodel jModel = new Jmodel();
                    jModel.setQuality(label);
                    jModel.setUrl(list.get(i));
                    jModels.add(jModel);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jModels;
    }

    private static String fixURL(String url){
        if (!url.contains("embed-")) {
            final String regex = "co/([^']*)";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                String id = matcher.group(1);
                if (id.contains("/")) {
                    id = id.substring(0, id.lastIndexOf("/"));
                }
                url = getDomainFromURL(url)+"/embed-" + id;
            } else {
                return null;
            }
        }
        return url;
    }
}
