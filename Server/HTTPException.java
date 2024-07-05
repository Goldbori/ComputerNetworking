package Server;

public class HTTPException extends Exception{
    
    @Override
    public void printStackTrace() {
        // TODO Auto-generated method stub
        System.out.println("HTTP request 오류");
    }
}
