/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.io.*;
import java.net.*;


public class TCPServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        //Variables
        
        MongoClient mongo;
        DB db = null;
        DBCollection coll = null;
        
        
        String fromClient;
        String processedData;    
        
        
        mongo = new MongoClient("localhost",27017);
        System.out.println("DBMS Mongo Conectado");
        db = mongo.getDB( "prueba" ); //nombre de la bd usada
        coll = db.getCollection("pagina");
        
        
        //Socket para el servidor en el puerto 5000
        ServerSocket acceptSocket = new ServerSocket(5000);
        System.out.println("Server is running...\n");
        
        while(true){
            //Socket listo para recibir 
            Socket connectionSocket = acceptSocket.accept();
            //Buffer para recibir desde el cliente
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            //Buffer para enviar al cliente
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            
            //Recibimos el dato del cliente y lo mostramos en el server
            fromClient =inFromClient.readLine();
            System.out.println("Received: " + fromClient);
            
            BasicDBObject searchQuery2 = new BasicDBObject().append("palabras", fromClient);
            System.out.println(fromClient);
            DBCursor cursor2 = coll.find(searchQuery2);
            
            String json = JSON.serialize(cursor2);
            json += '\n';

            //Se procesa el dato recibido
            //processedData = fromClient.toUpperCase() + '\n';
            String reverse = new StringBuffer(fromClient).reverse().toString() + '\n';
            
            
            //Se le envia al cliente
             System.out.println(json);
             System.out.println(json.length());
            outToClient.writeBytes(json);
            
            //outToClient.writeBytes(as.toString());

        }
        
        
    }
    
}
