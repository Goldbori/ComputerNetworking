package Server;

import java.io.BufferedReader;
import java.io.IOException;
public class BodyParser {
    
    public String BodyHandler(BufferedReader br) throws IOException{
        String line;
        String body = "";
        while ((line = br.readLine()) != null){//line에 다음줄 대입 및 null값 처리

            if (line.isEmpty())break;   //앞으로 빈라인이 들어오면 EOL
            System.out.println(line);   //요청본문 출력
            body += line;
            
        }
        return body;
    }
}
