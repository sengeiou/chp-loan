<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>蓝补金额修改</title>

<script type="text/javascript" src="${context }/js/payback/paybackBlueUpdate.js"></script>
<meta name="decorator" content="default"/>

</head>
<body>
 <div class="box2 mb10">
  <form id="inputForm" >
  <h2 class=" f14 pl10 ">退款</h2>
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="95%">
            <tr>
                <td><label class="lab">蓝补金额：</label>
                <input type="hidden" name="contractCode" value="${payback.contractCode}" />
               <input type="text" class="input_text178" id = "paybackBuleAmount" name="paybackBuleAmount" value="<fmt:formatNumber value="${payback.paybackBuleAmount }" pattern='0.00'/>" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"/>
                </td>
                <td>
                  
                </td>
             </tr>
			 <tr>
                <td cols='2'><label class="lab">金额修改原因：</label>
            <textarea rows="5" cols="6" maxlength="150" style="width:200px;height: 80px;" class="required" name="paybackBuleReson"></textarea>
                </td>
             </tr>  
        </table>
        
        <div class="tright pr30">
       <input type="submit" id="submitBtn" value="提交"  class="btn btn-primary"/> 
        
    
        </div>
 </form>  
</div>
</body>
</html>
