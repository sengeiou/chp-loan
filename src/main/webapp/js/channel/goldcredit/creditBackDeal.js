$(document).ready(function(){
	// 页面初始化
	//onload=show();
	
	$("#sel").change(function() { 
		SelectChange();
	}); 
	// 影像弹出层
	$("a[name='lis']").click(function(){
		var imageUrl = $("input[name='imageUrl']").val();
		art.dialog.open(imageUrl, {
			title: "客户影像资料",	
		    width: 1000,
		    height: 450,
		});	
	});

	// 点击金信取消
	$("#cancleJxBtn").click(function(){
		var sure=confirm("确定对单子进行金信取消么？");
		var loanMarking='';
		if(sure){
          updFlag(loanMarking);
          $(this).prop("type",'hidden');
          $("#addP2PBtn").prop("type",'button');
		  $("#addJxBtn").prop("type",'button');
		}else{
			return;
		}
	});

	// 点击退回
	$(".grantBackBtn").click(function(){	
	    $(this).attr("data-target","#sureBack");
	});
	
	// 点击确认退回原因,需要指定退回的节点
	$("#grantSureBackBtn").click(function(){
		// 获得填写的退回原因，调用流程退回的处理方法，更新单子的状态以及合同表的待放款确认退回原因
		var grantSureBackReason= $("#sel").find("option:selected").text();
		// 要退回的流程节点,合同的审核
		var response="TO_CONTRACT_CHECK";
		var param=$("#param").serialize();
		if(grantSureBackReason=="其他"){
			grantSureBackReason=$("#textarea").val();
			if(grantSureBackReason==null||grantSureBackReason==""){
				art.dialog.alert("退回原因不能为空！");
				return false;
			}
		}
		if(param!=null && param!=undefined){
			param+='&response='+response;
		}
		grantSureBack(param,grantSureBackReason);
	});
	
});
	
    // 更新单子标识方法
    function updFlag(loanMarking){
    	var param=$("#param").serialize();
    	param+='&loanMarking='+loanMarking;
    	$.ajax({
			type : 'post',
			url : ctx + '/borrow/grant/grantSureDeal/grantUpdFlag',
			data :param,
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				if(result){
					art.dialog.alert("标识修改成功");
				}else{
					art.dialog.alert("单子修改失败");
				}
			},
			error : function() {
				art.dialog.alert('请求异常');
			}
		});
    }
    // 退回方法声明
    function grantSureBack(param,grantSureBackReason){
    	if(param!=null && param!=undefined){
			param+='&backBatchReason='+encodeURI(grantSureBackReason);
		}
    	$.ajax({
			type : 'post',
			url : ctx+'/channel/goldcredit/back/batchBack',
			data :param,
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				if(result){
					art.dialog.alert("单子退回成功");
				}else{
					art.dialog.alert("单子退回失败");
				}
				window.location.href=ctx+'/channel/goldcredit/back/fetchTaskItems';
			},
			error : function() {
				art.dialog.alert('请求异常');
			}
		});
    }
    
    // 选择其他的时候，填写退回原因
    function SelectChange() {
        var selectText = $("#sel").find("option:selected").text();
        if (selectText != "其他") {
           $("#other").hide();
        } else {
		   $("#other").show(); 
        }
    }
 // 关闭窗口
	function closeModal(id){
		$("#"+id).modal('hide');
	}