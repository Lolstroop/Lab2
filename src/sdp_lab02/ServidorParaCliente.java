package sdp_lab02;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServidorParaCliente implements Runnable{
    private BufferedReader in, input;
    private PrintWriter out;
    public ServidorParaCliente(Socket clientSocket){
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) { e.printStackTrace();}
    }
    @Override
    public void run() {
        ServerPrincipal serv = new ServerPrincipal();
        try {
            while (true) {
                String request = in.readLine();
                switch (request) {
                    case "L", "l" -> out.println(serv.receber());
                    case "R", "r" -> {
                        out.println("Indique a chave que pretende adicionar");
                        String chave = input.readLine();
                        if(serv.buscarChave(chave)){
                            out.println("Não é possivel adicionar chave pretendida pois ela ja existe");
                        }else{
                            out.println("Indique o valor que pretende aicionar");
                            String valor = input.readLine();
                            serv.adicionarHashMap(chave, valor);
                            out.println("A chave " + "|" + chave + "|" + " e o valor " + "|" + valor + "|" + " foram adicionados com sucesso.");
                        }
                    }
                    case "alterar" -> {
                        out.println("Indique a chave a qual voce pretende alterar o valor");
                        String alterchave = input.readLine();
                        if(serv.buscarChave(alterchave)) {
                            out.println("Indique o novo valor");
                            String altervalor = input.readLine();
                            serv.replaceValue(alterchave, altervalor);
                            out.println("O valor da chave " + "|" + alterchave + "|" + " foi alterado para " + "|" + altervalor + "|");
                        }else{
                            out.println("A chave a qual voce pretende alterar o valor não existe");
                        }
                    }
                    case "C", "c" -> {
                        out.println("Introduza a chave do valor que pretende receber.");
                        String cchave = input.readLine();
                        if(serv.buscarChave(cchave)) {
                            out.println("O valor: " + serv.buscaValor(cchave));
                        }else{
                            out.println("A chave indicada é inexistente");
                        }
                    }
                    case "D", "d" -> {
                        out.println("Indique a chave do valor ao qual pretende eliminar.");
                        String elim = input.readLine();
                        if(serv.buscarChave(elim)) {
                            serv.eliminarCV(elim);
                            out.println("Item removido com sucesso.");
                        }else{
                            out.println("A chave que voce indicou é inexistente");
                        }
                    }
                    case "Q", "q" -> {out.print(request);in.close();out.close();input.close(); System.exit(1);}
                    default -> out.println("O caracter " + request + " é invalido");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
