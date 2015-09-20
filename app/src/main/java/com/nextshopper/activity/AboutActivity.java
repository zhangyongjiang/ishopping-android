package com.nextshopper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.nextshopper.view.TitleView;


public class AboutActivity extends BaseActivity {
    private static String TITLE="title";
    private TitleView titleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Intent intent = getIntent();
        String title = intent.getStringExtra(TITLE);
        titleView = (TitleView) findViewById(R.id.about_title);
        titleView.setTextMiddle(title);
        WebView webView = (WebView) findViewById(R.id.webview);
        if(title.equals("About Us")){
            webView.loadUrl("https://api.nextshopper.com/about-us/index.jsp.oo");
        }else if(title.equals("FAQ")){
            webView.loadUrl("https://api.nextshopper.com/faq/index.jsp.oo ");
        }else if(title.equals("Terms of Service")){
            webView.loadUrl(" https://api.nextshopper.com/tos/index.jsp.oo");
        }else if(title.equals("Privacy Policy")){
            webView.loadUrl("https://api.nextshopper.com/privacy-policy/index.jsp.oo");
        }

    }

}
