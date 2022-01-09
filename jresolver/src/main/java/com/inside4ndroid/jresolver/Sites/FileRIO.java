package com.inside4ndroid.jresolver.Sites;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.inside4ndroid.jresolver.Utils.Utils.getDomainFromURL;
import static com.inside4ndroid.jresolver.Utils.Utils.putModel;

import android.util.Log;

public class FileRIO {
    public static void fetch(String url, final Jresolver.OnTaskCompleted onTaskCompleted){
        Log.d("THE URL ", url);
        url = fixURL(url);
        AndroidNetworking.get(url)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        String src = getSrc(response);
                        Log.d("THE FULL SOURCE", src);
                        if (src!=null){
                            ArrayList<Jmodel> jModels = new ArrayList<>();
                            putModel(src,"Normal",jModels);
                            onTaskCompleted.onTaskCompleted(jModels,false);
                        }else onTaskCompleted.onError();
                    }

                    @Override
                    public void onError(ANError anError) {
                        onTaskCompleted.onError();
                    }
                });
    }

    private static String fixURL(String url){
        if (!url.contains("embed-")) {
            final String regex = "in\\/([^']*)";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                String id = matcher.group(1);
                if (id.contains("/")) {
                    id = id.substring(0, id.lastIndexOf("/"));
                }
                url = getDomainFromURL(url)+"/embed-" + id + ".html";
            } else {
                return null;
            }
        }
        return url;
    }

    private static String getSrc(String response){
        final String regex = "<source src=\"(.*?)\"";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
