package com.hunter123321000.merry;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Mydate extends Activity {
	private WebView wb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mydate);

		wb = (WebView) findViewById(R.id.wb);
		WebSettings websettings = wb.getSettings();
		websettings.setSupportZoom(true);
		websettings.setBuiltInZoomControls(true);
		websettings.setJavaScriptEnabled(true);

		wb.setWebViewClient(new WebViewClient());

		wb.loadUrl("https://docs.google.com/forms/d/179Ajf7--ga2gGGdsY0Poof8jctpjmq-dNM7f1Bktvrs/viewform");
	}
}
