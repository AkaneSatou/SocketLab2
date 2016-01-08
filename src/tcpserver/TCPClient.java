/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.ProcessBuilder.Redirect.Type;
import java.net.*;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class TCPClient {
    
    public static void main(String args[]) throws Exception{
        //Variables
        String sentence;
        String fromServer;
        List<Todo> listTodo = new ArrayList<>();
        //Buffer para recibir desde el usuario
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        
        //Socket para el cliente (host, puerto)
        Socket clientSocket = new Socket("localhost", 5000);
        
        //Buffer para enviar el dato al server
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        
        //Buffer para recibir dato del servidor
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //System.out.println(inFromServer);
        //Leemos del cliente y lo mandamos al servidor
        sentence = inFromUser.readLine();
        
        outToServer.writeBytes(sentence + '\n');
       
        fromServer = inFromServer.readLine();
        //System.out.println(fromServer);
        Object objet = JSONValue.parse(fromServer);
        JSONArray jsonArray = (JSONArray) objet;
        try{
            for (Object jsonArray1 : jsonArray) {
                JSONObject object = (JSONObject) jsonArray1;
                Todo todo = new Todo();
                todo.cantidad = (List)object.get("cantidad");
                todo.palabras = (List)object.get("palabras");
                todo.id = (List)object.get("id");
                todo.titulo = (String)object.get("titulo");
                listTodo.add(todo);
            }

            for (Todo listTodo1 : listTodo) {
                int lugar = listTodo1.palabras.indexOf(sentence);
                System.out.println("Titulo: " +listTodo1.titulo);
                System.out.println("IDS encontrados: "+listTodo1.id);
                System.out.println("Palabra: "+listTodo1.palabras.get(lugar));
                System.out.println("Repetida: "+listTodo1.cantidad.get(lugar));
            }
        }catch(Exception e){
            System.out.println("Su palabra  NO existe o se encuntra demasiadas veces repetida como para poder procesarla.  lamentamos los inconvenientes :c ");
        }
        //Object objet = JSONValue.parse(fromServer);
        //System.out.println("Server response: " + fromServer);
        
        //Cerramos el socket
        clientSocket.close();
    }
    
}
