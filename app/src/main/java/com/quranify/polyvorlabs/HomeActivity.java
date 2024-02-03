package com.quranify.polyvorlabs;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class HomeActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private double num = 0;
	private double position = 0;
	private String json = "";
	private HashMap<String, Object> trans = new HashMap<>();
	private boolean darkMode = false;
	private double length = 0;
	private double r = 0;
	private String value1 = "";
	private String save = "";
	private double b7s = 0;
	private HashMap<String, Object> Addd = new HashMap<>();
	private double number = 0;
	
	private ArrayList<HashMap<String, Object>> images = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> quran_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	private ArrayList<String> st = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private ImageView mode;
	private ImageView imageview2;
	private ImageView imageview1;
	private LinearLayout linear3;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout vscroll1;
	private LinearLayout linear5;
	private LinearLayout linear_1;
	private LinearLayout linear_2;
	private LinearLayout linear_3;
	private LinearLayout linear_4;
	private LinearLayout linear_5;
	private LinearLayout linear6;
	private ListView listview1;
	private EditText edittext2;
	
	private TimerTask timer;
	private Intent lntent = new Intent();
	private SharedPreferences modo;
	private SharedPreferences bookmark;
	private SharedPreferences save1;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear4 = findViewById(R.id.linear4);
		mode = findViewById(R.id.mode);
		imageview2 = findViewById(R.id.imageview2);
		imageview1 = findViewById(R.id.imageview1);
		linear3 = findViewById(R.id.linear3);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		vscroll1 = findViewById(R.id.vscroll1);
		linear5 = findViewById(R.id.linear5);
		linear_1 = findViewById(R.id.linear_1);
		linear_2 = findViewById(R.id.linear_2);
		linear_3 = findViewById(R.id.linear_3);
		linear_4 = findViewById(R.id.linear_4);
		linear_5 = findViewById(R.id.linear_5);
		linear6 = findViewById(R.id.linear6);
		listview1 = findViewById(R.id.listview1);
		edittext2 = findViewById(R.id.edittext2);
		modo = getSharedPreferences("modo", Activity.MODE_PRIVATE);
		bookmark = getSharedPreferences("bookmark", Activity.MODE_PRIVATE);
		save1 = getSharedPreferences("save1", Activity.MODE_PRIVATE);
		
		mode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_clickAnimation(mode);
				mode.setEnabled(false);
				final ViewGroup rootView = (ViewGroup) ((ViewGroup) HomeActivity.this.findViewById(android.R.id.content)).getChildAt(0);
				int centerX=0;
				{
					View _v = _view;
					 while (_v != rootView){
						 centerX += _v.getLeft(); 
						_v = (View) _v.getParent();
					}
				}
				centerX += _view.getWidth()/2;
				
				int centerY=0;
				{
					View _v = _view;
					 while (_v != rootView){
						 centerY += _v.getTop(); 
						_v = (View) _v.getParent();
					}
				}
				centerY += _view.getHeight()/2;
				int startRadius =Math.max( _view.getWidth()/2,_view.getHeight()/2);
				int endRadius = Math.max(rootView.getHeight()-centerY,rootView.getWidth() - centerX)+ startRadius;
				//Define a bitmap with the same size as the view
				Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(), rootView.getHeight(),Bitmap.Config.ARGB_8888); 
				//Bind a canvas to it 
				Canvas canvas = new Canvas(bitmap); 
				//Get the view's background
				android.graphics.drawable.Drawable bgDrawable =rootView.getBackground(); if (bgDrawable!=null){ 
					//has background drawable, then draw it on the canvas bgDrawable.draw(canvas); 
				}else{ 
					//does not have background drawable, then draw white background on the canvas 
					canvas.drawColor(Color.WHITE);
				}
				 // draw the view on the canvas 
				rootView.draw(canvas); 
				final ViewGroup contentView =(ViewGroup)HomeActivity.this.findViewById(Window.ID_ANDROID_CONTENT);
				final ImageView img = new ImageView (HomeActivity.this);
				img.setImageBitmap(bitmap);
				if (darkMode) {
					_light();
				}
				else {
					_dark();
				}
				darkMode = !darkMode;
				contentView.addView(img,0, new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				Animator cr = ViewAnimationUtils.createCircularReveal(rootView, centerX, centerY, startRadius, endRadius);
				//cr.setDuration(500);       
				cr.addListener(new Animator.AnimatorListener(){
					@Override
					public void onAnimationStart(Animator animator){
						if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) 
						{
							Window w = HomeActivity.this.getWindow();	
							if (darkMode) {
								w.setStatusBarColor(0xFF282828);
								w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
								w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
							}
							else {
								w.setStatusBarColor(0xFFF5F5F5);
								w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_VISIBLE);
							}
						}
					}
					@Override
					public void onAnimationEnd(Animator animator){
						if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) 
						{	
							Window w = HomeActivity.this.getWindow();	
							if (darkMode) {
								w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
								w.setNavigationBarColor(0xFF212121);
							}
							else {
								w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
								w.setNavigationBarColor(0xFFFFFFFF);
							}
						}
						((ViewGroup)img.getParent()).removeView(img);
						mode.setEnabled(true);
					}
					@Override
					public void onAnimationCancel(Animator animator){
					}
					@Override
					public void onAnimationRepeat(Animator animator){
					}
				});
				cr.start();
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				lntent.setClass(getApplicationContext(), BookmarkActivity.class);
				startActivity(lntent);
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (b7s == 0) {
					linear6.setVisibility(View.VISIBLE);
					imageview1.setImageResource(R.drawable.ic_close_black);
					b7s++;
				}
				else {
					edittext2.setText("");
					linear6.setVisibility(View.GONE);
					imageview1.setImageResource(R.drawable.ic_search_black);
					b7s--;
				}
			}
		});
		
		edittext2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				quran_list = new Gson().fromJson(save, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				length = quran_list.size();
				r = length - 1;
				for(int _repeat27 = 0; _repeat27 < (int)(length); _repeat27++) {
					value1 = quran_list.get((int)r).get("transliteration").toString();
					if (!(_charSeq.length() > value1.length()) && value1.toLowerCase().contains(_charSeq.toLowerCase())) {
						
					}
					else {
						quran_list.remove((int)(r));
						listview1.setAdapter(new Listview1Adapter(quran_list));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					r--;
				}
				listview1.setAdapter(new Listview1Adapter(quran_list));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
	}
	
	private void initializeLogic() {
		linear6.setVisibility(View.GONE);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_medium.ttf"), 0);
		edittext2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_regular.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/hafs_v20.ttf"), 0);
		_Add("#9E9E9E", mode);
		_UI_GradientLR(edittext2, "#FFA000", "#FFCA28", 30, 30, 30, 30, 0, "#ffffff", 0, "#ffffff");
		_UI_GradientLR(imageview1, "#FFA000", "#FFCA28", 30, 30, 30, 30, 0, "#ffffff", 0, "#ffffff");
		_UI_GradientLR(imageview2, "#FFA000", "#FFCA28", 30, 30, 30, 30, 0, "#ffffff", 0, "#ffffff");
		android.graphics.drawable.GradientDrawable CEEFAJA = new android.graphics.drawable.GradientDrawable();
		CEEFAJA.setColor(Color.parseColor("#FFFFFFFF"));
		CEEFAJA.setCornerRadii(new float[] { 50, 50, 50, 50, 0, 0, 0, 0 });
		linear4.setBackground(CEEFAJA);
		_removeScollBar(listview1);
		try {
			  java.io.InputStream is = this.getAssets().open("QuranJson.json");
			           int size = is.available();
			           byte[] buffer = new byte[size];
			           is.read(buffer);
			           is.close();
			           json = new String(buffer, "UTF-8");
			 
			quran_list = new Gson().fromJson(json, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			if (quran_list.size() > 0) {
				listview1.setAdapter(new Listview1Adapter(quran_list));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			save = new Gson().toJson(quran_list);
			listview1.setVisibility(View.VISIBLE);
		} catch(Exception e) {
			
			listview1.setVisibility(View.VISIBLE);
		}
		if (modo.getString("theme", "").equals("")) {
			modo.edit().putString("theme", "light").commit();
		}
		if (modo.getString("theme", "").equals("light")) {
			darkMode = false;
			_light();
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) 
			{
				Window w = HomeActivity.this.getWindow();	
				if (darkMode) {
					w.setStatusBarColor(0xFF282828);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
				}
				else {
					w.setStatusBarColor(0xFFF5F5F5);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_VISIBLE);
				}
			}
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) 
			{	
				Window w = HomeActivity.this.getWindow();	
				if (darkMode) {
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					w.setNavigationBarColor(0xFF282828);
				}
				else {
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
					w.setNavigationBarColor(0xFFF5F5F5);
				}
			}
		}
		if (modo.getString("theme", "").equals("dark")) {
			darkMode = true;
			_dark();
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) 
			{
				Window w = HomeActivity.this.getWindow();	
				if (darkMode) {
					w.setStatusBarColor(0xFF282828);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
				}
				else {
					w.setStatusBarColor(0xFFF5F5F5);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_VISIBLE);
				}
			}
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) 
			{	
				Window w = HomeActivity.this.getWindow();	
				if (darkMode) {
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					w.setNavigationBarColor(0xFF282828);
				}
				else {
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
					w.setNavigationBarColor(0xFFF5F5F5);
				}
			}
		}
		_clickAnimation(listview1);
	}
	
	public void _Add(final String _Colour, final ImageView _Imageview) {
		_Imageview.getDrawable().setColorFilter(Color.parseColor(_Colour), PorterDuff.Mode.SRC_IN);
	}
	
	
	public void _AFAFS() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
	}
	
	
	public void _removeScollBar(final View _view) {
		_view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
	}
	
	
	public void _UI_GradientLR(final View _view, final String _left, final String _right, final double _lt, final double _rt, final double _lb, final double _rb, final double _str, final String _str_color, final double _ele, final String _ripple) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		int clrs[] = new int[]{
			Color.parseColor(_left), Color.parseColor(_right)
		};
		gd.setColors(clrs);
		gd.setOrientation(android.graphics.drawable.GradientDrawable.Orientation.TL_BR);
		gd.setStroke((int)_str, Color.parseColor(_str_color));
		gd.setCornerRadii(new float[] {(float)_lt, (float)_lt, (float)_rt, (float)_rt, (float)_rb, (float)_rb, (float)_lb, (float)_lb});
		_view.setElevation((int)_ele);
		android.content.res.ColorStateList clrbs = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_ripple)});
		android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrbs , gd, null);
		_view.setBackground(ripdrb);
	}
	
	
	public void _fade_translate() {
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	
	public void _dark() {
		modo.edit().putString("theme", "dark").commit();
		android.graphics.drawable.GradientDrawable CEEFAJA = new android.graphics.drawable.GradientDrawable();
		CEEFAJA.setColor(Color.parseColor("#FF212121"));
		CEEFAJA.setCornerRadii(new float[] { 50, 50, 50, 50, 0, 0, 0, 0 });
		linear4.setBackground(CEEFAJA);
		linear1.setBackgroundColor(0xFF282828);
		linear6.setBackgroundColor(0xFF282828);
		edittext2.setTextColor(0xFFFFFFFF);
		textview1.setTextColor(0xFFFFFFFF);
		textview2.setTextColor(0xFFEEEEEE);
	}
	
	
	public void _light() {
		modo.edit().putString("theme", "light").commit();
		android.graphics.drawable.GradientDrawable CEEFAJA = new android.graphics.drawable.GradientDrawable();
		CEEFAJA.setColor(Color.parseColor("#FFFFFFFF"));
		CEEFAJA.setCornerRadii(new float[] { 50, 50, 50, 50, 0, 0, 0, 0 });
		linear4.setBackground(CEEFAJA);
		linear1.setBackgroundColor(0xFFF5F5F5);
		linear6.setBackgroundColor(0xFFF5F5F5);
		edittext2.setTextColor(0xFF616161);
		textview1.setTextColor(0xFF212121);
		textview2.setTextColor(0xFF212121);
	}
	
	
	public void _clickAnimation(final View _view) {
		ScaleAnimation fade_in = new ScaleAnimation(0.9f, 1f, 0.9f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.7f);
		fade_in.setDuration(300);
		fade_in.setFillAfter(true);
		_view.startAnimation(fade_in);
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
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.sorah, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView name = _view.findViewById(R.id.name);
			final TextView info = _view.findViewById(R.id.info);
			
			if (modo.getString("theme", "").equals("dark")) {
				modo.edit().putString("theme", "dark").commit();
				name.setTextColor(0xFFFFFFFF);
				info.setTextColor(0xFF757575);
			}
			else {
				if (modo.getString("theme", "").equals("light")) {
					modo.edit().putString("theme", "light").commit();
					name.setTextColor(0xFF212121);
					info.setTextColor(0xFF7E7E7E);
				}
			}
			name.setText("Surah ".concat(_data.get((int)_position).get("transliteration").toString()));
			info.setText("".concat("Number of Verses : ".concat(String.valueOf((long)(Double.parseDouble(_data.get((int)_position).get("total_verses").toString()))))));
			name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_medium.ttf"), 0);
			info.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_regular.ttf"), 0);
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					trans = _data.get((int)_position);
					lntent.setClass(getApplicationContext(), QraActivity.class);
					lntent.putExtra("sorah", new Gson().toJson(trans));
					startActivity(lntent);
					_fade_translate();
				}
			});
			if (bookmark.getString(_data.get((int)_position).get("transliteration").toString(), "").equals(_data.get((int)_position).get("transliteration").toString())) {
				imageview2.setImageResource(R.drawable.ic_turned_in_grey);
			}
			else {
				imageview2.setImageResource(R.drawable.ic_turned_in_not_grey);
			}
			imageview2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (bookmark.getString(_data.get((int)_position).get("transliteration").toString(), "").equals(_data.get((int)_position).get("transliteration").toString())) {
						
					}
					else {
						trans = _data.get((int)_position);
						Addd = new HashMap<>();
						Addd.put("name", "Surah ".concat(_data.get((int)_position).get("transliteration").toString()));
						Addd.put("sorah", new Gson().toJson(trans));
						Addd.put("info", "".concat("Number of Verses : ".concat(String.valueOf((long)(Double.parseDouble(_data.get((int)_position).get("total_verses").toString()))))));
						listmap.add(Addd);
						save1.edit().putString("Quran", new Gson().toJson(listmap)).commit();
						bookmark.edit().putString(_data.get((int)_position).get("transliteration").toString(), _data.get((int)_position).get("transliteration").toString()).commit();
						imageview2.setImageResource(R.drawable.ic_turned_in_grey);
					}
				}
			});
			imageview1.setElevation((float)50);
			imageview1.setOutlineAmbientShadowColor(0xFFA000);
			imageview1.setOutlineSpotShadowColor(0xFFA000);
			_Add("#FFFFFF", imageview1);
			_UI_GradientLR(imageview1, "#FFA000", "#FFCA28", 30, 30, 30, 30, 0, "#ffffff", 0, "#ffffff");
			
			return _view;
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
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}