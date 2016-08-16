package org.ppl.plug.wryip;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Inet4Address;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;

public class IPSeeker {
	final ByteBuffer buffer;
	final Helper h;
	final int offsetBegin, offsetEnd;

	public IPSeeker(Path path) throws IOException {
		if (Files.exists(path)) {
			buffer = ByteBuffer.wrap(Files.readAllBytes(path));
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			offsetBegin = buffer.getInt(0);
			offsetEnd = buffer.getInt(4);
			if (offsetBegin == -1 || offsetEnd == -1) {
				throw new IllegalArgumentException("File Format Error");
			}
			h = new Helper(this);
		} else {
			System.out.println(path);
			throw new FileNotFoundException();
			
		}
	}

	public IPLocation getLocation(final byte ip1, final byte ip2,
			final byte ip3, final byte ip4) {
		return getLocation(new byte[] { ip1, ip2, ip3, ip4 });
	}

	public IPLocation getIP(String ipString) {
		String[] ips = ipString.split("\\.");
		
		int ip1 = Integer.valueOf(ips[0]);
		int ip2 = Integer.valueOf(ips[1]);
		int ip3 = Integer.valueOf(ips[2]);
		int ip4 = Integer.valueOf(ips[3]);

		return getLocation((byte) ip1, (byte) ip2, (byte) ip3, (byte) ip4);
	}

	protected final IPLocation getLocation(final byte[] ip) {
		return h.getLocation(h.locateOffset(ip));
	}

	public IPLocation getLocation(final Inet4Address address) {
		return getLocation(address.getAddress());
	}
}
