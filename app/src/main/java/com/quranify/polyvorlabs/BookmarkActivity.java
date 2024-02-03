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
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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

public class BookmarkActivity extends AppCompatActivity {
	
	private boolean darkMode = false;
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private LinearLayout linear1;
	private TextView textview1;
	private LinearLayout linear2;
	private ListView listview1;
	private LinearLayout linear3;
	private ImageView imageview1;
	private TextView textview2;
	
	private Intent Go = new Intent();
	private SharedPreferences modo;
	private SharedPreferences save1;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.bookmark);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		linear2 = findViewById(R.id.linear2);
		listview1 = findViewById(R.id.listview1);
		linear3 = findViewById(R.id.linear3);
		imageview1 = findViewById(R.id.imageview1);
		textview2 = findViewById(R.id.textview2);
		modo = getSharedPreferences("modo", Activity.MODE_PRIVATE);
		save1 = getSharedPreferences("save1", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(0xFFFFFFFF);
		if (save1.getString("Quran", "").equals("")) {
			listview1.setVisibility(View.GONE);
		}
		else {
			listview1.setVisibility(View.VISIBLE);
			listmap = new Gson().fromJson(save1.getString("Quran", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			listview1.setAdapter(new Listview1Adapter(listmap));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		}
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_medium.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_regular.ttf"), 0);
		_removeScollBar(listview1);
		if (modo.getString("theme", "").equals("")) {
			modo.edit().putString("theme", "light").commit();
		}
		if (modo.getString("theme", "").equals("light")) {
			darkMode = false;
			_light();
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) 
			{
				Window w = BookmarkActivity.this.getWindow();	
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
				Window w = BookmarkActivity.this.getWindow();	
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
				Window w = BookmarkActivity.this.getWindow();	
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
				Window w = BookmarkActivity.this.getWindow();	
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
	
	
	public void _removeScollBar(final View _view) {
		_view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
	}
	
	
	public void _dark() {
		textview1.setTextColor(0xFFFAFAFA);
		textview2.setTextColor(0xFFFAFAFA);
		linear1.setBackgroundColor(0xFF212121);
	}
	
	
	public void _light() {
		textview1.setTextColor(0xFF212121);
		textview2.setTextColor(0xFF212121);
		linear1.setBackgroundColor(0xFFFFFFFF);
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
				_view = _inflater.inflate(R.layout.sorah, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView name = _view.findViewById(R.id.name);
			final TextView info = _view.findViewById(R.id.info);
			
			imageview2.setImageResource(R.drawable.ic_turned_in_grey);
			name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/heavy.ttf"), 0);
			info.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/tajawal_regular.ttf"), 0);
			_UI_GradientLR(imageview1, "#FFA000", "#FFCA28", 30, 30, 30, 30, 0, "#ffffff", 0, "#ffffff");
			name.setText(_data.get((int)_position).get("name").toString());
			info.setText(_data.get((int)_position).get("info").toString());
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Go.setClass(getApplicationContext(), BookmarkQuranActivity.class);
					Go.putExtra("sorah", _data.get((int)_position).get("sorah").toString());
					startActivity(Go);
				}
			});
			if (modo.getString("theme", "").equals("dark")) {
				modo.edit().putString("theme", "dark").commit();
				name.setTextColor(0xFFFFFFFF);
				info.setTextColor(0xFFFFFFFF);
			}
			else {
				if (modo.getString("theme", "").equals("light")) {
					modo.edit().putString("theme", "light").commit();
					name.setTextColor(0xFF212121);
					info.setTextColor(0xFF212121);
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