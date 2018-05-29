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
import android.view.View;
import android.widget.AdapterView;

public class SchreibenActivity extends Activity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> MsgMap = new HashMap<>();
	private boolean connected = false;
	private String command = "";
	
	private ArrayList<HashMap<String, Object>> Map = new ArrayList<>();
	private ArrayList<String> str = new ArrayList<>();
	
	private LinearLayout linear_oben;
	private LinearLayout linear_seite;
	private Button zuruek;
	private TextView textview_name;
	private LinearLayout senden;
	private LinearLayout lesen;
	private EditText edittext_nachricht;
	private Button send;
	private ListView listview_nachrichten;
	
	private SharedPreferences mode;
	private SharedPreferences name;
	private SharedPreferences namedata;
	private SharedPreferences Usernumber;
	private DatabaseReference Nachrichten = _firebase.getReference("Nachrichten");
	private ChildEventListener _Nachrichten_child_listener;
	private AlertDialog.Builder Dialog;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.schreiben);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		linear_oben = (LinearLayout) findViewById(R.id.linear_oben);
		linear_seite = (LinearLayout) findViewById(R.id.linear_seite);
		zuruek = (Button) findViewById(R.id.zuruek);
		textview_name = (TextView) findViewById(R.id.textview_name);
		senden = (LinearLayout) findViewById(R.id.senden);
		lesen = (LinearLayout) findViewById(R.id.lesen);
		edittext_nachricht = (EditText) findViewById(R.id.edittext_nachricht);
		send = (Button) findViewById(R.id.send);
		listview_nachrichten = (ListView) findViewById(R.id.listview_nachrichten);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		name = getSharedPreferences("name", Activity.MODE_PRIVATE);
		namedata = getSharedPreferences("namedata", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		Dialog = new AlertDialog.Builder(this);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				_Check();
				if (connected) {
					if (edittext_nachricht.getText().toString().length() < 3) {
						SketchwareUtil.showMessage(getApplicationContext(), "Der Text ist zu kurz. Min 3");
					}
					else {
						if (250 < edittext_nachricht.getText().toString().length()) {
							SketchwareUtil.showMessage(getApplicationContext(), "Der Text ist zu lang. Max. 250");
						}
						else {
							MsgMap.put("An", textview_name.getText().toString());
							MsgMap.put("Msg", edittext_nachricht.getText().toString());
							MsgMap.put("Von", namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))));
							Nachrichten.push().updateChildren(MsgMap);
							SketchwareUtil.showMessage(getApplicationContext(), "Nachricht gesendet");
							edittext_nachricht.setText("");
						}
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Keine oder zu schlechte Verdindung!");
				}
			}
		});
		
		listview_nachrichten.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				Dialog.setMessage("Willst du diese Nachricht löschen?");
				Dialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						Nachrichten.child(str.get((int)(_position))).removeValue();
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
		
		_Nachrichten_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Nachrichten.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						Map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								Map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview_nachrichten.setAdapter(new Listview_nachrichtenAdapter(Map));
						((BaseAdapter)listview_nachrichten.getAdapter()).notifyDataSetChanged();
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
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Nachrichten.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						Map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								Map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview_nachrichten.setAdapter(new Listview_nachrichtenAdapter(Map));
						((BaseAdapter)listview_nachrichten.getAdapter()).notifyDataSetChanged();
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
		Nachrichten.addChildEventListener(_Nachrichten_child_listener);
	}
	private void initializeLogic() {
		textview_name.setText(name.getString("name", ""));
		if (mode.getString("mode", "").equals("0")) {
			linear_oben.setBackgroundColor(0xFF00B0FF);
			linear_seite.setBackgroundColor(0xFF80D8FF);
			senden.setBackgroundColor(0xFF00B0FF);
			lesen.setBackgroundColor(0xFF00B0FF);
			zuruek.setTextColor(0xFFE91E63);
		}
		else {
			linear_oben.setBackgroundColor(0xFF37474F);
			linear_seite.setBackgroundColor(0xFF455A64);
			senden.setBackgroundColor(0xFF37474F);
			lesen.setBackgroundColor(0xFF37474F);
			zuruek.setTextColor(0xFF2196F3);
		}
		if (name.getString("name", "").equals(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))))) {
			senden.setVisibility(View.GONE);
			lesen.setVisibility(View.VISIBLE);
		}
		else {
			senden.setVisibility(View.VISIBLE);
			lesen.setVisibility(View.GONE);
		}
	}
	
	private void _Check () {
		try{
			command = "ping -c 1 google.com";
			connected = (Runtime.getRuntime().exec (command).waitFor() == 0); } catch (Exception e){ showMessage(e.toString());}
	}
	
	
	public class Listview_nachrichtenAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview_nachrichtenAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
			
			name.setText(Map.get((int)_position).get("Msg").toString());
			if (mode.getString("mode", "").equals("0")) {
				android.graphics.drawable.GradientDrawable linearLy = new android.graphics.drawable.GradientDrawable();  linearLy.setColor(Color.parseColor("#40C4FF"));  linearLy.setCornerRadius(60);  name.setBackground(linearLy);
			}
			else {
				android.graphics.drawable.GradientDrawable linearLy = new android.graphics.drawable.GradientDrawable();  linearLy.setColor(Color.parseColor("#607D8B"));  linearLy.setCornerRadius(60);  name.setBackground(linearLy);
			}
			if (namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))).equals(Map.get((int)_position).get("An").toString())) {
				linear.setVisibility(View.VISIBLE);
			}
			else {
				linear.setVisibility(View.GONE);
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
