package ru.inp.stackexchange.web;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inp.stackexchange.api.*;

/**
 * Action for search requests.
 */
public class ActionSearch implements Action{

    private final static String PARAM_TITLE = "title";
    private final static String PAGE_CODE = "page.search";

    private static Logger logger = LoggerFactory.getLogger(Action.class);
       
    @Override
    public String execute(ActionContext context) throws Exception {
        
        String title = context.getParameter(PARAM_TITLE);
        if(title == null || title.isEmpty())
            return PAGE_CODE;

        try{
            
            // getting cached SEGatewayClient
            SEGatewayClient seClient = context.getSessionAttributeT("stackexchange.client");
            if(seClient == null) {
                seClient = SEGatewayClientFactory.createFromAppConfig();            
            }    
            if(seClient == null)
                throw new IllegalArgumentException("Gateway client is null");
            
            //setting cached SEGatewayClient
            context.setSessionAttribute("stackexchange.client", seClient);
            
            SERequest seRequest = SERequestFactory.createFromAppConfig("stackexchange.cmd.search")
                    .setIntitle(title)
                    .build();
            if(seRequest == null)
                throw new IllegalArgumentException("Gateway request is null");
            
            SEResponse<SEQuestion> seResponse = seClient.sendRequest(seRequest, SEQuestion.class);
            if(seResponse.isError()) {
                throw new IOException("Response has error: " + seResponse.getError().getErrorId());
            }
            
            context.setAttribute("questions", seResponse.getItems());            
            
        } catch(IllegalArgumentException e) {
            context.setError("Some internal error. Sorry.");
            logger.error("Request creation error: ", e);
        } catch(IOException e) {
            context.setError("Some service error. Sorry.");
            logger.error("Request sending error: ", e);
        } catch(Exception e) {
            logger.error("Unknown error: ", e);
        }
        
        return PAGE_CODE;
    }      
}
