package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;

public class HttpServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(80)) { //http통신을 위해 80번포트에서 서버소켓 대기
            System.out.println("Listening on port 80...");
            while (true) {
                try {
                    Socket client = server.accept();
                    System.out.println("Connected to client " + client.getInetAddress());
                    client.setSoTimeout(10000);  // 10초로 타임아웃 설정

                    new Thread(new ClientHandler(client)).start();  // 각 요청을 받을 때마다 새로운 스레드로 각각 처리 가능
                } catch (SocketException e) {
                    System.err.println("SocketException: " + e.getMessage());
                } catch (IOException e) {
                    System.err.println("IOException: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("ServerSocket initialization error: " + e.getMessage());
        }
    }
}
