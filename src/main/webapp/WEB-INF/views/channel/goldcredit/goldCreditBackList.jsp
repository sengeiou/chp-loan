<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/channel/goldcredit/popuplayer.js"></script>
<script type="text/javascript"
	src='${context}/js/common.js'></script>
<title>金信债权退回列表</title>
<script type="text/javascript">
	$(function (){
		//获取table数据集
		var tlen = $("#tableList tbody>tr");
		//如果只有一条记录,则需要判断
		if(tlen.length==1){
			//获取tr的第二列
			var $td = $("#tableList tbody>tr").find("td:eq(1)");
			//第二列的文本内容
			var tdtext = $td.text();
			//如果=='-',则表示没有数据,则需要做以下操作,此行的第一个列增加colspan属性,删除之后的数据
			if(tdtext=='-'){
				//获取th的列数
				var theadtr = $("#tableList thead>tr").find("th");
				//
				var len = theadtr.length;
				$("#tableList tbody>tr").find("td:eq(0)").attr("colspan",len).css({"text-align":"center","color":"black"});
				tlen.children("td:gt(0)").remove();
			}
		}
		$("#tableList tbody").css("color","red");
		
		loan.getstorelsit("storesName", "storeOrgId");
		$.popuplayer(".edit");
		// 伸缩
		$("#showMore").click(function(){
			if(document.getElementById("T1").style.display=='none'){
				document.getElementById("showMore").src=ctxStatic + '/images/down.png';
				document.getElementById("T1").style.display='';
				document.getElementById("T2").style.display='';
			}else{
				document.getElementById("showMore").src=ctxStatic + '/images/up.png';
				document.getElementById("T1").style.display='none';
				document.getElementById("T2").style.display='none';
			}
		});
		$("#clearBtn").click(function () {
			$(":input","#disCardForm").not(":submit,:button,:reset").val('').removeAttr("checked").removeAttr("selected");
		});
		var $subBox = $(":input[name='checkBoxItem']");
		var num = 0,money = 0.00,contractMoney = 0.00;
		/* $subBox.each(function(){
			var totalGrantMoney = parseFloat($(this).attr("grantAmount"));
				contractMoney += parseFloat($(this).attr("contractMoney"))
				money += totalGrantMoney;
				num ++;
			$("#totalGrantMoney").text(fmoney(money,2));
			$("#totalNum1").text(num);
			$("#contractTotalGrantMoney").text(fmoney(contractMoney,2));
		}); */
		// 点击全选
		$("#checkAll").click(function() {
			var cumulativeLoan = 0.00,
			totalNum = 0,cmoney  = 0.00;
			$subBox.prop("checked",this.checked);
			//全选按钮选中
			if ($(this).is(":checked")) {
				/* //放款总额
				var hiddenDeduct = parseFloat($("#deductHidden").val()),
				hiddenGrant = parseFloat($("#hiddenGrant").val());//合同金额
				$(":input[name='checkBoxItem']").each(function (){
					cmoney += hiddenGrant;
					totalNum += 1;
					cumulativeLoan += hiddenDeduct;
				});
				cumulativeLoan = money ;
				totalNum = num;
				cmoney = contractMoney;  */
				$(":input[name='checkBoxItem']").each(function (){
					var totalGrantMoney = parseFloat($(this).attr("grantAmount"));
					cmoney += parseFloat($(this).attr("contractMoney"))
					cumulativeLoan += totalGrantMoney;
					totalNum ++;
					$("#totalGrantMoney").text(fmoney(money,2));
					$("#totalNum1").text(num);
					$("#contractTotalGrantMoney").text(fmoney(contractMoney,2));
				});
			}else{
				cumulativeLoan = 0.00;
				totalNum = 0;
				cmoney  = 0.00;
			} 
			$("#totalGrantMoney").text(fmoney(cumulativeLoan,2));
			$("#totalNum1").text(totalNum);
			$("#contractTotalGrantMoney").text(fmoney(cmoney,2));
			//合同金额
			$("#hiddenGrant").val(cmoney);
			$("#deductHidden").val(cumulativeLoan);
			$("#totalNum").val(totalNum);
		});
		// 计算金额,
		$subBox.click(function() {
			// 获得单个单子的金额
			var deductAmount = parseFloat($(this).attr("grantAmount")),
			grant = parseFloat($(this).attr("contractMoney"));
			
			//放款总额
			var hiddenDeduct = parseFloat($("#deductHidden").val()),
			totalNumm = parseFloat($("#totalNum").val()),
			hiddenGrant = parseFloat($("#hiddenGrant").val());//合同金额
			
			var total  = 0,contractM = 0.00,lendM = 0.00;
			if ($(this).is(':checked')) {
				lendM = hiddenDeduct += deductAmount;
				total = totalNumm += 1 ;
				contractM = hiddenGrant += grant;
			} else {
				if ($(":input[name='checkBoxItem']:checked").length == 0) {
					hiddenDeduct = money;
					totalNumm = num;
					hiddenGrant = contractMoney;
				} else {
					lendM = hiddenDeduct -= deductAmount;
					total = totalNumm -= 1 ;
					contractM = hiddenGrant -= grant;
				}
			}
			$("#checkAll").prop("checked",($subBox.length == $(":input[name='checkBoxItem']:checked").length) ? true : false);
			
			$("#totalGrantMoney").text(fmoney(hiddenDeduct,2));
			$("#totalNum1").text(totalNumm);
			$("#contractTotalGrantMoney").text(fmoney(hiddenGrant,2));
			//合同金额
			$("#hiddenGrant").val(contractM);
			$("#deductHidden").val(lendM);
			$("#totalNum").val(total);
			
			
		});
		$("#disCardBtn").click(function () {
			var formSerialize = $("#disCardForm").serialize(),
			length = selectedList();
			if (length.length == 0){
				art.dialog.alert('请选择需要取消的金信数据');
			return false;
			} else if (length.length!= 0) {
				art.dialog({
					title : '提示',
					content : '确认取消选中的金信标识？',
					opacity : .1,
					lock : true,
					ok : function() {
						window.location.href = ctx
						+ "/channel/goldcredit/back/cancelJINXINFlag?applyIds="+length+"&"+formSerialize;
					},
					cancel : true
				});
			}
		});
		//将金信退回的的债权信息重新返回到金信待确认放款列表中
		$("#returnWaitBtn").click(function (){
			var checkedLength = $(":input[name='checkBoxItem']:checked").length;
			if (!checkedLength){
				art.dialog.alert("请选择相应的数据信息进行操作！");
				return false;
			}
			//获取选中的applyId
			var applyIds = "";
			$(":input[name='checkBoxItem']:checked").each(function (index, domEle){
				applyIds += ","+$(this).val();
			});
			applyIds = applyIds.substr(1);
			art.dialog({
				title:'提示',
				content:'确定要将选中的数据信息返回到金信待放款确认列表中？',
				opacity : .1,
				lock : true,
				ok : function() {
					$.ajax({
						type : 'post',
						url : ctx+'/channel/goldcredit/back/returnToConfirmation',
						data :{"applyIds":applyIds},
						cache : false,
						dataType : 'json',
						async : false,
						success : function(result) {
							if(result){
								art.dialog.alert("数据信息返回到金信待放款确认列表成功",function () {
									window.location.href=ctx+'/channel/goldcredit/back/getJXCreditorInfo';
								});
							}else{
								art.dialog.alert("数据信息返回到金信待放款确认列表失败");
							}
						},
						error : function() {
							art.dialog.alert('请求异常');
						}
					});
				},
				cancel:true
			});
		});
	});
	//办理
	/*function dealBtn (applyId,loanCode){
		window.location.href = ctx
		+ "/channel/goldcredit/back/creditBackDeal?applyId="+applyId+"&loanCode="+loanCode;
	}*/
	function selectedList() {
		var cid = "";
		if ($(":input[name='checkBoxItem']:checked").length > 0) {
			$(":input[name='checkBoxItem']:checked").each(function() {
				if (cid != "") {
					cid += "," + $(this).val();
				} else {
					cid = $(this).val();
				}
			});
		}
		return cid;
	}
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
	$(function (){
		// 导出打款表
		$("#btnExportRemit")
				.click(
						function() {
							var idVal = "";
							var loanGrant = $("#disCardForm").serialize();
							if ($(":input[name='checkBoxItem']:checked").length > 0) {
								$(":input[name='checkBoxItem']:checked").each(
										function() {
											if (idVal != "") {
												idVal += ","
														+ $(this).attr(
																"loanCode");
											} else {
												idVal = $(this)
														.attr("loanCode");
											}
										});
							}
							window.location.href = ctx
									+ "/channel/goldcredit/back/exportRemit?loanCodes="
									+ idVal + "&" + loanGrant;
						});

		// 汇总表导出
		$("#btnExportSummary")
				.click(
						function() {
							var idVal = "";
							var loanGrant = $("#disCardForm").serialize();
							if ($(":input[name='checkBoxItem']:checked").length > 0) {
								$(":input[name='checkBoxItem']:checked").each(
										function() {
											if (idVal != "") {
												idVal += ","
														+ $(this).attr(
																"loanCode");
											} else {
												idVal = $(this)
														.attr("loanCode");
											}
										});
							}
							window.location.href = ctx
									+ "/channel/goldcredit/back/exportSummary?loanCodes="
									+ idVal + "&" + loanGrant;
						});
	});
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#disCardForm").attr("action","${ctx }/channel/goldcredit/back/getJXCreditorInfo");
	$("#disCardForm").submit();
	return false;
}
/****************20160816金信债权退回列表【操作】中点击退回后，无需跳转到查看页面，直接执行此按钮功能即可*****************************/
 
    function grantBackBtnclick(applyId,loanCode,obj){
    	$("#applyId").val(applyId);
    	$("#loanCode").val(loanCode);
    	//obj.attr("data-target","#sureBack");
		$("#sureBack").modal('show');
    	
	}
 // 点击确认退回原因,需要指定退回的节点
	function dealBtn(){
	     var applyId=$("#applyId").val();
	     var loanCode=$("#loanCode").val();
	     if(applyId==null || applyId==undefined||applyId==''||loanCode==null || loanCode==undefined||loanCode==''){
				art.dialog.alert("数据不能为空！");
				return false;
	     }
		// 获得填写的退回原因，调用流程退回的处理方法，更新单子的状态以及合同表的待放款确认退回原因
		var grantSureBackReason= $("#sel").find("option:selected").text();
		// 要退回的流程节点,合同的审核
		var param="applyId="+applyId+"&loanCode="+loanCode;
		if(grantSureBackReason=="其他"){
			grantSureBackReason=$("#textarea").val();
			if(grantSureBackReason==null||grantSureBackReason==""){
				art.dialog.alert("退回原因不能为空！");
				return false;
			}
		}
		grantSureBack(param,grantSureBackReason);
	}
     // 退回方法声明
    function grantSureBack(param,grantSureBackReason){
    	if(param!=null && param!=undefined){
			param+='&backBatchReason='+encodeURI(grantSureBackReason);
		}
    	$.ajax({
			type : 'post',
			url : ctx+'/channel/goldcredit/back/creditBackDeal',
			data :param,
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				if(result){
					art.dialog.alert("单子退回成功");
				}else{
					art.dialog.alert("单子退回失败");
				}
				window.location.href=ctx+'/channel/goldcredit/back/getJXCreditorInfo';
			},
			error : function() {
				art.dialog.alert('请求异常');
			}
		});
    }// 关闭窗口
	function closeModal(id){
		$("#"+id).modal('hide');
	} 
    // 选择其他的时候，填写退回原因
    function SelectChange() {
        var selectText = $("#sel").find("option:selected").text();
        if (selectText != "其他") {
           $("#other").hide();
        } else {
		   $("#other").show(); 
        }
    }
 /*******************************************************************************************************/
</script>
</head>
<body>

	<div class="control-group" >
	
      <form:form id="disCardForm" action="${ctx }/channel/goldcredit/back/getJXCreditorInfo" modelAttribute="params" method="post">
      <input type="hidden" id="userCode">
      <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
	  <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
	  <input type="hidden" name="menuId" value="${param.menuId }">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
                <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
                <td><label class="lab">门店：</label><input type="text"
							class="input_text178" name="storeName" id="storesName"
							readonly="readonly" value="${params.storeName }" /> <i
							id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
							<input type="hidden" name="storeOrgIds"
							value="${params.storeOrgId}" id="storeOrgId" />
               </td>
                
            </tr>
            <tr>
                <td><label class="lab">证件号码：</label><form:input type="text" class="input_text178" path="identityCode"></form:input></td>
                <td ><label class="lab">产品类型：</label><select name="replyProductCode" class="select180">
						<option value=''>请选择</option>
						<c:forEach items="${productList}" var="product">
						   <option value="${product.productCode}" 
							  <c:if test="${params.replyProductCode==product.productCode}">
								    selected=true
							   </c:if>
								>${product.productName}</option>
						  </c:forEach>
					 </select>
               </td>
				<td ><label class="lab">是否加急：</label><form:select class="select180" path="urgentFlag">
				<form:option value="">请选择</form:option>
					<c:forEach items="${fns:getDictList('jk_urgent_flag')}" var="urgent_flag">
						<form:option value="${urgent_flag.value}">${urgent_flag.label}</form:option>
		             </c:forEach>
				</form:select></td>
            </tr>
			<tr id="T1" style="display:none">
			    <td ><label class="lab">是否追加借：</label><form:select class="select180" path="additionalFlag">
						<form:option value="">请选择</form:option>
						<form:option value="0">否</form:option>
						<form:option value="1">是</form:option>
							<%-- <c:forEach items="${fns:getDictList('jk_add_flag')}" var="loan_type">
								<form:option value="${loan_type.value}">${loan_type.label}</form:option>
				             </c:forEach> --%>
						</form:select></td>
				<%--  <td ><label class="lab">系统来源：</label><form:select class="select180" path="dictSource">
						<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="loan_type">
								<form:option value="${loan_type.value}">${loan_type.label}</form:option>
				             </c:forEach>
						</form:select>
				</td> --%>
				 <td><label class="lab">借款状态：</label><form:select class="select180" path="loanStatusCode">
                	<form:option value="">请选择</form:option>
                	<form:option value="99">金信初审拒绝</form:option>
                	<form:option value="100">金信复审拒绝</form:option>
                </form:select></td>
            </tr>
           <!--  <tr id="T2" style="display:none">
            </tr> -->
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
		<c:if test="${role eq '1'}">
	    	<button class="btn btn-small jkhj_goldback_batchcancel" id="disCardBtn">批量金信取消</button>
	    	&nbsp;
	 		<button class="btn btn-small jkhj_goldback_backconfirm" id="returnWaitBtn">返回金信待款项确认</button>
	 		&nbsp;
			<button class="btn btn-small jkhj_goldback_sendexport" id="btnExportRemit">打款表导出</button>
			&nbsp;
			<button class="btn btn-small jkhj_goldback_sumexport" id="btnExportSummary">汇总表导出</button>
		</c:if>
    	&nbsp;&nbsp;<label class="lab1 red">放款总金额：</label><label class="red" id="totalGrantMoney">0.00</label><label class="lab1 red">元 </label>
    	<input type="hidden" id="deductHidden" value="0.00"> 	
    	&nbsp;&nbsp;<label class="lab1 red">放款总笔数：</label><label class="red" id="totalNum1">0</label><label class="lab1 red">笔</label>
    	<input type="hidden" id="totalNum" value="0"> 	
    	&nbsp;&nbsp;<label class="lab1 red">合同总金额：</label><label class="red" id="contractTotalGrantMoney">0.00</label><label class="lab1 red">元</label></p>
    	<input type="hidden" id="hiddenGrant" value="0.00">
    	<sys:columnCtrl pageToken="goldee"></sys:columnCtrl>
    	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;">
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
          <tr>
            <th>全选
			<input type = "checkbox" id="checkAll"/></th>
            <th>合同编号</th>
            <th>门店名称</th>
            <th>客户姓名</th>
            <th>产品</th>
            <th>共借人</th>
            <th>证件号码</th>
            <th>期限</th>
            <th>合同金额</th>
            <th>放款金额</th>
            <th>开户行</th>
            <th>支行名称</th>
            <th>账号</th>
            <th>借款状态</th>
			<th>加急标识</th>
            <th>合同版本号</th>
            <th>借款利率</th>
            <th>催收服务费</th>
            <th>是否无纸化</th>
            <th>是否有保证人</th>
            <th>是否登记失败</th>
            <th>是否加盖失败</th>
            <th>是否电销</th>
            <th>渠道</th>
            <th>模式</th>
            <th>操作</th>
         </tr>
         </thead>
		<tbody>
         <c:if test="${workItems!=null && fn:length(workItems)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${workItems}" var="item" varStatus="status">
          <c:set var="index" value="${index+1}" />
          	<tr style="color:red;">
            <%--  <tr <c:if test="${item.urgentFlag=='1' || item.frozenFlag == '1'}">
                   style="color:red"
             </c:if>> --%>
             <td><input type="checkbox" name="checkBoxItem" contractMoney='${item.contractMoney}' grantAmount='${item.lendingMoney}' loanCode = "${item.loanCode}"
              value='${item.applyId}'/> ${status.count}
              </td>
             <td>${item.contractCode}</td>
             <td>${item.storeName }</td>
             <td>${item.customerName}</td>
             
             <td>${item.replyProductName}</td>
             <td>${empty item.coborrowerName || item.coborrowerName == 'null' ? '' : item.coborrowerName}</td>
             <td>${item.identityCode}</td>
             <td>${item.replyMonth}</td>
             <td><fmt:formatNumber value='${item.contractMoney}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.lendingMoney}' pattern="#,#00.00"/></td>
             
             <td>${item.depositBank}</td>
             <td>${item.bankBranchName }</td>
             <td>${item.bankAccountNumber}</td>
             <td>${item.loanStatusName}
             	<c:if test="${item.frozenFlag == '1' }">
	             	(门店申请冻结)
	            </c:if>
	         </td>
             <td>${item.urgentFlag}</td> 
             <td>
             	 <c:forEach items="${fns:getDictList('jk_contract_ver')}" var="contract_vesion">
				 	<c:if test ="${contract_vesion.value eq item.contractVersion}">${contract_vesion.label}</c:if>
				 </c:forEach>	
             </td> 
            <td><fmt:formatNumber value='${item.monthRate }'
								pattern="#,##0.000" /></td>
			<td><fmt:formatNumber value='${item.urgeServiceFee }'
								pattern="#,##0.00" /></td>
            <td>
				<c:if test="${item.paperLessFlag eq '1'}" var = 'paperLessFlag'>
					是
				</c:if>
				<c:if test="${!paperLessFlag}">
					否
				</c:if>
			</td>
			<td>
				<c:if test="${item.ensureManFlag eq '1'}" var = 'ensureManFlag'>
					是
				</c:if>
				<c:if test="${!ensureManFlag}">
					否
				</c:if>
			</td>
			<td>
				<c:if test="${item.registFlag eq '1'}">
					成功
				</c:if>
				<c:if test="${item.registFlag eq '0'}">
					失败
				</c:if>
				<c:if test="${empty item.registFlag}"></c:if>
			</td>
			<td>
				<c:if test="${item.signUpFlag eq '1'}">
					成功
				</c:if>
				<c:if test="${item.signUpFlag eq '0'}">
					失败
				</c:if>
				<c:if test="${empty item.signUpFlag}"></c:if>
			</td>
            <td>${item.telesalesFlag}</td> 
            <td>${item.channelName}</td> 
            <td></td>
            <td style="position:relative">
				<input type="button" class="btn btn_edit edit" value="操作"/>
				 <div class="alertset" style="display:none">
				 	<c:if test="${role eq '1' && item.issplit=='0'}">
					 	<button class="btn_edit" name="dealBtn" value='${item.applyId}' data-toggle="modal" onclick="grantBackBtnclick('${item.applyId}','${item.loanCode }',this);">退回</button>
				 	</c:if>
            	 	<button class="btn_edit" name="history" onclick="showAllHisByLoanCode('${item.loanCode}')">历史</button>
				 </div>
			</td>       
         </tr> 
         
         </c:forEach>  
            
         </c:if>
         
         <c:if test="${ workItems==null || fn:length(workItems)==0}">
           <tr><td colspan="26" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
    </table>
    <c:if test="${workItems!=null || fn:length(workItems)>0}">
		<div class="page">${page}</div>
	</c:if>
	</div>
<!-- 20160816金信债权退回列表【操作】中点击退回后，无需跳转到查看页面，直接执行此按钮功能即可  start-->
     <div class='modal fade' id="sureBack" style="width:90%;height:90%">
		<input type="hidden"  id="applyId" value=''></input>
		<input type="hidden"  id="loanCode" value=''></input>
       <div class="modal-dialog" role="document">
	       <div class="modal-content">
           <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h3 class="modal-title" id="grantSureBack">金信退回</h3>
		    </div>
       <div class="modal-body">
			<table class="table table-striped table-bordered table-condensed">
                <tr>
                    <td align="left"><label>退回原因</label></td>
                    <td align="left">
                      <select id="sel" class="select180" onchange="SelectChange();">
                        <option>风险客户</option>
                        <option>客户原因放弃</option>
                        <option>其他</option>
                      </select>
                    </td>
                </tr>
                <tr id="other" style="display:none">
                    <td align="left"><label>其他退回原因</label>
                    <span class = "red">*</span><textarea id = "textarea"  rows="" cols="" style='font-family:"Microsoft YaHei";' ></textarea></td>
                </tr>
            </table>
		</div>
            <div class="modal-footer"><button class="btn btn-primary" onclick="dealBtn();">确认</button>
            <button class="btn btn-primary" class="close" data-dismiss="modal" onclick="closeModal('sureBack')">取消</button></div>
		</div>
		</div>
		</div>
<!-- 20160816金信债权退回列表【操作】中点击退回后，无需跳转到查看页面，直接执行此按钮功能即可  end-->
<%-- <div class="pagination">${workItems}</div> --%>
<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-300,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>