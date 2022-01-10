package com.inside4ndroid.jresolver;

import android.content.Context;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.inside4ndroid.jresolver.Sites.Amazon;
import com.inside4ndroid.jresolver.Sites.BitTube;
import com.inside4ndroid.jresolver.Sites.DMotion;
import com.inside4ndroid.jresolver.Sites.Dood;
import com.inside4ndroid.jresolver.Sites.FShared;
import com.inside4ndroid.jresolver.Sites.GoUnlimited;
import com.inside4ndroid.jresolver.Sites.MixDrop;
import com.inside4ndroid.jresolver.Sites.StreamSB;
import com.inside4ndroid.jresolver.Sites.StreamTape;
import com.inside4ndroid.jresolver.Sites.VideoBIN;
import com.inside4ndroid.jresolver.Sites.VideoBM;
import com.inside4ndroid.jresolver.Sites.Vudeo;
import com.inside4ndroid.jresolver.Sites.YT;
import com.inside4ndroid.jresolver.Utils.DailyMotionUtils;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Sites.FB;
import com.inside4ndroid.jresolver.Sites.FEmbed;
import com.inside4ndroid.jresolver.Sites.FileRIO;
import com.inside4ndroid.jresolver.Sites.GPhotos;
import com.inside4ndroid.jresolver.Sites.MFire;
import com.inside4ndroid.jresolver.Sites.MP4Upload;
import com.inside4ndroid.jresolver.Sites.OKRU;
import com.inside4ndroid.jresolver.Sites.SendVid;
import com.inside4ndroid.jresolver.Sites.SolidFiles;
import com.inside4ndroid.jresolver.Sites.TW;
import com.inside4ndroid.jresolver.Sites.VK;
import com.inside4ndroid.jresolver.Sites.Vidoza;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.inside4ndroid.jresolver.Utils.FacebookUtils.check_fb_video;

public class Jresolver {

    private Context context;
    private OnTaskCompleted onComplete;
    public static final String agent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.99 Safari/537.36";
    private final String mp4upload = "https?:\\/\\/(www\\.)?(mp4upload)\\.[^\\/,^\\.]{2,}\\/.+";
    private final String filerio = "https?:\\/\\/(www\\.)?(filerio)\\.[^\\/,^\\.]{2,}\\/.+";
    private final String sendvid = "https?:\\/\\/(www\\.)?(sendvid)\\.[^\\/,^\\.]{2,}\\/.+";
    private final String gphoto = "https?:\\/\\/(photos.google.com)\\/(u)?\\/?(\\d)?\\/?(share)\\/.+(key=).+";
    private final String mediafire = "https?:\\/\\/(www\\.)?(mediafire)\\.[^\\/,^\\.]{2,}\\/(file)\\/.+";
    private final String okru = "https?:\\/\\/(www.|m.)?(ok)\\.[^\\/,^\\.]{2,}\\/(video|videoembed)\\/.+";
    private final String vk = "https?:\\/\\/(www\\.)?vk\\.[^\\/,^\\.]{2,}\\/video\\-.+";
    private final String twitter = "http(?:s)?:\\/\\/(?:www\\.)?twitter\\.com\\/([a-zA-Z0-9_]+)";
    private final String youtube = "^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(\\/(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?$";
    private final String solidfiles = "https?:\\/\\/(www\\.)?(solidfiles)\\.[^\\/,^\\.]{2,}\\/(v)\\/.+";
    private final String vidoza = "https?:\\/\\/(www\\.)?(vidoza)\\.[^\\/,^\\.]{2,}.+";
    private final String fembed = "https?:\\/\\/(www\\.)?(fembed|vcdn|feurl|fcdn|embedsito|dutrag|lajkema|fembed-hd)\\.[^\\/,^\\.]{2,}\\/(v|f)\\/.+";
    private final String vidbm = "https?:\\/\\/(vidbam)\\.[^\\/,^\\.]{2,}\\/.+";
    private final String bitTube = "https?:\\/\\/(www\\.)?(bittube\\.video\\/videos)\\/(watch|embed)\\/.+";
    private final String videoBIN = "https?:\\/\\/(videobin\\.co)\\/.+";
    private final String fourShared = "https?:\\/\\/(www\\.)?(4shared\\.com)\\/(video|web\\/embed)\\/.+";
    private final String streamtape = "https?:\\/\\/(www\\.)?(streamtape\\.com)\\/(v)\\/.+";
    private final String vudeo = "https?:\\/\\/(www\\.)?(vudeo\\.net)\\/.+";
    private final String amazon = "https?:\\/\\/(www\\.)?(amazon\\.com)\\/?(clouddrive)\\/+";
    private final String doodstream = "(?://|\\.)(dood(?:stream)?\\.(?:com|watch|to|so|la|ws))/(?:d|e)/([0-9a-zA-Z]+)";
    private final String streamsb = ".+(streamsb|sbplay|sbplay2|sbembed|sbembed1|sbvideo|cloudemb|playersb|tubesb|sbplay1|embedsb|watchsb)\\.(com|net|one|org)/.+";
    private final String mixdrop = ".+(mixdrop)\\.(co|to|sx|bz)\\/.+";
    private final String gounlimited = "https?:\\/\\/(www\\.)?(gounlimited)\\.[^\\/,^\\.]{2,}\\/.+";

    public Jresolver(@NonNull Context context){
        this.context=context;
        AndroidNetworking.initialize(context);
    }

    public void find(String url){
        if (check(mp4upload, url)) {
            MP4Upload.fetch(url,onComplete);
        } else if (check(sendvid, url)) {
            SendVid.fetch(url,onComplete);
        } else if (check(mixdrop, url)) {
            MixDrop.fetch(url,onComplete);
        } else if (check(streamsb, url)) {
            StreamSB.fetch(url,onComplete);
        } else if (check(doodstream, url)) {
            Dood.fetch(url,onComplete);
        } else if (check(amazon, url)) {
            Amazon.fetch(url,onComplete);
        } else if (check(gphoto, url)) {
            GPhotos.fetch(url,onComplete);
        } else if (check_fb_video(url)) {
            FB.fetch(url,onComplete);
        } else if (check(mediafire, url)) {
            MFire.fetch(url,onComplete);
        } else if (check(okru,url)){
            OKRU.fetch(url,onComplete);
        } else if (check(vk,url)){
            VK.fetch(url,onComplete);
        } else if (check(twitter,url)){
            TW.fetch(url,onComplete);
        } else if (check(solidfiles,url)){
            SolidFiles.fetch(url,onComplete);
        } else if (check(vidoza,url)){
            Vidoza.fetch(url,onComplete);
        } else if (check(fembed,url)){
            FEmbed.fetch(url,onComplete);
        } else if (check(filerio,url)){
            FileRIO.fetch(url,onComplete);
        } else if (DailyMotionUtils.getDailyMotionID(url)!=null){
            DMotion.fetch(url,onComplete);
        } else if (check(vidbm,url)){
            VideoBM.fetch(url,onComplete);
        } else if (check(bitTube,url)){
            BitTube.fetch(url,onComplete);
        } else if (check(videoBIN,url)){
            VideoBIN.fetch(url,onComplete);
        } else if (check(fourShared,url)){
            FShared.fetch(url,onComplete);
        }else if (check(streamtape,url)){
            StreamTape.fetch(url,onComplete);
        } else if (check(vudeo,url)) {
            Vudeo.fetch(url, onComplete);
        } else  if (check(gounlimited,url)){
            GoUnlimited.fetch(url,onComplete);
        } else if (check(youtube,url)) {
            YT.fetch(context,url, onComplete);
        } else onComplete.onError();
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(ArrayList<Jmodel> vidURL, boolean multiple_quality);
        void onError();
    }

    public void onFinish(OnTaskCompleted onComplete) {
        this.onComplete = onComplete;
    }

    private boolean check(String regex, String string) {
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }
}
