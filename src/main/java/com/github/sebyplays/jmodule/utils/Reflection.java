package com.github.sebyplays.jmodule.utils;

import com.github.sebyplays.jevent.JEvent;
import com.github.sebyplays.logmanager.LogManager;
import com.github.sebyplays.logmanager.utils.LogType;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Reflection {

    @SneakyThrows
    public void universalMethodInvoke(File file, String classpath, String methodName){
        Class jClass = Class.forName(classpath, true, new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader()));
        Method method = jClass.getDeclaredMethod(methodName);
        method.invoke(jClass.newInstance());
    }

    @SneakyThrows
    public void invokeMethod(Module module, String methodName){
        if(!new JEvent(new MethodInvocationEvent(module, methodName)).callEvent().getEvent().isCancelled()){
            Class jClass = Class.forName(module.getInfo().getModuleMainPath(), true, new URLClassLoader(new URL[]{module.getPath().toURI().toURL()}, this.getClass().getClassLoader()));
            Method method = jClass.getDeclaredMethod(methodName);
            if(method != null){
                method.invoke(jClass.newInstance());
            } else {
                LogManager.getLogManager("JModule").log(LogType.ERROR, "Method " + methodName + " does not exist in " + module.getInfo().getModuleMainPath(), false, false, true, true);
            }
        }
    }

}
