package com.inside4ndroid.Jresolverexample;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import com.inside4ndroid.xplayer.XPlayer;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {
    Jresolver Jresolver;
    ProgressDialog progressDialog;
    String org;
    EditText edit_query;
    String player_referer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        Jresolver = new Jresolver(this);

        doTrustToCertificates();

        Jresolver.onFinish(new Jresolver.OnTaskCompleted() {

            @Override
            public void onTaskCompleted(ArrayList<Jmodel> vidURL, boolean multiple_quality) {
                progressDialog.dismiss();
                if (multiple_quality){
                    if (vidURL!=null) {
                        multipleQualityDialog(vidURL);
                    }else done(null);
                }else {
                   done(vidURL.get(0));
                }
            }

            @Override
            public void onError() {
                progressDialog.dismiss();
                done(null);
            }
        });

        edit_query = findViewById(R.id.edit_query);

    }

    private void letGo(String url, String referer) {
        org = url;
        if (checkInternet()) {
            progressDialog.show();
            player_referer = referer;
            Jresolver.find(url);
        }
    }

    public void streamvid(View view) {letGo("https://streamvid.net/wdw5ru5kvian", "https://streamvid.net/wdw5ru5kvian");}
    public void mp4upload(View view) {letGo(getString(R.string.s_mp4upload), getString(R.string.s_mp4upload));}
    public void gphotos(View view) {letGo(getString(R.string.s_gphotos), getString(R.string.s_gphotos));}
    public void fb(View view) {letGo(getString(R.string.s_facebook), getString(R.string.s_facebook));}
    public void mediafire(View view) {letGo(getString(R.string.s_mediafire), getString(R.string.s_mediafire));}
    public void okru(View view) {letGo(getString(R.string.s_okru), getString(R.string.s_okru));}
    public void vk(View view) {letGo(getString(R.string.s_vkdotcom), getString(R.string.s_vkdotcom));}
    public void twitter(View view) {letGo(getString(R.string.s_twitter), getString(R.string.s_twitter));}
    public void youtube(View view) {letGo(getString(R.string.s_youtube), getString(R.string.s_youtube));}
    public void solidfiles(View view) {letGo(getString(R.string.s_solidfiles), getString(R.string.s_solidfiles));}
    public void vidozafiles(View view) {letGo(getString(R.string.s_vidoza), getString(R.string.s_vidoza));}
    public void sendvid(View view) {letGo(getString(R.string.s_sendvid), getString(R.string.s_sendvid));}
    public void fEmbed(View view) {letGo(getString(R.string.s_fembed), getString(R.string.s_fembed));}
    public void filerio(View view){letGo(getString(R.string.s_filerio), getString(R.string.s_filerio));}
    public void dailymotion(View view) {letGo(getString(R.string.s_dailymotion), getString(R.string.s_dailymotion));}
    public void vidbm(View view) {letGo(getString(R.string.s_vidbam), "https://vidbam.org/");}
    public void videobin(View view) {letGo(getString(R.string.s_videobin), getString(R.string.s_videobin));}
    public void fourshared(View view) {letGo(getString(R.string.s_fourshared), getString(R.string.s_fourshared));}
    public void streamtape(View view) {letGo(getString(R.string.s_streamtape), getString(R.string.s_streamtape));}
    public void vudeo(View view) {letGo(getString(R.string.s_vudeo), getString(R.string.s_vudeo));}
    public void amazon(View view) {letGo(getString(R.string.amazon), getString(R.string.amazon));}
    public void doodstream(View view) {letGo(getString(R.string.dood), getString(R.string.dood));}
    public void streamsb(View view) {letGo(getString(R.string.streamsb) , "https://sbspeed.com/");}
    public void mixdrop(View view) {letGo(getString(R.string.mdrop), getString(R.string.mdrop));}
    public void gounlimited(View view) {letGo(getString(R.string.gounlimiteduri), getString(R.string.gounlimiteduri));}
    public void aparat(View view) {letGo(getString(R.string.aparat_uri), getString(R.string.aparat_uri));}
    public void archive(View view) {letGo(getString(R.string.archive_uri), getString(R.string.archive_uri));}
    public void bitchute(View view) {letGo(getString(R.string.bitchute), getString(R.string.bitchute));}
    public void brighteon(View view) {letGo(getString(R.string.brighteon), getString(R.string.brighteon));}
    public void deadlyblogger(View view) {letGo(getString(R.string.deadlyblogger),getString(R.string.deadlyblogger));}
    public void fansubs(View view) {letGo(getString(R.string.fansubs),getString(R.string.fansubs));}
    public void diasfem(View view) {letGo(getString(R.string.diasfem),getString(R.string.diasfem));}
    public void gdstream(View view) {letGo(getString(R.string.gdstream),getString(R.string.gdstream));}
    public void googleusercontent(View view) {letGo(getString(R.string.gusercontent), getString(R.string.google));}
    public void hdvid(View view) {letGo(getString(R.string.hdvid),getString(R.string.hdvid));}
    public void voesx(View view) {letGo(getString(R.string.voesx), getString(R.string.voesx));}
    public void eplayvid(View view) {letGo(getString(R.string.eplayvid), getString(R.string.eplayvid));}
    public void vidmoly(View view) {letGo(getString(R.string.vidmoly), getString(R.string.vidmolyref));}
    public void midian(View view) {letGo(getString(R.string.midian), getString(R.string.google));}
    public void upstream(View view) {letGo(getString(R.string.upstream), getString(R.string.upstream));}
    public void yodbox(View view) {letGo(getString(R.string.yodbox), getString(R.string.yodbox));}
    public void evoload(View view) {letGo(getString(R.string.evoload), getString(R.string.evoload_ref));}
    public void cloudvideo(View view) {letGo("https://cloudvideo.tv/uw3a35m590w6", "https://cloudvideo.tv/");}
    public void streamlare(View view) {letGo("https://slmaxed.com/v/9aNdbzk2JGOD8JjB", "https://sltube.org/v/padWzjBP7Akz9NyA");}
    public void streamz(View view) {letGo("https://streamz.ws/fc3l1ZXk4anp0a2pmWFhY", "https://streamz.ws/fc3l1ZXk4anp0a2pmWFhY");}

    public void linkbox(View view) {letGo("https://www.linkbox.to/file/fp3al40000bu?ch=202093565", "https://www.linkbox.to/file/fp3al40000bu?ch=202093565");}

    public void vidhd(View view) {letGo("https://vidhd.fun/embed-3fbzrogstpb6.html","https://vidhd.fun/embed-3fbzrogstpb6.html");}

    public boolean checkInternet() {
        boolean what;
        CheckInternet checkNet = new CheckInternet(this);
        if (checkNet.isInternetOn()) {
            what = true;
        } else {
            what = false;
            Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
        return what;
    }

    private void done(Jmodel xModel){
        String url = null;
        if (xModel!=null) {
            url = xModel.getUrl();
            //checkFileSize(xModel);
        }
        MaterialStyledDialog.Builder builder = new MaterialStyledDialog.Builder(this);
        if (url!=null) {
            builder.setTitle(getString(R.string.congrats))
                    .setDescription(R.string.can_stream)
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setIcon(R.drawable.right)
                    .withDialogAnimation(true)
                    .setPositiveText(R.string.stream)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            watchVideo(xModel);
                        }
                    })
                    .setNeutralText(R.string.copy)
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            clipboardManager.setText(xModel.getUrl());
                            if (clipboardManager.hasText()){
                                Toast.makeText(MainActivity.this, getString(R.string.copied), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            builder.setTitle(getString(R.string.sorry))
                    .setDescription(R.string.error)
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setIcon(R.drawable.wrong)
                    .withDialogAnimation(true)
                    .setPositiveText(R.string.ok)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    });
        }
        MaterialStyledDialog dialog = builder.build();
        dialog.show();
    }

    private void watchVideo(Jmodel xModel){

        String uri;
        String test = xModel.getUrl().substring(xModel.getUrl().length() - 1);

        if(test.equals("\"")){
            uri = removeLastChar(xModel.getUrl());
            Log.d("THE URL EDIT", uri);
        } else {
            uri = xModel.getUrl();
            Log.d("THE URI ", uri);
        }

        Intent intent = new  Intent(this, XPlayer.class);
        intent.putExtra(XPlayer.XPLAYER_URL, uri);
        if (xModel.getCookie()!=null){
            intent.putExtra(XPlayer.XPLAYER_COOKIE,xModel.getCookie());
        }
        intent.putExtra(XPlayer.XPLAYER_REFERER, player_referer);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }

    public void dev(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.twitter_profile)));
            startActivity(intent);
        } catch (Exception A){
            Toast.makeText(MainActivity.this, getString(R.string.twitter_toast), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            showAbout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetch(View view) {
        String url = edit_query.getText().toString();
        letGo(url, url);
    }

    public void showAbout() {
        String message = getString(R.string.about);

        View view = getLayoutInflater().inflate(R.layout.about, null);
        TextView textView = view.findViewById(R.id.message);
        textView.setText(message);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.about_title)
                .setView(view)
                .setPositiveButton(R.string.ok_x, null);
        builder.show();
    }


    private void multipleQualityDialog(ArrayList<Jmodel> model) {
        CharSequence[] name = new CharSequence[model.size()];

        for (int i = 0; i < model.size(); i++) {
            name[i] = model.get(i).getQuality();
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.quality)
                .setItems(name, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        done(model.get(which));
                    }
                })
                .setPositiveButton(R.string.ok_z, null);
        builder.show();
    }

    public void doTrustToCertificates() {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        //No need to implement.
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        //No need to implement.
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance(getString(R.string.ssl));
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
