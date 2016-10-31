package com.example.jesper.lab12java;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

public class MainActivity extends AppCompatActivity {

	private final LinearLayout.LayoutParams matchAndWrap = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);


		// Namn
		layout.addView(createRow("Namn", new EditText(this)));

		// Lösen
		EditText password = new EditText(this);
		password.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
		layout.addView(createRow("Lösenord", password));

		// Epost
		EditText email = new EditText(this);
		email.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		layout.addView(createRow("Epost", email));

		// Ålder
		layout.addView(createRow("Ålder", new SeekBar(this)));

		setContentView(layout);
	}

	private LinearLayout createRow(String text, View input) {
		// Container för en rad
		LinearLayout tmpLayout = new LinearLayout(this);
		tmpLayout.setLayoutParams(matchAndWrap);
		tmpLayout.setOrientation(LinearLayout.HORIZONTAL);

		// Text-delen
		TextView textView = new TextView(this);
		textView.setLayoutParams(
				new ActionBar.LayoutParams(
						120,
						LinearLayout.LayoutParams.WRAP_CONTENT
				)
		);
		textView.setText(text);

		// Input-delen får rätt dimensioner
		input.setLayoutParams(matchAndWrap);

		tmpLayout.addView(textView);
		tmpLayout.addView(input);

		return tmpLayout;
	}
}
