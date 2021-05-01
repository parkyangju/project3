package com.example.my38_locationmap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class ParkingAddAddress extends AppCompatActivity {
    private WebView browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_layout);
        setContentView(R.layout.activity_parking_add_address);

        browser = (WebView) findViewById(R.id.daum_webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new AndroidBridge(), "Android");
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                browser.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });
        browser.loadUrl("http://3.143.192.36/api/daumAddressApi");
    }

    public void nextPage(String data) {
        Log.v("testes", data);
        Bundle extra = new Bundle();
        Intent intent = new Intent(ParkingAddAddress.this, ParkingAddActivity.class);
        //Intent intent = getIntent();
        extra.putString("data", data);
        //intent.putExtras(extra);
        intent.putExtra("data", data);
        //setResult(RESULT_OK, intent);
        startActivity(intent);
        finish();
    }

    class AndroidBridge {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            nextPage(data);
        }
    }
}
