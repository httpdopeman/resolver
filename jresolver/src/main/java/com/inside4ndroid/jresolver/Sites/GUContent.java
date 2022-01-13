package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Utils.Utils.putModel;

import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import java.util.ArrayList;

public class GUContent {

    private static ArrayList<Jmodel> videoModels;

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){

        videoModels = new ArrayList<>();
        putModel(url,"Normal", videoModels);
        if (videoModels !=null) {
            if(videoModels.size() == 0){
                onComplete.onError();
            } else {
                onComplete.onTaskCompleted(videoModels,false);
            }
        }else onComplete.onError();
    }
}
