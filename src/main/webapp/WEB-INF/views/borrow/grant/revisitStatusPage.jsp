<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script src="${context}/js/grant/revisitStatusDeal.js" type="text/javascript"></script>
<title>回访状态</title>
<script type="text/javascript">
$(document).ready(function(){
	if("${isFailBtn}" !="" ){
		document.getElementById("T1").style.display = '';
	}
	revisitStatusObj.checkAll('selectAll',"${isFailBtn}");
});
</script>
</script>
</head>
<body>
 <table id="contentTable" class="table table-striped table-bordered table-condensed">
       <tr>
         <th><input type="checkbox" id="selectAll">全选</input></th>
         <th>回访状态</th>
       </tr>
       
         <tr>
         	<td>
         		<input name="revisitStatusCheck" id="revisitStatusCheck" sname="成功" type="checkbox" value="1"></input> 
         	</td>
            <td>
              <span>成功</span>
            </td>
         </tr>
         <tr>
         	<td>
         		<input name="revisitStatusCheck" id="revisitStatusCheck" sname="待回访" type="checkbox" value="0"></input> 
         	</td>
         	<td>
         		<span> 待回访</span>
         	</td>
         </tr>
          <tr>
         	<td>
         		<input name="revisitStatusCheck" id="revisitStatusCheck" sname="空" type="checkbox" value="kong"></input>
         	</td>
         	<td>
         		<span>空</span>
         	</td>
         </tr>
         <tr id="T1" style="display:none">
         	<td>
         		<input id="T2" name="revisitStatusCheck" id="revisitStatusCheck" sname="失败" type="checkbox" value="-1"></input> 
         	</td>
         	<td>
         		<span>失败</span>
         	</td>
         </tr>
       
 </table>
</body>
</html>