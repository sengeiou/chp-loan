<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/consult/popUpWindows.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  infos.datavalidate();
  });
</script>
<meta name="decorator" content="default"/>

</head>
<body>
 <div class="box2 mb10">
  <form id="inputForm">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="95%">
            <tr>
                <td><label class="lab">客户姓名：</label>
                <input type="text" name="customerName"  value="${customerName}" disabled="disabled" class="input_text178"/>
                <input type="hidden" name="customerName" value="${customerName}"/>
                <input type="hidden" name="id" value="${consultId}"/>
                <input type="hidden" name="customerCode" value="${customerCode}"/>
                </td>
                <td><label class="lab">手机号码：</label>
                   <input type="text" name="customerMobilePhone" value="${customerMobilePhone}" disabled="disabled"  class="input_text178"/>
                   <input type="hidden" name="customerMobilePhone" value="${customerMobilePhone}"/>
                </td>
             
             </tr>
			  <tr>
                <td><label class="lab"><span style="color: #ff0000">*</span>本次沟通时间：</label>
                <input id="d4311" name="consCommunicateDate" type="text" class="required input_text178" size="10"  
                   onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: pointer"/>
                </td>
                <td><label class="lab"><span style="color: #ff0000">*</span>下一步：</label>
                    <select name="consOperStatus" class="required select180">
                       <option value="">请选择</option>
                       <c:forEach items="${fns:getDictList('jk_next_step')}" var="item">
		     			<c:if test="${item.label!='已进件'  && item.label!='待申请'}">
		      				<option value="${item.value}">${item.label}</option>
		    			</c:if>
		 			 </c:forEach>
                    </select>
                </td>
             </tr>
			  <tr>
                <td colspan="2"><label class="lab"><span style="color: #ff0000">*</span>沟通内容描述：</label>
                   <textarea  name="consLoanRecord" class="textarea_refuse required"  rows="10" cols="50"></textarea></td>      
             </tr>
        </table>
        <div class="tright pr30"><input type="submit" id="submitBtn" value="提交" class="btn btn-primary"></input> </div>
 </form>  
</div>
</body>
</html>
