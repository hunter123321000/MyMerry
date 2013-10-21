package com.hunter123321000.merry;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.opengl.Matrix;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	int mGalleryItemBackground;
	private Context mContext;
	private Integer[] mImageIds;
	private ImageView[] mImages;

	public ImageAdapter(Context c, Integer[] ImageIds) {
		mContext = c;
		mImageIds = ImageIds;
		mImages = new ImageView[mImageIds.length];
	}

	/**
	 * ?�建?�影?��?
	 * 
	 * @return
	 */
	public boolean createReflectedImages() {
		// ?�影?��??�图之间?��?�?
		final int reflectionGap = 4;
		int index = 0;
		for (int imageId : mImageIds) {
			// 返�??�图�??之�??�bitmap对象
			Bitmap originalImage = BitmapFactory.decodeResource(
					mContext.getResources(), imageId);
			int width = originalImage.getWidth();
			int height = originalImage.getHeight();
			// ?�建?�阵对象
			android.graphics.Matrix matrix = new android.graphics.Matrix();

			// ?��?�?��角度�?,0为�??��?行�?�?
			// matrix.setRotate(30);

			// ?��??�阵(x轴�??��?y轴相??
			matrix.preScale(1, -1);

			// 将矩?��??�到该�??��?中�?返�?�?��宽度不�?，�?度为?�图1/2?��?影�???
			Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
					height / 2, width, height / 2, matrix, false);

			// ?�建�?��宽度不�?，�?度为?�图+?�影?��?度�?位图
			Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
					(height + height / 2), Config.ARGB_8888);

			// 将�??��?建�?位图?��??�到?��?
			Canvas canvas = new Canvas(bitmapWithReflection);
			canvas.drawBitmap(originalImage, 0, 0, null);

			Paint deafaultPaint = new Paint();
			deafaultPaint.setAntiAlias(false);
			// canvas.drawRect(0, height, width, height +
			// reflectionGap,deafaultPaint);
			canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
			Paint paint = new Paint();
			paint.setAntiAlias(false);

			/**
			 * ?�数�?为�??�起?�点?��?x位置�??�数�?为y轴�?置�? ?�数三�????�辨对�?渐�?终点�?????�数为平?�方式�?
			 * 这�?设置为�??�Gradient?�基于Shader类�???��?�们?��?Paint?�setShader?��??�设置�?个�???
			 */
			LinearGradient shader = new LinearGradient(0,
					originalImage.getHeight(), 0,
					bitmapWithReflection.getHeight() + reflectionGap,
					0x70ffffff, 0x00ffffff, TileMode.MIRROR);
			// 设置?�影
			paint.setShader(shader);
			paint.setXfermode(new PorterDuffXfermode(
					PorterDuff.Mode.DST_IN));
			// ?�已经�?义好?�画笔�?建�?个矩形阴影�??��???
			canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
					+ reflectionGap, paint);

			// ?�建�?��ImageView?�来?�示已�??�好?�bitmapWithReflection
			ImageView imageView = new ImageView(mContext);
			imageView.setImageBitmap(bitmapWithReflection);
			// 设置imageView大�? ，�?就是????�示?�图?�大�?		
			Log.i("0.0", "parameter.vHeight="+parameter.vHeight+"   parameter.vWidth="+parameter.vWidth);
			if(parameter.vHeight>parameter.vWidth)
				imageView.setLayoutParams(new GalleryFlow.LayoutParams(parameter.vWidth/2,parameter.vHeight/2));
			else
				imageView.setLayoutParams(new GalleryFlow.LayoutParams(parameter.vHeight/2,parameter.vWidth/2));
			// imageView.setScaleType(ScaleType.MATRIX);
			mImages[index++] = imageView;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private Resources getResources() {
		return null;
	}

	public int getCount() {
		return mImageIds.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return mImages[position];
	}

	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}
}