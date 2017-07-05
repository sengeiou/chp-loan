var centerApply = {};
/**
 * 集中划扣申请
 */
centerApply.centerReduct = function(){
	$("#centerApply").bind('click', function() {
		art.dialog({
		    content: document.getElementById("myModalPlat"),
		    title:'集中划扣申请',
		    fixed: true,
		    lock:true,
		    id: 'confirm',
		    okVal: '确认申请',
		    ok: function () {
		    	 centerReductApply();
		    },
		    cancel: true
		});
	});
}

function centerReductApply(){
	   var platForm=$("#platForm").val();
	   if(!platForm){
		   art.dialog.alert("请选择要申请的平台！");
		   return false;
	   }
		var daihkId="";
		 $("input:checkbox[name='checkBox']:checked").each(function(){
		    daihkId=daihkId+","+$(this).next().next().val();
		 });
		 var dialog = art.dialog({
		      content: '发送中。。。',
		      cancel:false,
		      lock:true
	 	});
		 $("#ids").val(daihkId);
		 $("#platFormId").val(platForm);
	     $("#CenterapplyPayForm").attr("action",ctx+"/borrow/payback/jzapply/centerApply");
	     $("#CenterapplyPayForm").submit();
}

//查询条件中图标的收缩和显示
function show() {
		if (document.getElementById("T1").style.display == 'none') {
			$("#showMore").attr({
				"src" : "images/down.png"
			});
			$("#T1").removeAttr("style");
			$("#T2").removeAttr("style");
			$("#T3").removeAttr("style");
		} else {
			$("#showMore").attr({
				"src" : "images/up.png"
			});
			$("#T1").attr("style", "display:none;");
			$("#T2").attr("style", "display:none;");
			$("#T3").attr("style", "display:none;");
		}
	}
//全选
var flag = true;
function selectAll(){
	   if(flag){
		  $("input:checkbox[name='checkBox']").each(function(){
			  this.checked = flag;
		  });
		}else{
		  $("input:checkbox[name='checkBox']").each(function(){
			  this.checked = flag;
		  });
		}
		var totalMoney = 0.0;
		var totalNum = 0;
		$("[name='checkBox']").each(function() {
			if (this.checked) {
				totalMoney = totalMoney + parseFloat($(this).val());
				totalNum = totalNum + 1;
			}
			$("#total_money").text(totalMoney.toFixed(2));
			$("#total_num").text(totalNum);
			
		});
		if(flag){
			flag = false;	
		}else{
			flag = true;
			$("#total_money").text($("#totalbk").val());
			$("#total_num").text($("#numbk").val());
		}
		
}
//全选和反选复选框   划扣金额和划扣笔数的显示
$(function(){
	//$(".body_top").css("display","none");
	//全选和反选
	$("#checkAll").click(function() {
		$("input:checkbox[name='checkBox']").attr("checked", this.checked);
		var totalMoney = 0;
		var totalNum = 0;
		if (this.checked) {
			$("[name='checkBox']").each(function() {
				totalNum = totalNum + 1;
				totalMoney = totalMoney + parseFloat($(this).val());
			});
			$("#total_money").text(totalMoney.toFixed(2));
			$("#total_num").text(totalNum);
		} else {
			$("#total_money").text($("#totalbk").val());
			$("#total_num").text($("#numbk").val());
		}
		
	});
	//为每一条记录的复选框绑定事件
	$("[name='checkBox']").click(function() {
		var totalMoney = 0.0;
		var totalNum = 0;
		$("[name='checkBox']").each(function() {
			if (this.checked) {
				totalMoney = totalMoney + parseFloat($(this).val());
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
	
	//为每个还款账号按钮绑定事件

		
		
		$(".btn_edit").click(function(){	
			$.ajax({  
				   type : "POST",
				   data:{
					   "contractCode":$(this).val()
				   },
					url : ctx+"/borrow/payback/jzapply/cardInfo",
					datatype : "json",
					success : function(data){
						var jsonData = eval("("+data+")");
						console.log(jsonData);
							$('#centerHKTab').bootstrapTable('destroy');
							$('#centerHKTab').bootstrapTable({
								data:jsonData,
								dataType: "json",
								pageSize: 10,
								pageNumber:1
							})
							
					},
					error: function(){
							alert("服务器没有返回数据，可能服务器忙，请重试");
						}
				});
			
			
			art.dialog({
			title: '还款账号',
			padding: 0,
			content: document.getElementById('myModal'),
			lock: true,
			button:[{
			name: '选中',
			callback: function() {
				var checkData = $('#centerHKTab').bootstrapTable('getSelections')[0];
				var checkNum=checkData.id;
				if(checkNum==""){
					alert("请选择数据!");
				}else{
				  
				 $.ajax({  
					   type : "POST",
					   data:{
						   "selectCard":checkNum
					   },
						url : ctx+"/borrow/payback/jzapply/modifyCardInfo",
						datatype : "json",
						success : function(data){
						},
						error: function(){
								alert("服务器没有返回数据，可能服务器忙，请重试");
							}
					});
				}
			}
			},{
			name: '关闭'
			}]
			},false);
	
		
	});
	
	//为选择账户信息弹框的确定按钮绑定事件
	$("#confirm").click(function(){
		var checkData = $('#centerHKTab').bootstrapTable('getSelections')[0];
		var checkNum=checkData.id;
		if(checkNum==""){
			alert("请选择数据!");
		}else{
		  
		 $.ajax({  
			   type : "POST",
			   data:{
				   "selectCard":checkNum
			   },
				url : ctx+"/borrow/payback/jzapply/modifyCardInfo",
				datatype : "json",
				success : function(data){
				},
				error: function(){
						alert("服务器没有返回数据，可能服务器忙，请重试");
					}
			});
		}
		
	});

	//清除按钮绑定事件
	$("#reset").click(function(){
		$(this)[0].reset();
	});
 
	//伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
		}
	});
	
	//导出Excel数据表
	$("button[name='exportExcel']").click(function() {
		var idVal = "";
		 $("input:checkbox[name='checkBox']:checked").each(function(){
			 idVal+=","+$(this).next().next().val();
			});
		$("#ids").val(idVal);
		$("#CenterapplyPayForm").attr("action",ctx+"/borrow/payback/jzapply/exportExcel");
		$("#CenterapplyPayForm").submit();
		$("#CenterapplyPayForm").attr("action",ctx+"/borrow/payback/jzapply/list");
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
//关闭弹窗
function wclose(){
	$('#myModalPlat').modal('hide');
}
//点击清除按钮调用的的方法
function clearBtns(){
	// 清除text	
	$(":input").val('');
	$("#CenterapplyPayForm").submit();
}


		
		

