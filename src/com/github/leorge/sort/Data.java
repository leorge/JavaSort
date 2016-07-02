package com.github.leorge.sort;

public class Data implements Comparable {
	private int id;
	private String name;
	
	Data(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
    public int compareTo(Object obj) {
//    	return this.id - ((Data) obj).getId();
    	return this.name.compareTo(((Data) obj).getName());
    }
}
