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
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;
import android.content.SharedPreferences;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;

public class NameActivity extends Activity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> NameMap = new HashMap<>();
	private boolean connected = false;
	private String command = "";
	private HashMap<String, Object> Usermap = new HashMap<>();
	
	private LinearLayout ganze_seite;
	private LinearLayout platzhalter_oben;
	private EditText name;
	private Button send;
	private TextView text;
	private Button zuruek_knopf;
	
	private TimerTask Timer;
	private SharedPreferences namedata;
	private SharedPreferences mode;
	private SharedPreferences Usernumber;
	private SharedPreferences first;
	private DatabaseReference Name = _firebase.getReference("Name");
	private ChildEventListener _Name_child_listener;
	private DatabaseReference AppStats = _firebase.getReference("AppStats");
	private ChildEventListener _AppStats_child_listener;
	private DatabaseReference Chat_user = _firebase.getReference("Chat_user");
	private ChildEventListener _Chat_user_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.name);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		ganze_seite = (LinearLayout) findViewById(R.id.ganze_seite);
		platzhalter_oben = (LinearLayout) findViewById(R.id.platzhalter_oben);
		name = (EditText) findViewById(R.id.name);
		send = (Button) findViewById(R.id.send);
		text = (TextView) findViewById(R.id.text);
		zuruek_knopf = (Button) findViewById(R.id.zuruek_knopf);
		namedata = getSharedPreferences("namedata", Activity.MODE_PRIVATE);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		first = getSharedPreferences("first", Activity.MODE_PRIVATE);
		
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				_check();
				if (connected) {
					if (name.getText().toString().length() > 2) {
						NameMap = new HashMap<>();
						zuruek_knopf.setVisibility(View.GONE);
						AppStats.child(Usernumber.getString("Usernumber", "")).removeValue();
						NameMap.put("Name", name.getText().toString().concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))));
						Name.push().updateChildren(NameMap);
						SketchwareUtil.showMessage(getApplicationContext(), "Registrierung erfolgreich!");
						namedata.edit().putString("namedata", name.getText().toString()).commit();
						Timer = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										SketchwareUtil.showMessage(getApplicationContext(), "Starte die App bitte neu!");
									}
								});
							}
						};
						_timer.schedule(Timer, (int)(2000));
						NameMap.clear();
						Usermap.put("User", namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))));
						Chat_user.push().updateChildren(Usermap);
						name.setVisibility(View.GONE);
						send.setVisibility(View.GONE);
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "Das ist kein gültiger Name");
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Du hast keine oder zu schlechte Verbindung");
				}
			}
		});
		
		zuruek_knopf.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		_Name_child_listener = new ChildEventListener() {
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
		Name.addChildEventListener(_Name_child_listener);
		
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
		
		_Chat_user_child_listener = new ChildEventListener() {
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
		Chat_user.addChildEventListener(_Chat_user_child_listener);
	}
	private void initializeLogic() {
		SketchwareUtil.showMessage(getApplicationContext(), "Bitte keine Sonderzeichen verwenden!");
		if (mode.getString("mode", "").equals("0")) {
			ganze_seite.setBackgroundColor(0xFF64B5F6);
			text.setTextColor(0xFF000000);
			send.setTextColor(0xFFE91E63);
			zuruek_knopf.setTextColor(0xFFE91E63);
			platzhalter_oben.setBackgroundColor(0xFF00B0FF);
		}
		else {
			ganze_seite.setBackgroundColor(0xFF455A64);
			text.setTextColor(0xFFFFFFFF);
			send.setTextColor(0xFF2196F3);
			zuruek_knopf.setTextColor(0xFF2196F3);
			platzhalter_oben.setBackgroundColor(0xFF37474F);
		}
	}
	
	@Override
	public void onBackPressed() {
		
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
