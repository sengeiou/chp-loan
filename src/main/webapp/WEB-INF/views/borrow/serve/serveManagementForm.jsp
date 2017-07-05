<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/serve/contractSendSerList.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  infos.datavalidate();
		/**
		 * @function 点击其他 显示输入框
		 */
		$('#settleCause').click(function(){
		      $('#settleCauseElse').css('display','block'); 
		})
		$('#settleCause0').click(function(){
		      $('#settleCauseElse').css('display','none'); 
		})
	    $('#settleCause1').click(function(){
		      $('#settleCauseElse').css('display','none'); 
		})
      //提前 结清  90 默认选中提前结清  89 结清项   其他项选中其他
	   if($("#creditStatus").val() == 90 ){
		   $('#settleCause1').attr("checked","checked");
	   }else if($("#creditStatus").val() == 89){
		   $('#settleCause0').attr("checked","checked");
	   }else{
		   $('#settleCause').attr("checked","checked");
		   $('#settleCauseElse').css('display','block'); 
	   }
		
		
  });
</script>
<meta name="decorator" content="default"/>
</head>
<body>
 <div class="box2 mb10">
  <form id="inputForm">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="95%">
              <tr>
                  <input type="hidden" name="id" value="${id}"/>
                  <input type="hidden" name="contractCode" value="${contractCode}"/>
                  <input type="hidden" name="loanCode" value="${loanCode}"/>
                   <input type="hidden" name="fileType" value="1"/>
                 <input type="hidden" id="creditStatus" value="${creditStatus}"/>
                 <input type="hidden" name="type" id="type" value="${type}"/>
                 <input type="hidden" name="loanFlag" id="loanFlag" value="${loanFlag}"/>
               <td align="left"><label class="lab"><span style="color: #ff0000">*</span>选择结清原因,点击提交按钮将会制作结清通知书</label></td>
              <tr>
              	  <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"    style="margin-top:1px"  id="settleCause0"  name="settleCause" value="0"><label class="lab">原合同到期,借款人还款义务履行完毕</label></td>
              </tr>
              <tr>
              	  <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" style="margin-top:1px" id="settleCause1" name="settleCause" value="1"><label class="lab">原合同期内,借款人提前清偿全部借款</label></td>
              </tr>
              <tr>
              	<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" style="margin-top:1px" id="settleCause"  name="settleCause"  value="2"><label class="lab">其他</label>
                </td>
              	<td id="settleCauseElse" style="display: none;" >                     
              		<form:textarea path="settleCauseElse" name="settleCauseElse" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge  required"/>
              	</td>
              </tr>
        </table>
        <div class="tright pr30"><input type="submit" id="submitBtn" value="提交" class="btn btn-primary"></input> </div>
 </form>  
</div>
</body>
</html>
