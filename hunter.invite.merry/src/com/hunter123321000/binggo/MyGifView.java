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

	// ���B�������g�Ӻc�y��k
	public MyGifView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		// �H���y�]InputStream�^Ū���igif �Ϥ��귽
		movie = Movie.decodeStream(getResources().openRawResource(
				R.drawable.bb_title));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		long curTime = android.os.SystemClock.uptimeMillis();
		// �Ĥ@������
		if (movieStart == 0) {
			movieStart = curTime;
		}
		if (movie != null) {
			int duraction = movie.duration();
			int relTime = (int) ((curTime - movieStart) % duraction);
			movie.setTime(relTime);
			movie.draw(canvas, 0, 0);
			// �j�ø
			invalidate();
		}
		super.onDraw(canvas);
	}
}