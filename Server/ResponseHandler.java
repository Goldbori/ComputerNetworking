package Server;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseHandler {
    String method;
    String path;
    BufferedWriter bw;
    
    ResponseHandler(){}
    ResponseHandler(String method, String path, BufferedWriter bw)throws IOException{

        this.method = method;
        this.path = path;
        this.bw = bw;
        methodHandler(this.method);

    }

    String methodHandler(String method) throws IOException{
        String responseString = "";
        switch (method) { // 메서드에 따른 처리
            case "HEAD":
                headHandler(path);
                break;
            case "GET":

                getHandler(path);
                break;
            case "POST":
                postHandler(path);
                break;
            case "DELETE":
                deleteHandler(path);
                break;

            default:
                break;
        }

        return responseString;
    }

    void getHandler(String path) {

        switch(path){

            case "/":

        }
    }

    void headHandler(String path) throws IOException{
        sendTextResponse(this.bw, 200, "");
    }

    void postHandler(String path){

    }

    void deleteHandler(String path){

    }

    void sendTextResponse(BufferedWriter bw, int status, String bodyString) throws IOException{
        HTTPException e = new HTTPException(status);
        String responseString = String.format(
                        "HTTP/1.1 %d %s\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "Content-Length: %d\r\n" +
                        "\r\n" +
                        "%s",
                status, e.statusMap.get(status), bodyString.length(), bodyString);
        
                bw.write(responseString);
                bw.flush();

    }
}
