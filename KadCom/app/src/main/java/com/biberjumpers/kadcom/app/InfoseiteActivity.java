package com.biberjumpers.kadcom.app;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class InfoseiteActivity extends Activity {
	
	
	private LinearLayout platzhalter_oben;
	private LinearLayout seite;
	private LinearLayout linear6;
	private Button zuruek;
	private LinearLayout linear10;
	private LinearLayout linear20;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private ImageView imageview1;
	private TextView statistik;
	private ImageView imageview2;
	private TextView app_info;
	private ImageView imageview3;
	private TextView kadcon_team;
	private ImageView imageview4;
	private TextView kadcom_team;
	private LinearLayout linear5;
	private LinearLayout linear7;
	private ImageView imageview5;
	private TextView user;
	private ImageView imageview7;
	private TextView bank;
	private ImageView imageview6;
	private TextView impressum;
	
	private SharedPreferences mode;
	private Intent I = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.infoseite);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		platzhalter_oben = (LinearLayout) findViewById(R.id.platzhalter_oben);
		seite = (LinearLayout) findViewById(R.id.seite);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		zuruek = (Button) findViewById(R.id.zuruek);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear20 = (LinearLayout) findViewById(R.id.linear20);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		statistik = (TextView) findViewById(R.id.statistik);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		app_info = (TextView) findViewById(R.id.app_info);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		kadcon_team = (TextView) findViewById(R.id.kadcon_team);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		kadcom_team = (TextView) findViewById(R.id.kadcom_team);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		user = (TextView) findViewById(R.id.user);
		imageview7 = (ImageView) findViewById(R.id.imageview7);
		bank = (TextView) findViewById(R.id.bank);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		impressum = (TextView) findViewById(R.id.impressum);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Deine Statistiken");
				I.setClass(getApplicationContext(), StatistikenActivity.class);
				startActivity(I);
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "App Infos");
				I.setClass(getApplicationContext(), InfosActivity.class);
				startActivity(I);
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Kadcon Team");
				I.setClass(getApplicationContext(), TeamActivity.class);
				startActivity(I);
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "KadCom Team");
				I.setClass(getApplicationContext(), KadcomTeamActivity.class);
				startActivity(I);
			}
		});
		
		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Alle registrierten User");
				I.setClass(getApplicationContext(), UserlistActivity.class);
				startActivity(I);
			}
		});
		
		imageview7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Konto Auszug");
				I.setClass(getApplicationContext(), KontoActivity.class);
				startActivity(I);
			}
		});
		
		imageview6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Impressum");
				I.setClass(getApplicationContext(), ImpressumActivity.class);
				startActivity(I);
			}
		});
	}
	private void initializeLogic() {
		if (mode.getString("mode", "").equals("0")) {
			platzhalter_oben.setBackgroundColor(0xFF00B0FF);
			seite.setBackgroundColor(0xFF0277BD);
			zuruek.setTextColor(0xFFE91E63);
			linear1.setBackgroundColor(0xFF00B0FF);
			linear2.setBackgroundColor(0xFF00B0FF);
			linear3.setBackgroundColor(0xFF00B0FF);
			linear4.setBackgroundColor(0xFF00B0FF);
			linear5.setBackgroundColor(0xFF00B0FF);
			linear6.setBackgroundColor(0xFF00B0FF);
			linear7.setBackgroundColor(0xFF00B0FF);
			statistik.setTextColor(0xFF000000);
			app_info.setTextColor(0xFF000000);
			kadcon_team.setTextColor(0xFF000000);
			kadcom_team.setTextColor(0xFF000000);
			user.setTextColor(0xFF000000);
			impressum.setTextColor(0xFF000000);
			bank.setTextColor(0xFF000000);
			imageview1.setImageResource(R.drawable.ic_assessment_black);
			imageview2.setImageResource(R.drawable.ic_description_black);
			imageview3.setImageResource(R.drawable.ic_people_black);
			imageview4.setImageResource(R.drawable.ic_supervisor_account_black);
			imageview5.setImageResource(R.drawable.ic_assignment_ind_black);
			imageview6.setImageResource(R.drawable.ic_assignment_black);
			imageview7.setImageResource(R.drawable.ic_account_balance_black);
		}
		else {
			platzhalter_oben.setBackgroundColor(0xFF37474F);
			seite.setBackgroundColor(0xFF455A64);
			zuruek.setTextColor(0xFF2196F3);
			linear1.setBackgroundColor(0xFF37474F);
			linear2.setBackgroundColor(0xFF37474F);
			linear3.setBackgroundColor(0xFF37474F);
			linear4.setBackgroundColor(0xFF37474F);
			linear5.setBackgroundColor(0xFF37474F);
			linear6.setBackgroundColor(0xFF37474F);
			linear7.setBackgroundColor(0xFF37474F);
			statistik.setTextColor(0xFFFFFFFF);
			app_info.setTextColor(0xFFFFFFFF);
			kadcon_team.setTextColor(0xFFFFFFFF);
			kadcom_team.setTextColor(0xFFFFFFFF);
			user.setTextColor(0xFFFFFFFF);
			impressum.setTextColor(0xFFFFFFFF);
			bank.setTextColor(0xFFFFFFFF);
			imageview1.setImageResource(R.drawable.ic_assessment_white);
			imageview2.setImageResource(R.drawable.ic_description_white);
			imageview3.setImageResource(R.drawable.ic_people_white);
			imageview4.setImageResource(R.drawable.ic_supervisor_account_white);
			imageview5.setImageResource(R.drawable.ic_assignment_ind_white);
			imageview6.setImageResource(R.drawable.ic_assignment_white);
			imageview7.setImageResource(R.drawable.ic_account_balance_white);
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
