/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.entity.exBackResponse.java
 * @Create By 张灏
 * @Create In 2016年1月6日 下午4:14:50
 */
package com.creditharmony.loan.borrow.contract.entity.ex;

/**
 * 设置退回节点响应
 * @Class Name BackResponse
 * @author 张灏
 * @Create In 2016年1月6日
 */
public class BackResponse {

   private String backResponse;   // 退回相应
   
   private String backMsg;        // 响应名字

public String getBackResponse() {
    return backResponse;
}

public void setBackResponse(String backResponse) {
    this.backResponse = backResponse;
}

public String getBackMsg() {
    return backMsg;
}

public void setBackMsg(String backMsg) {
    this.backMsg = backMsg;
}
   
}
