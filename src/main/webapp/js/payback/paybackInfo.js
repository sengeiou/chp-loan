$(document).ready(function(){
	var dictRepayMethod =$('#dictRepayMethod').val();
	if(dictRepayMethod=='0'){
		$('#qishu_div0').css('display','block'); 
	}
	if(dictRepayMethod=='1'){
		$('#qishu_div1').css('display','block'); 
	}
	if(dictRepayMethod=='2'){
		$('#qishu_div2').css('display','block'); 
	}
	if(dictRepayMethod=='3'){
		$('#qishu_div3').css('display','block'); 
	}
	
	/**
	 * @function 加载弹出框
	 */
	var msg = $("#msg").val();
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	}
	
	$('#dictPayResultGo').click(function(){
		  $('#shhy').css('display','none');
	})
    $('#dictPayResultBack').click(function(){
		  $('#shhy').css('display','block');
	})
	$("#failReason").change(function(){
		if($(this).val()=='其它')
			$("#backMesDiv").css('display','block');
		else
			$("#backMesDiv").css('display','none');
	})
	/**
	 * @function 手动匹配弹出框
	 */
	var idx;
	$("[name='matchingPayback']").each(function(){
		$(this).bind('click',function(){
			$('#infoId').val($(this).attr('infoId'));
			idx =  $(this).attr('id');
			var outDepositTime = $(this).attr("tranDepositTime");
			var data = {outDepositTime: typeof(outDepositTime)=="undefined"?'':outDepositTime,
						outReallyAmount:typeof($(this).attr("reallyAmount"))=="undefined"?'':$(this).attr("reallyAmount"),
						outEnterBankAccount:$('#storesInAccount').val()};
			$.ajax({  
				   type : "POST",
				   async:false,
				   url : ctx+"/borrow/payback/dealPayback/getMatchingList",
				   data:data,
				   datatype : "json",
					success : function(data){
						var jsonData = eval("("+data+")");
						for(var i=0;i<jsonData.length;i++){
							$.extend(jsonData[i], jsonData[i].middlePerson);
							delete jsonData[i].middlePerson;
						}
						$('#matchingTable').bootstrapTable('destroy');
						$('#matchingTable').bootstrapTable({
							data:jsonData,
							dataType: "json",    
							pageSize: 10,
							pageNumber:1
						});
					},
					error: function(){
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
						}
				});
		});
	});
	
	/**
	 * @function 图片预览下载
	 */
	var num=0;
	$("[name='previewPngBtn']").each(function(){
		$(this).bind('click',function(){
			var url=ctx + "//borrow/payback/dealPayback/previewPng?docId="+$(this).attr('docId');
			art.dialog.open(url, {  
		         id: 'previewPngBtn',
		         title: '图片预览',
		         lock:true,
		         width:800,
		     	 height:500,
		     	 button : [{
						name:'向左旋转',callback:function() {
							if(num/90==4)
								num=-90;
							else
								num-=90;
							$(this.iframe.contentDocument.images[0]).rotate(90*(num/90));
							return false;
						}
		     	 	},
					{
		     	 		name:'向右旋转',callback:function() {
		     	 			if(num/90==4)
								num=90;
							else
								num+=90;
							$(this.iframe.contentDocument.images[0]).rotate(90*(num/90));
							return false;
							
		     	 		}
					
					}]
		     },  
		     false);
		});
	});

	
	
	
	$("[name='downPng']").each(function(){
		$(this).bind('click',function(){
			window.location.href=ctx+"/borrow/payback/dealPayback/downPng?docId="+$(this).attr('docId') + "&fileName=" + $(this).attr('fileName');
		});
	});

	/**
	 * @function 手动匹配
	 */
	$('#matchingSemiautomatic').click(function(){	
		var checkData = $('#matchingTable').bootstrapTable('getSelections')[0],
			contractCode = $('[name="contractCode"]').val(),
			blueAmount = $('#paybackBuleAmount').val(),
			applyReallyAmount = $('[name="applyReallyAmount"]').val(),
			paybackId = $('#paybackId').val(),
			id = $('[name="id"]').val();
		$.ajax({  
			   type : "POST",
			   async:false,
			   data:{
				   infoId:$('#infoId').val(),
				   outId:checkData.id,
				   contractCode:contractCode,
				   outReallyAmount:checkData.outReallyAmount,
				   blueAmount:blueAmount,
				   applyReallyAmount:applyReallyAmount,
				   applyId:id,
				   paybackId:paybackId
			   },
				url : ctx+"/borrow/payback/dealPayback/matchingSingle",
				datatype : "json",
				success : function(amount){
					if(amount > 0){
						$('#'+idx).val('已匹配');
						$('#'+idx).attr('disabled',true);
						$('#applyReallyAmount').val(amount.toFixed(2))
						$('#applyReallyAmountFormat').val(amount.toFixed(2))
					}else{
						top.$.jBox.tip('数据已经被查账，请刷新页面!','warning');
					}
				},
				error: function(){
						top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
					}
			});
	});
	
	$('#applyPaySaveBtn').click(function(){
		// 实际到账金额
		var applyReallyAmount = $('#applyReallyAmount').val();
		var applyAmount = $('[name="applyAmount"]').val();
		if($('[name="dictPayResult"]:checked').val()==0){
			if(applyReallyAmount == applyAmount){
				$("#paybackInfoForm").validator();
			}else{
				artDialog.alert('汇款转账未完全匹配,不允许提交!');
				return false;
			}
		}else if($('[name="dictPayResult"]:checked').val()==1){
		  if($("#failReason").val()=='其它' && ($('#applyBackMes').val()==null || $('#applyBackMes').val()=='' )){
				artDialog.alert('请填写审核意见!');
				return false;
		  }
		}else{
			$("#paybackInfoForm").validator();
		}
	});
	$("#btnCancel").click(function(){
		if($("#zhcz").val()!=null && $("#zhcz").val()=='1')
			window.location=ctx+"/borrow/payback/matching/list?zhcz=1";
		else
			window.location=ctx+"/borrow/payback/matching/list";
	})
});
//图片可以旋转
function imgReverse(arg) {
   var img = $("[name='downPng']");
   if (arg == 'h'){
	   img.css( {'filter' : 'fliph','-moz-transform': 'matrix(-1, 0, 0, 1, 0, 0)','-webkit-transform': 'matrix(-1, 0, 0, 1, 0, 0)'} );
   }else{
	   img.css( {'filter' : 'flipv','-moz-transform': 'matrix(1, 0, 0, -1, 0, 0)','-webkit-transform': 'matrix(1, 0, 0, -1, 0, 0)'} );
   }
}
/*
 * @function 日期格式化
 */
function dataFormatter(value, row) {
	var d = new Date(value);
    return DateUtils.format(d, 'yyyy-MM-dd');
}

// 金额格式化
function numberFormatter(value, row) {
	if (value && typeof(value)!="undefined" && value!=0)
	{
	  return value.toFixed(2);
	}
}
/**
 * js from表单set方法
 * @param $
 */
(function ($) {
    $.fn.setForm = function (jsonValue) {
         var obj=this;
        $.each(jsonValue, function (id, ival) {obj.find("#" + id).val(ival); })
        }
})(jQuery)