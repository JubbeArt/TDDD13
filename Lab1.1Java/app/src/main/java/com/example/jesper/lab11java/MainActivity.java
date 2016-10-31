package com.example.jesper.lab11java;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Main container för appen
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		// För stjärnorna (ratingBar)
		layout.setGravity(Gravity.CENTER_HORIZONTAL);

		// Simpel knapp
		Button button = new Button(this);
		button.setText("Fin Knapp");
		layout.addView(button);

		// Textfält med max 1 rad
		EditText textField = new EditText(this);
		textField.setMaxLines(1);
		layout.addView(textField);

		// 5 rating-stjärnor
		RatingBar stars = new RatingBar(this);
		stars.setMax(5);
		stars.setNumStars(5);
		// Måste wrappa elementet annars: "the results may be unpredictable" - Android-API
		LinearLayout.LayoutParams starParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.addView(stars, starParams);

		// Multiline textfält
		EditText multilineTextField = new EditText(this);
		LinearLayout.LayoutParams multilineTextFieldParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, // Match == fyll allt i detta fall
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout.addView(multilineTextField, multilineTextFieldParams);

		setContentView(layout);
	}
}
