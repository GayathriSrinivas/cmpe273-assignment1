package edu.sjsu.cmpe.library.domain;


public class Link {

	private String rel;
	private String href;
	private String method;
	
	public Link(){
		
	}
	
	public Link(String rel,String href,String method){
		this.rel = rel;
		this.href = href;
		this.method = method;
	}
	
	public String getRel(){
		return rel;
	}
	
	public String getHref(){
		return href;
	}
	
	public String getMethod(){
		return method;
	}
	
}
