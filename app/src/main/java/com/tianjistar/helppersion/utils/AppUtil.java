package com.tianjistar.helppersion.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Xml;
import android.view.inputmethod.InputMethodManager;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * App工具类
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2016年4月29日 上午12:08:16
 */
public class AppUtil {

    public static List<String[]> mProcessList = null;


    /**
     * 判断app是否在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }


    /**
     * 打开位置的位置
     *
     * @param context 上下文
     */
    public static void openLocationSourceSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 打开系统的“设置-->应用”设置界面
     *
     * @param context 上下文
     */
    public static void openApplicationSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 直接拨打电话
     *
     * @param context 上下文
     * @param tel     电话号码
     */
    public static void call(Context context, String tel) {
        // 用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转到拨号界面
     *
     * @param context 上下文
     * @param tel     电话号码
     */
    public static void dial(Context context, String tel) {
        // 用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 获取应用程序的版本名称.
     *
     * @param context the mContext
     */
    public static String getVersionName(Context context) {
        // 用来管理手机的APK
        PackageManager pm = context.getPackageManager();
        try {
            // 得到知道APK的功能清单文件
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取应用程序的版本号.
     *
     * @param context the mContext
     */
    public static int getVersionCode(Context context) {
        // 用来管理手机的APK
        PackageManager pm = context.getPackageManager();
        try {
            // 得到知道APK的功能清单文件
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 描述：打开并安装文件.
     *
     * @param context the mContext
     * @param file    apk文件路径
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 描述：卸载程序.
     *
     * @param context     the mContext
     * @param packageName 包名
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("app:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param context   the mContext
     * @param className 判断的服务名字 "com.xxx.xx..XXXService"
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);
        Iterator<RunningServiceInfo> l = servicesList.iterator();
        while (l.hasNext()) {
            RunningServiceInfo si = (RunningServiceInfo) l.next();
            if (className.equals(si.service.getClassName())) {
                isRunning = true;
            }
        }
        return isRunning;
    }

    /**
     * 停止服务.
     *
     * @param context   the mContext
     * @param className the class goods_name
     * @return true, if successful
     */
    public static boolean stopRunningService(Context context, String className) {
        Intent intent_service = null;
        boolean ret = false;
        try {
            intent_service = new Intent(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent_service != null) {
            ret = context.stopService(intent_service);
        }
        return ret;
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     *
     * @return The number of cores, or 1 if failed to get result
     */
    public static int getNumCores() {
        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    // Check if filename is "cpu", followed by a single digit
                    // number
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }

            });
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 描述：判断网络是否有效.
     *
     * @param context the mContext
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Gps是否打开 需要
     * <uses-permission android:goods_name="android.permission.ACCESS_FINE_LOCATION"
     * />权限
     *
     * @param context the mContext
     * @return true, if is gps enabled
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 网络定位是否打开 需要
     * <uses-permission android:goods_name="android.permission.ACCESS_FINE_LOCATION"
     * />权限
     *
     * @param context the mContext
     * @return true, if is gps enabled
     */
    public static boolean isNetworkLocationEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * 判断当前网络是否是移动数据网络.
     *
     * @param context the mContext
     * @return boolean
     */
    public static boolean isMobile(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 导入数据库.
     *
     * @param context the mContext
     * @param dbName  the db goods_name
     * @param rawRes  the raw res
     * @return true, if successful
     */
    public static boolean importDatabase(Context context, String dbName, int rawRes) {
        int buffer_size = 1024;
        InputStream is = null;
        FileOutputStream fos = null;
        boolean flag = false;

        try {
            String dbPath = "/data/data/" + context.getPackageName() + "/databases/" + dbName;
            File dbfile = new File(dbPath);
            // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
            if (!dbfile.exists()) {
                // 欲导入的数据库
                if (!dbfile.getParentFile().exists()) {
                    dbfile.getParentFile().mkdirs();
                }
                dbfile.createNewFile();
                is = context.getResources().openRawResource(rawRes);
                fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[buffer_size];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.flush();
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
        return flag;
    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context the mContext
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();

        } else {
            mResources = context.getResources();
        }
        // DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5,
        // xdpi=160.421, ydpi=159.497}
        // DisplayMetrics{density=2.0, width=720, height=1280,
        // scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }

    /**
     * 打开键盘.
     *
     * @param context the mContext
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭键盘事件.
     *
     * @param context the mContext
     */
    public static void closeSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity) context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 获取包信息.
     *
     * @param context the mContext
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            String packageName = context.getPackageName();
            info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 描述：根据进程名返回应用程序.
     *
     * @param context
     * @param processName
     * @return
     */
    public static ApplicationInfo getApplicationInfo(Context context, String processName) {
        if (processName == null) {
            return null;
        }

        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        List<ApplicationInfo> appList = packageManager
                .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (ApplicationInfo appInfo : appList) {
            if (processName.equals(appInfo.processName)) {
                return appInfo;
            }
        }
        return null;
    }

    /**
     * 描述：kill进程.
     *
     * @param context
     * @param pid
     */
    public static void killProcesses(Context context, int pid, String processName) {
        /*
         * String cmd = "kill -9 "+pid; Process process = null; DataOutputStream
		 * os = null; try { process = Runtime.getRuntime().exec("su"); os = new
		 * DataOutputStream(process.getOutputStream()); os.writeBytes(cmd +
		 * "\n"); os.writeBytes("exit\n"); os.flush(); process.waitFor(); }
		 * catch (Exception e) { e.printStackTrace(); }
		 * AbLogUtil.d(AppUtil.class, "#kill -9 "+pid);
		 */

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = null;
        try {
            if (processName.indexOf(":") == -1) {
                packageName = processName;
            } else {
                packageName = processName.split(":")[0];
            }

            activityManager.killBackgroundProcesses(packageName);

            //
            Method forceStopPackage = activityManager.getClass().getDeclaredMethod("forceStopPackage", String.class);
            forceStopPackage.setAccessible(true);
            forceStopPackage.invoke(activityManager, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 描述：执行命令.
     *
     * @param command
     * @param workdirectory
     * @return
     */
    public static String runCommand(String[] command, String workdirectory) {
        String result = "";
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            // set working directory
            if (workdirectory != null) {
                builder.directory(new File(workdirectory));
            }
            builder.redirectErrorStream(true);
            Process process = builder.start();
            InputStream in = process.getInputStream();
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                String str = new String(buffer);
                result = result + str;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 描述：运行脚本.
     *
     * @param script
     * @return
     */
    public static String runScript(String script) {
        String sRet = "";
        try {
            final Process m_process = Runtime.getRuntime().exec(script);
            final StringBuilder sbread = new StringBuilder();
            Thread tout = new Thread(new Runnable() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(m_process.getInputStream()), 8192);
                    String ls_1 = null;
                    try {
                        while ((ls_1 = bufferedReader.readLine()) != null) {
                            sbread.append(ls_1).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            tout.start();

            final StringBuilder sberr = new StringBuilder();
            Thread terr = new Thread(new Runnable() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(m_process.getErrorStream()), 8192);
                    String ls_1 = null;
                    try {
                        while ((ls_1 = bufferedReader.readLine()) != null) {
                            sberr.append(ls_1).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            terr.start();

            int retvalue = m_process.waitFor();
            while (tout.isAlive()) {
                Thread.sleep(50);
            }
            if (terr.isAlive())
                terr.interrupt();
            String stdout = sbread.toString();
            String stderr = sberr.toString();
            sRet = stdout + stderr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sRet;
    }

    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     *
     * @return 应用程序是/否获取Root权限
     */
    public static boolean getRootPermission(Context context) {
        String path = context.getPackageCodePath();
        return getRootPermission(path);
    }

    /**
     * 修改文件权限
     *
     * @return 文件路径
     */
    public static boolean getRootPermission(String path) {
        Process process = null;
        DataOutputStream os = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                return false;
            }
            String cmd = "chmod 777 " + path;
            // 切换到root帐号
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 描述：获取进程运行的信息.
     *
     * @return
     */
    public static List<String[]> getProcessRunningInfo() {
        List<String[]> processList = null;
        try {
            String result = runCommandTopN1();
            processList = parseProcessRunningInfo(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processList;
    }

    /**
     * 描述：top -n 1.
     *
     * @return
     */
    public static String runCommandTopN1() {
        String result = null;
        try {
            String[] args = {"/system/bin/top", "-n", "1"};
            result = runCommand(args, "/system/bin/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 描述：解析数据.
     *
     * @param info User 39%, System 17%, IOW 3%, IRQ 0% PID PR CPU% S #THR VSS
     *             RSS PCY UID Name 31587 0 39% S 14 542288K 42272K fg u0_a162
     *             cn.amsoft.process 313 1 17% S 12 68620K 11328K fg system
     *             /system/bin/surfaceflinger 32076 1 2% R 1 1304K 604K bg
     *             u0_a162 /system/bin/top
     * @return
     */
    public static List<String[]> parseProcessRunningInfo(String info) {
        List<String[]> processList = new ArrayList<String[]>();
        int Length_ProcStat = 10;
        String tempString = "";
        boolean bIsProcInfo = false;
        String[] rows = null;
        String[] columns = null;
        rows = info.split("[\n]+");
        // 使用正则表达式分割字符串
        for (int i = 0; i < rows.length; i++) {
            tempString = rows[i];
            // AbLogUtil.d(AppUtil.class, tempString);
            if (tempString.indexOf("PID") == -1) {
                if (bIsProcInfo == true) {
                    tempString = tempString.trim();
                    columns = tempString.split("[ ]+");
                    if (columns.length == Length_ProcStat) {
                        // 把/system/bin/的去掉
                        if (columns[9].startsWith("/system/bin/")) {
                            continue;
                        }
                        // AbLogUtil.d(AppUtil.class,
                        // "#"+columns[9]+",PID:"+columns[0]);
                        processList.add(columns);
                    }
                }
            } else {
                bIsProcInfo = true;
            }
        }
        return processList;
    }

    /**
     * 描述：获取可用内存.
     *
     * @param context
     * @return
     */
    public static long getAvailMemory(Context context) {
        // 获取android当前可用内存大小
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        // 当前系统可用内存 ,将获得的内存大小规格化
        return memoryInfo.availMem;
    }

    /**
     * 描述：总内存.
     *
     * @param context
     * @return
     */
    public static long getTotalMemory(Context context) {
        // 系统内存信息文件
        String file = "/proc/meminfo";
        String memInfo;
        String[] strs;
        long memory = 0;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 8192);
            // 读取meminfo第一行，系统内存大小
            memInfo = bufferedReader.readLine();
            strs = memInfo.split("\\s+");
            // 获得系统总内存，单位KB
            memory = Integer.valueOf(strs[1]).intValue() * 1024;
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Byte转位KB或MB
        return memory;
    }

    /**
     * 获取mac地址.
     *
     * @param context
     * @return
     */
    public static String getMac(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info.getMacAddress() == null) {
            return null;
        } else {
            return info.getMacAddress();
        }
    }

    /**
     * 获取SSID地址.
     *
     * @param context
     * @return
     */
    public static String getSSID(Context context) {

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info.getSSID() == null) {
            return null;
        } else {
            return info.getSSID();
        }
    }

    /**
     * 获取IMSI.
     *
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getSubscriberId() == null) {
            return null;
        } else {
            return telephonyManager.getSubscriberId();
        }
    }

    /**
     * 获取IMEI.
     *
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getDeviceId() == null) {
            return null;
        } else {
            return telephonyManager.getDeviceId();
        }
    }

    /**
     * 手机号码
     *
     * @return
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getLine1Number() == null || telephonyManager.getLine1Number().length() < 11) {
            return null;
        } else {
            return telephonyManager.getLine1Number();
        }
    }

    /**
     * 获取QQ号.
     *
     * @return
     */
    public static String getQQNumber(Context context) {
        String path = "/data/data/com.tencent.mobileqq/shared_prefs/Last_Login.xml";
        getRootPermission(context);
        File file = new File(path);
        getRootPermission(path);
        boolean flag = file.canRead();
        String qq = null;
        if (flag) {
            try {
                FileInputStream is = new FileInputStream(file);
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(is, "UTF-8");
                int event = parser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT) {

                    switch (event) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if ("map".equals(parser.getName())) {
                            }
                            if ("string".equals(parser.getName())) {
                                String uin = parser.getAttributeValue(null, "goods_name");
                                if (uin.equals("uin")) {
                                    qq = parser.nextText();
                                    return qq;
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                    }
                    event = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取WEIXIN号.
     *
     * @return
     */
    public static String getWeiXinNumber(Context context) {
        String path = "/data/data/com.tencent.mm/shared_prefs/com.tencent.mm_preferences.xml";
        getRootPermission(context);
        File file = new File(path);
        getRootPermission(path);
        boolean flag = file.canRead();
        String weixin = null;
        if (flag) {
            try {
                FileInputStream is = new FileInputStream(file);
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(is, "UTF-8");
                int event = parser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT) {

                    switch (event) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if ("map".equals(parser.getName())) {
                            }
                            if ("string".equals(parser.getName())) {
                                String nameString = parser.getAttributeValue(null, "goods_name");
                                if (nameString.equals("login_user_name")) {
                                    weixin = parser.nextText();
                                    return weixin;
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                    }
                    event = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取deviceid
     *
     * @param context
     * @return
     */

    public static String getDeviceId(final Context context) {
        String imei = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        imei = tm.getDeviceId();
        if(imei==null)
        {
            // android pad
            imei = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
//
        return imei;
    }

    public static String getPhoneImei(Context context) {
        String imeiVaue = "";
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if(manager.getDeviceId() == null || manager.getDeviceId().equals("")) {
                if (Build.VERSION.SDK_INT >= 23) {
                    imeiVaue = manager.getDeviceId(0);
                }
            }else{
                imeiVaue = manager.getDeviceId();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return imeiVaue;
    }

}
