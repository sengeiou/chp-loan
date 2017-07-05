$(function(){
		// 为全选和反选绑定事件
		$("#checkAll").click(function() {
			$("input:checkbox").attr("checked", this.checked);
		});
		
		//将预约划扣的时间置为有效
		$("#toUse").click(function(){
			var strIds=new Array();
			   if($("input:checkbox[name='checkBox']:checked").length==0){
				   // 如果没有选中的数据，默认将所有列都置为无效
				   var conFlag=confirm("如果没有选中要操作的数据，默认修改所有列，是否继续？");
				   if(conFlag){
					   $("input:checkbox[name='checkBox']").each(function(i,d){ 
						   if (d.checked) { 
								strIds.push(d.value); 
							} 
					   	});
				   }else{
					   return;
				   }
				   
			   }else{
				   $("input:checkbox[name='checkBox']").each(function(i,d){ 
					   if (d.checked) { 
							strIds.push(d.value); 
						} 
				   	});
			   }
			   $("#id").val(strIds);
			   //document.forms[0].action=ctx+"/borrow/payback/deductsDue/toUse";
			   //document.forms[0].submit();
			   $.ajax({  
				   type : "POST",
				   data : $('#centerapplyPayForm').serialize(),
					url : ctx+"/borrow/payback/deductsDue/toUse",
					datatype : "json",
					success : function(data){
						window.location.href=ctx+"/borrow/payback/deductsDue/centerDeductsDue";
					},
					error: function(){
							//alert("服务器没有返回数据，可能服务器忙，请重试");
						}
				});
		});
		
		//将预约划扣的时间置为无效
		$("#toUnUse").click(function(){
			var strIds=new Array();
			   if($("input:checkbox[name='checkBox']:checked").length==0){
				   // 如果没有选中的数据，默认将所有列都置为无效
				   var conFlag=confirm("如果没有选中要操作的数据，默认修改所有列，是否继续？");
				   if(conFlag){
					   $("input:checkbox[name='checkBox']").each(function(i,d){ 
						   if (d.checked) { 
								strIds.push(d.value); 
							} 
					   	});
				   }else{
					   return;
				   }
				   
			   }else{
				   $("input:checkbox[name='checkBox']").each(function(i,d){ 
					   if (d.checked) { 
							strIds.push(d.value); 
						} 
				   	});
			   }
			   $("#id").val(strIds);
			   //document.forms[0].action=ctx+"/borrow/payback/deductsDue/toUnUse";
			   //document.forms[0].submit();
			   $.ajax({  
				   type : "POST",
				   data : $('#centerapplyPayForm').serialize(),
					url : ctx+"/borrow/payback/deductsDue/toUnUse",
					datatype : "json",
					success : function(data){
						window.location.href=ctx+"/borrow/payback/deductsDue/centerDeductsDue";
					},
					error: function(){
							//alert("服务器没有返回数据，可能服务器忙，请重试");
						}
				});
		});
		
		// 为新增预约按钮绑定事件
		/*$("#addDue").click(function(){
			$('#addDueDiv').modal('toggle').css({
				width : '90%',
				"margin-left" : (($(document).width() -  $("#addDueDiv").css("width").replace("px", "")) ),
				"margin-top" : (($(document).height() -  $("#addDueDiv").css("height").replace("px", "")))
			});
		});*/
		
		
		//清除按钮绑定事件
		$("#reset").click(function(){
			// 清除text	
			$(":input").val('');
			$("#centerapplyPayForm").attr("action", ctx + "/borrow/payback/deductsDue/centerDeductsDue");
			$("#centerapplyPayForm").submit();
		});
		
				
});

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
	$("#centerapplyPayForm").attr("action",  ctx + "/borrow/payback/deductsDue/centerDeductsDue");
	$("#centerapplyPayForm").submit();
	return false;
}

function addRow(){
	var length=$('#backTB').find('tr').length-2;
	if(length>22){
		alert('新增预约时最多可以输入7条预约时间');
		return false;
	}
	var idName = (length+1);
	str='<tr id="add'+idName+'">'+
	'<td><label class="lab">预约时间:</label>  <label> <input type="text" name="appointmentTime" node="2"  maxlength="40" '+
	'  onchange="selectIsAppointment(this.value,'+idName+')" onfocus="WdatePicker({skin:\'whyGreen\',dateFmt:\'HH:mm\',disabledDates:[\'..:[1,2,4,5].\',\'..:.[5]\']})" class="Wdate" readonly="readonly"/>'+
	'<img alt="" src="../../../../static/images/u205.png" onclick="delRow('+idName+');">'+
	'<font color="red"><span id="span'+idName+'"></span></font></td>'+
	'</tr>';
	$("#backTB").append(str);
}

function delRow(idName){
	var length=$('#add').find('tr').length-1;
	if(length=='0'){
		alert('最少要保留一行数据,不能删除','提示');
		return false;
	}
	  $("tr[id=add"+idName+"]").remove();
}
var isSbmit = "0";
function selectIsAppointment(value,serialNumber){
	var bankIdA = $("#bankIdA").val();
	var appointmentDate = $("#appointmentDate").val()
	if(bankIdA){
		if(appointmentDate){
			$.ajax({
		        type: "post",
		        url: ctx + "/borrow/payback/deductsDue/selectIsAppointment",
		        data: {"dueDateStr":$("#appointmentDate").val(),"dueTimeStr":value,"dueBank":$("#bankIdA").val()},
		        success: function(data) {
		        	if(data == 'existence'){
		        		isSbmit = "1";
		            	$("#span"+serialNumber).html("已预约");
		        	}else{
		        		isSbmit = "0"
		            	$("#span"+serialNumber).html("");
		        	}
		        },
		        error: function(data) {
		            alert("数据异步发送失败。");
		        }
			});
		}
	}
}
function deductsDueSubmit(){
	if(!$("input[name='modeWay']:checked").val()){
		alert("方式不能为空！");
		return false;
	}
	if(!$("#appointmentDate").val()){
		alert("选择预约日期！");
		return false;
	}
	if(isSbmit == "1"){
		alert("预约时间已存在,请重新选择。");
		return false;
	}
	var appointmentTime=new Array();
	
	$("input[name='appointmentTime']").each(function(){
		appointmentTime.push(this.value); 
    });
	var appointmentstr='';
	$("input[name='appointmentTime']").each(function(){
		appointmentstr+=this.value+','; 
    });
	appointmentstr = appointmentstr.substring(0,appointmentstr.length-1);
	//对appointmentTime进行校验
	if(!checkAppointmentTime(appointmentTime)){
		alert("预约时间必须是整点或者半点。");
		return false;
	}
	// alert($("input[name='modeWay']").val());
	$.ajax({
        type: "post",
        dataType: "json",
        url:ctx + "/borrow/payback/deductsDue/addGoldAccountDue",
        data: {"dueDateStr":$("#appointmentDate").val(),"dueTimeStr":appointmentstr,"modeWay":$("input[name='modeWay']:checked").val()},
        success: function(data) {
                	if(data.flag){
                		alert(data.msg);
                		$("#centerapplyPayForm").submit();
                	}else{
                		alert(data.msg);
                	}
        },
        error: function(data) {
            alert("数据异步发送失败。");
        }
	});
}

//检测appointmentTime
function checkAppointmentTime(value){
	var str=(value+"").split(",");
	for(var index=0;index<str.length;index++){
		var minute=str[index].substring(str[index].indexOf(":")+1);
		if(minute!="30" && minute!= "00"){
			return false;
		}
	}
	return true;
}
function emp(){
	$("#bankName").val("");
	$("#bankIdA").val("");
	$("#appointmentDate").val("");

	var length=$('#backTB').find('tr').length-2;
	// alert(length);
	for(var i=length;i>0;i--){
		 $("#backTB tr:gt("+i+")").remove();
	}

	//$("tr[id=addFirst]").remove();
	/*var div = "<tr><td><label class=\"lab\">预约银行：</label>"
			+ "<sys:multipleBank bankClick=\"bankBtn\" bankName=\"bankName\" bankId=\"bankIdA\">"
			+ "</sys:multipleBank><input id=\"bankName\" type=\"text\" class=\"input_text178\" name=\"bankName\" readonly>"
			+ "&nbsp;<i id=\"bankBtn\" class=\"icon-search\" style=\"cursor: pointer\"></i>"
			+ "<input type=\"hidden\" id=\"bankIdA\" name=\"bankIdA\" ></td></tr>"
			+ "<tr><td><label class=\"lab\">选择预约日期:</label><label>"
			+ "<input id=\"appointmentDate\" type=\"text\" readonly=\"readonly\" maxlength=\"80\" class=\"Wdate\" onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});\" onfocus=\"WdatePicker({minDate:'%y-%M-{%d}'})\" />"
			+ "</label></td></tr><tr><td><label class=\"lab\">预约时间:</label>"
			+ "<label><input name=\"appointmentTime\" id=\"appointmentDate\"  type=\"text\"  maxlength=\"40\" onChange=\"selectIsAppointment(this.value,'0')\" onfocus=\"WdatePicker({skin:'whyGreen',dateFmt:'HH:mm',disabledDates:['..:[1,2,4,5].','..:.[5]']})\" class=\"Wdate\" readonly=\"readonly\" />"
			+ "</label><img alt=\"\" src=\""+xt+"/static/images/u207.png\" onclick=\"addRow();\">"
			+ "<font color=\"red\"><span id=\"span0\"></span></font></td></tr>";*/
	var div = "<tr id=\"addFirst\"><td><label class=\"lab\">预约时间 : </label>  "
		+ "<label><input name=\"appointmentTime\" id=\"appointmentDate\"  type=\"text\"  maxlength=\"40\" onChange=\"selectIsAppointment(this.value,'0')\" onfocus=\"WdatePicker({skin:'whyGreen',dateFmt:'HH:mm',disabledDates:['..:[1,2,4,5].','..:.[5]']})\" class=\"Wdate\" readonly=\"readonly\" />"
		+ "</label><img alt=\"\" src=\""+con+"/static/images/u207.png\" onclick=\"addRow();\">"
		+ "<font color=\"red\"><span id=\"span0\"></span></font></td></tr>";
	//$("#backTB").empty();
	$("#backTB").append(div);
}