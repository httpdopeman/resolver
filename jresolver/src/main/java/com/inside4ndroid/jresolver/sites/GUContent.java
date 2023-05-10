package com.inside4ndroid.jresolver.sites;

import static com.inside4ndroid.jresolver.utils.Utils.putModel;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.model.Jmodel;
import java.util.ArrayList;

public class GUContent {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){

        ArrayList<Jmodel> videoModels = new ArrayList<>();
        putModel(url,"Normal", videoModels);
        if(videoModels.size() == 0){
            onComplete.onError();
        } else {
            onComplete.onTaskCompleted(videoModels,false);
        }
    }
}
