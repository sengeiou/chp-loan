/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.test.contractRegexMattchTest.java
 * @Create By 张灏
 * @Create In 2016年5月4日 上午9:48:51
 */
package com.creditharmony.loan.test.contract;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.creditharmony.loan.test.base.AbstractTestCase;

/**
 * @Class Name RegexMattchTest
 * @author 张灏
 * @Create In 2016年5月4日
 */
public class RegexMattchTest extends AbstractTestCase{

    @Test
    public void regexMatch(){
        String[] targetArray = {"23432.2343","werew","32432","adf23","233df","2343.2se"};
        Pattern p = Pattern.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
        Matcher m = null; 
       for(String s:targetArray){
           m = p.matcher(s);  
           System.out.println(s +": 匹配结果" +m.matches());
       }
    }
}
