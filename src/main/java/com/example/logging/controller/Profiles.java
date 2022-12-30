package com.example.logging.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.example.logging.LoggingApplication;

public class Profiles {

	HashMap<User,Integer> read_logs = new HashMap<User,Integer>();
	HashMap<User,Integer> write_logs = new HashMap<User,Integer>();
	HashMap<User,Integer> price_logs= new HashMap<User,Integer>();
	HashMap<String,Integer> error_logs = new HashMap<String,Integer>();
 	ArrayList<User> readProfileUsers = new ArrayList<User>();
	ArrayList<User> writeProfileUsers = new ArrayList<User>() ;
	ArrayList<User> priceProfileUsers = new ArrayList<User>() ;
	
	
	public Profiles(ArrayList<HashMap<String,String>> logs){
		//System.out.println(LoggingApplication.userservice.getUsers());
		for(HashMap<String,String> log: logs ) {
			if(log.size()>0 && log.containsKey("op")) {
				if(log.get("op").contains("READ")) {
					
					User u = LoggingApplication.userservice.getUser(log.get("uid"));
					if(read_logs.containsKey(u)) {
						read_logs.put(u,read_logs.get(u)+1);
					}
					else {
						read_logs.put(u, 1);
					}
					
					if(log.get("op").equals("READONE")) {
						if(price_logs.containsKey(u)) {
							if(price_logs.get(u)<Integer.parseInt(log.get("pprice"))) {
								price_logs.put(u, Integer.parseInt(log.get("pprice")));
							}
						}else {
							price_logs.put(u, Integer.parseInt(log.get("pprice")));
						}
					}
					
				} else if(log.get("op").contains("WRITE")) {
					User u = LoggingApplication.userservice.getUser(log.get("uid"));
					if(write_logs.containsKey(u)) {
						write_logs.put(u,write_logs.get(u)+1);
					}
					else {
						write_logs.put(u, 1);
					}
					
				}
			}else if(log.size()>0 && log.containsKey("Message")) {
				String error_message= log.get("Message");
				if (error_logs.containsKey(error_message)){
					error_logs.put(error_message, error_logs.get(error_message)+1);
				}
				else {
					error_logs.put(error_message,1);
				}
			}
		}
		
		for( User user : LoggingApplication.userservice.getUsers()) {
			if(read_logs.containsKey(user) && write_logs.containsKey(user)) {
				if(read_logs.get(user)>write_logs.get(user)) {
					readProfileUsers.add(user);
				}else {
					writeProfileUsers.add(user);
				}
			}else if(read_logs.containsKey(user)) {
				readProfileUsers.add(user);
			}else if(write_logs.containsKey(user)) {
				writeProfileUsers.add(user);
			}
		}
		
		for(Entry<User,Integer> e: price_logs.entrySet()) {
			if(e.getValue()>= LoggingApplication.productservice.getExpensiveThreshold()) {
				priceProfileUsers.add(e.getKey());
			}
		}
		
	}


	public HashMap<User, Integer> getPrice_logs() {
		return price_logs;
	}


	public ArrayList<User> getPriceProfileUsers() {
		return priceProfileUsers;
	}


	public HashMap<User, Integer> getRead_logs() {
		return read_logs;
	}


	public HashMap<User, Integer> getWrite_logs() {
		return write_logs;
	}


	public ArrayList<User> getReadProfileUsers() {
		return readProfileUsers;
	}


	public ArrayList<User> getWriteProfileUsers() {
		return writeProfileUsers;
	}
	
	public void printBothMaps() {
		String rs="{";
		String ws="{";
		String ps="{";
		String es="{";
		for(Entry<User,Integer> e: read_logs.entrySet()) {
			rs+= e.getKey().getId()+"="+e.getValue()+",  ";
		}
		for(Entry<User,Integer> f: write_logs.entrySet()) {
			ws+= f.getKey().getId()+"="+f.getValue()+",  ";
		}
		for(Entry<User,Integer> f: price_logs.entrySet()) {
			ps+= f.getKey().getId()+"="+f.getValue()+",  ";
		}
		for(Entry<String,Integer> f: error_logs.entrySet()) {
			es+= f.getKey()+"="+f.getValue()+",  ";
		}
		rs+="}";
		ws+="}";
		ps+="}";
		es+="}";
		System.out.println(rs);
		System.out.println(ws);
		System.out.println(ps);
		System.out.println(es);
		
	}
	
	public void printReadProfiles() {
		String to_print= "The users with the Read profile are: { ";
		for (User u: readProfileUsers) {
			to_print += u.getId()+" ";
			
		}
		to_print+="}";
		System.out.println(to_print);
	}
	
	public void printWriteProfiles() {
		String to_print= "The users with the Write profile are: { ";
		for (User u: writeProfileUsers) {
			to_print += u.getId()+" ";
			
		}
		to_print+="}";
		System.out.println(to_print);
	}
	public void printPriceProfiles() {
		String to_print= "The users with the Price profile are: { ";
		for (User u: priceProfileUsers) {
			to_print += u.getId()+" ";
			
		}
		to_print+="}";
		System.out.println(to_print);
	}


	public HashMap<String, Integer> getError_logs() {
		return error_logs;
	}
	
}
