package org.ppl.plug.wryip;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.Map;
 
public class IPSeekerSimpleCache extends IPSeeker {
    private final Map<byte[], IPLocation> cache = new Hashtable<>();
     
    public IPSeekerSimpleCache(Path path) throws IOException {
        super(path);
    }
     
    @Override
    public synchronized IPLocation getLocation(byte ip1, byte ip2, byte ip3, byte ip4) {
        final byte[] ip = { ip1, ip2, ip3, ip4 };
        if (cache.containsKey(ip)) {
            return cache.get(ip);
        } else {
            return cache.put(ip, super.getLocation(ip));
        }
    }
}
