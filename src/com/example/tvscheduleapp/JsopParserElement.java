package com.example.tvscheduleapp;

public class JsopParserElement {
	
	public String program;
	public String programTime;
	public String imgSrc;
	
	public String channelLink;
	
	
	public void setProgram(String program){
		this.program=program;
	}
	
	public String getProgram(){
		return this.program;
	}
	
	public void setProgramTime(String programTime){
		this.programTime=programTime;
	
	}
	public String getProgramTime(){
		return this.programTime;
	}
	public void setImageSrc(String imgeSrc){
		this.imgSrc=imgeSrc;
	}
	public String getImageSrc(){
		return this.imgSrc;
	}

	public String getChannelLink() {
		return channelLink;
	}

	public void setChannelLink(String channelLink) {
		this.channelLink = channelLink;
	}


}
