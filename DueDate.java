package personal;

import java.io.Serializable;

public class DueDate implements Comparable<DueDate>, Serializable {
    private String month;
    private int day;
    private int monthNum;
    private static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public DueDate(int month, int day) {
        this.month = MONTHS[month - 1];
        this.day = day;
        this.monthNum = month;
    }

    public void changeDate(int newMonth, int newDay) {
        this.month = MONTHS[newMonth - 1];
        this.day = newDay;
        this.monthNum = newMonth;
    }

    public void changeDate(int newDay) {
        day = newDay;
    }

    public int getMonthNum() { return monthNum; }

    public int getDay() { return day; }

    public String toString() { return month + " " + day; }

    @Override
    public int compareTo(DueDate other) {
        if (monthNum == other.getMonthNum()) {
            return day - other.getDay();
        }
        return monthNum - other.getMonthNum();
    }
}
