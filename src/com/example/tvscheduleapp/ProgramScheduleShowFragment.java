package com.example.tvscheduleapp;



import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Fragment;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class ProgramScheduleShowFragment extends Fragment {
	private ListView programList;

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
			 adapter=new ChannelListAdapter(getActivity(), result);

			programList.setAdapter(adapter);

		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View programShowView = inflater.inflate(R.layout.list,
				container, false);
		 programList=(ListView) programShowView.findViewById(R.id.schedule_list);
		AllChannelFragmentTab objAllChannel=new AllChannelFragmentTab();
		String itemUrl=objAllChannel.getItem();

		new DownloadTextTask().execute(itemUrl);
		MainActivity obj=new MainActivity();
		obj.setBoolean(true);
		
		
//		Fragment fragment;
//		fragment.getView().setFocusableInTouchMode(true);
//		fragment.getView().setOnKeyListener(new OnKeyListener() {
//			
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//		});

		
		return programShowView;
	}
	
 	
 


}
