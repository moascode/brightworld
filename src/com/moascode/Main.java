package com.moascode;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.parse("2019-09-08T03:00:20.000");
        System.out.println(localDateTime);
        LocalDate date = localDateTime.toLocalDate();
        System.out.println(date);
    }

}
