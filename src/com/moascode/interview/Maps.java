package com.moascode.interview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Maps {
    public static void main(String[] args) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date validFrom = dateFormat.parse("21-12-2022 12:00:00");
        Date validTo = dateFormat.parse("22-12-2022 12:00:00");

        Date validFromParam = dateFormat.parse("20-12-2022 12:00:00");
        Date validToParam = dateFormat.parse("20-12-2022 12:00:00");

        System.out.println("validTo: " + validTo);
        System.out.println("validFrom: " + validFrom);
        System.out.println("\nvalidFrom is after validToParam :" + validFrom.after(validToParam));
        System.out.println("validTo is before validFromParam :" + validTo.before(validFromParam));

        System.out.println("\nvalidFrom is not after validToParam :" + !validFrom.after(validToParam));
        System.out.println("validTo is not before validFromParam :" + !validTo.before(validFromParam));
    }
}
