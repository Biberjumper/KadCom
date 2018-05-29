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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.SharedPreferences;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import android.widget.AdapterView;

public class UserlistActivity extends Activity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private boolean connected = false;
	private String command = "";
	
	private ArrayList<HashMap<String, Object>> usermap = new ArrayList<>();
	
	private LinearLayout linear_oben;
	private LinearLayout seite;
	private Button zuruek;
	private ListView listview1;
	private LinearLayout kein_netz;
	private LinearLayout loading_linear;
	private TextView kein_netz_text;
	
	private SharedPreferences mode;
	private DatabaseReference Name = _firebase.getReference("Name");
	private ChildEventListener _Name_child_listener;
	private SharedPreferences namedata;
	private SharedPreferences Usernumber;
	private TimerTask Timer;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.userlist);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		linear_oben = (LinearLayout) findViewById(R.id.linear_oben);
		seite = (LinearLayout) findViewById(R.id.seite);
		zuruek = (Button) findViewById(R.id.zuruek);
		listview1 = (ListView) findViewById(R.id.listview1);
		kein_netz = (LinearLayout) findViewById(R.id.kein_netz);
		loading_linear = (LinearLayout) findViewById(R.id.loading_linear);
		kein_netz_text = (TextView) findViewById(R.id.kein_netz_text);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		namedata = getSharedPreferences("namedata", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				
			}
		});
		
		_Name_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Name.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						usermap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								usermap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(usermap));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Name.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						usermap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								usermap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(usermap));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Name.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						usermap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								usermap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(usermap));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final String _errorCode = String.valueOf(_param1.getCode());
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		Name.addChildEventListener(_Name_child_listener);
	}
	private void initializeLogic() {
		loading_linear.setVisibility(View.VISIBLE);
		if (mode.getString("mode", "").equals("0")) {
			linear_oben.setBackgroundColor(0xFF00B0FF);
			zuruek.setTextColor(0xFFE91E63);
			seite.setBackgroundColor(0xFF2196F3);
		}
		else {
			linear_oben.setBackgroundColor(0xFF37474F);
			zuruek.setTextColor(0xFF2196F3);
			seite.setBackgroundColor(0xFF546E7A);
		}
		_Check();
		if (connected) {
			loading_linear.setVisibility(View.VISIBLE);
			ProgressBar cpb = new ProgressBar(UserlistActivity.this);
			loading_linear.addView(cpb);
			Name.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
					usermap = new ArrayList<>();
					try {
						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						for (DataSnapshot _data : _dataSnapshot.getChildren()) {
							HashMap<String, Object> _map = _data.getValue(_ind);
							usermap.add(_map);
						}
					}
					catch (Exception _e) {
						_e.printStackTrace();
					}
					listview1.setAdapter(new Listview1Adapter(usermap));
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
			});
			kein_netz.setVisibility(View.GONE);
			Timer = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							loading_linear.setVisibility(View.GONE);
						}
					});
				}
			};
			_timer.schedule(Timer, (int)(2457));
		}
		else {
			kein_netz.setVisibility(View.VISIBLE);
			listview1.setVisibility(View.GONE);
			loading_linear.setVisibility(View.GONE);
		}
	}
	
	private void _Check () {
		try{
			command = "ping -c 1 google.com";
			connected = (Runtime.getRuntime().exec (command).waitFor() == 0); } catch (Exception e){ showMessage(e.toString());}
	}
	
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.user_custom, null);
			}
			
			final LinearLayout linear_name = (LinearLayout) _v.findViewById(R.id.linear_name);
			final TextView name = (TextView) _v.findViewById(R.id.name);
			
			name.setText(usermap.get((int)_position).get("Name").toString());
			if (namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))).equals(name.getText().toString())) {
				if (mode.getString("mode", "").equals("0")) {
					linear_name.setBackgroundColor(0xFF80D8FF);
				}
				else {
					name.setTextColor(0xFFFFFFFF);
					linear_name.setBackgroundColor(0xFF78909C);
				}
			}
			else {
				if (mode.getString("mode", "").equals("0")) {
					linear_name.setBackgroundColor(0xFF40C4FF);
				}
				else {
					name.setTextColor(0xFFFFFFFF);
					linear_name.setBackgroundColor(0xFF455A64);
				}
			}
			
			return _v;
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
