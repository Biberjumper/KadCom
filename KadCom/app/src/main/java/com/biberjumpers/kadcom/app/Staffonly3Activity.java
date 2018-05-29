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
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;

public class Staffonly3Activity extends Activity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> bann_map = new HashMap<>();
	
	private ArrayList<String> str = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> banned = new ArrayList<>();
	
	private LinearLayout linear_oben;
	private LinearLayout seite;
	private Button zuruek;
	private LinearLayout app_gebannte;
	private TextView textview_banned;
	private ListView listview1;
	private LinearLayout user_bannen;
	private EditText edittext_bann;
	private Button bannen;
	
	private SharedPreferences mode;
	private DatabaseReference App_gebannte = _firebase.getReference("App_gebannte");
	private ChildEventListener _App_gebannte_child_listener;
	private SharedPreferences namedata;
	private SharedPreferences Usernumber;
	private Calendar Callender = Calendar.getInstance();
	private AlertDialog.Builder Dialog;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.staffonly3);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		linear_oben = (LinearLayout) findViewById(R.id.linear_oben);
		seite = (LinearLayout) findViewById(R.id.seite);
		zuruek = (Button) findViewById(R.id.zuruek);
		app_gebannte = (LinearLayout) findViewById(R.id.app_gebannte);
		textview_banned = (TextView) findViewById(R.id.textview_banned);
		listview1 = (ListView) findViewById(R.id.listview1);
		user_bannen = (LinearLayout) findViewById(R.id.user_bannen);
		edittext_bann = (EditText) findViewById(R.id.edittext_bann);
		bannen = (Button) findViewById(R.id.bannen);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		namedata = getSharedPreferences("namedata", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		Dialog = new AlertDialog.Builder(this);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				Dialog.setMessage("User entbannen?");
				Dialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						App_gebannte.child(str.get((int)(_position))).removeValue();
						SketchwareUtil.showMessage(getApplicationContext(), "User entbannt");
					}
				});
				Dialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				Dialog.create().show();
				return true;
			}
		});
		
		bannen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Callender = Calendar.getInstance();
				bann_map.put("User", edittext_bann.getText().toString());
				bann_map.put("Gebannt von", namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")".concat(" Zeit: ".concat(new SimpleDateFormat("yyyy/MM/dd hh:mm").format(Callender.getTime())))))));
				App_gebannte.push().updateChildren(bann_map);
				SketchwareUtil.showMessage(getApplicationContext(), "User wurde aus der App verbannt");
				edittext_bann.setText("");
			}
		});
		
		_App_gebannte_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				App_gebannte.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						banned = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								banned.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview1.setAdapter(new Listview1Adapter(banned));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
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
				App_gebannte.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						banned = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								banned.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview1.setAdapter(new Listview1Adapter(banned));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
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
				App_gebannte.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						banned = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								banned.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview1.setAdapter(new Listview1Adapter(banned));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
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
		App_gebannte.addChildEventListener(_App_gebannte_child_listener);
	}
	private void initializeLogic() {
		if (mode.getString("mode", "").equals("0")) {
			linear_oben.setBackgroundColor(0xFF00B0FF);
			seite.setBackgroundColor(0xFF0277BD);
			app_gebannte.setBackgroundColor(0xFF00B0FF);
			zuruek.setTextColor(0xFFE91E63);
		}
		else {
			linear_oben.setBackgroundColor(0xFF37474F);
			seite.setBackgroundColor(0xFF546E7A);
			app_gebannte.setBackgroundColor(0xFF37474F);
			zuruek.setTextColor(0xFF2196F3);
		}
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
				_v = _inflater.inflate(R.layout.banned, null);
			}
			
			final LinearLayout linear = (LinearLayout) _v.findViewById(R.id.linear);
			final TextView banned_user = (TextView) _v.findViewById(R.id.banned_user);
			
			banned_user.setText(banned.get((int)_position).get("User").toString());
			
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
