package  com.hdsx.lh.maptiles.utils;

import java.io.Serializable;

/**
 * Created by 黄聪 on 2017/11/2.
 */
public class DateFormat implements Serializable{

    private String regStr;

    private String formatStr;

    public DateFormat(String formatStr,String regStr){
        this.regStr=regStr;//匹配日期的正则表达式
        this.formatStr=formatStr;//转换的格式
    }

    public String getRegStr() {
        return regStr;
    }

    public void setRegStr(String regStr) {
        this.regStr = regStr;
    }

    public String getFormatStr() {
        return formatStr;
    }

    public void setFormatStr(String formatStr) {
        this.formatStr = formatStr;
    }

}
