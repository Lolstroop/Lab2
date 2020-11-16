package sdp_lab02;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServidoresNos extends Thread{
    HashMap<String, String> HM = new HashMap<>();
    private static final int PORT = 9091;
    private static ServerSocket serverSocket;
    private final Socket socket;
    public ServidoresNos(Socket socket){this.socket = socket;}

    public void run(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            while(true){
                Object obj = in.readObject();
                if(obj instanceof String){
                    if(obj.equals("Q")){
                        break;
                    }else if(obj.equals("T")){
                        serverSocket.close();
                        break;
                    }
                }
                else if(obj instanceof Payload){
                    System.out.println("[payload] " + ((Payload) obj).getData());
                    ((Payload) obj).setData("(modification) "+ ((Payload) obj).getData());
                    out.writeObject(obj);
                }else{
                    System.out.println("[payload] Unexpected data.");
                }
            }
            socket.close();
            out.close();
            in.close();
        } catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
    }
    public static void main(String[] args) {
        ServerPrincipal serv = new ServerPrincipal();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("[started]");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!serverSocket.isClosed()){
            try{
                Socket socket = serverSocket.accept();
                System.out.println("[connection]" + socket.getInetAddress().getHostName() + "@" + socket.getInetAddress().getHostAddress() + " " + socket.getLocalPort() + ":" + socket.getPort());
                new ServidoresNos(socket).start();
            } catch (IOException e) {
                if(serverSocket.isClosed()){
                    System.out.println("[terminated]");
                    break;
                }
                else{
                    e.printStackTrace();
                }
            }
        }
    }


}
