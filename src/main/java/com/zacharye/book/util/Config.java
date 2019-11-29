package com.zacharye.book.util;

import com.zacharye.book.util.AbstractConfig;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class Config extends AbstractConfig<Properties> {

    private static Config config = new Config();

    public static synchronized Config getConfig(){
        return config;
    }

    public Config() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("config/config.properties");
        File cfgfile = new File(url.getFile());
        if(!cfgfile.exists()){
            log.error("config.properties is not found");
        }
        setCfgfile(cfgfile);
    }

    @Override
    protected Properties parseCfg(File paramsFile) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(paramsFile));
            return properties;
        } catch (IOException e) {
            log.error("config.properties load error",e);
            return null;
        }
    }

    public String get(String key){
        return getCfg().getProperty(key);
    }
}
