package com.example.jesper.lab3;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by jesper on 10/30/16.
 */

public class InteractiveSearcher extends LinearLayout {

	private final String baseUrl = "http://flask-afteach.rhcloud.com/getnames/%d/%s";
	private static int urlID = 0;
	private int largestReceivedID = 0;

	private EditText textField;
	private TextWatcher textWatcher;
	private SuggestionsBox suggestionsBox;

	private LayoutParams matchAndWrap = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	private LayoutParams wrapAndWrap = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	public InteractiveSearcher(Context context) {
		super(context);

		setOrientation(VERTICAL);
		setLayoutParams(matchAndWrap);

		textField = new EditText(context);
		textField.setLayoutParams(matchAndWrap);

		textWatcher = new TextWatcher() {
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

			@Override
			public void afterTextChanged(Editable editable) {
				if(editable.length() != 0)
					new AsyncConnection(InteractiveSearcher.this).execute(getUrlString(editable.toString()));
				else
					suggestionsBox.setCurrentSuggestions(new String[0]);
			}
		};

		textField.addTextChangedListener(textWatcher);

		suggestionsBox = new SuggestionsBox(context, this);
		suggestionsBox.setLayoutParams(wrapAndWrap);

		addView(textField);
		addView(suggestionsBox);
	}

	public void setText(String text) {
		textField.removeTextChangedListener(textWatcher);
		textField.setText(text);
		textField.addTextChangedListener(textWatcher);

		suggestionsBox.setCurrentSuggestions(new String[0]);
	}

	private String getUrlString(String searchTerm) {
		urlID++;
		return String.format(baseUrl, urlID, searchTerm);
	}

	public void setLargestReceivedID(int id) {
		largestReceivedID = id;
	}

	public int getLargestReceivedID() {
		return largestReceivedID;
	}

	public SuggestionsBox getSuggestionsBox() {
		return suggestionsBox;
	}

}
