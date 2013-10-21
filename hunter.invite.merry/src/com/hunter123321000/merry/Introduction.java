package com.hunter123321000.merry;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.widget.HorizontalScrollView;

public class Introduction extends Activity {

	private HorizontalScrollView hsv;
	private TextViewVertical tv;
	private SpannableString msp = null;
	private boolean f_play = false;
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.introduction);

		new Thread(new Runnable() {
			@Override
			public void run() {
				AssetFileDescriptor fileDescriptor = null;
				try {
					fileDescriptor = getAssets().openFd(
							"introd.mp3");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mediaPlayer = new MediaPlayer();
				try {
					mediaPlayer.setDataSource(
							fileDescriptor.getFileDescriptor(),
							fileDescriptor.getStartOffset(),
							fileDescriptor.getLength());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					mediaPlayer.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mediaPlayer.start();

			}
		}).start();
		hsv = (HorizontalScrollView) findViewById(R.id.sv);
		tv = (TextViewVertical) findViewById(R.id.label_introd);

		// 设置接口事件接收
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case TextViewVertical.LAYOUT_CHANGED:
					hsv.scrollBy(tv.getTextWidth(), 0);// 滚动到最右边
					// Toast.makeText(TestFontActivity.this, "位置",
					// Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
		// tv.setHandler(handler);// 将Handler绑定到TextViewVertical

		// 创建并设置字体（这里只是为了效果好看一些，但为了让网友们更容易下载，字体库并没有一同打包
		// 如果需要体验下效果的朋友可以自行在网络上搜索stxingkai.ttf并放入assets/fonts/中）
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/mystyle.TTC");
		tv.setTypeface(face);

		// msp = new SpannableString(getResources().getString(R.string.introd));
		// msp.setSpan(new ForegroundColorSpan(Color.MAGENTA), 64, 79,
		// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(getResources().getString(R.string.introd));

	}

	@Override
	protected void onDestroy() {
		mediaPlayer.stop();
		super.onDestroy();
	}
	
}
