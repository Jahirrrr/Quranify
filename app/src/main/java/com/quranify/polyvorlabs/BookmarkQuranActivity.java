package com.quranify.polyvorlabs;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
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
import java.util.regex.*;
import org.json.*;

public class BookmarkQuranActivity extends AppCompatActivity {
	
	private HashMap<String, Object> sorah = new HashMap<>();
	private boolean darkMode = false;
	private String a = "";
	private String b = "";
	private double b7s = 0;
	private double length = 0;
	private double r = 0;
	private String value1 = "";
	private String save = "";
	
	private ArrayList<HashMap<String, Object>> ayat = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear4;
	private LinearLayout linear16;
	private ListView listview1;
	private LinearLayout linear6;
	private LinearLayout s_name;
	private LinearLayout linear7;
	private ImageView imageview1;
	private LinearLayout linear9;
	private LinearLayout linear14;
	private LinearLayout linear15;
	private TextView textview1;
	private TextView Name1;
	private TextView textview2;
	private TextView textview3;
	private EditText edittext2;
	
	private SharedPreferences modo;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.bookmark_quran);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear4 = findViewById(R.id.linear4);
		linear16 = findViewById(R.id.linear16);
		listview1 = findViewById(R.id.listview1);
		linear6 = findViewById(R.id.linear6);
		s_name = findViewById(R.id.s_name);
		linear7 = findViewById(R.id.linear7);
		imageview1 = findViewById(R.id.imageview1);
		linear9 = findViewById(R.id.linear9);
		linear14 = findViewById(R.id.linear14);
		linear15 = findViewById(R.id.linear15);
		textview1 = findViewById(R.id.textview1);
		Name1 = findViewById(R.id.Name1);
		textview2 = findViewById(R.id.textview2);
		textview3 = findViewById(R.id.textview3);
		edittext2 = findViewById(R.id.edittext2);
		modo = getSharedPreferences("modo", Activity.MODE_PRIVATE);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (b7s == 0) {
					linear7.setVisibility(View.VISIBLE);
					s_name.setVisibility(View.GONE);
					imageview1.setImageResource(R.drawable.ic_close_black);
					b7s++;
				}
				else {
					edittext2.setText("");
					linear7.setVisibility(View.GONE);
					s_name.setVisibility(View.VISIBLE);
					imageview1.setImageResource(R.drawable.ic_search_black);
					b7s--;
				}
			}
		});
		
		edittext2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				ayat = new Gson().fromJson(save, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				length = ayat.size();
				r = length - 1;
				for(int _repeat17 = 0; _repeat17 < (int)(length); _repeat17++) {
					value1 = ayat.get((int)r).get("id").toString();
					if (!(_charSeq.length() > value1.length()) && value1.toLowerCase().contains(_charSeq.toLowerCase())) {
						
					}
					else {
						ayat.remove((int)(r));
						listview1.setAdapter(new Listview1Adapter(ayat));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					r--;
				}
				listview1.setAdapter(new Listview1Adapter(ayat));
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
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(0xFFFFFFFF);
		linear7.setVisibility(View.GONE);
		sorah = new Gson().fromJson(getIntent().getStringExtra("sorah"), new TypeToken<HashMap<String, Object>>(){}.getType());
		Name1.setText("Surah ".concat(sorah.get("transliteration").toString()));
		textview3.setText(sorah.get("type").toString().concat(" - Number of Verses : ".concat(String.valueOf((long)(Double.parseDouble(sorah.get("total_verses").toString()))))));
		if (!sorah.isEmpty()) {
			setTitle("Surah ".concat(sorah.get("name").toString()));
			ayat = new Gson().fromJson(sorah.get("verses").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			listview1.setAdapter(new Listview1Adapter(ayat));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		}
		if (modo.getString("theme", "").equals("")) {
			modo.edit().putString("theme", "light").commit();
		}
		if (modo.getString("theme", "").equals("light")) {
			darkMode = false;
			_light();
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) 
			{
				Window w = BookmarkQuranActivity.this.getWindow();	
				if (darkMode) {
					w.setStatusBarColor(0xFF212121);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
				}
				else {
					w.setStatusBarColor(0xFFFFFFFF);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_VISIBLE);
				}
			}
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) 
			{	
				Window w = BookmarkQuranActivity.this.getWindow();	
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
				Window w = BookmarkQuranActivity.this.getWindow();	
				if (darkMode) {
					w.setStatusBarColor(0xFF212121);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
				}
				else {
					w.setStatusBarColor(0xFFFFFFFF);
					w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_VISIBLE);
				}
			}
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) 
			{	
				Window w = BookmarkQuranActivity.this.getWindow();	
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
		_removeScollBar(listview1);
		_UI_GradientLR(imageview1, "#FFA000", "#FFCA28", 30, 30, 30, 30, 0, "#ffffff", 0, "#ffffff");
		_UI_GradientLR(edittext2, "#FFA000", "#FFCA28", 30, 30, 30, 30, 0, "#ffffff", 0, "#ffffff");
		Name1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_medium.ttf"), 0);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_regular.ttf"), 0);
		edittext2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_regular.ttf"), 0);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/mushaff.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/mushaff.ttf"), 0);
		save = new Gson().toJson(ayat);
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
	
	
	public void _dark() {
		linear1.setBackgroundColor(0xFF212121);
		linear16.setBackgroundColor(0xFFFAFAFA);
		Name1.setTextColor(0xFFFFFFFF);
		textview1.setTextColor(0xFFFFFFFF);
		textview2.setTextColor(0xFFFFFFFF);
		textview3.setTextColor(0xFFFFFFFF);
	}
	
	
	public void _light() {
		linear1.setBackgroundColor(0xFFFFFFFF);
		linear16.setBackgroundColor(0xFF212121);
		Name1.setTextColor(0xFF212121);
		textview1.setTextColor(0xFF212121);
		textview2.setTextColor(0xFF212121);
		textview3.setTextColor(0xFF212121);
	}
	
	
	public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor("#FF757575")}), GG, null);
		_view.setBackground(RE);
		_view.setElevation(5);
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
				_view = _inflater.inflate(R.layout.aya, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final TextView num = _view.findViewById(R.id.num);
			final TextView aya = _view.findViewById(R.id.aya);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			final LinearLayout share0 = _view.findViewById(R.id.share0);
			final LinearLayout copy0 = _view.findViewById(R.id.copy0);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			
			num.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_regular.ttf"), 0);
			aya.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/hafs_v20.ttf"), 0);
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_medium.ttf"), 0);
			textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_medium.ttf"), 0);
			textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/heavy.ttf"), 0);
			num.setText(String.valueOf((long)(Double.parseDouble(_data.get((int)_position).get("id").toString()))));
			aya.setText(_data.get((int)_position).get("text").toString());
			textview3.setText(_data.get((int)_position).get("transliteration").toString());
			share0.setVisibility(View.GONE);
			copy0.setVisibility(View.GONE);
			if (modo.getString("theme", "").equals("dark")) {
				modo.edit().putString("theme", "dark").commit();
				aya.setTextColor(0xFFFFFFFF);
				textview1.setTextColor(0xFFFFFFFF);
				textview2.setTextColor(0xFFFFFFFF);
				textview3.setTextColor(0xFFFFFFFF);
				imageview1.setImageResource(R.drawable.ic_content_copy_white);
				imageview2.setImageResource(R.drawable.ic_share_white);
				linear5.setBackgroundColor(0xFFFFFFFF);
				_rippleRoundStroke(share0, "#212121", "#FFAB00", 15, 2.5d, "#FFFFFF");
				_rippleRoundStroke(copy0, "#212121", "#FFAB00", 15, 2.5d, "#FFFFFF");
			}
			else {
				if (modo.getString("theme", "").equals("light")) {
					modo.edit().putString("theme", "light").commit();
					aya.setTextColor(0xFF212121);
					textview1.setTextColor(0xFF212121);
					textview2.setTextColor(0xFF212121);
					textview3.setTextColor(0xFF212121);
					imageview1.setImageResource(R.drawable.ic_content_copy_black);
					imageview2.setImageResource(R.drawable.ic_share_black);
					linear5.setBackgroundColor(0xFF212121);
					_rippleRoundStroke(share0, "#FFFFFF", "#FFAB00", 15, 2.5d, "#212121");
					_rippleRoundStroke(copy0, "#FFFFFF", "#FFAB00", 15, 2.5d, "#212121");
				}
			}
			
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