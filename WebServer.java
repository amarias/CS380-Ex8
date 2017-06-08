import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class WebServer {

	public static void main(String[] args) {
		try {

			ServerSocket serverSocket = new ServerSocket(8080);

			while (true) {

				Socket socket = serverSocket.accept();

				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				String text;
				String path = br.readLine();
				System.out.println(path);

				while (!(text = br.readLine()).equals("")) {
					System.out.println(text);

				}

				String[] getPath = path.split(" ");
				File file = new File("www" + getPath[1]);
				PrintStream out = new PrintStream(socket.getOutputStream(), true, "UTF-8");
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

				System.out.println("\nChecking if the file exists");
				if (file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					out.println("HTTP/1.1 200 OK");
					out.println("Content-type: text/html");
					out.println("Content-length: " + file.length() + "\n");

					Scanner scan = new Scanner(file);
					while (scan.hasNext()) {
						out.println(scan.nextLine());
					}

				} else {
					File errorFile = new File("www/404 Not Found.html");
					FileInputStream errorFis = new FileInputStream(errorFile);

					out.println("HTTP/1.1 404 Not Found");
					out.println("Content-type: text/html");
					out.println("Content-length: " + errorFile.length() + "\n");

					Scanner scanErrorFile = new Scanner(errorFile);
					while (scanErrorFile.hasNext()) {
						out.println(scanErrorFile.nextLine());
					}
				}
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
