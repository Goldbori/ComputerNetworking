package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    Socket client;
    HeaderParser hP;
    BodyParser bP;

    ClientHandler(Socket client) {
        this.client = client;
        this.hP = new HeaderParser();
        this.bP = new BodyParser();
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
             
            System.out.println("Socket connected from " + client.getInetAddress()); // 연결된 소켓의 IP address 출력
            //System.out.println("Socket closed: " + client.isClosed());
            //System.out.println("Socket connected: " + client.isConnected());

            HashMap<String, String> headerMap = new HashMap<>(); // 헤더 종류: 헤더값 저장
            String[] methodPathVersion = new String[3]; // 0: 메서드, 1: 경로, 2: HTTP 버전
            StringBuilder sb = new StringBuilder(); // Body 저장할 StringBuilder

            hP.HeaderHandler(br, methodPathVersion, headerMap); // 헤더 처리 완료
            if (headerMap.containsKey("Content-Length")) { // Body가 있는 경우
                bP.BodyHandler(br, sb, Integer.parseInt(headerMap.get("Content-Length")));  // Body 처리 완료
            }
            new ResponseHandler(methodPathVersion[0], methodPathVersion[1], bw); // Response 처리 후 전송
            headerMap.clear(); // headerMap 초기화

            hP.HeaderHandler(br, methodPathVersion, headerMap); // 헤더 처리 완료
            if (headerMap.containsKey("Content-Length")) { // Body가 있는 경우
                bP.BodyHandler(br, sb, Integer.parseInt(headerMap.get("Content-Length")));  // Body 처리 완료
            }
            new ResponseHandler(methodPathVersion[0], methodPathVersion[1], bw); // Response 처리 후 전송
        } catch (HTTPException e) {
            System.err.println("HTTPException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        } finally {
            try {
                client.close();
                System.out.println("--------------------------------");
                System.out.println("Client disconnected from " + client.getInetAddress());
            } catch (IOException e) {
                System.err.println("Failed to close client socket: " + e.getMessage());
            }
        }
    }
}
