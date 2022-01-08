package com.inside4ndroid.jresolver.Utils;

import com.inside4ndroid.jresolver.Model.Jmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static void putModel(String url, String quality, ArrayList<Jmodel> model){
        for (Jmodel x:model){
            if (x.getQuality().equalsIgnoreCase(quality)){
                return;
            }
        }
        if (url!=null && quality!=null) {
            Jmodel jModel = new Jmodel();
            jModel.setUrl(url);
            jModel.setQuality(quality);
            model.add(jModel);
        }
    }

    public static ArrayList<Jmodel> sortMe(ArrayList<Jmodel> x){
        if (x!=null) {
            if (x.size() > 0) {
                ArrayList<Jmodel> result = new ArrayList<>();
                for (Jmodel t : x) {
                    if (startWithNumber(t.getQuality()) || t.getQuality().isEmpty()) {  // with this modificaction it is included those with quality field is empty. EX. openload
                        result.add(t);
                    }
                }
                Collections.sort(result, Collections.reverseOrder());
                return result;
            }
        }
        return null;
    }

    private static boolean startWithNumber(String string){
        final String regex ="^[0-9][A-Za-z0-9-\\s,]*$"; // start with number and can contain space or comma ( 480p , ENG)
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string);
        return  matcher.find();
    }

    public static String getDomainFromURL(String url){
        String regex = "^(?:https?:\\/\\/)?(?:[^@\\n]+@)?(?:www\\.)?([^:\\/\\n?]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }
}
