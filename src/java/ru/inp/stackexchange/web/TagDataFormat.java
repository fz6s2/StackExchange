package ru.inp.stackexchange.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author inp
 */
public class TagDataFormat {
    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) { 
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern)); 
    } 
    public static String formatLocalDate(LocalDateTime localDate, String pattern) { 
        return localDate.format(DateTimeFormatter.ofPattern(pattern)); 
    } 
}
