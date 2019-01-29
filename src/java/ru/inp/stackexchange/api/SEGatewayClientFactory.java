package ru.inp.stackexchange.api;

import java.net.InetSocketAddress;
import java.net.Proxy;
import ru.inp.stackexchange.AppConfig;

/**
 * Factory for SEGatewayClient objects.
 */
public class SEGatewayClientFactory {
    /**
     * Creating SEGatewayClient from application config.
     * @return SEGatewayClient object
     * @throws IllegalArgumentException 
     */
    public static SEGatewayClient createFromAppConfig()  throws IllegalArgumentException{
        
        String proxyHost = AppConfig.get("proxy.host");
        String proxyPort = AppConfig.get("proxy.port");

        Proxy proxy = Proxy.NO_PROXY;
        if(proxyHost != null && !proxyHost.isEmpty() &&
           proxyPort != null && !proxyPort.isEmpty()) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.parseInt(proxyPort)));
        }
        
        int timeoutRead = Integer.parseInt(AppConfig.get("stackexchange.timeout.read"));
        int timeoutConnect = Integer.parseInt(AppConfig.get("stackexchange.timeout.connect"));
        SECompression compression = SECompression.ofString(AppConfig.get("stackexchange.compress"));
        
        return new SEGatewayClient()
                    .setCompressing(compression)
                    .setConnectTimeout(timeoutConnect)
                    .setReadTimeout(timeoutRead)
                    .setProxy(proxy)
                    .build();
    }
}
