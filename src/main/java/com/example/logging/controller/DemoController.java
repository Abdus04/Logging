package com.example.logging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
// Annotation
@RestController
 
// Class
public class DemoController {
 
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userservice;
	
	
	@RequestMapping("/users")
    public List<User> getAllUsers(){
    	return userservice.getUsers();
    }
    
    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
    	return userservice.getUser(id);
    }
    
    @RequestMapping(method= RequestMethod.POST, value = "/users")
    public void addUser(@RequestBody User user) {
    	userservice.addUser(user);
    }
	
    
    @RequestMapping("/products")
    public List<Product> getAllProducts(){
    	return productService.getProducts();
    }
    
    @RequestMapping("/products/{id}")
    public Product getProduct(@PathVariable String id) {
    	return productService.getProduct(id);
    }
    
   /* @RequestMapping(method= RequestMethod.POST, value = "/products")
    public void addProduct(@RequestBody Product product) {
    	productService.addProduct(product);
    }
    
    @RequestMapping(method= RequestMethod.PUT, value = "/products/{id}")
    public void updateProduct(@RequestBody Product product, @PathVariable String id) {
    	productService.updateProduct(id, product);
    }
    
    @RequestMapping(method= RequestMethod.DELETE, value = "/products/{id}")
    public void deleteProduct(@PathVariable String id) {
    	productService.deleteProduct(id);
    }*/
    
}