package com.messages.telegram;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;




import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Message {
	
	private static int wordsNum = 10;
	
	/**
	 * This is a main function.
	 */
	
	private static void test() throws IOException {
		String urlString = "https://store.playstation.com/en-us/grid/STORE-MSF77008-ALLGAMES/1?price=0-0";
		
		
		urlString = String.format(urlString);
		
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
			
		StringBuilder sb = new StringBuilder();
		InputStream is = new BufferedInputStream(conn.getInputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String inputLine = "";
		while ((inputLine = br.readLine()) != null) {
			sb.append(inputLine);
		}
		
		String response = sb.toString();
		System.out.println(response);
	}
	
	
	public static void main(String[] args) throws IOException {
		
		polska();
//		
//		String[] array = getWord();
//				
//		String test = "";
//		
//		for(int i = 0; i < wordsNum; i++) {
////			test = test + array[i] + "%0A";
//			test = test + array[i] + "\n\n";
//		}
//		
//		String messageText = URLEncoder.encode(test, "UTF-8");
//		Message.send(messageText);
	}
	
	private static void polska() throws IOException {
		
		String[] array = getWord();
		
		String test = "";

		for(int i = 0; i < wordsNum; i++) {
		//	test = test + array[i] + "%0A";
			test = test + array[i] + "\n";
			System.out.println(test);
		}

		String messageText = URLEncoder.encode(test, "UTF-8");
		Message.send(messageText);
	}
	
	/**
	 * this function stops the code
	 * @param sleep - time in seconds
	 */
	private static void sleep(int sleep){
		try{
			Thread.sleep(sleep);			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this function sends the message to the channel
	 * @param text
	 * @throws IOException
	 */
	private static void send(String text) throws IOException {
		String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
		String apiToken = "676719947:AAFIOGYVcvyuq5ssYJQgSWR9zCwsCfBiiyY";
//		String chatId = "@My_test_alex";
		String chatId = "@polska_words";
		
		urlString = String.format(urlString, apiToken, chatId, text);
		
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
			
		StringBuilder sb = new StringBuilder();
		InputStream is = new BufferedInputStream(conn.getInputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String inputLine = "";
		while ((inputLine = br.readLine()) != null) {
			sb.append(inputLine);
		}
		
		String response = sb.toString();
		// Do what you want with response
		String[] parse = response.split("[\"]");
		System.out.println(parse[1]);
	}

	/**
	 * this function gets the array of three words
	 * @return - the array
	 */
	private static String[] getWord() {
		ArrayList<String> list = parseFile("C:/Users/oleksandr.nadzon/Desktop/polska_words.txt");
		String[] words = new String[wordsNum];
		
		int max = list.size() - 1;
		int min = 0;
		
		int count = 0;
		
		while(count < wordsNum) {
			int index = (int) (Math.random() * (max - min));
			if(!checkWord(words, list.get(index))) {
				words[count] = new String(list.get(index));
				count++;
			}
		}
		return words;
	}
	
	/**
	 * this function checks the word in the array
	 * @param words - array of words
	 * @param word - added word
	 * @return false of true (if the array contains the word)
	 */
	private static boolean checkWord(String[] words, String word) {
		boolean check = false;
		for(int i = 0; i < words.length; i++) {
			if(words[i] != null) {
				if(words[i].equals(word)) {
					check = true;
				}
			}
		}
		return check;
	}
	
	/**
	 * this function parses the file
	 * @param filePath - the path to file
	 * @return the array list of parsed file
	 */
	private static ArrayList<String> parseFile(String filePath) {
		File file = new File(filePath);
	    FileReader fileReader = null;
	    ArrayList<String> list = new ArrayList<String>();
	    
	    String line = null;

	    try{
	    	fileReader = new FileReader(file);
	    	BufferedReader bufferedReader = new BufferedReader(fileReader);
//	    	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "ISO 8859-2"));
	    	while((line = bufferedReader.readLine()) != null){
	    		
//	    		if(line.contains("ś")) {
//	    			System.out.println("-------");
//	    		}
	    		
//	    		System.out.println("BLA" + line);
	    		
	    		
	    		
	    		list.add(line);
		    }
	    	bufferedReader.close();
		    fileReader.close();
	    }
	    catch(FileNotFoundException e){
	    	e.printStackTrace();
	    }
	    catch(IOException e) {
	    	e.printStackTrace();	    	
	    }
	    return list;
	}
}
