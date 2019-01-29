package ru.inp.stackexchange.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stack Exchange API response error.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SEError {
    @JsonProperty("error_id")
    private int errorId;
    @JsonProperty("error_name")
    private String errorName;
    @JsonProperty("error_message")
    private String errorMessage;
    
    public SEError() {};
    
    public SEError(int aErrorId, String aErrorName, String aErrorMessage) {
        errorId = aErrorId;
        errorName = aErrorName;
        errorMessage = aErrorMessage;
    }

    public void setErrorId(int aErrorId) {
        errorId = aErrorId;
    }

    public void setErrorName(String aErrorName) {
        errorName = aErrorName;
    }

    public void setErrorMessage(String aErrorMessage) {
        errorMessage = aErrorMessage;
    }
    
    public int getErrorId() {
        return errorId;
    }
    
    public String getErrorName() {
        return errorName;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    @Override
    public String toString() {
        return String.format("code: %d, name: %s, descriprion: %s", errorId, errorName, errorMessage);
    }
}