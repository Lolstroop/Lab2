package sdp_lab02;
import java.io.*;
import java.net.Socket;
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9090);
            ClienteMenu serverConn = new ClienteMenu(socket);
            new Thread(serverConn).start();
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String command = keyboard.readLine();
                if (command.equals("quit")) break;
                out.println(command);
            }
        } catch (IOException e) { e.printStackTrace();}
    }
}
