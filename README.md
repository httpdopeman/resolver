# JResolver for Android! [![](https://jitpack.io/v/Inside4ndroid/resolver.svg)](https://jitpack.io/#Inside4ndroid/resolver)

This is a useful library for those who want to create movie and video apps and resolve common links from hoster to play as raw links.
There is an example application which offers an example of how the resolvers work.

Supported Sites/Hosts :

1. Google Photos
2. Mp4Upload
3. Facebook
4. Mediafire
5. Ok.Ru
6. VK
7. Twitter
8. Youtube
9. SolidFiles
10. Vidoza
11. SendVid
12. FEmbed
13. FileRio
14. DailyMotion
15. VidBam
16. VideoBin
17. BitTube
18. 4Shared
19. StreamTape
20. Vudeo
21. Amazon Drive
22. Doodstream
23. StremSB
24. Mixdrop

I will be adding more hosters over time and fixing any issues reported.

# How To Use

### Add this to the PROJECT build.gradle

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
	implementation 'com.github.Inside4ndroid:resolver:3.0'<font></font>
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
