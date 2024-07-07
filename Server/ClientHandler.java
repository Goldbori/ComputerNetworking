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
    
    void receiveHTTPRequest(BufferedReader br, String[] MPV, HashMap<String, String> headerMap, StringBuilder sb) throws IOException, HTTPException {
        headerMap.clear(); // 헤더 초기화
        hP.HeaderHandler(br, MPV, headerMap);
        if (headerMap.containsKey("Content-Length")) { // Body가 있는 경우
            bP.BodyHandler(br, sb, Integer.parseInt(headerMap.get("Content-Length")));  // Body 처리 완료
        }
    }
    void sendHTTPResponse(BufferedWriter bw, String method, String path, HashMap<String,String> headerMap, StringBuilder sb) throws IOException, HTTPException{
        new ResponseHandler(method, path, bw, headerMap, sb);
        sb.setLength(0);    
    }
    void sendExceptionResponse(BufferedWriter bw, int code) throws IOException{
        new ResponseHandler(code, bw);
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
            while (client.isConnected() && !client.isClosed()){
                try{
                    receiveHTTPRequest(br, methodPathVersion, headerMap, sb);
    
                }catch(HTTPException e){
                    sendExceptionResponse(bw, e.status);
                    return;
                }
                sendHTTPResponse(bw, methodPathVersion[0], methodPathVersion[1], headerMap, sb);
            }

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
