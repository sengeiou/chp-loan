var outsidetodo = {};
outsidetodo.completeVisit = function() {
	$("#confirmInfo").bind('click', function() {
		var tb1 = document.getElementById("rejectRes");
		var tb2 = document.getElementById("distance");
		tb1.style.display ="none";
		tb2.style.display ="block";
		art.dialog({
		    content: document.getElementById("boxy"),
		    title:'确认上传外访',
		    fixed: true,
		    lock:true,
		    id: 'confirm',
		    okVal: '确认上传',
		    ok: function () {
		    	$('#response').val('TO_COMPLETE_OUTVISIT');
		    	$('#remark').val("");
		    	var srcFormParam = $('#outvisittodo');
				srcFormParam.action = ctx+'/loan/workFlow/doOutTask';
				srcFormParam.submit();
				return false;
		    },
		    cancel: true
		});
	});
}

// 客户放弃
outsidetodo.giveup = function(){
	$("#giveUpBtn").bind('click',function(){
		var url = ctx + "/borrow/borrowlist/dispatchFlow";		
		art.dialog.confirm("是否确认放弃?",function(){
			$('#remark').val("");
			$('#response').val('TO_GIVEUP');
			$('#itemDistance').val("0");
			$('#coitemDistance').val("0");
			var srcFormParam = $('#outvisittodo');
			srcFormParam.action = ctx+'/loan/workFlow/doOutTask';
			srcFormParam.submit();
		});
	});
}

//门店拒绝
outsidetodo.reject = function(){
	$("#rejectBtn").bind('click',function(){
		var tb1 = document.getElementById("rejectRes");
		var tb2 = document.getElementById("distance");
		
		tb1.style.display ="block";
		tb2.style.display ="none";
		
		var url = ctx + "/borrow/borrowlist/dispatchFlow";		
		art.dialog({
		    content: document.getElementById("boxy"),
		    title:'门店拒绝',
		    fixed: true,
		    lock:true,
		    id: 'confirm',
		    okVal: '确认拒绝',
		    ok: function () {
		    	var srcFormParam = $('#outvisittodo');
		    	$('#response').val('TO_REJECT');
		    	$('#itemDistance').val("0");
				$('#coitemDistance').val("0");
				srcFormParam.action = ctx+'/loan/workFlow/doOutTask';
				srcFormParam.submit();
				return false;
		    },
		    cancel: true
		});
	});
}


outsidetodo.fileUpload = function(imageUrl) {
	// 分配外访任务绑定
	$("#uploadbtn")
			.bind(
					'click',
					function() {
						var url = imageUrl;
						art.dialog.open(url, {
							id : 'outvisitor',
							title : '外访资料上传',
							lock : true,
							width : 800,
							height : 400
						}, false);
					});
}

outsidetodo.downloadload = function() {
	$("#download").bind(
			'click',
			function() {
				art.dialog.tips("文件下载中...");
				window.location.href = ctx
						+ "/loan/workFlow/downLoadVisitTask?loanCode="+ $("#loanCode").val();
			});
}

jQuery.validator.addMethod("decimal", function(value, element) {
	var decimal = /^-?\d+(\.\d{1,2})?$/;
	return this.optional(element) || (decimal.test(value));
	}, $.validator.format("小数位数不能超过两位!"));

outsidetodo.outsidetodovalidate = function(visitform) {
	$("#"+visitform).validate({
			onkeyup: true, 
			rules : {
				'itemDistance' : {
					number:true,
					min:0,
					max:99999,
					decimal:true
				},
				'coitemDistance' : {
					equalTo:"#itemDistance",
					number:true,
					min:0,
					max:99999,
					decimal:true
				},
				'remark':{
					maxlength:100
				}
			},
			messages : {
				'itemDistance':{
					number:  "距离为数字",
					min:"输入的数据不合法",
					max:"输入的数据不合法"
				},
				'coitemDistance':{
					equalTo: "输入距离不一致",
					number:  "距离为数字",
					min:"输入的数据不合法",
					max:"输入的数据不合法"
				},
				'remark':{
					maxlength:$.validator.format("文字数不能超过 {0}")
				}
			},
			submitHandler : function(form) {
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
			if (element.is(":checkbox") || element.is(":radio")
					|| element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
};

//外访历史
function wfHistoryBtn(relationId){
	var url = "/loan/workFlow/wfHistoryShow?relationId="+relationId;
	art.dialog.open(ctx+url, {
		title: "外访明细",	
		lock: true,
	    width: 1000,
	    height: 500,
	},false);	
}