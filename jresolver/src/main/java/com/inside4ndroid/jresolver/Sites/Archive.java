package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Jresolver.agent;
import static com.inside4ndroid.jresolver.Utils.Utils.putModel;
import android.os.StrictMode;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Archive {

    public static void fetch(final String urls, final Jresolver.OnTaskCompleted onComplete){

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(urls);
            HttpURLConnection ucon = null;
            ucon = (HttpURLConnection) url.openConnection();
            ucon.setRequestProperty("Referer", urls);
            ucon.setRequestProperty("User-Agent", agent);
            ucon.setInstanceFollowRedirects(false);
            URL secondURL = new URL(ucon.getHeaderField("Location"));
            String finallink = secondURL.toString();

            ArrayList<Jmodel> videoModels = new ArrayList<>();
            putModel(finallink,"Normal", videoModels);
            if (videoModels !=null) {
                if(videoModels.size() == 0){
                    onComplete.onError();
                } else onComplete.onTaskCompleted(videoModels, videoModels.size() > 1);
            }else onComplete.onError();
        } catch (Exception E){
            onComplete.onError();
            E.printStackTrace();
        }
    }
}
