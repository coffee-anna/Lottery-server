package lottery;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;

public class random {
	public void getWinTickets(int num) {
		FileOutputStream tickets;
		Random random = new Random();
		String lineSeparator = System.getProperty("line.separator");
		
		try {
			tickets = new FileOutputStream("/Volumes/Transcend/Для учебы/Программирование/eclipse/java/server lottery/src/lottery/tickets");
			for (int i = 0; i < num; i++) {
				for (int j = 0; j < 10; j++) {
					try {
						String content = String.valueOf(random.nextInt(100) + 1) + " ";
						tickets.write(content.getBytes());
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				tickets.write(lineSeparator.getBytes());
				tickets.flush();
			}
			tickets.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
