package com.example.jesper.lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		TextView test = new TextView(this);
		test.setText("Test 1234: Text above searchbox");
		TextView test2 = new TextView(this);
		test2.setText("Test 1234: Text below searchbox");

		InteractiveSearcher searchBox = new InteractiveSearcher(this);

		layout.addView(test);
		layout.addView(searchBox);
		layout.addView(test2);

		setContentView(layout);
	}
}
