package com.hunter123321000.binggo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import com.hunter123321000.merry.R;

public class MyGifView extends View {
	private long movieStart;
	private Movie movie;

	// 此處必須重寫該構造方法
	public MyGifView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		// 以文件流（InputStream）讀取進gif 圖片資源
		movie = Movie.decodeStream(getResources().openRawResource(
				R.drawable.bb_title));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		long curTime = android.os.SystemClock.uptimeMillis();
		// 第一次播放
		if (movieStart == 0) {
			movieStart = curTime;
		}
		if (movie != null) {
			int duraction = movie.duration();
			int relTime = (int) ((curTime - movieStart) % duraction);
			movie.setTime(relTime);
			movie.draw(canvas, 0, 0);
			// 強制重繪
			invalidate();
		}
		super.onDraw(canvas);
	}
}