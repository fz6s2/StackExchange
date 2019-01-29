package ru.inp.stackexchange.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inp.stackexchange.format.JSONParser;

/**
 * Stack Exchange API gateway client. Is used to send a request and read the response.
 * Should be built by using methods set* and build().
 * Can be fully created by the SEGatewayClientFactory.
 */
public class SEGatewayClient {
    //private final static Logger logger = LoggerFactory.getLogger("stackexchange");
    private final static Logger logger = LoggerFactory.getLogger(SEGatewayClient.class);
               
    private String encoding;
    private int timeoutConnect;
    private int timeoutRead;
    private Proxy proxy;
    private boolean useProxy;
    private SECompression compression;

    public SEGatewayClient(){
        timeoutConnect = 10000;
        timeoutRead = 10000;
        proxy = Proxy.NO_PROXY;
        compression = SECompression.NONE;
        encoding = "UTF-8";
    }

    public SEGatewayClient build() throws IllegalArgumentException{
        try{
            Charset.forName(encoding);  
        } catch(UnsupportedCharsetException e) {
            throw new IllegalArgumentException("Encoding initialization error");
        }
        
        timeoutConnect = timeoutConnect > 0 ? timeoutConnect : 0;
        timeoutRead = timeoutRead > 0 ? timeoutRead : 0; 
        
        if(compression == null) {
            throw new IllegalArgumentException("Compression initialization error");
        }
        if(useProxy && proxy == null) {
            throw new IllegalArgumentException("Proxy initialization error");
        }
        return this;
    }
    
    public SEGatewayClient setEncoding(String aEncoding) {
        encoding = aEncoding;
        return this;
    }    
    public SEGatewayClient setCompressing(SECompression aCompression) {
        compression = aCompression;
        return this;
    }
    public SEGatewayClient setConnectTimeout(int timeout) {
        timeoutConnect = timeout;
        return this;
    }
    public SEGatewayClient setReadTimeout(int timeout) {
        timeoutRead = timeout;
        return this;
    }    
    public SEGatewayClient setProxy(Proxy aProxy) {
        proxy = aProxy;
        if(proxy == null) {
            proxy = Proxy.NO_PROXY;
        }
        
        useProxy = true;
        if(proxy == Proxy.NO_PROXY) {
            useProxy = false;
        }
        
        return this;
    }
    public SEGatewayClient dontUseProxy(){
        useProxy = false;
        return this;
    }    
    
    /**
     * Sending request and parsing response.
     * @param <T> is the type of objects in the list of response values.
     * @param request is the SERequest object
     * @param contentClass is the class of type T
     * @return SEResponse object
     * @throws IOException 
     */
    public <T> SEResponse<T> sendRequest(SERequest request, Class<T> contentClass) 
            throws IOException{
        
        SEResponse<T> seResponse = null;
        HttpURLConnection con = null;
        try{
            con = getConnection(request.getFullURL());
            
            logger.debug("Connection url: " + request.getFullURL());
            logDebugInfo();
            
            con.connect();
            if(con == null)
                throw new IOException("Connection error");
                        
            logger.debug("HTTP response: " + con.getResponseCode() + " " + con.getResponseMessage());
            
            seResponse = parseResponse(con, contentClass);
            if(seResponse == null)
                throw new IOException("Parsing response error");
                                    
            logger.debug("Response received. Error: " + seResponse.isError());
            if(seResponse.isError())
                logger.debug("Response error: " + seResponse.getError().toString());
                    
        } catch(IOException e) {
            throw e;
        } catch(Exception e) {
            logger.error("Gateway unknown error", e);
        }
        finally {
            if(con!= null)
                con.disconnect();
        }        
        
        return seResponse;
    }
    
    private <T> SEResponse<T> parseResponse(HttpURLConnection con, Class<T> contentClass) throws IOException{
        Reader reader = null;
        SEResponse<T> seResponse = null;
        int responseCode = con.getResponseCode();
        
        try{
            if(responseCode == HttpURLConnection.HTTP_OK) {
                reader = encodingStreamReader(con.getContentEncoding(), con.getInputStream());
                seResponse = JSONParser.getObject(reader, SEResponse.class, contentClass);
            }
            else {
                if(con.getErrorStream() == null) {
                    throw new IOException("Connection error: " + responseCode + " " + con.getResponseMessage());
                }
                reader = encodingStreamReader(con.getContentEncoding(), con.getErrorStream());
                SEError error = JSONParser.getObject(reader, SEError.class);
                seResponse = new SEResponse<T>().setError(error);
            }        
        } catch(IOException e)  {
            throw e;
        } finally {
            if(reader != null)
                reader.close();
        }
        
        return seResponse;
    }
    // https://api.stackexchange.com/docs/compression
    private Reader encodingStreamReader (String encodingType, InputStream stream) 
        throws IOException{
        
        switch(encodingType.toLowerCase()){
            case "gzip": stream = new GZIPInputStream(stream); break;
            case "deflate": stream = new InflaterInputStream(stream, new Inflater(true)); break;
        }
        
        return new BufferedReader(
                new InputStreamReader(stream,  encoding)
        );
    }
   
    private HttpURLConnection getConnection(String urlString) throws IOException{
        URL url = new URL(urlString);
            
        HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
        
        // https://api.stackexchange.com/docs/
        con.setRequestProperty("Accept-Encoding", compression.getHttpHeaderCode());
        con.setRequestMethod("GET");
        con.setConnectTimeout(timeoutConnect);
        con.setReadTimeout(timeoutRead);

        return con;
    }
    
    private void logDebugInfo () {
        if(!logger. isDebugEnabled())
            return;
     
        if(useProxy) {
            logger.debug(String.format("Proxy settings: use=%b, address=%s", useProxy, proxy.address().toString()));
        }
        
        logger.debug(String.format("Gateway settings: encoding=%s, timeout_connect=%d, timeout_read=%d, proxy=%b, compression=%s",
                encoding,
                timeoutConnect,
                timeoutRead,
                useProxy,
                compression.name())); 
    }
}
