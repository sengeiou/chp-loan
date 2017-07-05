<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>交割办理详情</title>
<script type="text/javascript">
// 驳回通过选择
function show(a){
  if(a==3){
	  $("#WWW").show();
	  $("input[name='deliveryResult']").val("3");
  }else{
	  $("#WWW").hide();
	  $("input[name='deliveryResult']").val("2");
  }
}
// 验证是否填写驳回原因
$(function(){
	$("#subBtn").click(function(){
		var result = $("input[name='deliveryResult']").val();
		if (result == 3) {
			var reason = $("#rejectedReason").val();
			if (reason != null && "" != reason) {
				$("#subForm").submit();
			}else{
				art.dialog.alert("请填写驳回原因!");
			}
		}else{
			$("#subForm").submit();
		}		
	});
});
</script>
</head>
<body>
		<form action="${ctx }/borrow/delivery/deliveryResult?loanCode=${dv.loanCode }" id="subForm" method="post">
     		<table class="table  table-bordered table-condensed table-hover">							
				<tr>
                    <th></th>
                    <th>旧数据</th>
					<th>新数据</th>
                </tr>
               
                <tr>
                    <td>门店名称</td>
                    <td>${dv.orgName }</td>
					<td>${dv.newOrgName }</td>
                </tr>
                
				<tr>
					<td>团队经理</td>
                    <td>${dv.teamManagerName }</td>
		            <td>${dv.newTeamManagerName }</td>
				</tr>
				
				<tr>
				    <td>客户经理</td>
                    <td>${dv.managerName }</td>
					<td>${dv.newManagerName }</td>
				</tr>
				
				<tr>
				    <td>客服</td>
                    <td>${dv.customerServiceName }</td>
					 <td>${dv.newCustomerServiceName }</td>
				</tr>
				
				<tr>
				    <td>外访员</td>
                    <td>${dv.outbountByName }</td>
					 <td>${dv.newOutbountByName }</td>
				</tr>
				 
                <tr>
                   <td>交割原因</td> 
				   <td colspan='2'>${dv.deliveryReason }</td>
                </tr>
                
				<tr>
                   <td> 附件</td> 
				   <td colspan='2'>
				   	
				   	<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0" width="100%" height="400"
						src="${dv.imageUrl }">
					</iframe>
					
				   </td>
                </tr>
				
				<tr>
                   <td>审核结果</td> 
				   <td colspan='2'  > 
				      <input type='radio' checked="checked" name='deliveryResult' value='2' onclick='show(2)' > 通过  &nbsp;&nbsp;&nbsp; 
					  <input type='radio' name='deliveryResult' value='3' onclick='show(3)'> 驳回
				   </td>
                </tr>
                
				<tr id="WWW" style="display:none">
                   	<td>驳回理由</td> 
				   	<td colspan='2'  >  
				   		<textarea class="textarea_refuse" name="rejectedReason" id="rejectedReason"></textarea>
				   	</td>
                </tr>
                
				<tr>
                   <td></td> 
				   <td colspan='2'  > 
					   <input type="button" value="提交" class="btn btn-primary" id="subBtn" />
					   <input type="button" value="取消" class="btn btn-primary" onclick="location.href='javascript:history.go(-1);'" />
				   </td> 
				 </tr>				
            </table>
        </form>    
  
</body>
</body>
</html>
