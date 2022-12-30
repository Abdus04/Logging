package com.example.logging.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	private List<Product> products = new ArrayList<>(Arrays.asList( 
			new Product("p1","Cheese",3,"31/12/2022"),
			new Product("p2","Milk",5,"15/01/2023"),
			new Product("p3","Yoghurt",4,"02/02/2023"),
			new Product("p4","Juice",2,"14/06/2023"),
			new Product("p5","Beans",10,"01/03/2026"),
			new Product("p6","Beans",35,"01/03/2026"),
			new Product("p7","Beans",16,"01/03/2026")
			
			));

	public void showProducts(){
		for(Product p: products) {
			p.printProduct();
		}
	}
	
	public List<Product> getProducts(){
		return products;
	}
	
	public Product getProduct(String id) {
		return products.stream().filter(p -> p.getId().equals(id)).findFirst().get();
	}

	public void addProduct(Product product) throws Exception {
		for(Product p: products) {
			if(p.getId().equals(product.getId())) {
				throw new Exception("Item already Exists!");
			}
		}
		
		products.add(product);
		
		
	}

	public void updateProduct(String id, Product product) throws Exception{
		
		Product mp=null;
		int index=1;
		for(int i=0 ; i< products.size(); i++) {
			if(products.get(i).getId().equals(id)) {
				mp=products.get(i);
				index=i;
				break;
			}
		}
		if(mp==null) {
			throw new Exception("Item not found!");
		}else {
			products.set(index, product);
		}
		
	}

	public void deleteProduct(String id) throws Exception {
		Product dp=null;
		for(Product p: products) {
			if(p.getId().equals(id)) {
				dp=p;
				break;
			}
		}
		if(dp==null) {
			throw new Exception("Item not found!");
		}else {
			products.remove(dp);
		}
		
	}
	
	public int getExpensiveThreshold() {
		ArrayList<Integer> prices= new ArrayList<Integer>();
		for(Product p: products) {
			prices.add(p.getPrice());
		}
		List<Integer>sortedPrices=prices.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()); 
		return sortedPrices.get(prices.size()/4);
	}
}
