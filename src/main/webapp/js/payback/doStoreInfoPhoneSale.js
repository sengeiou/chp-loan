$(document).ready(function(){
	
	 $("#bcTarget").barcode($("#src").val(), "code128");
	 

	 // 绑定还款明细页面弹出框事件
	$("#applyPaybackMonthBth").click(function() {
		var contractCode = $("#contarctCode").val();
		var id = $("#paybackMonthId").val();
		if(contractCode == null || contractCode == "" || id == null || id == ""){
			top.$.jBox.tip('请输入合同编号并点击查询!');
			return;
		}
		var url=ctx + "/borrow/payback/manualOperation/queryManualOperation?contractCode="+contractCode+"&id="+id;
		art.dialog.open(url, {  
	         id: 'his',
	         title: '还款明细',
	         lock:true,
	         width:1200,
	     	 height:500
	     },  
	     false);
	});
		
	//划扣
	if($('#paymentsAuto').attr('checked')){
          $('#qishu_div1').css('display','none'); 
	      $('#qishu_div7').css('display','none'); 
		  $('#qishu_div6').css('display','none'); 
		  $('#qishu_div5').css('display','none'); 
          $('#qishu_div2').css('display','block'); 
	}
	
	//pos机刷卡
	if($('#paymentsPosCard').attr('checked')){
	     $('#qishu_div5').css('display','block'); 
	      
	      $('#qishu_div1').css('display','none'); 
	      $('#qishu_div7').css('display','none');
		  $('#qishu_div6').css('display','none'); 
		  $('#qishu_div2').css('display','none'); 
	}
	
	
	//汇款
	if($('#paymentsManual').attr('checked')){
		
          $('#qishu_div1').css('display','block'); 
          $('#qishu_div7').css('display','block'); 
	      $('#qishu_div6').css('display','none'); 
	      $('#qishu_div2').css('display','none'); 
	      $('#qishu_div5').css('display','none'); 
	}
	
	//POS机刷卡查账
	if($('#paymentsPosAudit').attr('checked')){
		
         $('#qishu_div6').css('display','block'); 
	      $('#qishu_div1').css('display','none'); 
	      $('#qishu_div7').css('display','none'); 
		  $('#qishu_div5').css('display','none'); 
		  $('#qishu_div2').css('display','none'); 
	}
	
	
	/**
	 * @function 点击汇款/转账radio事件
	 */
	$('#paymentsManual').click(function(){
		
		  $('#paymentsManual').attr('checked',true);
	      $('#paymentsAuto').attr('checked',false);
	      
	      $('#qishu_div1').css('display','block'); 
	      $('#qishu_div7').css('display','block'); 
	      $('#qishu_div6').css('display','none'); 
	      $('#qishu_div2').css('display','none'); 
	      $('#qishu_div5').css('display','none'); 
	     
	})
	
	/**
	 *  @function 点击划扣radio事件
	 */
	$('#paymentsAuto').click(function(){
		  $('#paymentsManual').attr('checked',false);
	      $('#paymentsAuto').attr('checked',true);
	      $('#qishu_div2').css('display','block'); 
	      $('#qishu_div1').css('display','none'); 
	      $('#qishu_div7').css('display','none');
		  $('#qishu_div6').css('display','none'); 
		  $('#qishu_div5').css('display','none'); 
	})
	
	 /**
	  * @function 点击POS机刷卡事件
	  */
	$('#paymentsPosCard').click(function(){
		  $('#paymentsManual').attr('checked',false);
	      $('#paymentsPosCard').attr('checked',true);
	      
	      $('#qishu_div5').css('display','block'); 
	      
	      $('#qishu_div7').css('display','none');
	      $('#qishu_div1').css('display','none'); 
		  $('#qishu_div6').css('display','none'); 
		  $('#qishu_div2').css('display','none'); 
	})
	
	/**
	 * @function 点击POS机刷卡查账
	 */
	$('#paymentsPosAudit').click(function(){
		  $('#paymentsManual').attr('checked',false);
	      $('#paymentsPosAudit').attr('checked',true);
	      
	      $('#qishu_div6').css('display','block'); 
	      
	      $('#qishu_div7').css('display','none');
	      $('#qishu_div1').css('display','none'); 
		  $('#qishu_div5').css('display','none'); 
		  $('#qishu_div2').css('display','none'); 
	})
	
	/**
	 * @function 账户选择按钮事件
	 * @param loanCode
	 */
	$('#customerMal').click(function() {
		$.ajax({  
			   type : "POST",
			   data:{
				   loanCode:$('#loanCode').val(),
				   id:$("#loanBankNewId").val()
			   },
				url : ctx+"/borrow/payback/applyPayback/findCustomerByLoanCode",
				datatype : "json",
				success : function(data){
					var jsonData = eval("("+data+")");
					if (jsonData.length>0){
						$("#customerModal").removeClass("hide");
						$("#customerModal").attr("aria-hidden",false);
						$('#customerTab').bootstrapTable('destroy');
						$('#customerTab').bootstrapTable({
							data:jsonData,
							dataType: "json",
							pageSize: 10,
							pageNumber:1
						});
						$('#customerModal').modal('toggle').css({
							width : 'auto',
							'margin-left' : function() {
								return 0;
							}
						})
					}else{
						top.$.jBox.tip('该客户没有其他账号信息！','warning');
					}
				},
				error: function(){
						top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
					}
			});
	});
	
	/**
	 * @function 选择其他账户确认按钮
	 */
	$('#selectBankAccountBtn').click(function() {
		var checkData = $('#customerTab').bootstrapTable('getSelections')[0];
		console.log(checkData);
		$("#doStoreForm").setForm({ 
			"loanBankNewId":checkData.id,
			"loanCode":checkData.loanCode,
			"bankAccountName":checkData.bankAccountName,
			"bankAccount":checkData.bankAccount,
			"bankName":checkData.bankName
			});
	});
	

	/**
	 * @function 实际到账金额累加
	 */
	$('#doStoreAppendTab').on('blur','tr', function(event) {
		var amountCount = 0;
		$('input[id="applyAmountCopy"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			$("#transferAmount").val(amountCount);
		});
	});
	
	/**
	 * @function 放弃按钮提交
	 */
	$('#giveUpDoStoreBtn').bind('click',function(){	
		art.dialog.confirm("是否确定放弃？", function(){
			// 提交和放弃按钮区分标识
			$('#applyPaybackStatus').val('8');
			$("#doStoreForm").ajaxSubmit(
		    		{   type: "POST",
						url:ctx + "/borrow/payback/doStore/save",
						dataType: "json",
					    success: function(data){
					    	top.$.jBox.tip(data.msg);
					    	var zhcz=$("#zhcz").val();
				    		if(zhcz!=null && zhcz!="")
				    			window.location.href=ctx+"/borrow/payback/doStore/phoneSaleList?zhcz=1";
				    		else
				    			window.location.href=ctx+"/borrow/payback/doStore/phoneSaleList";	
						}
		    	});
		});
	});
	
	/**
	 * @function 返回按钮
	 */
	$('#returnBtn').bind('click',function(){	
		var zhcz=$("#zhcz").val();
		if(zhcz!=null && zhcz!="")
			window.location.href=ctx+"/borrow/payback/doStore/phoneSaleList?zhcz=1";
		else
			window.location.href=ctx+"/borrow/payback/doStore/phoneSaleList";
	});
	
	/**
	 * @function 提交按钮
	 */
	$('#applyPayLaunchBtn').bind('click',function(){
		// 判断还款方式
		var dictRepayMethod = $('input:radio[name="dictRepayMethod"]:checked').val();
		var checkText=$("#storesInAccount").find("option:selected").text();
		$('#storesInAccountname').val(checkText);
		var regex1=/^\d{1,10}\.\d{1,2}$/;
		var regex2=/^\d{1,10}$/;
		var regex3 =/^[0-9]+([.]\d{1,2})?$/;
		if (dictRepayMethod=='1'){
			var storesInAccount = $('#storesInAccount').val();
			if(storesInAccount=="请选择" || storesInAccount == '' || storesInAccount== null){
				top.$.jBox.tip('请选择存入银行!');
            	return false;
            }
			var dictDepositTrue = true;
			$('#doStoreAppendTab').find("[name='paybackTransferInfo.dictDeposit']").each(function(d,e){
				if (e.value.length == 0) {
					top.$.jBox.tip('第 '+(d+1)+' 行存款方式必填!')
					dictDepositTrue=false;
					return dictDepositTrue;
					}
		    });	
			if(dictDepositTrue == false){
				return false;
			}
			var tranDepositTimeTrue = true;
			$('#doStoreAppendTab').find("[name='paybackTransferInfo.tranDepositTimeStr']").each(function(d,e){
				if (e.value.length == 0) {
					top.$.jBox.tip('第 '+(d+1)+' 行存款时间必填!')
					tranDepositTimeTrue=false;
					return tranDepositTimeTrue;
					}
		    });	
			if(tranDepositTimeTrue == false){
				return false;
			}
			var reallyAmountTrue = true;
			$('#doStoreAppendTab').find("[name='paybackTransferInfo.reallyAmountStr']").each(function(d,e){
				if (e.value.length == 0) {
					top.$.jBox.tip('第 '+(d+1)+' 行实际到账金额必填!')
					reallyAmountTrue=false;
					return reallyAmountTrue;
					}else{
						var isOK = regex1.test(e.value)||regex2.test(e.value);
						if (!isOK) {
							top.$.jBox.tip('实际到账金额只能为数字且保留2位小数!');
							 reallyAmountTrue=false;
							 return reallyAmountTrue;
						}
					}
		    });	
			if(reallyAmountTrue == false){
				return false;
			}
			
			var depositNameTrue = true;
			$('#doStoreAppendTab').find("[name='paybackTransferInfo.depositName']").each(function(d,e){
				if (e.value.length == 0) {
					top.$.jBox.tip('第 '+(d+1)+' 行实际存入人必填!')
					depositNameTrue=false;
					return depositNameTrue;
					}
		    });	
			if(depositNameTrue == false){
				return false;
			}
			var urgetrue = true,files;
			$("#doStoreAppendTab").find("[name='files']").each(function(i,e){
				if (e.value.length > 0) {
					files = e.value.substring(e.value.lastIndexOf("."));
					if (files != ".jpg" && files != ".png" && files != ".jpeg") {
						top.$.jBox.tip('第 '+(i+1)+' 行请上传图片格式的的凭条!');
						urgetrue=false;
						return urgetrue;
					}
				} else {
					top.$.jBox.tip('第 '+(i+1)+' 行请上传凭条!');
					urgetrue=false;
					return urgetrue;
				}
		    });	
			if(urgetrue==false){
				return false;
			}
        }else if (dictRepayMethod == '0'){
    		var dictDealType = $('#dictDealType').val();
    		var deductAmountPayback = $('#deductAmountPayback').val();
    		var isDeductOK = regex1.test(deductAmountPayback)||regex2.test(deductAmountPayback);
        	if(dictDealType==""){
            	art.dialog.tips('请选择划扣平台!');
            	return false;
            }
        	if(deductAmountPayback==""){
            	art.dialog.tips('划扣金额必填!');
            	return false;
        	}
        	if (!isDeductOK) {
				 art.dialog.tips('划扣金额只能为数字!');
					return false;
			}
        	if(deductAmountPayback <= 0){
            	art.dialog.tips('划扣金额必须大于0元！');
            	return false;
        	}
			if(deductAmountPayback > 510000 ){
            	art.dialog.tips('划扣金额必须小于51万元！');
            	return false;
        	}
        	if(!regex3.test(deductAmountPayback)){
            	art.dialog.tips('划扣金额必须保留2位小数!');
            	return false;
        	}
        }
		/*art.dialog.confirm("信息已经填写完成,确定提交？", function(){
			// 提交和放弃按钮区分标识		
			var checkText=$("#storesInAccount").find("option:selected").text();
			$('#storesInAccountname').val(checkText);
			$('#applyPaybackStatus').val('');
			$('#doStoreForm').submit();
		});*/
		var zhcz=$("#zhcz").val();
		art.dialog.confirm("信息已经填写完成,确定提交？", function(){
			$("#doStoreForm").ajaxSubmit(
		    		{   type: "POST",
						url:ctx + "/borrow/payback/doStore/save",
						dataType: "json",
					    success: function(data){
					    	if(data.code == '1'){
					    		top.$.jBox.tip(data.msg);
					    		// 从新获取token
					    		$("#doStoreForm").ajaxSubmit({
				     				type: "POST",
				     				url:ctx + "/borrow/payback/applyPayback/getApplyToken",
				     				dataType: "json",
				     			    success: function(data){
				     			    	$("#doStoreForm").setForm({ 
											"doStoreTokenId":data.id,
											"doStoreToken":data.token
											});
				     				}
				     			});
			    			}else if(data.code == '0'){
			    				// 从新获取token
					    		$("#doStoreForm").ajaxSubmit({
				     				type: "POST",
				     				url:ctx + "/borrow/payback/applyPayback/getApplyToken",
				     				dataType: "json",
				     			    success: function(data){
				     			    	$("#doStoreForm").setForm({ 
											"doStoreTokenId":data.id,
											"doStoreToken":data.token
											});
				     				}
				     			});
			    				art.dialog.confirm(data.msg, function(){
					     			$('#confrimFlag').val('1');
					     			$("#doStoreForm").ajaxSubmit({
					     				type: "POST",
					     				url:ctx + "/borrow/payback/doStore/save",
					     				dataType: "json",
					     			    success: function(data){
								    		top.$.jBox.tip(data.msg);
								    		if(zhcz!=null && zhcz!="")
								    			window.location.href=ctx+"/borrow/payback/doStore/phoneSaleList?zhcz=1";
								    		else
								    			window.location.href=ctx+"/borrow/payback/doStore/phoneSaleList";
					     				}
					     			});
				     		});
			    			}else{
					    		top.$.jBox.tip(data.msg);
					    		if(zhcz!=null && zhcz!="")
					    			window.location.href=ctx+"/borrow/payback/doStore/phoneSaleList?zhcz=1";
					    		else
					    			window.location.href=ctx+"/borrow/payback/doStore/phoneSaleList";
			    			}
						}
		    	});
		});
	});
	
	/**
	 * @function 图片预览下载
	 */
	var num=0;
	$("[name='doPreviewPngBtn']").each(function(){
		$(this).bind('click',function(){
			
			if(isContains($(this).attr('docId'),'chpdata_jk')){
				art.dialog.tips('未找到图片!');
				return false;
			}
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
});

function isContains(str, substr) {
    return str.indexOf(substr) >= 0;
}
/**
 * @function 新增一条银行账款数据
 */
function appendRow(){
	var tr = $('#appendId').clone();
	tr.find('input[name="doPreviewPngBtn"]').hide();
	tr.find('input[name="files"]').val('');
	var currentDate = new Date();
	var displayDate = currentDate.getFullYear()+"-"+(currentDate.getMonth()+1)+"-"+currentDate.getDate();
	tr.find("td:last").text(displayDate);
	tr.find('input[name="paybackTransferInfo.uploadDate"]').val(currentDate);
	$('#doStoreAppendTab tr:last').after(tr);
	var amountCount = 0;
	$('input[id="applyAmountCopy"]').each(function(idx) {
		var price = parseFloat($(this).val());
		if (price) {
			amountCount += price;
		}
		$("#transferAmount").val(amountCount);
	});
}

/**
 * @function 删除一条银行账款数据
 */
function deleteRow(){
	if($("#doStoreAppendTab tr").length >= 4){
		$("#doStoreAppendTab tr:last").remove();
		var amountCount = 0;
		$('input[id="applyAmountCopy"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			$("#transferAmount").val(amountCount);
		});
	}
	else 
		art.dialog.tips('最后一条数据不允许删除！');
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