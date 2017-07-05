$(document).ready(function(){
	
	/**
	 * @function 绑定还款明细页面弹出框事件
	 */
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
	
	/**
	 * @function 初始化页面布局
	 */
	$("#qishu_div2").css('display','none'); 
	$("#qishu_div3").css('display','none'); 
	$("#qishu_div4").css('display','none'); 
	
	/**
	 * @function 日期选择
	 */
	$('input[name="paybackTransferInfo.tranDepositTimeStr"]').daterangepicker({ 
		 singleDatePicker: true,
		 startDate: moment().subtract('days')
	})
		
	/**
	 * @function 还款申请表单提交
	 */
	$('#applyPayLaunchBtn').click(function(){
		
		//先获取还款选中状态  看是不是POS刷卡
		if($('input:radio[id="paymentsPosCard"]:checked').val()!=null){
	     //获得合同编号 
	      var contractCode = $("#contarctCode").val();
	     //获得客户姓名
	      var customerName = $("#customerName").val();
	  //ajax 事件开始----------------------------------------------------------------
	      if(/^[\u4e00-\u9fa5]+$/.test(contarctCode)){
				top.$.jBox.tip('不能输入汉字!','warning');
				return false;
		     };
			//查看改合同编号在门店待办中是否有待还款的POS信息
				if(typeof(contarctCode)!="undefined" && contarctCode!=0){
					  $.ajax({  
						   type : "POST",
						   data:{
							   contractCode:$('#contarctCode').val()
						   },
							url : ctx+"/borrow/payback/applyPayback/findApplyByDealt",
							datatype : "json",
							success : function(data){
								alert(data);
								if(data==1){
									  alert("客户 "+customerName+" 有待还款的POS机还款申请,不允许再次提交!");
									  return false;
								}
							},
							//其他异常错误
							error: function(){
									top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
								}
						});
					}else{
					//合同编号为空	
						top.$.jBox.tip('请输入合同编号!','warning');
						return false;
					}
					}
	
      //ajax事件结束----
			
		var dictRepayMethod = $('input:radio[name="paybackApply.dictRepayMethod"]:checked').val();
		var dictDealType = $('#dictDealType').val();
		var storesInAccount = $('#storesInAccount').val();
		var checkText=$("#storesInAccount").find("option:selected").text();
		$('#storesInAccountname').val(checkText);
		if (dictRepayMethod=='1'){
			if($("#transferAmont").val()==""){
				top.$.jBox.tip('实际到账金额必填!','warning');
				return false;
			}
			if(storesInAccount=="请选择"){
            	top.$.jBox.tip('请选择存入银行!','warning');
            	return false;
            }
        }else if (dictRepayMethod == '0'){
        	if(dictDealType=="请选择"){
            	top.$.jBox.tip('请选择划扣平台!','warning');
            	return false;
            }
        	if($("#deductAmount").val()==""){
            	top.$.jBox.tip('划扣金额必填!','warning');
            	return false;
        	}
        	if($("#deductAmount").val() < 0){
            	top.$.jBox.tip('划扣金额不能为负数!','warning');
            	return false;
        	}
        }
    	$("#applyPayLaunchForm").submit();
	})
	/**
	 * 查询按钮点击事件
	 * @param contractCode
	 */
	$("#searchApplyPayBtn").click(function(){
		var contarctCode = $('#contarctCode').val();
		if(/^[\u4e00-\u9fa5]+$/.test(contarctCode)){
			top.$.jBox.tip('不能输入汉字!','warning');
			return false;
			};
		if(typeof(contarctCode)!="undefined" && contarctCode!=0){
		  $.ajax({  
			   type : "POST",
			   data:{
				   contractCode:$('#contarctCode').val()
			   },
				url : ctx+"/borrow/payback/applyPayback/findApplyByContractCode",
				datatype : "json",
				success : function(data){
					var jsonData = eval("("+data+")");
					$("input[name=paymentsManual]").attr("checked",false);
				    $("input[name=paymentsAuto]").attr("checked",false);
					if (jsonData.length>0){
						var date=new Date();
					    var applyDate=date.getFullYear()+"-"+("0"+(date.getMonth()+1)).slice(-2)+"-"+("0"+date.getDate()).slice(-2);
						$("#applyPayLaunchForm").setForm({ 
							"uploadDate":applyDate,
							"paybackDay":paybackDayDate
							});
					    $('input[id="applyPayDay"]').each(function(idx){     
					    	$(this).val(applyDate);   
					    });
					    if(typeof jsonData[0].paybackDay != "undefined" && jsonData[0].paybackDay != null){
							var paybackDay = new Date(jsonData[0].paybackDay);
						    var paybackDayDate = paybackDay.getFullYear()+"-"+(paybackDay.getMonth()+1)+"-"+paybackDay.getDate();
					    }
					    if(typeof jsonData[0].paybackMonth != "undefined" && jsonData[0].paybackMonth != null){
							$("#applyPayLaunchForm").setForm({ 
								"paybackMonthId":jsonData[0].paybackMonth.id
								});
					    }
					    if(typeof jsonData[0].loanCustomer != "undefined" && jsonData[0].loanCustomer != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"customerCertNum": jsonData[0].loanCustomer.customerCertNum,
								"customerName": jsonData[0].loanCustomer.customerName
								});
					    }
					    if(typeof jsonData[0].contract != "undefined" && jsonData[0].contract != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"loanCode": jsonData[0].contract.loanCode,
								"productType": jsonData[0].contract.productType,
								"contractAmount": isNull(jsonData[0].contract.contractAmount),
								"contractMonths": jsonData[0].contract.contractMonths
								});
					    }
					    if(typeof jsonData[0].loanInfo != "undefined" && jsonData[0].loanInfo != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"dictLoanStatus":jsonData[0].loanInfo.dictLoanStatus
								});
					    }
					    if(typeof jsonData[0].paybackBuleAmount != "undefined" && jsonData[0].paybackBuleAmount != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"paybackBuleAmount":isNull(jsonData[0].paybackBuleAmount),
								"paybackMonthAmount":isNull(jsonData[0].paybackMonthAmount)
								});
					    }
					    if(typeof jsonData[0].loanBank != "undefined" && jsonData[0].loanBank != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"loanBankId":jsonData[0].loanBank.id,
								"bankAccountName":jsonData[0].loanBank.bankAccountName,
								"bankAccount":jsonData[0].loanBank.bankAccount,
								"bankBranch":jsonData[0].loanBank.bankBranch,
								"bankId":jsonData[0].loanBank.bankName,
								"bankName":jsonData[0].loanBank.bankNameStr
								});
					    }
					    if(typeof jsonData[0].paybackTransferInfo != "undefined" && jsonData[0].paybackTransferInfo != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"uploadName":jsonData[0].paybackTransferInfo.uploadName
								});
					    }
						$("#qishu_div3").css('display','block'); 
					}else{
						$("#qishu_div3").css('display','none'); 
						$("#qishu_div2").css('display','none'); 
						$("#qishu_div1").css('display','none'); 
						$("#qishu_div0").css('display','none'); 
						$("#qishu_div4").css('display','none'); 
						$(':input','#applyPayLaunchForm') 
						.not(':button, :submit, :reset, :hidden') 
						.val('') 
						.removeAttr('checked') 
						.removeAttr('selected');
						top.$.jBox.tip('不存在的合同编号!','warning');
						return false;
					}
				},
				error: function(){
						top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
					}
			});
		}else{
			top.$.jBox.tip('请输入合同编号!','warning');
			$("#qishu_div3").css('display','none'); 
			$("#qishu_div2").css('display','none'); 
			$("#qishu_div1").css('display','none');
			$("#qishu_div0").css('display','none');
			$("#qishu_div4").css('display','none'); 
			$(':input','#applyPayLaunchForm') 
			.not(':button, :submit, :reset, :hidden') 
			.val('') 
			.removeAttr('checked') 
			.removeAttr('selected');
			return false;
		}
	});
	
	/**
	 * @function 点击汇款/转账radio事件 
	 */
	$('#paymentsManual').click(function(){
		  $('#paymentsManual').attr('checked',true);
	      $('#paymentsAuto').attr('checked',false);
	      
	      $('#paymentsPosCard').attr('checked',false);
	      $('#paymentsPosAudit').attr('checked',false);
	      
	    
		  $('#qishu_div2').css('display','none'); 
		  $('#qishu_div1').css('display','block');
		  $('#qishu_div0').css('display','block');
		  $('#qishu_div4').css('display','block'); 
		  
		  $('#qishu_div5').css('display','none'); 
		  $('#qishu_div6').css('display','none'); 
	})
	
	/**
	 * @function 点击划扣radio事件
	 */
	$('#paymentsAuto').click(function(){
		  $('#paymentsManual').attr('checked',false);
	      $('#paymentsAuto').attr('checked',true);
	      
	      $('#paymentsPosCard').attr('checked',false);
	      $('#paymentsPosAudit').attr('checked',false);
	      
		  $('#qishu_div2').css('display','block'); 
		  $('#qishu_div1').css('display','none'); 
		  $('#qishu_div0').css('display','none'); 
		  $('#qishu_div4').css('display','block'); 
		  
		  $('#qishu_div5').css('display','none'); 
		  $('#qishu_div6').css('display','none'); 
	})
	
	/**
	 * @function 点击pos机刷卡radio事件
	 */
	$('#paymentsPosCard').click(function(){
		
		// 获得合同编码
		var contarctCode = $('#contarctCode').val();
		if(/^[\u4e00-\u9fa5]+$/.test(contarctCode)){
			top.$.jBox.tip('不能输入汉字!','warning');
			return false;
	     };
		//传回后台 查询该人是否是逾期或者是还款中的客户  
			if(typeof(contarctCode)!="undefined" && contarctCode!=0){
				  $.ajax({  
					   type : "POST",
					   data:{
						   contractCode:$('#contarctCode').val()
					   },
						url : ctx+"/borrow/payback/applyPayback/findApplyByOverdue",
						datatype : "json",
						success : function(data){
							if(data==1){
								  $('#paymentsManual').attr('checked',false);
							      $('#paymentsAuto').attr('checked',false);
							      
							      $('#paymentsPosCard').attr('checked',true);
							      $('#paymentsPosAudit').attr('checked',false);
							      
								  $('#qishu_div2').css('display','none'); 
								  $('#qishu_div1').css('display','none'); 
								  $('#qishu_div0').css('display','none'); 
								  $('#qishu_div4').css('display','block'); 
								  
								  $('#qishu_div5').css('display','block'); 
								  $('#qishu_div6').css('display','none'); 
								  return false;
							}else{
								  alert("客户的还款状态不是逾期，请选择其他还款方式！");
								  $('#paymentsManual').attr('checked',true);
							      $('#paymentsAuto').attr('checked',false);
							      
							      $('#paymentsPosCard').attr('checked',false);
							      $('#paymentsPosAudit').attr('checked',false);
							    
								  $('#qishu_div2').css('display','none'); 
								  $('#qishu_div1').css('display','block'); 
								  $('#qishu_div0').css('display','block'); 
								  $('#qishu_div4').css('display','block'); 
								  
								  $('#qishu_div5').css('display','none'); 
								  $('#qishu_div6').css('display','none'); 
								  return false;
							}
						
							
						},
						//其他异常错误
						error: function(){
								top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
							}
					});
				}else{
				//合同编号为空	
					top.$.jBox.tip('请输入合同编号!','warning');
					return false;
				}
	})
	
    /**
	 * @function 点击pos机刷卡查账radio事件
	 */
	$('#paymentsPosAudit').click(function(){
		  $('#paymentsManual').attr('checked',false);
	      $('#paymentsAuto').attr('checked',false);
	      
	      $('#paymentsPosCard').attr('checked',false);
	      $('#paymentsPosAudit').attr('checked',true);
	      
		  $('#qishu_div2').css('display','none'); 
		  $('#qishu_div1').css('display','none'); 
		  $('#qishu_div0').css('display','none'); 
		  $('#qishu_div4').css('display','block'); 
		  
		  $('#qishu_div5').css('display','none'); 
		  $('#qishu_div6').css('display','block'); 
	})
	
	
	
	/**
	 * @function 新增一条pos机刷卡查账款数据
	 */
	function appendRowPos(){ 
		var tr = $('#appendIdPos').clone().unbind();
		tr.insertAfter('#appendTabPos tr:last');
		$('input[name="paybackTransferInfo.tranDepositTimeStrPos"]').each(function(idx) {
			$(this).daterangepicker({ 
				 singleDatePicker: true,
				 startDate: moment().subtract('days')
			});
		});
	}

	/**
	 * @function 删除一条pos机刷卡查账数据
	 */
	function deleteRowPos(){
		if($("#appendTabPos tr").length >= 4){
			$("#appendTabPos tr:last").remove();
		}
		else 
			top.$.jBox.tip('最后一条数据不允许删除','warning');
	} 

	/**
	 * @function 账户选择按钮事件
	 * @param loanCode
	 */
	$('#customerMal').on('show.bs.modal', function () {
		$.ajax({  
			   type : "POST",
				url : ctx+"/borrow/payback/applyPayback/findCustomerByLoanCode", data:{
					   loanCode:$('#loanCode').val()
				   },
				datatype : "json",
				success : function(data){
					var jsonData = eval("("+data+")");
					console.log(jsonData);
					if (jsonData.length>0){
						$('#customerTab').bootstrapTable('destroy');
						$('#customerTab').bootstrapTable({
							data:jsonData,
							dataType: "json",
							pageSize: 10,
							pageNumber:1
						});
					}else{
						top.$.jBox.tip('没有数据!','warning');
					}
				},
				error: function(){
						top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
					}
			});
		})
	/**
	 * @function 选择其他账户确认按钮
	 */
	$('#selectBankAccountBtn').click(function() {
		var checkData = $('#customerTab').bootstrapTable('getSelections')[0];
		$("#applyPayLaunchForm").setForm({ 
			"loanBankNewId":checkData.id,
			"bankAccountName":checkData.bankAccountName,
			"bankAccount":checkData.bankAccount,
			"bankId":checkData.bankName,
			"bankName":checkData.bankNameStr
			});
	});
	

	/**
	 * @function 实际到账金额累加
	 */
	$('#appendTab').on('blur','tr', function(event) {
		var amountCount = 0;
		$('input[id="applyAmountCopy"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			if(amountCount.toFixed(2) < 0){
				top.$.jBox.tip('实际到账金额不能为负数!','warning');
			}else{
				$("#transferAmount").val(amountCount.toFixed(2));
			}
		});
	});
});

	/**
	 * @function 新增一条银行账款数据
	 */
	function appendRow(){ 
		var tr = $('#appendId').clone().unbind();
		tr.insertAfter('#appendTab tr:last');
		$('input[name="paybackTransferInfo.tranDepositTimeStr"]').each(function(idx) {
			$(this).daterangepicker({ 
				 singleDatePicker: true,
				 startDate: moment().subtract('days')
			});
		});
	}

	/**
	 * @function 删除一条银行账款数据
	 */
	function deleteRow(){
		if($("#appendTab tr").length >= 4){
			$("#appendTab tr:last").remove();
		}
		else 
			top.$.jBox.tip('最后一条数据不允许删除','warning');
	}
	
	/**
	 * @function 空值判断
	 */
	function isNull(v){
		 if(typeof v != "undefined" && v != null){
			 return v.toFixed(2);
		}
		 else return null;
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