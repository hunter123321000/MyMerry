package com.hunter123321000.merry;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.Toast;

import com.hunter123321000.binggo.Game_bb;
import com.hunter123321000.takepic.Showpic;
import com.hunter123321000.takepic.Takepic;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	private Gallery gallery;

	private Integer[] mThumbIds = { R.drawable.publish, R.drawable.docs,
			R.drawable.map, R.drawable.phone, R.drawable.imgs,R.drawable.games, R.drawable.takepic };

	private Intent intent = new Intent();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		function.getDisplayMetrics(MainActivity.this);
		ImageAdapter adapter = new ImageAdapter(MainActivity.this, mThumbIds);
		adapter.createReflectedImages();// ?�建?�影?��?
		gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setAdapter(adapter);

		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// Toast.makeText(getApplicationContext(),
				// "arg1="+arg1+"   arg2="+arg2, 5000).show();
				switch (arg2) {
				case 0:
					intent.setClass(MainActivity.this, Introduction.class);
					startActivity(intent);
					overridePendingTransition(R.anim.shake,
							R.anim.my_alpha_action);
					break;
				case 1:
					intent.setClass(MainActivity.this, Mydate.class);
					startActivity(intent);
					overridePendingTransition(R.anim.myanimation_simple,
							R.anim.my_alpha_action);
					break;
				case 2:
					intent.setClass(MainActivity.this, Myplace.class);
					startActivity(intent);
					overridePendingTransition(R.anim.myanimation_simple,
							R.anim.my_alpha_action);
					break;
				case 3:
					intent.setClass(MainActivity.this, CallMe.class);
					startActivity(intent);
					overridePendingTransition(R.anim.myanimation_simple,
							R.anim.my_alpha_action);
					break;
				case 4:
//					Toast.makeText(MainActivity.this, getResources().getString(R.string.no_service), 1000).show();
					intent.setClass(MainActivity.this, Showpic.class);
					startActivity(intent);
					overridePendingTransition(R.anim.alpha_translate_rotate,
							R.anim.my_alpha_action);
					break;
				case 5:
					intent.setClass(MainActivity.this, Game_bb.class);
					startActivity(intent);
					overridePendingTransition(R.anim.alpha_translate_rotate,
							R.anim.my_alpha_action);
					break;
				case 6:
					intent.setClass(MainActivity.this, Takepic.class);
					startActivity(intent);
					overridePendingTransition(R.anim.alpha_translate_rotate,
							R.anim.my_alpha_action);
					break;
				}
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
