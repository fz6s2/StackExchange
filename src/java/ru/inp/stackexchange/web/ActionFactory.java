package ru.inp.stackexchange.web;

/**
 * Factory for servlet actions.
 */
public class ActionFactory {

    public static Action getAction(ActionContext context) {
        String action = context.getRequest().getServletPath();
        
        switch(action.toLowerCase()){
            case "/search": return new ActionSearch();
            //some other actions ...
        }
        return null;
    };
}
