var returnRateStats = {}

returnRateStats.init = function(){
	//条件搜索
	$("#searchBtn").bind("click", function(){
		var url = ctx + "/borrow/statistics/returnRateStatsList"
		$("#returnRateSearchForm").attr("action", url);
		$("#returnRateSearchForm").submit();
	});
	//导出Excel
	$("#exportBtn").bind("click", function(){
		var url = ctx + "/borrow/statistics/exportReturnRateStatsExcel"
		var id = "";
		$("input[name='id']:checked").each(function(){
			id += $(this).val()+",";
		});
		$("#export").val(id);
		$("#returnRateSearchForm").attr("action", url);
		$("#returnRateSearchForm").submit();
	});
	//清除
	$("#clearBtn").bind("click", function(){
		
//		$("input[name='userName']").val("");
//		$("select[name='roleId']").val("");
//		$("select[name='roleId']").trigger("change");
//		$("select[name='businessDepartmentId']").val("");
//		$("select[name='businessDepartmentId']").trigger("change");
//		$("select[name='provinceBranchId']").val("");
//		$("select[name='provinceBranchId']").trigger("change");
//		$("select[name='cityBranchId']").val("");
//		$("select[name='cityBranchId']").trigger("change");
//		$("select[name='storeId']").val("");
//		$("select[name='storeId']").trigger("change");
//		$("input[name='month']").val("");
		
		location.href = ctx + "/borrow/statistics/returnRateStatsList";
	});
	//全选
	$("#checkAll").bind("click", function(){
		if($(this).prop("checked")){
			$("input[name='id']").prop("checked", true);
		}else{
			$("input[name='id']").prop("checked", false);
		}
	});
	//（勾选单条信息时，判断是不是全选，如果是则全选按钮勾选，如果不是，则全选按钮不勾选）
	$("input[name='id']").bind("click", function(){
	
		var flag = true;
		$("input[name='id']").each(function(){
			if(!$(this).prop("checked")){
				flag = false;
			}
		});
		
		if(flag){
			$("#checkAll").prop("checked", true);
		}else{
			$("#checkAll").prop("checked", false);
		}
	});
	//机构级联查询(业务部变动时，省分公司，城支和门店都要跟着变动)
	$("select[name='businessDepartmentId']").bind("change", function(event, noChange){

		if(noChange == undefined || !noChange){
			var id = $(this).val();
			//查询省分公司
			returnRateStats.loanChildOrg(id, '6200000002,6200000005');
			//查询城支公司
			returnRateStats.loanChildOrg(id, '6200000006');
			//查询门店
			returnRateStats.loanChildOrg(id, '6200000003');
		}
		
	});
	//机构级联查询（省分公司变动时，城支和门店都要变动）
	$("select[name='provinceBranchId']").bind("change", function(event, noChange){
		
		if(noChange == undefined || !noChange){
			var id = $(this).val();
			//查询城支公司
			returnRateStats.loanChildOrg(id, '6200000006');
			//查询门店
			returnRateStats.loanChildOrg(id, '6200000003');
		}
		
	});
	//机构级联查询（城支变动时，门店跟着变动）
	$("select[name='cityBranchId']").bind("change", function(event, noChange){
		
		if(noChange == undefined || !noChange){
			var id = $(this).val();
			//查询门店
			returnRateStats.loanChildOrg(id, '6200000003');
		}
	});
}

returnRateStats.loanChildOrg = function(parentId, type){
	
	$.post(ctx + "/borrow/statistics/queryChildOrg", {"parentId":parentId, "type":type}, function(data){
		
		var selectDocoment;
		
		if(type == "6200000002,6200000005"){
			selectDocoment = $("select[name='provinceBranchId']");
		}else if(type == "6200000006"){
			selectDocoment = $("select[name='cityBranchId']");
		}else if(type == "6200000003"){
			selectDocoment = $("select[name='storeId']");
		}
		
		if(selectDocoment != undefined){
			selectDocoment.empty();
			selectDocoment.append("<option value='' selected ='selected'>全部</option>");
			for(var i=0; i< data.length ; i++){
				selectDocoment.append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
			selectDocoment.trigger("change", true);
		}
		
	}, "json");
}