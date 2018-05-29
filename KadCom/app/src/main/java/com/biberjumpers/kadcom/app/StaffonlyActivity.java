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
import android.content.SharedPreferences;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

public class StaffonlyActivity extends Activity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> Updatemap = new HashMap<>();
	private HashMap<String, Object> entbannt_map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> banned_user_map = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> entbannt_user_map = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> name_changed_map = new ArrayList<>();
	private ArrayList<String> str = new ArrayList<>();
	
	private LinearLayout linear_oben;
	private LinearLayout linear_seite;
	private Button zuruek;
	private LinearLayout linear1;
	private Button mehr;
	private LinearLayout update_linear;
	private LinearLayout linear_banned;
	private LinearLayout linear_entbannt;
	private LinearLayout linear_name_changed;
	private Button update_setzen;
	private TextView text_banned;
	private ListView listview1;
	private TextView text_entbannt;
	private ListView listview2;
	private TextView text_name_changed;
	private ListView listview3;
	
	private SharedPreferences mode;
	private DatabaseReference Update = _firebase.getReference("Update");
	private ChildEventListener _Update_child_listener;
	private AlertDialog.Builder Dialog;
	private DatabaseReference Chat_entbannt = _firebase.getReference("Chat_entbannt");
	private ChildEventListener _Chat_entbannt_child_listener;
	private Calendar Calender = Calendar.getInstance();
	private SharedPreferences namedata;
	private SharedPreferences Usernumber;
	private DatabaseReference Name_changed = _firebase.getReference("Name_changed");
	private ChildEventListener _Name_changed_child_listener;
	private Intent I = new Intent();
	private DatabaseReference Chat_banned = _firebase.getReference("Chat_banned");
	private ChildEventListener _Chat_banned_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.staffonly);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		linear_oben = (LinearLayout) findViewById(R.id.linear_oben);
		linear_seite = (LinearLayout) findViewById(R.id.linear_seite);
		zuruek = (Button) findViewById(R.id.zuruek);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		mehr = (Button) findViewById(R.id.mehr);
		update_linear = (LinearLayout) findViewById(R.id.update_linear);
		linear_banned = (LinearLayout) findViewById(R.id.linear_banned);
		linear_entbannt = (LinearLayout) findViewById(R.id.linear_entbannt);
		linear_name_changed = (LinearLayout) findViewById(R.id.linear_name_changed);
		update_setzen = (Button) findViewById(R.id.update_setzen);
		text_banned = (TextView) findViewById(R.id.text_banned);
		listview1 = (ListView) findViewById(R.id.listview1);
		text_entbannt = (TextView) findViewById(R.id.text_entbannt);
		listview2 = (ListView) findViewById(R.id.listview2);
		text_name_changed = (TextView) findViewById(R.id.text_name_changed);
		listview3 = (ListView) findViewById(R.id.listview3);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		Dialog = new AlertDialog.Builder(this);
		namedata = getSharedPreferences("namedata", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		mehr.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				I.setClass(getApplicationContext(), Staffonly2Activity.class);
				startActivity(I);
			}
		});
		
		update_setzen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Update gesetzt");
				Updatemap = new HashMap<>();
				Updatemap.put("Update", "1.2");
				Update.push().updateChildren(Updatemap);
				Updatemap.clear();
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				Dialog.setMessage("Willst du diesen User im Chat freigeben?");
				Dialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						Calender = Calendar.getInstance();
						entbannt_map = new HashMap<>();
						entbannt_map.put("Entbannt", "\"".concat(banned_user_map.get((int)_position).get("User").toString().concat("\" ".concat("entbannt von: ".concat(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")".concat(" Zeit:".concat(new SimpleDateFormat("yyyy/MM/dd hh:mm").format(Calender.getTime())))))))))));
						Chat_entbannt.push().updateChildren(entbannt_map);
						entbannt_map.clear();
						Chat_banned.child(str.get((int)(_position))).removeValue();
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
		
		_Update_child_listener = new ChildEventListener() {
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
		Update.addChildEventListener(_Update_child_listener);
		
		_Chat_entbannt_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Chat_entbannt.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						entbannt_user_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								entbannt_user_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview2.setAdapter(new Listview2Adapter(entbannt_user_map));
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
				Chat_entbannt.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						entbannt_user_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								entbannt_user_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview2.setAdapter(new Listview2Adapter(entbannt_user_map));
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
				Chat_entbannt.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						entbannt_user_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								entbannt_user_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview2.setAdapter(new Listview2Adapter(entbannt_user_map));
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
		Chat_entbannt.addChildEventListener(_Chat_entbannt_child_listener);
		
		_Name_changed_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Name_changed.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						name_changed_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								name_changed_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview3.setAdapter(new Listview3Adapter(name_changed_map));
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
				Name_changed.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						name_changed_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								name_changed_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview3.setAdapter(new Listview3Adapter(name_changed_map));
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
				Name_changed.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						name_changed_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								name_changed_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview3.setAdapter(new Listview3Adapter(name_changed_map));
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
		Name_changed.addChildEventListener(_Name_changed_child_listener);
		
		_Chat_banned_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Chat_banned.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						banned_user_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								banned_user_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview1.setAdapter(new Listview1Adapter(banned_user_map));
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
				Chat_banned.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						banned_user_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								banned_user_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview1.setAdapter(new Listview1Adapter(banned_user_map));
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
				Chat_banned.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						banned_user_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								banned_user_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview1.setAdapter(new Listview1Adapter(banned_user_map));
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
		Chat_banned.addChildEventListener(_Chat_banned_child_listener);
	}
	private void initializeLogic() {
		if (mode.getString("mode", "").equals("0")) {
			linear_oben.setBackgroundColor(0xFF2196F3);
			linear_seite.setBackgroundColor(0xFF00B0FF);
			update_linear.setBackgroundColor(0xFF0091EA);
			linear_banned.setBackgroundColor(0xFF0277BD);
			linear_entbannt.setBackgroundColor(0xFF0091EA);
			linear_name_changed.setBackgroundColor(0xFF0277BD);
			zuruek.setTextColor(0xFFE91E63);
			update_setzen.setTextColor(0xFF8BC34A);
			mehr.setTextColor(0xFF4CAF50);
		}
		else {
			linear_oben.setBackgroundColor(0xFF37474F);
			linear_seite.setBackgroundColor(0xFF455A64);
			update_linear.setBackgroundColor(0xFF37474F);
			linear_banned.setBackgroundColor(0xFF546E7A);
			linear_entbannt.setBackgroundColor(0xFF37474F);
			linear_name_changed.setBackgroundColor(0xFF546E7A);
			zuruek.setTextColor(0xFF2196F3);
			update_setzen.setTextColor(0xFF8BC34A);
			mehr.setTextColor(0xFF673AB7);
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
			
			banned_user.setText(banned_user_map.get((int)_position).get("User").toString());
			
			return _v;
		}
	}
	
	public class Listview2Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
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
			
			banned_user.setText(entbannt_user_map.get((int)_position).get("Entbannt").toString());
			
			return _v;
		}
	}
	
	public class Listview3Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview3Adapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_v = _inflater.inflate(R.layout.name_changed, null);
			}
			
			final LinearLayout linear = (LinearLayout) _v.findViewById(R.id.linear);
			final TextView text = (TextView) _v.findViewById(R.id.text);
			
			text.setText(name_changed_map.get((int)_position).get("Name").toString());
			
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
