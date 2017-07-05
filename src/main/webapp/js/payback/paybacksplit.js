var deductpayback = {};


/**
 * 一键发送
 */
deductpayback.sendAllPlat = function() {
	
	$("#modifyPlatForm").bind('click', function() {
		  var flag = true;
		  $.ajax({  
			    type : "POST",
				url :ctx+"/borrow/payback/payBackSplitapply/getSystemSetting",
				datatype : "json",
				async: false,
				success : function(data){
					if(data == 'success'){
						  art.dialog.alert("滚动划扣在进行，不能一键发送");
						  flag = false;
						   return;
					 }
				 },
				error: function(){
					art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
				}
		 });
		if(flag){
		   art.dialog.confirm('是否发送数据？', 
				    function () {
			                  sendmethod();
				    }, 
				    function () {
				      // art.dialog.tips('');
				});
		 }
	 });
}


/**
 * 线上划扣
 */
deductpayback.onlinededuct = function() {

	$("#onlinedeductForm").bind('click', function() {
		  var flag = true;
		  $.ajax({  
			    type : "POST",
				url :ctx+"/borrow/payback/payBackSplitapply/getSystemSetting",
				datatype : "json",
				async: false,
				success : function(data){
					if(data == 'success'){
						  art.dialog.alert("滚动划扣在进行，不能线上划扣");
						  flag = false;
						   return;
					 }
				 },
				error: function(){
					art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
				}
		 });
		  if(flag){
			art.dialog({
				content: document.getElementById("onlineDeductDiv"),
				title:'线上划扣',
				fixed: true,
				lock:true,
				id: 'onlinConfirm',
				okVal: '确认发送',
				ok: function () {
					onlinesendmethod();
					art.dialog.close();
				},
				cancel: true
			 });
		  }
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
	$("#backFlagBtn").bind('click', function() {
		$("#textarea").val("");
		art.dialog({
			content: document.getElementById("backDiv"),
			title:'批量退回',
			fixed: true,
			lock:true,
			width:400,
			id: 'backDivid',
			okVal: '确认',
			ok: function () {
				splitdeductBack();
				art.dialog.close();
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
					url=ctx+"/borrow/payback/payBackSplitapply/trustRechargeOutput"; 
				
				    $("#ids").val(idVal);
				    var search_trustRechargeFlag = $("#trustRechargeFlag").val();
				    $("#trustRechargeFlag").attr('value','1');
				    $("#CenterapplyPayForm").attr("action",url);
				    $("#CenterapplyPayForm").submit();
				    $("#ids").val("");
				    $("#CenterapplyPayForm").attr("action",ctx+"/borrow/payback/payBackSplitapply/paybackSplitListApply");
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
					    data:$("#CenterapplyPayForm").serialize(),
						url :ctx+"/borrow/payback/payBackSplitapply/trushRechargeOnline",
						datatype : "json",
						success : function(data){
							 if(eval(data)==""){
								 art.dialog.alert("委托充值成功",function(){
									 document.forms[0].submit();
								 });
							 }else{
								 art.dialog.alert(eval(data),function(){
									 document.forms[0].submit();
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
					url=ctx+"/borrow/payback/payBackSplitapply/trustDeductOutput"; 
				
				    $("#ids").val(idVal);
				    var loanFlag = $("#loanFlag").val();
				    //$("#loanFlag").attr('value','3');
				    $("#CenterapplyPayForm").attr("action",url);
				    $("#CenterapplyPayForm").submit();
				    $("#ids").val("");
				    $("#CenterapplyPayForm").attr("action",ctx+"/borrow/payback/payBackSplitapply/paybackSplitListApply");
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
				    //$("#loanFlag").attr('value','3');				    
				    
				    $.ajax({  
					    type : "POST",
					    data:$("#CenterapplyPayForm").serialize(),
						url :ctx+"/borrow/payback/payBackSplitapply/trustDeductOnline",
						datatype : "json",
						success : function(data){
							if(data==""){
								art.dialog.alert("已执行划拨",function(){
									document.forms[0].submit();
								});				
							}else{
								art.dialog.alert(data,function(){
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

//全选和反选复选框   划扣金额和划扣笔数的显示
$(function(){
	$("#qishu_div3").css("display","none");
	//全选和反选
	$("#checkAll").click(function() {
		$("input[name='checkBox']").attr("checked", this.checked);
		var totalMoney = 0;
		var totalNum = 0;
		var totalreallyMoney =0;
		if (this.checked) {
			$("[name='checkBox']").each(function() {
				totalNum = totalNum + 1;
				totalMoney = totalMoney + parseFloat($(this).val()?$(this).val():0);
				totalreallyMoney = totalreallyMoney + parseFloat($(this).attr("reallyAmount") ? $(this).attr("reallyAmount"):0);
			});
			$("#total_money").text(totalMoney.toFixed(2));
			$("#total_reallyMoney").text(totalreallyMoney.toFixed(2));
			$("#total_num").text(totalNum);
		} else {
			$("#total_money").text(0);
			$("#total_reallyMoney").text(0);
			$("#total_num").text(0);
		}
	});
	//为每一条记录的复选框绑定事件
	$("[name='checkBox']").click(function() {
		var totalMoney = 0.0;
		var totalNum = 0;
		var  totalreallyMoney= 0;
	
		$("[name='checkBox']").each(function() {
			if (this.checked) {
				totalMoney = totalMoney + parseFloat($(this).val()?$(this).val():0);
				totalNum = totalNum + 1;
				totalreallyMoney = totalreallyMoney + parseFloat($(this).attr("reallyAmount") ? $(this).attr("reallyAmount"):0);
			}
			$("#total_money").text(totalMoney.toFixed(2));
			$("#total_num").text(totalNum);
			$("#total_reallyMoney").text(totalreallyMoney.toFixed(2));
			
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

	//清除按钮绑定事件
	$("#cleBtn").click(function(){
		$(":input").val("");
		$("#CenterapplyPayForm").submit();
	});
 
	//伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
			document.getElementById("T3").style.display='';
			document.getElementById("T4").style.display='';
			document.getElementById("T5").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
			document.getElementById("T3").style.display='none';
			document.getElementById("T4").style.display='none';
			document.getElementById("T5").style.display='none';
		}
	});
	
	
	//导出Excel数据表
	$("#exportAll").click(function() {
		window.location.href=ctx+"/borrow/payback/payBackSplitapply/exportExcelAll";
	});
	
	 //还款日验证
	   $("#repaymentDate").blur(function(){
	   	var da = $("#repaymentDate").val();
	   	if (da != null && "" != da) {
	   		var dar = eval(da);
	   		if (dar>31 || dar<1 ) {
	   			artDialog.alert('请输入1~31之间的数字!');
	   			$("#repaymentDate").focus();
	   			return;
	   		}
	   	}
	   });
});
// 显示历史弹框事件
function showHirstory(applyId){
	var url="ctx + borrow/payback/payBackSplitapply/getHirstory?applyId="+applyId;	
		  $.colorbox({      
	         	href:url,
		        iframe:true,
		        width:"900",
		        height:"500"
		 });
	
}
// 线上划扣弹框中的批量实时按钮事件
function showPingtai(){
var elements =  document.getElementsByName("back");
for(var i = 0 ; i < elements.length ;i++){
	var clickEle= elements[i].value;
	if(elements[i].checked){
		if("0"==clickEle){
			$("#qishu_div2").css("display","inline");
			$("#qishu_div3").css("display","none");
		}else{
		   $("#qishu_div3").css("display","inline");
			$("#qishu_div2").css("display","none");
	   } 
	 }
   }
}
//点击清除按钮调用的的方法
function resets(){
	// 清除text	
	$(":text").val('');
	// 清除checkbox	
	$(":checkbox").attr("checked", false);
	// 清除radio			
	$(":radio").attr("checked", false);
	// 清除select			
	$("select").val("");
	$("#CenterapplyPayForm").submit();
}
function diashow(){
	$("#DT").removeAttr("style");
	$("#DT").removeAttr("style");
	
}
function diahide(){
	$(".DT").removeAttr("style");
	$("#DT").attr("style","display:none;");
	$("#DT").attr("style","display:none;");
	if($("#platId").val()=='3'){
		$("#templateType").attr("style","display:block;");
	}; 
	
}
function diahideAll(){
	$(".DT").attr("style","display:none;");
}

function trustDeductshow(){
	$("#ODT").removeAttr("style");
	$("#ODT").removeAttr("style");	
}
function trustDeductHide(){
	$(".ODT").removeAttr("style");
	$("#ODT").attr("style","display:none;");
	$("#ODT").attr("style","display:none;");	
}
function trustDeductHideAll(){
	$(".ODT").attr("style","display:none;");	
}

function ExcelorImport(){
	var idVal ='';
	var flag = $("#tpTable input:radio[name='tp']:checked").val();
	var url = "";
	var fileFormat =  $("#fileFormat").val();
	//导出
	if(flag==1){
	   var platId = $("#platId").val();
		if(fileFormat=="txt"){
			if(platId == '0' || platId == '3'){
				 art.dialog.alert("该平台不支持txt导出！");
				 return false;
			}
		}
	   if(!platId){
		   art.dialog.alert("请选择划扣平台！");
			return ;
		 }
	   
	   $("input:checkbox[name='checkBox']:checked").each(function(){
			 idVal+=","+$(this).next().val();
		  });
	   if(platId==0){
			  // 富有导入
			  url=ctx+"/borrow/payback/payBackSplitapply/exportExcelFy"; 
		  }else if(platId==1){
			  // 好易联
			 if(fileFormat=="txt"){
			   url=ctx+"/borrow/payback/payBackSplitapply/exportTxtHyl"; 
			  }else{
			   url=ctx+"/borrow/payback/payBackSplitapply/exportExcelHyl";   
			  }
		  }else if(platId==2){
			  // 中金
			  if(fileFormat=="txt"){
				  url=ctx+"/borrow/payback/payBackSplitapply/exportTxtZj"; 
			  }else{
				  url=ctx+"/borrow/payback/payBackSplitapply/exportExcelZj"; 
			  }
			 
		  }else if(platId==3){
			  // 通联
			  url=ctx+"/borrow/payback/payBackSplitapply/exportExcelTl"; 
		  }
		  $("#ids").val(idVal);
		  $("#plat").val(platId);
		  var plat = $("#dictDealplat").val();
		  
		  $("#CenterapplyPayForm").attr("action",url);
		  $("#CenterapplyPayForm").submit();
		  $("#CenterapplyPayForm").attr("action",ctx+"/borrow/payback/payBackSplitapply/paybackSplitListApply");
		  
		 
	}else{
		
		ajaxFileUpload();
  }
}
function ajaxFileUpload() {
	 var plat = $("#platId").val();
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
      var url =""
    	  if(plat==0){
    		  // 富有导出
    		  url=ctx+"/borrow/payback/payBackSplitapply/importExcelFy"; 
    	  }else if(plat==1){
    		  // 好易联
    		  url=ctx+"/borrow/payback/payBackSplitapply/importExcelHyl"; 
    	  }else if(plat==2){
    		  // 中金
    		  url=ctx+"/borrow/payback/payBackSplitapply/importExcelZj"; 
    	  }else if(plat==3){
    		  // 通联
    			 url=ctx+"/borrow/payback/payBackSplitapply/importExcelTl1"; 
    	 }
    		  
      
    // 开始上传文件时显示一个图片
    $.ajaxFileUpload({
   	 url : url,
        type: 'post',
        secureuri: false, //一般设置为false
        fileElementId: "fileid", // 上传文件的id、name属性名
        dataType: 'json', //返回值类型，一般设置为json、application/json
        data:{
       	 "plat":plat
        },
       // elementIds: elementIds, //传递参数到服务器
        success: function(data, status){ 
        	/*if(data=="success"){
        		art.dialog.alert("上传成功！");	
        			//隐藏选择划扣平台弹框
 				$('#realDeductDiv').modal('hide');
 				window.location.reload(true);
        	}else{
        		art.dialog.alert("上传失败！")
        		window.location.reload(true);
        	}
        	*/
        		art.dialog.alert(data);	
        			//隐藏选择划扣平台弹框
 				$('#realDeductDiv').modal('hide');
 				window.location.reload(true);
        },
        error: function(data, status, e){ 
        	art.dialog.alert(e);
        }
    });
}

//委托充值/划扣上传
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
	 if (t=='0'){
		//委托充值上传
		 url=ctx+"/borrow/payback/payBackSplitapply/trustRechargeInput";
	 } else if (t=='1'){
		//线上划拨上传
		 url=ctx+"/borrow/payback/payBackSplitapply/trustDeductInput"; 
	 }
         		  
       
   // 开始上传文件时显示一个图片
   $.ajaxFileUpload({
  	 url : url,
       type: 'post',
       secureuri: false, //一般设置为false
       fileElementId: "fileid", // 上传文件的id、name属性名
       dataType: 'json', //返回值类型，一般设置为json、application/json
//     data:{},
      // elementIds: elementIds, //传递参数到服务器
       success: function(data, status){ 
       	if(data==""){
       		art.dialog.alert("上传成功！",function(){
       			window.location.reload(true);
       		});
				
       	}else{
       		art.dialog.alert(data.replace(/;/g,"<br>"),function(){
       			window.location.reload(true);
       		});
       		
       	}
       	
       },
       error: function(data, status, e){ 
       	art.dialog.alert(e);
       }
   });
}

function fileChange(){
	var filepath=$("input[name='file']").val();
	    
	}

/**
 * 一键发送
 */
function sendmethod(){
	  var dialog = art.dialog({
		      content: '发送中。。。',
		      cancel:false,
		      lock:true
	 	});
     var url =  ctx+"/borrow/payback/payBackSplitapply/oneKeysendDeductor"; // 设置url
	 $("#deductId").val("key"); //一键发送
     $("#onekeySned").attr("action",url);
	 $("#onekeySned").submit();
	// $("#onekeySned").attr("action",ctx+"/borrow/payback/payBackSplitapply/paybackSplitListApply");
}

/**
 * 线上划扣
 * @param id
 */
function onlinesendmethod(){
	
	   var realTimePlat=""; // 划扣平台
	   var id=""; // 选中id
	   var rule; // 规则
	   var deductMethod=$("input[name='back']:radio:checked").val(); //实时批量
	   // 取得划扣平台
	   realTimePlat=$("#realTimePlatSelect").val();
	   if(!realTimePlat){
		   art.dialog.alert("请选择划扣平台！！");
		   return;
	   }
	   if(!deductMethod){
		   art.dialog.alert("请选择是否批量！");
		   return;
	   }
	   rule = realTimePlat +':'+ deductMethod;
	   $("input:checkbox[name='checkBox']:checked").each(function(){
		   id=id+","+$(this).next().val();
	   });
	   $("#deductType").val(realTimePlat);
	   $("#rule").val(rule); // 设置规则
	   $("#ids").val(id); // 设置规则
	   var dialog = art.dialog({
		      content: '发送中。。。',
		      cancel:false,
		      lock:true
	 	});
	   var  url = ctx+"/borrow/payback/payBackSplitapply/lineDeductorKeysend";
	   $("#CenterapplyPayForm").attr("action",url);
	   $("#CenterapplyPayForm").submit();
	   $("#CenterapplyPayForm").attr("action",ctx+"/borrow/payback/payBackSplitapply/paybackSplitListApply");
}

  function splitdeductBack(){
	   var flag= false
	   $(":input[name='checkBox']:checked").each(function(){
		   var d =  $(this).attr("splitBackResult");
		   if(d==3){
			   flag = true;
			   return false;
		   }
		})
		if(flag){
			art.dialog.alert("划扣中的数据不能退回，请重新选择!");
			return false;
		}
	   var applyId = "";
	   var textarea = $("#textarea").val();
	   textarea = $.trim(textarea);
	   if(!textarea){
		   art.dialog.alert("请输入退回内容！");
		   return false;
	   }
	   if(textarea.length>1500){
		   art.dialog.alert("超过1500字符，从新输入！");
		   return false;
	   }
	   $("input:checkbox[name='checkBox']:checked").each(function(){
		   applyId=applyId+","+$(this).next().val();
	   });
	   var dialog = art.dialog({
		      content: '正在退回。。。',
		      cancel:false,
		      lock:true
	 	});
	   $("#ids").val(applyId);
	   $("#backmsg").val(textarea);
	   $("#CenterapplyPayForm").attr("action",ctx+"/borrow/payback/payBackSplitapply/backResult");
	   $("#CenterapplyPayForm").submit();
	   $("#CenterapplyPayForm").attr("action",ctx+"/borrow/payback/payBackSplitapply/paybackSplitListApply"); 
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
  /*function trim(str){ //删除左右两端的空格
　     return str.replace(/(^s*)|(s*$)/g, "");
　 }
*/
  
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
		url :ctx+"/borrow/payback/payBackSplitapply/startOrstopRolls",
		datatype : "json",
		success : function(data){
			if(data=='success'){
				if(flag=='1'){
					art.dialog.alert("启动成功！");
					$("#startRoll").attr("disabled",true);
					$("#stopRoll").attr("disabled",false);
				}else{
					art.dialog.alert("停止成功！");
					$("#startRoll").attr("disabled",false);
					$("#stopRoll").attr("disabled",true);
				}
			}						 
		},
		error: function(){
			art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
		}
	});
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

$(function(){
		$.ajax({  
		    type : "POST",
			url :ctx+"/borrow/payback/payBackSplitapply/getSystemSetting",
			datatype : "json",
			success : function(data){
				if(data == 'success'){
					$("#startRoll").attr("disabled",true);
					$("#stopRoll").attr("disabled",false);
				}else{
					$("#startRoll").attr("disabled",false);
					$("#stopRoll").attr("disabled",true);
				}
			 },
			error: function(){
				art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
			}
		});
	})
	
$(function(){
	
	//导出Excel数据表
	$("#exportExcel").click(function() {
		var idVal = "";
		  $("input:checkbox[name='checkBox']:checked").each(function(){
			  idVal=idVal+","+$(this).next().val();
		   });
		   $("#ids").val(idVal);
		   $("#CenterapplyPayForm").attr("action",ctx+"/borrow/payback/payBackSplitapply/exportExcel");
		   $("#CenterapplyPayForm").submit();
		   $("#CenterapplyPayForm").attr("action",ctx+"/borrow/payback/payBackSplitapply/paybackSplitListApply");
	});
	
})