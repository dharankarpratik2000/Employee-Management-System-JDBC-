package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class DateUtil 
{
	public static int getWorkingDays(int month, int year) {

        YearMonth yearMonth = YearMonth.of(year, month);

        int totalDays = yearMonth.lengthOfMonth();

        int workingDays = 0;

        for (int day = 1; day <= totalDays; day++) {

            LocalDate date = LocalDate.of(year, month, day);

            if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
        }

        return workingDays;
    }

}
