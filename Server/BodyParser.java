package Server;

import java.io.BufferedReader;
import java.io.IOException;

public class BodyParser {

    public void BodyHandler(BufferedReader br, StringBuilder sb, int len) throws IOException {
        char[] bodyChars = new char[len];
                int read = br.read(bodyChars, 0, len);
                sb.append(bodyChars, 0, read);
    }
}
