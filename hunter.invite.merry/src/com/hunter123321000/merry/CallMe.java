package com.hunter123321000.merry;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class CallMe extends Activity {
	private Button btn_call, btn_phone1, btn_phone2, btn_phone3;
	private TextView lb_phone1,lb_phone2,lb_phone3;
	private Boolean f_expand = false;
	private LayoutParams params = new LayoutParams(0, 0);
	private Animation animationTranslate;
	private BTN_OnclickListener btn_onclicklisten;
	private Intent phoneCallIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.callme);

		getView();
		btn_onclicklisten = new BTN_OnclickListener();
		btn_call.setOnClickListener(btn_onclicklisten);
		btn_phone1.setOnClickListener(btn_onclicklisten);
		btn_phone2.setOnClickListener(btn_onclicklisten);
		btn_phone3.setOnClickListener(btn_onclicklisten);
	}

	private class BTN_OnclickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_call:
				if (f_expand == false) {
					btn_phone1.setVisibility(View.VISIBLE);
					btn_phone2.setVisibility(View.VISIBLE);
					btn_phone3.setVisibility(View.VISIBLE);
					lb_phone1.setVisibility(View.VISIBLE);
					lb_phone2.setVisibility(View.VISIBLE);
					lb_phone3.setVisibility(View.VISIBLE);
					lb_phone1.setPadding(parameter.vWidth / 8, (int)(parameter.vHeight / 3.5), 0, 0);
					lb_phone2.setPadding((parameter.vWidth / 20)*9, (int)(parameter.vHeight / 3.5), 0, 0);
					lb_phone3.setPadding((parameter.vWidth /4)*3, (int)(parameter.vHeight / 3.5), 0, 0);
					f_expand = true;
					btn_phone1.startAnimation(animTranslate(
							parameter.vWidth / 2, parameter.vWidth / 20,
							parameter.vHeight / 2, parameter.vHeight / 8,
							btn_phone1, 80));					
					btn_phone2.startAnimation(animTranslate(
							parameter.vWidth / 2, (parameter.vWidth / 5)*3,
							parameter.vHeight / 2, parameter.vHeight / 8,
							btn_phone2, 80));
					btn_phone3.startAnimation(animTranslate(
							parameter.vWidth / 2, (parameter.vWidth / 10)*9,
							parameter.vHeight / 2, parameter.vHeight / 8,
							btn_phone3, 80));

				} else {
					btn_phone1.setVisibility(View.GONE);
					btn_phone2.setVisibility(View.GONE);
					btn_phone3.setVisibility(View.GONE);
					lb_phone1.setVisibility(View.GONE);
					lb_phone2.setVisibility(View.GONE);
					lb_phone3.setVisibility(View.GONE);
					f_expand = false;
					btn_phone1.startAnimation(animTranslate(
							parameter.vWidth / 12, parameter.vWidth / 6,
							parameter.vHeight / 12, parameter.vHeight / 4,
							btn_phone1, 80));
					btn_phone2.startAnimation(animTranslate(
							parameter.vWidth / 10, 0, parameter.vHeight / 12,
							parameter.vHeight / 4, btn_phone2, 80));
					btn_phone3.startAnimation(animTranslate(0,
							(0 - parameter.vWidth / 8), parameter.vHeight / 12,
							parameter.vHeight / 4, btn_phone3, 80));
				}
				Log.i("0.0", "vWidth=" + parameter.vWidth + "   vHeight="
						+ parameter.vHeight);
				break;
			case R.id.btn_phone1:
				phoneCallIntent = new Intent(Intent.ACTION_CALL);
				phoneCallIntent.setData(Uri.parse("tel:0980889645"));
				startActivity(phoneCallIntent);
				break;
			case R.id.btn_phone2:
				phoneCallIntent = new Intent(Intent.ACTION_CALL);
				phoneCallIntent.setData(Uri.parse("tel:0423375809"));
				startActivity(phoneCallIntent);
				break;
			case R.id.btn_phone3:
				phoneCallIntent = new Intent(Intent.ACTION_CALL);
				phoneCallIntent.setData(Uri.parse("tel:0925797662"));
				startActivity(phoneCallIntent);
				break;
			}
		}

	}

	private void getView() {
		btn_call = (Button) findViewById(R.id.btn_call);
		btn_phone1 = (Button) findViewById(R.id.btn_phone1);
		btn_phone2 = (Button) findViewById(R.id.btn_phone2);
		btn_phone3 = (Button) findViewById(R.id.btn_phone3);
		lb_phone1 = (TextView) findViewById(R.id.label_phone1);
		lb_phone2 = (TextView) findViewById(R.id.label_phone2);
		lb_phone3 = (TextView) findViewById(R.id.label_phone3);
	}

	protected Animation animTranslate(float toX, final float lastX,
			final float toY, final float lastY, final Button button,
			long durationMillis) {
		// TODO Auto-generated method stub
		animationTranslate = new TranslateAnimation(toX, lastX, toY, lastY);// 動畫設定
																			// (指定移動動畫)
																			// (x1,
																			// x2,
																			// y1,
																			// y2)
		animationTranslate.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationEnd(Animation animation) {
				
				// TODO Auto-generated method stub
				params = new LayoutParams(0, 0);
				if (f_expand == true) {
					if (button == btn_phone1)
						params.setMargins((int) lastX, (int) lastY, 0, 0);
					else if (button == btn_phone2)
						params.setMargins((int) lastX - 80, (int) lastY, 0, 0);
					else
						params.setMargins((int) lastX - 60, (int) lastY, 0, 0);
					params.width=parameter.vWidth/4;
					params.height = params.width;					
				} else {
					params.width=parameter.vWidth/4;
					params.height = params.width;
				}

				button.setLayoutParams(params);
				button.clearAnimation();
			}
		});
		animationTranslate.setDuration(durationMillis);
		return animationTranslate;
	}

	

	// monitor phone call states
	private class PhoneCallListener extends PhoneStateListener {

		String TAG = "LOGGING PHONE CALL";

		private boolean phoneCalling = false;

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			if (TelephonyManager.CALL_STATE_RINGING == state) {
				// phone ringing
				Log.i(TAG, "RINGING, number: " + incomingNumber);
			}

			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
				// active
				Log.i(TAG, "OFFHOOK");

				phoneCalling = true;
			}

			// When the call ends launch the main activity again
			if (TelephonyManager.CALL_STATE_IDLE == state) {

				Log.i(TAG, "IDLE");

				if (phoneCalling) {

					Log.i(TAG, "restart app");

					// restart app
					Intent i = getBaseContext().getPackageManager()
							.getLaunchIntentForPackage(
									getBaseContext().getPackageName());

					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);

					phoneCalling = false;
				}

			}
		}
	}
}
