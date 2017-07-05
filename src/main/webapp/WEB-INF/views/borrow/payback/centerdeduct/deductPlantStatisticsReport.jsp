<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>划扣平台统计报表</title>
<script type="text/javascript" src="${context}/js/payback/deductPlantStatisticsReport.js"></script>
<script src="../../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<link media="screen" rel="stylesheet" href="../../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#deductPlant").attr("action", "${ctx}/borrow/payback/deductPlantStatisticsReport/queryPage");
	$("#deductPlant").submit();
	return false;
}
</script>
</head>
<div>
<div>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
             
		     <div id="onLineTable" class='table1'>
		      <div class="control-group">各平台划扣失败比例</div>
		      <div >
                    <label class='lab'>中金失败笔数：</label>
                    <input   name="failureRate"   id ="failureRateId"  readonly="true"    value= ${report.zjfailureNumber }> 
					<label class='lab'>中金划扣笔数：</label>
		            <input    name="notEnoughProportion"  id="notEnoughProportionId" readonly="true"    value= ${report.zjdeductNumber }> 
		            <label class='lab'>比例（%）：</label> 
		            <input   name="notEnoughBase"    id ="notEnoughBaseId"    readonly="true"   value= ${report.zjfailureRate }>
			   </div>
			   <div >
			        <label class='lab'>富友失败笔数：</label>
		            <input   name="failureRate"   id ="failureRateId"         readonly="true"    value= ${report.fyfailureNumber }> 
		            <label class='lab'>富友划扣笔数：</label>
		            <input   name="failureBase"    id ="failureBaseId"     readonly="true"      value= ${report.fydeductNumber }>
		            <label class='lab'>比例（%）：</label>
		            <input    name="failureNumber"     id ="failureNumberId"  readonly="true"    value= ${report.fyfailureRate }>
		        </div>
		          <div >
			        <label class='lab'>卡联失败笔数：</label>
		            <input   name="failureRate"   id ="failureRateId"     readonly="true"       value= ${report.klfailureNumber }> 
		            <label class='lab'>卡联划扣笔数：</label>
		            <input   name="failureBase"    id ="failureBaseId"     readonly="true"      value= ${report.kldeductNumber }>
		            <label class='lab'>比例（%）：</label>
		            <input    name="failureNumber"     id ="failureNumberId"   readonly="true"   value= ${report.klfailureRate }>
		        </div>
		        
		         <div >
			        <label class='lab'>畅捷失败笔数：</label>
		            <input   name="failureRate"   id ="failureRateId"       readonly="true"      value= ${report.cjfailureNumber }> 
		            <label class='lab'>畅捷划扣笔数：</label>
		            <input   name="failureBase"    id ="failureBaseId"   readonly="true"      value= ${report.cjdeductNumber }>
		            <label class='lab'>比例（%）：</label>
		            <input    name="failureNumber"     id ="failureNumberId"   readonly="true"     value= ${report.cjnotEnoughProportion }>
		        </div>
		        
		        <div >
			        <label class='lab'>通联失败笔数：</label>
		            <input   name="failureRate"   id ="failureRateId"      readonly="true"      value= ${report.tlfailureNumber }> 
		            <label class='lab'>通联划扣笔数：</label>
		            <input   name="failureBase"    id ="failureBaseId"    readonly="true"      value= ${report.tldeductNumber }>
		            <label class='lab'>比例（%）：</label>
		            <input    name="failureNumber"     id ="failureNumberId"   readonly="true"     value= ${report.tlfailureRate }>
		        </div>
	         </div>
		     <div id="onLineTable" class='table1'>
		      <div class="control-group">各平台余额不足划扣失败比例</div>
		      <div>
                    <label class='lab'>中金余额不足笔数：</label>
                    <input   name="failureRate"   id ="failureRateId"     readonly="true"       value= ${report.zjnotEnoughNumber }> 
					<label class='lab'>中金划扣笔数：</label>
		            <input    name="notEnoughProportion"  id="notEnoughProportionId"  readonly="true"   value= ${report.zjdeductNumber }>
		            <label class='lab'>比例（%）：</label> 
		            <input   name="notEnoughBase"    id ="notEnoughBaseId"   readonly="true"   value= ${report.zjnotEnoughProportion }>
			   </div>
			   <div >
			        <label class='lab'>富友余额不足笔数：</label>
		            <input   name="failureRate"   id ="failureRateId"      readonly="true"     value= ${report.fynotEnoughNumber }> 
		            <label class='lab'>富友划扣笔数：</label>
		            <input   name="failureBase"    id ="failureBaseId"     readonly="true"      value= ${report.fydeductNumber }>
		            <label class='lab'>比例（%）：</label>
		            <input    name="failureNumber"     id ="failureNumberId" readonly="true"     value= ${report.fynotEnoughProportion }>
		        </div>
		         <div>
			        <label class='lab'>卡联余额不足笔数：</label>
		            <input   name="failureRate"   id ="failureRateId"      readonly="true"      required value= ${report.klnotEnoughNumber }> 
		            <label class='lab'>卡联划扣笔数：</label>
		            <input   name="failureBase"    id ="failureBaseId"    readonly="true"      required value= ${report.kldeductNumber }>
		            <label class='lab'>比例（%）：</label>
		            <input    name="failureNumber"     id ="failureNumberId"   readonly="true"     required  value= ${report.klnotEnoughProportion }>
		        </div>
		        
		         <div >
			        <label class='lab'>畅捷余额不足笔数：</label>
		            <input   name="failureRate"   id ="failureRateId"  readonly="true"       value= ${report.cjnotEnoughNumber }> 
		            <label class='lab'>畅捷划扣笔数：</label>
		            <input   name="failureBase"    id ="failureBaseId"    readonly="true"       value= ${report.cjdeductNumber }>
		            <label class='lab'>比例（%）：</label>
		            <input    name="failureNumber"     id ="failureNumberId"  readonly="true"    value= ${report.cjnotEnoughProportion }>
		        </div>
		        
		        <div >
			        <label class='lab'>通联余额不足笔数：</label>
		            <input   name="failureRate"   id ="failureRateId"    readonly="true"     value= ${report.tlnotEnoughNumber }> 
		            <label class='lab'>通联划扣笔数：</label>
		            <input   name="failureBase"    id ="failureBaseId"    readonly="true"     value= ${report.tldeductNumber }>
		            <label class='lab'>比例（%）：</label>
		            <input    name="failureNumber"     id ="failureNumberId" readonly="true"   value= ${report.tlnotEnoughProportion }>
		            <input  type ='button' style ="float: right;margin-right: 30px;" id="searchBtn" value ="刷 新" /> 
		         </div>
	       </div>
	       
	       
	       <div id="onLineTable" class='table1'>
	       	<button class="btn btn-small"  onclick ="exportAll()">导出Excel</button>
	       	  <form id="deductPlant"  action="${ctx}/borrow/payback/deductPlantStatisticsReport/queryPage" method="post">
	       	    <input   name="statisticsFlag"   id ="statisticsFlag"   type="hidden"    value= ${record.statisticsFlag }> 
	       	   
		      <div>
                    <label class='lab'>平台：</label>
                   	<select name="plantCode" id ="plantCodeId"   class="select78"  style ="width:135px;" onchange = "plantChage(this)">
						<option value="">请选择</option>
                		<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="d">
		                    <c:if test="${ d.value != '4' && d.value != '1'}">                		 
		                       <option value="${d.value}" 
                				<c:if test="${d.value eq record.plantCode}">
                					selected
                				</c:if> 
                			>${d.label}</option>
                		    
                		    </c:if>
                			
                		</c:forEach>
                	</select>
					<label class='lab'>日期：</label>
		            <label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
								value="<fmt:formatDate value="${record.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						</label>-<label>
							<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
								value="<fmt:formatDate value="${record.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
					</label>
					
				     <div class="tright pr30 pb5">
				     	<input type ="button" class="btn btn-primary"   value="清除"  id="clearBtn"/>
			     		<input type ="button" class="btn btn-primary" id="searchBtn111"  value="搜索" />
			     		<input type ="button" class="btn btn-primary" value="汇总"  id="sumBtn"/>
			     		
			         </div>
			   </div>
			   </form>
	       </div>
	       
	       <div class="box5"> 
        <table  class="table  table-bordered table-condensed" >
        <thead>
            <tr>
               	<th>序号</th>
                <th>平台</th>
                <th>日期</th>
                <th>划扣失败比例</th>
                <th>余额不足比例</th>
            </tr>
         </thead>   
        <c:if test="${page.list!=null && fn:length(page.list)>0}">
         <c:forEach items="${page.list}" var="bankPlantPort" varStatus="status">
            <tr>
                 <td>${(page.pageNo-1)*page.pageSize+status.count}</td>
                 <td>${bankPlantPort.plantCode}</td>
                 <td>
                    ${bankPlantPort.createDate}
                 </td>
                   <td>
                    ${bankPlantPort.failureRate}
                 </td>
                  <td>
                    ${bankPlantPort.notEnoughProportion}
                 </td>
            </tr>
			</c:forEach>
		</c:if>
        <c:if test="${ page.list==null || fn:length(page.list)==0}">
           <tr><td colspan="6" style="text-align:center;">没有待办数据</td></tr>
        </c:if>
      </table>
    </div>
     <div class="pagination">${page}</div>
	 </form>
</div>
</div>
</body>
<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</html>