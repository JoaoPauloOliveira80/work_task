package APPLICATION.connection;
import java.net.InetSocketAddress;
import java.net.Socket;

public class VerificarServidor {

    public static void main(String[] args) {
        final String SERVIDOR = "localhost"; // Endere�o do servidor (pode ser um IP ou nome de dom�nio)
        final int PORTA = 8080; // Porta do servidor
        final int TIMEOUT_MS = 2000; // Tempo limite em milissegundos (2 segundos)

        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(SERVIDOR, PORTA), TIMEOUT_MS);
            System.out.println("O servidor est� ligado e aceitando conex�es!");
            socket.close(); // Fecha a conex�o

        } catch (Exception e) {
            System.err.println("Erro ao conectar ao servidor: " + e.getMessage());
        }
    }
}
