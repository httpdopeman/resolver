# JResolver for Android! [![](https://jitpack.io/v/Inside4ndroid/resolver.svg)](https://jitpack.io/#Inside4ndroid/resolver)

This is a useful library for those who want to create movie and video apps and resolve common links from hoster to play as raw links.
There is an example application which offers an example of how the resolvers work.

Supported Sites/Hosts :

1. Amazon
2. Aparat
3. Archive
4. BitChute
5. Brighteon
6. Cloudvideo
7. DeadlyBlogger
8. DailyMotion
9. Doodstream
10. Eplayvid
11. Filerio
12. 4Shared
13. Google Photos
14. Google User Content
15. Hdvid
16. MediaFire
17. Midian
18. Mixdrop
19. Mp4upload
20. Ok.ru
21. Sendvid
22. Streamhide
23. Streamlare
24. Streamsb
25. Streamtape
26. Streamvid
27. Upstream
28. VideoBM
29. VidHD
30. Vidmoly
31. Vidoza
32. VK
33. Voesx
34. Vudeo
35. Yodbox
36. Youtube

I will be adding more hosters over time and fixing any issues reported.

# How To Use

### Add this to the PROJECT build.gradle

<!--suppress ALL -->







































<pre>
<code>allprojects {<font></font>
  repositories {  <font></font>
  google()  <font></font>
        jcenter()  <font></font>
        maven { url "https://jitpack.io" }  //Add this<font></font>
 }}<font></font>
</code>
</pre>

### Add this to the APP build.gradle

<pre>
<code>
dependencies {  <font></font>
	implementation 'com.github.Inside4ndroid:resolver:7.3'<font></font>
}<font></font>
</code>
</pre>

### Add this to you applications manifest.xml

<pre><code> &lt;application .....<font></font>
     android:usesCleartextTraffic="true"&gt;<font></font>
</code></pre>

### Add the rules to your proguard for okhttp3

<pre><code>  # JSR 305 annotations are for embedding nullability information.  <font></font>
-dontwarn javax.annotation.**  <font></font>
  <font></font>
# A resource is loaded with a relative path so the package of this class must be preserved.  <font></font>
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase  <font></font>
  <font></font>
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.  <font></font>
-dontwarn org.codehaus.mojo.animal_sniffer.*  <font></font>
  <font></font>
# OkHttp platform used only on JVM and when Conscrypt dependency is available.  <font></font>
-dontwarn okhttp3.internal.platform.ConscryptPlatform<font></font>
</code></pre>

### Then to use the library make this call

<pre><code>Jresolver jResolver = new Jresolver(this);  <font></font>
jResolver.onFinish(new Jresolver.OnTaskCompleted() {  <font></font>
    @Override  <font></font>
  public void onTaskCompleted(ArrayList&lt;Jmodel&gt; vidURL, boolean multiple_quality) {  <font></font>
        if (multiple_quality){ //This video you can choose qualities  <font></font>
  for (Jmodel model : vidURL){  <font></font>
                String url = model.getUrl();   <font></font>
  }   <font></font>
        }else {//If single  <font></font>
  String url = vidURL.get(0).getUrl();  <font></font>
  }  <font></font>
    }  <font></font>
  <font></font>
    @Override  <font></font>
  public void onError() {  <font></font>
        //Error  <font></font>
        
  }  <font></font>
});<font></font>
</code></pre>

### Dependencies used in the JResolver Library

<a href="https://github.com/evgenyneu/js-evaluator-for-android">JS Evaluator For Android</a>

<a href="https://github.com/amitshekhariitbhu/Fast-Android-Networking">Fast Android Networking Library</a>

<a href="https://github.com/jhy/jsoup">JSoup</a>

<a href="https://github.com/apache/commons-lang">Apache Commons Lang</a>

### Special Thanks

The library is based on the <a href="https://github.com/KhunHtetzNaing/xGetter">xGetter Library</a> by <a href="https://github.com/KhunHtetzNaing">Khun Htetz Naing</a>
