import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java Cliente <puerto> <palabra_clave_cliente>");
            return;
        }

        int puerto;
        try {
            puerto = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Puerto invalido: " + args[0]);
            return;
        }

        String clientKeyword = args[1];

        System.out.println("PORT_SERVIDOR: " + puerto);
        System.out.println("PARAULA_CLAU_CLIENT: \"" + clientKeyword + "\"");
        System.out.println("Client chat to port " + puerto);

        try (Socket socket = new Socket("127.0.0.1", puerto);
             BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Inicializing client... OK");

            socketOut.println(clientKeyword);
            String serverKeyword = socketIn.readLine();
            if (serverKeyword == null) {
                System.out.println("No se recibio la palabra clave del servidor.");
                return;
            }

            System.out.println("Inicializing chat... OK");

            while (true) {
                System.out.print("#Enviar al servidor: ");
                String mensaje = console.readLine();
                if (mensaje == null) {
                    System.out.println("No se pudo leer del teclado.");
                    break;
                }

                socketOut.println(mensaje);
                if (mensaje.equals(clientKeyword) || mensaje.equals(serverKeyword)) {
                    System.out.println("Client keyword detected!");
                    break;
                }

                String respuesta = socketIn.readLine();
                if (respuesta == null) {
                    System.out.println("El servidor cerro la conexion.");
                    break;
                }

                System.out.println("#Rebut del servidor: " + respuesta);
                if (respuesta.equals(clientKeyword) || respuesta.equals(serverKeyword)) {
                    System.out.println("Server keyword detected!");
                    break;
                }
            }

            System.out.println("Closing chat... OK");
            System.out.println("Closing client... OK");
            System.out.println("Bye!");
        } catch (IOException e) {
            System.out.println("Error en el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}