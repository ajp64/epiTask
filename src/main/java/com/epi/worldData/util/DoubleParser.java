package com.epi.worldData.util;

import com.opencsv.bean.AbstractBeanField;

/*
 Parser to handle cases where a non-Double is provided to a Double attribute, or a value of 0 or negative
 is provided.
*/

public class DoubleParser extends AbstractBeanField <Double, String> {
    @Override
    protected Double convert(String value) {
        if (value == null || value.trim().isEmpty())
            return null;

        try {
            final Double doubleValue = Double.valueOf(value.trim());
            if (isEqualOrLessThan0(doubleValue))
                return null;

            return Double.valueOf(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isEqualOrLessThan0(Double value){
        return value <= 0;
    }
}
