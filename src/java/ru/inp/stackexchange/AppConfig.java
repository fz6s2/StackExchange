package ru.inp.stackexchange;

import java.util.ResourceBundle;

/**
 * Application general settings
 */
public class AppConfig {
    private final static ResourceBundle RESOURCE = ResourceBundle.getBundle("ru.inp.stackexchange.resources.config");
    
    public static String get(String key) {
        try{
            return RESOURCE.getString(key);
        } catch(Exception e) {
            return null;
        }    
    } 
    
    public static String get(String key, String defValue) {
        String value = get(key);
        return value == null ? defValue : value;
    }        
    
    public static int getInt(String key, int defValue) {
        String value = get(key);
        try{
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {}
        
        return defValue;
    }  
}        
