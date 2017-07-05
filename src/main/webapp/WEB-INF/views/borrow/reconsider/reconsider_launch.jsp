<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script src="${context}/js/reconsider/reconsider.js?version=1" type="text/javascript"></script>
<script type="text/javascript">
var imgURL ="${workItem.bv.imageUrl}";
returnParam={
   queue:'HJ_SUB_MANAGER',
   viewName:'reconsider_workItems'	
};
   $(document).ready(function(){
	  var teleFlag='${param.teleFlag}';
	  $('#viewHisBtn').bind('click',function(){
		  showHisByLoanCode($(this).attr('loanCode'));
	  });
	  $('#viewApplyBtn').bind('click',function(){
		  var loanInfoOldOrNewFlag = $('#loanInfoOldOrNewFlag').val();
		  var loanCode=$('#loanCode').val();
		  //信借申请标志为0，为空跳转到旧版，为1跳转到新版
		  if(1==loanInfoOldOrNewFlag){
			  var url=ctx+"/apply/reconsiderApply/getAllApplyInfoNew?viewName=viewApplyInfoNew&loanCode="+loanCode;
		  }else{
			  var url=ctx+"/apply/reconsiderApply/getAllApplyInfo?viewName=viewApplyInfo&loanCode="+loanCode;
		  }
		  art.dialog.open(url, {  
		         id: 'his',
		         title: '申请信息!',
		         lock:true,
		         width:1000,
		     	 height:'90%'
		     },  
		     false);  
	  });
	  $('#retBtn').bind('click',function(){
		  consider.retTaskItemsView(returnParam,teleFlag);
	  });
	  $('#submitBtn').bind('click',function(){
		  consider.dispatchTasks("reconsiderForm");
		  
	  });
	  $('#uploadBtn').bind('click',function(){
		  art.dialog.open(imgURL, {  
			   id: 'fileUpload',
			   title: '资料上传',
			   lock:false,
			   width:700,
			   height:350
			},false); 
		/*   window.open('http://123.124.20.50:9099/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:HUIJINSY1;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111', "window",
					"width=1000,height=500,status=no,scrollbars=yes");  */
	  });
   });
</script>
</head>
<body>

    <div class="pt5 pb5 tright pr30 ">
        <button class="btn btn-small" id="viewHisBtn" loanCode="${workItem.bv.loanInfo.loanCode}" applyId="${workItem.bv.applyId}">查看历史</button>
        <button class="btn btn-small" id="viewApplyBtn" applyId="${workItem.bv.applyId}">查看申请</button>
    </div>
<div class="content control-group pb10">
       <form id="reconsiderForm" method="post">
        <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
       	<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
        <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
        <input type="hidden" value="${workItem.token}" name="token"></input>
        <input type="hidden" value="${workItem.flowId}" name="flowId"></input>
        <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
        <input type="hidden" value="${workItem.bv.applyId}" name="oldApplyId"></input>
        <input type="hidden" value="${workItem.bv.loanInfo.loanInfoOldOrNewFlag }" id="loanInfoOldOrNewFlag" name="loanInfoOldOrNewFlag"/>
        <input type="hidden" name="teleFlag" value="${param.teleFlag}" />
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	        <tr>
                <td width="31%"><label class="lab">姓名：</label>${workItem.bv.loanCustomer.customerName}</td>
                <td width="31%"><label class="lab">身份证号：</label>${workItem.bv.loanCustomer.customerCertNum}</td>
                <td><label class="lab">借款类型：</label>个人信用借款</td>
            </tr>
            <tr>
                <td><label class="lab">借款申请日期：</label><fmt:formatDate value="${workItem.bv.loanInfo.loanApplyTime}" pattern="yyyy-MM-dd"/></td>
                <td><label class="lab">申请额度：</label><fmt:formatNumber value='${workItem.bv.loanInfo.loanApplyAmount}' pattern="#,#00.00"/></td>
                <td><label class="lab">申请分期：</label>${workItem.bv.loanInfo.loanMonths}</td>
            </tr>
            <tr>
               <c:if test="${workItem.bv.loanInfo.loanInfoOldOrNewFlag eq 0}">
               		<td><label class="lab">借款用途：</label>${workItem.bv.loanInfo.dictLoanUserLabel}</td>
                	<td><label class="lab">具体用途：</label>${workItem.bv.loanInfo.realyUse}</td>
                	<td>
	                	<label class="lab">是否有共借人：</label>
	                   	<c:choose>
		                     <c:when test="${workItem.bv.coborrowers==null || fn:length(workItem.bv.coborrowers)==0}">
		                                                                     无
		                     </c:when>
		                     <c:otherwise>
		                                                                    有
		                     </c:otherwise>
	                   	</c:choose>
	                </td>
               </c:if>
               <c:if test="${workItem.bv.loanInfo.loanInfoOldOrNewFlag eq 1}">
               		<td><label class="lab">主要借款用途：</label>${workItem.bv.loanInfo.dictLoanUserLabel}</td>
                	<td><label class="lab">具体用途：</label>${workItem.bv.loanInfo.dictLoanUseNewOther}</td>
                	<td>
	                	<label class="lab">是否有自然人保证人：</label>
	                   	<c:choose>
		                     <c:when test="${workItem.bv.coborrowers==null || fn:length(workItem.bv.coborrowers)==0}">
		                                                                     无
		                     </c:when>
		                     <c:otherwise>
		                                                                    有
		                     </c:otherwise>
	                   	</c:choose>
	                </td>
               </c:if>
            </tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>复议类型：</label>
                <input type="hidden" name="reconsiderApplyEx.customerName" value="${workItem.bv.loanCustomer.customerName}"/>
                 <input type="hidden" name="reconsiderApply.loanCode" id="loanCode" value="${workItem.bv.loanInfo.loanCode}"/>
                 <c:forEach items="${fns:getDictList('jk_reconside_reason')}" var="item">
                 <input type="radio" name="reconsiderApply.dictReconsiderType" class="required" value="${item.value}">${item.label}</input>
                 </c:forEach>
                </td>
            </tr>
            <tr>
                <td colspan="3"><label class="lab"><span class="red">*</span>复议原因：</label>
                   <textarea rows="" cols="" name="reconsiderApply.secondReconsiderMsg" class="textarea_big required"></textarea> 
                </td>
            </tr>
            <tr>
                <td colspan="3"><label class="lab">资料上传：</label>
                   <input type="button" id="uploadBtn" value="资料上传" class="btn btn-small"></input>
                </td>
            </tr>
        </table>
        </div>
          <div class="tright mr10 mt10">
           <input class="btn btn-primary" type="submit" id="submitBtn" value="提交复议"></input>
           <input class="btn btn-primary" type="button" id="retBtn" value="返回"></input>
          </div>
        </form>
</body>
</html>