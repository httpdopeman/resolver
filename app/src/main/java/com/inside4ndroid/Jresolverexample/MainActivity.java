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
    public void vidbm(View view) {letGo(getString(R.string.s_vidbam), getString(R.string.s_vidbam));}
    public void bittube(View view) {letGo(getString(R.string.s_bittube), getString(R.string.s_bittube));}
    public void videobin(View view) {letGo(getString(R.string.s_videobin), getString(R.string.s_videobin));}
    public void fourshared(View view) {letGo(getString(R.string.s_fourshared), getString(R.string.s_fourshared));}
    public void streamtape(View view) {letGo(getString(R.string.s_streamtape), getString(R.string.s_streamtape));}
    public void vudeo(View view) {letGo(getString(R.string.s_vudeo), getString(R.string.s_vudeo));}
    public void amazon(View view) {letGo(getString(R.string.amazon), getString(R.string.amazon));}
    public void doodstream(View view) {letGo(getString(R.string.dood), getString(R.string.dood));}
    public void streamsb(View view) {letGo(getString(R.string.streamsb) , getString(R.string.streamsb));}
    public void mixdrop(View view) {letGo("https://mixdrop.co/e/vnr7den9sl11e8?sub1=https://msubload.com/sub/tom-clancys-without-remorse/tom-clancys-without-remorse.vtt&sub1_label=English", "https://mixdrop.co/e/vnr7den9sl11e8?sub1=https://msubload.com/sub/tom-clancys-without-remorse/tom-clancys-without-remorse.vtt&sub1_label=English");}

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
            checkFileSize(xModel);
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
        Intent intent = new  Intent(this, XPlayer.class);
        intent.putExtra(XPlayer.XPLAYER_URL, xModel.getUrl());
        if (xModel.getCookie()!=null){
            intent.putExtra(XPlayer.XPLAYER_COOKIE,xModel.getCookie());
        }
        intent.putExtra(XPlayer.XPLAYER_REFERER, player_referer);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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

    @SuppressLint("StaticFieldLeak")
    private void checkFileSize(Jmodel xModel){
        new AsyncTask<Void,Void,String>(){

            @Override
            protected String doInBackground(Void... voids) {
                if (xModel.getUrl()!=null) {
                    try {
                        URLConnection connection = new URL(xModel.getUrl()).openConnection();
                        if (xModel.getCookie() != null) {
                            connection.setRequestProperty(getString(R.string.cookie), xModel.getCookie());
                        }
                        connection.connect();
                        return calculateFileSize(connection.getContentLength());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                System.out.println(xModel.getUrl()+R.string.file_size+s+getString(R.string.cookie_z)+xModel.getCookie());
                Toast.makeText(MainActivity.this, getString(R.string.files_size_z)+s, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private String calculateFileSize(long size) {
        if(size <= 0) return getString(R.string.zero);
        final String[] units = new String[] { getString(R.string.b), getString(R.string.kb), getString(R.string.mb), getString(R.string.gb), getString(R.string.tb) };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat(getString(R.string.decimal_format)).format(size/Math.pow(1024, digitGroups)) + getString(R.string.space) + units[digitGroups];
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
