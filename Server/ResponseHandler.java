package Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import Server.HTTPException;

public class ResponseHandler {
    String method;
    String path;
    BufferedWriter bw;
    HashMap<String, String> headerMap;
    
    ResponseHandler(){}
    ResponseHandler(int code, BufferedWriter bw) throws IOException{
        //this.method = method;
        //this.path = path;
        this.bw = bw;
        ///this.headerMap = headerMap;
        sendTextResponse(bw, code, "");
    }
    ResponseHandler(String method, String path, BufferedWriter bw, HashMap<String,String> headerMap, StringBuilder body)throws IOException,HTTPException{

        this.method = method;
        this.path = path;
        this.bw = bw;
        this.headerMap = headerMap;
        try{
            methodHandler(method, body);
        }catch(HTTPException e){        
            sendTextResponse(bw, e.status, "");
        }

    }

    String methodHandler(String method, StringBuilder body) throws IOException, HTTPException{
        String responseString = "";
        switch (method) { // 메서드에 따른 처리
            case "HEAD":
                headHandler(path);
                break;
            case "GET":

                getHandler(path);
                break;
            case "POST":
                postHandler(path, body);
                break;
            case "DELETE":
                deleteHandler(path);
                break;
            case "PUT":
                putHandler(path);
                break;
            default:
                break;
        }

        return responseString;
    }

    void getHandler(String path)throws IOException, HTTPException{

        if (path == null) {
            //sendTextResponse(this.bw, 404,"");
            throw new HTTPException(404);
        }
        if (path.equals("/errorpath")){
            throw new HTTPException(404);
        }
        sendTextResponse(this.bw, 200, "Hello, World!");
        
    }

    void putHandler(String path)throws IOException, HTTPException{

        if (path == null) {
            //sendTextResponse(this.bw, 404,"");
            throw new HTTPException(404);
        }
        if (path.equals("/errorpath")){
            throw new HTTPException(404);
        }
        sendTextResponse(this.bw, 200, "PUT Response");
        
    }

    void headHandler(String path) throws IOException, HTTPException{
        if (path == null) {
            sendTextResponse(this.bw, 404, "");
            throw new HTTPException(404);
        }
        sendHeadResponse(this.bw, 200); //헤더만 보냄 + 쿠키설정
    }

    void postHandler(String path, StringBuilder body) throws IOException, HTTPException{
        if (path == null) {
            sendTextResponse(this.bw, 404,"");
            throw new HTTPException(404);
        }
        String resbody = "";
        try{
            String id = headerMap.get("Cookie").split("=")[1];
            String fileName = "file_" + id + ".txt";
            Path filePath = Paths.get(fileName);
            // Write body to file
            Files.write(filePath, body.toString().getBytes(StandardCharsets.UTF_8));
            resbody += "File saved: " + filePath;
        }catch (Exception e){
            sendTextResponse(this.bw, 500, "");
            e.printStackTrace();
        }
        sendTextResponse(this.bw, 200, resbody);
    }

    void deleteHandler(String path) throws IOException, HTTPException{
        if (path == null) {
            sendTextResponse(this.bw, 404,"");
            throw new HTTPException(404);
        }
        String body = "";
        try{
            String id = headerMap.get("Cookie").split("=")[1];
            String fileName = "file_" + id + ".txt";
            Path filePath = Paths.get(fileName);
            Files.delete(filePath);
            body += "File Deleted: " + filePath;
        }catch (Exception e){
            e.printStackTrace();
        }
        sendTextResponse(this.bw, 200, body);
    }
    void sendHeadResponse(BufferedWriter bw, int status)throws IOException{
        HTTPException e = new HTTPException(status);
        String id = UUID.randomUUID().toString();
        String responseString = String.format(
                       
                        "HTTP/1.1 %d %s\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "Content-Length: 0\r\n" +
                        "Date: %s\r\n" +
                        "Server: HTTPServer\r\n" +
                        "Set-Cookie: client_id="+ id +"\r\n" +
                        "\r\n",
                status, e.statusMap.get(status), Calendar.getInstance().getTime().toString());
        
                bw.write(responseString);
                bw.flush();
    }
    void sendTextResponse(BufferedWriter bw, int status, String bodyString) throws IOException{
        HTTPException e = new HTTPException(status);
        String responseString = String.format(
                       
                        "HTTP/1.1 %d %s\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "Content-Length: %d\r\n" +
                        "Date: %s\r\n" +
                        "Server: HTTPServer\r\n" +
                        "\r\n" +
                        "%s",
                status, e.statusMap.get(status), bodyString.getBytes().length,Calendar.getInstance().getTime().toString(), bodyString);
        
                bw.write(responseString);
                bw.flush();

    }
}
