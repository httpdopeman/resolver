package com.inside4ndroid.jresolver.Sites;

import static com.inside4ndroid.jresolver.Utils.Utils.putModel;
import static com.inside4ndroid.jresolver.Utils.Utils.sortMe;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class FanSubs {

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){
        AndroidNetworking.get(url)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Jmodel> models = new ArrayList<>();
                        Document document = Jsoup.parse(response);
                        if (document.html().contains("<source")){
                            Elements element = document.getElementsByTag("source");
                            for (int i=0;i<element.size();i++){
                                Element temp = element.get(i);
                                if (temp.hasAttr("src")) {
                                    String url = temp.attr("src");
                                    putModel(url, temp.attr("label"), models);
                                }
                            }
                        }
                        if (models.size()!=0){
                            onComplete.onTaskCompleted(sortMe(models),true);
                        }else onComplete.onError();
                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }
}
