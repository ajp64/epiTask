package com.epi.worldData.util;

import com.opencsv.bean.AbstractBeanField;

/*
 Converter to handle cases where a non-Double is provided to a Double attribute
*/

public class DoubleConverter extends AbstractBeanField <Double, String> {
    @Override
    protected Double convert(String value) {
        if (value == null || value.trim().isEmpty())
            return null;

        try {
            return Double.valueOf(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
