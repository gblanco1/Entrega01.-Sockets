import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 6500);

            // permitir que el usuario escriba un mensaje
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Escribe un mensaje para el servidor: ");
            String msg = console.readLine();

            // Salida de datos
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            salida.println(msg);

            // Entrada de datos
            BufferedReader entrada = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
            String respuesta = entrada.readLine();
            System.out.println("El servidor respondió: " + respuesta);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}