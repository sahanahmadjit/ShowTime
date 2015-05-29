package com.example.tvscheduleapp;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChannelListAdapter2 extends ArrayAdapter<String> {

	private Activity context;
	private List<String> channelList;
	private List<String> channelLink;
	private Boolean buttonText;
	ShowChannelList obj = new ShowChannelList();

	public ChannelListAdapter2(Activity context, List<String> channelList,
			List<String> channelLink, Boolean buttonText) {
		super(context, R.layout.row, channelList);
		this.context = context;
		this.channelList = channelList;
		this.channelLink = channelLink;
		this.buttonText = buttonText;
	}

	public static List<FavouriteTvChannel> existingItem(String linkItem) {

		return new Select().from(FavouriteTvChannel.class)
				.where("link = ?", linkItem + "").execute();
	}


	
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.row, null, true);

		TextView channelName = (TextView) rowView
				.findViewById(R.id.channel_name);
		channelName.setText(channelList.get(position));
		// ImageView imageView = (ImageView) rowView
		// .findViewById(R.id.channel_logo);
		Button favChannelSelectBtn = (Button) rowView
				.findViewById(R.id.favouriteChannelStar_CheckButton);
		if (buttonText)
			favChannelSelectBtn.setText("Add to Favourite List");
		else
			favChannelSelectBtn.setText("Remove From Favourite List");

		favChannelSelectBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String urlItem = channelLink.get(position);
				String urlText = channelList.get(position);
				FavouriteTvChannel newLink = new FavouriteTvChannel();
				if (buttonText) {

					if (existingItem(urlItem).size() > 0)
						Toast.makeText(
								getContext(),
								urlText
										+ " is Already Exist In Favourite Channel List!",
								Toast.LENGTH_LONG).show();
					else {
						newLink.setLink(urlItem);
						newLink.setName(urlText);

						newLink.save();
						Toast.makeText(getContext(),
								"Add To Fav DB:" + newLink.getId(),
								Toast.LENGTH_LONG).show();

					}
				}
				else {
					//new Delete().from(FavouriteTvChannel.class).execute();
					//newLink.getLink();
					new Delete().from(FavouriteTvChannel.class).where("link = ?", urlItem).execute();
					channelLink.remove(position);
					channelList.remove(position);
					notifyDataSetChanged();
					Toast.makeText(getContext(),
							"Delete From Fav DB:" + urlItem,
							Toast.LENGTH_SHORT).show();

				}
			}
		});

		return rowView;

	}

}
