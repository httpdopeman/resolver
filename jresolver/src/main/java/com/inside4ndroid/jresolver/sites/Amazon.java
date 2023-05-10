package com.inside4ndroid.jresolver.sites;

import static com.inside4ndroid.jresolver.Jresolver.agent;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.model.Jmodel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Amazon {

    private static String ShareID;

    public static void fetch(String url, final Jresolver.OnTaskCompleted onComplete){
        ShareID = get_ShareID(url);
        String baseurl = "https://www.amazon.com/drive/v1/shares/"+ShareID+"?shareId="+ShareID+"&resourceVersion=V2&ContentType=JSON&_="+System.currentTimeMillis();
        AndroidNetworking.get(baseurl)
                .setUserAgent(agent)
                .addHeaders("Referer", "https://www.amazon.com/")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        final String regex = "id\":\"(.*?)\"";
                        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                        final Matcher matcher = pattern.matcher(response);
                        if (matcher.find()) {
                            String nodeInfo = matcher.group(1);
                            String data_url = "https://www.amazon.com/drive/v1/nodes/"+nodeInfo+"/children?asset=ALL&limit=1&searchOnFamily=false&tempLink=true&shareId="+ShareID+"&offset=0&resourceVersion=V2&ContentType=JSON&_="+System.currentTimeMillis();
                            AndroidNetworking.get(data_url)
                                    .setUserAgent(agent)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            ArrayList<Jmodel> jModels = parse(response);
                                            if (jModels!=null){
                                                onComplete.onTaskCompleted(jModels,false);
                                            }else onComplete.onError();
                                        }

                                        @Override
                                        public void onError(ANError anError) {
                                            onComplete.onError();
                                        }
                                    });

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        onComplete.onError();
                    }
                });
    }

    private static ArrayList<Jmodel> parse(JSONObject response){
        try {
            JSONArray array = response.getJSONArray("data");
            JSONObject A = array.getJSONObject(0);
            String source = A.getString("tempLink");
            if (source.length()>0) {
                Jmodel jModel = new Jmodel();
                jModel.setUrl(source);
                jModel.setQuality("Normal");

                ArrayList<Jmodel> jModels = new ArrayList<>();
                jModels.add(jModel);
                return jModels;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String get_ShareID(String link){
        String[] seperate = link.split("/");
        return seperate[seperate.length-1];
    }
}
