package ru.inp.stackexchange.api;

import ru.inp.stackexchange.AppConfig;

/**
 * Factory for SERequest objects.
 */
public class SERequestFactory {
    /**
     * Creating SERequest from application config. 
     * Some field values can be default (params stackexchange.*.default.*).
     * @param cfgCommandCode parameter code to define Stack Exchange API command 
     * @return SERequest object
     * @throws IllegalArgumentException 
     */
    public static SERequest createFromAppConfig(String cfgCommandCode) throws IllegalArgumentException{
        String cfgCommandValue = AppConfig.get(cfgCommandCode);
        SECommand comand = SECommand.ofString(cfgCommandValue);
        String cfgURL = AppConfig.get("stackexchange.url");
        
        SERequest seRequest = new SERequest(cfgURL, comand);
        
        // default field values
        String fieldCodeCfg = String.format("stackexchange.%s.fields", cfgCommandValue);
        try{
            String [] cfgFields = AppConfig.get(fieldCodeCfg).split(",");
            
            for(String fieldCode: cfgFields){
                String fieldValue = AppConfig.get("stackexchange.search.default." + fieldCode);
                if(fieldValue != null){
                    seRequest.set(fieldCode, fieldValue);
                }
            }
        } catch(Exception e) {
            throw new IllegalArgumentException("Config fields parsing error");
        }
        
        return seRequest;
    }
}
