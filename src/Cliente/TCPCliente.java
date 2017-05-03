/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author rodrigo
 */
public class TCPCliente {

    public static void main(String[] args) throws IOException {
        
        
        //Stream para leer los datos desde teclado
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        //Creacion de socket para cliente
        Socket socketCliente = new Socket("localhost", 9875);
        System.out.println("Conexion aceptada. " + socketCliente.toString());

        //Stream de envio de datos al servidor
        DataOutputStream alServidor = new DataOutputStream(socketCliente.getOutputStream());

        //Stream recepcion datos del servidor
        BufferedReader delServidor = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

        //Lectura teclado
        System.out.println("Introduzca su usuario");
        String mensaje = teclado.readLine();

        //Envio de cadena al servidor
        alServidor.writeBytes(mensaje + "\n");

        
        //Lectura teclado
        System.out.println("Introduzca su password");
        mensaje = teclado.readLine();

        //Envio de cadena al servidor
        alServidor.writeBytes(mensaje + "\n");
        
        //Recepcion mensaje del servidor
        String mensajeServidor = delServidor.readLine();

        System.out.println("Mensaje del servidor: " + mensajeServidor);

        socketCliente.close();
    }
}
