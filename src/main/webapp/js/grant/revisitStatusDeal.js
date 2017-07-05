var revisitStatusObj={};

revisitStatusObj.getRevisitStatusList = function(btnId,revisitStatus,revisitStatusName,isFailBtn){
	$("#"+btnId).click(function(){ 
		art.dialog.open(ctx + "/borrow/grant/grantSure/pathSkip?returnURL=revisitStatusPage&isFailBtn="+isFailBtn,{
			title:"回访状态", 
			width:460, 
			height:200, 
			lock:true,
			opacity: .1,
	        okVal: '确定', 
	        ok:function(){
	        	var iframe = this.iframe.contentWindow;
	        	var rsName = "";
				var rs = "";
				$('input[name="revisitStatusCheck"]:checked',iframe.document).each(function(){ 
					if($(this).attr("checked")=='checked' || $(this).attr("checked") ){
						rs += $(this).val()+",";
						rsName += $(this).attr("sname")+",";
	        	    }
					/*rs = rs +  $(this).val() + ",";
					rsName = rsName + $(this).next().text() + ",";*/
				});
				rs = rs.replace(/,$/g,"");
				rsName = rsName.replace(/,$/g,"");
				$("#"+revisitStatus).val(rs);
				$("#"+revisitStatusName).val(rsName);
	        },
	        cancelVal: '取消',
	        cancel: true
		},false);
	});
};


revisitStatusObj.checkAll = function(selector,isFailBtn){
	 $('#'+selector).bind(
		'click',function() {
		 var checked = false;
		if ($('#'+selector).attr('checked') == 'checked'
				|| $('#'+selector).attr('checked')) {
			checked = true;
		}
		$("input[name='revisitStatusCheck']").each(function() {
			$(this).attr('checked', checked);
		});
		if(isFailBtn == ""){
			$("#T2").attr('checked', false);
		}
	 });
	};

