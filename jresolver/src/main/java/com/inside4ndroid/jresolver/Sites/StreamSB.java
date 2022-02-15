package com.inside4ndroid.jresolver.Sites;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Utils.M3UItem;
import com.inside4ndroid.jresolver.Utils.M3UParser;
import com.inside4ndroid.jresolver.Utils.M3UPlaylist;
import com.inside4ndroid.jresolver.Utils.Utils;
import com.inside4ndroid.jresolver.callbacks.NetworkCallback;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okio.Utf8;

public class StreamSB {

    private static String UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4136.7 Safari/537.36";;
    private static String HOST;
    private static String MEDIAID;
    private static String B64DOMAIN;
    public static void fetch(final String url, final Jresolver.OnTaskCompleted onComplete) {

        MEDIAID = Utils.getID(url);
        HOST = Utils.getDomainFromURL(url);
        final String Nurl = generateUrl();

        AndroidNetworking.get(Nurl)
                .setUserAgent(UserAgent)
                .addHeaders("Referer", url)
                .addHeaders("watchsb", "streamsb")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("stream_data");
                            String file = obj.getString("file");
                            AndroidNetworking.get(file)
                                    .setUserAgent(UserAgent)
                                    .addHeaders("Referer", url)
                                    .addHeaders("watchsb", "streamsb")
                                    .build()
                                    .getAsString(new StringRequestListener() {
                                        @Override
                                        public void onResponse(String response) {
                                            System.out.print(response);
                                            try {
                                                M3UPlaylist plist = new M3UParser().parseFile(response);
                                                ArrayList<M3UItem> array = plist.getPlaylistItems();
                                                ArrayList<Jmodel> jModels = new ArrayList<>();
                                                for (int i = 0; i < array.size(); i++) {
                                                    M3UItem t = array.get(i);
                                                    Jmodel jModel = new Jmodel();
                                                    jModel.setUrl(t.getItemUrl());
                                                    jModel.setQuality(t.getItemName());
                                                    jModels.add(jModel);
                                                }
                                                if(jModels.size()>1){
                                                    onComplete.onTaskCompleted(jModels,true);
                                                } else {
                                                    onComplete.onTaskCompleted(jModels,false);
                                                }
                                            } catch (FileNotFoundException e) {
                                                onComplete.onError();
                                            }
                                        }

                                        @Override
                                        public void onError(ANError anError) {
                                            onComplete.onError();
                                        }
                                    });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }
                });

    }

    private static String generateUrl(){
        String generateLink1 = makeid()+"||"+MEDIAID+"||"+makeid()+"||streamsb";
        String L1binary = asciiTObinary(generateLink1);
        String L1hex = binaryTOhex(L1binary);
        String partial = makeid()+"||"+makeid()+"||"+makeid()+"||streamsb";
        String P1binary = asciiTObinary(partial);
        String P1hex = binaryTOhex(P1binary);
        String LastPart = makeid()+"||"+P1hex+"||"+makeid()+"||streamsb";
        String LastPart1 = asciiTObinary(LastPart);
        String L2hex = binaryTOhex(LastPart1);

        return HOST+"/sources40/"+L1hex+"/"+L2hex;
    }

    private static String makeid() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    private static String asciiTObinary(String s){
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return String.valueOf(binary);
    }

    private static String binaryTOhex(String binary) {
        return new BigInteger(binary, 2).toString(16);
    }
}
