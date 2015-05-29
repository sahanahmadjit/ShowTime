package com.example.tvscheduleapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import android.R.bool;
import android.R.string;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowChannelList extends Activity {

	private static String Url = "http://www.locatetv.com/listings/and-e-network";
	private static final String mainUrl = "http://www.locatetv.com";
	private static final String LOGTAG = "cat";
	protected ListView allChannelShowListView, favChannelShowListView;
	
	
	public static String urlItem = "";

	public static ArrayList<String> linkArrList = new ArrayList<String>();
	public static ArrayList<String> favLinkArrList = new ArrayList<String>();
	public static ArrayList<String> favLinkText=new ArrayList<String>();
	private static Boolean buttonText;
	
	private static String item = "";
	Integer[] banglaChannelImageIDs = { R.drawable.pic1, R.drawable.pic2,
			R.drawable.pic3, R.drawable.pic4 };

	
	private static ArrayList<String> DownloadChannelListByJSOP(String Url) {
		ArrayList<String> objArrList = new ArrayList<String>();

		try {
			org.jsoup.nodes.Document document = Jsoup.connect(Url).get();
			Elements optionElements = (Elements) document
					.getElementsByTag("option");
			// Elements h2=clistingElement.getElementsByTag("h2");
			// result+=h2.text()+"\n";

			// Elements
			// schedTvProgram=clistingElement.getElementsByClass("schedTv");
			Element singleElement;
			for (int i = 1; i < optionElements.size(); i++) {
				singleElement = optionElements.get(i);
				objArrList.add(singleElement.text());
				linkArrList.add(mainUrl
						+ singleElement.attr("value").toString());
				// result+=schedTvProgram.get(i).text()+"\n";

			}

			return objArrList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<String>();

	}

	private class DownloadAllChannelList extends
			AsyncTask<String, Void, ArrayList<String>> {
		protected ArrayList<String> doInBackground(String... urls) {
			return DownloadChannelListByJSOP(urls[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			
			buttonText=true;
			ChannelListAdapter2 adapter = new ChannelListAdapter2(
					ShowChannelList.this, result,linkArrList,buttonText);

	
				allChannelShowListView.setAdapter(adapter);

		}
	}
	public void setAdapter(){
		ArrayList<FavouriteTvChannel> retriveLink=new Select().from(FavouriteTvChannel.class).execute();
		for(int i=0;i<retriveLink.size();i++){
			FavouriteTvChannel newLink=new FavouriteTvChannel();
			newLink=retriveLink.get(i);
			favLinkArrList.add(newLink.getLink());
			favLinkText.add(newLink.getName());
		}

		buttonText=false;
		ChannelListAdapter2 adapter = new ChannelListAdapter2(
				ShowChannelList.this, favLinkText,favLinkArrList,buttonText);

		Log.d(LOGTAG, favLinkArrList.size()+"");
			allChannelShowListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_select_list);
		Intent intent = getIntent();
		item = intent.getStringExtra(MainActivity.ChannelRefIntent);
		Log.d(LOGTAG, item);
		if (item.equals("all")) {
			//favChannelSelectBtn = (Button) findViewById(R.id.favouriteChannelStar_CheckButton);
			allChannelShowListView = (ListView) findViewById(R.id.allChannellList);

			new DownloadAllChannelList().execute(Url);
			//
			allChannelShowListView
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							// TODO Auto-generated method stub

							urlItem = linkArrList.get(position).toString();

							

							Intent intent = new Intent(ShowChannelList.this,
									ChannelSchedule.class);
							intent.putExtra("itemSend", urlItem);
							startActivity(intent);

						}
					});

		} else if (item.equals("fav")) {
		//	Toast.makeText(ShowChannelList.this,ShowChannelList.favLinkArrList.size(),Toast.LENGTH_SHORT).show();
			allChannelShowListView = (ListView) findViewById(R.id.allChannellList);
			ArrayList<FavouriteTvChannel> retriveLink=new Select().from(FavouriteTvChannel.class).execute();
			for(int i=0;i<retriveLink.size();i++){
				FavouriteTvChannel newLink=new FavouriteTvChannel();
				newLink=retriveLink.get(i);
				favLinkArrList.add(newLink.getLink());
				favLinkText.add(newLink.getName());
			}

			buttonText=false;
			ChannelListAdapter2 adapter = new ChannelListAdapter2(
					ShowChannelList.this, favLinkText,favLinkArrList,buttonText);

			Log.d(LOGTAG, favLinkArrList.size()+"");
				allChannelShowListView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				//allChannelShowListView.setVisibility(View.INVISIBLE);
			allChannelShowListView
					.setOnItemClickListener(new OnItemClickListener() {

						
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							// TODO Auto-generated method stub

							urlItem = favLinkArrList.get(position).toString();

							

							Intent intent = new Intent(ShowChannelList.this,
									ChannelSchedule.class);
							intent.putExtra("itemSend", urlItem);
							startActivity(intent);

						}
					});
		
		}
	}

}
