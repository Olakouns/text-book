package io.cassiopee.textbook.storages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {

    private String location;
    @Value("${files.storage}")
    private String filesStorage;
    ClassLoader classLoader = getClass().getClassLoader();

    public StorageProperties() {
        this.location = System.getProperty("user.home") + File.separator + "textbook";
        System.out.println("loaction file " + this.location);
        File dir = new File(this.location + File.separator + "images");
        if (!dir.exists())
            if(!dir.mkdirs()){
                System.err.println("no create "+dir.getAbsolutePath());
            }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
