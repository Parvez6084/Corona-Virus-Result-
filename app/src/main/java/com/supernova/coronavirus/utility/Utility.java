package com.supernova.coronavirus.utility;

import com.supernova.coronavirus.model.CountryModel;
import java.util.List;

public class Utility {

    public static CountryModel searchItem(List<CountryModel> countries, String name) {

        for (CountryModel c : countries) {

            if (c.getCountry().trim().toLowerCase().equals(name.trim().toLowerCase())) {
                return c;
            }
        }

        return null;
    }
}
