package com.example.logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.logging.controller.DemoController;
import com.example.logging.controller.Product;
import com.example.logging.controller.ProductService;
import com.example.logging.controller.Profiles;
import com.example.logging.controller.User;
import com.example.logging.controller.UserService;

@SpringBootApplication
public class LoggingApplication {

	public static ProductService productservice = new ProductService();
	public static UserService userservice= new UserService();
	
	static Logger logger = LoggerFactory.getLogger(LoggingApplication.class);
	private static ArrayList<HashMap<String,String>> logs = new ArrayList<HashMap<String,String>>();
	
	public static void main(String[] args) {
		SpringApplication.run(LoggingApplication.class, args);
		
		System.out.println("Welcome to string boot app! ");
		
		DisplayUserMenu();
		
	}
	
	public static void DisplayUserMenu() {
		System.out.println("Welcome to this simple application! ");
		String menu= "Choose your operation: \n1-Create a new user.\n2-Log In. \n3-Show the profiling results \n4-Show the most frequent errors \n5-Leave ";
		Boolean out=false;
		while(!out) {
			System.out.println(menu);
			Scanner scanner= new Scanner(System.in);
			
			int in = scanner.nextInt();
			switch(in) {
			case(1):
			 System.out.println("Enter the Informations of the new user: ");
			 logger.info("Attempting user craetion");
			 System.out.println("User ID: "); 
			 String id= scanner.next();
			 System.out.println("User name: "); 
			 String name= scanner.next();
			 System.out.println("User age: "); 
			 int age= scanner.nextInt();
			 System.out.println("User email: "); 
			 String email= scanner.next();
			 System.out.println("User password: "); 
			 String password= scanner.next();
			 User new_user= new User(id,name,age,email,password);
			 userservice.addUser(new_user);
			 logger.atInfo().addKeyValue("op", "SIGNUP").addKeyValue("uid", new_user.getId()).addKeyValue("uname", new_user.getName()).addKeyValue("uage", new_user.getAge()).addKeyValue("uemail", new_user.getEmail()).log();
			 System.out.println("User added!");
			 out= false;
			 break;
			case(2):
			 System.out.println("Enter your email:");
			 String log_email = scanner.next();
			 User u= userservice.getUserByEmail(log_email);
			 if(u==null) {
				System.out.println("User not Found");
				logger.atError().addKeyValue("Message", "InvalidCredentials!").log();
				out= false;
				break;
			 }else {
				System.out.println("Enter your Password: ");
				String pws= scanner.next();
				if(u.getPassword().equals(pws)) {
					System.out.println("Valid Credentials");
					logger.atInfo().addKeyValue("op", "LOGIN").addKeyValue("id", u.getId()).addKeyValue("name", u.getName()).log();;
					DisplayProductMenu(u);
					out= false;
					break;
				}else {
					System.out.println("Invalid Credentials");
					logger.atError().addKeyValue("Message", "InvalidCredentials!").log();
					out= false;
					break;
				}
		  	 }
			case(3):
				parseLog("App-logs.txt");
				Profiles profiles = new Profiles(logs);
				//profiles.printBothMaps();
				//System.out.println(productservice.getExpensiveThreshold());
				profiles.printReadProfiles();
				profiles.printWriteProfiles();
				profiles.printPriceProfiles();
				out=false;
				break;
			case(4):
				parseLog("App-logs.txt");
				Profiles error_profiles = new Profiles(logs);
				System.out.println(error_profiles.getError_logs());
			case(5):
				out=true;
				break;
		}
	}	
		
	}

	
	public static void DisplayProductMenu(User u) {
		System.out.println("You are now Logged in! ");
		String menu= "Choose your operation: \n1-View all products \n2-Fetch a product by ID \n3-Create a new product.\n4-Update an existing product. \n5-Delete a product \n6-Log out ";
		Boolean out=false;
		while(!out) {
			System.out.println(menu);
			Scanner scanner= new Scanner(System.in);
			
			int in = scanner.nextInt();
			switch(in) {
			case(1):
				
			    logger.atInfo().addKeyValue("op", "READALL").addKeyValue("uid", u.getId()).addKeyValue("uname", u.getName()).log();;
				productservice.showProducts();
				out=false;
				break;
			case(2):
				try {
					System.out.println("Enter the id of the product you want to lookup:");
			 		String f_id= scanner.next();
			 		Product fetchedProduct = productservice.getProduct(f_id);
			 		System.out.println("Product found! ");
			 		logger.atInfo().addKeyValue("op", "READONE").addKeyValue("uid", u.getId()).addKeyValue("uname", u.getName()).addKeyValue("pname", fetchedProduct.getName()).addKeyValue("pprice", fetchedProduct.getPrice()).log();;
			 		fetchedProduct.printProduct();
		 		}
		 		catch(Exception e) {
		 			logger.atError().addKeyValue("Message", "ItemNotFound!").log();
		 		}
		 		
			 	out=false;
			 	break;
		 		
		 		
			case(3):
				try { 
					System.out.println("Enter the Informations of the new product: ");
					 System.out.println("Product ID: "); 
					 String id= scanner.next();
					 System.out.println("Product name: "); 
					 String name= scanner.next();
					 System.out.println("Product price: "); 
					 int price= scanner.nextInt();
					 System.out.println("Product expiration date: "); 
					 String date= scanner.next();
					 Product new_product= new Product(id,name,price,date);
					 productservice.addProduct(new_product);
					 System.out.println("Product added!");
					 logger.atInfo().addKeyValue("op", "WRITEC").addKeyValue("uid", u.getId()).addKeyValue("uname", u.getName()).addKeyValue("pname", new_product.getName()).addKeyValue("pid", new_product.getId()).log();
					}
				catch(Exception e) {
					logger.atError().addKeyValue("Message", "ItemAlreadyExists!").log();
				}
				out= false;
				 break;	
			case(4):
				try { 
				System.out.println("Enter the id of the product you want to modify:");
				 String searched_id= scanner.next();
				 System.out.println("Enter the new Informations of the product: ");
				 System.out.println("Product ID: "); 
				 String mid= scanner.next();
				 System.out.println("Product name: "); 
				 String mname= scanner.next();
				 System.out.println("Product price: "); 
				 int mprice= scanner.nextInt();
				 System.out.println("Product expiration date: "); 
				 String mdate= scanner.next();
				 Product m_product= new Product(mid,mname,mprice,mdate);
				 productservice.updateProduct(searched_id,m_product);
				 System.out.println("Product modified!");
				 logger.atInfo().addKeyValue("op", "WRITEU").addKeyValue("uid", u.getId()).addKeyValue("uname", u.getName()).addKeyValue("pname", m_product.getName()).addKeyValue("pid", m_product.getId()).log();
				}catch(Exception e){
					logger.atError().addKeyValue("Message", "ItemNotFound!").log();
				}
				 out= false;
				 break;
			case(5):
				try {
					System.out.println("Enter the id of the product you want to delete:");
				 	String d_id= scanner.next();
				 	productservice.deleteProduct(d_id);
				 	System.out.println("Product deleted!");logger.atInfo().addKeyValue("op", "WRITED").addKeyValue("uid", u.getId()).addKeyValue("uname", u.getName()).addKeyValue("pid", d_id).log();
				}
				catch(Exception e) {
					logger.atError().addKeyValue("Message", "ItemNotFound!").log();
				}
				out=false;
			 	break;
			case(6):
				out=true;
				break;
			}
		}
	}
	
	
	public static void parseLog(String path) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();

			while (line != null) {
				String [] halves = line.split("[-]+");
				
				if(halves.length>1) {
					//System.out.println(halves[1]);
					String [] log_parts = halves[1].split("[ ]+");
					HashMap<String,String> parts_map = new HashMap<String,String>();
					for (String part:log_parts) {
						parts_map.putAll(parseParam(part));
					}
					//System.out.println(parts_map);
					logs.add(parts_map);
				}
				
				line = reader.readLine();
			}
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("ERROR READING FILE");
		}
		
	}
	
	public static Map<String,String> parseParam(String p){
		HashMap<String,String> result = new HashMap<String,String>();
		
		String [] parts = p.split("[=\"]+");
		if(parts.length>1) {
		result.put(parts[0],parts[1]);}
		return result;
	}
}
