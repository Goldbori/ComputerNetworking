package 기본소켓통신;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


class Client{
    public static void main(String[] args) throws IOException{
        Socket socket = new Socket("127.0.0.1", 8000);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        String ouputMsg = "보냄";
        out.write(ouputMsg.getBytes());
        out.flush();

        byte[] inputData = new byte[100];
        int length = in.read(inputData);
        String inputMsg = new String(inputData, 0 , length);

        System.out.println(inputMsg);
        socket.close();

    }
}