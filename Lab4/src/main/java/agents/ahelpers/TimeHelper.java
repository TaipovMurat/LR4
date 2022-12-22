package agents.ahelpers;

public class TimeHelper {

    private static long initialTime = System.currentTimeMillis();

    //Продолжительность часа в мс
    public static int hourDurationMs = 2000;

    //Текущий час
    public static int getCurrentHour() {
        return (int)(System.currentTimeMillis() - initialTime) / hourDurationMs %24;
    }

    //Мс до следующего часа
    public static int getMSBeforeNextHour() {
        return hourDurationMs - ((int)(System.currentTimeMillis() - initialTime)%hourDurationMs);
    }
}

