<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<%-- <script src="${context}/js/contract/contractAudit.js" type="text/javascript"></script> --%>
<script type="text/javascript">
cancelFlagRetVal="";
 $(document).ready(function(){
	 var errorCount=$("#errorCount").val();
	 var sucessCount=$("#sucessCount").val();
	$('#ConfirmBtn').bind('click',function(){
		var win = art.dialog.open.origin;//来源页面
		var param=$('#batchColl',win.document).val();
		var batchValue = $('#batchValue option:selected').val();
		var paramBack=$('#batchCollBack',win.document).val();
		var errorCountZCJ=$("#errorCountZCJ",win.document).val();
		var sucessCountZCJ=$("#sucessCountZCJ",win.document).val();
		
		
		if(batchValue=='' || batchValue==null){
			$('#batchValueMessage').html("请选择需要添加的渠道");
			return false;
		 }else{
			 $('#batchValueMessage').html(""); 
		 }
		var batchLabel = $('#batchValue option:selected').text();
		if(batchLabel=='ZCJ'){
			param=paramBack;
			errorCount=errorCountZCJ;
			sucessCount=sucessCountZCJ;
		}
		
		if(sucessCount==0 || param=="a"){
			 art.dialog.close();
			 art.dialog.alert("符合条件修改"+sucessCount+"条数据,不符合条件修改"+errorCount+"条数据");
			 return false;
		}
		// 如果父页面重载或者关闭其子对话框全部会关闭
		$.ajax({
			type : 'post',
			url : ctx + '/apply/contractAudit/asynChangeFlag',
			data : {
				'batchColl':param,
				'attributName':$('#attributName').val(),
				'batchValue':batchValue,
				"batchLabel":batchLabel
			},
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
		      cancelFlagRetVal = result;
			},
			error : function() {
				art.dialog.alert('请求异常！', '提示信息');
			}
		});
		if(cancelFlagRetVal==true || cancelFlagRetVal=='1'){
			
			$('#searchBtn',win.document).click();
		    art.dialog.close();
		    art.dialog.alert("符合条件修改"+sucessCount+"条数据,不符合条件修改"+errorCount+"条数据");
		}
	}); 
   $('#CancelBtn').bind('click',function(){
	   art.dialog.close();
	}); 
	 
 });
 
</script>
</head>
<body>
<div id="auditRateBackWindow">
 <div class="control-group">
 <form id="backForm">
  <table>
     <thead>
       <tr><td colspan="2">提示</td></tr>
     </thead>
     <tbody>
        <tr>
           <td>选择需要添加的渠道：</td>
           <td>
               <span style="color:red">*</span>
               <select name="batchValue" id="batchValue" class="input-medium">
				 <option value=''>请选择</option>
				 <c:if test="${channels==null || fn:length(channels)==0}">
			    	 <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="item">
				       <c:if test="${item.label!='TG' && item.label!='XT01'}">
				  	     <option value='${item.value}'>${item.label}</option>
				       </c:if>
				     </c:forEach>
				 </c:if>
				 <c:if test="${channels!=null && fn:length(channels)>0}">
				    <c:forEach items="${channels}" var="item">
			     	     <option value='${item.code}'>${item.name}</option>
				     </c:forEach>
				 </c:if>
				</select>
				<span id="batchValueMessage" style="color:red;"></span>
      			 <input type="hidden" id="batchColl" value="${batchColl}" name="batchColl"/>
      			 <input type="hidden" value="${flowFlag}" id="flowFlag" name="flowFlag"/>
      			 <input type="hidden" value="${redirectUrl}" id="redirectUrl" name="redirectUrl"/>
      			 <input type="hidden" value="${errorCount}" id="errorCount" name="errorCount"/>
      			 <input type="hidden" value="${sucessCount}" id="sucessCount" name="sucessCount"/>
      			 <input type="hidden" id="attributName" value="${attributeName}" name="attributName"/>
  		   </td>
         </tr>
         <tr><td colspan="2" style="text-align:right;">
              <input type="button" class="btn btn-primary" value="确认" id="ConfirmBtn"></input>
              <input type="button" class="btn btn-primary" value="取消" id="CancelBtn"></input>
            </td>
        </tr>
     </tbody>
  </table>
 </form>
  </div>
</div>
</body>
</html>
