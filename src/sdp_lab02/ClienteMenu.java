package sdp_lab02;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
public class ClienteMenu implements Runnable {
    private final BufferedReader in;
    public ClienteMenu(Socket s) throws IOException {
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }
    @Override
    public void run() {
        try {
            System.out.println("Indique o que pretene fazer:");
            System.out.println("Para adicionar algo a HashMap - R");
            System.out.println("Ver valor dado uma chave - C");
            System.out.println("Para remover um item - D");
            System.out.println("Para ver HashMap - L");
            System.out.println("Para alterar valor de uma determinada chave - alterar");
            while (true) {
                System.out.println(">");
                String serverResponse = in.readLine();
                if (serverResponse == null) break;
                System.out.println("A resposta do servidor: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}