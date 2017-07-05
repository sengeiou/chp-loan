$(document).ready(function(){
	// 点击页面跳转事件
	$(":input[name='jumpTo']").each(function(){
		$(this).click(function(){
			var listFlag = $("#listFlag").val();
			var applyId=$(this).val();
			var url=ctx +"/borrow/grant/grantAudit/grantAuditJump?checkVal="+applyId+"&listFlag="+listFlag;
			jump(url);
		
	});
	});
	
	function jump(url){
	    art.dialog.open(url, {  
			   title: '放款审核',
			   lock:true,
			   width:700,
			   height:350
			},false);  
	}
	
	// 点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){		
	 $(':input','#grantAuditForm')
	  .not(':button,:submit, :reset,:hidden')
	  .val('')
	  .removeAttr('checked')
	  .removeAttr('selected');
	});
	
	// 点击修改放款银行
	$("#updGrantYh").click(function(){
		var idVal = "";
		if($(":input[name='checkBoxItem']:checked").length == 0){
			art.dialog.alert("请选择要修改的单子");
			return;
		}
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).val();
        		}else{
        			idVal=$(this).val();
        		}
        	});
			window.location.href = ctx+"/borrow/grant/grantAudit/updBankJump?idVal="+idVal;
		}
	});
	$('#uploadAuditForm2').validate({
		onkeyup: false,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
		$("#messageBox").text("输入有误，请先更正。");
		if (element.is(":checkbox") || element.is(":radio")
				|| element.parent().is(".input-append")) {
			error.appendTo(element.parent().parent());
		} else {
			error.insertAfter(element);
		 }
		}
	});	
	// 放款途径的选择
	$('#lendingWay').bind('change',function(){
		var lendingWayName = $("#lendingWay option:selected").text();
		if(lendingWayName=='通联'){
			$("#templateTypeTr").css('display','block');
			$(":input[name='templateType']").each(function(){
				$(this).addClass("required");
			});
		}else if(lendingWayName=='中金'){
			$(":input[name='templateType']").each(function(){
				$(this).removeClass("required");
			});
			$("#templateTypeTr").css('display','none');
		}
	});
	
	// 导出excel,
	$("#daoBtn").click(function(){
		var loanGrant = $("#grantAuditForm").serialize();
		var idVal = "";
		var listFlag = $("#listFlag").val();
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).val();
        		}else{
        			idVal=$(this).val();
        		}
        	});
		}
		window.location.href=ctx+"/borrow/grant/grantAudit/grantAuditExl?idVal="+idVal+"&"+loanGrant+"&listFlag="+listFlag;
	});
	
	// 点击取消，关闭模式对话框
	$("#closeBtn").click(function(){
		closeBtn();
	});
	
	function closeBtn(){
		art.dialog.close();
	}
	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src=ctxStatic + '/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
			if(document.getElementById("T3")){
			  document.getElementById("T3").style.display='';
		    }
			document.getElementById("T4").style.display='';
		}else{
			document.getElementById("showMore").src=ctxStatic + '/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
			if(document.getElementById("T3")){
			   document.getElementById("T3").style.display='none';
			}
			document.getElementById("T4").style.display='none';
		}
	});
	
	// 审核或者退回
	$(":input[name='sort']").click(function(){
		showDiv1();
	});
	
	// 点击确认
	$("#auditSure").click(function(){
		// 暂时获得放款时间，放款时间需要进行日期判定
		var ParamEx=$("#param").serialize();
		var listFlag = $("#listFlag").val();
		var checkEle=$(":input[name='sort']:checked").val();
		var result=null;
		// 点击退回
		if(checkEle=="退回"){
			result=$("#backResult").attr("selected","selected").val();
			if(result==""){
				art.dialog.alert("请选择退回原因");
				return;
			}else{
				var messageBuffer=$("#messageBuffer").val();	
				var isExistSplit=$("#isExistSplit").val();		
				if(isExistSplit=='1'){
					art.dialog.alert(messageBuffer);
					return;
				}
				// 退回原因确定
				back(ParamEx,result,listFlag);
			}
		}else{
			// 审核通过
			result=$("#grantDate").val();
			if(result==""){
				art.dialog.alert("请选择放款日期！");
				return;
			}else{
				var myDate = new Date();
				var type=false;
				$("input[name='submitDeductTime']").each(function(){
					var grantDateStr = $(this).val();
					if(grantDateStr != null && grantDateStr != ""){
						var grantDateArray = grantDateStr.split(" ");
						var grantDay = new Date(grantDateArray[0]);
							grantDay.setHours(grantDateArray[1].split(":")[0]);
							grantDay.setMinutes(grantDateArray[1].split(":")[1]);
							grantDay.setSeconds(grantDateArray[1].split(":")[2]);
						var submitDeduct = grantDay.getTime();
						if(submitDeduct > 0){
							if(submitDeduct > (myDate.getTime()-3*24*60*60*1000)){
								type=true;
							}
						}
					}
				});
				if(type){
					art.dialog.confirm('存在未到3天的数据，是否确认审核?', 
		            	    function(){
						    grantOver(ParamEx,result,listFlag);
					    },
		            	    function () {
		            	    art.dialog.tips('执行取消操作');
		            	    return;
		        	    });	
				}else{
					grantOver(ParamEx,result,listFlag);
				}
			}
			
		}
	});
	
	//退回处理
	function back(ParamEx,result,listFlag){
		ParamEx+="&result="+result+"&listFlag="+listFlag;
		$.ajax({
			type : 'post',
			url : ctx+'/borrow/grant/grantAudit/grantAuditBack',
			data :ParamEx,
			cache : false,
			dataType : 'text',
			success : function(result) {
				art.dialog.alert(result);
				art.dialog.open.origin.location.href = ctx+"/borrow/grant/grantAudit/grantAuditItem/?listFlag="+listFlag;
			},
			error : function() {
				art.dialog.alert('请求失败');
			}
		});
	}
	
	// 审核通过,更新放款日期
	function grantOver(ParamEx,result,listFlag){
		ParamEx+="&result="+result+"&listFlag="+listFlag;
		$.ajax({
			type : 'post',
			url : ctx+'/borrow/grant/grantAudit/grantAuditOver',
			data : ParamEx,
			dataType : 'text',
			success : function(result) {
				art.dialog.alert(result);
				art.dialog.open.origin.location.href=ctx+"/borrow/grant/grantAudit/grantAuditItem?listFlag="+listFlag;
			},
			error : function() {
				art.dialog.alert('请求失败！');
			}
		});
	}
	
	// 点击全选
	$("#checkAll").click(function(){
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
		}
	});
	
	// 点击全选
	$("#checkAll").click(function(){
		var curMoney = 0.0;
		var curNum = 0;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
			  $(this).attr("checked",'true');
			  curMoney = parseFloat($(this).attr("lendingMoney"))+curMoney;
			  curNum=curNum+1;
			});
			$('#totalItem').text(curNum);
			$('#numHid').val(curNum);
			$('#grantMoneyText').text(fmoney(curMoney, 2));
			$('#grantMoneyHid').val(curMoney);
		}else{
			$(":input[name='checkBoxItem']").each(function() {
			  $(this).removeAttr("checked");
			});
			$('#grantMoneyHid').val(0.00);
			$('#numHid').val(0);
			$('#totalItem').text($("#hiddenNum").val());
			$('#grantMoneyText').text(fmoney($("#hiddenTotalGrant").val(), 2));
		}
		
   });
	//单独点击复选框，统计放款笔数和放款金额
	$(":input[name='checkBoxItem']").click(function() {
		// 记录总金额，当length为0时，进行总金额的处理
		var totalGrantMoney = $("#hiddenTotalGrant").val();
		var totalNum = $("#hiddenNum").val();
		// 获得单个单子的金额
		var deductAmount = parseFloat($(this).attr("lendingMoney"));
		var num = 1;
		if ($(this).attr('checked') == 'checked' || $(this).attr('checked')) {
			var hiddenNum = parseFloat($("#numHid").val()) + num;
			var hiddenDeduct = parseFloat($("#grantMoneyHid").val()) + deductAmount;
			$('#totalItem').text(hiddenNum);
			$("#numHid").val(hiddenNum);
			$("#grantMoneyHid").val(hiddenDeduct);
			$('#grantMoneyText').text(fmoney(hiddenDeduct, 2));
		} else {
			$('#checkAll').removeAttr("checked");
			if ($(":input[name='checkBoxItem']:checked").length == 0) {
				$('#totalItem').text(totalNum);
				$("#grantMoneyText").text(fmoney(totalGrantMoney, 2));
				$('#grantMoneyHid').val(0.00);
				$('#numHid').val(0);
			} else {
				var hiddenDeduct = parseFloat($("#grantMoneyHid").val()) - deductAmount;
				$('#totalItem').text(parseFloat($("#numHid").val()) - num);
				$("#numHid").val(parseFloat($("#numHid").val()) - num);
				$("#grantMoneyText").text(fmoney(hiddenDeduct, 2));
				$("#grantMoneyHid").val(hiddenDeduct);
			}
		}
	});
	
	// 批量处理,根据id查找办理页面
	$("#auditBtn").click(function(){
		var listFlag = $("#listFlag").val();
		var checkVal=null;
		if($(":input[name='checkBoxItem']:checked").length==0){
			   art.dialog.alert("请选择要进行操作的数据!");
	           return;					
			}else{
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
         		if(checkVal!=null)
         		{
         			checkVal+=","+$(this).val();
         		}else{
         			checkVal=$(this).val();
         		}
         	});
				var url=ctx +"/borrow/grant/grantAudit/grantAuditJump?checkVal="+checkVal+"&listFlag="+listFlag;
				jump(url);
			}
	})
	
	// 日期验证触发事件,点击搜索进行验证
	$("#searchBtn").bind("click",function(){//金额输入框失去焦点时，进行格式的验证
		var startDate = $("#lendingTimeStart").val();
		var endDate = $("#lendingTimeEnd").val();
		if(endDate!=null && startDate!=null){
			var arrStart = startDate.split("-");
			var arrEnd = endDate.split("-");
			var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
			var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
			if(sd.getTime()>ed.getTime()){   
				art.dialog.alert("开始日期不能大于结束日期");
				$("#lendingTimeStart").val("");
				$("#lendingTimeEnd").val("");
				return false;     
			}  
		}
	});
	
	//切换
	 function showDiv1(){
			var elements =  document.getElementsByName("sort");
			for(var i = 0 ; i < elements.length ;i++){
				var clickEle= elements[i].value;
				if(elements[i].checked){
					if("通过"==clickEle){
					document.getElementById("sort_div").style.display = "inline";
					document.getElementById("back_div").style.display = "none";
				    }
				   else{
					 document.getElementById("back_div").style.display = "inline";
					 document.getElementById("sort_div").style.display = "none";
				   } 
				}
			}
	 }
});

/**
 * 放款历史
 */
function showGrantHis(contractCode){
	 var url = ctx+"/borrow/grant/grantAudit/getGrantHis?contractCode="+contractCode;
	 art.dialog.open(url, {  
			 title: '放款划扣历史',
			 lock:true,
			 width:700,
			 height:350
	},false);  
	
}
//格式化，保留两个小数点
function fmoney(s, n) {  
    n = n > 0 && n <= 20 ? n : 2;  
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
    t = "";  
    for (i = 0; i < l.length; i++) {  
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
    }  
    return t.split("").reverse().join("") + "." + r;  
} 