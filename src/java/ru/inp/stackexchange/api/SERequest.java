package ru.inp.stackexchange.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Request ojbect for generate valid Stack Exchange API URL. 
 * Should be built by using methods set* and build().
 * Can be created by the SERequestFactory, but field values will be default.
 */
public class SERequest {
    private final Map<String, String> fields;
    private final SECommand command;
    private final String apiURL;
    private String fullURL;
    
    public SERequest(String aApiURL, SECommand aCommand) {
        fields = new HashMap<>();
        command = aCommand;
        apiURL = aApiURL;
    }
    
    public SERequest set(String key, String value) {
        fields.put(key, value);
        return this;
    }
    
    public SERequest setOrder(String order) {
        return set("order", order);
    }
    
    public SERequest setSort(String sort) {
        return set("sort", sort);
    }
    
    public SERequest setIntitle(String intitle) {
        return set("intitle", intitle);
    }
    
    public SERequest setSite(String site) {
        return set("site", site);
    }
    
    public String getFullURL(){
        return fullURL;
    }
    
    public SERequest build() throws IllegalArgumentException{
        
        StringBuilder resultURL = new StringBuilder();
        
        if(apiURL == null) {
            throw new IllegalArgumentException("URL is null");
        }    
        if(command == null) {
            throw new IllegalArgumentException("Command is null");
        }    
        
        resultURL.append(apiURL);
        if(apiURL.charAt(apiURL.length() -1) != '/') {
            resultURL.append("/");
        }    

        resultURL.append(command.getCode());
        
        if(!fields.isEmpty()) {
            resultURL.append("?");
        }    
        
        for(Map.Entry<String, String> entry: fields.entrySet()){
            
            if(entry.getKey() == null) {
                throw new RuntimeException("get key null");
            }    
            if(entry.getValue() == null) {
                throw new RuntimeException("get value null " + entry.getKey());
            }    
            
            try{
                resultURL.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append("&");
            } catch(UnsupportedEncodingException e) {}
        }
        
        fullURL = resultURL.toString();
        if(fullURL.charAt(fullURL.length()-1) == '&') {
            fullURL = fullURL.substring(0, fullURL.length() -1);
        }    
        
        return this;
    }        
}
