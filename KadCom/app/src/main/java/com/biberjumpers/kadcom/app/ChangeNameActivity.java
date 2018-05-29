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
import android.content.SharedPreferences;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;

public class ChangeNameActivity extends Activity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> namemap_new = new HashMap<>();
	private HashMap<String, Object> namemap = new HashMap<>();
	private boolean connected = false;
	private String command = "";
	
	private LinearLayout linear_oben;
	private LinearLayout seite;
	private Button zuruek;
	private EditText name_jetzt;
	private EditText name_neu;
	private Button change;
	
	private SharedPreferences namedata;
	private SharedPreferences Usernumber;
	private Calendar Calender = Calendar.getInstance();
	private DatabaseReference Name_changed = _firebase.getReference("Name_changed");
	private ChildEventListener _Name_changed_child_listener;
	private DatabaseReference Name = _firebase.getReference("Name");
	private ChildEventListener _Name_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.change_name);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		linear_oben = (LinearLayout) findViewById(R.id.linear_oben);
		seite = (LinearLayout) findViewById(R.id.seite);
		zuruek = (Button) findViewById(R.id.zuruek);
		name_jetzt = (EditText) findViewById(R.id.name_jetzt);
		name_neu = (EditText) findViewById(R.id.name_neu);
		change = (Button) findViewById(R.id.change);
		namedata = getSharedPreferences("namedata", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		change.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				_Check();
				if (connected) {
					if (namedata.getString("namedata", "").equals(name_jetzt.getText().toString())) {
						if (name_neu.getText().toString().length() < 40) {
							SketchwareUtil.showMessage(getApplicationContext(), "Name wurde geändert!");
							Calender = Calendar.getInstance();
							namemap_new = new HashMap<>();
							namemap_new.put("Name", namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")".concat(" ist jetzt ".concat(name_neu.getText().toString().concat(". Geändert: ".concat(new SimpleDateFormat("yyyy/MM/dd hh:mm").format(Calender.getTime())))))))));
							Name_changed.push().updateChildren(namemap_new);
							namemap_new.clear();
							namedata.edit().putString("namedata", name_neu.getText().toString()).commit();
							namemap = new HashMap<>();
							namemap.put("Name", namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))));
							Name.push().updateChildren(namemap);
							namemap.clear();
							finish();
						}
						else {
							SketchwareUtil.showMessage(getApplicationContext(), "Neuer Name ist zu lang (maximal 40)");
						}
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "Dein alter Name stimmt nicht überein");
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Du hast keine oder zu schlechte Verbindung!");
				}
			}
		});
		
		_Name_changed_child_listener = new ChildEventListener() {
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
		Name_changed.addChildEventListener(_Name_changed_child_listener);
		
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
	}
	private void initializeLogic() {
	}
	
	private void _Check () {
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
