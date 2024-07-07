package Server;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import Server.HeaderParser;
import Server.ResponseHandler;
import Server.MethodParser;
public class HttpServer {
    
    public static void main(String[] args) throws IOException{
        ServerSocket server = new ServerSocket(80); //http통신을 위해 80번포트에서 서버소켓 대기
        
        System.out.println("Listening on port 80...");
        while (true){
            
            try(Socket client = server.accept()){
                

                new Thread(new ClientHandler(client)).start();  //각 요청을 받을 때 마다 새로운 스레드로 각각 처리가능
                
            }catch(SocketException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }

            
        }

       // server.close();
    }
}
