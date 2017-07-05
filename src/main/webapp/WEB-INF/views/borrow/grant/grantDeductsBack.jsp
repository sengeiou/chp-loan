<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${context}/js/grant/grantDeducts.js" type="text/javascript"></script>
<meta name="decorator" content="default"/>
	<title>放款划扣追回</title>
</head>
<body>
<div class='diabox01'>
    <h3 class="pop_title">确认已经追回</h3>
        <div class="pop_content">
            <table class=" table4" cellpadding="0" cellspacing="0" border="0" width="100%"> 
                 <tr>
                     <td>
					 <span style="color:red;">*</span>备注：
					 <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea rows="" cols="" class="textarea_refuse" id="backReason"></textarea></p>
                     </td> 
				</tr>
				<tr>
					<td><span style="color:red;">最多输入1500字符</span></td>
				</tr>
                
            </table>
       
    <div class="tcenter pb10 pt10 pr30"><button class="btn_tj mr10" id="backSure">确认</button><button class="btn_tj" onclick="javascript:window.close()">取消</button></div>
      </div>
 </div>

</body>
</html>