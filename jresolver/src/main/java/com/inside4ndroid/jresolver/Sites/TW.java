package com.inside4ndroid.jresolver.Sites;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;

import static com.inside4ndroid.jresolver.Utils.Utils.sortMe;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TW {
    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){
        AndroidNetworking.post("https://twdown.net/download.php")
                .addBodyParameter("URL", url)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        onComplete.onTaskCompleted(sortMe(fetch(response)),true);
                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }

    public static ArrayList<Jmodel> fetch(String response){
        Document doc = Jsoup.parse(response);
        try{
            Elements elements = doc.getElementsByTag("tbody").get(0).getElementsByTag("tr");
            ArrayList<Jmodel> models = new ArrayList<>();
            for (int i=0;i<elements.size();i++) {
                Element items = elements.get(i);
                String url = getUrl(items);
                String size = getSize(items);
                if (url!=null && size!=null){
                    Jmodel jModel = new Jmodel();
                    jModel.setQuality(size);
                    jModel.setUrl(url);
                    models.add(jModel);
                }
            }
            return models;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private static String getSize(Element input){
        Elements elements = input.getElementsByTag("td");
        for (int i=0;i<elements.size();i++){
            String s = elements.get(i).html();
            if (!s.startsWith("<") && s.contains("x")){
                if (s.contains(" ")){
                    s = s.replace(" ","");
                }
                return s;
            }
        }
        return null;
    }

    private static String getUrl(Element data){
        String regex = "preview_video\\('(.*)'";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(data.html());

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
