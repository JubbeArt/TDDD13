package com.example.jesper.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.Arrays;


/**
 * Created by jesper on 10/30/16.
 */

public class InteractiveSearcher extends EditText {

	private final String baseUrl = "http://flask-afteach.rhcloud.com/getnames/%d/%s";
	private static int urlID = 0;
	private int largestReceivedID = 0;
	private String[] currentSuggestions = new String[0];
	private TextWatcher textWatcher;

	Paint transWhite; //scum
	Paint textBlack;
	Rect textBounds = new Rect();


	public InteractiveSearcher(final Context context) {
		super(context);

		setText("");
		setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		));

		transWhite = new Paint();
		transWhite.setColor(Color.parseColor("#00000066"));
		textBlack = new Paint();

		textWatcher = new TextWatcher() {
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

			@Override
			public void afterTextChanged(Editable editable) {
				if(editable.length() != 0)
					new AsyncConnection(InteractiveSearcher.this).execute(getUrlString(editable.toString()));
				else
					currentSuggestions = new String[0];
			}
		};

		addTextChangedListener(textWatcher);

	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if(currentSuggestions.length != 0) {
			// Gör kopia bara för att vara säker
			String[] suggestions = currentSuggestions;

			Rect clipBounds = canvas.getClipBounds();
			clipBounds.inset(-200, -200);
			canvas.clipRect(clipBounds, Region.Op.REPLACE);

			float startX = this.getX(); // lite offset
			float startY = 0;//this.getY() + this.getHeight();

			int topOffset = 10;
			int paddingRight = 10;

			textBlack.getTextBounds("AWI", 0, 3, textBounds);
			int fontHeight = textBounds.height();
			int textWidth = 0;
			int words = suggestions.length;

			for(String suggestion : suggestions) {
				textBlack.getTextBounds(capitalize(suggestion), 0, suggestion.length(), textBounds);

				if(textBounds.width() > textWidth)
					textWidth = textBounds.width();
			}

			textWidth += paddingRight;

			canvas.drawRect(
					startX,
					startY,
					startX + textWidth,
					startY + (fontHeight + topOffset) * words,
					transWhite
			);

			for(int i = 0; i < suggestions.length; i++) {
				canvas.drawText(
						suggestions[i],
						startX,
						startY + i * (fontHeight + topOffset),
						textBlack
				);
			}

		}
	}

	private String capitalize(String word) {
		return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
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

	public void setCurrentSuggestions(String[] suggestions) {
		currentSuggestions = suggestions;

		System.out.println(Arrays.toString(currentSuggestions));
	}

}
