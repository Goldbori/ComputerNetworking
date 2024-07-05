package 기본소켓통신;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


class Server{

    public static void main(String[] args)throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        Socket socket = serverSocket.accept();

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        byte[] inputData = new byte[100];
        int length = in.read(inputData);
        String inputMsg = new String(inputData,0,length);

        String outputMsg = "보냄";
        out.write(outputMsg.getBytes());
        out.flush();    

        System.out.println(inputMsg);
        socket.close();
        serverSocket.close();
    }
}