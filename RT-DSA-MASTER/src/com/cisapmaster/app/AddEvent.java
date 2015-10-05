package com.cisapmaster.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddEvent extends Activity {
	Context myContext = null;
	EditText eventName = null;
	Button add = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myContext = this;
		setContentView(R.layout.addevent);
		eventName = (EditText) findViewById(R.id.editText1);
		add = (Button) findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (eventName != null
						&& !eventName.getText().toString().isEmpty()) {
					Log.d("AddEvent", "Exeecuting Async Task");
					new SaveEvent(myContext, eventName.getText().toString())
							.execute();
				}

			}
		});
	}
	class SaveEvent extends AsyncTask {
		Context pContext = null;
		String eventName = null;
		String eList = null;
		BufferedReader in = null;
		String result = "";
		ProgressDialog mp = null;

		public SaveEvent(Context context, String event) {
			pContext = context;
			eventName = event;
			mp = new ProgressDialog(pContext);
			mp.setTitle("Network Operation");
			mp.setMessage("Uploading Event ... ");
			mp.show();
		}

		@Override
		protected Object doInBackground(Object... params) {
			String temp = "";
			// TODO Auto-generated method stub
			try {
				HttpClient httpclient = new DefaultHttpClient();

				HttpGet request = new HttpGet();
				String url = "http://lasir.umkc.edu:8080/cisaservice/webresources/ocisa/putevents?event="
						+ eventName;
				URI website = new URI(url);
				request.setURI(website);
				HttpResponse response = httpclient.execute(request);
				in = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				result = in.readLine();
			} catch (Exception e) {
				Log.d("AddEventException", e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object object) {
			if (result.equals("1")) {
				mp.dismiss();
				Toast.makeText(myContext, eventName + " is addded succesfully",
						Toast.LENGTH_LONG).show();
			}
		}

	}
}
