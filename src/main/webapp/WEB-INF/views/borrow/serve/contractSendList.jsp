<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/serve/contractSendCommon.js"></script>
<script type="text/javascript" src="${context}/js/common.js?ver=1"></script>

<script type="text/javascript">
	$(document).ready(function(){
		// 初始化门店下拉框
		loan.getstorelsitSub("txtStore","hdStore","","sousuo");
	});
	$(function(){
		var msg= '${msg}'
		if(msg!=null && msg !=''){
			art.dialog.alert(msg);
		}
	})
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#contractSendForm").attr("action", "${ctx }/borrow/serve/customerServe/contractSendList");
		$("#contractSendForm").submit();
		return false;
	}
	
	//点击清除按钮调用的的方法
	function resets(){
		// 清除text	
		$(":text").val('');
		// 清除门店隐藏域
		$("#hdStore").val('');
		$("#revisitStatus").val('');
		// 清除select	
		$("select").val("");
		$("#contractSendForm").submit();
	}
	
	function checkAll(obj){
		if(obj.checked){
			$("input[name='checkItem']").attr('checked', true)
	    }else{
			$("input[name='checkItem']").attr('checked', false)
		}
	}
	
	function batchReceive(){
		var ids = "";
		$("input[name='checkItem']:checkbox").each(function(i,d){
			if (d.checked){
				ids += d.value + ",";
			}
		});
		if (ids == ""){
			 
			receiveAll();
		}else{
			ids = ids.substr(0, ids.length-1)
			receive(ids);
		}
	}
	
	function receive(ids){
		if (window.confirm("确认接收吗？")){
			$.ajax({
				type : "post",
				url : "${ctx }/borrow/serve/customerServe/receiveContract?fileType=" + $("#fileType").val(),
				dataType : "json",
				data : {'ids':ids},
				async : false,
				success : function(msg){
					art.dialog.alert("接收成功");
					$("#contractSendForm").submit();
				},
				error : function(msg){
					art.dialog.alert("接收失败");
				}
			});
		}
	}
	function receiveAll(){
		var conts=$('#conts').val();
		if (window.confirm("确认接收本查询条件下所有数据吗？")){
			if(conts!=null && conts!=''){
				$.ajax({
					type : "post",
					url : "${ctx }/borrow/serve/customerServe/receiveAllContract?fileType=" + $("#fileType").val(),
					dataType : "json",
					data : {'conts':conts},
					async : false,
					success : function(msg){
						art.dialog.alert("接收成功");
						conts='';
						$("#contractSendForm").submit();
					},
					error : function(msg){
						art.dialog.alert("接收失败");
					}
				});
			}else{
				$.ajax({
					type : "post",
					url : "${ctx }/borrow/serve/customerServe/receiveAllContract?fileType=" + $("#fileType").val(),
					dataType : "json",
					data : $('#contractSendForm').serialize(),
					dataType : 'json',
					async : false,
					success : function(msg){
						art.dialog.alert("接收成功");
						$("#contractSendForm").submit();
					},
					error : function(msg){
						art.dialog.alert("接收失败");
					}
				});
			}
		}
	}
	
	function exportExcel(){
		var ids = "";
		$("input[name='checkItem']:checkbox").each(function(i,d){
			if (d.checked){
				ids += d.value + ",";
			}
		});
		if (ids != ""){
			ids = ids.substr(0, ids.length-1);
			$("#ids").val(ids);
		}
		var conts=$('#conts').val();
		if(conts!=null && conts!=''){
			$('#contracts').val(conts);
		}
		$("#contractSendForm").attr("action", "${ctx }/borrow/serve/customerServe/exportExcel");
		$("#contractSendForm").submit();
		$('#contracts').val("");
		$("#ids").val("");
		$("#contractSendForm").attr("action", "${ctx }/borrow/serve/customerServe/contractSendList");
	}
	$(function(){
		document.onkeydown = function(e){
			var ev = document.all ? window.event : e;
			 if(ev.keyCode==13) {
		           $('#contractSendForm').submit();//处理事件
		     }
		}
		}); 
	function importExcel(){
    	$("#file").click();
	}
	
	$(function(){
		$("#revisitStatusId").click(function(){ 
			 art.dialog.open(ctx + "/borrow/grant/grantSure/pathSkip?returnURL=loanflagPage&isFailBtn=",{
				title:"渠道标识", 
				width:460, 
				height:200, 
				lock:true,
				opacity: .1,
		        okVal: '确定', 
		        ok:function(){
		        	 var iframe = this.iframe.contentWindow;
		        	var rsName = "";
					var rs = "";
					$('input[name="revisitStatusCheck"]:checked',iframe.document).each(function(){ 
						if($(this).attr("checked")=='checked' || $(this).attr("checked") ){
							rs += $(this).val()+",";
							rsName += $(this).attr("sname")+",";
		        	    }
					});
					rs = rs.replace(/,$/g,"");
					rsName = rsName.replace(/,$/g,"");
					$("#revisitStatus").val(rs);
					$("#revisitStatusName").val(rsName); 
					$("#sousuo").get(0).focus();
		        },
		        cancelVal: '取消',
		        cancel: function(){
					 $("#sousuo").get(0).focus();
			        }
			},false);
		});
		
	})
	
	
	function fileChange(){
		var filepath=$("input[name='file']").val();
	    var extStart=filepath.lastIndexOf(".");
	    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if(ext!=".XLS" && ext!=".XLSX"){
	    	art.dialog.alert("仅限于上传EXCEL格式文档");
	    	return false;
	    }
	    $("#importExcel1").submit();
	}
</script>
</head>
<body>
	<div class = "control-group">
		<form id="contractSendForm" action="${ctx }/borrow/serve/customerServe/contractSendList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			<input id="ids" type="hidden" name="ids"/>
			<input id="contracts" type="hidden" name="conts"/>
			<input type="hidden" name="excelFlag" value="1">
			<input type="hidden" id="fileType" name="fileType" value="${contractFileSendView.fileType }">
		
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>
					<input type="text" class="input-medium" name="customerName" value="${contractFileSendView.customerName }"/>
					
					
					</td>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" readonly="readonly"  class="txt date input_text178" value="${contractFileSendView.storeName}"/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="orgCode" value="${contractFileSendView.orgCode}"/>
					</td>
					<td><label class="lab">合同编号：</label>
					<input type="text" class="input-medium" name="contractCode" value="${contractFileSendView.contractCode }"/></td>
				</tr>
				<tr>
					<td>
						<label class="lab">紧急程度：</label>
						<select class="select180" name="emergentLevel">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_cm_admin_urgent_flag')}" var="urgent">
								<option value="${urgent.value}"
									<c:if test="${contractFileSendView.emergentLevel == urgent.value}">
										selected = true
									</c:if>>
									${urgent.label}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">模式：</label>
						<select class="select180" name="model">
							<option value="">全部</option>
							<option value="1" <c:if test="${contractFileSendView.model == '1'}">selected = true</c:if>>TG</option>
							<option value="0" <c:if test="${contractFileSendView.model == '0'}">selected = true</c:if>>非TG</option>
						</select>
					</td>
					<td><label class="lab">渠道标识：</label> 
					     <%-- <form:input path="model" id="revisitStatusName" class="input_text178" readonly="true"/>
					     <form:hidden path="loanFlag" id="revisitStatus"/> --%>
					     <input type="text" name="loanFlagName" id="revisitStatusName" class="input_text178" value="${contractFileSendView.loanFlagName}" readonly="true"/>
					     <input type="hidden" name="loanFlag" id="revisitStatus" value="${contractFileSendView.loanFlag}"/>
						 <i id="revisitStatusId"  class="icon-search" style="cursor: pointer;"></i>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">是否无纸化：</label>
						<select class="select180" name="paperlessFlag">
							<option value="">全部</option>
							<option value="1" <c:if test="${contractFileSendView.paperlessFlag == '1'}">selected = true</c:if>>是</option>
							<option value="0" <c:if test="${contractFileSendView.paperlessFlag == '0'}">selected = true</c:if>>否</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" id="sousuo" onclick="document.forms[0].submit();" value="搜索"> 
			<input type="button" class="btn btn-primary" onclick="resets()" value="清除">
		</div>
	</div>
	
	<p class="mb5">
		<button class="btn btn-small" onclick="exportExcel()">导出excel</button>&nbsp;
		<button class="btn btn-small" onclick="batchReceive()">批量接收</button>
		<button class="btn btn-small" onclick="importExcel()">批量查询</button>
		<input type="hidden" id="conts" value="${cons}">
			<form id="importExcel1" style="display:none;" action="${ctx }/borrow/serve/customerServe/checkAll" method="post" enctype="multipart/form-data">
				<input type="file" id="file" name="file" onchange="fileChange()" style="position:relative; left:-75px; width:30px; 
						overflow:hidden; filter:alpha(opacity=0); opacity:0;cursor:hand;" size="1" />
						<input type="hidden" name="fileType" value="${contractFileSendView.fileType }">
			</form>
	</p>
	<sys:columnCtrl pageToken="outside"></sys:columnCtrl>
	<div class="box5">
		<table id="tableList" class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th><input type="checkbox" id="checkAll" onclick="checkAll(this)"/>序号</th>
					<th>客户姓名</th>
					<th>合同编号</th>
					<th>门店名称</th>
					<th>借款产品</th>
					<th>借款期限</th>
					<th>合同金额</th>
					<th>合同到期日期</th>
					<th>合同签订日期</th>
					<th>结清日期</th>
					<th>借款状态</th>
					<th>紧急程度</th>
					<th>合同状态</th>
					<th>渠道</th>
					<th>模式</th>
					<th>是否无纸化</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${page.list != null && fn:length(page.list)>0}">
					<c:set var="index" value="0"/>
					<c:forEach items="${page.list}" var="item">
						<c:set var="index" value="${index+1}"/>
						<tr>
							<td><input type="checkbox" name="checkItem" value="${item.id}">${index}</td>
							<td>${item.customerName}</td>
							<td>${item.contractCode}</td>
							<td>${item.storeName}</td>
							<td>${item.productType}</td>
							<td>${item.loanMonths}</td>
							<td><fmt:formatNumber value="${item.contractAmount}" pattern="#,##0.00"/> </td>
							<td><fmt:formatDate value="${item.contractEndDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatDate value="${item.contractFactDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatDate value="${item.settleDay}" type="date" pattern="yyyy-MM-dd"/></td>
							<td>${item.creditStatusName}</td>
							<td>${item.emergentLevelName}</td>
							<td>${item.sendStatusName}</td>
							<td>${item.loanFlag}</td>
							<td>
								<c:if test="${item.model=='1'}">
									TG
								</c:if>
								<c:if test="${item.model!='1'}">
									非TG
								</c:if>
							</td>
							<td>${item.paperlessFlag}</td>
							<td>
								<button class="btn_edit" onclick="receive('${item.id}')">接收</button>&nbsp;
								<button class="btn_edit" onclick="doOpencheck('${item.id}','8','contractSendForm')">退回</button>
								<button class="btn_edit" onclick="doOpenhis('${item.loanCode}','${contractFileSendView.fileType }')">历史</button>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${page.list == null || fn:length(page.list)==0}">
					<tr>
						<td colspan="18" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	
	<c:if test="${page.list != null && fn:length(page.list)>0}">
		<div class="pagination">${page }</div>
	</c:if>
	
	<div class="modal fade" id="deleteItem" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">删除数据</h4>
				</div>
				<div class="box1 mb10">
					<form id="delForm" action="${ctx }/borrow/serve/customerServe/deleteItem" method="post">
						<input type="hidden" id="id" name="id"/>
						<input type="hidden" id="applyId" name="applyId"/>
						<input type="hidden" id="loanCode" name="loanCode"/>
						<input type="hidden" id="operateStep" name="operateStep"/>
						<input type="hidden" id="formId">
						<table class="f14 table4" cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<font color="red"></font><br />
									<label class="lab">删除原因:</label> 
									<textarea class="input-xlarge" id="delReason" name="remarks" rows="3" 
												maxlength="200" style="width: 292px; height: 75px;vertical-align:top"></textarea>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<a type="button" class="btn btn-primary" data-dismiss="modal">取消</a>
					<a type="button" class="btn btn-primary" data-dismiss="modal" onclick="confirmDel()">确定删除</a>
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