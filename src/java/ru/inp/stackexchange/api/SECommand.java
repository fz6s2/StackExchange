package ru.inp.stackexchange.api;

/**
 * Stack Exchange API comand enumeration
*/
public enum SECommand {
    SEARCH("search");
    /*
    ANSWERS, EVENTS, TAGS...
    */
    private final String cmdCode;
    
    private SECommand(String aCmdCode){
        cmdCode = aCmdCode;
    }
    
    public String getCode(){
        return cmdCode;
    }
    
    public static SECommand ofString(String code){
        for(SECommand c: SECommand.values()) {
            if(c.name().toLowerCase().equals(code.toLowerCase())) {
                return c;
            }
        }    
        return null;
    }
}
