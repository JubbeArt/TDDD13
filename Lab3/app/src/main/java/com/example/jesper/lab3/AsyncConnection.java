package com.example.jesper.lab3;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jesper on 10/30/16.
 */

class AsyncConnection extends AsyncTask<String, Void, String> {
	private InteractiveSearcher searcher;

	public AsyncConnection(InteractiveSearcher searcher) {
		super();
		this.searcher = searcher;
	}

	protected String doInBackground(String... urls) {
		String result = "";
		HttpURLConnection connection = null;
		BufferedReader reader = null;

		try {
			URL url = new URL(urls[0]);
			connection = (HttpURLConnection) url.openConnection();

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line;

			while((line = reader.readLine()) != null)
				stringBuilder.append(line);

			result = stringBuilder.toString();

		}  catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();

			try {
				if(reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	protected void onPostExecute(String results) {
		try {
			JSONObject json = new JSONObject(results);

			int id = json.getInt("id");

			if(id > searcher.getLargestReceivedID()) {
				JSONArray jsonResult = json.getJSONArray("result");
				int length = jsonResult.length();
				String[] resultArray = new String[length];

				for(int i = 0; i < length; i++)
					resultArray[i] = jsonResult.getString(i);

				searcher.setLargestReceivedID(id);
				searcher.getSuggestionsBox().setCurrentSuggestions(resultArray);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}