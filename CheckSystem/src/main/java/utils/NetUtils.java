package utils;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class NetUtils {
    public static String getHostName() throws UnknownHostException {
        String name;
        InetAddress address = InetAddress.getLocalHost();
        name=address.getHostName();
        return name;
    }


    public static String getIP() throws UnknownHostException {
        String ip;
        InetAddress address = InetAddress.getLocalHost();
        ip=address.getHostAddress();
        return ip;
    }

    public static String getSubNetMask(String ipAddress)
    {
        try {
            List<String> list = new ArrayList<String>();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                List<InterfaceAddress> faceAddresses = ni.getInterfaceAddresses();
                if (faceAddresses == null) {
                    faceAddresses = Collections.EMPTY_LIST;
                }
                //
                for (InterfaceAddress faceAddress : faceAddresses) {
                    InetAddress address = faceAddress.getAddress();
                    if (address.isLoopbackAddress() == true || address.getHostAddress().contains(":")) {
                        continue;
                    }
                    //
                    byte[] ipBytes = address.getAddress();
                    long ipData = (ipBytes[0] << 24) + (ipBytes[1] << 16) + (ipBytes[2] << 8) + (ipBytes[3]);
                    String ipMask = calcMaskByPrefixLength(faceAddress.getNetworkPrefixLength());
                    //
                    //                    long a =4294967295L;
                    //
//                    System.out.println(ipMask + "\t" + address.getHostAddress());
                    if (ipAddress.equals(address.getHostAddress()))
                    {
                        return ipMask;
                    }
                    //
                }
            }
            return null;
        } catch (Throwable t) {
            return null;
        }
    }


    private static String calcMaskByPrefixLength(int length) {
        int mask = -1 << (32 - length);
        int partsNum = 4;
        int bitsOfPart = 8;
        int maskParts[] = new int[partsNum];
        int selector = 0x000000ff;
        for (int i = 0; i < maskParts.length; i++) {
            int pos = maskParts.length - 1 - i;
            maskParts[pos] = (mask >> (i * bitsOfPart)) & selector;
        }
        String result = "";
        result = result + maskParts[0];
        for (int i = 1; i < maskParts.length; i++) {
            result = result + "." + maskParts[i];
        }
        return result;
    }


    public static String getGateWay() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddress = netI.getInetAddresses(); enumIpAddress.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddress.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }
}

