package com.cisapmaster.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListEvent extends ListActivity {
	Context myContext = null;
	ArrayList<String> eventValues = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myContext = this;
		new GetEvents(myContext).execute();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	}

	class GetEvents extends AsyncTask {
		Context pContext = null;
		String line = "";
		BufferedReader in = null;

		public GetEvents(Context context) {
			pContext = context;
		}

		@Override
		protected Object doInBackground(Object... params) {
			eventValues = new ArrayList<String>();
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			try {
				HttpClient httpclient = new DefaultHttpClient();

				HttpGet request = new HttpGet();
				URI website = new URI(
						"http://lasir.umkc.edu:8080/cisaservice/webresources/ocisa/events");
				request.setURI(website);
				HttpResponse response = httpclient.execute(request);
				in = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				
					line = in.readLine();
				
			} catch (Exception e) {
				Log.d("AddEventException", e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object object) {
			if(line != null || !line.isEmpty())
			{
			String[] events = line.split(";");
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(myContext,
					android.R.layout.simple_list_item_1, events);
			setListAdapter(adapter);
			}

		}

	}
}
