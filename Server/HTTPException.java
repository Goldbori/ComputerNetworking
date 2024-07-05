package Server;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HTTPException extends Exception {
    int status;
    String statusMsg;
    HashMap<Integer, String> statusMap;

    HTTPException() {
    }

    HTTPException(int status) throws IOException {
        this.status = status;
        this.readStatus();
        this.statusMsg = statusMap.get(status);
    }

    @Override
    public void printStackTrace() {
        // TODO Auto-generated method stub
        System.out.println("HTTP request 오류");
    }

    void readStatus() throws IOException {
        File status = new File("./src/StatusCode.txt");

        BufferedReader br = new BufferedReader(new FileReader(status));
        String line;
        while ((line = br.readLine()) != null) {
            String[] stat = new String[2];
            this.statusMap.put(Integer.parseInt(stat[0]), stat[1]);

        }
    }
}
