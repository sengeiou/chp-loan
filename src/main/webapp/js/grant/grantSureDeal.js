
$(document).ready(function(){
	
	// 页面初始化
	onload=show();
	
	$("#sel").change(function() { 
		SelectChange();
		}); 
	
	/**
	 * 点击放款确认
	 */
	$(":input[name='grantSureBtn']").each(function(){
		$(this).bind('click',function(){
			var listFlag = $("#listFlag").val();
			var frozenFlag = $(this).attr("frozenFlag");
			var urgentFlag = $(this).attr("urgentFlag");
			var contractCode = $(this).attr("contractCode");
			var revisitStatus = $(this).attr("revisitStatus");
			var menuId = $("#menuId").val();
			if(revisitStatus != ''&& revisitStatus != null){
				if(revisitStatus == 0 || revisitStatus == -1){
					art.dialog.alert("不允许确认待回访的数据");
					return;
				}
			}
			if(frozenFlag==1){
			   art.dialog.alert("该客户门店已申请冻结！");
			   return;
			}else{
				art.dialog.confirm('确定对单子进行放款确认？', 
					function(){
						$.ajax({
			    			type : 'post',
			    			url : ctx + '/borrow/grant/grantSureDeal/updGrantSureStatus',
			    			data :{
			    				'urgentFlag':urgentFlag,
			    				'contractCode':contractCode,
			    				'listFlag':listFlag
			    				},
			    			cache : false,
			    			async : false,
			    			success : function(result) {
			    		      if(result=="true"){
			    		    	art.dialog.alert("放款确认成功");
			    		    	url=ctx + '/borrow/grant/grantSure/getGrantSureInfo?listFlag='+listFlag+'&menuId='+menuId;
			    		    	window.location=url;
			    		      }else{
			    		    	 art.dialog.alert(result);
			    		      }
			    			},
			    			error : function() {
			    				art.dialog.alert('请求异常，放款确认失败！');
			    			}
			    		});
					  },
	            	    function () {
	            	    art.dialog.tips('执行取消操作');
	            	    return;
	        	    });	
			}
		});
	});
	
	/**
	 * 点击P2P
	 */
	$(":input[name='addP2PBtn']").each(function(){
		$(this).bind('click',function(){
			var loanMarking='P2P';
			var loanCode = $(this).attr('loanCode');
			var applyId = $(this).attr('applyId');
			var contractCode = $(this).attr('contractCode');
			art.dialog.confirm('确定将单子转为P2P标识么？', 
					function(){
				         updFlag(loanMarking,loanCode,applyId,contractCode);
				    },
            	    function () {
            	    art.dialog.tips('执行取消操作');
            	    return;
        	    });	
		});
	});
	
	// 点击退回
	$(":input[name='grantBackBtn']").each(function(){
		$(this).bind('click',function(){
			$('#applyId').val($(this).attr('applyId'));
			$('#loanCode').val($(this).attr('loanCode'));
			$('#contractCode1').val($(this).attr('contractCode'));
		});
	});
	
	// 点击确认退回原因,需要指定退回的节点
	$("#grantSureBackBtn").click(function(){
		var listFlag = $("#listFlag").val();
		var menuId = $("#menuId").val();
		// 获得填写的退回原因，调用流程退回的处理方法，更新单子的状态以及合同表的待放款确认退回原因
		var grantSureBackReason= $("#sel").find("option:selected").text();
		var grantSureBackReasonCode = $("#sel option:selected").val();
		if(grantSureBackReason=="其他"){
			grantSureBackReason=$("#remark").val();
			grantSureBackReasonCode = "9";
			if(grantSureBackReason==null||grantSureBackReason==""){
				art.dialog.alert("退回原因不能为空！");
				return;
			}
		}
		grantSureBack(grantSureBackReason,grantSureBackReasonCode,listFlag,menuId);
	});
	
});
    // 更新单子标识方法,
    function updFlag(loanMarking,loanCode,applyId,contractCode){
    	$.ajax({
			type : 'post',
			url : ctx + '/borrow/grant/grantSureDeal/grantUpdFlag',
			data :{
				'loanMarking':loanMarking,
				'loanCode':loanCode,
				'applyId':applyId,
				'contractCode':contractCode
			},
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				if(result){
					art.dialog.alert("标识修改成功",function(){window.location.reload()});
					
				}else{
					art.dialog.alert(result);
				}
			},
			error : function() {
				alert('请求异常');
			}
		});
    }
    // 退回方法声明
    function grantSureBack(grantSureBackReason,grantSureBackReasonCode,listFlag,menuId){
    	var param=$("#param").serialize();
    	if(param!=null && param!=undefined){
			param+='&backReason='+encodeURI(encodeURI(grantSureBackReason));
		}
    	$.ajax({
			type : 'post',
			url : ctx+'/borrow/grant/grantSureDeal/grantSureBack',
			data :param,
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				if(result){
					art.dialog.alert("单子退回成功");
					// 跳转到列表页面
					window.location.href=ctx+'/borrow/grant/grantSure/getGrantSureInfo?listFlag='+listFlag+'&menuId='+menuId;;
				}else{
					art.dialog.alert(result);
				}
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