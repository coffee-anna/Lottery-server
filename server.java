package lottery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

public class server {
	static int numberOfTickets = 10000;
	static int index = 0;
	
	public static void getWinTickets() {		
		random random;
		random = new random();
		random.getWinTickets(numberOfTickets);
	}
	
	public static String findbestMatch(String[] clientMessageReceived)
	{		
		levenstein_distance match;
		match = new levenstein_distance();
		String path = "/Volumes/Transcend/Для учебы/Программирование/eclipse/java/server lottery/src/lottery/tickets";
		List<String> bestMatches = new ArrayList<String>();
		
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) 
		{
		    int minChanges = 10;

		    while (index++ != numberOfTickets) {
		    	String line = br.readLine();
		    	int res = match.compute_Levenshtein_distanceDP(clientMessageReceived, line.split(" "));
		    	if (res == minChanges) {
					bestMatches.add(line);
				}
				else if (res < minChanges) {
					minChanges = res;
					bestMatches.clear();
					bestMatches.add(line);
				}
				
		    }
		    index = 0;
		} 
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		String res = String.join("\nmatch: ", bestMatches);
		
		return res;		
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
			String[] clientMessage = clientMessageReceived.split(" ");
			
			if(!clientMessageReceived.equals("quite"))
			{
				System.out.println("message received: " + clientMessageReceived);
				String bestMatch = findbestMatch(clientMessage);
				if (bestMatch == null)
					System.out.println("\ntry enother ticket :(");
				else
					System.out.println("\nbest match: " + bestMatch);
				soos.writeObject(bestMatch);				
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
