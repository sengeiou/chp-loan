$(document).ready(function(){
	/**
	 * @function 绑定还款明细页面弹出框事件
	 */
	$("#applyPaybackMonthBth").click(function() {
		var contractCode = $("#contarctCode").val();
		var id = $("#paybackMonthId").val();
		if(contractCode == null || contractCode == ""){
			art.dialog.tips('请输入合同编号并点击查询!');
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
	 * @function 还款申请表单提交
	 */
	$('#applyPayLaunchBtn').click(function(){
		var t=this;
		$(t).attr('disabled',true);
		//先获取还款选中状态  看是不是POS刷卡
		if($('input:radio[id="paymentsPosCard"]:checked').val()!=null){
		  //获得合同编号 
	      var contractCode = $("#contarctCode").val();
	      //获得客户姓名
	      var customerName = $("#customerName").val();
	      
	      if(typeof(contarctCode)=="undefined" && contarctCode==0){
				//合同编号为空	
					art.dialog.tips('请输入合同编号!');
					$(t).removeAttr("disabled");
					return false;
	      	}
	      
	      if(/^[\u4e00-\u9fa5]+$/.test(contarctCode)){
				art.dialog.tips('不能输入汉字!');
				$(t).removeAttr("disabled");
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
						if(data==1){
							  art.dialog.tips('客户  '+customerName+'  有待还款的POS机还款申请,不允许再次提交!');
							  $(t).removeAttr("disabled");
							  return false;
						}else{
							//---提交方法
							var dictRepayMethod = $('input:radio[name="paybackApply.dictRepayMethod"]:checked').val();
							var dictDealType = $('#dictDealType').val();
							var storesInAccount = $('#storesInAccount').val();
							var checkText=$("#storesInAccount").find("option:selected").text();
							var regex1=/^\d{1,10}\.\d{1,2}$/;
							var regex2=/^\d{1,10}$/;
							var regex3 =/^[0-9]+([.]\d{1,2})?$/;
							var isOK = regex1.test($("#applyAmountCopy").val())||regex2.test($("#applyAmountCopy").val());
							var isPosCardOk=regex1.test($("#deductAmountPosCard").val())||regex2.test($("#deductAmountPosCard").val());
							var isDeductOK = regex1.test($("#deductAmount").val())||regex2.test($("#deductAmount").val());
							$('#storesInAccountname').val(checkText);
							if (dictRepayMethod=='1'){
								if(storesInAccount=="请选择" || storesInAccount == '' || storesInAccount== null){
					            	art.dialog.tips('请选择存入银行!');
					            	$(t).removeAttr("disabled");
					            	return false;
					            }
								if($('#dictDeposit').val()==""){
									art.dialog.tips('存款方式必填!');
									$(t).removeAttr("disabled");
									return false;
								}
								if($('#tranDepositTimeStr').val()==""){
									art.dialog.tips('存款时间必填!');
									$(t).removeAttr("disabled");
									return false;
								}
								if($("#applyAmountCopy").val()==""){
									art.dialog.tips('实际到账金额必填!');
									$(t).removeAttr("disabled");
									return false;
								}
								if (!isOK) {
									 art.dialog.tips('实际到账金额只能为数字!');
									 $(t).removeAttr("disabled");	
									 return false;
								}
								if (!regex3.test($("#applyAmountCopy").val())) {
									 art.dialog.tips('实际到账金额只能保留2位小数!');
									 $(t).removeAttr("disabled");	
									 return false;
								}
								if($("#applyAmountCopy").val() <= 0){
					            	art.dialog.tips('实际到账金额必须大于0！');
					            	$(t).removeAttr("disabled");
					            	return false;
					        	}
								if($('#depositName').val()==""){
									art.dialog.tips('实际存入人必填!');
									$(t).removeAttr("disabled");
									return false;
								}
								var urgetrue = true,files;
								$("[name='files']").each(function(i,e){
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
									$(t).removeAttr("disabled");
									return false;
								}
					        }else if (dictRepayMethod == '0'){
					        	if(dictDealType=="请选择"){
					            	art.dialog.tips('请选择划扣平台!');
					            	$(t).removeAttr("disabled");
					            	return false;
					            }
					        	if($("#deductAmount").val()==""){
					            	art.dialog.tips('划扣金额必填!');
					            	$(t).removeAttr("disabled");
					            	return false;
					        	}
					          	if($("#bankAccount").val()==""){
					            	art.dialog.tips('没有划扣帐号!');
					            	$(t).removeAttr("disabled");
					            	return false;
					        	}
					        	if (!isDeductOK) {
									 art.dialog.tips('划扣金额只能为数字!');
									 $(t).removeAttr("disabled");
										return false;
								}
					        	if($("#deductAmount").val() <= 0){
					            	art.dialog.tips('划扣金额必须大于0元！');
					            	$(t).removeAttr("disabled");
					            	return false;
					        	}
								if($("#deductAmount").val() > 510000 ){
					            	art.dialog.tips('划扣金额必须小于51万元！');
					            	$(t).removeAttr("disabled");
					            	return false;
					        	}
					        	if(!regex3.test($("#deductAmount").val())){
					            	art.dialog.tips('划扣金额必须保留2位小数!');
					            	$(t).removeAttr("disabled");
					            	return false;
					        	}
					        }else if (dictRepayMethod == '2'){
					        	if($('#deductAmountPosCard').val()==""){
									art.dialog.tips('申请刷卡金额必填!');
									$(t).removeAttr("disabled");
									return false;
								}
					        	if (!isPosCardOk) {
									 art.dialog.tips('申请刷卡金额只能为数字!');
									 $(t).removeAttr("disabled");
										return false;
								}
					        	if($('#dictPosType').val()=="请选择" || $('#dictPosType').val() == '' || $('#dictPosType').val()== null){
					            	art.dialog.tips('请选择POS机平台!');
					            	$(t).removeAttr("disabled");
					            	return false;
					            }
					        	
					        }else if (dictRepayMethod == '3'){
					        	var files = $('[name="files"]').val()
								if (files.length > 0) {
									files = files.substring(files.lastIndexOf("."));
									if (files != ".jpg" && files != ".png"  && files != ".jpeg") {
										art.dialog.tips('请上传图片格式的的凭条!');
										$(t).removeAttr("disabled");
										return false;
									}
								} else {
									art.dialog.tips('请上传凭条!');
									$(t).removeAttr("disabled");
									return false;
								}
					        }
							
							
							art.dialog.confirm('内容已修改，是否保存？',   
									  function () {
								$("#applyPayLaunchForm").ajaxSubmit({
					    			type: "POST",
									url:ctx + "/borrow/payback/applyPayback/saveApplyPayLaunch",
									dataType: "json",
								    success: function(data){
								    	if(data.code == '0'){
								    		art.dialog.tips(data.msg);
					 			    		window.location.href=ctx+"/borrow/payback/applyPayback/form";
						    			}else{
								    		art.dialog.tips(data.msg);
						    			}
									}
					    	});
									  //调用确定执行的事
									  },   
									  function () {
									
									  });
									  return false;
						}
					},
					//其他异常错误
					error: function(){
							art.dialog.tips('服务器没有返回数据，可能服务器忙，请重试!');
							$(t).removeAttr("disabled");
						}
				});
			}
		  //如果选择的不是POS机刷卡选择 则走下面的提交
			}else{
				var postrue=true;
				var posrefcode=true;
				var posrefCard=true;
				var test1=false;
				
				var dictRepayMethod = $('input:radio[name="paybackApply.dictRepayMethod"]:checked').val();
				var dictDealType = $('#dictDealType').val();
				var storesInAccount = $('#storesInAccount').val();
				var checkText=$("#storesInAccount").find("option:selected").text();
				var regex1=/^\d{1,10}\.\d{1,2}$/;
				var regex2=/^\d{1,10}$/;
				var regex3 =/^[0-9]+([.]\d{1,2})?$/;
				var isPosCardOk=regex1.test($("#deductAmountPosCard").val())||regex2.test($("#deductAmountPosCard").val());
				var isDeductOK = regex1.test($("#deductAmount").val())||regex2.test($("#deductAmount").val());
				$('#storesInAccountname').val(checkText);
				if (dictRepayMethod=='1'){
					if(storesInAccount=="请选择" || storesInAccount == '' || storesInAccount== null){
						top.$.jBox.tip('请选择存入银行!');
						$(t).removeAttr("disabled");
						return false;
		            }
					var dictDepositTrue = true;
					$('#appendTab').find("[name='paybackTransferInfo.dictDeposit']").each(function(d,e){
						if (e.value.length == 0) {
							top.$.jBox.tip('第 '+(d+1)+' 行存款方式必填!')
							dictDepositTrue=false;
							$(t).removeAttr("disabled");
							return dictDepositTrue;
							}
				    });	
					if(dictDepositTrue == false){
						$(t).removeAttr("disabled");
						return false;
					}
					var tranDepositTimeTrue = true;
					$('#appendTab').find("[name='paybackTransferInfo.tranDepositTimeStr']").each(function(d,e){
						if (e.value.length == 0) {
							top.$.jBox.tip('第 '+(d+1)+' 行存款时间必填!')
							tranDepositTimeTrue=false;
							$(t).removeAttr("disabled");
							return tranDepositTimeTrue;
							}
				    });	
					if(tranDepositTimeTrue == false){
						$(t).removeAttr("disabled");
						return false;
					}
					var reallyAmountTrue = true;
					$('#appendTab').find("[name='paybackTransferInfo.reallyAmountStr']").each(function(d,e){
						if (e.value.length == 0) {
								top.$.jBox.tip('第 '+(d+1)+' 行实际到账金额必填!')
								reallyAmountTrue=false;
								$(t).removeAttr("disabled");
								return reallyAmountTrue;
							}else{
								var isOK = regex1.test(e.value)||regex2.test(e.value);
								if (!isOK) {
									top.$.jBox.tip('实际到账金额只能为数字且保留2位小数!');
									 reallyAmountTrue=false;
									 $(t).removeAttr("disabled");
									 return reallyAmountTrue;
								}
							}
				    });	
					if(reallyAmountTrue == false){
						$(t).removeAttr("disabled");
						return false;
					}
					var depositNameTrue = true;
					$('#appendTab').find("[name='paybackTransferInfo.depositName']").each(function(d,e){
						if (e.value.length == 0) {
							top.$.jBox.tip('第 '+(d+1)+' 行实际存入人必填!')
							depositNameTrue=false;
							$(t).removeAttr("disabled");
							return depositNameTrue;
							}
				    });	
					if(depositNameTrue == false){
						$(t).removeAttr("disabled");
						return false;
					}
					var urgetrue = true,files;
					$("#appendTab").find("[name='files']").each(function(i,e){
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
						$(t).removeAttr("disabled");
						return false;
					}
					
		        }else if (dictRepayMethod == '0'){
		        	if(dictDealType=="请选择"){
		            	art.dialog.tips('请选择划扣平台!');
		            	$(t).removeAttr("disabled");
		            	return false;
		            }
		        	if($("#deductAmount").val()==""){
		            	art.dialog.tips('划扣金额必填!');
		            	$(t).removeAttr("disabled");
		            	return false;
		        	}
		           	if($("#bankAccount").val()==""){
		            	art.dialog.tips('没有划扣帐号!');
		            	$(t).removeAttr("disabled");
		            	return false;
		        	}
		        	if(!regex3.test($("#deductAmount").val())){
		            	art.dialog.tips('划扣金额必须保留2位小数!');
		            	$(t).removeAttr("disabled");
		            	return false;
		        	}
		        	if (!isDeductOK) {
						 art.dialog.tips('划扣金额只能为数字!');
						 $(t).removeAttr("disabled");
							return false;
					}
		        	if($("#deductAmount").val() <= 20){
		            	art.dialog.tips('划扣金额必须大于20元！');
		            	$(t).removeAttr("disabled");
		            	return false;
		        	}
		        	var sumAmount = $("#sumDayLimit").val();
		           if(parseFloat(sumAmount)>0){
						if(parseFloat($("#deductAmount").val()) > parseFloat(sumAmount)){
			            	art.dialog.tips('划扣金额必须小于'+sumAmount+'元！');
			            	$(t).removeAttr("disabled");
			            	return false;
			        	}
		        	}
		        	/*var zjcnt = $("#zjcnt").val();
		        	if(parseFloat(zjcnt)>=2){
		        		art.dialog.tips('此账户已经余额不足划扣1次！当日不允许再提交划扣！');
		        		return false;
		        	 }*/
		        }else if (dictRepayMethod == '2'){
		        	if($('#deductAmountPosCard').val()==""){
						art.dialog.tips('申请刷卡金额必填!');
						$(t).removeAttr("disabled");
						return false;
					}
		        	if (!isPosCardOk) {
						 art.dialog.tips('申请刷卡金额只能为数字!');
						 $(t).removeAttr("disabled");
							return false;
					}
		        	if($('#dictPosType').val()=="请选择" || $('#dictPosType').val() == '' || $('#dictPosType').val()== null){
		            	art.dialog.tips('请选择POS机平台!');
		            	$(t).removeAttr("disabled");
		            	return false;
		            }
		        	
		        }else if(dictRepayMethod == '3'){
		        	   var coCode=	$("input[name^='posCardInfo.referCode'").val();
		        	   //参考号
		        		var coCode=	$("input[name^='posCardInfo.referCode'").val();
		        		//到账日期
		        		var srDate=	$("input[name^='posCardInfo.paybackDateStr'").val();
		        		//实际到账金额
		        		var srDumon=$("input[name^='posCardInfo.applyReallyAmountPosCard'").val();
		        		//订单号
		        		var srNumber=$("input[name^='posCardInfo.posOrderNumber'").val();
		        		//存入账户
		        		var posCh=$("select[name^='posCardInfo.dictDepositPosCard'").val();
		        			
		        	 	if($('#posAccount').val()=="请选择" || $('#posAccount').val() == '' || $('#posAccount').val()== null){
			            	top.$.jBox.tip('请选择存入账户!');
			            	$(t).removeAttr("disabled");
			            	return false;
			            }	
		        		if(posCh==null || posCh=="" || posCh=="请选择"){
		        			   top.$.jBox.tip('请选择存款方式！');
		        			   $(t).removeAttr("disabled");
		        			   return false;
		        		}else if (coCode==null || coCode==""){
		        		       top.$.jBox.tip('参考号不能为空！');
		        		       $(t).removeAttr("disabled");
		        		   return false;
		        		}else if (srDate==null || srDate==""){
		        			   top.$.jBox.tip('到账日期不能为空！');
		        			   $(t).removeAttr("disabled");
		        		    return false;
		        		}else if (srDumon==null || srDumon== ""){
		        			   top.$.jBox.tip('实际到账金额不能为空！');
		        			   $(t).removeAttr("disabled");
		        			 return false;
		        		}else if (srNumber==null || srNumber==""){
		        			 top.$.jBox.tip('订单号不能为空！');
		        			 $(t).removeAttr("disabled");
		        			 return false;
		        		}
		        		if(postrue==true){
		        			//POS小票未上传的校验
			        		$("input[id^='filess'").each(function(index,e){
			        			if (e.value.length > 0) {
									files = e.value.substring(e.value.lastIndexOf("."));
									if (files != ".jpg" && files != ".png" && files != ".jpeg") {
										top.$.jBox.tip('第 '+(index+1)+' 行请上传图片格式的的凭条!');
										postrue=false;
										$(t).removeAttr("disabled");
										return postrue;
									}
								} else {
									top.$.jBox.tip('第 '+(index+1)+' 行请上传凭条!');
									postrue=false;
									$(t).removeAttr("disabled");
									return postrue;
								}
			        			
			     	     	});
		        		}
		        		
		        		if(posrefcode==true){
		        			  //参考号重复的校验	 
			        		var a=0;
			        		 	$("input[name^='posCardInfo.referCode'").each(function(index,e){
			        		 		   if(coCode==e.value){
			        		 			   a++;
			        		 			   if(a>=2){
			        		 				top.$.jBox.tip('参考号  '+coCode +' 重复,请重新填写！');
			        		 				 posrefcode=false;
			        		 				$(t).removeAttr("disabled");
												return posrefcode;
			        		 			   }
			        		 		   }
			        			});
		        		}
		        		
		        		if(posrefcode==true && postrue ==true){
		       	    	 //POS刷卡数据的校验
		        	     $('#appendTabPos tr').each(function () {
		        		    	 //d到账日期
		        		            var checkDateCard = $(this).find("#checkDateCard").val();
		        		            var checkApplyAmountCopyPosCard = $(this).find("#checkApplyAmountCopyPosCard").val();
		        		            var depositNamereferCode = $(this).find("#depositNamereferCode").val();
		        		            var depositNameOrderNumber = $(this).find("#depositNameOrderNumber").val();
			        		            
		        		            if(typeof(checkDateCard)!= "undefined" && 
		        		               typeof(checkApplyAmountCopyPosCard)!= "undefined" && 
		        		               typeof(depositNamereferCode)!= "undefined" && 
		        		               typeof(depositNameOrderNumber)!= "undefined" 
		        		            ){
											$.ajax({  
											   type : "POST",
											   async:false,
											   data:{
												   chaCode:depositNamereferCode,
												   posnum:depositNameOrderNumber,
												   srDate:checkDateCard,
												   srDumnet:checkApplyAmountCopyPosCard
											   },
												url : ctx+"/borrow/poscard/posBacktageList/checkRefPosStr",
												datatype : "json",
												success : function(data){
													if(data==-1){
												     top.$.jBox.tip('未找到相关POS刷卡信息！请核实参考号 '+depositNamereferCode+'，订单号'+depositNameOrderNumber+'，到账日期'+checkDateCard+'，金额 '+checkApplyAmountCopyPosCard+' 是否有误！');
												     posrefCard=false;
												     $(t).removeAttr("disabled");
												     return posrefCard;
													}
												},
												//其他异常错误
												error: function(){
														top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!');
														$(t).removeAttr("disabled");
													}
											});
		        		             }
		        		      });
		        	     
		        		 }	 	
		        }
				
				if(postrue==true && posrefcode==true && posrefCard==true){
					var branchTrue = true;
					var msg = "信息已经填写完成,确定提交？";
					if(dictRepayMethod == '1'){
						$('#appendTab').find("[name='paybackTransferInfo.accountBranch']").each(function(d,e){
							var time = $('#appendTab').find("[name='paybackTransferInfo.applyTimeStr']")[d];
							if (e.value.length == 0 || $(time).val()=='') {
								msg='填写“存款支行”和“存款时间”可以增加查账确认的速度，”你确认提交查账申请？';
							}
					    });
					}
						
					
					art.dialog.confirm(msg, function(){

						top.$.jBox.tip("正在提交数据。。", 'loading');
						$("#applyPayLaunchForm").ajaxSubmit(
					    		{   type: "POST",
									url:ctx + "/borrow/payback/applyPayback/saveApplyPayLaunch",
									dataType: "json",
								    success: function(data){
								    	if(data.code == '1'){
								    		$(t).removeAttr("disabled");
											top.$.jBox.closeTip();
								    		top.$.jBox.tip(data.msg);
								    		// 从新获取token
								    		$("#applyPayLaunchForm").ajaxSubmit({
							     				type: "POST",
							     				url:ctx + "/borrow/payback/applyPayback/getApplyToken",
							     				dataType: "json",
							     			    success: function(data){
							     			    	$("#applyPayLaunchForm").setForm({ 
														"applyTokenId":data.id,
														"applyToken":data.token
														});
							     				}
							     			});
						    			}else if(data.code == '0'){
						    				alert($("#applyType").val());
											top.$.jBox.closeTip();
											// 从新获取token
								    		$("#applyPayLaunchForm").ajaxSubmit({
							     				type: "POST",
							     				url:ctx + "/borrow/payback/applyPayback/getApplyToken",
							     				dataType: "json",
							     			    success: function(data){
							     			    	$("#applyPayLaunchForm").setForm({ 
														"applyTokenId":data.id,
														"applyToken":data.token
														});
							     				}
							     			});
						    				art.dialog.confirm(data.msg, function(){
								     			$('#confrimFlag').val('1');
								     			$("#applyPayLaunchForm").ajaxSubmit({
								     				type: "POST",
								     				url:ctx + "/borrow/payback/applyPayback/saveApplyPayLaunch",
								     				dataType: "json",
								     			    success: function(data){
														top.$.jBox.closeTip();
											    		top.$.jBox.tip(data.msg);
											    		
							     			    		window.location.href=ctx+"/borrow/payback/applyPayback/form?applyType="+$("#applyType").val();
								     				}
								     			});
							     		});
						    			}else{
						    				$(t).removeAttr("disabled");
								    		top.$.jBox.tip(data.msg);
					 			    		window.location.href=ctx+"/borrow/payback/applyPayback/form?applyType="+$("#applyType").val();
						    			}
									}
					    	});
					},
					//提交框点击取消操作
					function (){
						$(t).removeAttr("disabled");
					});
				}
			}
	})
	
	
	/**
	 * 查询按钮点击事件
	 * @param contractCode
	 */
	$("#searchApplyPayBtn").click(function(){
		var contarctCode = $('#contarctCode').val();
		if(/^[\u4e00-\u9fa5]+$/.test(contarctCode)){
			top.$.jBox.tip('不能输入汉字!');
			return false;
			};
		if(typeof(contarctCode)!="undefined" && contarctCode!=0){
		  $.ajax({  
			   type : "POST",
			   data:{
				   contractCode:$('#contarctCode').val(),
				   applyTokenId:$('#applyTokenId').val(),
				   applyToken:$('#applyToken').val(),
				   applyType:$("#applyType").val()
			   },
				url : ctx+"/borrow/payback/applyPayback/findApplyByContractCode",
				datatype : "json",
				success : function(data){
					var jsonData = eval("("+data+")");
					$("input[name=paymentsManual]").attr("checked",false);
				    $("input[name=paymentsAuto]").attr("checked",false);
					if (jsonData.length>0){
						var isfive =$("#isfive").val();
						var checkDay =$("#checkDay").val();
						var date = new Date();
					    var applyDate=date.getFullYear()+"-"+("0"+(date.getMonth()+1)).slice(-2)+"-"+("0"+date.getDate()).slice(-2);
						$("#applyPayLaunchForm").setForm({ 
							"id":jsonData[0].id,
							"uploadDate":applyDate,
							"uploadDatePosCard":applyDate,
							"applyTokenId":jsonData[0].applyTokenId,
							"applyToken":jsonData[0].applyToken
							});
						
					    $('input[id="applyPayDay"]').each(function(idx){     
					    	$(this).val(applyDate);   
					    });
					    
					    
					    if(typeof jsonData[0].paybackDay != "undefined" && jsonData[0].paybackDay != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"paybackDay":jsonData[0].paybackDay
								});
					    }
					    
//					    if((jsonData[0].paybackDay*1)==(checkDay*1) && isfive == "1"){
//					    	top.$.jBox.tip('还款日前一工作日下午五点后无法申请还款!');
//					    	$('#paymentsAuto').css('display','none'); 
//					    }
					    var myDate =new Date();
					    var days = myDate.getDate();
					    $('#deductDiv').show(); 
					  	if((jsonData[0].paybackDay*1)==(days*1)){
					  		if(jsonData[0].dictPayStatus == '0'){
					  		top.$.jBox.tip('还款日当天无法申请划扣!');
					  		$('#deductDiv').hide(); 
					  		}
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
								"contractMonths": jsonData[0].contract.contractMonths,
								"channelFlag": jsonData[0].contract.channelFlag
								});
					    }
					    if(typeof jsonData[0].jkProducts != "undefined" && jsonData[0].jkProducts != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"productName": jsonData[0].jkProducts.productName,
								});
					    }
					    if(typeof jsonData[0].loanInfo != "undefined" && jsonData[0].loanInfo != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"dictLoanStatus":jsonData[0].loanInfo.dictLoanStatusLabel
								});
					    }
					    if(typeof jsonData[0].paybackBuleAmount != "undefined" && jsonData[0].paybackBuleAmount != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"paybackBuleAmount":isNull(jsonData[0].paybackBuleAmount),
								"paybackMonthAmount":isNull(jsonData[0].paybackMonthAmount)
								});
					    }
					    if(typeof jsonData[0].loanInfo != "undefined" && typeof jsonData[0].loanInfo != null){
					    	//委托充值
					    	$("#applyPayLaunchForm").setForm({ 
								"trustRecharge":jsonData[0].loanInfo.trustRecharge
								});
					    }
					    if(typeof jsonData[0].loanBank != "undefined" && jsonData[0].loanBank != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"loanBankId":jsonData[0].loanBank.id,
								"bankAccountName":jsonData[0].loanBank.bankAccountName,
								"bankAccount":jsonData[0].loanBank.bankAccount,
								"bankName":jsonData[0].loanBank.bankName+jsonData[0].loanBank.bankBranch,
								"bankCode":jsonData[0].loanBank.bankCode
								});
					    	$("#dictDealType").find("option[value="+jsonData[0].loanBank.bankSigningPlatform+"]").attr("selected",true);
					    	$("#dictDealType").trigger("change");
					    }
					    if(typeof jsonData[0].paybackTransferInfo != "undefined" && jsonData[0].paybackTransferInfo != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"uploadName":jsonData[0].paybackTransferInfo.uploadName
								});
					    }
					    if(typeof jsonData[0].paybackTransferInfo != "undefined" && jsonData[0].paybackTransferInfo != null){
					    	$("#applyPayLaunchForm").setForm({ 
								"uploadNamePosCard":jsonData[0].paybackTransferInfo.uploadName
								});
					    }
					    if(jsonData[0].sumDayLimit){
					    	$("#sumDayLimit").val(jsonData[0].sumDayLimit);
					    }
					    if(jsonData[0].zjcnt){
					    	$("#zjcnt").val(jsonData[0].zjcnt);
					    }
					    //如果是结清或提前结清并且不是zcj数据则只有汇款转账可以选择
					    if(jsonData[0].dictPayStatus=='2' || jsonData[0].dictPayStatus=='3'){
					    	$("#paymentsAuto").attr("disabled",true);
					    	$("#paymentsPosCard").attr("disabled",true);
					    	$("#paymentsPosAudit").attr("disabled",true);
					    }
					    if(typeof jsonData[0].contract != "undefined" && jsonData[0].contract != null 
					    		&& typeof jsonData[0].loanInfo != "undefined" && typeof jsonData[0].loanInfo != null
					    	&& jsonData[0].contract.channelFlag=='5'){
					    	//当时大金融数据时且借款状态是还款中只能选择划扣
					    	if(jsonData[0].loanInfo.dictLoanStatus=='87' || jsonData[0].loanInfo.dictLoanStatus=='88'
					    		|| jsonData[0].loanInfo.dictLoanStatus=='91'){
					    		$("#paymentsManual").attr("disabled",true);
						    	$("#paymentsPosCard").attr("disabled",true);
						    	$("#paymentsPosAudit").attr("disabled",true);
					    	//}
					    	//当时大金融数据时且借款状态是还款中或逾期，划扣平台只能选择zcj金账户
					    	//if(jsonData[0].loanInfo.dictLoanStatus=='87' || jsonData[0].loanInfo.dictLoanStatus=='88'
					    	//	|| jsonData[0].loanInfo.dictLoanStatus=='91'){
					    		var d = new Date();
						    	//alert(jsonData[0].paybackMonth.monthPayDay);
					    		$("#dictDealType").find("option[value=4]").attr("selected",true);
						    	$("#dictDealType").trigger("change");
						    	$("#dictDealType").attr("disabled",true);
					    	}
					    }
						$("#qishu_div3").css('display','block'); 
					}else{
						var applyType =	$("#applyType").val();
						$(':input','#applyPayLaunchForm') 
						 .not(':button, :submit, :reset, :hidden')  
						 .val('')  
						 .removeAttr('checked')  
						 .removeAttr('selected');
						//设置申请类型类型
						$("#applyType").val(applyType)
						$("#qishu_div3").css('display','none'); 
						$("#qishu_div2").css('display','none'); 
						$("#qishu_div1").css('display','none'); 
						$("#qishu_div0").css('display','none'); 
						$("#qishu_div4").css('display','none'); 
						if(jsonData.limit){
							top.$.jBox.tip(jsonData.limit);

						}else{
							top.$.jBox.tip('不存在的合同编号!');

						}
					
					}
				},
				error: function(){
						top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!');
					}
			});
		}else{
			var applyType =	$("#applyType").val();
			$(':input','#applyPayLaunchForm') 
			.not(':button, :submit, :reset, :hidden') 
			.val('') 
			.removeAttr('checked') 
			.removeAttr('selected');
			$("#applyType").val(applyType)
			$("#qishu_div3").css('display','none'); 
			$("#qishu_div2").css('display','none'); 
			$("#qishu_div1").css('display','none');
			$("#qishu_div0").css('display','none');
			$("#qishu_div4").css('display','none'); 
			top.$.jBox.tip('请输入合同编号!');
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
			top.$.jBox.tip('不能输入汉字!');
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
								  top.$.jBox.tip('客户的借款状态不是逾期，请选择其他还款方式!');
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
								top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!');
							}
					});
				}else{
				//合同编号为空	
					top.$.jBox.tip('请输入合同编号!');
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
	 * @function 账户选择按钮事件
	 * @param loanCode
	 */
	$('#customerBtn').click(function () {
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
						$('#customerMal').modal('toggle');
					}else{
						top.$.jBox.tip('该客户没有其他账户信息！');
					}
				},
				error: function(){
						top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!');
					}
			});
		})
	/**
	 * @function 选择其他账户确认按钮
	 */
	$('#selectBankAccountBtn').click(function() {
		var checkData = $('#customerTab').bootstrapTable('getSelections')[0];
		if(checkData!= null && checkData!= undefined){
		$("#applyPayLaunchForm").setForm({ 
			"loanBankNewId":checkData.id,
			"bankAccountName":checkData.bankAccountName,
			"bankAccount":checkData.bankAccount,
			"bankId":checkData.bankName,
			"bankName":checkData.bankName
			});
		}else{
			top.$.jBox.tip('请选择需要更改的划扣账户!');
		}
	});
	

	/**
	 * @function 汇款实际到账金额累加
	 */
	$('#appendTab').on('blur','tr', function(event) {
		var amountCount = 0;
		$('input[id="applyAmountCopy"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			if(amountCount.toFixed(2) < 0){
				top.$.jBox.tip('实际到账金额不能为负数!');
			}else{
				$("#transferAmount").val(amountCount.toFixed(2));
			}
		});
	});
	/**
	 * @function pos实际到账金额累加
	 */
	$('#appendTabPos').on('blur','tr', function(event) {
		var amountCount = 0;
		$('input[id="checkApplyAmountCopyPosCard"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			if(amountCount.toFixed(2) < 0){
				top.$.jBox.tip('实际到账金额不能为负数!');
			}else{
				$("#transferAmountPosCard").val(amountCount.toFixed(2));
			}
		});
	});
	
	
	/**
	 * @function 实际到账金额累加POS查账金额
	 */
	$('#appendTabPos').on('blur','tr', function(event) {
		var amountCount = 0;
		$('input[id="applyAmountCopyPosCard"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			if(amountCount.toFixed(2) < 0){
				top.$.jBox.tip('实际到账金额不能为负数!');
			}else{
				$("#transferAmountPosCard").val(amountCount.toFixed(2));
			}
		});
	});
	
	/**
	 * 点击中和公司查账按钮
	 */
	$("#operateRole").click(function(){
		var storesInAccount = $("#storesInAccount").val();
		if($(this).attr("checked")){
			$.ajax({
				type : "POST",
				datatype : "json",
				url:ctx+"/borrow/payback/applyPayback/getMiddlePersonList",
				success:function(data){
					var jsonData = eval("("+data+")");
					var html="<option>请选择</option>";
					$.each(jsonData, function (n, v) {
						var selected=""
						if(storesInAccount==v.bankCardNo){
							selected="selected";
						}
			              html+="<option value="+v.bankCardNo+" "+selected+">"+v.midBankName+"</option>";
			        });
					$("#storesInAccount").html(html);
					$("#storesInAccount").trigger("change");
				}
			});
		}else{
			var selected=$("#storesInAccount").find("option:selected").text();
			if(selected=='中和-中国工商银行' || selected=='中和-招商银行'){
				$("#storesInAccount option[value='']").attr("selected", true);
			}
			$("#storesInAccount option:contains('中和-中国工商银行')").remove();
			$("#storesInAccount option:contains('中和-招商银行')").remove();
			$("#storesInAccount").trigger("change");
		}
		
	});
});

	/**
	 * @function 新增一条银行账款数据
	 */
	function appendRow(){ 
		//数据校验
		
		   //存款方式
		   var dictDeposit=	$("input[name^='paybackTransferInfo.dictDeposit'").val();
		   //存款时间
		   var tranDepositTimeStr=	$("input[name^='paybackTransferInfo.tranDepositTimeStr'").val();
		  //实际到账金额
	      var reallyAmountStr=$("input[name^='paybackTransferInfo.reallyAmountStr'").val();
	     //实际存入人
	      var depositName=$("input[name^='paybackTransferInfo.depositName'").val();
	
			
	  	if(dictDeposit==null || dictDeposit=="" ){
	  		   top.$.jBox.tip('请填写存款方式！');
	  		   return false;
	  	}else if (tranDepositTimeStr==null || tranDepositTimeStr==""){
	  	       top.$.jBox.tip('请选择存款时间！');
	  	   return false;
	  	}else if (reallyAmountStr==null || reallyAmountStr==""){
	  		   top.$.jBox.tip('请填写实际到账金额！');
	  	    return false;
	  	}else if (depositName==null || depositName== ""){
	  		   top.$.jBox.tip('请填写实际存入人！');
	  		 return false;
	  	}
	  	
		var files = $('[name="files"]').val()
		if (files.length > 0) {
			files = files.substring(files.lastIndexOf("."));
			if (files != ".jpg" && files != ".png" && files != ".jpeg") {
				top.$.jBox.tip('请上传图片格式的的凭条!');
				return false;
			}
		} else {
			top.$.jBox.tip('请上传凭条!');
			return false;
		}
		
		var tr = $('#appendId').clone();
		tr.find('input[name="files"]').val('');
		$('#appendTab tr:last').after(tr);
		
		var amountCount = 0;
		$('input[name="paybackTransferInfo.reallyAmountStr"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			if(amountCount.toFixed(2) < 0){
				top.$.jBox.tip('实际到账金额不能为负数!');
			}else{
				$("#transferAmount").val(amountCount.toFixed(2));
			}
		});
	}

	/**
	 * @function 删除一条银行账款数据
	 */
	function deleteRow(){
		if($("#appendTab tr").length >= 4){
			$("#appendTab tr:last").remove();
		}
		else {
			top.$.jBox.tip('最后一条数据不允许删除');
		}
		var amountCount = 0;
		$('input[name="paybackTransferInfo.reallyAmountStr"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			if(amountCount.toFixed(2) < 0){
				top.$.jBox.tip('实际到账金额不能为负数!');
			}else{
				$("#transferAmount").val(amountCount.toFixed(2));
			}
		});
	}
	
	
	/**
	 * @function 新增一条pos机刷卡查账款数据
	 */
function append(){ 
   var a=0;
   //存款方式
   var coCode=	$("input[name^='posCardInfo.referCode'").val();
   //参考号
	var coCode=	$("input[name^='posCardInfo.referCode'").val();
	//到账日期
	var srDate=	$("input[name^='posCardInfo.paybackDateStr'").val();
	//实际到账金额
	var srDumon=$("input[name^='posCardInfo.applyReallyAmountPosCard'").val();
	//订单号
	var srNumber=$("input[name^='posCardInfo.posOrderNumber'").val();
	//存入账户
	var posCh=$("select[name^='posCardInfo.dictDepositPosCard'").val();
		
	if(posCh==null || posCh=="" || posCh=="请选择"){
		   top.$.jBox.tip('请选择存款方式！');
		   return false;
	}else if (coCode==null || coCode==""){
	       top.$.jBox.tip('参考号不能为空！');
	   return false;
	}else if (srDate==null || srDate==""){
		   top.$.jBox.tip('到账日期不能为空！');
	    return false;
	}else if (srDumon==null || srDumon== ""){
		   top.$.jBox.tip('实际到账金额不能为空！');
		 return false;
	}else if (srNumber==null || srNumber==""){ 
		 top.$.jBox.tip('订单号不能为空！');
		 return false;
	}
	 	$("input[name^='posCardInfo.referCode'").each(function(index,e){
	 		   if(coCode==e.value){
	 			   a++;
	 			   if(a>=2){
	 				  top.$.jBox.tip('参考号 '+coCode+'重复！ ');
	 				  return false;
	 			   }
	 		   }
		});
	 	if (a<=1){
	 		 var tr = $('#appendIdPos').clone().unbind();
				tr.insertAfter('#appendTabPos tr:last');
				$('input[name="paybackTransferInfo.tranDepositTimeStrPos"]').each(function(idx) {
					$(this).daterangepicker({ 
						 singleDatePicker: true,
						 startDate: moment().subtract('days')
					});
				});
	 	}
	 	var amountCount = 0;
		$('input[id="checkApplyAmountCopyPosCard"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			if(amountCount.toFixed(2) < 0){
				top.$.jBox.tip('实际到账金额不能为负数!');
			}else{
				$("#transferAmountPosCard").val(amountCount.toFixed(2));
			}
		});
	}
	/**
	 * @function 删除一条pos机刷卡查账数据
	 */
	function deleteR(){
		if($("#appendTabPos tr").length >= 4){
			$("#appendTabPos tr:last").remove();
		}
		else {
			top.$.jBox.tip('最后一条数据不允许删除');
		}
		var amountCount = 0;
		$('input[id="checkApplyAmountCopyPosCard"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			if(amountCount.toFixed(2) < 0){
				top.$.jBox.tip('实际到账金额不能为负数!');
			}else{
				$("#transferAmountPosCard").val(amountCount.toFixed(2));
			}
		});
	} 
	
	
	/**
	 * @function 判断POS后台是否有相关的POS数据
	 */
	function checkRefPosStr(chaCode,posnum,srDate,srDumnet){
		  $.ajax({  
			   type : "POST",
			   data:{
				   chaCode:chaCode,
				   posnum:posnum,
				   srDate:srDate,
				   srDumnet:srDumnet
			   },
				url : ctx+"/borrow/poscard/posBacktageList/checkRefPosStr",
				datatype : "json",
				success : function(data){
					if(data==-1){
				     top.$.jBox.tip('未找到相关POS刷卡信息！请核实参考号 '+chaCode+'，订单号'+posnum+'，刷卡日期'+srDate+'，金额 '+srDumnet+' 是否有误！');
				     return false;
					}
				},
				//其他异常错误
				error: function(){
						top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!');
					}
			});
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
	
	/**
	 * 校验日期是否为空
	 * @param $
	 */
	function checkDateStr(obj){
	  if(obj.value==null || obj.value==" "){
		  top.$.jBox.tip('到账日期不能为空，请选择！');
		  obj.focus();
	  }	
	}
	
	/**
	 * 实际到账金额  
	 * @param $
	 */
	function checkCardStr(obj){
	  if(obj.value=='' || obj.value==null){
		  top.$.jBox.tip('实际到账金额不能为空，请填写！');
		  obj.focus();
	  }else {
			var regex1=/^\d{1,10}\.\d{1,2}$/;
			var regex2=/^\d{1,10}$/;
			var isOK = regex1.test(obj.value)||regex2.test(obj.value);
			if (!isOK){
				  top.$.jBox.tip('实际到账金额只能为数字！');
				  obj.value="";
				  obj.focus();
			}
    	  }
	   }
	
	
	/**
	 * 订单号校验
	 * @param $
	 */
	function checkNumber(obj){
	  if(obj.value=='' || obj.value==null){
		  top.$.jBox.tip('订单号不能为空！，请填写！');
		  obj.focus();
	  }else {
			var regex1=/^\d{1,10}\.\d{1,2}$/;
			var regex2=/^\d{1,24}$/;
			var isOK = regex1.test(obj.value)||regex2.test(obj.value);
			if (!isOK){
				  top.$.jBox.tip('订单号只能为数字！');
				  obj.value="";
				  obj.focus();
			}
	    }
	}
	
	/**
	 * 校验参考号非空，是否数字，以及重复
	 * @param $
	 */
	function checkReferCode(obj){
    var chaceStr=true;		
	var b =0;
	  if(obj.value=='' || obj.value==null){
		  top.$.jBox.tip('参考号不能为空，请填写！');
		  obj.focus();
		  return false;
	  }else if(obj.value!='' && obj.value!=null) {
			var regex1=/^\d{1,10}\.\d{1,2}$/;
			var regex2=/^\d{1,15}$/;
			var isOK = regex1.test(obj.value)||regex2.test(obj.value);
			if (!isOK){
				  top.$.jBox.tip('参考号只能为数字！');
				  obj.value="";
				  obj.focus();
				  return false;
			}
	  }
		  /**
			 * 校验参考号重复
			 * @param $
			 */ 
		$("input[name^='posCardInfo.referCode'").each(function(index,e){
		       var chaCode =obj.value;
				 if(chaCode==e.value){
					 b++;
					 if(b>=2){
					     top.$.jBox.tip('参考号  '+chaCode +' 重复,请重新填写！');
						 obj.focus();
						 chaceStr= false;
					 } 
				 }
		});
	  
 	    if(chaceStr){
 	   	var chaCode =obj.value;
		//订单号
	    var posnum =$(obj).parent().next().children('input').val();
	    if(posnum==null || posnum == ''){
	  	  top.$.jBox.tip('订单号不能为空！，请填写！');
	  	 return false;
	    }
		//到账日期
		var srDate=$(obj).parent().prev().prev().children('input').val();
		if(srDate==null || srDate==''){
		  top.$.jBox.tip('到账日期不能为空，请选择！');
		  return false;
		}
	 	//金额
	    var srDumnet=$(obj).parent().prev().children('input').val();
        if(srDumnet==null || srDumnet=='' ){
           top.$.jBox.tip('实际到账金额不能为空，请填写！');
           return false;
		}
  	   // ajax 查询是否有未匹配的数据 方法 开始----------------------------------------
 		//参考号
       /* checkRefPosStr(chaCode,posnum,srDate,srDumnet);*/
 	   // ajax 方法结束-----------------------------------------
 	    }
 	    
 	    
 	
  }
	
	
	
	
	
	
	