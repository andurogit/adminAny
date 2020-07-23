package com.adm.backend.core.persistence.cache;

import java.util.Properties;

import com.ibatis.sqlmap.engine.cache.CacheController;
import com.ibatis.sqlmap.engine.cache.CacheModel;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSCacheController implements CacheController {
    private static final Logger log = LoggerFactory.getLogger(OSCacheController.class);
    private static GeneralCacheAdministrator CACHE_ADMINISTRATOR;

    public static GeneralCacheAdministrator getCacheAdministrator() {
        return CACHE_ADMINISTRATOR;
    }

    public static void setCacheAdministrator(GeneralCacheAdministrator cacheAdministrator) {
        CACHE_ADMINISTRATOR = cacheAdministrator;
    }

    public void flush(CacheModel cacheModel) {
        CACHE_ADMINISTRATOR.flushGroup(cacheModel.getId());
    }

    public Object getObject(CacheModel cacheModel, Object key) {
        String keyString = key.toString();
        try {
            int refreshPeriod = (int)cacheModel.getFlushIntervalSeconds();
            return CACHE_ADMINISTRATOR.getFromCache(keyString, refreshPeriod);
        }
        catch (NeedsRefreshException e) {
            CACHE_ADMINISTRATOR.cancelUpdate(keyString);
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object removeObject(CacheModel cacheModel, Object key) {
        Object result;
        String keyString = key.toString();
        try {
            int refreshPeriod = (int)cacheModel.getFlushIntervalSeconds();
            Object value = CACHE_ADMINISTRATOR.getFromCache(keyString, refreshPeriod);
            if (value != null) {
                CACHE_ADMINISTRATOR.flushEntry(keyString);
            }
            result = value;
        }
        catch (NeedsRefreshException e) {
            try {
                CACHE_ADMINISTRATOR.flushEntry(keyString);
            }
            finally {
                CACHE_ADMINISTRATOR.cancelUpdate(keyString);
                result = null;
            }
        }
        return result;
    }

    public void putObject(CacheModel cacheModel, Object key, Object object) {
        String keyString = key.toString();
        CACHE_ADMINISTRATOR.putInCache(keyString, object, new String[]{cacheModel.getId()});
    }

    public void setProperties(Properties properties) {
    }

    public static void destroy() {
        log.debug("Shutting down OSCache administrator instance.");
        CACHE_ADMINISTRATOR.destroy();
    }
}