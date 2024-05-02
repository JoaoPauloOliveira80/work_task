package APPLICATION.connection;
import java.net.Socket;
import java.io.IOException;

public class ServerChecker {

    // Método para verificar se o servidor está ligado
    public static boolean isServerRunning(String serverAddress, int port) {
        try {
            // Tenta criar um socket para se conectar ao servidor
            Socket socket = new Socket(serverAddress, port);
            
            // Se a conexão for estabelecida com sucesso, o servidor está ligado
            socket.close();
            return true;
        } catch (IOException e) {
            // Se houver uma exceção ao tentar se conectar, o servidor não está ligado
            return false;
        }
    }

    // Método main para exemplo de uso
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Endereço do servidor
        int port = 8080; // Porta do servidor
        
        // Verifica se o servidor está ligado
        if (isServerRunning(serverAddress, port)) {
            System.out.println("O servidor está ligado.");
        } else {
            System.out.println("O servidor não está ligado.");
        }
    }
}
