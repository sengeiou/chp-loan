package com.creditharmony.loan.borrow.sms.entity;

import java.util.Date;

import com.creditharmony.loan.borrow.pushdata.util.ComUtils;
import com.creditharmony.loan.borrow.pushdata.util.Constants;



/**
 * 短信待发送列表生成唯一标识
 * @Class Name OnlyFlag
 * @author 武文涛
 * @Create In 2016年1月19日
 */
public class OnlyFlag {
	// 该类中的常量作为固定值存在，无论后续开发或者维护该常量都不可变，所以写入类中防止修改。
    private static long[] ls = new long[3000];
    private static int li = 0;
    
    /**
     * 短信待发送列表生成唯一标识
     * 2016年1月19日
     * By 武文涛
     * @param none
     * @return none
     */
    public synchronized static long getPK(){
        long lo = getpk();
        for (int i = 0; i < 3000; i++){
            long lt = ls[i];
            if (lt == lo){
                lo = getPK();
                break;
            }
        }
        ls[li] = lo;
        li++;
        if (li == 3000){
          li = 0;
        }
        return lo;
    }

    /**
     * 短信待发送列表生成唯一标识
     * 2016年1月19日
     * By 武文涛
     * @param none
     * @return none
     */
    private static long getpk(){
        String a = (String.valueOf(System.currentTimeMillis())).substring(3, 13);
        String d = (String.valueOf(Math.random())).substring(2, 9);
        return Long.parseLong(a + d);
    }

	public static String getOnlyFlag() {
		String dateNow = ComUtils.dateToString(new Date(), Constants.DATAFORMAT[11]);
    	String radom =ComUtils.randomString(3);
    	return dateNow+radom;
	}
}
