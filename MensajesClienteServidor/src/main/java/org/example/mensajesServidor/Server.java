package org.example.mensajesServidor;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public Server(ServerSocket serverSocket) {
        try{
            this.serverSocket=serverSocket;
            this.socket=serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        }catch (IOException e){
            System.out.println("Error creando servidor");
            closeEverything(socket,bufferedReader,bufferedWriter);
            e.printStackTrace();
        }

    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (bufferedWriter!=null){
                bufferedWriter.close();
            }
            if (socket!=null){
                socket.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void sendMessageToClient(String messageToSend) {
        try {
            bufferedWriter.write(messageToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }catch (IOException e){
            System.out.println("error al enviar mensaje");
            closeEverything(socket,bufferedReader,bufferedWriter);
            e.printStackTrace();
        }
    }

    public void reciveMessageFromClient(VBox vboxMessage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String messafeFromClient= bufferedReader.readLine();
                        HelloController.addLabel(messafeFromClient,vboxMessage);

                    }catch (IOException e){
                        System.out.println("error recivir mensaje");
                        closeEverything(socket,bufferedReader,bufferedWriter);
                        e.printStackTrace();
                        break;
                    }


                }
            }
        }).start();
    }
}
