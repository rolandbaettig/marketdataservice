package ch.steponline.mds.util;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 15:11
 */
public class SystemDefaultLanguage implements Serializable {
    private static SystemDefaultLanguage ourInstance = new SystemDefaultLanguage();

    private String language = "de";

    private static final long serialVersionUID = 8708471409609884576L;

    public static SystemDefaultLanguage getInstance() {
        return ourInstance;
    }

    /**
     * DefaultSprache dem Server Startup-Parameter mitgeben -Dmds.defaultLanguage=de
     */
    private SystemDefaultLanguage() {
        if (System.getProperty("mds.defaultLanguage")!=null &&
                !"".equals(System.getProperty("mds.defaultLanguage"))) {
            this.language = System.getProperty("mds.defaultLanguage").toLowerCase();
        } else {
        this.language = Locale.getDefault().getLanguage();
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
