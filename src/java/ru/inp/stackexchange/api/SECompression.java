package ru.inp.stackexchange.api;

/**
 * Stack Exchange API compression alg. enumeration
 */
public enum SECompression {
    GZIP("gzip"),
    DEFLATE("deflate"),
    NONE("*");

    private String httpHeaderCode;

    private SECompression(String aHeaderCode) {
        httpHeaderCode = aHeaderCode;
    } 
        
    public String getHttpHeaderCode() {
        return httpHeaderCode;
    }    
    
    public static SECompression ofString(String code) {
        for(SECompression c: SECompression.values())
            if(c.name().toLowerCase().equals(code.toLowerCase()))
                return c;
        return null;
    }
}
