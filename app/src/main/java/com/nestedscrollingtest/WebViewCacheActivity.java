package com.nestedscrollingtest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.tools.FileUtil;

/**
 * Created by Fang Ruijiao on 2017/7/14.
 */

public class WebViewCacheActivity extends BaseActivity implements View.OnClickListener{
    private WebView mWebView;
    private Button nightModeBtn;
    private Button lightModeBtn;

    // -----
    private static final String TAG = "HTML_PRINT";
    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录
    private String url; // 网页url

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_webview_catch);

        mWebView = (WebView) this.findViewById(R.id.webview);
        nightModeBtn = (Button) this.findViewById(R.id.btn_nightmode);
        lightModeBtn = (Button) this.findViewById(R.id.btn_lightmode);

        nightModeBtn.setOnClickListener(this);
        lightModeBtn.setOnClickListener(this);

        for(int i = 0; i < 1000000; i++){
            mWebView = (WebView) this.findViewById(R.id.webview);
            nightModeBtn = (Button) this.findViewById(R.id.btn_nightmode);
            lightModeBtn = (Button) this.findViewById(R.id.btn_lightmode);
        }

        // webView.loadUrl("http://www.baidu.com");


        // 绑定javaScript接口，可以实现在javaScript中调用我们的Android代码
        // webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        // webView.setWebViewClient(new MyWebViewClient());

        // 加载assets目录下的html页面
        // mWebView.loadUrl("file:///android_asset/01.html");
        url = "http://blog.csdn.net/wwj_748/article/details/44810283";

        findView();
    }

    public void findView() {
        WebSettings settings = mWebView.getSettings();
        // 设置javaScript可用
        settings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                Log.i(TAG, "onLoadResource url=" + url);

                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "intercept url=" + url);
                view.loadUrl(url);
                return true;
            }

            // 页面开始时调用
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e(TAG, "onPageStarted");
                super.onPageStarted(view, url, favicon);
            }

            // 页面加载完成调用
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
                        "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e(TAG, "description:" + description + "    errorCode:" + errorCode + "    failingUrl:" + failingUrl);
                Toast.makeText(getApplicationContext(), "errorCode:" + errorCode, Toast.LENGTH_LONG)
                        .show();
            }
        });
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/htmlPrint.txt";
        String html = FileUtil.readFile(path);
//        mWebView.loadData(html,"text/html", "utf-8");
        mWebView.loadUrl(url);
    }

    final class InJavaScriptLocalObj {

        @JavascriptInterface
        public void showSource(String html) {
            Log.d("HTML_PRINT", html);
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/htmlPrint.txt";
            Log.d("HTML_PRINT","path:" + path);
            FileUtil.saveFileToCompletePath(path,html,false);
        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_nightmode:
                mWebView.loadUrl("javascript:load_night()");
                break;
            case R.id.btn_lightmode:
                mWebView.loadUrl("javascript:load_day()");
                break;
            default:
                break;
        }
    }
}
