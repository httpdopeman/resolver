package com.inside4ndroid.jresolver.Sites;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import java.util.ArrayList;

import static com.inside4ndroid.jresolver.Jresolver.agent;
import static com.inside4ndroid.jresolver.Utils.FacebookUtils.getFbLink;
import static com.inside4ndroid.jresolver.Utils.Utils.putModel;

public class FB {
    public static void fetch(String url, final Jresolver.OnTaskCompleted onTaskCompleted){
        AndroidNetworking.post("https://fdown.net/download.php")
                .addBodyParameter("URLz", "https://www.facebook.com/video.php?v="+ url)
                .addHeaders("User-agent", agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Jmodel> jModels = new ArrayList<>();
                        String sd = getFbLink(response, false);
                        if (sd!=null){
                            putModel(sd,"SD",jModels);
                        }
                        String hd = getFbLink(response, true);
                        if (hd!=null) {
                            putModel(hd, "HD", jModels);
                        }
                        if (jModels.isEmpty()){
                            onTaskCompleted.onError();
                        }else onTaskCompleted.onTaskCompleted(jModels,true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        onTaskCompleted.onError();
                    }
                });
    }
}
