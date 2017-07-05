<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>批量冲抵确认页面</title>
</head>
<body>
	<div class='diabox01'>
    <h3 class="pop_title">批量冲抵信息</h3>
        <div class="pop_content">
            <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%">
                 <tr style="color:red">
                     <td width="60%" align="center">冲抵金额：2,000</td>
                     <td width="40%">冲抵笔数：2</td>
                 </tr>
                 <tr style="color:red">
                     <td rowspan="2" align="right">是否确认冲抵？</td>
                 </tr>
            </table>
            <div class="tcenter pb10 pt10 pr30"><button class="btn_tj mr10" onclick="javascript:confirmQuit();">确认</button></div>
    </div>
 </div>
	
</body>
</html>