package com.pps.globant.fittracker.utils;

import com.facebook.CallbackManager;

public class CallBackManagerProviderForFb {
    private static CallbackManager callbackManager=null;
    public static CallbackManager getCallbackManager(){
        if (callbackManager==null){
            callbackManager=CallbackManager.Factory.create();
        }
        return callbackManager;
    }
}
