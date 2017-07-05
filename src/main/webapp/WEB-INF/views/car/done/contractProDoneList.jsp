<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借合同制作已办列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
		
	 $('#searchBtn').bind('click',function(){
		 page(1,${page.pageSize});
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("#hdStore").val('');
		 $("select").trigger("change");
	 });
	 $('#showMore').bind('click',function(){
		show();
	 });
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 var contractNo='<c:forEach items="${fns:getDictList('jk_car_contract_version')}" var="dict" varStatus="status"><c:if test="${status.last}">${dict.label }</c:if></c:forEach>';
		$(".contractNo").html(contractNo);
 });
  function page(n,s){
      if(n) $("#pageNo").val(n);
      if(s) $("#pageSize").val(s);
      $("#inputForm").attr("action", "${ctx}/common/carHistory/contractProDoneList");
      $("#inputForm").submit();
      return false;
  }
  
 //2016-05-23 根据测试人员要求修改 测试id：123 -8、制作合同已办点击办理后进入页面并非窗口
 //原窗口方法为contractDone
 function contractViewDone(applyId){
	window.location.href = ctx + "/car/carContract/checkRate/contractDone?applyId="+applyId;                    
 }  
 
 //查看影像调用
 imageUrl='${imageurl}'
 $(document).ready(function(){
 $("#ImageData").click(function(){
 	art.dialog.open(imageUrl/* 'http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111' */, {
 	title: "客户影像资料",	
 	top: 80,
 	lock: true,
 	    width: 1000,
 	    height: 450,
 	},false);	
 	});
 });

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
 //协议查看
 function  showImage(applyId, contractCode){
		  var url = '${ctx}/car/carContract/checkRate/xieyiLookList?applyId=' + applyId + '&contractCode=' + contractCode;
		  art.dialog.open(url, {
			   id: 'protcl',
			   title: '协议查看',
			   lock:false,
			   width:1500,
			   height:600
			},false); 
	}
 /**
  * 弹出车借合同协议预览和下载 loanCode flag
  */
  function signDocShow(applyId,loanCode,contractCode){
		 var url =ctx+"/car/carContract/checkRate/initPreviewSignContract?contractCode="+contractCode;
		 art.dialog.open(url, {
			   id: 'protcl',
			   title: '协议查看',
			   lock:false,
			   width:1500,
			   height:600
			},false); 
	}
</script>
</head>
<body style="overflow-y: hidden">
	<div class="control-group">
		<form:form id="inputForm" modelAttribute="carLoanStatusHisEx"
			method="post" class="form-horizontal">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input name="menuId" type="hidden" value="${param.menuId}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table id = "searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> <form:input
							id="customerName" path="loanCustomerName" class="input_text178" />
					</td>
					<td><label class="lab">门店：</label> <form:input id="txtStore"
						path="storeName" maxlength="20"
						class="txt date input_text178" value="${secret.store }" /> <i
						id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
					<td><label class="lab">产品类型：</label> <form:select
							id="productType" path="productTypeContract" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fncjk:getPrd('products_type_car_credit')}"
								var="product_type">
								<form:option value="${product_type.productCode}">${product_type.productName}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>
				<tr>
					<td><label class="lab">身份证号：</label> <form:input
							path="certNum" class="input_text178" /></td>
					<td><label class="lab">合同编号：</label> <form:input
							path="contractCode" class="input_text178" /></td>
					<td><label class="lab">借款期限：</label> <form:select
							id="loanMonths" path="contractMonths" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fncjk:getPrdMonthsByType('products_type_car_credit')}"
								var="product_type">
								<form:option value="${product_type.key}">${product_type.value}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>
				<tr id="T1" style="display: none">
					<td><label class="lab">签约日期：</label> <input
						name="contractFactDay" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						style="cursor: pointer" class="Wdate input_text178" 
						value="<fmt:formatDate value='${carLoanStatusHisEx.contractFactDay}' type='date' pattern='yyyy-MM-dd' />"  /></td>
					<td><label class="lab">借款状态：</label> <form:select
							id="loanMonths" path="loanStatusCode" class="select180">
							<option value="">全部</option>
							<form:options items="${fns:getDictList('jk_car_loan_status')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
					 <td>
		              <label class="lab">是否电销：</label>
	                   <c:forEach items="${fns:getDictList('yes_no')}" var="item">
						 <input type="radio" name="telesalesFlag"
									value="${item.value}"
									<c:if test="${item.value==param.telesalesFlag}">
	                                checked='true'
	                             	</c:if> />
	                             	${item.label}
	               	   </c:forEach>
		            </td>
		           </tr><tr id="T2" style="display: none">
		            <td>
		              <label class="lab">系统来源：</label>
	                   <form:select id="dictSourceType" path="dictSourceType" class="select180">
							<option value="">请选择</option>
			                <option value="2">2.0</option> 
			                <option value="3">3.0</option>
						</form:select>
		            </td>
				</tr>
				<tr id="T2" style="display:none">
				<td><label class="lab">渠道：</label>
		             	<form:select id="loanFlag" path="loanFlag" class="select180">
		                   <option value="">请选择</option>
			                    <c:forEach items="${fns:getDictList('jk_car_throuth_flag')}" var="loan_Flag">
			                   		<form:option value="${loan_Flag.value}">${loan_Flag.label}</form:option>
			              </c:forEach>
				         </form:select>
		        </td>
		        <td><label class="lab">无纸化：</label>
		             	<form:select id="paperLessFlag" path="paperLessFlag" class="select180">
		                   <option value="">请选择</option>
			               <option value="1">是</option> 
			               <option value="0">否</option>  
				         </form:select>
		        </td>
			</tr>
			</table>
			<div class="tright pr30 pb5">
			<span style="position: relative;left: 100px; top: 43px;color:red;font-size:14px;" >当前合同版本号：<span class="contractNo"></span></span>
				<form:input type="hidden" id="hdStore" path="storeCode"/></td>
				<input type="button" class="btn btn-primary" id="searchBtn"
					value="搜索"></input> <input type="button" class="btn btn-primary"
					id="clearBtn" value="清除"></input>
				<div class="xiala" style="text-align: center;">
					<img src="${context}/static/images/up.png" id="showMore"></img>
				</div>
			</div>
	</div>
	</form:form>
	<sys:columnCtrl pageToken="carContractProDoneList"></sys:columnCtrl>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
	 
		<table id="tableList" class="table table-bordered table-condensed table-hover"
			style="margin-bottom: 200px;">
			<thead>
				<tr>
					<th>序号</th>
					<th>合同编号</th>
					<th>身份证号</th>
					<th>客户姓名</th>
					<th>共借人</th>
					<!-- <th>申请金额（元）</th> -->
					<th>借款期限(天)</th>
					<th>产品类型</th>
					<th>合同金额</th>
					<!-- <th>批借金额</th> -->
					<th>签约日期</th>
					
					<th>所属省份</th>
					<th>门店名称</th>
					<!-- <th>团队经理</th> -->
					<!-- <th>客户经理</th> -->
					<!-- <th>面审人员</th> -->
					<th>借款状态</th>
					<th>车牌号码</th>
					<th>合同版本号</th>
					<th>是否电销</th>
					<th>渠道</th>
					<th>是否无纸化</th>
					<th>系统来源</th>
					<th>签章成功否</th>
					<th>操作</th>
				</tr>
			</thead>
			<c:if test="${ page.list != null && fn:length(page.list)>0}">
				<c:forEach items="${page.list}" var="item" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${item.contractCode}</td>
						<td>${item.certNum}</td>
						<td>${item.loanCustomerName}</td>
						<td>${item.coborrowerName}</td>
						<!-- <td>${item.loanApplyAmount}</td> -->
						<td>${item.contractMonths}</td>
						<td>${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.productTypeContract)}</td>
						<td><fmt:formatNumber value='${item.contractAmount}' pattern='#,##0.00' /></td>
						<!--<c:choose>
							<c:when test="${item.finalAuditAmount != null }">
								<td>${item.finalAuditAmount}</td>
							</c:when>
							<c:when test="${item.secondAuditAmount != null }">
								<td>${item.secondAuditAmount}</td>
							</c:when>
							<c:otherwise>
								<td>${item.firstAuditAmount}</td>
							</c:otherwise>
						</c:choose>-->
						<td><fmt:formatDate value="${item.contractFactDay}"
								pattern="yyyy-MM-dd" /></td>
						
						<td>${item.provinceName}</td>
						<td>${item.storeName}</td>
						<!-- <td>${item.teamManagerName}</td>
						<td>${item.costumerManagerName}</td>
						<td>${item.reviewMeetName}</td> -->
						<td>${item.loanStatusCode}</td>
						<td>${item.plateNumbers}</td>
						<td>${item.contractVersion }</td>
						<td>${item.telesalesFlag}</td>
						<td>${item.loanFlag}</td>
						<c:choose>
							<c:when test="${item.paperLessFlag eq '0' }">
								<td>否</td>
							</c:when>
							<c:when test="${item.paperLessFlag eq '1' }">
								<td>是</td>
							</c:when>
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
						<td><fmt:formatNumber value="${item.dictSourceType}"  pattern="0.0"/></td>
						<c:choose>
							<c:when test="${item.signFlag eq '0' }">
								<td>失败</td>
							</c:when>
							<c:when test="${item.signFlag eq '1' }">
								<td>成功</td>
							</c:when>
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
						<td><input type="button" value="查看" class="btn_edit jkcj_contractProDoneList_check"
						 onclick="contractViewDone('${item.applyId}')"></input>
							 <input type="button" class="btn_edit ViewImageData jkcj_contractProDoneList_video" value="查看影像" onclick="getImageUrl('${item.loanCode}')"/> 
							 <button class="btn_edit jkcj_contractProDoneList_history"  value="${item.applyId}"  onclick="javascript:showCarLoanHis('${item.loanCode}')">历史</button>&nbsp;
							 <c:if test="${item.signFlag=='1'}">
	                            <button  class="btn_edit jkcj_contractProDoneList_showcontract"   onclick="javascript:signDocShow('','${item.loanCode}','${item.contractCode}');">
	                                                                            电子协议查看
	                            </button>
	                        </c:if>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ page.list==null || fn:length(page.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有已办数据</td>
				</tr>
			</c:if>
		</table>
	</div>
	<div class="pagination">${page}</div>
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