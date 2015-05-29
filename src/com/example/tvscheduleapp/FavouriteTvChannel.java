package com.example.tvscheduleapp;

import java.lang.reflect.Modifier;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import android.media.Image;
@Table(name="favouriteLinkModel")
public class FavouriteTvChannel extends Model{
	@Column(name="name")
	private String name;
	@Column(name="link")
	private String link;
	@Column(name="imageLink")
	private String imageLink;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	


}
