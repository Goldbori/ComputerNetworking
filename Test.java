import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Test {
    public static void main(String[] args) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("E, d M yyyy H:m:ss ");
        System.out.println(LocalDateTime.now().format(format));
        System.out.println(Calendar.getInstance().getTime().toString());
    }
}
