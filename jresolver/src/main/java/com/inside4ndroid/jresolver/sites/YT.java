package com.inside4ndroid.jresolver.sites;

import android.content.Context;
import android.util.SparseArray;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.model.Jmodel;
import com.inside4ndroid.jresolver.utils.Utils;
import com.inside4ndroid.jresolver.yt.VideoMeta;
import com.inside4ndroid.jresolver.yt.YouTubeExtractor;
import com.inside4ndroid.jresolver.yt.YtFile;
import java.util.ArrayList;

public class YT {

    public static void fetch(Context ctx, String url, final Jresolver.OnTaskCompleted onTaskCompleted){
        new YouTubeExtractor(ctx) {

            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {
                    try {
                        ArrayList<Jmodel> jModels = new ArrayList<>();
                        try {
                            int itag = 22;
                            Utils.putModel(ytFiles.get(itag).getUrl(),"Normal",jModels);
                            onTaskCompleted.onTaskCompleted(jModels, false);
                        } catch (Exception A){
                            int itag = 18;
                            Utils.putModel(ytFiles.get(itag).getUrl(),"Normal",jModels);
                            onTaskCompleted.onTaskCompleted(jModels, false);
                        }
                    } catch (Exception Error){
                        onTaskCompleted.onError();
                    }
                } else {
                    onTaskCompleted.onError();
                }
            }
        }.extract(url);
    }
}
