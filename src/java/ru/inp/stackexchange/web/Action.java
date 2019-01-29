package ru.inp.stackexchange.web;

/**
 * General interface for servlet actions.
*/
public interface Action {
    public String execute(ActionContext context) 
            throws Exception;
}
