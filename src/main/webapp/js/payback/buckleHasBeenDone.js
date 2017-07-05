
	function show(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
			document.getElementById("T3").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
			document.getElementById("T3").style.display='none';
		}
	}
	/**
	 * @function 页面分页
	 * @param n
	 * @param s
	 * @returns {Boolean}
	 */
	function page(n, s) {
		var menuId = $("#menuId").val();
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#auditForm").attr("action",  ctx + "/borrow/payback/centraliDeduc/buckleHasBeenDoneList?menuId"+menuId);
		$("#auditForm").submit();
		return false;
	}
	 
	 $(document).ready(function() {
		 
		// 初始化门店下拉框
		loan.getstorelsit("txtStore","hdStore");
		//全选 多选
			$("[name='checkBox']").each(
					function(){
						$(this).bind('click', function() {
							if($(this).attr('checked') == 'checked'
								|| $(this).attr('checked')){
							}else{
								$("#checkAll").removeAttr("checked");
							}
								
						});
			});
			$('#checkAll').bind(
					'click',
					function() {
						if ($('#checkAll').attr('checked') == 'checked'
								|| $('#checkAll').attr('checked')) {
							$(":input[name='checkBox']").attr("checked",'true');
						}else{
							$(":input[name='checkBox']").removeAttr("checked");
						}
					});
			// 导出Excel数据表
			$("button[name='exportExcel']").click(function() {
				var idVal = "";
				var checkList=$("input:checkbox[name='checkBox']:checked").val();
				$("input:checkbox[name='checkBox']:checked").each(function(){
					 if(checkList != null){
						 var value=$(this).val();
						 values=value.split(",");
						 idVal += values[0] + ";";// 期供ID
					 }
					});
				if(idVal.length!=0){
					idVal = idVal.substr(0,idVal.length-1);
				}
			    var urgeManage  =$('#urgeManage').val();
				$('#idVal').val(idVal);
				document.getElementById('auditForm').action=ctx+"/borrow/payback/centraliDeduc/redlineExportExcel?urgeManage="+urgeManage;
				$('#auditForm').submit();
				document.getElementById('auditForm').action =  ctx + "/borrow/payback/centraliDeduc/buckleHasBeenDoneList";
				//var obj = $('#auditForm').serialize()
				//window.location.href=ctx+"/borrow/payback/centraliDeduc/redlineExportExcel?idVal="+idVal+"&"+obj;
			});
			
			//还款日验证
			$("#paybackDay").blur(function(){
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
			
			// 清除按钮
			$("#clearBtn").click(function(){	
				var menuId = $("#menuId").val();
				// 清除text	
			   $(':input','#auditForm')
			   .not(':button, :reset')
			   .val('')
			   .removeAttr('checked')
			   .removeAttr('selected');
				$("#auditForm").attr("action",  ctx + "/borrow/payback/centraliDeduc/buckleHasBeenDoneList?menuId="+menuId);
				$("#auditForm").submit();
			});
			
			// 查看页面弹出框
			$(":input[name='seeBuckHasBeen']").each(function(){
				$(this).click(function(){
					var id=$(this).next().val();
					var url = ctx + '/borrow/payback/centraliDeduc/seeBuckleHasBeenDone?bId='+ id;
					art.dialog.open(url, {  
				         id: 'his',
				         title: '还款划扣已办查看页面',
				         lock:true,
				         width:1000,
				     	 height:400
				     },  
				     false);
				});
			});
	});
	 

function isDigit(obj){
	 var test_value= $(obj).val();
	 if(test_value){
	 var patrn=/^([1-9]\d*|0)(\.\d*[1-9])?$/;
	 if (!patrn.exec(test_value)){  
		 $(obj).val(0)
		 artDialog.alert("请输入正确的金额");
		 return;
	 }
	} 
}

$(function(){
	//全选和反选
	$("#checkAll").click(function() {
		$("input[name='checkBox']").attr("checked", this.checked);
		var totalMoney = 0;
		var totalNum = 0;
		if (this.checked) {
			$("[name='checkBox']").each(function() {
				totalNum = totalNum + 1;
				totalMoney = totalMoney + parseFloat($(this).next().val()?$(this).next().val():0);
			});
			$("#total_money").text(totalMoney.toFixed(2));
			$("#total_num").text(totalNum);
		} else {
			$("#total_money").text(0);
			$("#total_num").text(0);
		}
	});
	//为每一条记录的复选框绑定事件
	$("[name='checkBox']").click(function() {
		var totalMoney = 0.0;
		var totalNum = 0;
		$("[name='checkBox']").each(function() {
			if (this.checked) {
				totalMoney = totalMoney + parseFloat($(this).next().val());
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
    })
});
	 