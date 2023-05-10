package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Jresolver.agent;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Utils.JSUnpacker;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CloudVideo {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){
        AndroidNetworking.get(url)
                .setUserAgent(agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Jmodel> jModels = parse(response);
                        if (jModels!=null){
                            onComplete.onTaskCompleted(jModels,false);
                        }else onComplete.onError();
                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });

    }

    private static ArrayList<Jmodel> parse(String response){
        JSUnpacker jsUnpacker = new JSUnpacker(getEvalCode(response));
        if(jsUnpacker.detect()) {
            String src = getSrc(jsUnpacker.unpack());
            if (src!=null && src.length()>0){
                if(!src.contains("http")){
                    src = "https:"+src;
                }
                Jmodel xModel = new Jmodel();
                xModel.setUrl(src);
                xModel.setQuality("Normal");

                ArrayList<Jmodel> xModels = new ArrayList<>();
                xModels.add(xModel);
                return xModels;
            }
        }return null;
    }


    private static String getSrc(String code){
        final String regex = "src: ?\"(.*?)\"";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(code);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
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
}
