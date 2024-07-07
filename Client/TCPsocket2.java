package Client;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import Server.BodyParser;
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

public class TCPsocket2 {

        // 와이어샤크 캡처 성공
        public static void main(String[] args) throws IOException, InterruptedException, HTTPException {
                Socket socket = new Socket("127.0.0.1", 80);
                HashMap<String, String> cookie = new HashMap<>(); // 클라이언트에 저장할 쿠키(본 예시에서는 클라이언트 id를 저장할 것)

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); // io 스트림 생성 ->
                                                                                                        // 연결
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                HashMap<String, String> headerMap = new HashMap<>(); // 헤더 종류: 헤더값 저장
                String[] methodPathVersion = new String[3]; // 0: HTTP버전, 1: Status Code, 2: Status Code 설명

                String head_request = String.format("HEAD / HTTP/1.1\r\n" + // 첫번째 request HEAD 메소드(헤더정보만 요청할 때 사용)
                                "Host: %s\r\n" +
                                "User-Agent: HTTPClient/1.0\r\n" +
                                "\r\n", socket.getInetAddress() + ":" + socket.getPort());

                // 첫번째 요청 전송 및 응답
                sendHTTPRequest(bw, head_request);
                receiveHTTPResponse(br, methodPathVersion, headerMap, cookie);

                String reqBody = "Hello! Server!";
                String post_request = String.format("POST / HTTP/1.1\r\n" + // 세번째 request POST 메소드
                                "Host: %s\r\n" +
                                "User-Agent: HTTPClient/1.0\r\n" +
                                "Content-Length: %d\r\n" +
                                "Cookie: %s\r\n" + // 쿠키 전송
                                "\r\n" +
                                "%s", socket.getInetAddress() + ":" + socket.getPort(), reqBody.getBytes().length,
                                "client_id=" + cookie.get("client_id"), reqBody);
                String get_request = String.format("GET / HTTP/1.1\r\n" + // 두번째 request GET 메소드
                                "Host: %s\r\n" +
                                "User-Agent: HTTPClient/1.0\r\n" +
                                "\r\n", socket.getInetAddress() + ":" + socket.getPort());

                String delete_request = String.format("DELETE / HTTP/1.1\r\n" + // 네번째 request DELETE 메소드
                                "Host: %s\r\n" +
                                "User-Agent: HTTPClient/1.0\r\n" +
                                "Cookie: %s\r\n" + // 쿠키 전송
                                "\r\n" , socket.getInetAddress() + ":" + socket.getPort(),
                                "client_id=" + cookie.get("client_id"));

                String request_404 = String.format("GET /errorpath HTTP/1.1\r\n" + // 404 에러 발생시키는 request
                                "Host: %s\r\n" +
                                "User-Agent: HTTPClient/1.0\r\n" +
                                "\r\n", socket.getInetAddress() + ":" + socket.getPort());
                // 두번째 요청 전송 및 응답
                sendHTTPRequest(bw, get_request);
                receiveHTTPResponse(br, methodPathVersion, headerMap, cookie);

                // 세번째 요청 전송 및 응답
                sendHTTPRequest(bw, post_request);
                receiveHTTPResponse(br, methodPathVersion, headerMap, cookie);
                Thread.sleep(3000); // 3초 대기후 해당 파일 삭제요청 보냄
                // 네번째 요청 전송 및 응답
                sendHTTPRequest(bw, delete_request);
                receiveHTTPResponse(br, methodPathVersion, headerMap, cookie);
                
                // 404 에러 발생시키는 요청 전송 및 응답

                sendHTTPRequest(bw, request_404);
                receiveHTTPResponse(br, methodPathVersion, headerMap, cookie);

                // 505 에러 발생시키는 요청 전송 및 응답

                String request_505 = String.format("GET / HTTP/2\r\n" + // 505 에러 발생시키는 request
                                "Host: %s\r\n" +
                                "User-Agent: HTTPClient/1.0\r\n" +
                                "\r\n", socket.getInetAddress() + ":" + socket.getPort());
                sendHTTPRequest(bw, request_505);
                receiveHTTPResponse(br, methodPathVersion, headerMap, cookie);

        }

        static void sendHTTPRequest(BufferedWriter bw, String request) throws IOException {
                bw.write(request);
                bw.flush();
        }

        static void receiveHTTPResponse(BufferedReader br, String[] MPV, HashMap<String, String> headerMap,
                        HashMap<String, String> cookie) throws IOException, HTTPException {
                ClientHeaderParser hP = new ClientHeaderParser();
                BodyParser bP = new BodyParser();
                StringBuilder sb = new StringBuilder(); // Body 저장할 StringBuilder
                hP.HeaderHandler(br, MPV, headerMap);
                if (headerMap.containsKey("Content-Length")) { // Body가 있는 경우
                        bP.BodyHandler(br, sb, Integer.parseInt(headerMap.get("Content-Length"))); // Body 처리 완료
                }
                if (headerMap.containsKey("Set-Cookie")) { // 쿠키가 있는 경우
                        String[] cookieData = headerMap.get("Set-Cookie").split("=");
                        cookie.put(cookieData[0], cookieData[1]);
                        // System.out.println(cookie.get("client_id"));
                }
                System.out.println(sb.toString());
                headerMap.clear(); // headerMap 초기화
        }
}
