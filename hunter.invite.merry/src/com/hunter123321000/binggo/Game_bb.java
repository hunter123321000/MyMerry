package com.hunter123321000.binggo;






import com.hunter123321000.merry.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Game_bb extends Activity {

	private int i_max = 25;
	private Button[] btn = new Button[i_max];
	private String[] s_btn_value = new String[i_max], s_ary_binggo;
	private BTN_onclicklistener btn_onclicklistener;
	private int[] res_ofbtn = new int[i_max], i_ary_bbcase = new int[12];
	private boolean b_binggo = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_bb);

		// 判斷是否第一次執行，進行存取25個數值
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"share", MODE_PRIVATE);
		boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
		Editor editor = sharedPreferences.edit();
		if (isFirstRun) {
			editor.putBoolean("isFirstRun", false);
			s_btn_value = randomCommon(1, i_max, i_max);
			for (int i = 0; i < i_max; i++) {
				editor.putString(String.valueOf(i), s_btn_value[i]);
			}
			editor.commit();
		} else {
			for (int i = 0; i < i_max; i++)
				s_btn_value[i] = sharedPreferences.getString(String.valueOf(i),
						"");
		}
		getView();
	}

	private void getView() {
		btn[0] = (Button) findViewById(R.id.btn_0);
		btn[1] = (Button) findViewById(R.id.btn_1);
		btn[2] = (Button) findViewById(R.id.btn_2);
		btn[3] = (Button) findViewById(R.id.btn_3);
		btn[4] = (Button) findViewById(R.id.btn_4);
		btn[5] = (Button) findViewById(R.id.btn_5);
		btn[6] = (Button) findViewById(R.id.btn_6);
		btn[7] = (Button) findViewById(R.id.btn_7);
		btn[8] = (Button) findViewById(R.id.btn_8);
		btn[9] = (Button) findViewById(R.id.btn_9);
		btn[10] = (Button) findViewById(R.id.btn_10);
		btn[11] = (Button) findViewById(R.id.btn_11);
		btn[12] = (Button) findViewById(R.id.btn_12);
		btn[13] = (Button) findViewById(R.id.btn_13);
		btn[14] = (Button) findViewById(R.id.btn_14);
		btn[15] = (Button) findViewById(R.id.btn_15);
		btn[16] = (Button) findViewById(R.id.btn_16);
		btn[17] = (Button) findViewById(R.id.btn_17);
		btn[18] = (Button) findViewById(R.id.btn_18);
		btn[19] = (Button) findViewById(R.id.btn_19);
		btn[20] = (Button) findViewById(R.id.btn_20);
		btn[21] = (Button) findViewById(R.id.btn_21);
		btn[22] = (Button) findViewById(R.id.btn_22);
		btn[23] = (Button) findViewById(R.id.btn_23);
		btn[24] = (Button) findViewById(R.id.btn_24);

		//
		Typeface font = Typeface.createFromAsset(getResources().getAssets(), "fonts/lotsofdotz.ttf");
		//
		btn_onclicklistener = new BTN_onclicklistener();
		for (int i = 0; i < i_max; i++) {
			btn[i].setOnClickListener(btn_onclicklistener);
			btn[i].setText(s_btn_value[i].toString());
			btn[i].setTag("0");
			btn[i].setTypeface(font);		
			btn[i].setTextColor(Color.BLUE);
			res_ofbtn[i] = btn[i].getId();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static String[] randomCommon(int min, int max, int n) {
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		String[] result = new String[n];
		for (int i = 0; i < n; i++) {
			result[i] = "0";
			Log.i("0.0", "result[" + i + "]=" + result[i]);
		}
		int count = 0;
		while (count < n) {
			int num = (int) (Math.random() * (max)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				Log.i("0.0", "num=" + num + "   result[" + j + "]=" + result[j]);
				if (num == Integer.valueOf(result[j])) {
					flag = false;
					break;
				}
			}
			if (flag) {
				Log.i("0.0", "num=" + num + "   count=" + count);
				result[count] = String.valueOf(num);
				count++;
			}
		}
		return result;
	}

	private class BTN_onclicklistener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// Log.i("0.0",
			// "v.getId()="+v.getId()+"   res_ofbtn[0]="+res_ofbtn[0]);
			for (int i = 0; i < i_max; i++) {
				if (v.getId() == res_ofbtn[i]) {							
					if (btn[i].getTag().toString().equals("0")) {
						Typeface font = Typeface.createFromAsset(getResources().getAssets(), "fonts/lcdd.ttf");
//						btn[i].setTypeface(font);	
						btn[i].setBackgroundDrawable(getResources()
								.getDrawable(R.drawable.hit));
						btn[i].setTextColor(Color.RED);
						btn[i].setTag("1");
						check_binggo();
						if (b_binggo == true) {
							Toast.makeText(getApplicationContext(), "BingGo",
									1000).show();
							drawline();
						}
					} else {
						Typeface font = Typeface.createFromAsset(getResources().getAssets(), "fonts/lotsofdotz.ttf");
						btn[i].setTypeface(font);
						btn[i].setTextColor(Color.BLUE);
						btn[i].setBackgroundDrawable(getResources()
								.getDrawable(R.drawable.frame));
						btn[i].setTag("0");
					}
				}
			}
		}

	}

	private void check_binggo() {
		i_ary_bbcase = new int[12];
		b_binggo = false;
		s_ary_binggo = getResources().getStringArray(R.array.binggo);
		for (int i = 0; i < s_ary_binggo.length; i++) {
			int count = 0;
			for (int j = 0; j < s_ary_binggo[i].length(); j++) {
				if (s_ary_binggo[i].toString().substring(j, j + 1).equals("1")) {
					if (s_ary_binggo[i].toString().substring(j, j + 1)
							.equals(btn[j].getTag().toString()) == true) {
						count++;
						if (count == 5) {
							b_binggo = true;
							i_ary_bbcase[i] = i;
							Log.i("0.0", "i_ary_bbcase[i]=" + i_ary_bbcase[i]);
						}
					}
				}
			}
		}
	}

	private void drawline() {
		for (int i = 0; i < i_ary_bbcase.length; i++) {
			if (i_ary_bbcase[i] != 0) {
				switch(i_ary_bbcase[i]){
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
					for (int j = 0; j < i_max; j++) {
						if (s_ary_binggo[i_ary_bbcase[i]].substring(j, j + 1).equals("1")) {
							btn[j].setBackgroundDrawable(getResources().getDrawable(R.drawable.vertical));
						}
					}
					break;
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
					for (int j = 0; j < i_max; j++) {
						if (s_ary_binggo[i_ary_bbcase[i]].substring(j, j + 1).equals("1")) {
							btn[j].setBackgroundDrawable(getResources().getDrawable(R.drawable.horizontal));
						}
					}
					break;
				case 10:
					for (int j = 0; j < i_max; j++) {
						if (s_ary_binggo[i_ary_bbcase[i]].substring(j, j + 1).equals("1")) {
							btn[j].setBackgroundDrawable(getResources().getDrawable(R.drawable.slash1));
						}
					}
					break;
				case 11:
					for (int j = 0; j < i_max; j++) {
						if (s_ary_binggo[i_ary_bbcase[i]].substring(j, j + 1).equals("1")) {
							btn[j].setBackgroundDrawable(getResources().getDrawable(R.drawable.slash2));
						}
					}
					break;
				}				
			}
		}
	}
}
