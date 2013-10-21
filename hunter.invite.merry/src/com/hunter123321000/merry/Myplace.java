package com.hunter123321000.merry;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Myplace extends Activity implements LocationListener {
	private static final String MAP_URL = "file:///android_asset/deriction2.html";
	private WebView webView;
	private Location mostRecentLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myplace);
		getLocation();
		setupWebView();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@SuppressLint("JavascriptInterface")
	private void setupWebView() {

		webView = (WebView) findViewById(R.id.webview01);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDefaultTextEncodingName("utf-8");
		webView.loadUrl(MAP_URL);
//		webView.loadDataWithBaseURL(null, MAP_URL, "text/html", "utf-8", null);

		/** Allows JavaScript calls to access application resources **/
		webView.addJavascriptInterface(new JavaScriptInterface(), "android");

	}

	private class JavaScriptInterface {
		public double getLatitude() {
			return mostRecentLocation.getLatitude();
		}

		public double getLongitude() {
			return mostRecentLocation.getLongitude();
		}

	}

	private void getLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = locationManager.getBestProvider(criteria, true);

		// In order to make sure the device is getting location, request
		// updates. locationManager.requestLocationUpdates(provider, 1, 0,
		// this);
		mostRecentLocation = locationManager.getLastKnownLocation(provider);
	}

	@Override
	public void onLocationChanged(Location location) {
		mostRecentLocation = location;

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
}
