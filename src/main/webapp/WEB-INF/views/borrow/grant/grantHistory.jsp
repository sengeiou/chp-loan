<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>
<script src="${context}/js/grant/grant.js" type="text/javascript"></script>
<meta name="decorator" content="default" />

</head>
<body>
<div style="width:90%;margin:0 auto; margin-top: 30px; border:1px solid #e5c4a1; padding:20px;">
	  <input type="hidden" value='${dictLoanStatus}' id="loanStatus">
      <input type="hidden" value='${dictLoanResult}' id="loanResult"> 
        <div class="r">
            <input type="button" class="btn_sc" id="back"  value="退回"></input>
        </div>
		 <div class="r">
            <label>&nbsp;</label>
        </div>
		 <div class="r">
            <input type="button" class="btn_sc" id="seeBack" value="查看退回原因" ></input>
        </div>
    <table cellspacing="0" cellpadding="0" border="0"  class="table2" width="100%">
    <thead>
    <tr>
        <th>操作时间</th>
        <th>操作人</th>
        <th>操作步骤</th>
        <th>操作结果</th>
        <th>备注</th>
    </tr>
    </thead>
    <tbody>
         <c:if test="${ historyList!=null && fn:length(historyList)>0}">
          <c:forEach items="${historyList}" var="item">
            <tr>
             <td>${item.operateTime}</td>
             <td>${item.operator}</td>
             <td>${item.operateStep}</td>
             <td>${item.operateResult}</td>
             <td>${item.remark} </td>
             
            </tr> 
         </c:forEach>            
         </c:if>
         <c:if test="${ historyList==null || fn:length(historyList)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
       </tbody>
</table>
    <div class="tright pb10 pt10">共4条数据，每页 <input type="text" class="input_text50"> 条数据  1/1<input type="text" class="input_text50"><a href="#"> 跳转</a> </div>
</div>

</body>
</html>