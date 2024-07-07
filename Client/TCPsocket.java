package Client;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class TCPsocket {

    //와이어샤크 캡처 성공
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("172.30.1.51", 80);
        String cookie;
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String ostr = "HEAD / HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "User-Agent: HTTPClient/1.0\r\n" +
                "\r\n";
        
        bw.write(ostr);
        bw.flush();

        while (true) {
            String line = br.readLine();

            if (line == null) {
                continue;
            } else {
                System.out.println(line);
                break;
            }
        }
        // System.out.println(br.readLine());
        socket.close();
    }
}
