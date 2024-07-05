package Server;

import java.io.BufferedWriter;

public class ResponseHandler {

    String methodHandler(String method) {
        String responseString = "";
        switch (method) { // 메서드에 따른 처리
            case "HEAD":

                break;
            case "GET":

                String returnval = new ResponseHandler().getHandler();
                break;
            case "POST":

                break;
            case "DELETE":

                break;

            default:
                break;
        }

        return responseString;
    }

    String getHandler() {

        String returnMsg = "hello";

        return returnMsg;

    }

    void sendResponse(BufferedWriter bw, int status, String statusTxt, String bodyString) {
        String responseString = String.format(
                "HTTP/1.1 %d %s\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "Content-Length: %d\r\n" +
                        "\r\n" +
                        "%s",
                status, statusTxt, bodyString.length(), bodyString);
    }
}
