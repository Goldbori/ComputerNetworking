package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import Server.HTTPException;

public class ClientHeaderParser {
    public void HeaderHandler(BufferedReader br, String[] MPV, HashMap<String,String> headerMap)throws IOException, HTTPException{
        try{
            String line = br.readLine();  //request 첫줄 : 메서드' '경로' 'http버전
                System.out.println("--------------------------------");
                System.out.println("Response From Server");
                System.out.println(line);
                
                if (line == null){
                    //이러면 클라이언트 오류이므로 400반환
                }
                String[] firstData = line.split(" ");

                MPV[0] = firstData[0];  //HTTP 버전
                MPV[1] = firstData[1];  //Status Code
                MPV[2] = firstData[2];  //status Code 설명
                
                while ((line = br.readLine()) != null){//line에 다음줄 대입 및 null값 처리

                    if (line.isEmpty())break;   //앞으로 빈라인이 들어오면 EOL
                    System.out.println(line);   //응답 출력
                    String[] headerData = line.split(": ");
                    headerMap.put(headerData[0], headerData[1]);
                    
                }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
                
    }
}
