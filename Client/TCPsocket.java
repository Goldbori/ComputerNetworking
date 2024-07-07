package Client;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import Server.HTTPException;
import Server.HeaderParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class TCPsocket {

    // 와이어샤크 캡처 성공
    public static void main(String[] args) throws IOException, InterruptedException, HTTPException {
        Socket socket = new Socket("127.0.0.1", 80);
        HashMap<String, String> cookie = new HashMap<>(); // 클라이언트에 저장할 쿠키(본 예시에서는 클라이언트 id를 저장할 것)
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); // io 스트림 생성 -> 연결
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        HashMap<String, String> headerMap = new HashMap<>(); // 헤더 종류: 헤더값 저장
        String[] methodPathVersion = new String[3]; // 0: HTTP버전, 1: Status Code, 2: Status Code 설명
        
        String head_request = String.format("HEAD / HTTP/1.1\r\n" + // 첫번째 request HEAD 메소드(헤더정보만 요청할 때 사용)
                "Host: %s\r\n" +
                "User-Agent: HTTPClient/1.0\r\n" +
                "\r\n", socket.getInetAddress() + ":" + socket.getPort());
        String get_request = String.format("GET / HTTP/1.1\r\n" + // 두번째 request GET 메소드(헤더정보와 본문정보를 요청할 때 사용)
                "Host: %s\r\n" +
                "User-Agent: HTTPClient/1.0\r\n" +
                "\r\n", socket.getInetAddress() + ":" + socket.getPort());

        bw.write(head_request);
        bw.flush();

        ClientHeaderParser hP = new ClientHeaderParser();
        hP.HeaderHandler(br, methodPathVersion, headerMap);

        bw.write(get_request);
        bw.flush();

        hP.HeaderHandler(br, methodPathVersion, headerMap);

        socket.close();
    }
}
