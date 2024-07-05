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
        HeaderParser hP = new HeaderParser();
        
        System.out.println("Listening on port...");
        while (true){
            try (Socket client = server.accept()){//serverSocket에서 다른 소켓과 연결된 그 소켓을 client에 할당

                System.out.println("Socket connected from" + client.getInetAddress());  //연결된 소켓의 IP adress 출력

                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream())); //해당소켓과의 입출력 스트림 연결
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                
                HashMap<String,String> headerMap = new HashMap<>(); //헤더 종류: 헤더값 저장
                String[] methodPathVersion = new String[3]; // 0: 메서드, 1: 경로, 2: HTTP버전

                
                hP.HeaderHandler(br,methodPathVersion,headerMap);   //헤더 처리 완료

                new ResponseHandler(methodPathVersion[0], methodPathVersion[1], bw);




            }catch(HTTPException e){
                e.printStackTrace();
                break;
            }
            catch (SocketException e){
                break;
            }catch(IOException e){
                e.printStackTrace();
                continue;
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }

            
        }

        server.close();
    }
}
