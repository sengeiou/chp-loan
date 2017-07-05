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
		var msg = "${message}";
		if (msg != "" && msg != null) {
			top.$.jBox.tip(msg);
		};
		
		$("#confirmInput").click(function(){
			var expressNumber = $("#expressNumber").val();
			var sendCompany = $("#sendCompany").val();
			if (expressNumber == null || expressNumber == ""){
				art.dialog.alert("请输入快递单号");
				return;
			}
			if (sendCompany == null || sendCompany == ""){
				art.dialog.alert("请选择快递公司");
				return;
			}
			$.ajax({
				cache : false,
				type : "POST",
				url : "${ctx }/borrow/serve/customerServe/inputExpressNumber",
				dataType : "json",
				data : $("#expressForm").serialize(),
				async : false,
				success : function(data) {
	 				if (data == "1"){
		 				art.dialog.alert("录入成功");
						$("#waitSendForm").submit();
	 				} else {
	 					art.dialog.alert("录入失败");
	 				}
				}
			});
		});
	});
	
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#waitSendForm").attr("action", "${ctx}/borrow/serve/customerServe/waitSendList");
		$("#waitSendForm").submit();
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
		$("#waitSendForm").submit();
	}
	
	function checkAll(obj){
		if(obj.checked){
			$("input[name='checkItem']").attr('checked', true)
	    }else{
			$("input[name='checkItem']").attr('checked', false)
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
		$("#waitSendForm").attr("action", "${ctx }/borrow/serve/customerServe/exportExcel");
		$("#waitSendForm").submit();
		$("#ids").val("");
		$("#waitSendForm").attr("action", "${ctx }/borrow/serve/customerServe/waitSendList");
	}
	
	function importExcel(){
    	$("#file").click();
	}
	
	function fileChange(){
		var filepath=$("input[name='file']").val();
	    var extStart=filepath.lastIndexOf(".");
	    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if(ext!=".XLS" && ext!=".XLSX"){
	    	art.dialog.alert("仅限于上传EXCEL格式文档");
	    	return false;
	    }
	    $("#importExcel").submit();
	}
	
	function uploadCheck(){
		var filepath=$("input[name='file']").val();
	    var extStart=filepath.lastIndexOf(".");
	    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if(ext!=".XLS" && ext!=".XLSX"){
	    	art.dialog.alert("仅限于上传EXCEL格式文档");
	    	return false;
	    }
	    $("#importExcel").submit();
	}
	
	function inputExpressNumber(id, applyId, loanCode,sendStatus){
		$("#hid").val(id);
		$("#happlyId").val(applyId);
		$("#hloanCode").val(loanCode);
		$("#sendStatus").val(sendStatus);
		$("#expressNumber").val('');
		$("#sendCompany").trigger("change");
		$('#sendCompany').val('');
		$("#sendCompany").trigger("change");
		$('#inputExpressNumber').modal('toggle').css({
			width : '90%',   
			"margin-left" : (($(document).width() -  $("#inputExpressNumber").css("width").replace("px", "")) / 2),
			"margin-top" : (($(document).height() -  $("#inputExpressNumber").css("height").replace("px", "")) / 2)
		});
	}
	
	function settleProveLook(id){
		var url = "${ctx} /apply/contractAudit/writeTo?docId="+id;
	/* 	var url = ctx + "/borrow/serve/customerServe/settleProveLook?id=" + id; */
		art.dialog.open(url, {  
	        id: 'his',
	        title: '结清证明查看',
	        lock:true,
	        width:'100%',
	     	height:'100%'
	    }, false); 
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
		$(function(){
		document.onkeydown = function(e){
			var ev = document.all ? window.event : e;
			 if(ev.keyCode==13) {
		           $('#waitSendForm').submit();//处理事件
		     }
		}
		}); 
</script>
</head>
<body>
	<div class = "control-group">
		<form id="waitSendForm" action="${ctx }/borrow/serve/customerServe/waitSendList" method="post">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" />
			<input id="ids" type="hidden" name="ids"/>
			<input type="hidden" name="excelFlag" value="2">
			<input type="hidden" class="input-medium" id="fileType" name="fileType" value="${contractFileSendView.fileType }"/></td>
			
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>
					<input type="text" class="input-medium" name="customerName" value="${contractFileSendView.customerName }"/></td>
					<td>
						<label class="lab">门店：</label> 
						<input type="text" id="txtStore" name="storeName" maxlength="20" class="txt date input_text178" value="${contractFileSendView.storeName}"/> 
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="orgCode" value="${contractFileSendView.orgCode}"/>
					</td>
					<td><label class="lab">合同编号：</label>
					    <input type="text" class="input-medium" name="contractCode" value="${contractFileSendView.contractCode }"/></td>
				</tr>
				<tr>
					<td>
						<label class="lab">邮寄类别：</label>
						<select class="select180" name="fileType" disabled>
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_cm_admin_file_type')}" var="fType" >
								<option value="${fType.value}"   readonly="true" 
									<c:if test="${contractFileSendView.fileType == fType.value}">
										selected = true
									</c:if>>
									${fType.label}
								</option>
							</c:forEach>
						</select>
					</td>
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
				</tr>
				<tr>
					<td><label class="lab">渠道标识：</label> 
					     <%-- <form:input path="model" id="revisitStatusName" class="input_text178" readonly="true"/>
					     <form:hidden path="loanFlag" id="revisitStatus"/> --%>
					     <input type="text" name="loanFlagName" id="revisitStatusName" class="input_text178" value="${contractFileSendView.loanFlagName}" readonly="true"/>
					     <input type="hidden" name="loanFlag" id="revisitStatus" value="${contractFileSendView.loanFlag}"/>
						 <i id="revisitStatusId"  class="icon-search" style="cursor: pointer;"></i>
					</td>
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
			<button class="btn btn-small" onclick="importExcel()">导入excel</button>
			<form id="importExcel" style="display:none;" action="${ctx }/borrow/serve/customerServe/importExcel" method="post" enctype="multipart/form-data">
				<input type="file" id="file" name="file" onchange="fileChange()" style="position:relative; left:-75px; width:30px; 
						overflow:hidden; filter:alpha(opacity=0); opacity:0;cursor:hand;" size="1" />
						<input type="hidden" name="fileType" value="${contractFileSendView.fileType }">
			</form>
	</p>
	<div>
		<table id="tableList" class="table table-condensed table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" id="checkAll" onclick="checkAll(this)"/>序号</th>
					<th>客户姓名</th>
					<th>合同编号</th>
					<th>门店名称</th>
					<th>门店申请日期</th>
					<th>邮寄类别</th>
					<th>收件人姓名</th>
					<th>收件人电话</th>
					<th>收件人地址</th>
					<th>紧急程度</th>
					<th>
					<c:if test="${contractFileSendView.fileType != '0'}">
						邮寄状态
					</c:if> <c:if test="${contractFileSendView.fileType == '0'}">
						合同状态
					</c:if>
					</th>
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
							<td><fmt:formatDate value="${item.applyTime}" type="date" pattern="yyyy-MM-dd"/> </td>
							<td>${item.fileTypeName}</td>
							<td>${item.receiverName}</td>
							<td>${item.receiverPhone}</td>
							<td>${item.receiverAddress}</td>
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
								<button class="btn_edit" onclick="inputExpressNumber('${item.id}','${item.applyId}','${item.loanCode}','${item.sendStatus}')">录入快递编号</button>&nbsp;
                                <c:if test="${item.fileTypeName == '结清证明'}">
									<a class="btn_edit" href="${ctx}/borrow/serve/customerServe/downloadProof?pdfId=${item.docId}&name=${item.customerName}">下载</a>&nbsp;
									<button class="btn_edit" onclick="settleProveLook('${item.docId}')">查看</button>&nbsp;
								</c:if>
							<%-- 	<button class="btn_edit" onclick="deleteItem('${item.id}','${item.applyId}','${item.loanCode}','待办邮寄列表','waitSendForm')">删除</button>&nbsp; --%>
							    <button class="btn_edit" onclick="doOpencheck('${item.id}','7','waitSendForm')">退回</button>
								<button class="btn_edit" onclick="doOpenhis('${item.loanCode}','${contractFileSendView.fileType }')">历史</button>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${page.list==null || fn:length(page.list)==0}">
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
									<font color="red">删除后，可以在已删除合同列表查询</font><br />
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
	
	<div class="modal fade" id="inputExpressNumber" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">录入快递编号</h4>
				</div>
				<div class="box1 mb10">
					<form id="expressForm" action="${ctx }/borrow/serve/customerServe/inputExpressNumber" method="post">
						<input type="hidden" id="hid" name="id"/>
						<input type="hidden" id="happlyId" name="applyId"/>
						<input type="hidden" id="hloanCode" name="loanCode"/>
						<input type="hidden" id="sendStatus" name="sendStatus"/>
						<input type="hidden" id="fileType" name="fileType" value="${contractFileSendView.fileType }"/>
						<table id="backTB" class="f14 table4" cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td>
									<label class="lab">快递编号：</label>
									<input type="text" class="input-medium required" name="expressNumber" id="expressNumber" />
								</td>
							</tr>
							<tr>
								<td>
									<label class="lab">快递公司：</label>
									<select class="select180" name="sendCompany" id="sendCompany">
										<option value="">请选择</option>
										<c:forEach items="${fns:getDictList('jk_cm_admin_deliver')}" var="deliver">
											<option value="${deliver.value}">
												${deliver.label}
											</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<a type="button" class="btn btn-primary" data-dismiss="modal" id="confirmInput">确定</a>
				</div>
			</div>
		</div>
	</div>
	 <script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-230,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>