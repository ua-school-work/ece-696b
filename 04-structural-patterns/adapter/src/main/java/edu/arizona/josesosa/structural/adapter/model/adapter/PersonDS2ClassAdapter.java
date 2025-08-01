package edu.arizona.josesosa.structural.adapter.model.adapter;

import edu.arizona.josesosa.structural.adapter.model.IPerson;
import edu.arizona.josesosa.structural.adapter.model.remote.PersonDS2;

import java.util.Calendar;
import java.util.Date;

/**
 * Class Adapter for PersonDS2
 * This adapter extends PersonDS2 and implements IPerson interface
 */
public class PersonDS2ClassAdapter extends PersonDS2 implements IPerson {

    @Override
    public Double getAge() {
        Date bornDate = getBorn();
        if (bornDate == null) {
            return 0.0;
        }
        return calculateAgeFrom(bornDate);
    }

    @Override
    public Long getSalary() {
        return convertYearlyToMonthlySalary(getSalaryYear());
    }

    private Double calculateAgeFrom(Date bornDate) {
        Calendar born = toCalendar(bornDate);
        Calendar today = toCalendar(new Date());
        int age = today.get(Calendar.YEAR) - born.get(Calendar.YEAR);

        if (isBeforeBirthday(today, born)) {
            age--;
        }

        return (double) age;
    }

    private boolean isBeforeBirthday(Calendar today, Calendar born) {
        return today.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR);
    }

    private Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private Long convertYearlyToMonthlySalary(Long yearlySalary) {
        if (yearlySalary == null) {
            return 0L;
        }
        return yearlySalary / 12;
    }
}