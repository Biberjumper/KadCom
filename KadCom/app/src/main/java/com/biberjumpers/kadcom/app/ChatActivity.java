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
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
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
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import android.widget.AdapterView;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.ClipData;
import android.content.ClipboardManager;

public class ChatActivity extends Activity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> chatmap = new HashMap<>();
	private HashMap<String, Object> bannmap = new HashMap<>();
	private double bann = 0;
	private double bann_user = 0;
	private HashMap<String, Object> reportmap = new HashMap<>();
	private String command = "";
	private boolean connected = false;
	private String text_edit = "";
	private String position = "";
	private double picture_num = 0;
	private String picture_str = "";
	private HashMap<String, Object> deleted_map = new HashMap<>();
	private HashMap<String, Object> bannmap_team = new HashMap<>();
	private String pkg = "";
	private String p_ver = "";
	private HashMap<String, Object> schreibende_map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> chatmapdata = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> banned_users_map = new ArrayList<>();
	private ArrayList<String> str = new ArrayList<>();
	
	private LinearLayout linear_oben;
	private LinearLayout linear_schreiben;
	private ListView listview_banned;
	private Button zuruek;
	private TextView annonyme_nachrichten;
	private LinearLayout kein_netz;
	private LinearLayout loading_linear;
	private ListView listview_chat;
	private LinearLayout linear_pic;
	private LinearLayout linear_unten;
	private TextView kein_netz_text;
	private ImageView pic_vorschau;
	private Button pic_stopp;
	private Button send;
	private EditText edittext_chat;
	private ImageView senden;
	private ImageView pic;
	
	private SharedPreferences mode;
	private DatabaseReference Chat = _firebase.getReference("Chat");
	private ChildEventListener _Chat_child_listener;
	private SharedPreferences namedata;
	private Calendar Calender = Calendar.getInstance();
	private SharedPreferences Usernumber;
	private DatabaseReference Chat_banned = _firebase.getReference("Chat_banned");
	private ChildEventListener _Chat_banned_child_listener;
	private AlertDialog.Builder Dialog;
	private DatabaseReference Reported = _firebase.getReference("Reported");
	private ChildEventListener _Reported_child_listener;
	private AlertDialog.Builder Dialog2;
	private AlertDialog.Builder Dialog3;
	private AlertDialog.Builder Dialog4;
	private AlertDialog.Builder Dialog5;
	private AlertDialog.Builder Dialog6;
	private DatabaseReference Deleted = _firebase.getReference("Deleted");
	private ChildEventListener _Deleted_child_listener;
	private DatabaseReference Banned_team = _firebase.getReference("Banned_team");
	private ChildEventListener _Banned_team_child_listener;
	private SharedPreferences picture_show;
	private Intent I = new Intent();
	private TimerTask Timer;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.chat);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		linear_oben = (LinearLayout) findViewById(R.id.linear_oben);
		linear_schreiben = (LinearLayout) findViewById(R.id.linear_schreiben);
		listview_banned = (ListView) findViewById(R.id.listview_banned);
		zuruek = (Button) findViewById(R.id.zuruek);
		annonyme_nachrichten = (TextView) findViewById(R.id.annonyme_nachrichten);
		kein_netz = (LinearLayout) findViewById(R.id.kein_netz);
		loading_linear = (LinearLayout) findViewById(R.id.loading_linear);
		listview_chat = (ListView) findViewById(R.id.listview_chat);
		linear_pic = (LinearLayout) findViewById(R.id.linear_pic);
		linear_unten = (LinearLayout) findViewById(R.id.linear_unten);
		kein_netz_text = (TextView) findViewById(R.id.kein_netz_text);
		pic_vorschau = (ImageView) findViewById(R.id.pic_vorschau);
		pic_stopp = (Button) findViewById(R.id.pic_stopp);
		send = (Button) findViewById(R.id.send);
		edittext_chat = (EditText) findViewById(R.id.edittext_chat);
		senden = (ImageView) findViewById(R.id.senden);
		pic = (ImageView) findViewById(R.id.pic);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		namedata = getSharedPreferences("namedata", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		Dialog = new AlertDialog.Builder(this);
		Dialog2 = new AlertDialog.Builder(this);
		Dialog3 = new AlertDialog.Builder(this);
		Dialog4 = new AlertDialog.Builder(this);
		Dialog5 = new AlertDialog.Builder(this);
		Dialog6 = new AlertDialog.Builder(this);
		picture_show = getSharedPreferences("picture_show", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		annonyme_nachrichten.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				I.setClass(getApplicationContext(), AnnSchreibenListActivity.class);
				startActivity(I);
			}
		});
		
		listview_chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				try {
					picture_show.edit().remove("picture_show").commit();
					picture_show.edit().putString("picture_show", chatmapdata.get((int)_position).get("Picture").toString()).commit();
					I.setClass(getApplicationContext(), PicShowActivity.class);
					startActivity(I);
				} catch (Exception e) {
				}
			}
		});
		
		listview_chat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (bann == 1) {
					SketchwareUtil.showMessage(getApplicationContext(), "Du wurdest gebannt...");
				}
				else {
					text_edit = chatmapdata.get((int)_position).get("Text").toString();
					position = str.get((int)(_position));
					if (edittext_chat.getText().toString().equals("//bann user 9178")) {
						Dialog.setMessage("User aus dem Chat verbannen? (Achte darauf, dass er nicht schon gebannt ist!)");
						Dialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								edittext_chat.setText("");
								Calender = Calendar.getInstance();
								bannmap = new HashMap<>();
								bannmap.put("User", chatmapdata.get((int)_position).get("Name").toString());
								Chat_banned.push().updateChildren(bannmap);
								bannmap.clear();
								bannmap_team = new HashMap<>();
								bannmap_team.put("User", chatmapdata.get((int)_position).get("Name").toString().concat(" gebannt von ".concat(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")".concat(" Zeit: ".concat(new SimpleDateFormat("yyyy/MM/dd hh:mm").format(Calender.getTime())))))))));
								Banned_team.push().updateChildren(bannmap_team);
								bannmap_team.clear();
							}
						});
						Dialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						Dialog.create().show();
					}
					if (edittext_chat.getText().toString().equals("/report")) {
						Calender = Calendar.getInstance();
						Dialog2.setMessage("Wegen was möchtest du die Nachricht melden? (falsche Meldungen werden vermerkt [bei Wiederhohlung bestraft]!");
						Dialog2.setPositiveButton("Spam", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								reportmap = new HashMap<>();
								reportmap.put("Gemeldet", "Nachricht ".concat("\"".concat(chatmapdata.get((int)_position).get("Text").toString().concat("\" ".concat("von ".concat(chatmapdata.get((int)_position).get("Name").toString().concat(" vom ".concat(chatmapdata.get((int)_position).get("Zeit").toString().concat(", gemeldet von ".concat(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(") ".concat("am ".concat(new SimpleDateFormat("yyyy/MM/dd hh:mm").format(Calender.getTime()).concat(" wegen Spam"))))))))))))))));
								Reported.push().updateChildren(reportmap);
								reportmap.clear();
								SketchwareUtil.showMessage(getApplicationContext(), "Danke fürs melden!");
							}
						});
						Dialog2.setNegativeButton("Vulgäre Ausdrücke/Beleidigungen", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								reportmap = new HashMap<>();
								reportmap.put("Gemeldet", "Nachricht ".concat("\"".concat(chatmapdata.get((int)_position).get("Text").toString().concat("\" ".concat("von ".concat(chatmapdata.get((int)_position).get("Name").toString().concat(" vom ".concat(chatmapdata.get((int)_position).get("Zeit").toString().concat(", gemeldet von ".concat(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(") ".concat("am ".concat(new SimpleDateFormat("yyyy/MM/dd hh:mm").format(Calender.getTime()).concat(" wegen Vulgären Ausdrücken oder Beleidigung"))))))))))))))));
								Reported.push().updateChildren(reportmap);
								reportmap.clear();
								SketchwareUtil.showMessage(getApplicationContext(), "Danke fürs melden!");
							}
						});
						Dialog2.setNeutralButton("Sonstiges", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								reportmap = new HashMap<>();
								reportmap.put("Gemeldet", "Nachricht ".concat("\"".concat(chatmapdata.get((int)_position).get("Text").toString().concat("\" ".concat("von ".concat(chatmapdata.get((int)_position).get("Name").toString().concat(" vom ".concat(chatmapdata.get((int)_position).get("Zeit").toString().concat(", gemeldet von ".concat(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(") ".concat("am ".concat(new SimpleDateFormat("yyyy/MM/dd hh:mm").format(Calender.getTime()).concat(" wegen Sonstigem"))))))))))))))));
								Reported.push().updateChildren(reportmap);
								reportmap.clear();
								SketchwareUtil.showMessage(getApplicationContext(), "Danke fürs melden!");
							}
						});
						Dialog2.create().show();
						edittext_chat.setText("");
					}
					if (edittext_chat.getText().toString().equals("//delete 9178")) {
						Dialog4.setMessage("Nachricht löschen?");
						Dialog4.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								deleted_map = new HashMap<>();
								Calender = Calendar.getInstance();
								deleted_map.put("Nachricht", "Nachricht: \"".concat(chatmapdata.get((int)_position).get("Text").toString().concat("\" von ".concat(chatmapdata.get((int)_position).get("Name").toString().concat(" gelöscht von ".concat(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat("), ".concat("Zeit: ".concat(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(Calender.getTime()))))))))))));
								Deleted.push().updateChildren(deleted_map);
								deleted_map.clear();
								Chat.child(str.get((int)(_position))).removeValue();
							}
						});
						Dialog4.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						Dialog4.create().show();
					}
					if (edittext_chat.getText().toString().equals("//edit 9178")) {
						Dialog6.setTitle("Nachricht bearbeiten");
						final EditText e_text = new EditText(ChatActivity.this);
						
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
						
						e_text.setLayoutParams(lp);
						e_text.setText(text_edit);
						Dialog6.setView(e_text);
						Dialog6.setPositiveButton("Senden", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								Chat.child(position).child("Text").setValue(e_text.getText().toString());
							}
						});
						Dialog6.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						Dialog6.create().show();
					}
					if (edittext_chat.getText().toString().equals("/copy")) {
						SketchwareUtil.showMessage(getApplicationContext(), "Kopiert");
						((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", "Nachricht: ".concat("\"".concat(chatmapdata.get((int)_position).get("Text").toString().concat("\"".concat(" von ".concat(chatmapdata.get((int)_position).get("Name").toString().concat(", kopiert mit KadCom"))))))));
					}
					if (edittext_chat.getText().toString().equals("//special true 9178")) {
						Chat.child(position).child("Special").setValue(true);
					}
					if (edittext_chat.getText().toString().equals("//special false 9178")) {
						Chat.child(position).child("Special").setValue(false);
					}
				}
				return true;
			}
		});
		
		pic_stopp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				linear_pic.setVisibility(View.GONE);
			}
		});
		
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				pkg = "com.biberjumpers.kadcom.app";
				try { android.content.pm.PackageInfo pinfo = getPackageManager().getPackageInfo(pkg, android.content.pm.PackageManager.GET_ACTIVITIES); p_ver = pinfo.versionName; } catch (Exception e){ showMessage(e.toString()); }
				if (edittext_chat.getText().toString().equals("//bann user 1438") || (edittext_chat.getText().toString().equals("1438") || (edittext_chat.getText().toString().equals("//delete 1438") || (edittext_chat.getText().toString().equals("//edit 1438") || (edittext_chat.getText().toString().equals("//special true 1438") || edittext_chat.getText().toString().equals("//special false 1438")))))) {
					edittext_chat.setText("");
				}
				else {
					if (edittext_chat.getText().toString().length() < 2) {
						SketchwareUtil.showMessage(getApplicationContext(), "Der Text ist zu Kurz");
					}
					else {
						if (edittext_chat.getText().toString().length() > 250) {
							SketchwareUtil.showMessage(getApplicationContext(), "Maximal 250 Zeichen");
						}
						else {
							if (bann == 1) {
								SketchwareUtil.showMessage(getApplicationContext(), "Du wurdest gebannt. Probiere es morgen nochmal");
							}
							else {
								Calender = Calendar.getInstance();
								chatmap = new HashMap<>();
								chatmap.put("Name", namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))));
								chatmap.put("Text", edittext_chat.getText().toString());
								chatmap.put("Zeit", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(Calender.getTime()));
								chatmap.put("Special", "false");
								chatmap.put("Version", p_ver);
								chatmap.put("Likes", "0");
								if (picture_num == 1) {
									java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
									
									Bitmap bm = ((android.graphics.drawable.BitmapDrawable) pic_vorschau.getDrawable()).getBitmap();
									
									bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
									
									byte[] imageBytes = baos.toByteArray();
									
									picture_str = Base64.encodeToString(imageBytes, Base64.DEFAULT);
									chatmap.put("Picture", picture_str);
									picture_num = 0;
									linear_pic.setVisibility(View.GONE);
								}
								Chat.push().updateChildren(chatmap);
								chatmap.clear();
								edittext_chat.setText("");
							}
						}
					}
				}
			}
		});
		
		edittext_chat.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (250 < edittext_chat.getText().toString().length()) {
					SketchwareUtil.showMessage(getApplicationContext(), "Dein Text ist zu lang! < 245 ");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		senden.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				pkg = "com.biberjumpers.kadcom.app";
				try { android.content.pm.PackageInfo pinfo = getPackageManager().getPackageInfo(pkg, android.content.pm.PackageManager.GET_ACTIVITIES); p_ver = pinfo.versionName; } catch (Exception e){ showMessage(e.toString()); }
				if (edittext_chat.getText().toString().equals("/banned")) {
					linear_schreiben.setVisibility(View.GONE);
					Timer = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									linear_schreiben.setVisibility(View.VISIBLE);
								}
							});
						}
					};
					_timer.schedule(Timer, (int)(7000));
				}
				else {
					if (edittext_chat.getText().toString().contains("9178")) {
						edittext_chat.setText("");
					}
					else {
						if (edittext_chat.getText().toString().length() < 2) {
							SketchwareUtil.showMessage(getApplicationContext(), "Der Text ist zu Kurz");
						}
						else {
							if (edittext_chat.getText().toString().length() > 250) {
								SketchwareUtil.showMessage(getApplicationContext(), "Maximal 250 Zeichen");
							}
							else {
								if (bann == 1) {
									SketchwareUtil.showMessage(getApplicationContext(), "Du wurdest gebannt. Probiere es morgen nochmal");
								}
								else {
									Calender = Calendar.getInstance();
									chatmap = new HashMap<>();
									chatmap.put("Name", namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))));
									chatmap.put("Text", edittext_chat.getText().toString());
									chatmap.put("Zeit", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(Calender.getTime()));
									chatmap.put("Special", "false");
									chatmap.put("Version", p_ver);
									chatmap.put("Likes", "0");
									if (picture_num == 1) {
										java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
										
										Bitmap bm = ((android.graphics.drawable.BitmapDrawable) pic_vorschau.getDrawable()).getBitmap();
										
										bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
										
										byte[] imageBytes = baos.toByteArray();
										
										picture_str = Base64.encodeToString(imageBytes, Base64.DEFAULT);
										chatmap.put("Picture", picture_str);
										picture_num = 0;
										linear_pic.setVisibility(View.GONE);
									}
									Chat.push().updateChildren(chatmap);
									chatmap.clear();
									edittext_chat.setText("");
								}
							}
						}
					}
				}
			}
		});
		
		pic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				linear_pic.setVisibility(View.VISIBLE);
				Intent i = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		
		_Chat_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Chat.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						chatmapdata = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								chatmapdata.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview_chat.setAdapter(new Listview_chatAdapter(chatmapdata));
						((BaseAdapter)listview_chat.getAdapter()).notifyDataSetChanged();
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
				Chat.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						chatmapdata = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								chatmapdata.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview_chat.setAdapter(new Listview_chatAdapter(chatmapdata));
						((BaseAdapter)listview_chat.getAdapter()).notifyDataSetChanged();
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
				Chat.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						chatmapdata = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								chatmapdata.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							
							str.add(dshot.getKey());
							
						}
						listview_chat.setAdapter(new Listview_chatAdapter(chatmapdata));
						((BaseAdapter)listview_chat.getAdapter()).notifyDataSetChanged();
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
		Chat.addChildEventListener(_Chat_child_listener);
		
		_Chat_banned_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Chat_banned.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						banned_users_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								banned_users_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_banned.setAdapter(new Listview_bannedAdapter(banned_users_map));
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
						banned_users_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								banned_users_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_banned.setAdapter(new Listview_bannedAdapter(banned_users_map));
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
						banned_users_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								banned_users_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_banned.setAdapter(new Listview_bannedAdapter(banned_users_map));
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
		
		_Reported_child_listener = new ChildEventListener() {
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
		Reported.addChildEventListener(_Reported_child_listener);
		
		_Deleted_child_listener = new ChildEventListener() {
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
		Deleted.addChildEventListener(_Deleted_child_listener);
		
		_Banned_team_child_listener = new ChildEventListener() {
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
		Banned_team.addChildEventListener(_Banned_team_child_listener);
	}
	private void initializeLogic() {
		kein_netz.setVisibility(View.GONE);
		linear_pic.setVisibility(View.GONE);
		picture_num = 0;
		if (namedata.getString("namedata", "").equals("")) {
			SketchwareUtil.showMessage(getApplicationContext(), "Du musst einen Namen haben um schreiben zu können");
			edittext_chat.setVisibility(View.GONE);
			senden.setVisibility(View.GONE);
			pic.setVisibility(View.GONE);
			linear_unten.setVisibility(View.GONE);
		}
		if (mode.getString("mode", "").equals("0")) {
			linear_oben.setBackgroundColor(0xFF00B0FF);
			linear_schreiben.setBackgroundColor(0xFF80D8FF);
			android.graphics.drawable.GradientDrawable linearLy2 = new android.graphics.drawable.GradientDrawable();  linearLy2.setColor(Color.parseColor("#00B0FF"));  linearLy2.setCornerRadius(60); linear_unten.setBackground(linearLy2);
			android.graphics.drawable.GradientDrawable linearLy3 = new android.graphics.drawable.GradientDrawable();  linearLy3.setColor(Color.parseColor("#3F51B5"));  linearLy3.setCornerRadius(60);  linear_pic.setBackground(linearLy3);
			zuruek.setTextColor(0xFFE91E63);
			edittext_chat.setTextColor(0xFF000000);
			senden.setImageResource(R.drawable.ic_send_black);
			pic.setImageResource(R.drawable.ic_collections_black);
		}
		else {
			linear_oben.setBackgroundColor(0xFF37474F);
			linear_schreiben.setBackgroundColor(0xFF455A64);
			android.graphics.drawable.GradientDrawable linearLy2 = new android.graphics.drawable.GradientDrawable();  linearLy2.setColor(Color.parseColor("#37474F"));  linearLy2.setCornerRadius(60);  linear_unten.setBackground(linearLy2);
			android.graphics.drawable.GradientDrawable linearLy3 = new android.graphics.drawable.GradientDrawable();  linearLy3.setColor(Color.parseColor("#263238"));  linearLy3.setCornerRadius(60);  linear_pic.setBackground(linearLy3);
			zuruek.setTextColor(0xFF2196F3);
			edittext_chat.setTextColor(0xFFFFFFFF);
			senden.setImageResource(R.drawable.ic_send_white);
			pic.setImageResource(R.drawable.ic_collections_white);
		}
		bann_user = 0;
		_Check();
		if (connected) {
			loading_linear.setVisibility(View.VISIBLE);
			kein_netz.setVisibility(View.GONE);
			ProgressBar cpb = new ProgressBar(ChatActivity.this);  loading_linear.addView(cpb);
			Chat.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
					chatmapdata = new ArrayList<>();
					try {
						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						for (DataSnapshot _data : _dataSnapshot.getChildren()) {
							HashMap<String, Object> _map = _data.getValue(_ind);
							chatmapdata.add(_map);
						}
					}
					catch (Exception _e) {
						_e.printStackTrace();
					}
					str.clear();
					for (DataSnapshot dshot: _dataSnapshot.getChildren()){
						
						str.add(dshot.getKey());
						
					}
					listview_chat.setAdapter(new Listview_chatAdapter(chatmapdata));
					((BaseAdapter)listview_chat.getAdapter()).notifyDataSetChanged();
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
			});
		}
		else {
			kein_netz.setVisibility(View.VISIBLE);
			listview_chat.setVisibility(View.GONE);
			listview_banned.setVisibility(View.GONE);
		}
		listview_chat.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL); listview_chat.setStackFromBottom(true);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	} private static int RESULT_LOAD_IMAGE = 1;
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) { super.onActivityResult(requestCode, resultCode, data); if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) { Uri selectedImage = data.getData(); 
			pic_vorschau.setImageURI(selectedImage);
			picture_num = 1;
		}
	}
	
	private void _Check () {
		try{
			command = "ping -c 1 google.com";
			connected = (Runtime.getRuntime().exec (command).waitFor() == 0); } catch (Exception e){ showMessage(e.toString());}
	}
	
	
	public class Listview_bannedAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview_bannedAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
			
			banned_user.setText(banned_users_map.get((int)_position).get("User").toString());
			if (namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))).equals(banned_users_map.get((int)_position).get("User").toString())) {
				bann = 1;
			}
			
			return _v;
		}
	}
	
	public class Listview_chatAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview_chatAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_v = _inflater.inflate(R.layout.chat_custom, null);
			}
			
			final LinearLayout sender_linear = (LinearLayout) _v.findViewById(R.id.sender_linear);
			final LinearLayout sender_box = (LinearLayout) _v.findViewById(R.id.sender_box);
			final TextView sender_name = (TextView) _v.findViewById(R.id.sender_name);
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final TextView sender_text = (TextView) _v.findViewById(R.id.sender_text);
			final TextView sender_zeit = (TextView) _v.findViewById(R.id.sender_zeit);
			final TextView sender_version = (TextView) _v.findViewById(R.id.sender_version);
			final TextView special = (TextView) _v.findViewById(R.id.special);
			final ImageView imageview1 = (ImageView) _v.findViewById(R.id.imageview1);
			
			sender_name.setText(chatmapdata.get((int)_position).get("Name").toString());
			sender_text.setText(chatmapdata.get((int)_position).get("Text").toString());
			sender_zeit.setText(chatmapdata.get((int)_position).get("Zeit").toString());
			special.setText(chatmapdata.get((int)_position).get("Special").toString());
			sender_version.setText("Version: ".concat(chatmapdata.get((int)_position).get("Version").toString()));
			try {
				linear1.setVisibility(View.VISIBLE);
				imageview1.setVisibility(View.VISIBLE);
				picture_str = chatmapdata.get((int)_position).get("Picture").toString();
				byte[] imageBytes = Base64.decode(picture_str, Base64.DEFAULT);
				
				Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
				
				imageview1.setImageBitmap(decodedImage);
			} catch (Exception e) {
				linear1.setVisibility(View.GONE);
				imageview1.setVisibility(View.GONE);
			}
			special.setVisibility(View.GONE);
			if (namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")"))).equals(sender_name.getText().toString())) {
				if (mode.getString("mode", "").equals("0")) {
					android.graphics.drawable.GradientDrawable linearLy = new android.graphics.drawable.GradientDrawable();  linearLy.setColor(Color.parseColor("#40C4FF"));  linearLy.setCornerRadius(60);  sender_linear.setBackground(linearLy);
				}
				else {
					android.graphics.drawable.GradientDrawable linearLy = new android.graphics.drawable.GradientDrawable();  linearLy.setColor(Color.parseColor("#607D8B"));  linearLy.setCornerRadius(60);  sender_linear.setBackground(linearLy);
				}
			}
			else {
				if (mode.getString("mode", "").equals("0")) {
					android.graphics.drawable.GradientDrawable linearLy = new android.graphics.drawable.GradientDrawable();  linearLy.setColor(Color.parseColor("#0091EA"));  linearLy.setCornerRadius(60);  sender_linear.setBackground(linearLy);
				}
				else {
					android.graphics.drawable.GradientDrawable linearLy = new android.graphics.drawable.GradientDrawable();  linearLy.setColor(Color.parseColor("#37474F"));  linearLy.setCornerRadius(60);  sender_linear.setBackground(linearLy);
				}
			}
			if (chatmapdata.get((int)_position).get("Special").toString().equals("true")) {
				android.graphics.drawable.GradientDrawable linearLy = new android.graphics.drawable.GradientDrawable();  linearLy.setColor(Color.parseColor("#EC407A"));  linearLy.setCornerRadius(60);  sender_linear.setBackground(linearLy);
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
