package com.example.jesper.lab13java;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private final LinearLayout.LayoutParams matchAndWrap = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);

	private final LinearLayout.LayoutParams wrapAndWrap = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		// Första fråga
		layout.addView(getTextView("Hur trivs du på LiU?"));
		layout.addView(getRadioGroup(new String[]{
				"Dåligt", "Bra", "Superbra"
		}));


		// Andra fråga
		layout.addView(getTextView("Läser du på LiTH?"));
		layout.addView(getRadioGroup(new String[]{
				"Ja", "Nej"
		}));

		// LiUs logga
		ImageView liuLogo = new ImageView(this);
		LinearLayout.LayoutParams imageParams = wrapAndWrap;
		imageParams.gravity = Gravity.CENTER;


		liuLogo.setLayoutParams(imageParams);
		liuLogo.setBackgroundResource(R.drawable.liu);

		layout.addView(liuLogo);

		// Sista fråga
		layout.addView(getTextView("Är detta LiUs logotyp?"));
		layout.addView(getRadioGroup(new String[]{
				"Ja", "Nej"
		}));

		// Skicka in knapp
		Button button = new Button(this);
		button.setLayoutParams(matchAndWrap);
		button.setText("Skicka in");
		layout.addView(button);

		setContentView(layout);
	}

	// Skapar ett textfält som tar upp en rad med centrerad text
	private TextView getTextView(String text) {
		TextView textView = new TextView(this);
		textView.setLayoutParams(matchAndWrap);
		textView.setText(text);
		textView.setGravity(Gravity.CENTER);

		return textView;
	}

	// Skapar en grupp med radio-knappar
	private RadioGroup getRadioGroup(String[] buttons) {
		RadioGroup group = new RadioGroup(this);
		group.setLayoutParams(matchAndWrap);
		group.setOrientation(LinearLayout.HORIZONTAL);

		for(int i = 0; i < buttons.length; i++) {
			RadioButton button = new RadioButton(this);
			button.setLayoutParams(wrapAndWrap);
			button.setText(buttons[i]);

			group.addView(button);
		}

		return group;
	}

}
