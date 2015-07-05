package com.nextshopper.rest.beans;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

public class UniqueNumber {
	private static String machineId;
	static {
		try {
			Enumeration e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements())
			{
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    Enumeration ee = n.getInetAddresses();
			    while (ee.hasMoreElements())
			    {
			        InetAddress i = (InetAddress) ee.nextElement();
			        String addr = i.getHostAddress();
		        	int pos = addr.lastIndexOf(".");
					if(!"127.0.0.1".equals(addr) && pos>0) {
			        	int ip = Integer.parseInt(addr.substring(pos+1));
			        	machineId = String.format("%03d", ip);
			        	break;
			        }
			    }
			    if(machineId != null) 
			    	break;
			}
			if(machineId == null) {
				machineId = String.valueOf(new Random().nextInt(255));
				//throw new RuntimeException("cannot get ip");
			}
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
	}
	
}
