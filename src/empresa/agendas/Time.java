package empresa.agendas;

public class Time {

    private static int HOUR = 0;
    private static int DAY = 1;
    private static int WEEK = 0;

    public int getHOUR() {
        return HOUR;
    }

    public void setHOUR(int HOUR) {
        Time.HOUR += HOUR;
        Time.HOUR %= 24;
        if(Time.HOUR == 0) DAY++;
        DAY %= 365;
        WEEK = DAY/7;

        System.out.println("Hora(s) passada(s) com sucesso.");
    }

    public void setDAY(int DAY) {
        Time.DAY += DAY;
        DAY %= 365;
        WEEK = DAY/7;

        System.out.println("Dia(s) passado(s) com sucesso.");
    }

    public int getDAY() {
        return DAY;
    }

    public int getWEEK() {
        return WEEK;
    }

    public void setWEEK(int WEEK) {
        Time.WEEK += WEEK;

        System.out.println("Semana(s) passadas com sucesso.");
    }
}
