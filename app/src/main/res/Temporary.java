/**
 * Created by Mirza 01/28/2019.
 */

public class Temporary
{
    int minutes = 123 / (60 * 1000);
    int seconds = (123 / 1000) % 60;
    String str = String.format("%d:%02d", minutes, seconds);
    System.out.println(str);
}
