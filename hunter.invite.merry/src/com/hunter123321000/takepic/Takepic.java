package com.hunter123321000.takepic;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.hunter123321000.merry.R;

public class Takepic extends Activity {
	private Uri outputFileUri;
	private ImageView iv;
	private Button btn_upload;
	private File tmpFile;
	private EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.takepic);

		iv = (ImageView) findViewById(R.id.iv);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				open_camera();
			}
		});
		btn_upload = (Button) findViewById(R.id.btn_upload);
		btn_upload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Async_upload async_upload = (Async_upload) new Async_upload()
						.execute();
				et.setText("");
				iv.setImageDrawable(getResources().getDrawable(R.drawable.camer_icon));
			}
		});

		et = (EditText) findViewById(R.id.et);

	}

	private void open_camera() {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);// 利用intent去開啟android本身的照相介面

		// 取得系統時間
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
		String date = sDateFormat.format(new java.util.Date());
		// 設定圖片的儲存位置，以及檔名
		tmpFile = new File(Environment.getExternalStorageDirectory(), date
				+ ".jpg");
		outputFileUri = Uri.fromFile(tmpFile);

		/*
		 * 把上述的設定put進去！然後startActivityForResult,
		 * 記住，因為是有ForResult，所以在本身自己的acitivy裡面等等要複寫onActivityResult
		 * 稍後再說明onActivityResult
		 */
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Bitmap bmp = BitmapFactory.decodeFile(outputFileUri.getPath()); // 利用BitmapFactory去取得剛剛拍照的圖像

			iv.setImageBitmap(bmp);
		}
	}

	public void createPhoto() {
		Log.i("0.0", "createPhoto");
		PicasawebService myService = new PicasawebService(
				"exampleCo-exampleApp-1");
		try {
			Log.i("0.0", "111");
			myService.setUserCredentials("hunter123321000@gmail.com",
					"123321000");
			URL feedUrl = new URL(
					"https://picasaweb.google.com/data/feed/api/user/hunter123321000@gmail.com?kind=album");
			UserFeed myUserFeed = myService.getFeed(feedUrl, UserFeed.class);
			Log.i("0.0",
					"myUserFeed.getAlbumEntries()="
							+ myUserFeed.getAlbumEntries());
			for (AlbumEntry myAlbum : myUserFeed.getAlbumEntries()) {
				Log.i("0.0", "myAlbum.getTitle().getPlainText()="
						+ myAlbum.getTitle().getPlainText());
			}
			Log.i("0.0", "333");
			URL albumPostUrl = new URL(
					"https://picasaweb.google.com/data/feed/api/user/hunter123321000%40gmail.com/albumid/5903317148568359473");
			PhotoEntry myPhoto = new PhotoEntry();
			myPhoto.setTitle(new PlainTextConstruct("My Wedding"));
			myPhoto.setDescription(new PlainTextConstruct(et.getText()
					.toString()));
			myPhoto.setClient("myClientName");
			//
			MediaFileSource myMedia = new MediaFileSource(tmpFile, "image/jpeg");
			//
			myPhoto.setMediaSource(myMedia);
			PhotoEntry returnedPhoto = myService.insert(albumPostUrl, myPhoto);
			MediaContent content1 = (MediaContent) returnedPhoto.getContent();
			Log.i("0.0", "returnedPhoto.getHtmlLink().getHref()="
					+ returnedPhoto.getHtmlLink().getHref());

		} catch (Exception e) {
			Log.i("0.0", "e=" + e.toString());
			e.printStackTrace();
		}

	}

	private class Async_upload extends AsyncTask<String, Integer, String> {
		private ProgressDialog mDialog;

		@Override
		protected void onPreExecute() {
			mDialog = new ProgressDialog(Takepic.this);
			mDialog.setMessage("Uploading...");
			mDialog.setCancelable(false);
//			mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mDialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg0) {
			createPhoto();
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
