package com.github.sebyplays.jmodule.utils;

import com.github.sebyplays.jevent.api.Event;
import lombok.Getter;

public class MethodInvocationEvent extends Event {

    @Getter private String methodName;
    @Getter private Module module;

    public MethodInvocationEvent(Module module, String methodName){
        this.methodName = methodName;
        this.module = module;
    }

    public MethodInvocationEvent(){

    }

}
