package com.example.tvscheduleapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.R.string;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.DocumentsContract.Document;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChannelSchedule extends Activity {

	private ListView programList;
	private static String ATNBanglaUrl = "http://www.locatetv.com/listings/atn-bangla";

	// ---Connects using HTTP GET---
	public static InputStream OpenHttpGETConnection(String url) {
		InputStream inputStream = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			inputStream = httpResponse.getEntity().getContent();
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		return inputStream;
	}


	private static ArrayList<JsopParserElement> DownloadByJSOP(String Url) {

		try {
			org.jsoup.nodes.Document document = Jsoup.connect(Url).get();
			Element clistingElement = (Element)document.getElementById("cListings");
			Elements h2=clistingElement.getElementsByTag("h2");
			//result+=h2.text()+"\n";
	
			Elements schedTvProgram=clistingElement.getElementsByClass("schedTv");
			if(schedTvProgram.size()==0)
			schedTvProgram=clistingElement.getElementsByClass("schedMovie");

			ArrayList<JsopParserElement> objArrList=new ArrayList<JsopParserElement>();
			for(int i=0;i<schedTvProgram.size();i++){
				Element targetElement=schedTvProgram.get(i);
				JsopParserElement objJsopParserElement=new JsopParserElement();
					objJsopParserElement.programTime=targetElement.getElementsByClass("time").text();
					objJsopParserElement.setProgramTime(objJsopParserElement.programTime);
					
					objJsopParserElement.imgSrc=targetElement.getElementsByTag("img").attr("alt");
					objJsopParserElement.setImageSrc(objJsopParserElement.imgSrc);
					
					objJsopParserElement.program=targetElement.getElementsByClass("pickable").text();
					objJsopParserElement.setProgram(objJsopParserElement.program);
					
					objArrList.add(objJsopParserElement);
	
				
			}
			return objArrList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<JsopParserElement>();
	
	}

	private class DownloadTextTask extends AsyncTask<String, Void, ArrayList<JsopParserElement>> {
		protected ArrayList<com.example.tvscheduleapp.JsopParserElement> doInBackground(String... urls) {
			return DownloadByJSOP(urls[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<JsopParserElement> result) {
		//	ArrayAdapter<com.example.tvscheduleapp.JsopParserElement> adapter=new ArrayAdapter<JsopParserElement>(getApplicationContext(),android.R.layout.simple_list_item_1, result);
			ChannelListAdapter adapter;
		//	for(int i=0;i<result.size();i++)
			 adapter=new ChannelListAdapter(ChannelSchedule.this, result);

			programList.setAdapter(adapter);

		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		programList=(ListView) findViewById(R.id.schedule_list);
		
		Intent intent = getIntent();
		String itemUrl = intent.getStringExtra("itemSend");
		

		Toast.makeText(this, itemUrl, Toast.LENGTH_LONG).show();
		new DownloadTextTask().execute(itemUrl);


	}

}
