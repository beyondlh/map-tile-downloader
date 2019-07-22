package  com.hdsx.lh.maptiles.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期操作工具类
 * Created by 黄聪 on 2017/10/10.
 */
public class DateUtils {

    /**
     * 提取文件名中的日期(只针对中国气象的数据处理)
     * @param fullName
     * @return
     */
    public static Date getDatasFromImageName(String fullName){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String[] arr=fullName.split("_");
        String timeStr=arr[arr.length-1].split("\\.")[0];
        String fullDateStr=timeStr.substring(0,8);//当前日期
        String hourStr=timeStr.substring(8,10);//时
        String minStr=timeStr.substring(10,12);//分

        Date date =null;
        try {
            date = sdf.parse(fullDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int hour=Integer.parseInt(hourStr);
        int min=Integer.parseInt(minStr);

        Calendar ca=Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY,hour);
        ca.add(Calendar.MINUTE, min);
        date=ca.getTime();
        return date;
    }

    /**
     * 提取文件名中的日期(只针对中国气象的数据处理)
     * @param fullName
     * @return
     */
    public static List<Date> getDatasFromNMCImageName(String fullName){

        List<Date> dates=new ArrayList<Date>();
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1=null;//数据的发布时间
        Date date2=null;//预测数据预测范围（开始时间）
        Date date3=null;//预测数据预测范围（结束时间）

        String[] arr=fullName.split("_");
        String timeStr=arr[arr.length-1].split("\\.")[0];
        String fullDateStr=timeStr.substring(0,8);//当前日期
        String hourStr=timeStr.substring(8,10);//时
        String minStr=timeStr.substring(10,12);//分
        String fcHourStr=timeStr.substring(12,17);

        boolean isFC=!timeStr.endsWith("00000")&&!timeStr.endsWith("00001");//是否是预测数据
        date1=DateUtils.transformationDate(fullDateStr);
        int hour=Integer.parseInt(hourStr);
        int min=Integer.parseInt(minStr);

        Calendar ca=Calendar.getInstance();
        ca.setTime(date1);
        ca.add(Calendar.HOUR_OF_DAY,hour);
        ca.add(Calendar.MINUTE, min);
        date1=ca.getTime();

        if(isFC){//获取预测数据
            String addHourStr = fcHourStr.substring(0,3);//添加到截止时间(时)
            String IntervalStr  = fcHourStr.substring(3,5);//间隔时间（时）

            int addHour = Integer.parseInt(addHourStr);
            int Interval;

            if(IntervalStr.equals("00")){
                Interval = 24;
            }else{
                Interval = Integer.parseInt(IntervalStr);
            }

            //先计算结束时间
            date3 = date1;
            Calendar ca2=Calendar.getInstance();
            ca2.setTime(date3);
            ca2.add(Calendar.HOUR_OF_DAY,addHour);
            date3=ca2.getTime();
            //再计算开始时间
            Calendar ca3=Calendar.getInstance();
            ca3.setTime(date3);
            ca3.add(Calendar.HOUR_OF_DAY,0-Interval);
            date2=ca3.getTime();

        }else{//获取实时数据(实时数据的起止时间和结束时间 与数据时间相同)
            date2 =date1;
            date3 =date1;

        }
        if(date1!=null){
            dates.add(date1);
        }
        if(date2!=null){
            dates.add(date2);
        }
        if(date3!=null){
            dates.add(date3);
        }
        return dates;


    }

    /**
     * 字符串转日期
     * @param dateString
     * @return
     */
    public static Date transformationDate(String dateString){
        if(dateString==null){
            return null;
        }
        //yyyy-MM-dd HH:mm:ss
        List list=new ArrayList();
        list.add(new DateFormat("yyyyMMdd","^\\d{4}\\d{2}\\d{2}$"));
        list.add(new DateFormat("yyyyMMddHHmm","^\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}$"));
        list.add(new DateFormat("yyyyMMddHHmmss","^\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}\\d{2}$"));
        list.add(new DateFormat("yyyyMMddHHmmss","^\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}\\d{2}\\d+$"));

        list.add(new DateFormat("yyyy-MM-dd","^\\d{4}-\\d{1,2}-\\d{1,2}$"));
        list.add(new DateFormat("yyyy-MM-dd HH:mm","^\\d{4}-\\d{1,2}-\\d{1,2}\\s+\\d{1,2}:\\d{1,2}$"));
        list.add(new DateFormat("yyyyMMdd HH:mm","^\\d{4}\\d{1,2}\\d{1,2}\\s+\\d{1,2}:\\d{1,2}$"));
        list.add(new DateFormat("yyyy-MM-dd HH:mm:ss","^\\d{4}-\\d{1,2}-\\d{1,2}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}$"));

        list.add(new DateFormat("yyyy/MM/dd","^\\d{4}/\\d{1,2}/\\d{1,2}$"));
        list.add(new DateFormat("yyyy/MM/dd HH:mm","^\\d{4}/\\d{1,2}/\\d{1,2}\\s+\\d{1,2}:\\d{1,2}$"));
        list.add(new DateFormat("yyyy/MM/dd HH:mm:ss","^\\d{4}/\\d{1,2}/\\d{1,2}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}$"));

        Date date=null;
        boolean isMatch=false;
        for(int i=0;i<list.size();i++){
            DateFormat df=(DateFormat)list.get(i);
            Pattern pattern=Pattern.compile(df.getRegStr());
            Matcher matcher = pattern.matcher(dateString);
            if(matcher.matches()){
                isMatch=true;
                SimpleDateFormat formatTime = new SimpleDateFormat(df.getFormatStr());
                try {
                    date= formatTime.parse(dateString);
                } catch (ParseException e) {
                    System.out.println("转换日期错误："+dateString+"("+df.getFormatStr()+")");
                }
            }
        }
        if(!isMatch){
            System.out.println("不支持的日期格式："+dateString);
        }
        return date;
    }

    public static void main(String[] args) throws ParseException {
        getDatasFromNMCImageName("SEVP_NMC_STFC_SFER_EDA_ACHN_L88_PB_20171010080000000.jpg");
    }


}
