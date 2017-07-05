
$(function(){
	
	$("#addbutton").click(function(){
		var id = $("#deductProportion").children("div:last-child").attr("id");
        var ids = id.split("_");
        var number = parseInt(ids[1])+1;
		
		$("#deductProportion").append($("#grouptemp").clone(true).show());
		//$("#grouptemp").hide();
		$("#deductProportion").children("div:last-child").attr("id","group_"+number);
	})
	
	$("#submitButton").click(function(){
		
		var  groupProportion =  new Array();
		$("div[id^='group_']").each(function(){
			var proportion = "";
			$(this).find("select").each(function(){
				proportion += $(this).val() +","
			})
			$(this).find("input").each(function(){
				proportion += $(this).val()
			})
			groupProportion.push(proportion);
		})
		
		
		$.ajax({  
			    type : "POST",
				url :ctx+"/borrow/payback/deductStatisticsReport/saveOrupdate",
				data : {'groupProportion':groupProportion},
				datatype : "json",
				async: false,
				success : function(data){
				 },
				error: function(){
					art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
				}
		 });
		
	})
	
});

function addRowCell(obj){
	 var imgObj = $(obj);
	 imgObj.before($("#temprow").clone(true).show());
}
