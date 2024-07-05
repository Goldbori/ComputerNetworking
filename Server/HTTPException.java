package Server;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HTTPException extends Exception {
    int status;
    String statusMsg;
    HashMap<Integer, String> statusMap;

    HTTPException() {
    }

    HTTPException(int status) throws IOException {
        this.status = status;
        statusMap = new HashMap<>();
        this.readStatus();
        this.statusMsg = statusMap.get(status);
    }

    @Override
    public void printStackTrace() {
        // TODO Auto-generated method stub
        if (this.statusMsg == null){
            System.out.println("Unknown HTTP Exception");
        }
        else{
            System.out.println(this.statusMsg);
        }
        
    }

    void readStatus() throws IOException {
        File status = new File("C:\\Users\\SUN\\Desktop\\ComputerNetworking\\Server\\src\\StatusCode.txt");

        BufferedReader br = new BufferedReader(new FileReader(status));
        String line;
        while ((line = br.readLine()) != null) {
            String[] stat = line.split(" : ");
            this.statusMap.put(Integer.parseInt(stat[0]), stat[1]);

        }
        br.close();
    }
}
