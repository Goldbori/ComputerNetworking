package Server;

public class HTTPException extends Exception{
    int status;
    HTTPException(){}
    HTTPException(int status){
        this.status = status;
    }
    
    @Override
    public void printStackTrace() {
        // TODO Auto-generated method stub
        System.out.println("HTTP request 오류");
    }
}
