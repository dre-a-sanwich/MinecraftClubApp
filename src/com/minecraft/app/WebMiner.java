package com.minecraft.app;


import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Lucas Stuyvesant
 * @author Andrea Sanchez 
 * Scrapes data from the Minecraft Club website 
 * 
 */
public class WebMiner{
	
	/**
	 * Gets world information from website
	 * 
	 * @return world info
	 * @throws IOException
	 */
	public static String getWorlds() throws IOException{
		
		//connect to website
		Connection c = Jsoup.connect("https://sites.google.com/site/nmtminecraftclub/player-guides");
		Document document = c.get();
		
		//get desired text
		String[] parts = document.select("#sites-canvas-main.sites-canvas-main div").text().toString().split("--");		    
	    String[] worldParts = parts[0].toString().split("!");
	    
		String worlds = worldParts[1].substring(4, worldParts[1].length());
		
		return worlds;
	}
	
	/**
	 * Gets all plugin information avaliable on the website
	 * 
	 * @return desired plugin information
	 * @throws IOException
	 */
	public static String getPlugins() throws IOException{
		
		//connect to website
		Connection c = Jsoup.connect("https://sites.google.com/site/nmtminecraftclub/player-guides");
		Document document = c.get();
		
		//get desired text
		String[] parts = document.select("#sites-canvas-main.sites-canvas-main div").text().toString().split("-- ");		    
	    
		String worlds = parts[1];
		
		return worlds;
	}
	
	/**
	 * Gets plugin information i.e. commands and how to use information from website
	 * 
	 * @return plugin info
	 * @throws IOException
	 */
	public static String getPlugin() throws IOException{
		
		//connect to website
		Connection c = Jsoup.connect("https://sites.google.com/site/nmtminecraftclub/player-guides");
		Document document = c.get();
		
		//get desired text
		String news = document.select("#sites-canvas-main.sites-canvas-main div").text();		    
	    return news;
	}
	
	/**
	 * get contact information from website
	 * @return contact information
	 * 
	 * @throws IOException
	 */
	public static String getContactInfo() throws IOException{
		
		//connect to website
		Connection c = Jsoup.connect("https://sites.google.com/site/nmtminecraftclub/contact-us");
		Document document = c.get();
		
		//get desired text
		String news = document.select("#sites-canvas-main.sites-canvas-main").first().text();		    
	    return news;
	}
	
	/**
	 * Get recent news about club from website
	 * 
	 * @return news concerrning the club and/or minecraft
	 * 
	 * @throws IOException
	 */
	public static String getNews() throws IOException{
		
		//connect to website
		Connection c = Jsoup.connect("https://sites.google.com/site/nmtminecraftclub/News");
		Document document = c.get();
		
		//get desired text
		String news = document.select("#sites-canvas-main.sites-canvas-main .announcement ul").first().text();		    
	    return news;
	}
	
	/**
	 * Get recent/ ongoing events the club has participated/had
	 * 
	 * @return events
	 * @throws IOException
	 */
	public static String getEvents() throws IOException{
			
		//connect to website
		Connection c = Jsoup.connect("https://sites.google.com/site/nmtminecraftclub/poster-photo-contest");
		Document document = c.get();
		
		//get desired text
		String events = document.select("#sites-page-title").first().text();		    
	    return events;
	}
	
	/**
	 * Get any other exciting news not related to meetings
	 * Such as server updates or new plugins
	 * 
	 * @return other news
	 * @throws IOException
	 */
	public static String getOther() throws IOException{
		
		//connect to website
		Connection c = Jsoup.connect("https://sites.google.com/site/nmtminecraftclub/home");
		Document document = c.get();
		
		//get desired text
		String events = document.select("#sites-canvas-main-content p").text();		    
	    return events;
	}
}
