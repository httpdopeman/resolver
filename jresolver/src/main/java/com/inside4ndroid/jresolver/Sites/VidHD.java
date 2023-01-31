package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Jresolver.agent;
import static com.inside4ndroid.jresolver.Utils.Utils.getDomainFromURL;
import static com.inside4ndroid.jresolver.Utils.Utils.getID;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Utils.JSUnpacker;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VidHD {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){

        url = fixURL(url);

        AndroidNetworking.get(url)
                .setUserAgent(agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Jmodel> jModels = parse(response);

                        if(jModels == null){
                            onComplete.onError();
                        } else if(jModels.size() > 1){
                            onComplete.onTaskCompleted(jModels, true);
                        } else {
                            onComplete.onTaskCompleted(jModels,false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });

    }

    private static ArrayList<Jmodel> parse(String response){
        ArrayList<Jmodel> xModels = new ArrayList<>();
        JSUnpacker jsUnpacker = new JSUnpacker(getEvalCode(response));
        if(jsUnpacker.detect()) {
            String src = jsUnpacker.unpack();
            final String regex = "file:\"(.*?)\",.*?\"(.*?)\"";
            final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(src);
            while (matcher.find()) {
                Jmodel xModel = new Jmodel();
                xModel.setUrl(matcher.group(1));
                xModel.setQuality(matcher.group(2));
                xModels.add(xModel);
            }
            return xModels;
        } else {
            return null;
        }
    }

    private static String getEvalCode(String html){
        final String regex = ">eval(.*)";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    private static String fixURL(String url){
        if (!url.contains("embed-")) {
            String domain = getDomainFromURL(url);
            String id = getID(url);
            return domain+"/embed-"+id+".html";
        } else {
            return url;
        }
    }
}
