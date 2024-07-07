package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

import Server.HeaderParser;

public class ClientHandler implements Runnable {
    Socket client;
    HeaderParser hP;

    ClientHandler(Socket client) {
        this.client = client;
        this.hP = new HeaderParser();

    }

    @Override
    public void run() {
        try {
            System.out.println("Socket connected from" + client.getInetAddress()); // 연결된 소켓의 IP adress 출력

            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream())); // 해당소켓과의 입출력 스트림 연결
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            HashMap<String, String> headerMap = new HashMap<>(); // 헤더 종류: 헤더값 저장
            String[] methodPathVersion = new String[3]; // 0: 메서드, 1: 경로, 2: HTTP버전

            hP.HeaderHandler(br, methodPathVersion, headerMap); // 헤더 처리 완료

            new ResponseHandler(methodPathVersion[0], methodPathVersion[1], bw); // Response 처리후 전송
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
