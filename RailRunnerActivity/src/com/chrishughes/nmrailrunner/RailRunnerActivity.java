package com.chrishughes.nmrailrunner;


import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RailRunnerActivity extends Activity implements OnWheelChangedListener{
	/** Called when the activity is first created. */
	
	TableLayout schedule;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Resources res = getResources();
		String[] stations = res.getStringArray(R.array.stations);
		WheelView from = (WheelView) findViewById(R.id.fromspin);
		
		ArrayWheelAdapter<String> froma = new ArrayWheelAdapter<String>(this, stations);
		froma.setTextSize(16);
		from.setViewAdapter(froma);
		
		WheelView to = (WheelView) findViewById(R.id.tospin);
		ArrayWheelAdapter<String> toa = new ArrayWheelAdapter<String>(this, stations);
		toa.setTextSize(16);
		to.setViewAdapter(toa);
		
		from.addChangingListener(this);
		to.addChangingListener(this);
		
		schedule = (TableLayout) findViewById(R.id.schedule);
		
		String[] values = new String[] { "Northbound Monday-Friday", "Southbound Monday-Friday", "Northbound Saturday",
		"Southbound Saturday", "Northbound Sunday", "Southbound Sunday" };

		// First paramenter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the View to which the data is written
		// Forth - the Array of data


		// Assign adapter to ListView



	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		TextView tv = new TextView(this);
		TableRow tr1 = new TableRow(this);
		tv.setText("Hello");
		tr1.addView(tv);
		schedule.addView(tr1);
		
	}

}