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
import android.widget.ImageView;
import android.widget.Button;
import android.content.SharedPreferences;
import android.view.View;

public class PicShowActivity extends Activity {
	
	
	private String picture_str = "";
	
	private LinearLayout linear_oben;
	private ImageView imageview;
	private Button zuruek;
	
	private SharedPreferences picture_show;
	private SharedPreferences mode;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.pic_show);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		linear_oben = (LinearLayout) findViewById(R.id.linear_oben);
		imageview = (ImageView) findViewById(R.id.imageview);
		zuruek = (Button) findViewById(R.id.zuruek);
		picture_show = getSharedPreferences("picture_show", Activity.MODE_PRIVATE);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}
	private void initializeLogic() {
		if (mode.getString("mode", "").equals("0")) {
			linear_oben.setBackgroundColor(0xFF00B0FF);
			zuruek.setTextColor(0xFFE91E63);
		}
		else {
			linear_oben.setBackgroundColor(0xFF37474F);
			zuruek.setTextColor(0xFF2196F3);
		}
		picture_str = picture_show.getString("picture_show", "");
		byte[] imageBytes = Base64.decode(picture_str, Base64.DEFAULT);
		
		Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
		
		imageview.setImageBitmap(decodedImage);
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
