<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借_合同费率列表</title>
<meta name="decorator" content="default"/>
<style type="text/css">
.input-append {
    height: 30px;
    float:left;
    margin-left:3px;
    line-height:30px;
    
   
    }
    .input-small{
    	float:left;
    	width:138px;
    	border-radius:3px;
    	margin:1px 0;
    }
   
</style>
<script src="${context}/js/transate/transate.js"></script>
<script language="javascript"> 
// 	function page(n, s) {
// 		if (n) $("#pageNo").val(n);
// 		if (s) $("#pageSize").val(s);
// 		$("#traForm").attr("action", "${ctx}/borrow/transate/transateInfo");
// 		$("#traForm").submit();
// 		return false;
// 	}	
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

</head>
<body>	
	<div class="control-group">
		<form  id="traForm" >
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />					
        	<table cellpadding="0" cellspacing="0" border="0" width="100%">       	
            	<tr>
					<td>
                		<label class="lab">客户姓名：</label>
                		<input type="text" name="userName" class="input_text178" >
                	</td>
                	<td>
                		<label class="lab">门店名称：</label>
                		<input type="text" name="userName" class="input_text178" >
                	</td>
                	<td>
                		<label class="lab">产品类型：</label>
                		<select name="products" class="select180">
							<option value="">请选择</option>
						</select>
                	</td>
				</tr>
				<tr>
                	<td>
                		<label class="lab">身份证号：</label>
                		<input type="text" name="userName" class="input_text178" >
                	</td>
                	<td>
                		<label class="lab">合同编号：</label>
                		<input type="text" name="userName" class="input_text178" >
                	</td>
                	<td>
                		<label class="lab">终审日期：</label>
                		<input type="text" name="userName" class="input_text178" >
                	</td>
                </tr>
                <tr>
               		<td>
                		<label class="lab">产品类型：</label>
                			<select name="products" class="select180">
								<option value="">请选择</option>
							</select>
                	</td>
                	<td>
                		<label class="lab">渠道：</label>
                			<select name="products" class="select180">
								<option value="">请选择</option>
							</select>
                	</td>
                	<td>
                		<label class="lab">是否电销：</label>
                			<select name="products" class="select180">
								<option value="">请选择</option>
							</select>
                	</td>
                </tr>	
        	</table>       
        	 
        	<div class="tright pr30 pb5">
        		<input type="submit" class="btn btn-primary" value="搜索" >
        		<input type="button" class="btn btn-primary" value="清除" id="tRemoveBtn" />
        <div class="xiala">
        		<center>
					<img src="${context}/static/images/up.png" id="showMore"  onclick="javascript:show();"></img>
        		</center>
        	      	</div>      
        	</div> 
        	</div>   
        </form>
        <div class="box5" style="height:400px;">
           <table class="table table-hover table-bordered table-condensed" style="margin-bottom:100px">
             <thead>
                <tr>
                    <th>序号</th>
					<th>合同编号</th>
					<th>客户姓名</th>
                    <th>共借人姓名</th>
                    <th>门店名称</th>
                    <th>管辖省份</th>
                    <th>审批金额</th>
                    <th>借款期限</th>
                    <th>产品类型</th>
                    <th>终审日期</th>
                    <th>借款状态</th>
                    <th>是否电销</th>
                    <th>渠道</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            	<tr>
            		<td>1</td>
            		<td>G(俞)借字(2015)第0030019号</td>
            		<td>白羊</td>
            		<td>红宝石</td>
            		<td>洛阳汇金中心</td>
            		<td>河南省</td>
            		<td>33333.00</td>
            		<td>90</td>
            		<td>GPS</td>
            		<td>2015/12/09</td>
            		<td>待审核利率</td>
            		<td>是</td>
            		<td></td>
            		<td><input type="button" value="办理"/></td>
            	</tr>
<%--             <c:if test="${traPage.list==null || fn:length(traPage.list)==0}"> --%>
<!-- 				<tr> -->
<!-- 					<td colspan="19" style="text-align: center;">没有符合条件的数据</td> -->
<!-- 				</tr> -->
<%-- 			</c:if> --%>
            </tbody>
          </table>
	</div>
<div class="pagination">
	
</div>

</body>
</html>