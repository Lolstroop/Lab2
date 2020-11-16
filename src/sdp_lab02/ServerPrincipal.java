package sdp_lab02;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ServerPrincipal extends Thread {
    private static final ExecutorService pool = Executors.newFixedThreadPool(4);
    public static void main(String[] args) {
        try {
            Socket socket1 = new Socket("localhost",9091);
            ObjectOutputStream out1 = new ObjectOutputStream(socket1.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket1.getInputStream());
            Payload payload = new Payload("Test");
            out1.writeObject(payload);
            System.out.println("[sent]" + payload.getData());
            System.out.println("[received]" + ((Payload)in.readObject()).getData());
            ServerSocket serverSocket = new ServerSocket(9090);
            System.out.println("[STARTED]");
            while(true){
                try{
                    Socket socket = serverSocket.accept();
                    System.out.println("[CONNECTION]" + socket.getInetAddress().getHostName() + "@" + socket.getInetAddress().getHostAddress() + " " + socket.getLocalPort() + ":" + socket.getPort());
                    System.out.println("[SERVER] Connected to client!");
                    ServidorParaCliente clientThread = new ServidorParaCliente(socket);
                    pool.execute(clientThread);
                    if (socket.isClosed()) System.exit(1);
                }
                catch (IOException e) {
                    if(serverSocket.isClosed()){
                        socket1.close();
                        out1.close();
                        in.close();
                        System.out.println("[terminated]");
                    }
                    else{
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    ServidoresNos servs = new ServidoresNos(null);
    public String receber(){
        return String.valueOf(servs.HM);
    }
    public boolean buscarChave (String g){
        return servs.HM.containsKey(g);
    }
    public void adicionarHashMap(String a, String b) {
        servs.HM.put(a, b);
    }
    public String buscaValor(String c) {
        return servs.HM.get(c);
    }
    public void eliminarCV(String d) {
        servs.HM.remove(d);
    }
    public void replaceValue(String e, String f) {
        servs.HM.replace(e, f);
    }
}