package Cliente;

import java.io.*; 
import java.net.*; 
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

class TCPServidorMultiple extends Thread { 

  public Vector<BufferedReader> readers;
  public Vector<Socket> clientes;
  public HashMap<String,String> usuarios;
  public Socket connectionSocket;
  public  Vector<PrintWriter> writers;
  private boolean ready;  
  
  public TCPServidorMultiple(){
       ready = false;
      clientes = new Vector<Socket>();
      readers = new Vector<BufferedReader>();
      writers = new Vector<PrintWriter>();
      usuarios = new HashMap();
  }
  
  public void add(Socket connectionSocket) throws IOException{
	    ready = false;
	    clientes.add(connectionSocket);
      	BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);
	    readers.add(inFromClient);
            writers.add(outToClient);
            ready = true;
  }
  
  public void run(){
	  
	  String clientSentence; 
	  while(clientes!=null)
	  {
		
		  for(int i =0; i < clientes.size(); ++i)
		  {
			  if(ready) // una variable para controlar concurrencia. Mejor usar locks si multiples hebras usan add(), lo que no es el caso
			  {
			  BufferedReader reader = readers.get(i);
			  PrintWriter writer = writers.get(i);
			  
			try {
                                
				 if(reader.ready()) // revisa si hay datos nuevos en el buffer de lectura del socket i-emo
				 {
                                     
				   clientSentence = reader.readLine();
                                   String nombre = clientSentence;
                                   clientSentence = reader.readLine();
                                   String pass = clientSentence;
                                
                                   if(usuarios.containsKey(nombre)){
                                       
                                       writer.println(clientes.size()); 
                                       
                                   }else{
                                       usuarios.put(nombre, pass);
                                       writer.println("Usuario creado");
                                   }
                         
                                   Iterator  it = usuarios.entrySet().iterator();
                                   while(it.hasNext()){
                                       Map.Entry e=(Map.Entry)it.next();
                                       System.out.println(e.getKey() +" "+e.getValue() );
                                   }
                                   //for(int j=0; j<usuarios.size(); j++){
                                     //  System.out.println(usuarios.get(j).toString());
                                   
                                   //}
                                   writer.flush();
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			} //if
		  }//for
	  }//while
	  
  }
	
	
  public static void main(String argv[]) throws Exception 
    { 
      
      ServerSocket welcomeSocket = new ServerSocket(9875);
      
      boolean run = true;
      TCPServidorMultiple server = new TCPServidorMultiple();
      server.start(); // hebra que atiende conexiones aceptadas     
      
      while(run) {  // hebra principal escucha intentos de conexion
    	  Socket connectionSocket = welcomeSocket.accept(); 
    	  System.out.println("Agregando nuevo cliente");
    	  server.add(connectionSocket);
	 }
      welcomeSocket.close();
     }
  } 

