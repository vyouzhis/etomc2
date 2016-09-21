package com.lib.plug;

import java.io.IOException;
import java.nio.file.Path;

import org.ppl.common.function;
import org.ppl.plug.wryip.FileUtil;
import org.ppl.plug.wryip.IPLocation;
import org.ppl.plug.wryip.IPSeeker;

public class IptoAddr extends function {
	public String ia(String ip) {
		String city = "";
		IPSeeker seeker = null;
		Path ipWry = null;

		if (ip == null)
			return city;
		try {
			FileUtil fu = new FileUtil();
			ipWry = fu.classpath("properties/qqwry.dat");
			seeker = new IPSeeker(ipWry);
			IPLocation location = seeker.getIP(ip);
			if(location == null){
				return "";
			}
			
			city = location.getCountry();

			if (city == "" || city.equals("局域网")) {

				return city;
			} else if (city.length() > 2) {
				city += ","+location.getCountry().substring(0, 2);
			}			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return city;
	}
}
