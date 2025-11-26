package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Pattern VALID_PATTERN = Pattern.compile("^[ATCG]+$");

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length == 0)
            return false;

        int n = dna.length;
        for (String row : dna) {
            if (row == null || row.length() != n)
                return false; // Must be NxN
            if (!VALID_PATTERN.matcher(row).matches())
                return false; // Only ATCG
        }
        return true;
    }
}
