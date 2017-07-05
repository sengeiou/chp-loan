<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="decorator" content="default" />  
<script type="text/javascript" src="${context}/js/delivery/delivery.js"></script>
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#delForm").attr("action", "${ctx}/borrow/taskDeliveryInfo/queryTaskDelivery");
	$("#delForm").submit();
	return false;
	
}
function show() {
	if (document.getElementById("T1").style.display == 'none') {
		document.getElementById("showMore").src = '${context}/static/images/down.png';
		document.getElementById("T1").style.display = '';
		document.getElementById("T2").style.display = '';
	} else {
		document.getElementById("showMore").src = '${context}/static/images/up.png';
		document.getElementById("T1").style.display = 'none';
		document.getElementById("T2").style.display = 'none';
	}
}
</script>
<title>已交割列表</title>
</head>
<body>
	<div>
    	<div class="control-group">
			<form id="delForm" method="post">
				<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
        		<table cellpadding="0" cellspacing="0" border="0" width="100%">
            		<tr>
                		<td>
                			<label class="lab" >客户姓名：</label><input type="text" class="input_text178" name="custName" value="${params.custName }"/>
                		</td>
                		<td>
                			<label class="lab">合同编号：</label>
                			<input type="text" class="input_text178" name="contractCode" value="${params.contractCode }" />
                		</td>
                	 	<td>
                			<label class="lab">申请时间：</label>        
                			<input id="startTime" class="input_text70 Wdate" onchange="checkDate(this)" name="startDate" type="text" readonly="readonly"  value="${params.startTime }"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />-<input id="endTime" class="input_text70 Wdate" onchange="checkDate(this)" name="endDate" type="text" readonly="readonly"  value="${params.endTime }"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
						</td>	 			 
            		</tr>
            		<tr>
			    		<td>
			    			<label class="lab" style="float:left;height:30px;line-height:30px">门店名称：</label>
			    			<c:if test="${isManager==false }">
			    				<input name="strote" value="${params.strote}" type="hidden"></input>
			    				<input name="stroteshow" value="${orgNametemp}"  class="input_text178"  type="text" readonly="readonly"></input>
			    			</c:if>
			    			<c:if test="${isManager==true }">
				    			<select name="strote" class="select180" >
									<option value="" selected>请选择</option>
									<c:forEach var="org" items="${orgs }">
										<option value="${org.orgCode }" <c:if test="${params.strote==org.orgCode }">selected</c:if> >${org.orgName }</option>							
									</c:forEach>
								</select>
			    			</c:if>
						</td>
                		<td>
                			<label class="lab">客户经理：</label>
                			<input type="text"  class="input_text178" name="manager" value="${params.manager }" />
                		</td>
                		<td>
                			<label class="lab">团队经理：</label>
                			<input type="text" class="input_text178" name="teamManager" value="${params.teamManager }" />
                		</td>
            		</tr>
					<tr id="T1" style="display:none">
			    		<td>
			    			<label class="lab" style="float:left;height:30px;line-height:30px">门店名称(新)：</label>
							<select name="newStrote" class="select180">
							<option value="" selected>请选择</option>
							<c:forEach var="org" items="${orgs }">
								<option value="${org.orgCode }" <c:if test="${params.newStrote==org.orgCode }">selected</c:if> >${org.orgName }</option>							
							</c:forEach>
						</select>
			    		</td>
                		<td>
                			<label class="lab">客户经理(新)：</label>
                			<input type="text"  class="input_text178" name="newManager" value="${params.newManager }" />
                		</td>
                		<td>
                			<label class="lab">团队经理(新)：</label>
                			<input type="text" class="input_text178" name="newTeamManager" value="${params.newTeamManager }" />
                		</td>
            		</tr>
        		</table>
        		<div class="tright pr30 pb5">
        		<input type="submit" class="btn btn-primary" value="搜索" >
        		<input type="button" class="btn btn-primary" value="清除" id="removeBtn"/>
        <div class="xiala">
        		<center>
					<img src="${context}/static/images/up.png" id="showMore"  onclick="javascript:show();"></img>
        		</center>
        	      	</div>      
        	</div> 
        	</div> 
        	<sys:columnCtrl pageToken="deliveryy"></sys:columnCtrl>  
   		<div class='box5'> 
       		<table id="tableList" class="table table-hover table-bordered table-condensed">
          		<thead>  
          			<tr>                
                		<th>序号</th>
                		<th>客户姓名</th>               
                		<th>门店名称</th>
                		<th>团队经理</th>
                		<th>客户经理</th>
						<th>客服</th>
						<th>外访</th>
                		<th>门店名称(新)</th>
                		<th>团队经理(新)</th>
                		<th>客户经理(新)</th>
						<th>客服(新)</th>
						<th>外访(新)</th>
                		<th>交割申请时间</th>
                		<th>交割原因</th>
                		<th>申请人</th>
						<th>驳回原因</th>
						<th>交割状态</th>
             		</tr>
             	</thead>
              	<tbody>
             		<c:forEach var="t" items="${taskPage.list }" varStatus="sta">
           				<tr>
                			<td>${sta.count }</td>
                			<td>${t.loanCustomerName }</td> 
                			<td>${t.orgName }</td>
                			<td>${t.teamManagerName }</td>
                			<td>${t.managerName }</td>
                			<td>${t.customerServiceName }</td>
				 			<td>${t.outbountByName }</td>
                			<td>${t.newOrgName }</td>
                			<td>${t.newTeamManagerName }</td>
                			<td>${t.newManagerName }</td>
                			<td>${t.newCustomerServiceName }</td>
                			<td>${t.newOutbountByName }</td>
							<td><fmt:formatDate value="${t.deliveryApplyTime }" pattern="yyyy-MM-dd"/> </td>
                			<td>
                				<p style="width:50px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;" title=${t.deliveryReason }>               				
		                			${t.deliveryReason }	                		
                				</p>
                			</td>
                			<td>${t.entrustManName }</td>
                			<td>
                				<p style="width:50px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;" title=${t.rejectedReason }>
                					<c:if test="${t.deliveryResult != '3'}">                				
                					</c:if>
                					<c:if test="${t.deliveryResult == '3'}">                				
	                					${t.rejectedReason }
                					</c:if>
                				</p>
                			</td>        				
                			<td>${t.deliveryResultLabel}</td>
            			</tr>
		 			</c:forEach>
		 			<c:if test="${taskPage.list==null || fn:length(taskPage.list)==0}">
						<tr>
							<td colspan="17" style="text-align: center;">没有符合条件的数据</td>
						</tr>
					</c:if> 
		 		</tbody>	 
         	</table>
    	</div>
 	<div class="pagination">${taskPage }</div>
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
