package com.example.jesper.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Arrays;

/**
 * Created by jesper on 10/31/16.
 */

public class SuggestionsBox extends View {

	private String[] currentSuggestions = new String[0];

	Paint transWhite; //scum
	Paint textBlack;
	Rect textBounds;
	int fontHeight;
	int topOffset = 20;

	InteractiveSearcher searcher;

	public SuggestionsBox(Context context, final InteractiveSearcher searcher) {
		super(context);

		this.searcher = searcher;

		textBlack = new Paint();
		textBlack.setTextSize(16);
		textBlack.setAntiAlias(true);

		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ";
		textBounds = new Rect();
		textBlack.getTextBounds(alphabet, 0, alphabet.length(), textBounds);
		fontHeight = textBounds.height();

		transWhite = new Paint();
		transWhite.setColor(Color.parseColor("#11111166"));

		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				int y = (int) e.getY();
				int wordNr = y / (fontHeight + topOffset);

				searcher.setText(capitalize(currentSuggestions[wordNr]));
				return false;
			}
		});

	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		String[] suggestions = currentSuggestions.clone();

		if(suggestions.length == 0) {
			setLayoutParams(new LinearLayout.LayoutParams(0, 0));
			return;
		}

		int textWidth = 0, paddingRight = 30;

		for(String suggestion : suggestions) {
			textBlack.getTextBounds(capitalize(suggestion), 0, suggestion.length(), textBounds);

			if(textBounds.width() > textWidth)
				textWidth = textBounds.width();
		}

		int totalWidth = textWidth + paddingRight;
		int totalHeight = (fontHeight + topOffset) * suggestions.length;

		setLayoutParams(new LinearLayout.LayoutParams(totalWidth, totalHeight));

		canvas.drawRect(0, 0, totalWidth, totalHeight, transWhite);

		for(int i = 0; i < suggestions.length; i++) {
			canvas.drawText(
					capitalize(suggestions[i]),
					paddingRight / 2,
					20 + i * (fontHeight + topOffset),
					textBlack
			);
		}

	}

	public void setCurrentSuggestions(String[] suggestions) {
		currentSuggestions = suggestions;
		invalidate();
		System.out.println(Arrays.toString(currentSuggestions));
	}

	private String capitalize(String word) {
		return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
	}
}
