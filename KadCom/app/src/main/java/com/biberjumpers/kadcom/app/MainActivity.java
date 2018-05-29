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
import android.widget.SeekBar;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import java.util.Timer;
import java.util.TimerTask;
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
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private double laden = 0;
	private double bar = 0;
	private String a = "";
	private String command = "";
	private boolean connected = false;
	private double Image = 0;
	private HashMap<String, Object> AppStartMap = new HashMap<>();
	private double Starts = 0;
	private double UserNumber = 0;
	private double MenuCountNum = 0;
	private double reload = 0;
	private double votecounter = 0;
	private double nonetcount = 0;
	private double progressbar = 0;
	private double reloading = 0;
	private double name_change_number = 0;
	private String pkg = "";
	private String new_ver = "";
	private String p_ver = "";
	private double loading_hpb = 0;
	private double banned_nummer = 0;
	private double clicks = 0;
	
	private ArrayList<HashMap<String, Object>> name_changer_map = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> version_map = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> gebannte_map = new ArrayList<>();
	
	private LinearLayout komplette_seite;
	private ListView list_name_changer;
	private ListView listview_update;
	private ListView listview_gebannte;
	private LinearLayout platzhalter_oben;
	private LinearLayout menue;
	private LinearLayout menue2;
	private LinearLayout kein_netz;
	private Button name;
	private Button name_change;
	private SeekBar loading;
	private WebView kadcon_website;
	private Button exit;
	private ImageView seite_zuruek;
	private ImageView teilen;
	private ImageView aktualisieren;
	private ImageView seite_vor;
	private Button menue_button;
	private Button einstellungen;
	private Button voten;
	private Button kontakt;
	private Button mehr;
	private Button info;
	private Button sprache_in_text;
	private Button chat;
	private Button rate;
	private TextView kein_netz_text;
	
	private AlertDialog.Builder Exit_Dialog;
	private Intent Einstellungen = new Intent();
	private Intent Infos = new Intent();
	private Intent Kontakt = new Intent();
	private ObjectAnimator Menu_animation = new ObjectAnimator();
	private TimerTask Timer;
	private Intent Voten = new Intent();
	private SharedPreferences mode;
	private Intent NameEingeben = new Intent();
	private ObjectAnimator Back_animation = new ObjectAnimator();
	private Intent Sprache_in_text = new Intent();
	private Intent Rate = new Intent();
	private SharedPreferences namedata;
	private SharedPreferences rated;
	private Intent Team = new Intent();
	private SharedPreferences starts;
	private Intent Start = new Intent();
	private Calendar Zeit = Calendar.getInstance();
	private SharedPreferences startcount;
	private SharedPreferences Usernumber;
	private SharedPreferences first;
	private SharedPreferences menucount;
	private SharedPreferences reloadcounter;
	private SharedPreferences votecount;
	private SharedPreferences nonetcounter;
	private DatabaseReference AppStats = _firebase.getReference("AppStats");
	private ChildEventListener _AppStats_child_listener;
	private TimerTask Loading;
	private TimerTask loading2;
	private DatabaseReference Update = _firebase.getReference("Update");
	private ChildEventListener _Update_child_listener;
	private AlertDialog.Builder Update_Dialog;
	private Intent Update_Intent = new Intent();
	private Intent Chat = new Intent();
	private DatabaseReference Name_changer = _firebase.getReference("Name_changer");
	private ChildEventListener _Name_changer_child_listener;
	private Intent name_change_I = new Intent();
	private DatabaseReference App_gebannte = _firebase.getReference("App_gebannte");
	private ChildEventListener _App_gebannte_child_listener;
	private AlertDialog.Builder Dialog_banned;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		komplette_seite = (LinearLayout) findViewById(R.id.komplette_seite);
		list_name_changer = (ListView) findViewById(R.id.list_name_changer);
		listview_update = (ListView) findViewById(R.id.listview_update);
		listview_gebannte = (ListView) findViewById(R.id.listview_gebannte);
		platzhalter_oben = (LinearLayout) findViewById(R.id.platzhalter_oben);
		menue = (LinearLayout) findViewById(R.id.menue);
		menue2 = (LinearLayout) findViewById(R.id.menue2);
		kein_netz = (LinearLayout) findViewById(R.id.kein_netz);
		name = (Button) findViewById(R.id.name);
		name_change = (Button) findViewById(R.id.name_change);
		loading = (SeekBar) findViewById(R.id.loading);
		kadcon_website = (WebView) findViewById(R.id.kadcon_website);
		kadcon_website.getSettings().setJavaScriptEnabled(true);
		kadcon_website.getSettings().setSupportZoom(true);
		exit = (Button) findViewById(R.id.exit);
		seite_zuruek = (ImageView) findViewById(R.id.seite_zuruek);
		teilen = (ImageView) findViewById(R.id.teilen);
		aktualisieren = (ImageView) findViewById(R.id.aktualisieren);
		seite_vor = (ImageView) findViewById(R.id.seite_vor);
		menue_button = (Button) findViewById(R.id.menue_button);
		einstellungen = (Button) findViewById(R.id.einstellungen);
		voten = (Button) findViewById(R.id.voten);
		kontakt = (Button) findViewById(R.id.kontakt);
		mehr = (Button) findViewById(R.id.mehr);
		info = (Button) findViewById(R.id.info);
		sprache_in_text = (Button) findViewById(R.id.sprache_in_text);
		chat = (Button) findViewById(R.id.chat);
		rate = (Button) findViewById(R.id.rate);
		kein_netz_text = (TextView) findViewById(R.id.kein_netz_text);
		Exit_Dialog = new AlertDialog.Builder(this);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		namedata = getSharedPreferences("namedata", Activity.MODE_PRIVATE);
		rated = getSharedPreferences("rated", Activity.MODE_PRIVATE);
		starts = getSharedPreferences("starts", Activity.MODE_PRIVATE);
		startcount = getSharedPreferences("startcount", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		first = getSharedPreferences("first", Activity.MODE_PRIVATE);
		menucount = getSharedPreferences("menucount", Activity.MODE_PRIVATE);
		reloadcounter = getSharedPreferences("reloadcounter", Activity.MODE_PRIVATE);
		votecount = getSharedPreferences("votecount", Activity.MODE_PRIVATE);
		nonetcounter = getSharedPreferences("nonetcounter", Activity.MODE_PRIVATE);
		Update_Dialog = new AlertDialog.Builder(this);
		Dialog_banned = new AlertDialog.Builder(this);
		
		name.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				name.setVisibility(View.GONE);
				if (namedata.getString("namedata", "").equals("")) {
					name.setVisibility(View.VISIBLE);
					NameEingeben.setClass(getApplicationContext(), NameActivity.class);
					startActivity(NameEingeben);
				}
				else {
					name.setVisibility(View.GONE);
				}
			}
		});
		
		name_change.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Name ändern");
				name_change_I.setClass(getApplicationContext(), ChangeNameActivity.class);
				startActivity(name_change_I);
			}
		});
		
		kadcon_website.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				loading.setVisibility(View.VISIBLE);
				progressbar = 0;
				reloading = 0;
				Loading = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loading.setProgress((int)progressbar);
								if (progressbar == 101) {
									progressbar = 1;
								}
								else {
									progressbar++;
								}
							}
						});
					}
				};
				_timer.scheduleAtFixedRate(Loading, (int)(200), (int)(60));
				loading2 = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								reloading--;
								aktualisieren.setRotation((float)(reloading));
							}
						});
					}
				};
				_timer.scheduleAtFixedRate(loading2, (int)(0), (int)(5));
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Loading.cancel();
								loading2.cancel();
								aktualisieren.setRotation((float)(0));
								loading.setVisibility(View.GONE);
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(100));
				super.onPageFinished(_param1, _param2);
			}
		});
		
		exit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Exit_Dialog.setMessage("Willst du KadCom beenden?");
				Exit_Dialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						Back_animation.setTarget(komplette_seite);
						Back_animation.setPropertyName("translationX");
						Back_animation.setFloatValues((float)(0), (float)(1000));
						Back_animation.setDuration((int)(300));
						Back_animation.setInterpolator(new AccelerateInterpolator());
						Back_animation.start();
						Timer = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										finish();
									}
								});
							}
						};
						_timer.schedule(Timer, (int)(300));
					}
				});
				Exit_Dialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				Exit_Dialog.create().show();
			}
		});
		
		seite_zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				kadcon_website.goBack();
				SketchwareUtil.showMessage(getApplicationContext(), "⬅️");
			}
		});
		
		teilen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				a = kadcon_website.getUrl().concat("\n\nÜber KadCom gesendet");
				Intent i = new Intent(android.content.Intent.ACTION_SEND); i.setType("text/plain"); i.putExtra(android.content.Intent.EXTRA_TEXT,a); startActivity(Intent.createChooser(i,"Link versenden"));
				SketchwareUtil.showMessage(getApplicationContext(), "📤");
			}
		});
		
		aktualisieren.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				reload++;
				reloadcounter.edit().putString("reloadcounter", String.valueOf((long)(reload))).commit();
				kadcon_website.stopLoading();
				if (namedata.getString("namedata", "").equals("")) {
					rate.setVisibility(View.GONE);
				}
				else {
					rate.setVisibility(View.VISIBLE);
				}
				if (rated.getString("rated", "").equals("-")) {
					rate.setVisibility(View.GONE);
				}
				if (mode.getString("mode", "").equals("0")) {
					seite_zuruek.setImageResource(R.drawable.ic_chevron_left_black);
					teilen.setImageResource(R.drawable.ic_email_black);
					aktualisieren.setImageResource(R.drawable.ic_sync_black);
					seite_vor.setImageResource(R.drawable.ic_chevron_right_black);
					platzhalter_oben.setBackgroundColor(0xFF00B0FF);
					menue.setBackgroundColor(0xFF2196F3);
					menue2.setBackgroundColor(0xFF1976D2);
					exit.setTextColor(0xFFE91E63);
					menue_button.setTextColor(0xFF4CAF50);
					einstellungen.setTextColor(0xFFE91E63);
					info.setTextColor(0xFFE91E63);
					kontakt.setTextColor(0xFF4CAF50);
					mehr.setTextColor(0xFF4CAF50);
					voten.setTextColor(0xFFE91E63);
					komplette_seite.setBackgroundColor(0xFFFFFFFF);
					sprache_in_text.setTextColor(0xFFE91E63);
					rate.setTextColor(0xFF4CAF50);
					chat.setTextColor(0xFF4CAF50);
				}
				else {
					seite_zuruek.setImageResource(R.drawable.ic_chevron_left_white);
					teilen.setImageResource(R.drawable.ic_mail_white);
					aktualisieren.setImageResource(R.drawable.ic_loop_white);
					seite_vor.setImageResource(R.drawable.ic_chevron_right_white);
					platzhalter_oben.setBackgroundColor(0xFF37474F);
					menue.setBackgroundColor(0xFF455A64);
					menue2.setBackgroundColor(0xFF546E7A);
					exit.setTextColor(0xFF2196F3);
					menue_button.setTextColor(0xFF673AB7);
					einstellungen.setTextColor(0xFF2196F3);
					info.setTextColor(0xFF2196F3);
					kontakt.setTextColor(0xFF673AB7);
					mehr.setTextColor(0xFF673AB7);
					voten.setTextColor(0xFF2196F3);
					komplette_seite.setBackgroundColor(0xFF455A64);
					sprache_in_text.setTextColor(0xFF2196F3);
					rate.setTextColor(0xFF673AB7);
					chat.setTextColor(0xFF673AB7);
				}
				_Check();
				final ProgressDialog prog = new ProgressDialog(MainActivity.this);
				prog.setMax(100);prog.setTitle("Aktualisiere...");
				prog.setMessage("Website wird neu geladen.");
				prog.setIndeterminate(true);
				prog.show();
				if (connected) {
					if (namedata.getString("namedata", "").equals("")) {
						name.setVisibility(View.VISIBLE);
					}
					else {
						name.setVisibility(View.GONE);
					}
					rate.setVisibility(View.VISIBLE);
					kein_netz.setVisibility(View.GONE);
					kadcon_website.setVisibility(View.VISIBLE);
					Timer = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									kadcon_website.loadUrl(kadcon_website.getUrl());
								}
							});
						}
					};
					_timer.schedule(Timer, (int)(1));
					SketchwareUtil.showMessage(getApplicationContext(), "Aktualisiert");
				}
				else {
					kadcon_website.setVisibility(View.GONE);
					kein_netz.setVisibility(View.VISIBLE);
					SketchwareUtil.showMessage(getApplicationContext(), "Fehler beim laden der Seite");
					name.setVisibility(View.GONE);
					rate.setVisibility(View.GONE);
					loading.setVisibility(View.GONE);
					prog.hide();
				}
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								prog.hide();
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(2000));
			}
		});
		
		seite_vor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				kadcon_website.goForward();
				SketchwareUtil.showMessage(getApplicationContext(), "➡️");
			}
		});
		
		menue_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (bar == 0) {
					MenuCountNum++;
					menue.setVisibility(View.VISIBLE);
					Menu_animation.setTarget(menue);
					Menu_animation.setPropertyName("translationX");
					Menu_animation.setFloatValues((float)(1000), (float)(0));
					Menu_animation.setDuration((int)(400));
					Menu_animation.setInterpolator(new LinearInterpolator());
					Menu_animation.start();
					bar++;
					menucount.edit().putString("menucount", String.valueOf((long)(MenuCountNum))).commit();
				}
				else {
					if (bar == 1) {
						bar--;
						Menu_animation.setTarget(menue);
						Menu_animation.setPropertyName("translationX");
						Menu_animation.setFloatValues((float)(0), (float)(3000));
						Menu_animation.setDuration((int)(400));
						Menu_animation.setInterpolator(new LinearInterpolator());
						Menu_animation.start();
						Timer = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										menue.setVisibility(View.GONE);
									}
								});
							}
						};
						_timer.schedule(Timer, (int)(400));
					}
					if (bar == 2) {
						bar--;
						bar--;
						Menu_animation.setTarget(menue2);
						Menu_animation.setPropertyName("translationX");
						Menu_animation.setFloatValues((float)(0), (float)(3000));
						Menu_animation.setDuration((int)(400));
						Menu_animation.setInterpolator(new LinearInterpolator());
						Menu_animation.start();
						Timer = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										Menu_animation.setTarget(menue);
										Menu_animation.setPropertyName("translationX");
										Menu_animation.setFloatValues((float)(0), (float)(3000));
										Menu_animation.setDuration((int)(400));
										Menu_animation.setInterpolator(new LinearInterpolator());
										Menu_animation.start();
										Timer = new TimerTask() {
											@Override
											public void run() {
												runOnUiThread(new Runnable() {
													@Override
													public void run() {
														menue2.setVisibility(View.GONE);
														Timer = new TimerTask() {
															@Override
															public void run() {
																runOnUiThread(new Runnable() {
																	@Override
																	public void run() {
																		menue.setVisibility(View.GONE);
																	}
																});
															}
														};
														_timer.schedule(Timer, (int)(200));
													}
												});
											}
										};
										_timer.schedule(Timer, (int)(200));
									}
								});
							}
						};
						_timer.schedule(Timer, (int)(200));
					}
				}
			}
		});
		
		einstellungen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Einstellungen");
				Einstellungen.setClass(getApplicationContext(), EinstellungenActivity.class);
				startActivity(Einstellungen);
			}
		});
		
		voten.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				votecounter++;
				votecount.edit().putString("votecount", String.valueOf((long)(votecounter))).commit();
				SketchwareUtil.showMessage(getApplicationContext(), "Voten");
				Voten.setClass(getApplicationContext(), VotenActivity.class);
				startActivity(Voten);
			}
		});
		
		kontakt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Kontakt");
				Kontakt.setClass(getApplicationContext(), KontaktActivity.class);
				startActivity(Kontakt);
			}
		});
		
		mehr.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (bar == 1) {
					menue2.setVisibility(View.VISIBLE);
					Menu_animation.setTarget(menue2);
					Menu_animation.setPropertyName("translationX");
					Menu_animation.setFloatValues((float)(1000), (float)(0));
					Menu_animation.setDuration((int)(400));
					Menu_animation.setInterpolator(new LinearInterpolator());
					Menu_animation.start();
					bar++;
				}
				else {
					if (bar == 2) {
						bar--;
						Menu_animation.setTarget(menue2);
						Menu_animation.setPropertyName("translationX");
						Menu_animation.setFloatValues((float)(0), (float)(3000));
						Menu_animation.setDuration((int)(400));
						Menu_animation.setInterpolator(new LinearInterpolator());
						Menu_animation.start();
						Timer = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										menue2.setVisibility(View.GONE);
									}
								});
							}
						};
						_timer.schedule(Timer, (int)(400));
					}
				}
			}
		});
		
		info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Infos");
				Infos.setClass(getApplicationContext(), InfoseiteActivity.class);
				startActivity(Infos);
			}
		});
		
		sprache_in_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Sprache_in_text.setClass(getApplicationContext(), SpracheInTextActivity.class);
				startActivity(Sprache_in_text);
				SketchwareUtil.showMessage(getApplicationContext(), "Sprachumwandler");
			}
		});
		
		chat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Chat.setClass(getApplicationContext(), ChatActivity.class);
				startActivity(Chat);
				SketchwareUtil.showMessage(getApplicationContext(), "Chat");
			}
		});
		
		rate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (rated.getString("rated", "").equals("-")) {
					rate.setVisibility(View.GONE);
				}
				else {
					Rate.setClass(getApplicationContext(), BewertenActivity.class);
					startActivity(Rate);
					SketchwareUtil.showMessage(getApplicationContext(), "App bewerten");
				}
			}
		});
		
		kein_netz_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (namedata.getString("namedata", "").equals("")) {
					name.setVisibility(View.VISIBLE);
				}
				else {
					name.setVisibility(View.GONE);
				}
				if (namedata.getString("namedata", "").equals("")) {
					rate.setVisibility(View.GONE);
				}
				else {
					rate.setVisibility(View.VISIBLE);
				}
				if (rated.getString("rated", "").equals("-")) {
					rate.setVisibility(View.GONE);
				}
				if (mode.getString("mode", "").equals("0")) {
					seite_zuruek.setImageResource(R.drawable.ic_chevron_left_black);
					teilen.setImageResource(R.drawable.ic_email_black);
					aktualisieren.setImageResource(R.drawable.ic_sync_black);
					seite_vor.setImageResource(R.drawable.ic_chevron_right_black);
					platzhalter_oben.setBackgroundColor(0xFF00B0FF);
					menue.setBackgroundColor(0xFF2196F3);
					menue2.setBackgroundColor(0xFF1976D2);
					exit.setTextColor(0xFFE91E63);
					menue_button.setTextColor(0xFF4CAF50);
					einstellungen.setTextColor(0xFFE91E63);
					info.setTextColor(0xFFE91E63);
					kontakt.setTextColor(0xFF4CAF50);
					mehr.setTextColor(0xFF4CAF50);
					voten.setTextColor(0xFFE91E63);
					komplette_seite.setBackgroundColor(0xFFFFFFFF);
					sprache_in_text.setTextColor(0xFFE91E63);
					rate.setTextColor(0xFF4CAF50);
					chat.setTextColor(0xFF4CAF50);
				}
				else {
					seite_zuruek.setImageResource(R.drawable.ic_chevron_left_white);
					teilen.setImageResource(R.drawable.ic_mail_white);
					aktualisieren.setImageResource(R.drawable.ic_loop_white);
					seite_vor.setImageResource(R.drawable.ic_chevron_right_white);
					platzhalter_oben.setBackgroundColor(0xFF37474F);
					menue.setBackgroundColor(0xFF455A64);
					menue2.setBackgroundColor(0xFF546E7A);
					exit.setTextColor(0xFF2196F3);
					menue_button.setTextColor(0xFF673AB7);
					einstellungen.setTextColor(0xFF2196F3);
					info.setTextColor(0xFF2196F3);
					kontakt.setTextColor(0xFF673AB7);
					mehr.setTextColor(0xFF673AB7);
					voten.setTextColor(0xFF2196F3);
					komplette_seite.setBackgroundColor(0xFF455A64);
					sprache_in_text.setTextColor(0xFF2196F3);
					rate.setTextColor(0xFF673AB7);
					chat.setTextColor(0xFF673AB7);
				}
				_Check();
				final ProgressDialog prog = new ProgressDialog(MainActivity.this);
				prog.setMax(100);prog.setTitle("Aktualisiere...");
				prog.setMessage("Website wird neu geladen.");
				prog.setIndeterminate(true);
				prog.show();
				if (connected) {
					kein_netz.setVisibility(View.GONE);
					kadcon_website.setVisibility(View.VISIBLE);
					SketchwareUtil.showMessage(getApplicationContext(), "Verbunden");
					kadcon_website.loadUrl(kadcon_website.getUrl());
				}
				else {
					kadcon_website.setVisibility(View.GONE);
					kein_netz.setVisibility(View.VISIBLE);
					SketchwareUtil.showMessage(getApplicationContext(), "Fehler beim laden der Seite");
					name.setVisibility(View.GONE);
					rate.setVisibility(View.GONE);
					loading.setVisibility(View.GONE);
					prog.hide();
				}
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								prog.hide();
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(2000));
			}
		});
		
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
		
		_Update_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Update.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						version_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								version_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_update.setAdapter(new Listview_updateAdapter(version_map));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				pkg = "com.biberjumpers.kadcom.app";
				try { android.content.pm.PackageInfo pinfo = getPackageManager().getPackageInfo(pkg, android.content.pm.PackageManager.GET_ACTIVITIES); p_ver = pinfo.versionName; } catch (Exception e){ showMessage(e.toString()); }
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								new_ver = version_map.get((int)0).get("Update").toString();
								if (p_ver.equals(new_ver)) {
									
								}
								else {
									SketchwareUtil.showMessage(getApplicationContext(), "Version ".concat(new_ver.concat(" ist jetzt verfügbar!")));
									Update_Dialog.setTitle("Neues Update ".concat(new_ver.concat(" verfügbar!")));
									Update_Dialog.setMessage("Hallo. Das KadCom Team hat eine neue Version veröffentlicht! Willst du sie jetzt herunterladen?");
									Update_Dialog.setPositiveButton("Herunterladen", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											Update_Intent.setAction(Intent.ACTION_VIEW);
											Update_Intent.setData(Uri.parse("http://biberjumpers-kadcon-app.jimdo.com/update"));
											startActivity(Update_Intent);
										}
									});
									Update_Dialog.setNegativeButton("Noch nicht", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											
										}
									});
									Update_Dialog.create().show();
								}
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(3000));
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Update.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						version_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								version_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_update.setAdapter(new Listview_updateAdapter(version_map));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				pkg = "com.biberjumpers.kadcom.app";
				try { android.content.pm.PackageInfo pinfo = getPackageManager().getPackageInfo(pkg, android.content.pm.PackageManager.GET_ACTIVITIES); p_ver = pinfo.versionName; } catch (Exception e){ showMessage(e.toString()); }
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								new_ver = version_map.get((int)0).get("Update").toString();
								if (p_ver.equals(new_ver)) {
									
								}
								else {
									SketchwareUtil.showMessage(getApplicationContext(), "Version ".concat(new_ver.concat(" ist jetzt verfügbar!")));
									Update_Dialog.setTitle("Neues Update ".concat(new_ver.concat(" verfügbar!")));
									Update_Dialog.setMessage("Hallo. Das KadCom Team hat eine neue Version veröffentlicht! Willst du sie jetzt herunterladen?");
									Update_Dialog.setPositiveButton("Herunterladen", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											Update_Intent.setAction(Intent.ACTION_VIEW);
											Update_Intent.setData(Uri.parse("http://biberjumpers-kadcon-app.jimdo.com/update"));
											startActivity(Update_Intent);
										}
									});
									Update_Dialog.setNegativeButton("Noch nicht", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											
										}
									});
									Update_Dialog.create().show();
								}
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(3000));
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Update.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						version_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								version_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_update.setAdapter(new Listview_updateAdapter(version_map));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				pkg = "com.biberjumpers.kadcom.app";
				try { android.content.pm.PackageInfo pinfo = getPackageManager().getPackageInfo(pkg, android.content.pm.PackageManager.GET_ACTIVITIES); p_ver = pinfo.versionName; } catch (Exception e){ showMessage(e.toString()); }
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								new_ver = version_map.get((int)0).get("Update").toString();
								if (p_ver.equals(new_ver)) {
									
								}
								else {
									SketchwareUtil.showMessage(getApplicationContext(), "Version ".concat(new_ver.concat(" ist jetzt verfügbar!")));
									Update_Dialog.setTitle("Neues Update ".concat(new_ver.concat(" verfügbar!")));
									Update_Dialog.setMessage("Hallo. Das KadCom Team hat eine neue Version veröffentlicht! Willst du sie jetzt herunterladen?");
									Update_Dialog.setPositiveButton("Herunterladen", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											Update_Intent.setAction(Intent.ACTION_VIEW);
											Update_Intent.setData(Uri.parse("http://biberjumpers-kadcon-app.jimdo.com/update"));
											startActivity(Update_Intent);
										}
									});
									Update_Dialog.setNegativeButton("Noch nicht", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											
										}
									});
									Update_Dialog.create().show();
								}
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(3000));
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final String _errorCode = String.valueOf(_param1.getCode());
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		Update.addChildEventListener(_Update_child_listener);
		
		_Name_changer_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Name_changer.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						name_changer_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								name_changer_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						list_name_changer.setAdapter(new List_name_changerAdapter(name_changer_map));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (name_change_number == 1) {
									name_change.setVisibility(View.VISIBLE);
								}
								else {
									name_change.setVisibility(View.GONE);
								}
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(200));
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Name_changer.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						name_changer_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								name_changer_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						list_name_changer.setAdapter(new List_name_changerAdapter(name_changer_map));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (name_change_number == 1) {
									name_change.setVisibility(View.VISIBLE);
								}
								else {
									name_change.setVisibility(View.GONE);
								}
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(200));
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				Name_changer.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						name_changer_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								name_changer_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						list_name_changer.setAdapter(new List_name_changerAdapter(name_changer_map));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (name_change_number == 1) {
									name_change.setVisibility(View.VISIBLE);
								}
								else {
									name_change.setVisibility(View.GONE);
								}
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(200));
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final String _errorCode = String.valueOf(_param1.getCode());
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		Name_changer.addChildEventListener(_Name_changer_child_listener);
		
		_App_gebannte_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				App_gebannte.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						gebannte_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								gebannte_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_gebannte.setAdapter(new Listview_gebannteAdapter(gebannte_map));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (banned_nummer == 1) {
									Dialog_banned.setTitle("Du wurdest gebannt!");
									Dialog_banned.setMessage("Um entbannt zu werden, kontaktiere bitte Biberjumper Ingame!");
									Dialog_banned.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											finish();
										}
									});
								}
								else {
									
								}
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(2000));
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				App_gebannte.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						gebannte_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								gebannte_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_gebannte.setAdapter(new Listview_gebannteAdapter(gebannte_map));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (banned_nummer == 1) {
									Dialog_banned.setTitle("Du wurdest gebannt!");
									Dialog_banned.setMessage("Um entbannt zu werden, kontaktiere bitte Biberjumper Ingame!");
									Dialog_banned.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											finish();
										}
									});
								}
								else {
									
								}
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(2000));
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
						gebannte_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								gebannte_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						listview_gebannte.setAdapter(new Listview_gebannteAdapter(gebannte_map));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (banned_nummer == 1) {
									Dialog_banned.setTitle("Du wurdest gebannt!");
									Dialog_banned.setMessage("Um entbannt zu werden, kontaktiere bitte Biberjumper Ingame!");
									Dialog_banned.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface _dialog, int _which) {
											finish();
										}
									});
								}
								else {
									
								}
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(2000));
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
		pkg = "com.biberjumpers.kadcom.app";
		try { android.content.pm.PackageInfo pinfo = getPackageManager().getPackageInfo(pkg, android.content.pm.PackageManager.GET_ACTIVITIES); p_ver = pinfo.versionName; } catch (Exception e){ showMessage(e.toString()); }
		if (mode.getString("mode", "").equals("0")) {
			seite_zuruek.setImageResource(R.drawable.ic_chevron_left_black);
			teilen.setImageResource(R.drawable.ic_email_black);
			aktualisieren.setImageResource(R.drawable.ic_sync_black);
			seite_vor.setImageResource(R.drawable.ic_chevron_right_black);
			platzhalter_oben.setBackgroundColor(0xFF00B0FF);
			menue.setBackgroundColor(0xFF2196F3);
			menue2.setBackgroundColor(0xFF1976D2);
			exit.setTextColor(0xFFE91E63);
			menue_button.setTextColor(0xFF4CAF50);
			einstellungen.setTextColor(0xFFE91E63);
			info.setTextColor(0xFFE91E63);
			kontakt.setTextColor(0xFF4CAF50);
			mehr.setTextColor(0xFF4CAF50);
			voten.setTextColor(0xFFE91E63);
			komplette_seite.setBackgroundColor(0xFFFFFFFF);
			sprache_in_text.setTextColor(0xFFE91E63);
			rate.setTextColor(0xFF4CAF50);
			chat.setTextColor(0xFF4CAF50);
		}
		else {
			seite_zuruek.setImageResource(R.drawable.ic_chevron_left_white);
			teilen.setImageResource(R.drawable.ic_mail_white);
			aktualisieren.setImageResource(R.drawable.ic_loop_white);
			seite_vor.setImageResource(R.drawable.ic_chevron_right_white);
			platzhalter_oben.setBackgroundColor(0xFF37474F);
			menue.setBackgroundColor(0xFF455A64);
			menue2.setBackgroundColor(0xFF546E7A);
			exit.setTextColor(0xFF2196F3);
			menue_button.setTextColor(0xFF673AB7);
			einstellungen.setTextColor(0xFF2196F3);
			info.setTextColor(0xFF2196F3);
			kontakt.setTextColor(0xFF673AB7);
			mehr.setTextColor(0xFF673AB7);
			voten.setTextColor(0xFF2196F3);
			komplette_seite.setBackgroundColor(0xFF455A64);
			sprache_in_text.setTextColor(0xFF2196F3);
			rate.setTextColor(0xFF673AB7);
			chat.setTextColor(0xFF673AB7);
		}
		_Check();
		if (connected) {
			SketchwareUtil.showMessage(getApplicationContext(), "Verbindung wird hergestellt");
			kein_netz.setVisibility(View.GONE);
			kadcon_website.setVisibility(View.VISIBLE);
			_Load_website();
			if (laden == 0) {
				laden++;
				final ProgressDialog prog = new ProgressDialog(MainActivity.this);
				prog.setMax(100);prog.setTitle("Loading...");
				prog.setMessage("");
				prog.setIndeterminate(true);
				prog.show();
				Timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								prog.hide();
							}
						});
					}
				};
				_timer.schedule(Timer, (int)(2000));
			}
			if (starts.getString("starts", "").equals("")) {
				mode.edit().putString("mode", "0").commit();
				Start.setClass(getApplicationContext(), StartActivity.class);
				startActivity(Start);
				SketchwareUtil.showMessage(getApplicationContext(), "Willkommen auf KadCom");
				Zeit = Calendar.getInstance();
				first.edit().putString("first", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(Zeit.getTime())).commit();
				Starts = 1;
				startcount.edit().putString("startcount", String.valueOf((long)(Starts))).commit();
				UserNumber = SketchwareUtil.getRandom((int)(1), (int)(1000000));
				Usernumber.edit().putString("Usernumber", String.valueOf((long)(UserNumber))).commit();
				MenuCountNum = 0;
				menucount.edit().putString("menucount", String.valueOf((long)(MenuCountNum))).commit();
				reload = 0;
				reloadcounter.edit().putString("reloadcounter", String.valueOf((long)(reload))).commit();
				votecounter = 0;
				votecount.edit().putString("votecount", String.valueOf((long)(votecounter))).commit();
				nonetcount = 0;
				nonetcounter.edit().putString("nonetcounter", String.valueOf((long)(nonetcount))).commit();
			}
			else {
				MenuCountNum = Double.parseDouble(menucount.getString("menucount", ""));
				reload = Double.parseDouble(reloadcounter.getString("reloadcounter", ""));
				votecounter = Double.parseDouble(votecount.getString("votecount", ""));
				nonetcount = Double.parseDouble(nonetcounter.getString("nonetcounter", ""));
				Starts = Double.parseDouble(startcount.getString("startcount", ""));
				nonetcount = 0;
				name.setVisibility(View.GONE);
				rate.setVisibility(View.VISIBLE);
				if (namedata.getString("namedata", "").equals("")) {
					name.setVisibility(View.VISIBLE);
					rate.setVisibility(View.GONE);
					Starts = Double.parseDouble(startcount.getString("startcount", ""));
					Starts++;
					AppStartMap = new HashMap<>();
					Zeit = Calendar.getInstance();
					AppStartMap.put("Letztes mal um", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(Zeit.getTime()));
					AppStartMap.put("Anzahl des öffnens", String.valueOf((long)(Starts)));
					AppStartMap.put("First App Join", first.getString("first", ""));
					AppStartMap.put("Menü geöffnet", menucount.getString("menucount", ""));
					AppStartMap.put("Seite neu geladen", reloadcounter.getString("reloadcounter", ""));
					AppStartMap.put("Auf die Vote Seite gegangen", votecount.getString("votecount", ""));
					AppStartMap.put("App ohne Netzt geöffnet", nonetcounter.getString("nonetcounter", ""));
					AppStartMap.put("Version", p_ver);
					startcount.edit().putString("startcount", String.valueOf((long)(Starts))).commit();
					AppStats.child(Usernumber.getString("Usernumber", "")).updateChildren(AppStartMap);
					AppStartMap.clear();
				}
				else {
					Starts++;
					AppStartMap = new HashMap<>();
					Zeit = Calendar.getInstance();
					AppStartMap.put("Letztes mal um", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(Zeit.getTime()));
					AppStartMap.put("Anzahl des öffnens", String.valueOf((long)(Starts)));
					AppStartMap.put("First App Join", first.getString("first", ""));
					AppStartMap.put("Menü geöffnet", menucount.getString("menucount", ""));
					AppStartMap.put("Seite neu geladen", reloadcounter.getString("reloadcounter", ""));
					AppStartMap.put("Auf die Vote Seite gegangen", votecount.getString("votecount", ""));
					AppStartMap.put("App ohne Netzt geöffnet", nonetcounter.getString("nonetcounter", ""));
					AppStartMap.put("Version", p_ver);
					startcount.edit().putString("startcount", String.valueOf((long)(Starts))).commit();
					AppStats.child(namedata.getString("namedata", "").concat(" (".concat(Usernumber.getString("Usernumber", "").concat(")")))).updateChildren(AppStartMap);
					AppStartMap.clear();
				}
				if (rated.getString("rated", "").equals("-")) {
					rate.setVisibility(View.GONE);
				}
				menue.setVisibility(View.GONE);
				menue2.setVisibility(View.GONE);
				kein_netz.setVisibility(View.GONE);
				kadcon_website.setVisibility(View.VISIBLE);
			}
			Timer = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (name_change_number == 1) {
								name_change.setVisibility(View.VISIBLE);
							}
							else {
								name_change.setVisibility(View.GONE);
							}
						}
					});
				}
			};
			_timer.schedule(Timer, (int)(200));
		}
		else {
			MenuCountNum = Double.parseDouble(menucount.getString("menucount", ""));
			reload = Double.parseDouble(reloadcounter.getString("reloadcounter", ""));
			votecounter = Double.parseDouble(votecount.getString("votecount", ""));
			nonetcount = Double.parseDouble(nonetcounter.getString("nonetcounter", ""));
			Starts = Double.parseDouble(startcount.getString("startcount", ""));
			name_change.setVisibility(View.GONE);
			nonetcount++;
			Starts++;
			kein_netz.setVisibility(View.VISIBLE);
			kadcon_website.setVisibility(View.GONE);
			name.setVisibility(View.GONE);
			rate.setVisibility(View.GONE);
			loading.setVisibility(View.GONE);
			nonetcounter.edit().putString("nonetcounter", String.valueOf((long)(nonetcount))).commit();
			menue.setVisibility(View.GONE);
			menue2.setVisibility(View.GONE);
			if (starts.getString("starts", "").equals("")) {
				mode.edit().putString("mode", "0").commit();
				Start.setClass(getApplicationContext(), StartActivity.class);
				startActivity(Start);
				SketchwareUtil.showMessage(getApplicationContext(), "Willkommen auf KadCom");
				Zeit = Calendar.getInstance();
				first.edit().putString("first", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(Zeit.getTime())).commit();
				Starts = 1;
				startcount.edit().putString("startcount", String.valueOf((long)(Starts))).commit();
				UserNumber = SketchwareUtil.getRandom((int)(1), (int)(1000000));
				Usernumber.edit().putString("Usernumber", String.valueOf((long)(UserNumber))).commit();
				MenuCountNum = 0;
				menucount.edit().putString("menucount", String.valueOf((long)(MenuCountNum))).commit();
				reload = 0;
				reloadcounter.edit().putString("reloadcounter", String.valueOf((long)(reload))).commit();
				votecounter = 0;
				votecount.edit().putString("votecount", String.valueOf((long)(votecounter))).commit();
				nonetcount = 0;
				nonetcounter.edit().putString("nonetcounter", String.valueOf((long)(nonetcount))).commit();
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		if (clicks == 0) {
			clicks = 1;
			if (kadcon_website.canGoBack()) {
				kadcon_website.goBack();
			}
			Timer = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (clicks == 2) {
								Back_animation.setTarget(komplette_seite);
								Back_animation.setPropertyName("translationX");
								Back_animation.setFloatValues((float)(0), (float)(1000));
								Back_animation.setDuration((int)(300));
								Back_animation.setInterpolator(new AccelerateInterpolator());
								Back_animation.start();
								Timer = new TimerTask() {
									@Override
									public void run() {
										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												finish();
											}
										});
									}
								};
								_timer.schedule(Timer, (int)(300));
							}
							else {
								clicks = 0;
							}
						}
					});
				}
			};
			_timer.schedule(Timer, (int)(500));
		}
		else {
			clicks = 2;
		}
	}
	private void _Check () {
		try{
			command = "ping -c 1 google.com";
			connected = (Runtime.getRuntime().exec (command).waitFor() == 0); } catch (Exception e){ showMessage(e.toString());}
	}
	
	
	private void _Load_website () {
		kadcon_website.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onShowFileChooser(WebView webView, ValueCallback filePathCallback, FileChooserParams fileChooserParams) {
				mFilePathCallback = filePathCallback;
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT); intent.setType("*/*"); startActivityForResult(intent, PICKFILE_REQUEST_CODE);
				return true;
			}
		});
		kadcon_website.loadUrl("https://forum.kadcon.de/");
	}
	private ValueCallback mFilePathCallback;
	private static final int PICKFILE_REQUEST_CODE = 0;
	
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == PICKFILE_REQUEST_CODE) {
			if (resultCode != RESULT_OK){}else{
				Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
				Uri[] resultsArray = new Uri[1];
				resultsArray[0] = result;
				mFilePathCallback.onReceiveValue(resultsArray); }}
	}
	
	
	public class List_name_changerAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public List_name_changerAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_v = _inflater.inflate(R.layout.name_changer, null);
			}
			
			final LinearLayout linear = (LinearLayout) _v.findViewById(R.id.linear);
			final TextView name = (TextView) _v.findViewById(R.id.name);
			
			name.setText(name_changer_map.get((int)_position).get("Name").toString());
			if (namedata.getString("namedata", "").equals(name_changer_map.get((int)_position).get("Name").toString())) {
				name_change_number = 1;
			}
			else {
				name_change_number = 0;
			}
			
			return _v;
		}
	}
	
	public class Listview_updateAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview_updateAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
			
			name.setText(version_map.get((int)_position).get("Update").toString());
			
			return _v;
		}
	}
	
	public class Listview_gebannteAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview_gebannteAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
			
			banned_user.setText(gebannte_map.get((int)_position).get("User").toString());
			if (gebannte_map.get((int)_position).get("User").toString().equals(Usernumber.getString("Usernumber", ""))) {
				banned_nummer = 1;
			}
			else {
				banned_nummer = 0;
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
