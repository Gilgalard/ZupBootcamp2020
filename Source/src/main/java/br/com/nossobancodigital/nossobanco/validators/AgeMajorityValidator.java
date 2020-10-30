package br.com.nossobancodigital.nossobanco.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

public class AgeMajorityValidator implements ConstraintValidator<AgeMajority, Date> {
    private final static int MAJORITY_AGE = 18;

    @Override
    public void initialize(AgeMajority paramA) {
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }

        int requesterAge = getDiffYears(date, new java.util.Date());

        return requesterAge >= MAJORITY_AGE;
    }

    private static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}