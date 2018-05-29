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
import android.widget.ScrollView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.content.ClipData;
import android.content.ClipboardManager;

public class KadcomTeamActivity extends Activity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String a = "";
	private HashMap<String, Object> joinmap = new HashMap<>();
	
	private LinearLayout platzhalter_oben;
	private ScrollView vscroll;
	private Button zuruek;
	private LinearLayout seite;
	private TextView biberjumper;
	private TextView lechos;
	private LinearLayout linear2;
	private TextView text1;
	private TextView text2;
	private TextView text3;
	private LinearLayout linear;
	private EditText pass;
	private Button forteam;
	
	private SharedPreferences mode;
	private Intent i = new Intent();
	private TimerTask Timer;
	private SharedPreferences namedata;
	private SharedPreferences Usernumber;
	private DatabaseReference StaffOnly = _firebase.getReference("StaffOnly");
	private ChildEventListener _StaffOnly_child_listener;
	private Calendar Calender = Calendar.getInstance();
	private AlertDialog.Builder edittextdialog;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.kadcom_team);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		platzhalter_oben = (LinearLayout) findViewById(R.id.platzhalter_oben);
		vscroll = (ScrollView) findViewById(R.id.vscroll);
		zuruek = (Button) findViewById(R.id.zuruek);
		seite = (LinearLayout) findViewById(R.id.seite);
		biberjumper = (TextView) findViewById(R.id.biberjumper);
		lechos = (TextView) findViewById(R.id.lechos);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		linear = (LinearLayout) findViewById(R.id.linear);
		pass = (EditText) findViewById(R.id.pass);
		forteam = (Button) findViewById(R.id.forteam);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		namedata = getSharedPreferences("namedata", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		edittextdialog = new AlertDialog.Builder(this);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		biberjumper.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				PopupMenu popup = new PopupMenu(KadcomTeamActivity.this, text3);
				Menu menu = popup.getMenu();
				menu.add("Forum");
				menu.add("Instagram");
				menu.add("Mail");
				biberjumper.setTextColor(0xFF607D8B);
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								biberjumper.setTextColor(0xFF000000);
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(100));
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item){
						switch (item.getTitle().toString()){
							case "Forum":
							i.setAction(Intent.ACTION_VIEW);
							i.setData(Uri.parse("https://forum.kadcon.de/User/18533-Biberjumper/"));
							startActivity(i);
							break;
							case "Instagram":
							i.setAction(Intent.ACTION_VIEW);
							i.setData(Uri.parse("http://instagram.com/monsieur_klaus"));
							startActivity(i);
							break;
							case "Mail":
							i.setAction(Intent.ACTION_VIEW);
							i.setData(Uri.parse("mailto:kadze.biberjumper@gmail.com"));
							startActivity(i);
							break;}
						return true;
					}
				});
				popup.show();
			}
		});
		
		lechos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				PopupMenu popup = new PopupMenu(KadcomTeamActivity.this, text3);
				Menu menu = popup.getMenu();
				menu.add("Forum");
				lechos.setTextColor(0xFF607D8B);
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								lechos.setTextColor(0xFF000000);
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(100));
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item){
						switch (item.getTitle().toString()){
							case "Forum":
							i.setData(Uri.parse("https://forum.kadcon.de/User/17936-Lechos13/"));
							startActivity(i);
							break;}
						return true;
					}
				});
				popup.show();
			}
		});
		
		text3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				PopupMenu popup = new PopupMenu(KadcomTeamActivity.this, text3);
				Menu menu = popup.getMenu();
				menu.add("Öffnen");
				menu.add("Kopieren");
				menu.add("Versenden");
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item){
						switch (item.getTitle().toString()){
							case "Öffnen":
							i.setData(Uri.parse("http://biberjumpers-kadcon-App.jimdo.com/bewerben"));
							i.setAction(Intent.ACTION_VIEW);
							startActivity(i);
							break;
							case "Kopieren":
							((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", "http://biberjumpers-kadcon-app.jimdo.com"));
							SketchwareUtil.showMessage(getApplicationContext(), "Kopiert");
							break;
							case "Versenden":
							a = "http://biberjumpers-kadcon-app.jimdo.com".concat("\n\nÜber KadCom gesendet");
							Intent i = new Intent(android.content.Intent.ACTION_SEND); i.setType("text/plain"); i.putExtra(android.content.Intent.EXTRA_TEXT,a); startActivity(Intent.createChooser(i,"Link versenden"));
							SketchwareUtil.showMessage(getApplicationContext(), "📤");
							break;}
						return true;
					}
				});
				popup.show();
			}
		});
		
		forteam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (pass.getText().toString().length() < 2) {
					SketchwareUtil.showMessage(getApplicationContext(), "Das Passwort ist zu kurtz");
				}
				else {
					if (pass.getText().toString().equals("9178")) {
						pass.setText("");
						Calender = Calendar.getInstance();
						joinmap = new HashMap<>();
						joinmap.put(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))), new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(Calender.getTime()));
						StaffOnly.child("Last StaffRoom Join").updateChildren(joinmap);
						joinmap.clear();
						SketchwareUtil.showMessage(getApplicationContext(), "Richtig");
						i.setClass(getApplicationContext(), StaffonlyActivity.class);
						startActivity(i);
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "Falsches Psswort");
						joinmap = new HashMap<>();
						joinmap.put(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(") ".concat("Passwort: ".concat(pass.getText().toString()))))), new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(Calender.getTime()));
						StaffOnly.child("Wrong Passwort").updateChildren(joinmap);
						joinmap.clear();
					}
				}
			}
		});
		
		_StaffOnly_child_listener = new ChildEventListener() {
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
		StaffOnly.addChildEventListener(_StaffOnly_child_listener);
	}
	private void initializeLogic() {
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		pass.setVisibility(View.VISIBLE);
		if (mode.getString("mode", "").equals("0")) {
			platzhalter_oben.setBackgroundColor(0xFF00B0FF);
			seite.setBackgroundColor(0xFF0288D1);
			zuruek.setTextColor(0xFFE91E63);
			biberjumper.setTextColor(0xFF000000);
			lechos.setTextColor(0xFF000000);
			linear2.setBackgroundColor(0xFF000000);
			text1.setTextColor(0xFF000000);
			text2.setTextColor(0xFF000000);
			text3.setTextColor(0xFF000000);
			
			vscroll.setBackgroundColor(0xFF0288D1);
		}
		else {
			platzhalter_oben.setBackgroundColor(0xFF37474F);
			seite.setBackgroundColor(0xFF455A64);
			zuruek.setTextColor(0xFF2196F3);
			biberjumper.setTextColor(0xFF000000);
			lechos.setTextColor(0xFF000000);
			linear2.setBackgroundColor(0xFF000000);
			text1.setTextColor(0xFF000000);
			text2.setTextColor(0xFF000000);
			text3.setTextColor(0xFF000000);
			
			vscroll.setBackgroundColor(0xFF455A64);
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
