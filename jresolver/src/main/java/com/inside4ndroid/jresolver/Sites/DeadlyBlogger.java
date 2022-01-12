package com.inside4ndroid.jresolver.Sites;

import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import java.util.ArrayList;

public class DeadlyBlogger {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){

        if(url.contains(".mp4")){
            Jmodel jModel = new Jmodel();
            jModel.setUrl(url);
            jModel.setQuality("Normal");
            ArrayList<Jmodel> xModels = new ArrayList<>();
            xModels.add(jModel);
            onComplete.onTaskCompleted(xModels,false);
        } else if(url.contains(".html")){
            Jmodel xModel = new Jmodel();
            xModel.setUrl(url.replace(".html" , ".webm"));
            xModel.setQuality("Normal");
            ArrayList<Jmodel> xModels = new ArrayList<>();
            xModels.add(xModel);
            onComplete.onTaskCompleted(xModels,false);
        } else {
            onComplete.onError();
        }
    }
}
