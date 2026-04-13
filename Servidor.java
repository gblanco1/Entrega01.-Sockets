import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(6500);
            System.out.println("Servidor esperando conexión...");

            // aceptar varios clientes uno tras otro
            while (true) {
                Socket socket = servidor.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                // Entrada de datos
                BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
                String mensaje = entrada.readLine();
                System.out.println("Cliente dice: " + mensaje);

                // Salida de datos
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                salida.println("Hola desde el servidor");

                socket.close();
            }

            // servidor.close(); // nunca se alcanza, el servidor corre indefinidamente

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}