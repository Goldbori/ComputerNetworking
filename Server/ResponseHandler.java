package Server;

public class ResponseHandler {

    String methodHandler(String method){
        String responseString="";
        switch (method) {         //메서드에 따른 처리
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
}
