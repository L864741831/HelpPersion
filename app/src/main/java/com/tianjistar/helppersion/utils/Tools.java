package com.tianjistar.helppersion.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/13.
 */

public class Tools {
    public static int dip2px(Context context, float dpValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } catch (Throwable throwable) {
            // igonre
        }
        return 0;
    }
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
    public static String getWeek(Date date){
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }
    /**
     * <pre>
     * 根据指定的日期字符串获取星期几
     * </pre>
     *
     * @param strDate 指定的日期字符串(yyyy-MM-dd 或 yyyy/MM/dd)
     * @return week
     *         星期几(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY)
     */
    public static String getWeekByDateStr(String strDate)
    { int month;
        int day;
        int year = Integer.parseInt(strDate.substring(0, 4));
        try {
            month = Integer.parseInt(strDate.substring(5, 7));
             day = Integer.parseInt(strDate.substring(8, 10));
        }catch (Exception e){
             month = Integer.parseInt(strDate.substring(5,6));
            if (strDate.substring(7,strDate.length()).length()<2){
                String s="0"+strDate.substring(7,strDate.length());
                day = Integer.parseInt(s);
            }else{
                day = Integer.parseInt(strDate.substring(7,strDate.length()));
            }
//             day = Integer.parseInt(strDate.substring(7,strDate.length()));
        }


        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);

        String week = "";
        int weekIndex = c.get(Calendar.DAY_OF_WEEK);

        switch (weekIndex)
        {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }
    //基姆拉尔森计算公式根据日期判断星期几
    public static String  CalculateWeekDay(int y, int m, int d) {
        if (m == 1 || m == 2) {
            m += 12;
            y--;
        }
        int iWeek = (d + 2 * m + 3 * (m + 1) / 5 + y + y / 4 - y / 100 + y / 400) % 7;
        String week = "";
        switch (iWeek) {
            case 0:
                week= "星期一";
                break;
            case 1:
                week="星期二";
                break;
            case 2:
                week="星期三";
                break;
            case 3:
                week="星期四";
                break;
            case 4:
                week="星期五";
                break;
            case 5:
                week="星期六";;
                break;
            case 6:
                week="星期日";;
                break;
        }
        return week;
    }
    //将指定日期转化为秒数
    public static int getSecondsFromDate(String expireDate){
        if(expireDate==null||expireDate.trim().equals(""))
            return 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try{
            date=sdf.parse(expireDate);
            return (int)(date.getTime()/1000);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 获取当前日期是星期几

     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }
    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
//    /**
//     80      * 银行卡替换，保留后四位
//     81      *
//     82      * 如果银行卡号为空 或者 null ,返回null ；否则，返回替换后的字符串；
//     83      *
//     84      * @param bankCard
//     85      *            银行卡号
//     86      * @return
//     87      */
//   public static String bankCardReplaceWithStar(String sNumber) {
//
////                if (bankCard.isEmpty() || bankCard == null) {
////                        return null;
////                   } else {
////                         return replaceAction(bankCard, "(?<=\\d{0})\\d(?=\\d{4})");
////                    }
//       String numberEnd = sNumber.substring(sNumber.length() - 4,sNumber.length());//后四位
//       int emptyLenth = sNumber.length() % 4;
//       int endnumber = (sNumber.length() - 4) % 4;
//       StringBuilder sb = new StringBuilder();
//       if (emptyLenth == 0) {
//           for (int i = 0; i < sNumber.length() / 4-1; i++) {
//               sb.append("**** ");
//           }
//       } else {
//           for (int i = 0; i < emptyLenth; i++) {
//               sb.append("**** ");
//           }
//       }
//       for (int i = 0; i < endnumber; i++) {
//           sb.append("*");
//       }
//
//       return sb.append(numberEnd).toString();
//           }
//    /**
//     49      * 实际替换动作
//     50      *
//     51      * @param username
//     52      *            username
//     53      * @param regular
//     54      *            正则
//     55      * @return
//     56      */
//     private static String replaceAction(String username, String regular) {
//               return username.replaceAll(regular, "*");
//            }
    /**
     * 验证输入身份证号
     *
     * @param
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsIDcard(String str) {
        String regex = "(^\\d{18}$)|(^\\d{15}$)";
        return match(regex, str);
    }
    /**
     * @param regex
     * 正则表达式字符串
     * @param str
     * 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    /**
     * 判断是否是银行卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;

    }

    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}
