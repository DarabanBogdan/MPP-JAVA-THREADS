package Repo;

import java.util.Objects;
import java.util.Properties;

public class Config
{
    Properties configFile;
    public Config()
    {
        configFile = new Properties();
        try {
            configFile.load(Objects.requireNonNull(this.getClass().getClassLoader().
                    getResourceAsStream("prop.properties")));
        }catch(Exception eta){
            eta.printStackTrace();
        }
    }

    public String getProperty(String key)
    {
        String value = this.configFile.getProperty(key);
        return value;
    }
}
