var  rateObj={};
rateObj.getRateList = function(btnId){
	var url="/apply/rateInfo/getRateInfoList";
	$("#"+btnId).click(function(){
		art.dialog.open(ctx + url, 
		{
					title:"利率有效期设置", 
					width:660, 
					height:450, 
					lock:true,
					opacity: .1,
			        okVal: '确定', 
			        ok:function(){
			        	var form = this.iframe.contentWindow.rateForm;
			        	$.ajax({
			        		url:ctx+"/apply/rateInfo/updRateEffectiveDate",
			        		data:$(form).serialize(),
			        		type:'post',
			        		success:function(data){
			        			if(data){
			        				top.$.jBox.tip('设置成功');
			        			}
			        		}
			        	});
			 },
			 cancelVal: '取消',
			 cancel: true
	},false);
});
};
rateObj.checkAll = function(selector){
 $('#'+selector).bind(
	'click',function() {
	 var checked = false;
	if ($('#'+selector).attr('checked') == 'checked'
			|| $('#'+selector).attr('checked')) {
		checked = true;
	}
	$("input[name='monthRate']").each(function() {
		$(this).attr('checked', checked);
	});
 });
};
rateObj.getRateQueryList = function(btnId,inputText){
	var url="/apply/rateInfo/getRateQueryList";
	$("#"+btnId).click(function(){
		art.dialog.open(ctx + url, 
		{
					title:"利率查询", 
					width:660, 
					height:450, 
					lock:true,
					opacity: .1,
			        okVal: '确定', 
			        ok:function(){
			        	var iframe = this.iframe.contentWindow;
			        	var str="";
			        	$("input[name='monthRate']:checked",iframe.document).each(function(){ 
			        	    if($(this).attr("checked")=='checked' || $(this).attr("checked") ){
			        	        str += $(this).val()+",";
			        	    }
			        	});
			        	
			        	str = str.replace(/,$/g,"");
			        	$("#"+inputText).val(str);
			        },
			        cancelVal: '取消',
			        cancel: true
	},false);
});
};