
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class TCPsocket {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 80);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String ostr = "GET / HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "User-Agent: CustomClient/1.0\r\n" +
                "\r\n";
        ;
        bw.append(ostr);
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
