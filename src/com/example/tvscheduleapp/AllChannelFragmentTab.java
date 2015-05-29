package com.example.tvscheduleapp;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AllChannelFragmentTab extends Fragment {

	private static String Url = "http://www.locatetv.com/listings/and-e-network";
	private static final String mainUrl = "http://www.locatetv.com";
	private static final String LOGTAG = "cat";
	protected ListView allChannelShowListView, favChannelShowListView;
	protected EditText inputSearch;
	public static String urlItem = "";

	public static ArrayList<String> linkArrList = new ArrayList<String>();
	public static ArrayList<String> favLinkArrList = new ArrayList<String>();
	public static ArrayList<String> favLinkText = new ArrayList<String>();
	private static Boolean buttonText;
	public ChannelListAdapter2 adapter;
	private static String item = "";
	

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

			buttonText = true;
			//ChannelListAdapter2
			adapter = new ChannelListAdapter2(
					getActivity(), result, linkArrList, buttonText);
			Log.d(LOGTAG, "dw");
			allChannelShowListView.setAdapter(adapter);

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View allChannelView = inflater.inflate(R.layout.channel_select_list,
				container, false);

		// favChannelSelectBtn = (Button)
		// findViewById(R.id.favouriteChannelStar_CheckButton);
		allChannelShowListView = (ListView) allChannelView.findViewById(R.id.allChannellList);
		new DownloadAllChannelList().execute(Url);
		//doSearch();
		
        inputSearch=(EditText)allChannelView.findViewById(R.id.inputSearch);
 
		
		 allChannelShowListView
		 .setOnItemClickListener(new OnItemClickListener() {
		
		 @Override
		 public void onItemClick(AdapterView<?> arg0, View arg1,
		 int position, long arg3) {
		
		 urlItem = linkArrList.get(position).toString();
		 setItem(urlItem);


			
			
			 			Fragment Fr=new ProgramScheduleShowFragment();
						FragmentManager fm = getFragmentManager();
			            FragmentTransaction fragmentTransaction = fm.beginTransaction();
			            //fragmentTransaction.replace(R.id.activity_main_fragment, Fr).addToBackStack("list");
			 	        fragmentTransaction.detach(MainActivity.mFragments[MainActivity.ALLCHANNELTAB_POSITION]);
			 	        fragmentTransaction.add(R.id.activity_main_fragment, Fr);//addToBackStack("all");
			            fragmentTransaction.commit();
			            
			            MainActivity.mALLCHANNELINSIDEFragment=Fr;
		     
		 }
		 });

		return allChannelView;
	}
	 private void doSearch() {
	       inputSearch.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				AllChannelFragmentTab.this.adapter.getFilter().filter(s);
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
			});
	 


	    }
	
	public String getItem() {
		
		return this.urlItem;
	}
	
	public void  setItem(String urlItem){
		this.urlItem=urlItem;
	}
}
