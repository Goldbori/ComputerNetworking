package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import Server.HTTPException;
public class HeaderParser {
    


    public void HeaderHandler(BufferedReader br, String[] MPV, HashMap<String,String> headerMap)throws IOException, HTTPException{
        try{
            String line = br.readLine();  //request 첫줄 : 메서드' '경로' 'http버전
                System.out.println("--------------------------------");
                System.out.println("Request From Client");
                System.out.println(line);
                
                if (line == null){
                   throw new HTTPException(400);
                }
                String[] firstData = line.split(" ");
                try{
                    MPV[0] = firstData[0];  //메서드
                    MPV[1] = firstData[1];  //경로
                    MPV[2] = firstData[2];  //HTTP버전
                }
                catch (ArrayIndexOutOfBoundsException e){
                    throw new HTTPException(400);
                }

                if (!MPV[2].equals("HTTP/1.1")) throw new HTTPException(505);
                //br.readLine();  //한줄 띄고
                //System.out.println(httpMethod);
                
                while ((line = br.readLine()) != null){//line에 다음줄 대입 및 null값 처리

                    if (line.isEmpty())break;   //앞으로 빈라인이 들어오면 EOL
                    System.out.println(line);   //요청 출력
                    String[] headerData = line.split(": ");
                    headerMap.put(headerData[0], headerData[1]);
                    
                }
                
        }
        catch (HTTPException e){
           throw e;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
                
    }
}
