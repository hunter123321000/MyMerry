package com.hunter123321000.takepic;

import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.hunter123321000.merry.R;

public class Showpic extends Activity {
	private WebView wb_showpic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpic);

		wb_showpic = (WebView) findViewById(R.id.wb_showpic);
		wb_showpic.getSettings().setJavaScriptEnabled(true);
		wb_showpic.setWebViewClient(new WebViewClient());

		WebSettings settings = wb_showpic.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDefaultTextEncodingName("utf-8");
		wb_showpic.loadUrl("https://picasaweb.google.com/104153259779228726193/MyWedding?authuser=0&feat=directlink");
		
		Async_upload async_upload = (Async_upload) new Async_upload()
		.execute();

	}
	public void getPhoto() {
		PicasawebService myService = new PicasawebService(
				"exampleCo-exampleApp-1");
		try {
			myService.setUserCredentials("hunter123321000@gmail.com",
					"123321000");
			
			URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/hunter123321000%40gmail.com/albumid/5903317148568359473");
			AlbumFeed feed = myService.getFeed(feedUrl, AlbumFeed.class);
			
			Query myQuery = new Query(feedUrl);
			for(int i=0;i<myQuery.getMaxResults();i++)
				Log.i("0.0", "123)="+myQuery.getUrl());

			
			
			for(PhotoEntry photo : feed.getPhotoEntries()) {
			    Log.i("0.0", "photo.getTitle().getPlainText()="+photo.getTitle().getPlainText());
			}

		} catch (Exception e) {
			Log.i("0.0", "e=" + e.toString());
			e.printStackTrace();
		}

	}
	private class Async_upload extends AsyncTask<String, Integer, String> {
		private ProgressDialog mDialog;

		@Override
		protected void onPreExecute() {
			mDialog = new ProgressDialog(Showpic.this);
			mDialog.setMessage("loading...");
			mDialog.setCancelable(false);
//			mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mDialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg0) {
			getPhoto();
			return "ok";
		}

		@Override
		protected void onPostExecute(String result) {
			 if(result.equals("ok"))
				 mDialog.dismiss();
			super.onPostExecute(result);
		}

	}
}
