package com.github.sebyplays.jmodule;

import com.github.sebyplays.jmodule.utils.Config;
import com.github.sebyplays.jmodule.utils.Module;
import com.github.sebyplays.logmanager.utils.Logger;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;
import java.util.ArrayList;

public class JModule {

    @Getter private ArrayList<Module> modules = new ArrayList<>();
    @Getter private File modulesDirectory;
    @Getter @Setter private Logger logger = new Logger("JModule");

    @Getter @Setter private Config config;

    @SneakyThrows
    public JModule(String path){
        this.modulesDirectory = new File(System.getProperty("user.dir") + "/" + path + "/");
        config = new Config(path);
        if(!this.modulesDirectory.exists()){
            this.modulesDirectory.mkdirs();
            logger.print(true).info("Modules directory has been created!");
        }
    }

    public JModule(){
        this("modules");
    }

    /**
     * It loads all the modules in the modules directory
     */
    @SneakyThrows
    public void loadModules(){
        for(File file : modulesDirectory.listFiles()){
            if(file.getName().endsWith(".jar")){
                logger.print(true).info("Loading module {}...", file.getName().toUpperCase());
                prepareModule(file);
            }
        }

        for(Module module : modules){
            logger.print(true).info("Module {} is being loaded...", module.getInfo().getModuleName().toUpperCase());
            loadModule(module);
        }
    }

    /**
     * It loads a module
     *
     * @param module The module to load
     */
    @SneakyThrows
    public void loadModule(Module module){
        module.load();
        logger.print(true).info("Module {} has been loaded!", module.getInfo().getModuleName().toUpperCase());
    }

    /**
     * It loads a module
     *
     * @param file The file of the module you want to load.
     */
    @SneakyThrows
    public void loadModule(File file){
        if(file.getName().endsWith(".jar")){
            prepareModule(file);
            loadModule(getModuleByFile(file));
            logger.print(true).info("Module {} has been loaded!", file.getName().toUpperCase());
            return;
        }
        logger.print(true).error("File {} is not a module...", file.getName().toUpperCase());
    }

    /**
     * It creates a new Module object, adds it to the modules list, and prints a message to the console
     *
     * @param file The file of the module
     */
    @SneakyThrows
    public void prepareModule(File file){
        Module module = new Module(this, file);
        modules.add(module);
        logger.print(true).info("Module {} has been prepared..", module.getInfo().getModuleName().toUpperCase());
    }

    /**
     * This function sets the exclude flag of a module to the value of the exclude parameter, and returns the value of the
     * exclude flag.
     *
     * @param module The module to be excluded.
     * @param exclude true or false
     * @return The boolean value of the module's exclude status.
     */
    public boolean setExcludeModule(Module module, boolean exclude){
        module.setExclude(exclude);
        return module.isExclude();
    }

    /**
     * It loops through all the modules and checks if the module with the name passed is initialized
     *
     * @param name The name of the module you want to check if it's loaded.
     * @return A boolean value.
     */
    public boolean moduleIsLoaded(String name){
        for(Module module : modules){
            if(module.getInfo().getModuleName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    /**
     * This function returns the module with the given name, or null if no module with that name exists.
     *
     * @param name The name of the module you want to get.
     * @return A module with the name specified.
     */
    public Module getModuleWithName(String name){
        for(Module module : modules){
            if(module.getInfo().getModuleName().equalsIgnoreCase(name)){
                return module;
            }
        }
        return null;
    }

    /**
     * "Return the module that has the same path as the given file."
     *
     * The function is a bit more complicated than that, but that's the gist of it
     *
     * @param file The file that you want to get the module of.
     * @return The module that has the same path as the file.
     */
    public Module getModuleByFile(File file){
        for(Module module : modules){
            if(module.getPath().equals(file)){
                return module;
            }
        }
        return null;
    }

}
