package com.inside4ndroid.jresolver.sites;

import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.model.Jmodel;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MFire {

    public static void fetch(Context context, String url, final Jresolver.OnTaskCompleted onTaskCompleted) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements mfire = document.getElementsByClass("download_link");
        String finalurl = document.getElementsByClass("input popsok").attr("href");

        ArrayList<Jmodel>jModels = new ArrayList<>();
        Jmodel jmodel = new Jmodel();
        jmodel.setUrl(finalurl);
        jmodel.setQuality("Normal");
        onTaskCompleted.onTaskCompleted(jModels,false);
    }



}
