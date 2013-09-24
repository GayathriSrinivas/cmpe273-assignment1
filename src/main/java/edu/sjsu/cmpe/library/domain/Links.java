package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;

public class Links {

	ArrayList<Link> links;
	
	public Links() {
		links = new ArrayList<Link>();
	}
	
	public Links(ArrayList<Link> links) {
		this.links = links;
	}
	
	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}
	
	public ArrayList<Link> getLinks() {
		return links;
	}
	
	public void add(Link link) {
		links.add(link);
	}

}
