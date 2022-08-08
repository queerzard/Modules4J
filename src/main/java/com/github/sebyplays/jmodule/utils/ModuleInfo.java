package com.github.sebyplays.jmodule.utils;

import com.github.sebyplays.jmodule.utils.Module;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.jar.JarFile;

public class ModuleInfo implements Serializable {

    private final Yaml yaml = new Yaml();
    private transient InputStream inputStream;

    @Getter public String moduleName;
    @Getter public String moduleVersion;
    @Getter public String moduleAuthor;
    @Getter public String moduleMainPath;
    @Getter public String moduleDescription;
    @Getter public String modulePriority;
    @Getter public String dependingOnModule;
    @Getter transient public Module module;

    @Getter public File moduleFile;
    @Getter transient public JarFile jarFile;

    public ModuleInfo(File file) {
        try {
            this.moduleFile = file;
            jarFile = new JarFile(file);
            inputStream = jarFile.getInputStream(jarFile.getEntry("module.yml"));
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        Map<String, Object> keyVal = yaml.load(this.inputStream);
        moduleAuthor = (String) keyVal.get("author");
        moduleMainPath = (String) keyVal.get("main");
        moduleName = (String) keyVal.get("name");
        moduleVersion = (String) keyVal.get("version");
        moduleDescription = (String) keyVal.get("description");
        modulePriority = (String) keyVal.get("priority");
        dependingOnModule = (String) keyVal.get("depends");
    }



}
