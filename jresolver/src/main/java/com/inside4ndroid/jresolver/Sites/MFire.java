package com.inside4ndroid.jresolver.Sites;

import com.inside4ndroid.jresolver.Jresolver;
import com.inside4ndroid.jresolver.Model.Jmodel;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.inside4ndroid.jresolver.Utils.Utils.putModel;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MFire {

    private static WebView webView;
    private static Handler mHandler = new Handler();
    private static boolean stopMMHandler = false;
    private static Jresolver.OnTaskCompleted onTaskCompleted;

    public static void fetch(Context context, String url, final Jresolver.OnTaskCompleted onTaskCompleted) {
        webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().getAllowContentAccess();
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.addJavascriptInterface(new MyInterface(), "xGetter");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.evaluateJavascript(
                        "(function() { return ('<html>'+document.getElementsByTagName('head')[0].innerHTML+'</html>'); })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {

                                html = html.replaceAll("\\s", "");

                                if (html!=null){
                                    final String regex = "window\\.location\\.href.*?=.*?'(.*?)';";
                                    final Pattern pattern = Pattern.compile(regex);
                                    final Matcher matcher = pattern.matcher(html);
                                    if (matcher.find()) {
                                        ArrayList<Jmodel> jModels = new ArrayList<>();
                                        putModel(matcher.group(1),"Normal",jModels);
                                        onTaskCompleted.onTaskCompleted(jModels,false);
                                    }else {
                                        onTaskCompleted.onError();
                                    }
                                }else {
                                    onTaskCompleted.onError();
                                }
                            }
                        });
            }
        });

        webView.loadUrl(url);
    }

    private static class MyInterface {
        @SuppressWarnings("unused")
        @JavascriptInterface
        public void error(final String error) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    destroyWebView();
                    stopMMHandler = true;
                }
            });
        }
    }

    private static void destroyWebView() {
        if (webView!=null) {
            webView.loadUrl("about:blank");
        }
    }

}
