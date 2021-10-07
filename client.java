package lottery;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class client {
	public static void main(String[] arg) {
		try {
			System.out.println("server connecting...");
			Socket clientSocket = new Socket("127.0.0.1", 2525);
			System.out.println("connection established...");
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream cois = new ObjectInputStream(clientSocket.getInputStream());
			
			System.out.println("Enter the lottery number to check it\n\t('quite' – programme terminate)");
			String clientMessage = stdin.readLine();
			
			
			if (!clientMessage.equals("quite")) {
				coos.writeObject(clientMessage);
				System.out.println("~checking~: " + clientMessage);
				System.out.println("----------------------------");
				System.out.println("your ticket matches the best with: ");
				String result = (String)cois.readObject();
				System.out.println(result);
			}
						
			coos.close();
			cois.close();
			clientSocket.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
