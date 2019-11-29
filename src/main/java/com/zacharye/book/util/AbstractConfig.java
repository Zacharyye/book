package com.zacharye.book.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

/**
 * abstract class to read local file
 * @param <T>
 */
public abstract class AbstractConfig<T> {
    protected static final Logger log = LoggerFactory.getLogger(AbstractConfig.class);
    protected File cfgfile;
    private volatile long cfgfileLastModify = 0L;
    protected T cfg;

    public void setCfgfile (File cfgfile) {
        this.cfgfile = cfgfile;
    }

    public T getCfg () {
        long lastModify = this.cfgfile.lastModified();
        if(lastModify != this.cfgfileLastModify){
            reloadCfg(this.cfgfile,lastModify);
        }
        return this.cfg;
    }

    private synchronized void reloadCfg(File file,long lastModify) {
        if(this.cfgfileLastModify != lastModify){
            this.cfg = parseCfg(cfgfile);
            this.cfgfileLastModify = lastModify;
        }
    }

    protected abstract T parseCfg (File paramsFile);
}
