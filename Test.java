import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Test {
    public static void main(String[] args) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(", d M yyyy H:m:ss ");
        System.out.println(LocalDateTime.now().format(format));
        System.out.println(Calendar.getInstance().getTime().toString());
        Date today = new Date();
    }
}
