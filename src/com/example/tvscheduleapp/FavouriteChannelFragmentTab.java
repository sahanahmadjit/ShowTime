package com.example.tvscheduleapp;

import java.util.ArrayList;

import com.activeandroid.query.Select;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;



public class FavouriteChannelFragmentTab extends Fragment {
	private static String Url = "http://www.locatetv.com/listings/and-e-network";
	private static final String mainUrl = "http://www.locatetv.com";
	private static final String LOGTAG = "cat";
	protected ListView allChannelShowListView, favChannelShowListView;

	public static String urlItem = "";

	public static ArrayList<String> linkArrList = new ArrayList<String>();
	public static ArrayList<String> favLinkArrList = new ArrayList<String>();
	public static ArrayList<String> favLinkText = new ArrayList<String>();
	private static Boolean buttonText;

	private static String item = "";
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View favChannelView = inflater.inflate(R.layout.channel_select_list, container, false);
        
		allChannelShowListView = (ListView) favChannelView.findViewById(R.id.allChannellList);
		ArrayList<FavouriteTvChannel> retriveLink=new Select().from(FavouriteTvChannel.class).execute();
		favLinkArrList.clear();
		favLinkText.clear();
		for(int i=0;i<retriveLink.size();i++){
			FavouriteTvChannel newLink=new FavouriteTvChannel();
			newLink=retriveLink.get(i);
			favLinkArrList.add(newLink.getLink());
			favLinkText.add(newLink.getName());
		}

		buttonText=false;
		ChannelListAdapter2 adapter = new ChannelListAdapter2(
				getActivity(), favLinkText,favLinkArrList,buttonText);

		Log.d(LOGTAG, favLinkArrList.size()+"");
			allChannelShowListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			
			allChannelShowListView
			.setOnItemClickListener(new OnItemClickListener() {

				
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub

					urlItem = favLinkArrList.get(position).toString();
					AllChannelFragmentTab obj=new AllChannelFragmentTab();
					obj.setItem(urlItem);
					

//					Intent intent = new Intent(ShowChannelList.this,
//							ChannelSchedule.class);
//					intent.putExtra("itemSend", urlItem);
//					startActivity(intent);
					
					


						
						
					 			Fragment Fr=new ProgramScheduleShowFragment();
								FragmentManager fm = getFragmentManager();
					            FragmentTransaction fragmentTransaction = fm.beginTransaction();
					            //fragmentTransaction.replace(R.id.activity_main_fragment, Fr).addToBackStack("list");
					 	        fragmentTransaction.detach(MainActivity.mFragments[MainActivity.FAVCHANNELTAB_POSION]);
					 	        fragmentTransaction.add(R.id.activity_main_fragment, Fr);//.addToBackStack("fav");
					            fragmentTransaction.commit();
					            
					            MainActivity.mFAVCHANNELINSIDEFragment=Fr;

				}
			});
        return favChannelView;
    }
}
