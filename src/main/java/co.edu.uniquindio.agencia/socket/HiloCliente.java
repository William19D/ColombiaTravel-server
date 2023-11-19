package co.edu.uniquindio.agencia.socket;

import co.edu.uniquindio.agencia.model.AgenciaViajes;
import co.edu.uniquindio.agencia.model.Cliente;
import lombok.extern.java.Log;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
@Log
public class HiloCliente implements Runnable {
    private final Socket socket;
    private final AgenciaViajes agencia;

    public HiloCliente(Socket socket, AgenciaViajes agencia) {
        this.socket = socket;
        this.agencia = agencia;
    }

    @Override
    public void run() {
        try {
            //Se crean flujos de datos de entrada y salida para comunicarse a través del socket
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //Se lee el mensaje enviado por el cliente
            Mensaje mensaje = (Mensaje) in.readObject();
            //Se captura el tipo de mensaje
            String tipo = mensaje.getTipo();
            //Se captura el contenido del mensaje
            Object contenido = mensaje.getContenido();
            //Según el tipo de mensaje se invoca el método correspondiente
            switch (tipo) {
                case "agregarCliente":
                    agregarCliente((Cliente) contenido, out);
                    break;
                case "listarClientes":
                    //listarClientes(out);

                    break;
            }
//Se cierra la conexión del socket para liberar los recursos asociados
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void agregarCliente(Cliente cliente, ObjectOutputStream out) throws IOException {
        try {
            agencia.agregarCliente(cliente);
            out.writeObject("Cliente agregado correctamente");
        } catch (Exception e) {
            out.writeObject(e.getMessage());
        }
    }

    public void listarClientes(ObjectOutputStream out) throws IOException {
        out.writeObject(agencia.listarClientes());

    }
}
