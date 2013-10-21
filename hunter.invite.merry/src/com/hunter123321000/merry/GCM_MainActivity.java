package com.hunter123321000.merry;

import static com.hunter123321000.merry.GCM_CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.hunter123321000.merry.GCM_CommonUtilities.SENDER_ID;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gcm.GCMRegistrar;

public class GCM_MainActivity extends Activity {
	// label to display gcm messages
	TextView lblMessage;

	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;

	// Alert dialog manager
	GCM_AlertDialogManager alert = new GCM_AlertDialogManager();

	// Connection detector
	GCM_ConnectionDetector cd;

	public static String name;
	public static String email;
	private Button btn_next, btn_skip, btn_pg_right, btn_pg_left;
	private TextView tv_title;
	private EditText et_name, et_email;
	private ViewFlipper viewFlipper;
	float startX;
	int i_pg = 0;
	private BTN_onclicklistener btn_clicklistener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_gcm);

		getView();		
		if (GCMRegistrar.isRegisteredOnServer(this)) {
			viewFlipper.setDisplayedChild(4);
			Intent intent = new Intent();
			intent.setClass(GCM_MainActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			overridePendingTransition(R.anim.shake, R.anim.my_alpha_action);
		}
		tv_title.setText(getApplicationContext().getString(
				R.string.lb_descript1)
				+ (i_pg + 1)
				+ " / "
				+ viewFlipper.getChildCount()
				+ getApplicationContext().getString(R.string.lb_descript2));
		viewFlipper.setDisplayedChild(i_pg);

	}

	private void getView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_skip = (Button) findViewById(R.id.btn_skip);
		btn_pg_right = (Button) findViewById(R.id.btn_pg_right);
		btn_pg_left = (Button) findViewById(R.id.btn_pg_left);
		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		et_name = (EditText) findViewById(R.id.et_name);
		et_email = (EditText) findViewById(R.id.et_email);
		lblMessage = (TextView) findViewById(R.id.lblMessage);
		lblMessage.setText(getApplicationContext().getString(
				R.string.lb_notification));

		btn_clicklistener = new BTN_onclicklistener();
		btn_next.setOnClickListener(btn_clicklistener);
		btn_skip.setOnClickListener(btn_clicklistener);
		btn_pg_right.setOnClickListener(btn_clicklistener);
		btn_pg_left.setOnClickListener(btn_clicklistener);
	}

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			viewFlipper.setDisplayedChild(5);
			String newMessage = intent.getExtras().getString(
					GCM_CommonUtilities.EXTRA_MESSAGE);
			Log.i("0.0", "newMessage=" + newMessage);
			// Waking up mobile if it is sleeping
			GCM_WakeLocker.acquire(getApplicationContext());

			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */

			// Showing received message
			lblMessage.append(newMessage + "\n");
			Toast.makeText(getApplicationContext(),
					"New Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			GCM_WakeLocker.release();
		}
	};

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i("0.0", "進入Stop");
		super.onStop();
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			break;
		case MotionEvent.ACTION_UP:

			if (event.getX() > startX) { // 向右滑動
				i_pg++;
				if (i_pg >= viewFlipper.getChildCount())
					i_pg = 0;
				viewFlipper.setInAnimation(this, R.anim.in_leftright);
				viewFlipper.setOutAnimation(this, R.anim.out_leftright);
				// viewFlipper.showNext();
			} else if (event.getX() < startX) { // 向左滑動
				i_pg--;
				if (i_pg < 0)
					i_pg = viewFlipper.getChildCount() - 1;
				viewFlipper.setInAnimation(this, R.anim.in_rightleft);
				viewFlipper.setOutAnimation(this, R.anim.out_rightleft);
				// viewFlipper.showPrevious();
			}
			tv_title.setText(getApplicationContext().getString(
					R.string.lb_descript1)
					+ (i_pg + 1)
					+ " / "
					+ viewFlipper.getChildCount()
					+ getApplicationContext().getString(R.string.lb_descript2));
			viewFlipper.setDisplayedChild(i_pg);
			break;
		}

		return super.onTouchEvent(event);
	}

	public void regID() {
		cd = new GCM_ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(GCM_MainActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		name = et_name.getText().toString();
		email = et_email.getText().toString();
		
		Log.i("0.0", "name=" + name + "   email=" + email);
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		Log.i("0.0", "regId=" + regId);
		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
//				 Toast.makeText(getApplicationContext(),
//				 "Already registered with GCM", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.setClass(GCM_MainActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				overridePendingTransition(R.anim.shake, R.anim.my_alpha_action);
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						GCM_ServerUtilities.register(context, name, email,
								regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			onStop();
			return super.onKeyDown(keyCode, event);
		}else{
			return false;
		}
		
	}


	private class BTN_onclicklistener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_skip:
				Intent intent = new Intent();
				intent.setClass(GCM_MainActivity.this, MainActivity.class);
				startActivity(intent);				
				break;
			case R.id.btn_next:
				regID();
				intent = new Intent();
				intent.setClass(GCM_MainActivity.this, MainActivity.class);
				startActivity(intent);				
				break;
			case R.id.btn_pg_left:
				i_pg--;
				if (i_pg < 0)
					i_pg = viewFlipper.getChildCount() - 1;
				viewFlipper.setInAnimation(GCM_MainActivity.this,
						R.anim.in_rightleft);
				viewFlipper.setOutAnimation(GCM_MainActivity.this,
						R.anim.out_rightleft);

				tv_title.setText(getApplicationContext().getString(
						R.string.lb_descript1)
						+ (i_pg + 1)
						+ " / "
						+ viewFlipper.getChildCount()
						+ getApplicationContext().getString(
								R.string.lb_descript2));
				viewFlipper.setDisplayedChild(i_pg);
				break;
			case R.id.btn_pg_right:

				i_pg++;
				if (i_pg >= viewFlipper.getChildCount())
					i_pg = 0;
				viewFlipper.setInAnimation(GCM_MainActivity.this,
						R.anim.in_leftright);
				viewFlipper.setOutAnimation(GCM_MainActivity.this,
						R.anim.out_leftright);

				tv_title.setText(getApplicationContext().getString(
						R.string.lb_descript1)
						+ (i_pg + 1)
						+ " / "
						+ viewFlipper.getChildCount()
						+ getApplicationContext().getString(
								R.string.lb_descript2));
				viewFlipper.setDisplayedChild(i_pg);
				break;
			}
		}

	}

}
