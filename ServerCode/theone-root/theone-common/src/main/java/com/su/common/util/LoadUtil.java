package com.su.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LoadUtil {

    public final static Logger logger = LoggerFactory.getLogger(LoadUtil.class);

    /**
     * 提示
     */
    public final static Map<Integer, String> tipMap = new HashMap<>();


    public static void loadTips() {
        try {
            Properties pps = new Properties();
            pps.load(new FileInputStream(new File("../protocol-tools/tips.properties")));
            Enumeration<?> en = pps.propertyNames();
            while (en.hasMoreElements()) {
                String strKey = (String) en.nextElement();
                String strValue = pps.getProperty(strKey);
                tipMap.put(Integer.valueOf(strKey), strValue);
            }
            logger.info("加载 tips.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
