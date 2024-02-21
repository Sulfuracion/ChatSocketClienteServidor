package org.example.mensajescliente;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket) {

        try {
            this.socket=socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error creando cliente");
            closeEverything(socket,bufferedReader,bufferedWriter);

            throw new RuntimeException(e);
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

    public void receiveMessageFromServer(VBox vboxMessage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String messafeFromServer= bufferedReader.readLine();
                        HelloController.addLabel(messafeFromServer,vboxMessage);

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

    public void sendMessageToServer(String messageToServer) {
        try {
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }catch (IOException e){
            System.out.println("error al enviar mensaje");
            closeEverything(socket,bufferedReader,bufferedWriter);
            e.printStackTrace();
        }
    }
}
