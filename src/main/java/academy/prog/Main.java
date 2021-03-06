package academy.prog;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();

			Thread th = new Thread(new GetThread(login));
			th.setDaemon(true);
			th.start();

			System.out.println("**** For PM start your message with character \"@\" and username ****");

            System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				String to = "";

				if (text.isEmpty()) break;

				if (text.startsWith("@")) {
					String[] tmp = text.split(" ", 2);
					to = tmp[0];
					text = tmp[1];
				} else {
					to = "All";
				}

				Message m = new Message(login, text, to);
				int res = m.send(Utils.getURL() + "/add");

				if (res != 200) { // 200 OK
					System.out.println("HTTP error ocurred: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
