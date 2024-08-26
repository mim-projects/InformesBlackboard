import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static void main(String[] args) {
        String filename = "20241_POS_GRUPOS (02-27-15).txt";
        String regex = "[0-9]{5}_[A-Za-z]+_[A-Za-z]+ \\([0-9]{2}-[0-9]{2}-[0-9]{2}\\).(txt|csv|tsv)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(filename);
        System.out.println("-----------");
        System.out.println(regex);
        System.out.println(filename);
        System.out.println(matcher.matches());
    }
}
