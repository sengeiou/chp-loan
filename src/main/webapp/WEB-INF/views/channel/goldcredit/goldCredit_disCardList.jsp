<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html> 
<head> 

<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script src="${context}/js/grant/disCard.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/channel/goldcredit/popuplayer.js"></script>
<meta name="decorator" content="default"/>
	<title>分配卡号</title>	
<script type="text/javascript">
$(document).ready(
		function() {
			loan.getstorelsit("storeName","storeOrgId");
			loan.initCity("addrProvice", "addrCity",null);
			// 批量处理，批量进行分配卡号
			$("#disCardBtnn").click(function(){
				var checkVal=null,
				frozenFlag = false ,contractCode = "";
				if($(":input[name='checkBoxItem1']:checked").length==0){
					art.dialog.alert('请选择要进行操作的数据!');
			           return;					
				}else{
						$(":input[name='checkBoxItem1']:checked").each(function(){	                		
		            		if(checkVal!=null)
		            		{
		            			checkVal+=","+$(this).val();
		            		}else{
		            			checkVal=$(this).val();
		            		}
		            		if ($(this).attr("frozenFlag") == '1'){
		            			frozenFlag = true;
		            			contractCode = $(this).attr("contractCode");
		            			return false;
		            		}
		            	});
						if (!frozenFlag)
							window.location.href=ctx +"/channel/goldcredit/discard/disCardJump?checkVal="+checkVal;
						else 
							art.dialog.alert("合同编号为"+contractCode+"的客户门店已申请冻结!");
					}
			});
			var num = 0,money = 0.00;
			//计算金额
			$("input[name='checkBoxItem1']").click(function(){
				var cumulativeLoan  = parseFloat($("#hiddenGrant").val(),10);
				var totalNum = parseFloat($("#hiddenNum").val(),10);
				var hiddeNum = 0,hiddenMoney = 0.00;
				if ($(this).is(":checked")) {
					hiddenMoney = cumulativeLoan += parseFloat($(this).attr("grantAmount"));
					hiddeNum = totalNum += 1;
				} else {
					if ($("input[name='checkBoxItem1']:checked").length == 0){
						cumulativeLoan = money;
						totalNum = num;
					} else {
						hiddenMoney = cumulativeLoan -= parseFloat($(this).attr("grantAmount"));
						hiddeNum = totalNum -= 1 ;
					}
				}
				$("#checkAllBox").prop("checked",($("input[name='checkBoxItem1']").length == $("input[name='checkBoxItem1']:checked").length ? true : false));
				
				$("#totalGrantMoney").text(fmoney(cumulativeLoan,2));
				$("#hiddenGrant").val(hiddenMoney);
				$("#totalNum").text(totalNum);
				$("#hiddenNum").val(hiddeNum);
			});
			$("#reason").on("change",function() {
				if ($(this).val() == '9')
					$("#other").show();
				else 
					$("#other").hide();
			});
		});
		
		//点击全选反选选择框
		function allOrNo(){
			var $checkBoxItem = $("input[name='checkBoxItem1']");
			var cumulativeLoan = 0.00,
			totalNum = 0;
			//全选按钮选中
			if ($("#checkAllBox").is(":checked")) {
				$checkBoxItem.prop("checked","checked");
				$checkBoxItem.each(function(){
					var totalGrantMoney = parseFloat($(this).attr("grantAmount"));
					cumulativeLoan += totalGrantMoney;
					totalNum++;
				});
			}else{
				$checkBoxItem.prop("checked",false);
				cumulativeLoan = 0.00;
				totalNum = 0;
			} 
			$("#hiddenGrant").val(cumulativeLoan);
			$("#hiddenNum").val(totalNum);
			$("#totalGrantMoney").text(fmoney(cumulativeLoan,2));
			$("#totalNum").text(totalNum);
		}
			 // 拒绝确定
			function returnBackSure(){
				// 获得选中的退回原因，退回到合同审核，获得的为name
				// 批量处理
				if($(":input[name='checkBoxItem1']:checked").length==0){
					art.dialog.alert('请选择要进行操作的数据!');
			           return;					
					}else{
						var isSplit = false;
						$(":input[name='checkBoxItem1']:checked").each(function(){	                		
		            		if($(this).attr("issplit")!='0'){
		            			 art.dialog.alert('选择的数据中含有拆分数据，不能金信退回，合同号：'+$(this).attr("contractCode"));
		            			 isSplit = true;
		    					 return;
		            		}
		            	});
						if(!isSplit){
							$('#back_div1').modal('toggle').css({
								width : '90%',
								"margin-left" : (($(document).width() -  $("#back_div1").css("width").replace("px", "")) / 2),
								"margin-top" : (($(document).height() -  $("#back_div1").css("height").replace("px", "")) / 2)
							});
						}
					}
			};
	
		
		function returnBack(){
			 var grantSureBackReason=$("#reason").attr("selected","selected").val();
			 var checkVal=null;
			 var reason = "";
			 if (grantSureBackReason == 9 && $.trim($("#textarea").val()).length == 0){
				 art.dialog.alert("金信退回原因不能够为空！");
				 return false;
			 }
			 if (grantSureBackReason == 9 && $.trim($("#textarea").val()).length != 0){
				 reason = $("#textarea").val();
			 } else {
				 reason = $("#reason").find("option:selected").text();
			 }
			 //解决中文乱码问题
			 reason = decodeURIComponent(reason,true); 
			 reason = encodeURI(encodeURI(reason));
			$(":input[name='checkBoxItem1']:checked").each(function(){	                		
        		if(checkVal!=null)
        		{
        			checkVal+=";"+$(this).val();
        		}else{
        			checkVal=$(this).val();
        		}
        		checkVal+= ","+$(this).attr("wobNum")+","+$(this).attr("flowName")+","+$(this).attr("token")+","+$(this).attr("stepName")+","+$(this).attr("applyId");
        	});
		
			$.ajax({
				type : 'post',
				url : ctx + '/channel/goldcredit/discard/returnBackTo?checkVal='+checkVal+'&grantSureBackReasonCode='+$("#reason").val()+"&grantSureBackReason="+reason,
				//data : WorkItemView,
				success : function(data) {
					if(data!=null){
						art.dialog.alert('退回成功!',function (){
							window.location=ctx+'/channel/goldcredit/discard/getGCDiscardInfo';
						});
					}else{
						art.dialog.alert('退回失败!');
					}
					
				}
			});
		}
			 // 拒绝确定
			 function refuseBackSure(){
				// 获得选中的退回原因，退回到合同审核，获得的为name
				var grantSureBackReason=$("#reasons").val();
				
				// 批量处理
					if($(":input[name='checkBoxItem1']:checked").length==0){
						art.dialog.alert('请选择要进行操作的数据!');
				           return;					
						}else{
							var isSplit = false;
							$(":input[name='checkBoxItem1']:checked").each(function(){	                		
			            		if($(this).attr("issplit")!='0'){
			            			 art.dialog.alert('选择的数据中含有拆分数据，不能金信拒绝，合同号：'+$(this).attr("contractCode"));
			            			 isSplit = true;
			    					 return;
			            		}
			            	});
							if(!isSplit){
								 $('#back_div').modal('toggle').css({
										width : '90%',
										"margin-left" : (($(document).width() -  $("#back_div").css("width").replace("px", "")) / 2),
										"margin-top" : (($(document).height() -  $("#back_div").css("height").replace("px", "")) / 2)
									});
							}
						}
			};
			 
			 function refuseBack(){
				 var checkVal=null;
				 var textVal = $("#reasons").val();
				 if ($.trim(textVal).length == 0){
					 art.dialog.alert('拒绝原因不能够为空!');
					 return false;
				 } else if ($.trim(textVal).length > 200) {
					 art.dialog.alert('输入的字数多余规定的字数,无法继续操作!');
					 return false;
				 }
				 $(":input[name='checkBoxItem1']:checked").each(function(){	                		
	            		if(checkVal!=null)
	            		{
	            			checkVal+=";"+$(this).val();
	            		}else{
	            			checkVal=$(this).val();
	            		}
	            		checkVal+= ","+$(this).attr("wobNum")+","+$(this).attr("flowName")+","+$(this).attr("token")+","+$(this).attr("stepName")+","+$(this).attr("applyId");
	            	});
					$.ajax({
						type : 'post',
						url : ctx + '/channel/goldcredit/discard/refuseBackTo?checkVal='+checkVal+'&grantSureBackReason='+textVal,
						//data : WorkItemView,
						success : function(data) {
							if(data!=null)
								art.dialog.alert('退回成功!',function (){
									window.location=ctx+'/channel/goldcredit/discard/getGCDiscardInfo';
								});
							else
								art.dialog.alert('退回失败!');
						}
					});
			 }
//点击办理
function dealBtn(t){
	var checkVal=$(t).val();
	if ($(t).attr("frozenFlag") == '1'){
		art.dialog.alert('合同编号为'+$(t).attr("contractCode")+"的客户门店已申请冻结!");
	}else
		window.location.href=ctx +"/channel/goldcredit/discard/disCardJump?checkVal="+checkVal;

}
$(function (){
	//驳回申请
	$("#batchReject").click(function () {
		var params=null;
		var lengths = $(":input[name='checkBoxItem1']:checked").length;
		if (lengths == 0) {
			art.dialog.alert("请选择需要操作的数据信息");
			return false;
		}
		 $(":input[name='checkBoxItem1']:checked").each(function(){	                		
       		if ($(this).attr("frozenFlag") == '1'){
				if(params!=null) {
	       			params+=";"+$(this).val();
	       		}else {
	       			params=$(this).val();
	       		}
	       		params+= ","+$(this).attr("wobNum")+","+$(this).attr("flowName")+","+$(this).attr("token")+","+$(this).attr("stepName")+","+$(this).attr("applyId");
       		}
       	});
		 if (!params) {
			 art.dialog.alert("选中的数据中无冻结数据,无需驳回申请！");
			 return false;
		 }
		art.dialog({
			title : '消息',
			content : '确认驳回申请？',
			opacity : .1,
			lock : true,
			ok : function() {
				$.ajax({
					type : 'post',
					url : ctx+'/channel/goldcredit/discard/disCardRejectBack?params='+encodeURI(params),
					cache : false,
					dataType : 'json',
					async : false,
					success : function(result) {
						if(result){
							art.dialog.alert("驳回申请成功",function (){
								window.location.href=ctx+'/channel/goldcredit/discard/getGCDiscardInfo';
							});
							// 跳转到列表页面
						}else{
							art.dialog.alert("驳回申请失败");
							// 跳转到列表页面
						}
						
					},
					error : function() {
						art.dialog.alert('请求异常');
					}
				});
			},
			cancel : true
		});
		
	});
	 $("#reasons").keyup(function (){
		checklength($(this)) ;
	}); 
	 $.popuplayer(".edit");
});
//金额精确到小数点后2位
function fmoney(s, n) {  
      n = n > 0 && n <= 20 ? n : 2;  
      s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
      var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
      t = "";  
      for (i = 0; i < l.length; i++) {  
          t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
      }  
      return t.split("").reverse().join("") + "." + r;  
  } 
  //
function checklength(obj) {
	  //debugger;
	   var max = obj.attr("maxlength");
	   if(max == null || max == "" || max == undefined) {
	     return;
	   }
	   if(obj.val().length > max) {
	     art.dialog.alert("请不要超过最大长度:" + max);
	     obj.val(obj.val().substring(0,(max-1)));
	     return;
	   }
	  $("#fontSize").text(200-parseInt($.trim(obj.val()).length)+'字以内');
	 }
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#disCardForm").attr("action","${ctx }/channel/goldcredit/discard/getGCDiscardInfo");
	$("#disCardForm").submit();
	return false;
}
</script>
</head>
<body>

    <div style="position:fixed;top:0;left:0;right:0;">
	<div class="control-group" >
	
      <form:form id="disCardForm" action="${ctx }/channel/goldcredit/discard/getGCDiscardInfo" modelAttribute="LoanFlowQueryParam" method="post">
	      <input type="hidden" id="userCode">
	      <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
		  <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
                <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
                <td><label class="lab">提交日期：</label><input id="grantDate" name="submissionDate" 
                 value="${LoanFlowQueryParam.submissionDate}"
                     type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>
                </td>
            </tr>
            <tr>
                
                <td >
                <label class="lab">所属城市：</label><form:select class="select180" style="width:110px" path="provinceCode" id="addrProvice">
                <form:option value="">选择省份</form:option>
                <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <form:option value="${item.areaCode }">${item.areaName}</form:option>
	            </c:forEach>
                </form:select>-<form:select class="select180" style="width:110px" path="cityCode" id="addrCity">
                <form:option value="">请选择</form:option>
                <c:forEach var="item" items="${citiesList}" varStatus="status">
		             <form:option value="${item.areaCode }">${item.areaName}</form:option>
	            </c:forEach>
                </form:select>
                </td>
                 <td><label class="lab">放款金额：</label><input type="text" class="input_text70" name="lendingMoneyStart" id="lendingMoneyStart" value = "<fmt:formatNumber pattern="###.###" type='number' value = '${LoanFlowQueryParam.lendingMoneyStart}'/>"/>-<input type="text" class="input_text70" name="lendingMoneyEnd" id="lendingMoneyEnd"  value = "<fmt:formatNumber pattern="###.###" type='number' value = '${LoanFlowQueryParam.lendingMoneyEnd}'/>"/></td>
				 
				 <td><label class="lab">门店：</label><input type="text" id="storeName" name="storeName" value="${LoanFlowQueryParam.storeName }" readonly="readonly" class="input_text178" /> 
					 <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				    <input type="hidden" name="storeOrgIds" value="${LoanFlowQueryParam.storeOrgId }" id="storeOrgId" />
				 </td>
            </tr>
			
            <tr id="T1" style="display:none">
            <td><label class="lab">是否加急：</label><form:select class="select180" path="urgentFlag">
				 <form:option value="">请选择</form:option>
				 <c:forEach items="${fns:getDictList('jk_urgent_flag')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
				 </form:select></td>
             <td><label class="lab">提交批次：</label><form:select class="select180" path="submissionBatch">
				 <form:option value="">请选择</form:option>
				 <c:forEach items="${submitBatchList}" var="submitBatch">
								<form:option value="${submitBatch}">${submitBatch}</form:option>
				 </c:forEach>
				 </form:select></td>
				 <td><label class="lab">借款状态：</label><form:select class="select180" path="loanStatusCode">
					<form:option value="">请选择</form:option>
					<form:option value="2">门店申请冻结</form:option>
					</form:select>
				</td>
            </tr> 
        </table>
        <div class="tright pr30 pb5"><input class="btn btn-primary" type="submit" value="搜索" id="searchBtn"></input>
        <button class="btn btn-primary" id="clearBtn">清除</button>
        <div style="float: left; margin-left: 45%; padding-top: 10px">
	      <img src="${ctxStatic }/images/up.png" id="showMore"></img>
	   </div>
		</div>
		</form:form>
		</div>
		<p class="mb5">
    	<button class="btn btn-small" id="disCardBtnn">批量分配</button>
    	<button  class="btn btn-small"  onclick="refuseBackSure()" >金信拒绝</button>
    	<button class="btn btn-small" id="batchReject">驳回申请</button>
        <button  class="btn btn-small" onclick="returnBackSure()">金信退回</button>
    	&nbsp;&nbsp;<label class="lab1">放款累计金额：</label><label class="red" id="totalGrantMoney">0.00</label>元     	
    	&nbsp;&nbsp;<label class="lab1">放款总笔数：</label><label class="red" id="totalNum">0</label>笔</p>
    	<input type="hidden" id="hiddenGrant" value="0.00">
    	<input type="hidden" id="hiddenNum" value="0">
    
    	 <sys:columnCtrl pageToken="grantDisCardList"></sys:columnCtrl>
    	<div class="box5">
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
          <tr>
            <th><input type="checkbox" id="checkAllBox" onclick="allOrNo()"/></th>
            <th>合同编号</th>
            <th>门店名称</th>
            <th>客户姓名</th>
            <th>借款类型</th>
            <th>借款产品</th>
            <th>合同金额</th>
            <th>放款金额</th>
            <th>批借期限(月)</th>
            <th>状态</th>
            <th>渠道</th>
            <th>模式</th>
            <th>加急标识</th>
            <th>提交批次</th>
            <th>提交日期</th>
            <th>操作</th>
         </tr>
         </thead>
		<tbody>
         <c:if test="${ workItems!=null && fn:length(workItems)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${workItems}" var="item" varStatus="status">
          <c:set var="index" value="${index+1}" />
             <tr <c:if test="${item.frozenFlag == '1' }">style = 'color:red;'</c:if>>             
             	<td><input type="checkbox" name="checkBoxItem1" loanMarking='${item.channelName}'
               grantAmount='${item.lendingMoney}' wobNum = "" flowName = ""
               token = "" stepName = "" issplit="${item.issplit}" 
              value='${item.loanCode}' frozenFlag = "${item.frozenFlag}" contractCode = "${item.contractCode}" applyId = '${item.applyId}'/>
              ${status.count}
              </td>
             <td>${item.contractCode}</td>
             <td>${item.storeName}</td>
             <td>${item.customerName}</td>
             <td>信借</td>
             <td>${item.replyProductName}</td>
             <td><fmt:formatNumber value='${item.contractMoney}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.lendingMoney}' pattern="#,#00.00"/></td>
             <td>${item.replyMonth}</td>
             <td>${item.loanStatusName}
             	<c:if test="${item.frozenFlag == '1' }">(门店申请冻结)</c:if>
             </td>
             <td>${item.channelName}</td>
             <td></td>
             <td>
              <c:if test="${item.urgentFlag=='1'}">
                    	<span style="color:red">加急</span>
             		</c:if>
             </td>
             <td>${item.submissionBatch}</td>
             <td>${fn:substring(item.submissionDate,0,10)}</td> 
             <td style="position:relative">
				<input type="button" class="btn btn_edit edit" value="操作"/>
				 <div class="alertset" style="display:none">
				 	<button class="btn_edit" name="dealBtn1" onclick="dealBtn(this)" value='${item.loanCode}'
             			frozenFlag = '${item.frozenFlag}' contractCode = "${item.contractCode}">办理</button>
				 </div>
			</td>          
         </tr> 
         
         </c:forEach>  
            
         </c:if>
         
         <c:if test="${ workItems==null || fn:length(workItems)==0}">
           <tr><td colspan="17" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
    </table>
    <c:if test="${workItems!=null || fn:length(workItems)>0}">
		<div class="page">${gcDiscardPage}</div>
	</c:if>
	</div>
</div>
<div  id="back_div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role="document"">
				<div class="modal-content">
				<div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<label class="lab">拒绝原因：</label>
					 </div>
				 <div class="modal-body">
				 <textarea rows="8" cols="40" maxlength="200" id="reasons"></textarea><span style = "color:#cccccc;" id =  "fontSize">200字以内</span>
				 </div>
				 <div class="modal-footer" style="text-align:right">
				 <button onclick="refuseBack()" class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >确定</button>
				 <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
				 </div>
				</div>
				</div>

</div>
<div  id="back_div1" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role="document"">
				<div class="modal-content">
				<div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<label class="lab">退回原因：</label>
					 </div>
				 <div class="modal-body">
				 <select id="reason" class = "select180">
				 <c:forEach items="${fns:getDictList('jk_chk_back_reason')}" var="card_type">
							<option value="${card_type.value}">${card_type.label}</option>
				 </c:forEach>
				 </select>
				 <div id="other" style="display:none">
                    <td align="left"><label>其他退回原因</label></td>
                    <td align="left"><textarea  rows="3" cols="30" style='font-family:"Microsoft YaHei";' id = "textarea"></textarea></td>
                </div>
				 </div>
				 <div class="modal-footer" style="text-align:right">
				 <button id="backSure" onclick="returnBack()" class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >确定</button>
				 <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
				 </div>
				</div>
				</div>

</div>
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
</body>
</html>