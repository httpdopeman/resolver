package com.inside4ndroid.jresolver;

import android.content.Context;
import androidx.annotation.NonNull;
import com.androidnetworking.AndroidNetworking;
import com.inside4ndroid.jresolver.Sites.Amazon;
import com.inside4ndroid.jresolver.Sites.Aparat;
import com.inside4ndroid.jresolver.Sites.Archive;
import com.inside4ndroid.jresolver.Sites.BitChute;
import com.inside4ndroid.jresolver.Sites.Brighteon;
import com.inside4ndroid.jresolver.Sites.CloudVideo;
import com.inside4ndroid.jresolver.Sites.DMotion;
import com.inside4ndroid.jresolver.Sites.DeadlyBlogger;
import com.inside4ndroid.jresolver.Sites.Dood;
import com.inside4ndroid.jresolver.Sites.EplayVid;
import com.inside4ndroid.jresolver.Sites.FShared;
import com.inside4ndroid.jresolver.Sites.GUContent;
import com.inside4ndroid.jresolver.Sites.HDVid;
import com.inside4ndroid.jresolver.Sites.Midian;
import com.inside4ndroid.jresolver.Sites.MixDrop;
import com.inside4ndroid.jresolver.Sites.StreamHide;
import com.inside4ndroid.jresolver.Sites.StreamLare;
import com.inside4ndroid.jresolver.Sites.StreamSB;
import com.inside4ndroid.jresolver.Sites.StreamTape;
import com.inside4ndroid.jresolver.Sites.StreamVid;
import com.inside4ndroid.jresolver.Sites.Upstream;
import com.inside4ndroid.jresolver.Sites.VidMoly;
import com.inside4ndroid.jresolver.Sites.VideoBM;
import com.inside4ndroid.jresolver.Sites.VoeSX;
import com.inside4ndroid.jresolver.Sites.Vudeo;
import com.inside4ndroid.jresolver.Sites.YT;
import com.inside4ndroid.jresolver.Sites.YodBox;
import com.inside4ndroid.jresolver.Utils.DailyMotionUtils;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.jresolver.Sites.FileRIO;
import com.inside4ndroid.jresolver.Sites.GPhotos;
import com.inside4ndroid.jresolver.Sites.MFire;
import com.inside4ndroid.jresolver.Sites.MP4Upload;
import com.inside4ndroid.jresolver.Sites.OKRU;
import com.inside4ndroid.jresolver.Sites.SendVid;
import com.inside4ndroid.jresolver.Sites.VK;
import com.inside4ndroid.jresolver.Sites.Vidoza;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jresolver {

    private Context context;
    private OnTaskCompleted onComplete;
    public static final String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36";
    private final String mp4upload = "(?://|\\.)(mp4upload\\.com)/(?:embed-)?([0-9a-zA-Z]+)";
    private final String filerio = "(?://|\\.)(filerio\\.in)/(?:embed-)?([0-9a-zA-Z]+)";
    private final String sendvid = "(?://|\\.)(sendvid\\.com)/(?:embed/)?([0-9a-zA-Z]+)";
    private final String gphoto = "https?:\\/\\/(photos.google.com)\\/(u)?\\/?(\\d)?\\/?(share)\\/.+(key=).+";
    private final String mediafire = "https?:\\/\\/(www\\.)?(mediafire)\\.[^\\/,^\\.]{2,}\\/(file)\\/.+";
    private final String okru = "https?:\\/\\/(www.|m.)?(ok)\\.[^\\/,^\\.]{2,}\\/(video|videoembed)\\/.+";
    private final String vk = "https?:\\/\\/(www\\.)?vk\\.[^\\/,^\\.]{2,}\\/video\\-.+";
    private final String youtube = "^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(\\/(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?$";
    private final String vidoza = "(?://|\\.)(vidoza\\.(?:net|co))/(?:embed-)?([0-9a-zA-Z]+)";
    private final String vidbm = "(?://|\\.)((?:v[aie]d[bp][aoe]?m|myvii?d|v[aei]{1,2}dshar[er]?)\\.(?:com|net|org|xyz))(?::\\d+)?/(?:embed[/-])?([A-Za-z0-9]+)";
    private final String fourShared = "https?:\\/\\/(www\\.)?(4shared\\.com)\\/(video|web\\/embed)\\/.+";
    private final String streamtape = "(?://|\\.)(s(?:tr)?(?:eam|have)?(?:ta?p?e?|cloud|adblock(?:plus|er))\\.(?:com|cloud|net|pe|site|link|cc|online|fun|cash|to|xyz))/(?:e|v)/([0-9a-zA-Z]+)";
    private final String vudeo = "https?:\\/\\/(www\\.)?(vudeo\\.net)\\/.+";
    private final String amazon = "https?:\\/\\/(www\\.)?(amazon\\.com)\\/?(clouddrive)\\/+";
    public static String doodstream = "(?://|\\.)(do*d(?:stream)?\\.(?:com?|watch|to|s[ho]|cx|la|w[sf]|pm|re|yt))/(?:d|e)/([0-9a-zA-Z]+)";
    private final String streamsb = "(?://|\\.)((?:view|watch|embed(?:tv)?|tube|player|cloudemb|japopav|javplaya|p1ayerjavseen|gomovizplay|stream(?:ovies)?|vidmovie)?s{0,2}b?(?:embed\\d?|play\\d?|video|fast|full|streams{0,3}|the|speed|l?anh|tvmshow|longvu|arslanrocky|chill|rity|hight|brisk|face|lvturbo|net|one|asian|ani)?\\.(?:com|net|org|one|tv|xyz|fun|pro))/(?:embed[-/]|e/|play/|d/|sup/)?([0-9a-zA-Z]+)";
    private final String mixdrop = "(?://|\\.)(mixdro?p\\.(?:c[ho]|to|sx|bz|gl|club))/(?:f|e)/(\\w+)";
    private final String aparat = ".+(aparat\\.com/v/).+";
    private final String archive = ".+(archive)\\.(org)\\/.+";
    private final String bitchute = ".+(bitchute\\.com)/(?:video|embed).+";
    private final String brighteon = ".+(brighteon\\.com).+";
    private final String deadlyblogger = ".+(deadlyblogger\\.com).+";
    private final String googleusercontent = ".+(googleusercontent\\.com).+";
    private final String hdvid = "(?://|\\.)((?:hdvid|vidhdthe|hdthevid)\\.(?:tv|fun|online|website))/(?:embed-)?([0-9a-zA-Z]+)";
    private final String voesx = ".+(voe\\.sx|voe-unblock\\.com).+";
    private final String eplayvid = ".+(eplayvid)\\.(com|net)/.+";
    private final String vidmoly = ".+(vidmoly)\\.(me)/.+";
    private final String midian =  ".+(midian\\.appboxes)\\.(co)/.+";
    private final String upstream = ".+(upstream)\\.(to)/.+";
    private final String yodbox = "(?://|\\.)(you?dboo?x\\.(?:com|net|org))/(?:embed-)?(\\w+)";
    private final String cloudvideo = ".+(cloudvideo)\\.(tv)/.+";
    private final String streamlare = "(?://|\\.)((?:streamlare|sl(?:maxed|tube|watch))\\.(?:com?|org))/(?:e|v)/([0-9A-Za-z]+)";
    private final String streamvid = "(?://|\\.)(streamvid\\.net)/(?:embed-)?([0-9a-zA-Z]+)";
    private final String streamhide = "(?://|\\.)((?:moviesm4u|(?:stream|gucci|mov)hide)\\.(?:to|com|pro))/(?:e|d|w)/([0-9a-zA-Z]+)";

    public Jresolver(@NonNull Context context){
        this.context=context;
        AndroidNetworking.initialize(context);
    }

    public void find(String url){
        if (check(mp4upload, url)) {
            MP4Upload.fetch(url,onComplete);
        } else if (check(vidmoly,url)){
            VidMoly.fetch(url,onComplete);
        } else if (check(streamhide,url)){
            StreamHide.fetch(url,onComplete);
        } else if (check(streamvid,url)){
            StreamVid.fetch(url,onComplete);
        } else if (check(streamlare,url)){
            StreamLare.fetch(url,onComplete);
        } else if (check(cloudvideo,url)){
            CloudVideo.fetch(url,onComplete);
        } else if (check(yodbox,url)){
            YodBox.fetch(url,onComplete);
        } else if (check(upstream,url)){
            Upstream.fetch(url,onComplete);
        } else if (check(midian,url)){
            Midian.fetch(url,onComplete);
        } else if (check(eplayvid,url)){
            EplayVid.fetch(url,onComplete);
        }  else if (check(voesx, url)) {
            VoeSX.fetch(url, onComplete);
        } else if (check(hdvid, url)) {
            HDVid.fetch(url,onComplete);
        }else if (check(googleusercontent, url)) {
            GUContent.fetch(url,onComplete);
        } else if (check(sendvid, url)) {
            SendVid.fetch(url,onComplete);
        } else if (check(deadlyblogger, url)) {
            DeadlyBlogger.fetch(url,onComplete);
        } else if (check(brighteon, url)) {
            Brighteon.fetch(url,onComplete);
        } else if (check(bitchute, url)) {
            BitChute.fetch(url,onComplete);
        } else if (check(archive, url)) {
            Archive.fetch(url,onComplete);
        } else if (check(aparat, url)) {
            Aparat.fetch(url,onComplete);
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
        } else if (check(mediafire, url)) {
            MFire.fetch(context,url,onComplete);
        } else if (check(okru,url)){
            OKRU.fetch(url,onComplete);
        } else if (check(vk,url)){
            VK.fetch(url,onComplete);
        } else if (check(vidoza,url)){
            Vidoza.fetch(url,onComplete);
        } else if (check(filerio,url)){
            FileRIO.fetch(url,onComplete);
        } else if (DailyMotionUtils.getDailyMotionID(url)!=null){
            DMotion.fetch(url,onComplete);
        } else if (check(vidbm,url)){
            VideoBM.fetch(url,onComplete);
        } else if (check(fourShared,url)){
            FShared.fetch(url,onComplete);
        } else if (check(streamtape,url)){
            if(url.contains("shavetape.cash")){
                url = url.replace("shavetape.cash", "streamtape.to");
                StreamTape.fetch(url,onComplete);
            } else {
                StreamTape.fetch(url,onComplete);
            }

        } else if (check(vudeo,url)) {
            Vudeo.fetch(url, onComplete);
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
