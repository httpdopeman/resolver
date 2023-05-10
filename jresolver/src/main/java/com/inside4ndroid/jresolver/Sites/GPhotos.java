package com.inside4ndroid.jresolver.Sites;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Utils.GPhotosUtils;
import java.util.ArrayList;

public class GPhotos {
    public static void fetch(String url, final Jresolver.OnTaskCompleted onTaskCompleted){
        AndroidNetworking.get(url)
                .setUserAgent(Jresolver.agent)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Jmodel> jModels = GPhotosUtils.getGPhotoLink(response);
                        onTaskCompleted.onTaskCompleted(jModels,true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        onTaskCompleted.onError();
                    }
                });
    }
}
