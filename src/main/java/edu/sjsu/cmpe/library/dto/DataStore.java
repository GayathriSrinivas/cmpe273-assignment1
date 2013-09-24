package edu.sjsu.cmpe.library.dto;

import edu.sjsu.cmpe.library.domain.*;
import java.util.HashMap;

public class DataStore {
	
	public static HashMap<Integer, Book> books;
	
	static {
		books = new HashMap<Integer, Book>();
	}
}
