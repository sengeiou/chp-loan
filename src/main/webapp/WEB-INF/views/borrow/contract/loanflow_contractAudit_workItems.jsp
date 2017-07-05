<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma","no-cache");
%>
<html>
<head>
<!--  -->
<script src="${context}/js/contract/contractAudit.js?version=123"
	type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/contract/channelHandler.js?version=22222" type="text/javascript"></script>
<meta name="decorator" content="default" />
<title>合同审核</title>
<script type="text/javascript">
REDIRECT_URL='/apply/contractAudit/fetchTaskItems';
//待办列表 办理参数
 taskParam={
		taskBtnName:'dealctrAudit',
		taskStep:'合同审核',
		taskView:'loanflow_contractAudit_approve_0'
   }; 
 //待办列表 历史参数
 historyParam={
		 btnName:'history', 
   };
 //待办列表 退回参数
 backParam={
		 btnName:'backBtn',
		 viewName:'_grantBackDialog'
 };
 reloadParam={
	 curForm:'contractAuditForm',
	 flagForm:'flagForm'
  };
  //需要更改的列表属性key-value对
  kvParam={
	 key:'loanFlag',
	 value:' '
   };
 cancelFlagRetVal = '0';
 $(document).ready(function(){
	$('#showMore').bind('click',function(){
		 show();

	});  
	var msg = "${message}";
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	};
	//搜索绑定
    $('#searchBtn').bind(
			'click',
			function() {
				fetchTaskItems('contractAuditForm',
						'flagForm');
			}); 
	//清除按钮绑定
	 $('#clnBtn').bind('click',function(){
		 queryFormClear('contractAuditForm');
		 fetchTaskItems('contractAuditForm','flagForm');
	 });
    $(":input[name='"+taskParam.taskBtnName+"']").each(
			function() {
				$(this).bind(
						'click',
						function() {
							dealTaskNew($(this).attr('applyId'),
									$(this).attr('wobNum'), $(this)
											.attr('dealType'), $(
											this).attr('token'),taskParam.taskStep,'${param.menuId}');
						});
			});
           //待办列表 历史按钮绑定
      $(":input[name='history']").each(function(){
	     $(this).bind('click',function(){
	    	viewAuditHistory($(this).attr('loanCode'));
	     });
	     });
          //待办列表 退回按钮绑定
	  $(":input[name='backBtn']").each(function(){
		     $(this).bind('click',function(){
		    	 workItemBackFlow1('_grantBackDialog',$(this).attr('flowName'),$(this).attr('stepName'),$(this).attr('dealType'),$(this).attr('applyId'),$(this).attr('token'),$('#flowFlag').val(),REDIRECT_URL,$(this).attr('wobNum')); 
		     });
		 });
	  function workItemBackFlow1(viewName,flowName,stepName,dealType,applyId,token,flowFlag,redirectUrl,wobNum){
			
			var url=ctx + '/apply/contractUtil/openGrantDialog?viewName='+viewName+"&flowName="+flowName+"&dealType="+dealType+"&stepName="+stepName+"&applyId="+applyId+"&token="+token+"&wobNum="+wobNum+"&flowFlag="+flowFlag+"&redirectUrl="+redirectUrl+"&menuId="+$("#menuId").val();
			 art.dialog.open(url, {  
		         id: 'back',
		         title: '退回!',
		         lock:true,
		         width:700,
		     	 height:350
		     },  
		     false);
		}  
          
	 //全选绑定
	 $('#checkAll').bind(
				'click',
				function() {
					var checked = false;
					if ($('#checkAll').attr('checked') == 'checked'
							|| $('#checkAll').attr('checked')) {
						checked = true;
					}
					selectAll(checked);
					var $checkBox = $(":input[name='prepareCheckEle']"),count = 0,lendingMoney = 0.00,contractMoney = 0.00;
					if ($(this).is(":checked")) {
						$checkBox.each(function(){
							count += 1;
							lendingMoney += $(this).attr("lendingMoney")*1;
							contractMoney += $(this).attr("contractMoney")*1;
							lendingMoney=parseFloat(lendingMoney);
							contractMoney= parseFloat(contractMoney);
						});
					}
					$('#checkCount').text(count+'笔');
					$('#checkCount1').val(count);
					$('#contractNum').text(fmoney(parseFloat(contractMoney,10),2));
					$('#totalNum').text(fmoney(parseFloat(lendingMoney,10),2));
					
					$('#contractNum1').val(parseFloat(contractMoney,10));
					$('#totalNum1').val(parseFloat(lendingMoney,10));
					if(count == 0 || count == '0'){
						$('#checkCount').text('');
						$('#checkCount1').val(0);
						
						$('#contractNum').text('');
						$('#totalNum').text('');
						
						$('#contractNum1').val(0);
						$('#totalNum1').val(0);
					}
					
		 });
	 $(":input[name='prepareCheckEle']").bind('click',function(){
		 var contractMoney = $('#contractNum1').val();
		 var totalNum = $('#totalNum1').val();
		 
		 var lendingMoney = $(this).attr("lendingMoney")*1;
		 var contMoney = $(this).attr("contractMoney")*1; 
		 if ($(this).is(":checked")) {
			 $("#checkCount").text($("#checkCount1").val()*1+1 + '笔');
			 $("#checkCount1").val($("#checkCount1").val()*1+1);
			 
			 $('#contractNum').text(fmoney(contractMoney*1 + contMoney*1,2));
			 $('#totalNum').text(fmoney(totalNum*1 + lendingMoney*1,2));
			
			 $('#contractNum1').val((contractMoney*1 + contMoney*1).toFixed(2));
			 $('#totalNum1').val((totalNum*1 + lendingMoney*1).toFixed(2));
			 
		 }else{
			if($("#checkCount1").val()*1-1 == 0 ){
					$('#checkCount').text('');
					$('#checkCount1').val(0);
					
					$('#contractNum').text('');
					$('#totalNum').text('');
					
					$('#contractNum1').val(0);
					$('#totalNum1').val(0);
			 }else{
				    $("#checkCount").text($("#checkCount1").val()*1-1 + '笔');
				    $("#checkCount1").val($("#checkCount1").val()*1-1);
				 
				    $('#contractNum').text(fmoney(contractMoney*1 - contMoney*1,2));
				    $('#totalNum').text(fmoney(totalNum*1 - lendingMoney*1,2));
				
				    $('#contractNum1').val((contractMoney*1 - contMoney*1).toFixed(2));
				    $('#totalNum1').val((totalNum*1 - lendingMoney*1).toFixed(2));
			 }
		 }
	 });
       //批量取消标识按钮绑定
     /*   $('#cancelFlagBtn').bind('click',function(){
    	   batchCancelFlag(kvParam,reloadParam,null);
       }); */
    /*   $('#addFlagBtn').bind('click',function(){
    	  channel.openDialog('loanFlag',$('#flowFlag').val(),REDIRECT_URL);
    	 //openAddFlagDialog('loanFlag',$('#flowFlag').val(),REDIRECT_URL); 
      });  */
   /*    $('#cancelJXFlagBtn').bind('click',function(){
    	  batchCancelFlag(kvParam,reloadParam,"金信");
      }); */
      loan.getstorelsit("storeName","storeOrgId");
      $('#rejectApplyBtn').bind("click",function(){
    	  batchRejectApply('frozenFlag');
      });
      $('#backListBtn').bind("click",function(){
      	var param = "";
    		$("input[name='prepareCheckEle']:checked").each(function(){
    			if(param!=""){
    			    param+=";"+$(this).val();
    			}else{
    				param=$(this).val();	
    			}
    		});
    		var fromSubmit = "";
    		if(param == ""){
    			fromSubmit = fetchTaskItemsSubmit('contractAuditForm','flagForm');
    		}
  	    	backListFlow('backList',$('#flowFlag').val(),REDIRECT_URL,$('#menuId').val(),param,fromSubmit);  //传递退回窗口的视图名称
        });
  });
 function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		fetchTaskItems('contractAuditForm',
		'flagForm');
		return false;
}
 $(function (){
	 $("#clear").click(function() {
			art.dialog({
				title : '警告',
				content : '是否确认额度失效？',
				opacity : .1,
				lock : true,
				ok : function() {
					$.post(ctx+"/apply/contractAudit/clearCeilling", 
					function(data){
				          if (data == 'true' || data == true){
				        	  art.dialog.alert('金信额度清零成功！');
				        	  $(".span6").text("");
				        	  $(".span5").text("");
				          }
			         });
				},
				cancel : true
			});

		});
		$("#ceiling").click(function() {
			art.dialog.open(ctx + "/apply/contractAudit/getCeiling",
					{
						title : "设置金信额度",
						width : 350,
						height : 150,
						lock : true,
						opacity : .2,
						okVal : '确定',
						cancelVal : '取消',
						ok : function() {
							var iframe = this.iframe.contentWindow;
							var ceilingVal = iframe.document
									.getElementById('ceiling').value, ceilingHidden = iframe.document
									.getElementById('ceilingHidden').value;

							if (ceilingVal && !/^\d+\,?\.?\d{0,2}$/.test(ceilingVal)) {
								art.dialog.alert('输入的上限额度信息有误,请重试',function() {
									iframe.document.getElementById('ceiling').value = "";
								});
								return false;
							}
							if (!ceilingVal) {
								art.dialog.alert('金信上限额度不能够为空！');
								return false;
							} else if (ceilingHidden) {
								art.dialog.alert('金信上限额度上限已设定,无法设定额度！');
								return false;
							} else {
								$.post(ctx+"/apply/contractAudit/setJXCeiling", 
										{"ceilingMoney":ceilingVal}, function(data){
									          if (data == 'true' || data == true){
									        	  art.dialog.alert('金信上限额度设定成功！');
									        	  $(".span6").text(fmoney(ceilingVal,2));
									        	  $(".span5").text(fmoney(ceilingVal,2));
									          }else
									        	  art.dialog.alert('金信上限额度设定失败！');
								          });
							}
						},
						cancel : true

					}, false);
});
 });
//格式化，保留两个小数点
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
 document.onkeydown = function(e) {
   //捕捉回车事件
   var ev = (typeof event!= 'undefined') ? window.event : e;
	if(ev.keyCode == 13) {
		 $('#searchBtn').click();
	 }
 }
</script>
</head>
<body>
  <div class="control-group">
 	 <!-- 冻结驳回弹出框 -->
		<div id="rejectFrozen" style="display:none">
			<form method="post" action="">
				<table class="table1" cellpadding="0" cellspacing="0" border="0"
					width="100%">
					<tr valign="top">
						<td><label ><span style="color:red;">*</span>驳回理由：</label>
							<textarea id="rejectReason" maxlength="450" cols="55" class="textarea_refuse" ></textarea>
						</td>
					</tr>
				</table>		
			</form>
		</div>
         <form id="flagForm">
    	    <input type="hidden" value="${flowFlag}" id="flowFlag" name="flowFlag"/>
      	  	<input id="menuId" name="menuId" value='${menuId}' type="hidden">
    	  </form>
    	  <input type="hidden" id="batchColl"/>
        <form id="contractAuditForm">
        <input id="pageNo" name="pageNo" type="hidden"
				value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label>
                <input type="text" value="${ctrQryParam.customerName}" name="customerName" class="input_text178"></td>
				<td><label class="lab">合同编号：</label>
				<input type="text" value="${ctrQryParam.contractCode}" name="contractCode" class="input_text178"></td>
                <td><label class="lab">门店：</label>
                     <input type="text" id="storeName" name="storeName" value="${ctrQryParam.storeName}" class="input_text178" readonly="readonly"/> 
					  <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				    <input type="hidden" name="storeOrgId" value="${ctrQryParam.storeOrgId}" id="storeOrgId" />
                </td>
            </tr>
            <tr>
			   <td><label class="lab">身份证号：</label>
			   <input type="text" value="${ctrQryParam.identityCode}" name="identityCode" class="input_text178"></td>
			   <td><label class="lab">模式：</label>
						 <select id="model" name="model" class="select180">
                          <option value="">全部</option>
                          <c:forEach items="${fns:getDictList('jk_loan_model')}" var="item">
                         	  <option value="${item.value}"
					      		 <c:if test="${item.value==ctrQryParam.model}">
					      		   selected=true
					      		 </c:if>
					      		>${item.label}</option>
					       </c:forEach>
				        </select>	
			   </td>
               <td><label class="lab">渠道：</label>
               <select name="channelCode" class="select180">
                  <option value="">全部</option>
                  <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="mark"> 
                  <c:if test="${mark.label!='TG' && mark.label!='XT01' && mark.label!='附条件' }">
                    <option value="${mark.value}" 
                    <c:if test="${ctrQryParam.channelCode==mark.value}"> 
						       selected = true
					</c:if>
                    >${mark.label}</option>
                    </c:if>
                  </c:forEach>
                  </select>
               </td>
            </tr>
			<tr id="T1" style="display:none">
			        <td><label class="lab">产品：</label>
			        <select id="productId" name="replyProductCode" class="select180">
						 <option value="">全部</option>
						<c:forEach var="item" items="${productList}" varStatus="status">
					  		<option value="${item.productCode}" 
					  		  <c:if test="${ctrQryParam.replyProductCode==item.productCode}"> 
						       selected = true
					          </c:if>
					  		>${item.productName}</option>
					    </c:forEach>
     			       </select>
			        </td>
				 	<td><label class="lab">是否追加借：</label>
				 	  <select name="additionalFlag" class="select180">
						   		<option value="">全部</option>
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
							 	  <option value="${item.value}"
									 <c:if test="${ctrQryParam.additionalFlag==item.value}">
									    selected=true
									  </c:if>
									>${item.label}
								  </option>
				        	    </c:forEach>
               	        </select>
				  	</td>
					<td><label class="lab">是否电销：</label>
			           <select name="telesalesFlag" class="select180">
						   <option value="">全部</option>
						   <c:forEach items="${fns:getDictList('yes_no')}" var="rsSrc">
						         <option value="${rsSrc.value}"
									 <c:if test="${ctrQryParam.telesalesFlag==rsSrc.value}">
									    selected=true
									  </c:if>
									>${rsSrc.label}
								  </option>
						    </c:forEach>
						  </select>
				 </td>
				 </tr>
				 <tr id="T2" style="display:none">
				  <td><label class="lab">是否加急：</label>
			        <select name="urgentFlag" class="select180">
						   <option value="">全部</option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="urgentFlag">
						    	<option value="${urgentFlag.value}"
									 <c:if test="${ctrQryParam.urgentFlag==urgentFlag.value}">
									    selected=true
									  </c:if>
									>${urgentFlag.label}
								</option>
		     				</c:forEach>
					 </select>
			  	   </td>
				   <td>
					 <label class="lab">版本号：</label> 
					  <select name="contractVersion" class="select180">
					     <option value="">全部</option>
					      <c:forEach items="${fns:getDictList('jk_versions_search_one')}" var="item">
					          <option value="${item.value}"
									 <c:if test="${ctrQryParam.contractVersion==item.value}">
									    selected=true
									  </c:if>
									>
									${item.label}
								  </option>
					         </c:forEach>
					  </select>
				  </td>
				  <td><label class="lab">借款状态：</label> 
				    <select name="loanStatusCode" class="select180">
					     <option value="">全部</option>
					     <c:forEach items="${fns:getDictList('jk_loan_apply_status')}" var="s">
					      <c:if test="${s.label=='待审核合同'||s.label=='金信退回'||s.label=='放款退回'||s.label=='待款项确认退回' || s.label=='大金融拒绝' || s.label=='大金融退回'}">
					       <option value="${s.value}"
							 <c:if test="${ctrQryParam.loanStatusCode==s.value}">
								  selected=true
							 </c:if>
									>${s.label}
							 </option>
							 </c:if>
					      </c:forEach>
					  </select> 
					 </td>
				 </tr>
				 <tr id="T3" style="display:none">
				  <td><label class="lab">风险等级：</label>
					      <select name="riskLevel" class="select180">
					          <option value=''>全部</option>
			    	        <c:forEach items="${fns:getDictList('jk_loan_risk_level')}" var="item">
					           <option value="${item.value}"
									 <c:if test="${ctrQryParam.riskLevel==item.value}">
									    selected=true
									  </c:if>
									>
									${item.label}
								  </option>
					         </c:forEach>
					     </select>
					     </td>
					   <td><label class="lab">畅捷实名认证结果：</label><select class="select180" name="cjAuthen">
			                 <option value="">请选择</option>
							 <option value="1" <c:if test="${ctrQryParam.cjAuthen=='1'}">selected</c:if>>成功</option>
							 <option value="2" <c:if test="${ctrQryParam.cjAuthen=='2'}">selected</c:if>>失败</option>
		                  </select>
		                </td>
					    <td><label class="lab">畅捷实名认证失败原因：</label>
                        <input type="text" value="${ctrQryParam.cjAuthenFailure}" name="cjAuthenFailure" class="input_text178"></td>
				 </tr>
        </table>
        </form>
        <div class="tright pr30 pb5">
              <button class="btn btn-primary" id="searchBtn">搜索</button>
              <button class="btn btn-primary" id="clnBtn">清除</button>
    
        <div style="float: left; margin-left: 45%; padding-top: 10px">
				  <img src="../../../static/images/up.png" id="showMore"></img>
       </div>
    </div>
        </div>
	<p class="mb5"><input type="checkbox" id="checkAll">全选  
	  已勾选: <label  id="checkCount"></label> &nbsp;<input type="hidden" value="0" id="checkCount1">
	 <!-- <button class="btn btn-small" id="cancelFlagBtn">批量取消标识</button> -->
	<%--  <c:if test="${jkhj_contractaudit_batchcanceljx==null }">
     	<button class="btn btn-small jkhj_contractaudit_batchcanceljx" id="cancelJXFlagBtn">批量取消金信标识</button>
	 </c:if> --%>
	<%--  <c:if test="${jkhj_contractaudit_batchaddflag==null }">
	 	<button class="btn btn-small jkhj_contractaudit_batchaddflag" id="addFlagBtn">批量添加标识</button>
	 </c:if> --%>
	 <c:if test="${jkhj_contractaudit_rejectfrozen==null }">
	 	<button class="btn btn-small jkhj_contractaudit_rejectfrozen" id="rejectApplyBtn">驳回申请</button>
	 </c:if>
	 <c:if test="${jkhj_contractaudit_jxlimit==null }">
	 	<button class="btn btn-small jkhj_contractaudit_jxlimit" id="ceiling">金信额度</button>
	 </c:if>
	 <c:if test="${jkhj_contractaudit_clear==null }">
		 <button class="btn btn-small jkhj_contractaudit_clear" id="clear">清零</button>
	 </c:if>
	 <c:if test="${contract_check_backList==null }">
	 <button class="btn btn-small contract_check_backList" id="backListBtn">批量退回</button>
	 </c:if>
	 <label class="lab1"><span>金信额度:</span><span class = "span6"><fmt:formatNumber value='${ceiling.kinnobu_quota_limit}' pattern="#,##0.00" /></span></label>&nbsp;&nbsp;&nbsp;&nbsp;
	 <label class="lab1"><span>金信剩余额度:</span><span class = "span5"><fmt:formatNumber value='${ceiling.remainlimit}' pattern="#,##0.00" /></span></label>&nbsp;&nbsp;
		 放款总金额:<label  id="totalNum"></label> &nbsp;  <input type="hidden" id="totalNum1">
		 合同总金额:<label  id="contractNum"></label> &nbsp;<input type="hidden" id="contractNum1">
	</p>
	 <sys:columnCtrl pageToken="contractAuditList"></sys:columnCtrl>
   <div class="box5">
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
           <thead>
            <tr>
                <th>序号</th>
			    <th>合同编号</th>		    
                <th>客户姓名</th>
                <th>共借人</th>
                <th>自然人保证人</th>
                <th>省份</th>
                <th>城市</th>
                <th>门店</th>
                <th>产品</th>
                <th>状态</th>
                <th>退回原因</th>
                <th>批复金额</th>
                <th>批复期数</th>
                <th>签约平台</th>
                <th>进件时间</th>
                <th>门店上传时间</th>
                <th>汇诚审批时间</th>
				<th>上调标识</th>
				<th>加急标识</th>
				<th>模式</th>
                <th>渠道</th>
                <th>无纸化标识</th>
			    <th>版本号</th>
			    <th>是否登记失败</th>
			    <th>是否加盖失败</th>
			    <th>风险等级</th>
			    <th>畅捷实名认证结果</th>
                <th>畅捷失败原因</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="prepareListBody"> 
            <c:if test="${ workItems!=null && fn:length(workItems)>0}">
						<c:set var="index" value="0" />
						<c:forEach items="${workItems}" var="item">
							<c:set var="index" value="${index+1}" />
							<c:choose>
							  <c:when test="${item.loanStatusName=='待款项确认退回'||item.loanStatusName=='放款退回' || item.loanStatusName=='金信退回' || item.backFlag=='1' || item.loanStatusCode=='104' || item.loanStatusCode=='105'}">
							    <tr class="red">
								    <td><input type="checkbox" name="prepareCheckEle" flag='${item.channelName}'  model="${item.model}"  replyProductName='${item.replyProductName}'
								    contractMoney='<fmt:formatNumber value="${item.contractMoney}" pattern="#00.00#"/>' lendingMoney='<fmt:formatNumber value="${item.lendingMoney}" pattern="#00.00#"/>'
									value='${item.applyId},${item.wobNum},${item.flowName},${item.token},${item.stepName},${item.loanCode}' loanStatus="${item.loanStatusCode}" frozenFlag="${item.frozenFlag}" />${index}</td>
								    <td>${item.contractCode}</td>								    
									<td>${item.customerName}</td>								
									<td>									
										<c:if test="${item.loanInfoOldOrNewFlag eq '' || item.loanInfoOldOrNewFlag eq '0'}">
											<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
                                   		   		${item.coborrowerName}
                                			</c:if> 
                                		</c:if>	
									</td>
									<td>
										<c:if test="${item.loanInfoOldOrNewFlag eq '1'}">
											<c:choose>
									 			<c:when test="${item.bestCoborrowerFlag eq '0'}">
									 				<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
			                                			 ${item.coborrowerName}
			                                 		</c:if> 
									 			</c:when>
									 			<c:otherwise>
									 				<c:if test="${item.bestCoborrowerName!=null && fn:contains(item.bestCoborrowerName,'null')==false}" >
			                                    		${item.bestCoborrowerName}
			                                   		</c:if>
									 			</c:otherwise>
									 		</c:choose>
                                		</c:if>
									</td>
									<td>${item.provinceName}</td>								
									<td>${item.cityName}</td>									
									<td>${item.storeName}</td>
									<td>${item.replyProductName}</td>
									<td>${item.loanStatusName}
									   <c:if test="${item.frozenFlag=='1'}" >
                                   		   (门店申请冻结)
                       				   </c:if>
									</td>									
									<td>${item.backReason}</td>
									<td><fmt:formatNumber value='${item.replyMoney}' pattern="#,#00.00"/></td>
									<td>${item.replyMonth}</td>
									<td>${item.signPlatform}</td>
									<td><fmt:formatDate value="${item.intoLoanTime}" pattern="yyyy-MM-dd"/></td>
									<td>${item.lastDealTime}</td>
									<td>${item.outApproveTime}</td>
									<td>
									   <c:if test="${item.raiseFlag=='1'}">
                   					 	<span>是</span>
               					 	   </c:if>
									</td>
									<td>
									 <c:if test="${item.urgentFlag=='1'}">
                   					 	<span style="color:red">加急</span>
               					 	 </c:if>
						 			</td>
						 			<td>${item.modelLabel}</td>
									<td>${item.channelName}</td>
									<td>
                                  	  <c:if test="${item.paperLessFlag=='1'}">
                                   		   <span>是</span>
                                      </c:if>
                                    </td>
									<td>
									 <c:forEach var="dict" items="${dictlist }">
								            <c:if test="${item.contractVersion==dict.value}">
								               ${dict.label }
								            </c:if>
								         </c:forEach>
								    </td>
								    <td>
								    	<c:choose>
                     					  <c:when test="${item.registFlag=='1'}">
                      							  成功
                     					  </c:when>
                     					  <c:when test="${item.registFlag=='0'}">
                         					  	 失败
                    					   </c:when>
                   						  </c:choose>
								    </td>
								    <td>
								     	<c:choose>
                     					  <c:when test="${item.signUpFlag=='1'}">
                      							  成功
                     					  </c:when>
                     					  <c:when test="${item.signUpFlag=='0'}">
                         					  	 失败
                    					   </c:when>
                   						  </c:choose>
								    </td>
								    <td>${item.riskLevel}</td>
								    <td>
								    	<c:choose>
                     					  <c:when test="${item.cjAuthen=='1'}">
                      							  成功
                     					  </c:when>
                     					  <c:when test="${item.cjAuthen=='2'}">
                         					  	 失败
                    					   </c:when>
                   						  </c:choose>
								    </td>
                                     <td>${item.cjAuthenFailure}</td>
									<td> 
									   <c:if test="${item.backReason!='风险客户' && item.backReason!='客户主动放弃' && item.frozenFlag!='1' && item.loanStatusCode!='104'}">
									    <input type="button" class="btn_edit" 
										 applyId='${item.applyId}' wobNum='${item.wobNum}' dealType='0'
										 token='${item.token}' name="dealctrAudit" value="办理" />
										</c:if>
										<input type="button" class="btn_edit" applyId='${item.applyId}' wobNum='${item.wobNum}' dealType='0'
										token='${item.token}' stepName='${item.stepName}' flowName='${item.flowName}' name="backBtn" value="退回"/>
										<input type="button" class="btn_edit" loanCode='${item.loanCode}'
										name="history" value="历史" /> 
							    	</td>
								</tr>
							  </c:when>
							  <c:otherwise>
								<tr>
									<td><input type="checkbox" name="prepareCheckEle" flag='${item.channelName}'  model="${item.model}"  replyProductName='${item.replyProductName}'
									contractMoney='<fmt:formatNumber value="${item.contractMoney}" pattern="#00.00#"/>' lendingMoney='<fmt:formatNumber value="${item.lendingMoney}" pattern="#00.00#"/>'
									value='${item.applyId},${item.wobNum},${item.flowName},${item.token},${item.stepName},${item.loanCode}' loanStatus="${item.loanStatusCode}" frozenFlag="${item.frozenFlag}"/>${index}</td>
								    <td>${item.contractCode}</td>
									<td>${item.customerName}</td>
									<td>
										<c:if test="${item.loanInfoOldOrNewFlag eq '' || item.loanInfoOldOrNewFlag eq '0'}">
											<c:if test="${item.coborrowerName!=null &&  fn:contains(item.coborrowerName,'null')==false}" >
	                                   			${item.coborrowerName}
	                                		</c:if> 
                                		</c:if>
									</td>								
									<td>    
										<c:if test="${item.loanInfoOldOrNewFlag eq '1'}"> 
											<c:choose>
									 			<c:when test="${item.bestCoborrowerFlag eq '0'}">
									 				<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
			                                			 ${item.coborrowerName}
			                                 		</c:if> 
									 			</c:when>
									 			<c:otherwise>
									 				<c:if test="${item.bestCoborrowerName!=null && fn:contains(item.bestCoborrowerName,'null')==false}" >
			                                    		${item.bestCoborrowerName}
			                                   		</c:if>
									 			</c:otherwise>
									 		</c:choose>
	                                	</c:if>
									</td>
									<td>${item.provinceName}</td>
									<td>${item.cityName}</td>
									<td>${item.storeName}</td>
									<td>${item.replyProductName}</td>
									<td>${item.loanStatusName}
									   <c:if test="${item.frozenFlag=='1'}" >
                                   		   (门店申请冻结)
                       				   </c:if>
									</td>
									<td>${item.backReason}</td>
									<td><fmt:formatNumber value='${item.replyMoney}' pattern="#,#00.00"/></td>
									<td>${item.replyMonth}</td>
									<td>${item.signPlatform}</td>
									<td><fmt:formatDate value="${item.intoLoanTime}" pattern="yyyy-MM-dd"/></td>
									<td>${item.lastDealTime}</td>
									<td>${item.outApproveTime}</td>
									<td>
									   <c:if test="${item.raiseFlag=='1'}">
                   					 	<span>是</span>
               					 	   </c:if></td>
									<td>
									 <c:if test="${item.urgentFlag=='1'}">
                   					 	<span style="color:red">加急</span>
               					 	 </c:if>
						 			</td>
						 			<td>${item.modelLabel}</td>
									<td>${item.channelName}</td>
									 <td>
                                      <c:if test="${item.paperLessFlag=='1'}">
                                        <span>是</span>
                                      </c:if>
                                    </td>
									<td>
									  <c:forEach var="dict" items="${dictlist }">
								            <c:if test="${item.contractVersion==dict.value}">
								               ${dict.label }
								            </c:if>
								         </c:forEach>
								    </td>
								     <td>
								    	<c:choose>
                     					  <c:when test="${item.registFlag=='1'}">
                      							  成功
                     					  </c:when>
                     					  <c:when test="${item.registFlag=='0'}">
                         					  	 失败
                    					   </c:when>
                   						  </c:choose>
								    </td>
								    <td>
								     	<c:choose>
                     					  <c:when test="${item.signUpFlag=='1'}">
                      							  成功
                     					  </c:when>
                     					  <c:when test="${item.signUpFlag=='0'}">
                         					  	 失败
                    					   </c:when>
                   						  </c:choose>
								    </td>
								     <td>${item.riskLevel}</td>
								     <td>
								    	<c:choose>
                     					  <c:when test="${item.cjAuthen=='1'}">
                      							  成功
                     					  </c:when>
                     					  <c:when test="${item.cjAuthen=='2'}">
                         					  	 失败
                    					   </c:when>
                   						  </c:choose>
								    </td>
                                     <td>${item.cjAuthenFailure}</td>
									<td>
									   <c:if test="${item.frozenFlag!='1'}">
									     <input type="button" class="btn_edit" 
										   applyId='${item.applyId}' wobNum='${item.wobNum}' dealType='0'
										   token='${item.token}' name="dealctrAudit" value="办理" />
										</c:if>
										<input type="button" class="btn_edit" applyId='${item.applyId}' wobNum='${item.wobNum}' dealType='0'
										token='${item.token}' stepName='${item.stepName}' flowName='${item.flowName}' name="backBtn" value="退回"/>
										<input type="button" class="btn_edit" loanCode='${item.loanCode}' 
										name="history" value="历史" /> 
							    	</td>
								</tr>
							 </c:otherwise>
						  </c:choose>
						</c:forEach>
					</c:if>
					<c:if test="${ workItems==null || fn:length(workItems)==0}">
						<tr>
							<td colspan="18" style="text-align: center;">没有待办数据</td>
						</tr>
					</c:if>
             </tbody>
         </table>
         <c:if test="${ workItems!=null || fn:length(workItems)>0}">
		   <div class="page">${page}</div>
		 </c:if>
    </div>
    <script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-290,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>