package APPLICATION.connection;
import java.net.Socket;
import java.io.IOException;

public class ServerChecker {

    // M�todo para verificar se o servidor est� ligado
    public static boolean isServerRunning(String serverAddress, int port) {
        try {
            // Tenta criar um socket para se conectar ao servidor
            Socket socket = new Socket(serverAddress, port);
            
            // Se a conex�o for estabelecida com sucesso, o servidor est� ligado
            socket.close();
            return true;
        } catch (IOException e) {
            // Se houver uma exce��o ao tentar se conectar, o servidor n�o est� ligado
            return false;
        }
    }

    // M�todo main para exemplo de uso
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Endere�o do servidor
        int port = 8080; // Porta do servidor
        
        // Verifica se o servidor est� ligado
        if (isServerRunning(serverAddress, port)) {
            System.out.println("O servidor est� ligado.");
        } else {
            System.out.println("O servidor n�o est� ligado.");
        }
    }
}
