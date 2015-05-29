package com.example.tvscheduleapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;




import android.R.bool;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements TabListener {

	private static final String LOGTAG = "logtag";
	public static  String  ChannelRefIntent=null;
	private static  Button allChannelBtn,favouriteChannelBtn;
	
	public static final int ALLCHANNELTAB_POSITION=1;
	public static final int FAVCHANNELTAB_POSION=0;
	public static final int NUMOF_TAB=2;
	public static final String ALLCHANNEL="all";
	public static final String FAVCHANNEL="fav";
	public static Fragment[] mFragments = new Fragment[NUMOF_TAB];
	public static Fragment mALLCHANNELINSIDEFragment = null;
	public static Fragment mFAVCHANNELINSIDEFragment=null;
	public  Boolean insideFragment;

	ActionBar.Tab allChannelTab,favouriteChannelTab;
	
	Fragment allChannelFragment=new AllChannelFragmentTab();
	Fragment favChannelFragment=new FavouriteChannelFragmentTab();
	
	/*public  void IntentSend(){
		//Intent intent=getIntent();
		AllChannelFragmentTab objAllChannel=new AllChannelFragmentTab();
		//String UrlItem=intent.getStringExtra("itemSend");
		String UrlItem=((AllChannelFragmentTab) objAllChannel).getItem();
		Intent intentSend = new Intent(MainActivity.this,
                ProgramScheduleShowFragment.class);
        intentSend.putExtra("itemSend", UrlItem);
        startActivity(intentSend);
		
	}*/
	
	public void setBoolean(Boolean insideFragment){
		this.insideFragment=insideFragment;
	}
	public Boolean getBoolean(){
		return this.insideFragment;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		//--Using Custom Array Adapter
		
		// Asking for the default ActionBar element that our platform supports.
		ActionBar actionBar = getActionBar();
		 
        // Screen handling while hiding ActionBar icon.
        actionBar.setDisplayShowHomeEnabled(false);
 
        // Screen handling while hiding Actionbar title.
        actionBar.setDisplayShowTitleEnabled(false);
 
        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        allChannelTab=actionBar.newTab().setText("ALL Channel List");
        favouriteChannelTab=actionBar.newTab().setText("Fav Channel List");
        
        allChannelTab.setTabListener( this);
        favouriteChannelTab.setTabListener(this);
 
        Log.d("bug", "ok");
        
        
        
        actionBar.addTab(favouriteChannelTab);
        actionBar.addTab(allChannelTab);
		
		
		
//		allChannelBtn=(Button) findViewById(R.id.allChannelListBtn);
//		allChannelBtn.setOnClickListener(allChannelLister);
//		
//		favouriteChannelBtn=(Button) findViewById(R.id.favouriteChannelListBtn);
//		favouriteChannelBtn.setOnClickListener(favChannelLister);
//}
	/*
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private OnClickListener allChannelLister= new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
		Intent intent=new Intent(getApplicationContext(),ShowChannelList.class);
		ChannelRefIntent="all";
		
		intent.putExtra(ChannelRefIntent,"all");
		startActivity(intent);
	
	
		}
	};
	
	private OnClickListener favChannelLister= new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
		Intent intent=new Intent(getApplicationContext(),ShowChannelList.class);
		ChannelRefIntent="fav";
		intent.putExtra(ChannelRefIntent,"fav");
		startActivity(intent);
	
	
		}
	};
	
	
	
	

	
*/
	
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		int position=tab.getPosition();
		Fragment attachFragment=null;
		switch (position) {
		case ALLCHANNELTAB_POSITION:
			if(mFragments[position] == null){
				mFragments[position] = new AllChannelFragmentTab();
				ft.add(R.id.activity_main_fragment, mFragments[position], ALLCHANNEL);
				attachFragment = mFragments[position];
				//Log.d("frag",ALLCHANNEL+ position);
			}
			else if(mALLCHANNELINSIDEFragment != null)
				attachFragment = mALLCHANNELINSIDEFragment;
				
			else {
				attachFragment = mFragments[position];
			}
			break;
			
			
		

		case FAVCHANNELTAB_POSION:
			if(mFragments[position] == null){
				mFragments[position] = new FavouriteChannelFragmentTab();
				ft.add(R.id.activity_main_fragment, mFragments[position], FAVCHANNEL);
				attachFragment = mFragments[position];
				//Log.d("frag",FAVCHANNEL+ position);

			}
			else if(mFAVCHANNELINSIDEFragment != null)
				attachFragment = mFAVCHANNELINSIDEFragment;
			else {
				Log.d("frag", FAVCHANNEL+position);
				attachFragment = mFragments[position];
			}
			break;	
			
		default:
			break;
		}
		ft.attach(attachFragment);
	}
	
	@Override
	public void onBackPressed() {
		Tab tab = getActionBar().getSelectedTab();
		if(tab.getPosition() == ALLCHANNELTAB_POSITION && mALLCHANNELINSIDEFragment != null){
			android.app.FragmentManager fm = getFragmentManager();
			fm.beginTransaction().detach(mALLCHANNELINSIDEFragment)
			.attach(mFragments[ALLCHANNELTAB_POSITION])
			.commit();
			mALLCHANNELINSIDEFragment = null;
			return;
		}
		
		else if(tab.getPosition()==FAVCHANNELTAB_POSION && mFAVCHANNELINSIDEFragment!=null){
			android.app.FragmentManager fm=getFragmentManager();
			fm.beginTransaction().detach(mFAVCHANNELINSIDEFragment)
			.attach(mFragments[FAVCHANNELTAB_POSION])
			.commit();
			mFAVCHANNELINSIDEFragment=null;
			return;
		}
		
		super.onBackPressed();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if(tab.getPosition() == ALLCHANNELTAB_POSITION && mALLCHANNELINSIDEFragment != null)
			ft.detach(mALLCHANNELINSIDEFragment);
		else if(tab.getPosition()==FAVCHANNELTAB_POSION && mFAVCHANNELINSIDEFragment!=null)
			ft.detach(mFAVCHANNELINSIDEFragment);
		
		ft.detach(mFragments[tab.getPosition()]);
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	
}
