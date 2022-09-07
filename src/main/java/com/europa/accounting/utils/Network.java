package com.europa.accounting.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fengwen
 * @date 2022/7/2
 * @description TODO
 **/
@Slf4j
@Data
public class Network {
    private final static byte INVALID_MACS[][] = {
            {0x00, 0x05, 0x69},             // VMWare
            {0x00, 0x1C, 0x14},             // VMWare
            {0x00, 0x0C, 0x29},             // VMWare
            {0x00, 0x50, 0x56},             // VMWare
            {0x08, 0x00, 0x27},             // Virtualbox
            {0x0A, 0x00, 0x27},             // Virtualbox
            {0x00, 0x03, (byte) 0xFF},       // Virtual-PC
            {0x00, 0x15, 0x5D}              // Hyper-V
    };

    public static boolean isVMMac(byte[] mac) {
        if (null == mac) {
            return false;
        }

        for (byte[] invalid : INVALID_MACS) {
            if (invalid[0] == mac[0] && invalid[1] == mac[1] && invalid[2] == mac[2]) {
                return true;
            }
        }

        return false;
    }

    public static String getLocalIpAddress() {
        String result = "";
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = networkInterfaces.nextElement();

                if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) {
                    continue;
                }

                if (isVMMac(ni.getHardwareAddress())) {
                    continue;
                }


                Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();

                    if (inetAddress instanceof Inet6Address) {
                        continue;
                    }


                    NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
                    //获取网卡的mac地址字节数组，这个字节数组的长度是6，读者可以自行断点查看
                    byte[] macAddr = networkInterface.getHardwareAddress();
                    //将字节数组转成16进制表示
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < macAddr.length; i++) {
                        if (i != 0) {
                            sb.append("-");
                        }
                        //字节转换为整数
                        int temp = macAddr[i] & 0xff;
                        String str = Integer.toHexString(temp);
                        if (str.length() == 1) {
                            sb.append("0" + str);
                        } else {
                            sb.append(str);
                        }
                    }
                    result += sb.toString().toUpperCase() + ",";
                }
            }
        } catch (SocketException e) {
            log.error("获取本机IP地址失败。", e);
        }
        return result;
    }

    public static String getCpuId() throws IOException {

        // linux，windows命令
        String[] linux = {"dmidecode", "-t", "processor", "|", "grep", "'ID'"};
        String[] windows = {"wmic", "cpu", "get", "ProcessorId"};

        // 获取系统信息
        String property = System.getProperty("os.name");
        Process process = Runtime.getRuntime().exec(property.contains("Window") ? windows : linux);
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream(), "utf-8");
        sc.next();
        return sc.next();
    }

    public static List<String> getMacList() throws Exception {

        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> macList = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            NetworkInterface iface = enumeration.nextElement();
            List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
            for (InterfaceAddress addr : addrs) {

                // 获取本地IP对象
                InetAddress ia = addr.getAddress();
                NetworkInterface network = NetworkInterface.getByInetAddress(ia);
                if (network == null) {
                    continue;
                }
                // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
                byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                parseMac(mac, sb);
                macList.add(sb.toString().toUpperCase());
                sb.delete(0, sb.length());
            }
        }
        // 去重处理，同一个网卡的ipv4,ipv6得到的mac都是一样的
        if (macList != null && macList.size() > 0) {
            List<String> result = macList.stream().distinct().collect(Collectors.toList());
            return result;
        }
        return null;
    }
    public static void parseMac(byte[] mac, StringBuilder sb) {
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
    }

    /**
     * 获取主板序列号
     * @return
     */
    public static String getMotherboardSN() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            String path = file.getPath().replace("%20", " ");
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + path);
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     * 获取硬盘序列号(该方法获取的是 盘符的逻辑序列号,并不是硬盘本身的序列号)
     * 硬盘序列号还在研究中
     * @param drive 盘符
     * @return
     */
    public static String getHardDiskSN(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\""
                    + drive
                    + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber"; // see note
            fw.write(vbs);
            fw.close();
            String path = file.getPath().replace("%20", " ");
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + path);
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }


    public static void main(String[] args) throws Exception {
        String macAddr = getLocalIpAddress();
        System.out.println(macAddr);

        InetAddress ia = InetAddress.getLocalHost();
        String host = ia.getHostName();
        System.out.println(host);

        System.out.println(getCpuId());

        List<String> macList = getMacList();
        System.out.println(Arrays.toString(macList.toArray()));
        System.out.println(getMotherboardSN());
        getMotherboardSN();


    }

}
