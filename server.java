package lottery;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class server {
	static int numberOfTickets = 10000;
	static int index = 0;
	
	public static void getWinTickets() {		
		random random;
		random = new random();
		random.getWinTickets(numberOfTickets);
	}
	
	public static String[] findbestMatch(String clientMessageReceived)
	{
		//String bestMatch;
		int[] ticketMatches;
		ticketMatches = new int[numberOfTickets];
		FileInputStream tickets;
		
		levenstein_distance match;
		match = new levenstein_distance();
		String path = "/Volumes/Transcend/Для учебы/Программирование/eclipse/java/server lottery/src/lottery/tickets";
			
		try {
			tickets = new FileInputStream(path);
				for (int i = 0; i < numberOfTickets; i++) {
					String line = tickets.readNBytes(10).toString();
				}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		//find max resemblance number
		int bestMatch = Arrays.stream(ticketMatches).min().getAsInt();
		
		List<String> bestMatches = new ArrayList<String>();
		try {
			tickets = new FileInputStream(path);
			for (int i = 0; i < numberOfTickets; i++) {
	        	if (ticketMatches[index++] == bestMatch)
	        		bestMatches.add(line);  
	        	System.out.println(line);
	        };	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return (String[])bestMatches.toArray();
		
	}
	
	public static void main(String[] args)
	{
		ServerSocket serverSocket = null;
		Socket clientAccepted = null;
		
		ObjectInputStream sois = null;
		ObjectOutputStream soos = null;
		
		getWinTickets();
		
		try {
			System.out.println("server starting...");
			serverSocket = new ServerSocket(2525);
			clientAccepted = serverSocket.accept();
			System.out.println("connection established...");
			
						
			sois = new ObjectInputStream(clientAccepted.getInputStream());
			soos = new ObjectOutputStream(clientAccepted.getOutputStream());
			String clientMessageReceived=(String)sois.readObject();
			
			if(!clientMessageReceived.equals("quite"))
			{
				System.out.println("message received: " + clientMessageReceived);
				String[] bestMatch = findbestMatch(clientMessageReceived);
				System.out.println(Arrays.toString(bestMatch));
				String clientMessage = bestMatch.toString();
				System.out.println("best match: " + clientMessage);
				System.out.println(clientMessage);
				soos.writeObject(clientMessage);				
			}
		}		
		catch(Exception e) {
			}
		finally {
			try {
				sois.close();
				soos.close();
				clientAccepted.close();
				serverSocket.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
