package com.example.tvscheduleapp;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.R.string;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChannelListAdapter extends BaseAdapter{

	private final Activity context;
	private final List<JsopParserElement> channelNameArray;
	public  final ImageLoader imageLoader=null;
	
	
	public ChannelListAdapter(Activity context,List<JsopParserElement> channelNameArray) {
		//super(context, R.layout.program_name_title_row);
		this.context = context;
		this.channelNameArray = channelNameArray;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//---print the index of the row to examine---
		//Log.d("CustomArrayAdapter",String.valueOf(position));
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = convertView;
		if (convertView == null) {
			rowView= inflater.inflate(R.layout.program_name_title_row, null);
		}
		
		
		//---get a reference to all the views on the xml layout---
		TextView channelName = (TextView) rowView.findViewById(R.id.programNameTxV);
		TextView channelTime = (TextView)rowView.findViewById(R.id.programTimeTxv);
		ImageView imageProgram=(ImageView) rowView.findViewById(R.id.program_image);
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.txtProgramTitle);
		imageLoader.getInstance().displayImage(channelNameArray.get(position).getImageSrc(),imageProgram,new SimpleImageLoadingListener(){
		//	Log.d("cat",channelNameArray.get(position).getImageSrc().toString());
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				// TODO Auto-generated method stub
				super.onLoadingFailed(imageUri, view, failReason);
				
			}
			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				// TODO Auto-generated method stub
				super.onLoadingComplete(imageUri, view, loadedImage);
				Log.d("cat", "s");
			}
			
		});
		//---customize the content of each row based on position---
		
		channelName.setText(channelNameArray.get(position).getProgram());
		
		channelTime.setText(channelNameArray.get(position).getProgramTime());
		
		
		//txtDescription.setText(presidents[position] + "...Some descriptions here...");
	//	imageView.setImageResource(imageIds[position]);
		
		//imageProgram.setImageResource(R.drawable.pic1);
		
		
		
		
		return rowView;
		
		
		
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return channelNameArray.size();
	}

	@Override
	public Object getItem(int position) {
	return	channelNameArray.get(position);
		
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
