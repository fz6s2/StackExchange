package ru.inp.stackexchange.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Stack Exchange API general response object.Can be parsed from JSON. 
 * @param <T> defines the type of objects in the result list.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SEResponse <T>{
    @JsonProperty("items")
    private final List<T> items;
    
    private boolean isError;
    private SEError error;
    
    public SEResponse() {
        items = new ArrayList<>();
        isError = false;
    }
    
    public boolean isError() {
        return isError;
    }
    public SEResponse setError(SEError aError) {
        isError = true;
        error = aError;
        return this;
    }
    public SEError getError() {
        return error;
    }
    public List<T> getItems() {
        return items;
    }
}
