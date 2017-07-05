$(function(){
	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	
	// 搜索按钮
	$("#btn-submit").click(function(){	
		$("#waitSendForm").attr("action",ctx+"/borrow/serve/emailServe/paybackRemindList");
		$("#waitSendForm").submit();
	});
	
	// 清除按钮
	$("#clearBtn").click(function(){	
		$(':input','#waitSendForm')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		//$('select').val('');
		//$("select").trigger("change");
		$("#hdUser").val('');
		$("#hdStore").val('');
		$("#waitSendForm").attr("action",ctx+"/borrow/serve/emailServe/paybackRemindList");
		$("#waitSendForm").submit();
	});
	

})
function selectAll(){
	if($('#checkAllItem').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
	{
		$("input:checkbox[name='checkItem']").each(function() {
			$(this).attr("checked",'true');
		});
	}else{
		$("input:checkbox[name='checkItem']").each(function() {
			$(this).removeAttr("checked");
		});
	}
}
/**
 * 导出
 */
function exportExcel(){
	var idVal = '';
	$("input:checkbox[name='checkItem']:checked").each(function(){
		if(idVal==''){
			idVal = $(this).val();
		}else{
			idVal+=","+$(this).val();
		}
	});
	//alert(idVal);
	$("#ids").val(idVal);
	$("#waitSendForm").attr("action",ctx+"/borrow/serve/emailServe/exportExl");
	$("#waitSendForm").submit();
}
/**
 * 发送或作废
 * @param sendEmailStatus 1：发送 2：作废
 * @param type 0：批量  1：单个
 * @param idType 1:根据期供id  2：根据邮件id
 */
function send(sendEmailStatus,type,id,idType){
	var ids = '';
	var num=0;
	if(id!=''){
		ids=id;
	}else{
		$("input:checkbox[name='checkItem']:checked").each(function(){
			if(ids==''){
				ids = $(this).val();
			}else{
				ids+=","+$(this).val();
			}
			num++;
		});
	}
	if(ids==''){
		num=$("#pageCount").val();
	}
	/*if(ids==''){
		top.$.jBox.tip('请选择数据','warning');
	}else{*/
		var msg=""
		if(sendEmailStatus==1 && type==0){
			msg="即将为"+num+"笔数据发送电子邮件，是否确认发送？";
		}else if(sendEmailStatus==2 && type==0){
			msg="即将取消"+num+"笔数据电子邮件的发送，是否确认作废？"
		}else if(sendEmailStatus==1 && type==1){
			msg="即将发送电子邮件，是否确认发送？";
		}else if(sendEmailStatus==2 && type==1){
			msg="是否确认作废？"
		}
		$("#ids").val(ids);
		$("#sendEmailStatus").val(sendEmailStatus);
		$("#idType").val(idType);
		art.dialog.confirm(msg, function (){
			top.$.jBox.tip('正在操作，请稍后......','loading');
			$.ajax({
				  url:ctx+"/borrow/serve/emailServe/updateSendEmailStatus",
				  type:"post",
				  data:$("#waitSendForm").serialize(),
				  cache : false,
				  async : true,
				  dataType:'json',
				  success:function(data){
					  top.$.jBox.closeTip();
					  if(sendEmailStatus==1 && type==0){
						  art.dialog.alert("发送成功"+(data.totalNum-data.failNum)+"笔，失败"+data.failNum+"笔");
					  }else{
						  art.dialog.alert("操作成功");
					  }
					  $("#waitSendForm").attr("action",ctx+"/borrow/serve/emailServe/paybackRemindList");
					  $("#waitSendForm").submit();
				  }
				
			})
		});
		
	//}
	
}


/**
 * 查看历史操作
 * @param applyId
 */
function doOpenhis(loanCode,fileType) {
	var url = ctx + "/borrow/serve/customerServe/historyList?loanCode=" + loanCode + "&fileType=" + fileType;
	art.dialog.open(url, {  
        id: 'his',
        title: '历史列表',
        lock:true,
        width:700,
     	height:350
    }, false);  
}

/**
 * @function 
 * @param n
 * @param s
 * @returns {Boolean}
 */
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#waitSendForm").attr("action",ctx+"/borrow/serve/emailServe/paybackRemindList");
	$("#waitSendForm").submit();
	return false;
}