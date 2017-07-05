var wid = screen.width/2 + 150;
var hei = screen.height/2 + 100;
window.middleId;
window.userCode;
$(document).ready(function(){
	// 点击选择账户,重新获得窗体，
	$("#selectMiddleBtn").click(function(){
		var url= ctx+"/car/grant/disCard/showSelectPage";
		jump(url);
	});
	
	// 样式控制
	function jump(url){
		art.dialog.open(url, {  
			   title: '分配卡号!',
			   lock:true,
			   width:1000,
			   height:500
			},false);
	}
	

	// 点击确认，要进行判断是否进行选择，获得选中的放款人员编号，将中间人Id，放款人员id传送到主窗体，回显中间人信息
	$("#middleSureBtn").click(function(){

		//if($(":input[name='SelectBank']:checked",window.frames['midPerson'].contentDocument).length == 0)
		var middleId="";
		var userCode="";
		
		if($(":input[name='SelectBank']:checked",window.frames['midPerson'].contentDocument).length == 0){
			art.dialog.alert("请选择卡号信息");
			return;
		}else if($(":input[name='SelectEl']:checked",window.frames['disCardPerson'].contentDocument).length==0){
			art.dialog.alert("请选择放款人员信息");
			return;
		}else{
			
			//art.dialog.alert($(":input[name='SelectEl']:checked",window.frames['disCardPerson'].contentDocument).val());
			 middleId=$(":input[name='SelectBank']:checked",window.frames['midPerson'].contentDocument).attr("middleId");
			 
			 $(":input[name='SelectEl']:checked",window.frames['disCardPerson'].contentDocument).each(function(){
         		
         		if(userCode!="")
         		{
         			userCode+=","+$(this).val();
         		}else{
         			userCode=$(this).val();
         		}
         		
         	});
		
			 callback_setCardNum(middleId,userCode);
			 closeAtr();
			 
		
		}
	});

	function callback_setCardNum(middleId,userCode){
		$.ajax({
			type : 'post',
			url : ctx+'/car/grant/disCard/disPersonSure',
			data:{
				'middleId':middleId,
				'userCode':userCode
			},
			dataType:'json',
			async:false,
			success : function(data) {
				if(data!=null){
					var win = art.dialog.open.origin;//来源页面
					$("#middleName",win.document).val(data.middleName); 
					$("#midBankName",win.document).val(data.midBankName);
					$("#bankCardNo",win.document).val(data.bankCardNo);
					$("#userCode",win.document).val(userCode);
					$("#middleId",win.document).val(middleId);
				}
			},
			error : function() {
				alert('请求异常！');
			}
		});
		
	}
	// 点击提交，将中间人id和放款人员编号更新到放款记录表中，同时更新放款状态，走流程
	 $("#disCommitBtn").click(function(){
		 if($("#middleName").val()==""||$("#middleName").val()==null){
			 art.dialog.alert("请选择卡号再进行提交");
			 return;
		 }else{
			 commit();
		 }
	 });
	
	 // 点击返回
	 $("#bankBtn").click(function(){
		 window.history.go(-1);
	 })
})

// 点击提交，获得applyId等处理的功能
function commit(){
	 var ParamEx=$("#param").serialize();
	 var userCode=$("#userCode").val();
	 var middleId=$("#middleId").val();
	 ParamEx+="&userCode="+userCode+"&middleId="+middleId;
	 $.ajax({
			type : 'post',
			url : ctx+'/car/grant/disCard/disCardCommit',
			beforeSend : function(){
				waitingDialog.show();
			},
			data :ParamEx,
			cache : false,
			async:false,
			dataType : 'json',
			success : function(result) {
				waitingDialog.hide();
				if(result){
					alert("分配卡号成功");
					windowLocationHref(ctx+"/car/carLoanWorkItems/fetchTaskItems/balanceCommissioner");
				}else{
					alert("分配卡号失败");
					windowLocationHref(ctx+"/car/carLoanWorkItems/fetchTaskItems/balanceCommissioner");
				}
			},
			error : function() {
				waitingDialog.hide();
				alert('请求异常！');
			}
		});
}

function closeAtr(){
	art.dialog.close();
}