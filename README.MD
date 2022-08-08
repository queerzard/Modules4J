
# Modules4J

An extension library for java



## Maven Installation

```xml

<repository>
  <id>reposilite-repository</id>
  <name>Seby's Project Repository</name>
  <url>http://mcsilent.de:8091/{repository}</url>
</repository>

<dependency>
  <groupId>com.github.sebyplays</groupId>
  <artifactId>Modules4J</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Used libraries

- [lombok](https://projectlombok.org/)

## Usage/Examples
Host-Project:
```java
public class Main {

    public static void main(String[] args) {
        JModule jModule = new JModule("path/to/module/directory");
        jModule.loadModules();
    }

}
```

Main class of the module to be loaded:
```java
import com.github.sebyplays.jmodule.api.JavaModule;

public class TestModule implements JavaModule {


    @Override
    public void onLoad() {
        // do something when this loads
    }

    @Override
    public void onEnable() {
        //main code
    }

    @Override
    public void onDisable() {
        //self-explanatory
    }
}
```

module.yml (in project root of extension):
```yml
name: NameOfModule
main: path.to.main.Class
author: Seby_Plays
depends: ModuleItDependsOn //if it depends on a module
version: 1.0.0
```