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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

public class Staffonly2Activity extends Activity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> changermap = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> deleted_map = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> reported_map = new ArrayList<>();
	private ArrayList<String> str = new ArrayList<>();
	
	private LinearLayout linear_oben;
	private LinearLayout ganze_seite;
	private Button zuruek;
	private LinearLayout linear1;
	private Button mehr;
	private LinearLayout linear_name_changer;
	private LinearLayout linear_deleted;
	private LinearLayout gemeldet_linear;
	private TextView text_name_changer;
	private TextView text_manager;
	private EditText edittext_changer;
	private Button setzen;
	private TextView text_deleted;
	private ListView listview1;
	private TextView textview1;
	private ListView listview2;
	
	private DatabaseReference Name_changer = _firebase.getReference("Name_changer");
	private ChildEventListener _Name_changer_child_listener;
	private SharedPreferences mode;
	private DatabaseReference Deleted = _firebase.getReference("Deleted");
	private ChildEventListener _Deleted_child_listener;
	private DatabaseReference Reported = _firebase.getReference("Reported");
	private ChildEventListener _Reported_child_listener;
	private AlertDialog.Builder Dialog1;
	private DatabaseReference Chat = _firebase.getReference("Chat");
	private ChildEventListener _Chat_child_listener;
	private Intent I = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.staffonly2);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		linear_oben = (LinearLayout) findViewById(R.id.linear_oben);
		ganze_seite = (LinearLayout) findViewById(R.id.ganze_seite);
		zuruek = (Button) findViewById(R.id.zuruek);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		mehr = (Button) findViewById(R.id.mehr);
		linear_name_changer = (LinearLayout) findViewById(R.id.linear_name_changer);
		linear_deleted = (LinearLayout) findViewById(R.id.linear_deleted);
		gemeldet_linear = (LinearLayout) findViewById(R.id.gemeldet_linear);
		text_name_changer = (TextView) findViewById(R.id.text_name_changer);
		text_manager = (TextView) findViewById(R.id.text_manager);
		edittext_changer = (EditText) findViewById(R.id.edittext_changer);
		setzen = (Button) findViewById(R.id.setzen);
		text_deleted = (TextView) findViewById(R.id.text_deleted);
		listview1 = (ListView) findViewById(R.id.listview1);
		textview1 = (TextView) findViewById(R.id.textview1);
		listview2 = (ListView) findViewById(R.id.listview2);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		Dialog1 = new AlertDialog.Builder(this);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		mehr.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				I.setClass(getApplicationContext(), Staffonly3Activity.class);
				startActivity(I);
			}
		});
		
		setzen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Spieler darf nun seinen Namen ändern");
				changermap = new HashMap<>();
				changermap.put("Name", edittext_changer.getText().toString());
				Name_changer.push().updateChildren(changermap);
				changermap.clear();
				edittext_changer.setText("");
			}
		});
		
		listview2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				Dialog1.setMessage("Willst du diese Nachricht wieder freigeben?");
				Dialog1.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						Reported.child(str.get((int)(_position))).removeValue();
						SketchwareUtil.showMessage(getApplicationContext(), "Nachricht freigegeben!");
					}
				});
				Dialog1.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				Dialog1.create().show();
				return true;
			}
		});
		
		_Name_changer_child_listener = new ChildEventListener() {
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
		Name_changer.addChildEventListener(_Name_changer_child_listener);
		
		_Deleted_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Deleted.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						deleted_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								deleted_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(deleted_map));
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
				Deleted.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						deleted_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								deleted_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(deleted_map));
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
				Deleted.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						deleted_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								deleted_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview1.setAdapter(new Listview1Adapter(deleted_map));
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
		Deleted.addChildEventListener(_Deleted_child_listener);
		
		_Reported_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Reported.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						reported_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								reported_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview2.setAdapter(new Listview2Adapter(reported_map));
						((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
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
				Reported.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						reported_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								reported_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview2.setAdapter(new Listview2Adapter(reported_map));
						((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
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
				Reported.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						reported_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								reported_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview2.setAdapter(new Listview2Adapter(reported_map));
						((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
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
		Reported.addChildEventListener(_Reported_child_listener);
		
		_Chat_child_listener = new ChildEventListener() {
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
		Chat.addChildEventListener(_Chat_child_listener);
	}
	private void initializeLogic() {
		if (mode.getString("mode", "").equals("0")) {
			linear_oben.setBackgroundColor(0xFF00B0FF);
			ganze_seite.setBackgroundColor(0xFF0277BD);
			linear_name_changer.setBackgroundColor(0xFF00B0FF);
			linear_deleted.setBackgroundColor(0xFF039BE5);
			gemeldet_linear.setBackgroundColor(0xFF00B0FF);
			mehr.setTextColor(0xFF4CAF50);
			zuruek.setTextColor(0xFFE91E63);
		}
		else {
			linear_oben.setBackgroundColor(0xFF37474F);
			ganze_seite.setBackgroundColor(0xFF546E7A);
			linear_name_changer.setBackgroundColor(0xFF37474F);
			linear_deleted.setBackgroundColor(0xFF546E7A);
			gemeldet_linear.setBackgroundColor(0xFF37474F);
			mehr.setTextColor(0xFF673AB7);
			zuruek.setTextColor(0xFF2196F3);
		}
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
				_v = _inflater.inflate(R.layout.reported_custom, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final TextView textview1 = (TextView) _v.findViewById(R.id.textview1);
			
			textview1.setText(deleted_map.get((int)_position).get("Nachricht").toString());
			
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
				_v = _inflater.inflate(R.layout.reported_custom, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final TextView textview1 = (TextView) _v.findViewById(R.id.textview1);
			
			textview1.setText(reported_map.get((int)_position).get("Gemeldet").toString());
			
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
