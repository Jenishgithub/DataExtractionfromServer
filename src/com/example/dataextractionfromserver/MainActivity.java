package com.example.dataextractionfromserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
	Button btnExtratFromServer;
	String url = "http://192.168.0.78/crossover/transportation/admin/jsondata";
	TextView tvCount;
	int count;
	String response2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnExtratFromServer = (Button) findViewById(R.id.btnExtratFromServer);
		tvCount = (TextView) findViewById(R.id.tvCount);
		btnExtratFromServer.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		new DoNetworkOperations().execute();

	}

	public class DoNetworkOperations extends AsyncTask<Void, Void, String> {
		String result;
		JSONObject jsonObject;

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// DefaultHttpClient httpClient = new DefaultHttpClient();
			// HttpEntity entity = null;
			// HttpResponse httpResponse = null;
			try {
				result = readJsonFromUrl(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.d("crosssver", "response from server is " + result);
			response2 = result;
			count = response2.length();
			Log.d("crossover", "the length of all values is:" + count);
			tvCount.setText(String.valueOf(count));

			char f = response2.charAt(count - 61);
			Log.d("crosssver", "last character of the json is : " + f);
		}

	}

	// private static String readAll(BufferedReader rd) throws IOException {
	// StringBuilder sb = new StringBuilder();
	// String inputLine;
	//
	// while ((inputLine = rd.readLine()) != null) {
	// sb.append(inputLine);
	// }
	//
	// return sb.toString();
	// }

	public static String readJsonFromUrl(String url) throws IOException,
			JSONException {
		StringBuilder sb = new StringBuilder();
		String inputLine;
		InputStream is = new URL(url).openStream();
		try {
			// GZIP InputStream
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					new GZIPInputStream(is), Charset.forName("UTF-8")));

			while ((inputLine = rd.readLine()) != null) {
				sb.append(inputLine);
			}
			String jsonText = sb.toString();

			return jsonText;
		} finally {
			is.close();
		}
	}

}