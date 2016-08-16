package org.ppl.plug.wryip;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public  class FileUtil {
    public  Path classpath(String name) {
        try {
           // final URL url = Class.class.getResource(name);
        	
        	final  URL url = this.getClass().getClassLoader().getResource(name);                       
            return (url != null) ? Paths.get(url.toURI()) : null;
        } catch (URISyntaxException e) {
            return null;
        }
    }
 
    public FileUtil() {
    }
}
