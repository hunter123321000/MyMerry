package com.hunter123321000.merry;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class function {
	public static void getDisplayMetrics(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		parameter.vWidth = dm.widthPixels;
		parameter.vHeight = dm.heightPixels;

	}
}
