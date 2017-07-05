<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银行平台接口列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#centerapplyPayForm").attr("action", "${ctx}/borrow/payback/deductPlantLimit/queryPage");
		$("#centerapplyPayForm").submit();
		return false;
	}
	 $(document).ready(function() {
			$("#delete").live("click",function(){
				var url = $(this).attr("url");
				art.dialog.confirm("您确定要删除该条数据吗？",function() {
						window.location.href = url;
				})
			});
			
			$("#addDue").click(function(){
				window.location.href = ctx + "/borrow/payback/deductPlantLimit/goSaveOrEdit";
			});
	  });

	//点击清除按钮调用的的方法
	  function resets(){
	  	// 清除text	
	    $(":input").val("");
	  	$("#centerapplyPayForm").submit();
	  }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/borrow/payback/deductPlantLimit/queryPage">划扣限制配置列表</a></li>
		<li><a href="${ctx}/borrow/payback/plantskipordernew/queryPage">平台跳转顺序列表</a></li>
	</ul>
	<div class="control-group">
	<form method="post" id="centerapplyPayForm"  action="${ctx}/borrow/payback/deductPlantLimit/queryPage">
       <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
            <tr>
                <td><label class="lab">平台：</label>
                   <select class="select180" name="plantCode">
				    <option value="">请选择</option>
			         <c:forEach var="plant" items="${fns:getDictList('jk_deduct_plat')}">
			         	<c:if test="${plant.value != '4'}">
			         
						<option value="${plant.value }" <c:if test="${record.plantCode==plant.value }">selected</c:if> >${plant.label}</option>
					   </c:if>
					 </c:forEach>
				  </select>
                </td>
            </tr>
        </table>
        <div class="tright pr30 pb5">
        <button class="btn btn-primary" onclick="document.forms[0].submit();">搜索</button>
        <button class="btn btn-primary" onclick ='resets()'>清除</button></div>
   </form>
   </div>
      <sys:message content="${message}"/>
	<p class="mb5"> 
    <button id="addDue" role="button" class="btn btn-small">新增</button>
	<div class="box5"> 
        <table  class="table  table-bordered table-condensed" >
        <thead>
            <tr>
               	<th>序号</th>
                <th>平台</th>
                <th>余额不足比例(%)</th>
                <th>余额不足基数</th>
                <th>失败率(%)</th>
                <th>失败基数</th>
                <th>失败笔数</th>
                <th>金额条件1</th>
                <th>金额条件2</th>
                <th>操作</th>
            </tr>
         </thead>   
        <c:if test="${page.list!=null && fn:length(page.list)>0}">
         <c:forEach items="${page.list}" var="bankPlantPort" varStatus="status">
            <tr>
                 </td>
                 <td>${(page.pageNo-1)*page.pageSize+status.count}</td>
                 <td>${bankPlantPort.plantName}</td>
                 <td>
                    ${bankPlantPort.notEnoughProportion}
                 </td>
                   <td>
                    ${bankPlantPort.notEnoughBase}
                 </td>
                  <td>
                    ${bankPlantPort.failureRate}
                 </td>
                  <td>
                    ${bankPlantPort.failureBase}
                 </td>
                  <td>
                    ${bankPlantPort.failureNumber}
                 </td>
                  <td>
                    ${bankPlantPort.moneySymbol1} ${bankPlantPort.deductMoney1}  ${bankPlantPort.deductType1}
                  </td>
                  <td>
                    ${bankPlantPort.moneySymbol2}  ${bankPlantPort.deductMoney2} ${bankPlantPort.deductType2}
                  </td>
                   <td>
                   <a href="${ctx}/borrow/payback/deductPlantLimit/goSaveOrEdit?id=${bankPlantPort.id }">
					    <button class="cf_btn_edit" onclick="">修改</button>
			       </a>
				   <input id="delete" type="button" url="${ctx}/borrow/payback/deductPlantLimit/delete?id=${bankPlantPort.id }" value="删除" class="cf_btn_edit" style="cursor:pointer;">
                  </td>
            </tr>
			</c:forEach>
		</c:if>
        <c:if test="${ page.list==null || fn:length(page.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
        </c:if>
      </table>
    </div>
<div class="pagination">${page}</div>
</body>
</html>