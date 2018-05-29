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
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;

public class BewertenActivity extends Activity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> rate = new HashMap<>();
	private String command = "";
	private boolean connected = false;
	
	private LinearLayout platzhalter_oben;
	private LinearLayout platzhalter2;
	private Button zuruek;
	private LinearLayout sterne;
	private EditText kommentar;
	private Button senden;
	private TextView textview;
	private TextView text1;
	
	private SharedPreferences namedata;
	private SharedPreferences mode;
	private SharedPreferences rated;
	private TimerTask Timer;
	private SharedPreferences Usernumber;
	private DatabaseReference AppStats = _firebase.getReference("AppStats");
	private ChildEventListener _AppStats_child_listener;
	private SharedPreferences rate_comment;
	private SharedPreferences rate_number;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.bewerten);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		platzhalter_oben = (LinearLayout) findViewById(R.id.platzhalter_oben);
		platzhalter2 = (LinearLayout) findViewById(R.id.platzhalter2);
		zuruek = (Button) findViewById(R.id.zuruek);
		sterne = (LinearLayout) findViewById(R.id.sterne);
		kommentar = (EditText) findViewById(R.id.kommentar);
		senden = (Button) findViewById(R.id.senden);
		textview = (TextView) findViewById(R.id.textview);
		text1 = (TextView) findViewById(R.id.text1);
		namedata = getSharedPreferences("namedata", Activity.MODE_PRIVATE);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		rated = getSharedPreferences("rated", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		rate_comment = getSharedPreferences("rate_comment", Activity.MODE_PRIVATE);
		rate_number = getSharedPreferences("rate_number", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		senden.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				_check();
				if (connected) {
					rate_comment.edit().putString("rate_comment", kommentar.getText().toString()).commit();
					rate_number.edit().putString("rate_number", text1.getText().toString()).commit();
					final ProgressDialog prog = new ProgressDialog(BewertenActivity.this);
					prog.setMax(100);prog.setTitle("Bewerten");
					prog.setMessage("Bewertung wird gesendet");
					prog.setIndeterminate(true);
					prog.show();
					Timer = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									prog.hide();
								}
							});
						}
					};
					_timer.schedule(Timer, (int)(2000));
					text1.setText(String.valueOf(rb.getRating()));
					rate = new HashMap<>();
					rate.put("Bewertung", text1.getText().toString());
					rate.put("Kommentar", kommentar.getText().toString());
					AppStats.child(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")")))).updateChildren(rate);
					rate.clear();
					rated.edit().putString("rated", "-").commit();
					Timer = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									SketchwareUtil.showMessage(getApplicationContext(), "Danke das du die App bewertet hast!");
									finish();
								}
							});
						}
					};
					_timer.schedule(Timer, (int)(2200));
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Du hast keine oder zu schlechte Verbindung");
				}
			}
		});
		
		_AppStats_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final String _errorCode = String.valueOf(_param1.getCode());
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		AppStats.addChildEventListener(_AppStats_child_listener);
	}
	private void initializeLogic() {
		SketchwareUtil.showMessage(getApplicationContext(), "Bitte keine Sonderzeichen verwenden!");
		text1.setVisibility(View.GONE);
		if (mode.getString("mode", "").equals("0")) {
			platzhalter_oben.setBackgroundColor(0xFF00B0FF);
			platzhalter2.setBackgroundColor(0xFF2196F3);
			sterne.setBackgroundColor(0xFFEF5350);
			senden.setTextColor(0xFFE91E63);
			zuruek.setTextColor(0xFFE91E63);
		}
		else {
			platzhalter_oben.setBackgroundColor(0xFF37474F);
			platzhalter2.setBackgroundColor(0xFF455A64);
			int[] colors = {Color.rgb(138,41,81),Color.rgb(41,53,158)};
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.BR_TL, colors);
			gd.setCornerRadius(0f); 
			gd.setStroke(0,Color.WHITE);
			if(android.os.Build.VERSION.SDK_INT >= 16) {sterne.setBackground(gd); } else {sterne.setBackgroundDrawable(gd);}
			senden.setTextColor(0xFF2196F3);
			zuruek.setTextColor(0xFF2196F3);
		}
		rb = new RatingBar(this);
		rb.setNumStars(5);
		rb.setStepSize(0.25f);
		rb.setRating(2.5f);
		sterne.addView(rb);
	}
	RatingBar rb;
	
	private void nothing(){
	}
	
	private void _check () {
		try{
			command = "ping -c 1 google.com";
			connected = (Runtime.getRuntime().exec (command).waitFor() == 0); } catch (Exception e){ showMessage(e.toString());}
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
