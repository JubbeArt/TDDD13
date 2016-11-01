package com.example.jesper.projekt;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * Created by jesper on 10/31/16.
 */

public class PasswordStrengthMeter extends LinearLayout {

	protected Context context;
	protected PasswordStrength passwordStrength;
	protected PasswordStrengthAlgorithm algorithm;

	protected EditText passwordField;
	protected TextWatcher textWatcher;

	protected ProgressBar progressBar;

	protected TextView passwordStrengthView;
	protected String defaultPasswordStrength = "Too short";

	protected int currentStrength;
	protected int currentMessage;
	protected String currentTextRepresentation;

	protected LayoutParams matchAndWrap = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT,
			LayoutParams.WRAP_CONTENT
	);
	public PasswordStrengthMeter(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init() {
		setOrientation(VERTICAL);
		setLayoutParams(matchAndWrap);

		passwordStrength = new PasswordStrength();

		currentMessage = PasswordStrength.TOO_SHORT;
		currentStrength = passwordStrength.getStrength(currentMessage);
		currentTextRepresentation = defaultPasswordStrength;

		passwordField = new EditText(context);
		passwordField.setLines(1);
		passwordField.setLayoutParams(matchAndWrap);

		progressBar = (ProgressBar) inflate(context, R.layout.progressbar, null);
		progressBar.setMax(passwordStrength.getHighestStrength());
		progressBar.setProgress(0);
		progressBar.setProgressTintList(ColorStateList.valueOf(
				passwordStrength.getStrengthColor(currentStrength)
		));

		passwordStrengthView = new TextView(context);
		passwordStrengthView.setText(defaultPasswordStrength);
		passwordStrengthView.setLayoutParams(matchAndWrap);
		passwordStrengthView.setPadding(5, 0, 5, 0);
		passwordStrengthView.setGravity(Gravity.END);

		textWatcher = new TextWatcher() {
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

			@Override
			public void afterTextChanged(Editable editable) {
				currentMessage = algorithm.checkPassword(editable.toString());
				currentStrength = passwordStrength.getStrength(currentMessage);
				currentTextRepresentation = passwordStrength.getTextRepresentation(currentMessage);

				passwordStrengthView.setText(currentTextRepresentation);
				progressBar.setMax(passwordStrength.getHighestStrength());
				progressBar.setProgress(currentStrength);
				progressBar.setProgressTintList(ColorStateList.valueOf(
						passwordStrength.getStrengthColor(currentStrength)
				));
				/*
				progressBar.getProgressDrawable().setColorFilter(
						passwordStrength.getStrengthColor(currentStrength),
						android.graphics.PorterDuff.Mode.MULTIPLY
				);*/
			}
		};

		algorithm = new PasswordStrengthAlgorithm() {
			@Override
			public int checkPassword(String password) {

				if(password.length() < 8)
					return PasswordStrength.TOO_SHORT;

				int strengthPoints = 0;

				if(!password.toLowerCase().equals(password) && password.matches(".*[a-zA-Z].*"))
					strengthPoints++;
				if(password.length() >= 12)
					strengthPoints++;
				if(password.matches(".*\\d+.*"))
					strengthPoints++;
				if(password.matches(".*[a-zA-Z].*"))
					strengthPoints++;
				if(password.matches(".*[^A-Za-z0-9].*"))
					strengthPoints++;

				System.out.println(strengthPoints);

				if(strengthPoints <= 2)
					return PasswordStrength.WEAK;
				else if(strengthPoints == 3)
					return PasswordStrength.FAIR;
				else if(strengthPoints == 4)
					return PasswordStrength.GOOD;
				else
					return PasswordStrength.STRONG;
			}
		};

		passwordField.addTextChangedListener(textWatcher);

		addView(passwordField);
		addView(progressBar);
		addView(passwordStrengthView);

	}

	public int getCurrentStrength() {
		return currentStrength;
	}

	public int getCurrentMessage() {
		return currentMessage;
	}

	public String getCurrentTextRepresentation() {
		return currentTextRepresentation;
	}

	public PasswordStrength getPasswordStrength() {
		return passwordStrength;
	}

	public void setPasswordStrength(PasswordStrength passwordStrength) {
		this.passwordStrength = passwordStrength;
	}

	public EditText getPasswordField() {
		return passwordField;
	}

	public void setAlgorithm(PasswordStrengthAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public PasswordStrengthAlgorithm getAlgorithm() {
		return algorithm;
	}

}
