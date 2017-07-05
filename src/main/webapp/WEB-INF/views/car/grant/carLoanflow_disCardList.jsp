<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${context}/js/car/grant/carDisCard.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<meta name="decorator" content="default"/>
<title>分配卡号列表</title>	
<script type="text/javascript">
$(document).ready(function(){
	

	 $('#searchBtn').bind('click',function(){
			$('#disCardForm').submit(); 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#disCardForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("select").trigger("change");
		 
		 $("#cityCode").val("");
		 $("#bankId").val("");
		 
	 });
	 $('#showMore').bind('click',function(){
		show(); 
		 
	 });
	 
	 	disCard.initCity("addrProvice", "addrCity",null);
/* 	 	disCardArea.initCity("addrProvice", "addrCity",null, $("#cityCode")
						.attr("value"), null);  */
		
		
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 
	 // 当退回原因为其他时，备注前增加  红星
	  $('#reason').bind('change',function(){
		 var dictBackMestype=$("#reason").attr("selected","selected").val();
		 if(dictBackMestype != "9")
		 {
			 $('#remarkSpan').show();
		 }else{
			 $('#remarkSpan').hide();
		 }
	 }); 
/*   $(".ViewImageData").click(function(){
		art.dialog.open('http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111', {
			title: "客户影像资料",	
			top: 80,
			lock: true,
			    width: 1000,
			    height: 450,
		}, false);	
	});

 */});
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#disCardForm").attr("action", "${ctx}/car/carLoanWorkItems/fetchTaskItems/balanceCommissioner");
	$("#disCardForm").submit();
	return false;
} 
function getImageUrl(loancode){
	$.ajax({
 		type:'post',
		url :ctx + '/carpaperless/confirminfo/getImageUrl',
		data:{
			'loancode':loancode,
		},
		cache:false,
		dataType:'text',
		async:false,
		success:function(result) {
			if(""!=result&&null!=result){
				art.dialog.open(result, {
					title: "客户影像资料",	
					top: 80,
					lock: true,
					    width: 1000,
					    height: 450,
				}, false);	

			}
		},
		error : function() {
			art.dialog.alert('请求异常！', '提示信息');
		}
   	});
}
</script>
</head>
<body>

    <div style="position:fixed;top:0;left:0;right:0;">
	<div class="control-group" >
	
      <form:form id="disCardForm" action="${ctx }/car/carLoanWorkItems/fetchTaskItems/balanceCommissioner" modelAttribute="carLoanFlowQueryParam" method="post">
	  <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
      <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
      <input name="menuId" type="hidden" value="${param.menuId}" />
      <input type="hidden" value="${carLoanFlowQueryParam.addrCity }" id="cityCode">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="customerName" class="input_text178"/>
             </td>
             <td>			
				<label class="lab">门店：</label> 
				<form:input id="txtStore" path="storeName" class="txt date input_text178" readonly="true"/> 
				<i id="selectStoresBtn"
				class="icon-search" style="cursor: pointer;"></i>
				<input type="hidden" id="hdStore">
		     </td>
			  <td><sys:multipleBank bankClick="selectBankBtn"
							bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
						<label class="lab">开户行：</label> <form:input type="text"
							id="search_applyBankName" path="cardBankName"
							class="input_text178"></form:input> <i
						id="selectBankBtn" class="icon-search" style="cursor: pointer;"></i>
						<form:input type="hidden"
							id="bankId" path="cardBank"
							class="input_text178"></form:input>
			   </td>
           
         </tr>
         <tr>
         	<td><label class="lab">合同编号：</label>
			  <form:input path="contractCode"  class="input_text178"/></td>

               <td><label class="lab">所属城市：</label>
               <form:select class="select180" style="width:110px" path="addrProvince" id="addrProvice">
                <form:option value="">选择省份</form:option>
                <c:forEach var="item" items="${provinceList}" varStatus="status">
		   
		             <form:option value="${item.areaName }" areaCode="${item.areaCode }" >${item.areaName}</form:option>
	            </c:forEach>
                </form:select>-<form:select class="select180" style="width:110px" path="addrCity" id="addrCity">
                <form:option value="" >请选择</form:option>
                </form:select>
				</td>
				<td><label class="lab">渠道：</label>
		             	<form:select id="loanFlag" path="loanFlag" class="select180">
		                   <option value="">请选择</option>
			                   <c:forEach items="${fns:getDictList('jk_car_throuth_flag')}" var="loan_Flag">
			                   		<c:if test = "${loan_Flag.value!=1}">
			                   			<form:option value="${loan_Flag.value}">${loan_Flag.label}</form:option>
			                   		</c:if>	
			                   </c:forEach>
				         </form:select>
		             </td>
         </tr>
			
        </table>
        <div class="tright pr30 pb5">
        	<input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
		</div>
		</form:form>
		</div>
		
				<p class="mb5">
		&nbsp;&nbsp;&nbsp;
		<input type="checkbox" id="checkAll"/>  全选
    	<button class="btn btn-small" id="disCardBtn">批量分配</button>
    	<button class="btn btn-small" id="expDisCardBtn">导出excel</button>
 
    	&nbsp;&nbsp;<label class="lab1">放款累计金额：</label><label class="red" id="totalGrantMoney"><fmt:formatNumber value='${totalGrantMoney}' pattern="#,##0.00"/></label>元</p>
    	<input type="hidden" id="hiddenTotalGrant" value='${totalGrantMoney}'></input>
    	
    
    	 <sys:columnCtrl pageToken="carDisCardList"></sys:columnCtrl>
    	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
          <tr>
            <th></th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>门店名称</th>
            <th>所属省</th>
            <th>所属市</th>
            <th>借款产品</th>
            <th>合同金额</th>
            <th>批借期限(天)</th>
            <th>开户行</th>
            <th>划扣金额</th>
            <th>总费率</th>
            <th>借款状态</th>
            <th>是否电销</th>
            <th>渠道</th>
            <th>操作</th>
 
         </tr>
         </thead>
         	<tbody>
         	<c:if test="${ itemList!=null && fn:length(itemList)>0}">
       		<c:forEach items="${itemList}" var="item" varStatus="status"> 
             <tr>
              <td><input type="checkbox" name="checkBoxItem" grantAmount='${item.contractAmount}'
          		value='${item.applyId}'/>
              </td>
             <td>${item.contractCode}</td>
             <td>${item.customerName}</td>
             <td>${item.storeName}</td>
             <td>${item.addrProvince}</td>
             <td>${item.addrCity}</td>
             <td>${item.auditBorrowProductName}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,##0.00"/></td>
             <td>${item.auditLoanMonths}</td>
             <td>${item.cardBank}</td>
             <td><fmt:formatNumber value='${item.deductsAmount}' pattern="#,##0.00"/></td>
             <td><fmt:formatNumber value='${item.grossRate}' pattern="#,##0.00"/><c:if test="${item.grossRate != null}">%</c:if></td>
             <td>${item.dictStatus} </td>
             <td>${item.loanIsPhone}</td>
             <td>${item.loanFlag}</td>
             <td>
	             <button class="btn_edit" name="dealBtn" value='${item.applyId}'>分配</button>
	             <!-- <input type="button" value="查看" onclick="showCarLoanInfo('${item.loanCode}')" class="btn_edit"></input> -->
	             <button class="btn_edit" name="historyBtn" value='${item.applyId}' onclick="showCarLoanHis('${item.loanCode}')">历史</button>
	             <input type="button" class="btn_edit ViewImageData" value="查看影像" onclick="getImageUrl('${item.loanCode}')"/>
	             <button  class="btn_edit"  data-target="#back_div" data-toggle="modal" name="back" applyId='${item.applyId}' 
	             	wobNum='${item.wobNum}' dealType='0'token='${item.token}' contractCode='${item.contractCode}' stepName='${item.stepName }' loanCode='${item.loanCode}'>退回</button>
	             
             </td>
         </tr> 
	     </c:forEach>       
	     </c:if>
	     <c:if test="${ itemList==null || fn:length(itemList)==0}">
	        <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
	     </c:if>
     </tbody> 
    </table>
		
		</div>
<div class="pagination">${page}</div>
<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
    
    <!--点击退回时用  -->
  <form id="param">
		<input type="hidden" name="applyId" id="applyId"></input>
	    <input type="hidden" name="contractCode" id="contractCode"></input>	
	    <input type="hidden" name="loanCode" id="loanCode"></input>
  </form>	
 	

	</div>
	<!--退回弹框  -->
    <div  id="back_div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role="document"">
				<div class="modal-content">
				<div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						
						<div class="l">
         					<h4 class="pop_title">退回</h4>
       					</div>
					 </div>
				 <div class="modal-body">
				<form id="reasonValidate">
						    <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%" id="tpTable">
				    <tr>
				    	<td><lable class="lab "><span style="color: red;">*</span>退回原因：</lable>
				    		 <select id="reason" class="select180 required ">
				    				 <option value="">请选择</option>
									<c:forEach items="${fns:getDictLists('6,7,9', 'jk_chk_back_reason')}" var="item">
										<option value="${item.value}">${item.label}</option>
									</c:forEach>
				 			</select>
				 		</td>
				    </tr>
          			 <tr>
						<td><lable class="lab" id="redChange" ><span id="remarkSpan" style="color: red;display:none;" >*</span>备注：</lable>
								<textarea rows="" cols="" class="textarea_refuse"
									id="remark" name="consLoanRemarks" ></textarea>
						</td>
					</tr>
        </table>
				</form>
			
				 </div>
				 <div class="modal-footer">
				 <button id="backSure" class="btn btn-primary"  aria-hidden="true" >确定</button>
				 <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
				 </div>
				</div>
				</div>
				
	</div>
	<!--退回弹框  -->
</body>
</html>