package com.example.jesper.projekt;

import android.graphics.Color;

/**
 * Created by jesper on 10/31/16.
 */

public class PasswordStrength {
	public static final int TOO_SHORT = 0;
	public static final int WEAK = 1;
	public static final int FAIR = 2;
	public static final int GOOD = 3;
	public static final int STRONG = 4;
	public static final int TOO_LONG = 5;
	public static final int NO_CHARACTERS = 6;
	public static final int NO_NUMBERS = 7;
	public static final int NO_SPECIAL_CHARACTERS = 8;
	public static final int OTHER = 9;

	protected final String[] defaultTextRepresentations = {
			"Too short",
			"Weak",
			"Fair",
			"Good",
			"Strong",
			"Too long",
			"No characters",
			"No numbers",
			"No special characters",
			"Other"
	};

	protected String[] textRepresentations = defaultTextRepresentations;

	protected int lowestStrength = 0;
	protected int highestStrength = 4;

	protected int[] defaultStrengthColors = {
			Color.LTGRAY,
			Color.RED,
			Color.YELLOW,
			Color.BLUE,
			Color.GREEN
	};

	protected int[] strengthColors = defaultStrengthColors;

	protected final int[] defaultStrengths = {
			0, // TOO_SHORT
			1, // WEAK
			2, // FAIR
			3, // GOOD
			4, // STRONG
			3, // TOO_LONG
			2, // NO_CHARACTERS
			2, // NO_NUMBERS
			2, // NO_SPECIAL_CHARACTERS
			0, // OTHER
	};

	protected int[] strengths = defaultStrengths;

	public int addPasswordStrength(String textRepresentation, int strength, int color) {

		int newSize = textRepresentations.length + 1;

		String[] tmpTextRepresentations = new String[newSize];
		int[] tmpStrengths = new int[newSize];
		int[] tmpStrengthColors = new int[newSize];

		for(int i = 0; i < newSize - 1; i++) {
			tmpTextRepresentations[i] = textRepresentations[i];
			tmpStrengths[i] = strengths[i];
			tmpStrengthColors[i] = strengthColors[i];
		}

		tmpTextRepresentations[newSize - 1] = textRepresentation;
		tmpStrengths[newSize - 1] = strength;
		tmpStrengthColors[newSize - 1] = color;

		textRepresentations = tmpTextRepresentations;
		strengths = tmpStrengths;
		strengthColors = tmpStrengthColors;

		if(strength > highestStrength)
			highestStrength = strength;
		if(strength < lowestStrength)
			lowestStrength = strength;

		return newSize - 1;
	}

	public int addPasswordStrength(String textRepresentation, int strength) {
		return addPasswordStrength(textRepresentation, strength, Color.WHITE);
	}

	public int addPasswordStrength(String textRepresentation) {
		return addPasswordStrength(textRepresentation, 0);
	}

	public void setTextRepresentions(String[] textRepresentation){
		this.textRepresentations = textRepresentation;
	}

	public String[] getTextRepresentations() {
		return textRepresentations;
	}

	public String getTextRepresentation(int message) {
		return textRepresentations[message];
	}

	public void setTextRepresentation(int message, String textRepresentation) {
		textRepresentations[message] = textRepresentation;
	}

	public int[] getStrengths() {
		return strengths;
	}

	public void setStrengths(int[] strengths) {
		this.strengths = strengths;
	}

	public int getStrength(int message) {
		return strengths[message];
	}

	public void setStrength(int message, int strength) {
		strengths[message] = strength;
	}

	public int getLowestStrength() {
		return lowestStrength;
	}

	public void setLowestStrength(int lowestStrength) {
		this.lowestStrength = lowestStrength;
	}

	public int getHighestStrength() {
		return highestStrength;
	}

	public void setHighestStrength(int highestStrength) {
		this.highestStrength = highestStrength;
	}

	public int getStrengthColor(int strength) {
		return strengthColors[strength];
	}

	public void setStrengthColor(int strength, int color) {
		strengthColors[strength] = color;
	}

	public int[] getStrengthColors() {
		return strengthColors;
	}

	public void setStrengthColors(int[] strengthColors) {
		this.strengthColors = strengthColors;
	}
}