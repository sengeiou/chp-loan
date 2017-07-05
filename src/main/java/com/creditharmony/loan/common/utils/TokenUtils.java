/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsTokenUtils.java
 * @Create By 张灏
 * @Create In 2016年6月14日 下午7:23:27
 */
package com.creditharmony.loan.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;

/**
 * @Class Name TokenUtils
 * @author 张灏
 * @Create In 2016年6月14日
 */
public class TokenUtils {
      
    public static Map<String,String> createToken(){
        Map<String,String> result = new HashMap<String,String>();
        User user = UserUtils.getUser();
        StringBuffer tokenId = new StringBuffer();
        StringBuffer token = new StringBuffer();
        tokenId.append("token"+user.getId()).append(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss")).
        append(IdGen.uuid());
        token.append(user.getId()).append(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss")).
        append(IdGen.uuid());
        result.put("tokenId", tokenId.toString());
        result.put("token", token.toString());
        return result;
    }
    
    public static void removeToken(String tokenId){
        Session session =  UserUtils.getSession();
        session.removeAttribute(tokenId);
    }
    public static void saveToken(String tokenId,String token){
      Session session =  UserUtils.getSession();
      session.setAttribute(tokenId, token);
    }
    public static boolean validToken(String curTokenId,String curToken){
        if(StringUtils.isEmpty(curToken)){
            return false;
        }
        Session session =  UserUtils.getSession();
        String sessionToken = (String) session.getAttribute(curTokenId); 
        if(curToken.equals(sessionToken)){
            return true;
        }else{
            return false;
        }
    } 
}
