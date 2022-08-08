package com.github.sebyplays.jmodule.utils;

import com.github.sebyplays.jmodule.JModule;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;

public class Module {

    @Getter private ModuleInfo info;
    @Getter private File path;
    @Getter private boolean isLoaded = false;
    @Getter private boolean exclude;
    private final JModule jModule;
    private Module dependency;
    private Reflection reflection = new Reflection();
    public Module(JModule jmodule, File file){
        info = new ModuleInfo(file);
        path = file;
        jModule = jmodule;
        this.exclude = Boolean.parseBoolean((String)jmodule.getConfig().get("modules." + info.getModuleName() +
                "." + info.getModuleVersion() + "." + info.moduleAuthor + ".exclude", "false"));
    }

    /**
     * If the module is not excluded, and it is not loaded, then load it
     */
    @SneakyThrows
    public void load(){
        if(!exclude){
            jModule.getLogger().info("Loading module: " + info.getModuleName() + " v" + info.getModuleVersion());
            if(!isLoaded){
                if(info.dependingOnModule != null){
                    if(!jModule.moduleIsLoaded(info.dependingOnModule)){
                        dependency = jModule.getModuleWithName(info.dependingOnModule);
                        if(dependency != null){
                            if(!dependency.isLoaded)
                                dependency.load();
                        } else {
                            throw new RuntimeException("Module " + info.getModuleName() + " depends on " + info.dependingOnModule + " but it is not loaded!");
                        }
                    }
                }
                invokeOnLoad();
                isLoaded = true;
                invokeOnEnable();
                return;
            }
            jModule.getLogger().print(true).error("Module " + info.getModuleName() + " is already loaded!");
            return;
        }
        jModule.getLogger().print(true).warning("Module " + info.getModuleName() + " has been excluded!");
    }

    /**
     * It sets the exclude variable to the value of the parameter, and then sets the config value to the same value
     *
     * @param exclude This is a boolean that determines whether or not the module is excluded from being loaded.
     * @return The value of the exclude variable.
     */
    public boolean setExclude(boolean exclude){
        this.exclude = exclude;
        jModule.getConfig().set("modules." + info.getModuleName() + "." + info.getModuleVersion() + "." + info.moduleAuthor + ".exclude", exclude + "");
        return exclude;
    }



    /**
     * Invoke the method named 'onLoad'
     */
    public void invokeOnLoad(){
        reflection.invokeMethod(this, "onLoad");
    }

    /**
     * Invoke the onEnable method of the plugin.
     */
    public void invokeOnEnable(){
        reflection.invokeMethod(this, "onEnable");
    }

    /**
     * Invoke the onDisable() method of the plugin.
     */
    public void invokeOnDisable(){
        reflection.invokeMethod(this, "onDisable");
    }

}
