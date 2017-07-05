var infos = {};
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
$(document).ready(function(){
	$('#totalNum').text('0');
	$("#tmoney").text('0.0');
	//全选和反选 导出总条数
	$("#checkAll").click(function() {
		var $checkBox = $(":input[name='checkBoxItem']");
		var totalNum = 0;
		var tmoney=0.0;
		$checkBox.prop("checked",true);
		if ($(this).is(":checked")) {
			$checkBox.each(function(){
				tmoney +=parseFloat($(this).attr('tmoney'),10);
				$(this).prop("checked",true);
				totalNum += 1;
				
			});
			$('#totalNum').text(totalNum);
			$("#tmoney").text(fmoney(tmoney,2));
		}else{
			$checkBox.prop("checked",false);
			totalNum = 0;
			tmoney='0.0';
			$('#totalNum').text(totalNum);
			$("#tmoney").text(tmoney);
		}
	});
	
	
		
	
	//为每一条记录的复选框绑定事件
	$("[name='checkBoxItem']").click(function() {
		var tmoney = 0.0;
		var totalNum = 0;
		$("[name='checkBoxItem']").each(function() {
			if (this.checked) {
				totalNum = totalNum + 1;
				tmoney +=parseFloat($(this).attr('tmoney'),10);
			}
			$("#totalNum").text(totalNum);
			$("#tmoney").text(fmoney(tmoney,2));
		});
		var checkBox = $("input:checkbox[name='checkBoxItem']").length;
		var checkBoxchecked = $("input:checkbox[name='checkBoxItem']:checked").length;
		/*if(checkBoxchecked==0){
			$("#checkAll").attr("checked", false);
		}*/
		if(checkBox==checkBoxchecked){
			$("#checkAll").attr("checked", true);
		}else{
			$("#checkAll").attr("checked", false);
		}
	});
	
	//搜索框体显示隐藏
	$('#showMore').click(function() {
		if($("#T1").css('display')=='none'){
			$("#showMore").attr('src','../../../../static/images/down.png');
			$("#T1").show();
		}else{
			$("#showMore").attr("src",'../../../../static/images/up.png');
			$("#T1").hide();
		}
	});
	
	//搜索
	$('#searchBtn').bind('click',function(){
		$('#backForm')[0].submit();
	});  
	//清除搜索
	$('#clearBtn').bind('click',function(){
		$(':input', '#backForm').not(':button, :submit, :reset')
		.val('').removeAttr('checked').removeAttr('selected');
		$('#backForm')[0].submit();
	}); 
	// 导出POS后台数据列表
	$("#daoBtnList").click(function(){
		var idVal = "";
		 $("input:checkbox[name='checkBoxItem']:checked").each(function(){
			 idVal+=","+$(this).val();
			});
		
		 
		$("#ids").val(idVal);
		$("#backForm").attr("action",ctx+"/borrow/poscard/posBacktageList/exportPosBackList");
		$("#backForm").submit();
		$("#backForm").attr("action",ctx+"/borrow/poscard/posBacktageList/posBacktageInfo");
	});
   });

    //打开修改窗口
	function doOpenss(id, matchingState,auditDate, contractCode) {
		var url = ctx + '/borrow/poscard/posBacktageList/findPosConsult?id='
				+ id + "&matchingState=" + matchingState + "&contractCode="
				+ contractCode + "&auditDate="+ auditDate;
		art.dialog.open(url, {
			id:"id",
			title : '修改',
			width : 800,
			lock:true,
			height : 250,
			close:function()
			{
				$("form:first").submit();
				}
			});
	}
	
	/**
	 * 更新咨询信息
	 * 
	 * @param formId
	 * 
	 */
	function updateConsult(formId) {
		$.ajax({
			url : ctx + '/borrow/poscard/posBacktageList/updatePosConsult',
			type : 'post',
			data : $('#' + formId).serialize(),
			dataType : 'text',
			success : function(data) {
				if (data) {
	 				// 如果父页面重载或者关闭其子对话框全部会关闭
					var win = art.dialog.open.origin;//来源页面
					art.dialog.close();
					art.dialog.alert("数据修改成功！");
				} else {
					art.dialog.alert("数据更新失败！");
				}
			},
			error : function() {
				art.dialog.alert("服务器异常！");
			},
			async : false

		});
	}

	infos.datavalidate = function() {
		$("#inputForm").validate(
				{
					onkeyup : false,
					rules : {
	                   'contractCode':{
	                	   max:999999999,
	                	   number:true   
	                   }
					},
					messages : {
						'contractCode':{
							max:"合同编号输入有误",
							number : "编号只能是数字"	
						}
					},
					submitHandler : function(form) {
						updateConsult("inputForm");
					},
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
	};
