var deductpayback = {};
/**
 * 一键发送
 */
deductpayback.sendAllPlat = function() {
	
		$("#modifyPlatForm").bind('click', function() {
			 art.dialog.confirm('是否发送数据？', 
					    function () {
				                  sendmethod();
					    }, 
					    function () {
					      // art.dialog.tips('');
					});
		});
}

/**
 * 线上划扣
 */
deductpayback.onlinededuct = function() {
	$("#onlinedeductForm").bind('click', function() {
		art.dialog({
			content: document.getElementById("onlineDeductDiv"),
			title:'线上划扣',
			fixed: true,
			lock:true,
			id: 'onlinConfirm',
			okVal: '确认发送',
			ok: function () {
				onlinesendmethod(this);
			},
			cancel: true
		});
	});
}	

/**
 * 线下划扣
 */
deductpayback.exportAndImport = function() {
	$("#realdeductForm").bind('click', function() {
		art.dialog({
		    content: document.getElementById("realDeductDiv"),
		    title:'线下划扣处理',
		    fixed: true,
		    lock:true,
		    width:400,
		    id: 'oflineconfirm',
		    okVal: '确认',
		    ok: function () {
		    	ExcelorImport();
		    	art.dialog.close();
				return true;
		    },
		    cancel: true
		});
	});
}

/**
 * 批量退回
 */
deductpayback.deductBack = function() {
	$("#deductBackbutton").bind('click', function() {
		$("#applyBackMes").val("");
		art.dialog({
			content: document.getElementById("deductBackDiv"),
			title:'批量退回',
			fixed: true,
			lock:true,
			width:400,
			id: 'deductBackid',
			okVal: '确认',
			ok: function () {
				dedutpayBack();
				art.dialog.close();
				return false;
			},
			cancel: true
		});
	});
}

/**
 * 委托充值
 */
deductpayback.trustRecharge = function() {
	$("#trustRecharge").bind('click', function() {
		art.dialog({
			content: document.getElementById("trustRechargeDiv"),
			title:'委托充值',
			fixed: true,
			lock:true,
			width:400,
			id: 'rechargeDivid',
			okVal: '确认',
			ok: function () {
				var flag= false
				$(":input[name='checkBox']:checked").each(function(){
				   if($(this).attr("trustRecharge")!="1"){
					   flag = true;
					   return false;
				   }
				})
				if(flag){
					art.dialog.alert("提交数据含有非委托充值数据，请重新选择!");
					return false;
				}
				var operType = $("#trustRechargeDiv input:radio[name='trustRd']:checked").val()
				
				if(operType=='1'){
					//导出
					var idVal="";
					$("input:checkbox[name='checkBox']:checked").each(function(){						
						idVal+=$(this).next().val()+",";
					});
					url=ctx+"/borrow/payback/deduct/trustRechargeOutput"; 
				
				    $("#ids").val(idVal);
				    var search_trustRechargeFlag = $("#trustRechargeFlag").val();
				    $("#trustRechargeFlag").attr('value','1');
				    $("#applyPayDeductForm").attr("action",url);
				    $("#applyPayDeductForm").submit();
				    $("#ids").val("");
				    $("#applyPayDeductForm").attr("action",ctx+"/borrow/payback/deduct/list");
				    $("#trustRechargeFlag").attr('value',search_trustRechargeFlag);
				}else if(operType=='2'){
					//上传
					ajaxFileUpload2('0');
				}else{
					//线上委托充值
					var idVal="";
					$("input:checkbox[name='checkBox']:checked").each(function(){						
						idVal+=$(this).next().val()+",";
					});				
				    $("#ids").val(idVal);
				    
					$.ajax({  
					    type : "POST",
					    data:$("#applyPayDeductForm").serialize(),
						url :ctx+"/borrow/payback/deduct/trushRechargeOnline",
						datatype : "json",
						success : function(data){
							 if(eval(data)==""){
								 art.dialog.alert("委托充值成功",function(){
									 document.forms[0].submit()
								 });
							 }else{
								 art.dialog.alert(eval(data),function(){
									 document.forms[0].submit()
								 });
							 }							 
						},
						error: function(){
							art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
						}
					});
					$("#ids").val("");
				}
				art.dialog.close();
				return true;
			},
			cancel: true
		});
	});
}

/**
 * 划拨
 */
deductpayback.deductTrust = function() {
	$("#trustDeduct").bind('click', function() {
		art.dialog({
			content: document.getElementById("trustDeductDiv"),
			title:'划拨',
			fixed: true,
			lock:true,
			width:400,
			id: 'trustDeductDivid',
			okVal: '确认',
			ok: function () {
				var flag= false
				$(":input[name='checkBox']:checked").each(function(){
				   if($(this).attr("model")!="1"){
					   flag = true;
					   return false;
				   }
				})
				if(flag){
					art.dialog.alert("提交数据含有非TG的数据，请重新选择!");
					return false;
				}
				var operType = $("#trustDeductDiv input:radio[name='opeType']:checked").val()
				
				if(operType=='1'){
					//导出
					var idVal="";
					$("input:checkbox[name='checkBox']:checked").each(function(){						
						idVal+=$(this).next().val()+",";
					});
					url=ctx+"/borrow/payback/deduct/trustDeductOutput"; 
				
					$("#ids").val(idVal);
				    var loanFlag = $("#loanFlag").val();
				    //$("#loanFlag").attr('value','3');
				    $("#applyPayDeductForm").attr("action",url);
				    $("#applyPayDeductForm").submit();
				    $("#ids").val("");
				    $("#applyPayDeductForm").attr("action",ctx+"/borrow/payback/deduct/list");
				    $("#loanFlag").attr('value',loanFlag);
				}else if(operType=='2'){
					//上传
					ajaxFileUpload2('1');
				}else{
					//线上划拨
					var idVal="";
					$("input:checkbox[name='checkBox']:checked").each(function(){						
						idVal+=$(this).next().val()+",";
					});
				
				    $("#ids").val(idVal);
				    var loanFlag = $("#loanFlag").val();
				    //TG标识
				   // $("#loanFlag").attr('value','3');				    
				    
				    $.ajax({  
					    type : "POST",
					    data:$("#applyPayDeductForm").serialize(),
						url :ctx+"/borrow/payback/deduct/trustDeductOnline",
						datatype : "json",
						success : function(data){
							if(eval(data)==""){
								art.dialog.alert("已执行划拨",function(){
									document.forms[0].submit();
								});
							}else{
								art.dialog.alert(eval(data).replace(/;/g,"<br>"),function(){
									document.forms[0].submit();
								});
							}
										 
						},
						error: function(){
							art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
						}
					});
				    $("#ids").val("");
				    $("#loanFlag").attr('value',loanFlag);
				}
				art.dialog.close();
				return true;
			},
			cancel: true
		});
	});
}

function sendmethod() {
	var dialog = art.dialog({
	      content: '发送中。。。',
	      cancel:false,
	      lock:true
	});
	var url = ctx + "/borrow/payback/deduct/oneKeysendDeductor"; // 设置url
	$("#deductId").val("key"); //一键发送是
	$("#onekeySned").attr("action", url);
	$("#onekeySned").submit();
    $("#applyPayDeductForm").attr("action",ctx+"/borrow/payback/deduct/list");
}

// 线上划扣
function onlinesendmethod(obj) {
	  $(obj).attr("disabled",true);
	   var id="";
	   var dictDealType="";// 选择的划扣平台
	   var deductMethod=$("input[name='back']:radio:checked").val(); //实时批量
		   dictDealType=$("#realTimePlatSelect").val();
	   if(!dictDealType){
		   art.dialog.alert("请划扣平台！");
		   return;
	   }
	   if(!deductMethod){
		   art.dialog.alert("请选择是否批量！");
		   return;
	   }
	   $("input:checkbox[name='checkBox']:checked").each(function(){
		   id +=","+$(this).next().val();
	   });
	   rule = dictDealType +':'+ deductMethod;
	   $("#rule").val(rule); // 设置规则
	   $("#ids").val(id); // 设置规则
	   $("#deductType").val(dictDealType);
	   var  url = ctx+"/borrow/payback/deduct/buckleOnLine";
	   var dialog = art.dialog({
		      content: '发送中。。。',
		      cancel:false,
		      lock:true
	 	});
	   $("#applyPayDeductForm").attr("action",url);
	   $("#applyPayDeductForm").submit();
	   $("#applyPayDeductForm").attr("action",ctx+"/borrow/payback/deduct/list");
}

$(document).ready(function(){
	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	
	// 初始化tab
	$("#deductTab").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#deductTab').bootstrapTable({
		method: 'get',
		cache: false,
		height: 350,
		striped: true,
		pageSize: 20,
		pageNumber:1
	});
	/**
	 * @function 搜索条件显示隐藏事件
	 */
	$('#showSearchDeductBtn').click(function() {
		if($("#searchDeduct").css('display')=='none'){
			$("#showSearchDeductBtn").attr('src',context + '/static/images/down.png');
			
			$("#searchDeduct").show();
			$("#T2").show();
			$("#T3").show();
			$("#T4").show();
		}else{
			$("#showSearchDeductBtn").attr("src",context + '/static/images/up.png');
			
			$("#searchDeduct").hide();
			$("#T2").hide();
			$("#T3").hide();
			$("#T4").hide();
		}
	});
	
	/**
	 * @function 清除搜索表单数据
	 */
	$('#clearDeductBtn').click(function() {
		$(':input','#auditForm') 
		.not(':button, :submit, :reset, :hidden') 
		.val('') 
		.removeAttr('selected');
	});
	
	/**
	 * 页面办理按钮事件
	 */
	$(":input[name='deductInfoBtn']").each(function(){
		$(this).bind('click',function(){
			$('#deductContractCode').val($(this).attr('deductContractCode'));
			$('#matchingId').val($(this).attr('applyPaybackId'));
			$("#deductInfoForm").submit();
		});
	});
	
	/**
	 * @function 全选按钮事件
	 */
	$('#checkBoxAll').click(function() {
		if(this.checked){
			alert("2ww")
			$("input[name='checkBox']").attr("checked", true);  
		}else{
			$("input[name='checkBox']").attr("checked", false);
		}
	});
	
	/**
	 * @function 批量退回
	 */
	$('#backConfirm').click(function() {
		
	});
	 //还款日验证
	   $("#repaymentDate").blur(function(){
	   	var da = $("#repaymentDate").val();
	   	var re = /^((0?[1-9])|((1|2)[0-9])|30|31)$/;
	   	if (da != null && "" != da) {
	   		if (!re.test(da)){
	   			artDialog.alert('请输入1~31之间的整数!');
	   			$("#repaymentDate").focus();
	   			return;
	   		}
	   	}
	   });
	   
	 //全选和反选
	$("#checkAll").click(function() {
		$("input[name='checkBox']").attr("checked", this.checked);
		var totalMoney = 0;
		var totalNum = 0;
		if (this.checked) {
			$("[name='checkBox']").each(function() {
				totalNum = totalNum + 1;
				totalMoney = totalMoney + parseFloat($(this).attr("applyAmount"));
			});
			$("#total_money").text(totalMoney.toFixed(2));
			$("#total_num").text(totalNum);
		} else {
			$("#total_money").text("0.00");
			$("#total_num").text("0");
		}
	}); 
	
	//为每一条记录的复选框绑定事件
	$("[name='checkBox']").click(function() {
		var totalMoney = 0.0;
		var totalNum = 0;
		$("[name='checkBox']").each(function() {
			if (this.checked) {
				totalMoney = totalMoney + parseFloat($(this).attr("applyAmount"));
				totalNum = totalNum + 1;
			}
			$("#total_money").text(totalMoney.toFixed(2));
			$("#total_num").text(totalNum);
		});
		var checkBox = $("input:checkbox[name='checkBox']").length;
		var checkBoxchecked = $("input:checkbox[name='checkBox']:checked").length
		/*if(checkBoxchecked==0){
			$("#checkAll").attr("checked", false);
		}*/
		if(checkBox==checkBoxchecked){
			$("#checkAll").attr("checked", true);
		}else{
			$("#checkAll").attr("checked", false);
		}
	});
	   
});

function hideUpload(){
	$("#T").attr("style","display:none;");
	$("#T").attr("style","display:none;");
}
function showUpload(){	
	$("#T").removeAttr("style");
	$("#T").removeAttr("style");
	if($("#platId").val()=='3'){
		$("#templateType").attr("style","display:block;");
	}; 
}

function diashow(){
	$(".DT").show();
}
function diahide(){
	$(".DT").show();
	$("#DT").hide();
}

function diahideAll(){
	$(".DT").hide();
}

function trustDeductshow(){
	$(".ODT").show();
}
function trustDeductHide(){
	$(".ODT").show();
	$("#ODT").hide();
}
function trustDeductHideAll(){
	$(".ODT").hide();
}

/**
 * @function 页面分页
 * @param n
 * @param s
 * @returns {Boolean}
 */
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#applyPayDeductForm").attr("action",  ctx + "/borrow/payback/deduct/list");
	$("#applyPayDeductForm").submit();
	return false;
}
var flagplat = false;

// 导出
function ExcelorImport(){
	var idVal ='';
	var platId="";
	var flag = $("#tpTable input:radio[name='tp']:checked").val();
	var fileFormat =  $("#fileFormat").val();
	// 导出
	if(flag==1){
	 var platId = $("#platId").val();
		if(fileFormat=="txt"){
			if(platId == '0' || platId == '3'){
				 art.dialog.alert("该平台不支持txt导出！");
				 return false;
			}
		}
	 if(platId){
	          var url = "";
			  $("input:checkbox[name='checkBox']:checked").each(function(){
				 idVal+=","+$(this).next().val();
			   });
			  $("#ids").val(idVal);
			  $("#plat").val(platId);
			   var plat = $("#dictDealplat").val();
			  if(platId==0){
				  // 富有导出
				  url=ctx+"/borrow/payback/deduct/exportExcelFy"; 
			  }else if(platId==1){
				  // 好易联
				  if(fileFormat=="txt"){
				  url=ctx+"/borrow/payback/deduct/exportTxtHyl"; 
				  }else{
			      url=ctx+"/borrow/payback/deduct/exportExcelHyl";   
				  }
			  }else if(platId==2){
				  // 中金
				  // 中金
				  if(fileFormat=="txt"){
					  url=ctx+"/borrow/payback/deduct/exportTxtZj"; 
				  }else{
					  url=ctx+"/borrow/payback/deduct/exportExcelZj"; 
				  }
				  
			  }else if(platId==3){
				  // 通联
				  url=ctx+"/borrow/payback/deduct/exportExcelTl"; 
			  }
		      $("#applyPayDeductForm").attr("action",url);
			  $("#applyPayDeductForm").submit();
			  $("#applyPayDeductForm").attr("action",ctx+"/borrow/payback/deduct/list");
		      }else{
		    	  art.dialog.alert("请选择划扣平台！") 
		  }
	}else{
		// 导入
		ajaxFileUpload();
	 }
}
function ajaxFileUpload() {
	 var plat = $("#platId").val();
	 if(!plat){
		 art.dialog.alert("请选择平台！");
		 return;
	 }
	 
	 var file=$("#fileid").val();
     if($.trim(file) == ''){
    	 art.dialog.alert("请选择文件");
            return false;
    }
    var extStart=file.lastIndexOf(".");
	var ext=file.substring(extStart,file.length).toUpperCase();
    if(ext!=".XLSX" && ext!=".XLS"){
    	art.dialog.alert("仅限于上传Excle文件");
    	$("#fileid").val('');
    	return false;
    }
     
    var url = "";
    
    if(plat==0){
		  // 富有导出
		  url=ctx+"/borrow/payback/deduct/importExcelFy"; 
	  }else if(plat==1){
		  // 好易联
		  url=ctx+"/borrow/payback/deduct/importExcelHyl"; 
	  }else if(plat==2){
		  // 中金
		  
		  url=ctx+"/borrow/payback/deduct/importExcelZj"; 
	  }else if(plat==3){
		  // 通联
			 url=ctx+"/borrow/payback/deduct/importExcelTl1"; 
	  }
   // 开始上传文件时显示一个图片
   $.ajaxFileUpload({
  	   url : url,
       type: 'post',
       secureuri: false, // 一般设置为false
       fileElementId: "fileid", // 上传文件的id、name属性名
       dataType: 'json', // 返回值类型，一般设置为json、application/json
       data:{
      	 "plat":plat
       },
       success: function(data, status){  
           //if(data=="success"){
        	   art.dialog.alert("上传成功！");
        	   window.location.reload(true);
          // }
       },
       error: function(data, status, e){ 
    	   art.dialog.alert("上传失败！");
       }
   });
   // return false;
}

//委托充值/划拨上传
function ajaxFileUpload2(t) {
	  var file=$("#fileid").val();
     if($.trim(file)==''){
   	  art.dialog.alert("请选择文件");
            return false;
    }
    var extStart=file.lastIndexOf(".");
	    var ext=file.substring(extStart,file.length).toUpperCase();

	    if(ext!=".XLSX" && ext!=".XLS"){
	    	art.dialog.alert("仅限于上传Excel文件");
	    	$("#fileid").val('');
	    	return false;
	    }
	    var url;
	    if(t=='0'){
	    	//委托充值上传
	    	url=ctx+"/borrow/payback/deduct/trustRechargeInput";
	    } else if (t=='1'){
	    	//线下划拨上传
	    	url=ctx+"/borrow/payback/deduct/trustDeductInput";
	    }
     
   // 开始上传文件时显示一个图片
   $.ajaxFileUpload({
  	 url : url,
       type: 'post',
       secureuri: false, //一般设置为false
       fileElementId: "fileid", // 上传文件的id、name属性名
       dataType: 'json', //返回值类型，一般设置为json、application/json
       data:{ },
      // elementIds: elementIds, //传递参数到服务器
       success: function(data, status){ 
       	if(data==""){
       		art.dialog.alert("上传成功！",function(){
       			window.location.reload(true);
       		});	
       	}else{
       		art.dialog.alert(data.replace(/;/g,"<br>"),function(){
       			window.location.reload(true);
       		})       		
       	}       	
       },
       error: function(data, status, e){ 
       	art.dialog.alert(e);
       }
   });
}

// 线上划扣弹框中的批量实时按钮事件
function showPingtai(){
	var elements =  document.getElementsByName("back");
		for(var i = 0 ; i < elements.length ;i++){
			var clickEle= elements[i].value;
			if(elements[i].checked){
				if("0" == clickEle){
				document.getElementById("qishu_div2").style.display = "inline";
				document.getElementById("qishu_div3").style.display = "none";
			    }else{
				 document.getElementById("qishu_div3").style.display = "inline";
				 document.getElementById("qishu_div2").style.display = "none";
			   } 
			}
	   }
}

//点击清除按钮调用的的方法
function resets(){
	// 清除text	
	$(":input").val('');
	// 清除checkbox	
	//$(":checkbox").attr("checked", false);
	// 清除radio			
	//$(":radio").attr("checked", false);
	// 清除select			
	//$("select").val("");
	$("#applyPayDeductForm").submit();
	
	/*$(':input','#applyPayDeductForm')
	  .not(':button, :reset,:hidden')
	  .val('')
	  .removeAttr('checked')
	  .removeAttr('selected');*/
}

/**
 * 退回方法
 * @returns {Boolean}
 */
function dedutpayBack(){
	var flag= false
		$(":input[name='checkBox']:checked").each(function(){
		   var d =  $(this).attr("splitBackResult");
		   if(d==3 || d==4){
			   flag = true;
			   return false;
		   }
		})
		
		if(flag){
			art.dialog.alert("提交数据有划扣中和线下导出，请从新选择!");
			return false;
		}
		var applyBackMes = $('#applyBackMes').val();
		   applyBackMes = $.trim(applyBackMes);
		 if(!applyBackMes){
			   art.dialog.alert("请输入退回内容！");
			   return false;
		   }
		 if(applyBackMes.length>1500){
			   art.dialog.alert("超过1500字符，从新输入！");
			   return false;
		  }
		var ids ='';
		var contractCodes='';
		$("input[name='checkBox']:checked").each(function(){
			ids +="," + $(this).attr("applyDeductId");
		 });
		var dialog = art.dialog({
		      content: '退回中。。。',
		      cancel:false,
		      lock:true
	 	});
	   $("#ids").val(ids);
	   $("#backmsg").val(applyBackMes);
	   $("#applyPayDeductForm").attr("action",ctx+"/borrow/payback/deduct/stayDeductBatchBack");
	   $("#applyPayDeductForm").submit();
	   $("#applyPayDeductForm").attr("action",ctx+"/borrow/payback/deduct/list");
   }
   $(function(){
	  $("#realTimePlatSelect").change(function(){
		     
		  var plantValue = $(this).val();
		  if(plantValue=='0'){
			   $("#deductTime").css("display","none")
			   $("#deductTimeFy").css("display","block")
		  }else{
			  $("#deductTime").css("display","block")
			  $("#deductTimeFy").css("display","none")
		  }
		  
	  })
})
 
function plantChage(obj,flag){
	
	 var plantValue = $(obj).val();
	  if(plantValue=='0'){
		   $("#deductTime"+flag).css("display","none")
		   $("#deductTimeFy"+flag).css("display","block")
	  }else{
		  $("#deductTime"+flag).css("display","block")
		  $("#deductTimeFy"+flag).css("display","none")
	  }
	
}

$(function(){
	
	$("#realTimePlatSelect").change(function(){
		var pant = $(this).val();
	  if(pant){
		$.ajax({  
		    type : "POST",
		    data : {'platformId':pant},
			url :ctx+"/borrow/payback/payBackSplitapply/selPlatformBank",
			datatype : "json",
			success : function(data){
				var table = $("#onlineDeductTB");
				var tr = $("<tr id ='deductTime'></tr>")
				var td = $("<td><label class='lab'>是否批量：</label></td>")
				table.empty();
				for(var i=0;i<data.length;i++){
					if(data[i].deductType==0){
						td.append("<input type='radio' name='back' value="+data[i].deductType+">实时");
					}else{
						td.append("<input type='radio' name='back' value="+data[i].deductType+">批量");
					}
				}
				tr.append(td);
				table.append(tr);
			 },
			error: function(){
				art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
			}
		});
	   }else{
		   $("#onlineDeductTB").empty();
	   }
	})
})

function isDigit(obj){
	 var test_value= $(obj).val();
	 if(test_value){
	// var patrn=/^([1-9]\d*|0)(\.\d*[1-9])?$/;
	   var patrn = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	 if (!patrn.exec(test_value)){  
		 $(obj).val(0);
		 artDialog.alert("请输入正确的金额");
		 return;
	 }
	} 
}

$(function(){
	//导出Excel数据表
	$("#exportExcel").click(function() {
		var idVal = "";
		  $("input:checkbox[name='checkBox']:checked").each(function(){
			  idVal=idVal+","+$(this).next().val();
		   });
		   $("#ids").val(idVal);
		   $("#applyPayDeductForm").attr("action",ctx+"/borrow/payback/deduct/exportList");
		   $("#applyPayDeductForm").submit();
		   $("#applyPayDeductForm").attr("action",ctx+"//borrow/payback/deduct/list");
	});
	
	$.ajax({  
		    type : "POST",
			url :ctx+"/borrow/payback/deduct/getSystemSetting",
			datatype : "json",
			success : function(data){
				if(data == 'success'){
					$("#startRoll").attr("disabled",true);
					$("#stopRoll").attr("disabled",false);
					$("#deductBackbutton").attr("disabled",true);
					$("#onlinedeductForm").attr("disabled",true);
					$("#realdeductForm").attr("disabled",true);
					$("#modifyPlatForm").attr("disabled",true);

				}else{
					$("#startRoll").attr("disabled",false);
					$("#stopRoll").attr("disabled",true);
					$("#deductBackbutton").attr("disabled",false);
					$("#onlinedeductForm").attr("disabled",false);
					$("#realdeductForm").attr("disabled",false);
					$("#modifyPlatForm").attr("disabled",false);

				}
			 },
			error: function(){
				art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
			}
	});
})

function startOrstop(flag){
	
	if('0'==flag){
		 art.dialog.confirm('确认停止？', 
				    function () {
			                  rolls(flag);
				    }, 
				    function () {
				      // art.dialog.tips('');
				});
	}
	
	if('1'==flag){
		 art.dialog.confirm('确认开始？', 
				    function () {
			                  rolls(flag);
				    }, 
				    function () {
				      // art.dialog.tips('');
				});
	}
}

function rolls(flag){
	$.ajax({  
	    type : "POST",
	    data : {'sysValue':flag},
		url :ctx+"/borrow/payback/deduct/startOrstopRolls",
		datatype : "json",
		success : function(data){
			if(data=='success'){
				if(flag=='1'){
					art.dialog.alert("启动成功！");
					$("#startRoll").attr("disabled",true);
					$("#stopRoll").attr("disabled",false);
					$("#deductBackbutton").attr("disabled",true);
					$("#onlinedeductForm").attr("disabled",true);
					$("#realdeductForm").attr("disabled",true);
					$("#modifyPlatForm").attr("disabled",true);
				}else{
					art.dialog.alert("停止成功！");
					$("#startRoll").attr("disabled",false);
					$("#stopRoll").attr("disabled",true);
					$("#deductBackbutton").attr("disabled",false);
					$("#onlinedeductForm").attr("disabled",false);
					$("#realdeductForm").attr("disabled",false);
					$("#modifyPlatForm").attr("disabled",false);
				}
			}						 
		},
		error: function(){
			art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
		}
	});
}
	 