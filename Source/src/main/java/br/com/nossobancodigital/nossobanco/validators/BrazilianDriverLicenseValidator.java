package br.com.nossobancodigital.nossobanco.validators;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class BrazilianDriverLicenseValidator implements ConstraintValidator<BrazilianDriverLicense, String> {
    private static final String DRIVER_LICENSE_NO_REGEX = "[0-9]{11}";

    @Override
    public void initialize(BrazilianDriverLicense paramA) {
    }

    @Override
    public boolean isValid(String driverLicenseNo, ConstraintValidatorContext constraintValidatorContext) {
        final int[][] driverLicenseValidationDigits = {{9, 8, 7, 6, 5, 4, 3, 2, 1},	{1, 2, 3, 4, 5, 6, 7, 8, 9}};

        if (StringUtils.isEmpty(driverLicenseNo)) {
            return false;
        }

        // 1. Check only the format. But this is necessary for the digit conference algorithm.
        if (!Pattern.matches(DRIVER_LICENSE_NO_REGEX, driverLicenseNo)) {
            return false;
        }

        // 2. Now begins the conference by the digit.
        String licenseNo = driverLicenseNo.substring(0, 9);
        String licenseDigitNo = driverLicenseNo.substring(9);
        String conferenceDigit = "";

        int decrease = 0;
        for (int i = 1; i <= 2; i++) {
            int sum = 0;
            for (int j = 1; j <= 9; j++) {
                sum = sum + Integer.parseInt(licenseNo.substring(j-1, j)) * driverLicenseValidationDigits[i-1][j-1];
            }

            int digit = sum % 11;

            if (digit >= 10) {
                digit = 0;

                if (i == 1) {
                    decrease = 2;
                }
            }

            if (i == 2) {
                digit = digit - decrease;
            }

            conferenceDigit = conferenceDigit + Integer.toString(digit).substring(0, 1);
        }

        return licenseDigitNo.equals(conferenceDigit);
    }
}
