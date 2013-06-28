package com.chrishughes.nmrailrunner;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import au.com.bytecode.opencsv.CSVReader;

import com.google.analytics.tracking.android.EasyTracker;

public class RailRunnerActivity extends Activity implements OnWheelScrollListener,OnItemSelectedListener{
	/** Called when the activity is first created. */

	private TableLayout schedule;
	private int dotw;
	private WheelView from;
	private WheelView to;
	private List<String[]> data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Resources res = getResources();
		String[] stations = res.getStringArray(R.array.stations);

		Spinner spin = (Spinner) findViewById(R.id.select);
		spin.setOnItemSelectedListener(this);

		from = (WheelView) findViewById(R.id.fromspin);

		ArrayWheelAdapter<String> froma = new ArrayWheelAdapter<String>(this, stations);
		froma.setTextSize(16);
		from.setViewAdapter(froma);

		to = (WheelView) findViewById(R.id.tospin);
		ArrayWheelAdapter<String> toa = new ArrayWheelAdapter<String>(this, stations);
		toa.setTextSize(16);
		to.setViewAdapter(toa);

		to.addScrollingListener(this);
		from.addScrollingListener(this);

		schedule = (TableLayout) findViewById(R.id.schedule);

		String[] values = new String[] { "Northbound Monday-Friday", "Southbound Monday-Friday", "Northbound Saturday",
				"Southbound Saturday", "Northbound Sunday", "Southbound Sunday" };



		// First paramenter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the View to which the data is written
		// Forth - the Array of data


		// Assign adapter to ListView
		try{
			InputStream raw = null;
			raw = getAssets().open("rr.csv");
			CSVReader reader = new CSVReader(new InputStreamReader(raw));
			data = reader.readAll();
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
			finish();
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}

	@Override
	public void onScrollingFinished(WheelView wheel) {
		refresh();
	}

	@Override
	public void onScrollingStarted(WheelView wheel) {		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,long arg3) {
		dotw=pos;
		refresh();
	}

	private void refresh() {
		schedule.removeAllViews();
		int fromid = from.getCurrentItem()+1;
		int toid = to.getCurrentItem()+1;
		if (fromid > toid){
			fromid = 28 - fromid;
			toid = 28 - toid;
		}
		fromid += 28*dotw;
		toid+= 28*dotw;
		String[] fromtimes = data.get(fromid);
		String[] totimes = data.get(toid);
		for (int i = 1; i < fromtimes.length; i++) {
			if (fromtimes[i].equals("") || totimes[i].equals("")){
				continue;
			}
			TextView tv = new TextView(this);
			tv.setText(fromtimes[i]+" - "+totimes[i]);
			tv.setTextSize(23f);
			tv.setPadding(10, 2, 10, 2);
			TableRow tr = new TableRow(this);
			tr.addView(tv);
			schedule.addView(tr);
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

}