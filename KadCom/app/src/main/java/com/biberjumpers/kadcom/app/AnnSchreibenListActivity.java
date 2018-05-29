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
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;
import android.widget.AdapterView;

public class AnnSchreibenListActivity extends Activity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private boolean connected = false;
	private String command = "";
	
	private ArrayList<HashMap<String, Object>> Userlist = new ArrayList<>();
	
	private LinearLayout linear_oben;
	private LinearLayout ganze_seite;
	private Button zuruek;
	private TextView lesen;
	private TextView textview;
	private LinearLayout kein_netz;
	private ListView listview_user;
	private TextView kein_netz_text;
	
	private SharedPreferences mode;
	private SharedPreferences namedata;
	private SharedPreferences Usernumber;
	private SharedPreferences name;
	private Intent I = new Intent();
	private DatabaseReference Chat_user = _firebase.getReference("Chat_user");
	private ChildEventListener _Chat_user_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.ann_schreiben_list);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		linear_oben = (LinearLayout) findViewById(R.id.linear_oben);
		ganze_seite = (LinearLayout) findViewById(R.id.ganze_seite);
		zuruek = (Button) findViewById(R.id.zuruek);
		lesen = (TextView) findViewById(R.id.lesen);
		textview = (TextView) findViewById(R.id.textview);
		kein_netz = (LinearLayout) findViewById(R.id.kein_netz);
		listview_user = (ListView) findViewById(R.id.listview_user);
		kein_netz_text = (TextView) findViewById(R.id.kein_netz_text);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		namedata = getSharedPreferences("namedata", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		name = getSharedPreferences("name", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		lesen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				name.edit().putString("name", namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")")))).commit();
				I.setClass(getApplicationContext(), SchreibenActivity.class);
				startActivity(I);
			}
		});
		
		listview_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				name.edit().putString("name", Userlist.get((int)_position).get("User").toString()).commit();
				I.setClass(getApplicationContext(), SchreibenActivity.class);
				startActivity(I);
			}
		});
		
		_Chat_user_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Chat_user.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						Userlist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								Userlist.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_user.setAdapter(new Listview_userAdapter(Userlist));
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
				Chat_user.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						Userlist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								Userlist.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_user.setAdapter(new Listview_userAdapter(Userlist));
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
				Chat_user.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						Userlist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								Userlist.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_user.setAdapter(new Listview_userAdapter(Userlist));
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
		Chat_user.addChildEventListener(_Chat_user_child_listener);
	}
	private void initializeLogic() {
		kein_netz.setVisibility(View.GONE);
		if (mode.getString("mode", "").equals("0")) {
			linear_oben.setBackgroundColor(0xFF00B0FF);
			ganze_seite.setBackgroundColor(0xFF80D8FF);
			zuruek.setTextColor(0xFFE91E63);
		}
		else {
			linear_oben.setBackgroundColor(0xFF37474F);
			ganze_seite.setBackgroundColor(0xFF455A64);
			zuruek.setTextColor(0xFF2196F3);
		}
		_Check();
		if (connected) {
			kein_netz.setVisibility(View.GONE);
			Chat_user.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
					Userlist = new ArrayList<>();
					try {
						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						for (DataSnapshot _data : _dataSnapshot.getChildren()) {
							HashMap<String, Object> _map = _data.getValue(_ind);
							Userlist.add(_map);
						}
					}
					catch (Exception _e) {
						_e.printStackTrace();
					}
					listview_user.setAdapter(new Listview_userAdapter(Userlist));
					((BaseAdapter)listview_user.getAdapter()).notifyDataSetChanged();
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
			});
		}
		else {
			kein_netz.setVisibility(View.VISIBLE);
			lesen.setVisibility(View.GONE);
			listview_user.setVisibility(View.GONE);
		}
	}
	
	private void _Check () {
		try{
			command = "ping -c 1 google.com";
			connected = (Runtime.getRuntime().exec (command).waitFor() == 0); } catch (Exception e){ showMessage(e.toString());}
	}
	
	
	public class Listview_userAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview_userAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_v = _inflater.inflate(R.layout.user, null);
			}
			
			final LinearLayout linear = (LinearLayout) _v.findViewById(R.id.linear);
			final TextView name = (TextView) _v.findViewById(R.id.name);
			
			name.setText(Userlist.get((int)_position).get("User").toString());
			if (Userlist.get((int)_position).get("User").toString().equals(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))))) {
				linear.setVisibility(View.GONE);
				name.setVisibility(View.GONE);
			}
			else {
				if (mode.getString("mode", "").equals("0")) {
					android.graphics.drawable.GradientDrawable linearLy = new android.graphics.drawable.GradientDrawable();  linearLy.setColor(Color.parseColor("#0091EA"));  linearLy.setCornerRadius(60);  name.setBackground(linearLy);
				}
				else {
					android.graphics.drawable.GradientDrawable linearLy = new android.graphics.drawable.GradientDrawable();  linearLy.setColor(Color.parseColor("#37474F"));  linearLy.setCornerRadius(60);  name.setBackground(linearLy);
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
