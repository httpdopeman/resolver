package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Utils.Utils.getDomainFromURL;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Utils.Utils;
import org.json.JSONObject;
import java.util.ArrayList;

public class StreamLare {

    private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0";

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete) {

        url = fixURL(url);
        String api_surl = getDomainFromURL(url)+"/api/video/stream/get";

        AndroidNetworking.post(api_surl)
                .addHeaders("User-Agent", userAgent)
                .addHeaders("Referer", url)
                .addHeaders("X-Requested-With", "XMLHttpRequest")
                .addBodyParameter("id", Utils.getID(url))
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject json = new JSONObject(response);
                            JSONObject obj  = json.getJSONObject("result");
                            try {
                                JSONObject objorig  = obj.getJSONObject("Original");
                                String mediaUrl = objorig.getString("file");
                                ArrayList<Jmodel> xModels = new ArrayList<>();
                                Utils.putModel(mediaUrl, "normal", xModels);
                                onComplete.onTaskCompleted(xModels, false);
                            } catch (Exception getOther){
                                String mediaUrl = obj.getString("file");
                                ArrayList<Jmodel> xModels = new ArrayList<>();
                                Utils.putModel(mediaUrl, "normal", xModels);
                                onComplete.onTaskCompleted(xModels, false);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            onComplete.onError();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }

    private static String fixURL(String uri){
        return Utils.getDomainFromURL(uri)+"/e/"+uri.substring(uri.lastIndexOf("/") + 1);
    }
}
