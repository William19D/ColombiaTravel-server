package co.edu.uniquindio.agencia.app;
import co.edu.uniquindio.agencia.exceptions.AtributoVacioException;
import co.edu.uniquindio.agencia.exceptions.DestinoRepetidoException;
import co.edu.uniquindio.agencia.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agencia.exceptions.RutaInvalidaException;
import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.socket.HiloCliente;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class AplicacionServidor {
    public static void main(String[] args) throws AtributoVacioException, DestinoRepetidoException, RutaInvalidaException, InformacionRepetidaException {
        int puerto = 1234;
//Se crea la instancia de la clase principal que contiene toda la lógica del proyecto
        AgenciaViajes agenciaServidor = new AgenciaViajes();
//Se crea el ServerSocket en el puerto 1234
        try(ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Esperando conexión...");
            while (true) {
//Se obtiene la conexión del cliente
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado");
//Se crea un hilo para la conexión del cliente
                HiloCliente hilo = new HiloCliente(clienteSocket, agenciaServidor);
                new Thread(hilo).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}