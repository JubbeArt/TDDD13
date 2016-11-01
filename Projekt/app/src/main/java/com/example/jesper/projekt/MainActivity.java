package com.example.jesper.projekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		layout.setPadding(10, 0, 10, 0);

		layout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		));

		PasswordStrengthMeter passwordStrengthMeter = new PasswordStrengthMeter(this);
		layout.addView(passwordStrengthMeter);

		hidepassword

		setContentView(layout);



		//setContentView(R.layout.activity_main);
	}
}
