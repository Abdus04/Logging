package com.example.logging.controller;

public class Product {

	private String id;
	private String name;
	private int price;
	private String date;
	
	
	public Product() {
		
	}
	
	public Product(String id, String name, int price, String date) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.date = date;
	}
	public void printProduct() {
		System.out.println("Product : Id ="+id+", name= "+name+", price= "+price+", expiration date= "+date);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
