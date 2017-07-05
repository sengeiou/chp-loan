$(function(){
	$("#termManagerId").change(function() {
		var termManagerId = $("#termManagerId option:selected").val();
		if (termManagerId == "") {
			$("#custManagerId").empty();
			$("#custManagerId").append("<option value='' selected=true>请选择</option>");
			$("#custManagerId").trigger("change");
		} else {
			$.ajax({
				type : "POST",
				url : ctx + "/common/termCustomer/asynLoadCustManagers",
				data : {
					termId : termManagerId
				},
				success : function(data) {
					var data = eval("(" + data + ")");
					var custManager = $("#customerManagerHidden").val();
					$("#custManagerId").empty();
					$("#custManagerId").append("<option value=''>请选择</option>");
					$.each(data, function(i, item) {
						if (item.id == custManager) {
							$("#custManagerId").append("<option selected=true value='" + item.id + "'>" + item.name + "</option>");
						} else {
							$("#custManagerId").append("<option value='" + item.id + "'>" + item.name + "</option>");
						}
					});
					$("#custManagerId").trigger("change");
				}
			});
		}
	});
	$("#termManagerId").trigger("change");
});