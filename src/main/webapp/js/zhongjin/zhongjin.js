
	function getBank(inputText,hiddenText) {
		var url = "/sys/org/selectOrgList";

					art.dialog.open(url, {
						title : "选择银行",
						width : 660,
						height : 450,
						lock : true,
						opacity : .1,
						okVal : '确定',
						ok : function() {
							var iframe = this.iframe.contentWindow;
							var str = "";
							var strname = "";
							$("input[name='orgIds']:checked", iframe.document)
									.each(
											function() {
												if ($(this).attr("checked")) {
													str += $(this).attr("id")
															+ ",";
													strname += $(this).attr(
															"sname")
															+ ",";
												}
											});

							str = str.replace(/,$/g, "");
							strname = strname.replace(/,$/g, "");

							$("#" + inputText).val(strname);
							$("#" + hiddenText).val(str);
						}
					}, false);

	}
	function clear1(){
		$("#serialnum").val('');
		$("#contractCode").val('');
		$("#beginMoney").val('');
		$("#endMoney").val('');
		$("#bankName").val('');
		$("#banknum").val('');
		$("#banknum").trigger("change");
		$("#accountName").val('');
		$("#createTime").val('');
	 	$("#status").val('');
	 	$("#status").trigger("change");
	}
	function addPage(){
		//if($('#cpcnIdU').val()!=''){
			$('#cpcnIdU').val("");
			$('#accountNumU').val("");
			$('#accountNameU').val("");
			$('#dealMoneyU').val("");
			$('#contractCodeU').val("");
			$('#bankNumU').val("");
			$("#bankNumU").trigger("change");
			$('#accountTypeU').val("");
			$("#accountTypeU").trigger("change");
			$('#accounProviceU').val("");
			$("#accounProviceU").trigger("change");
			$('#accounCityU').val("");
			$("#accounCityU").trigger("change");
			$('#certTypeU').val("");
			$("#certTypeU").trigger("change");
			$('#certNumU').val("");
			$('#noteU').val("");
		//}
		
		   
		art.dialog({
		    content: document.getElementById("paycpcnDIV"),
		    title:'中金划扣',
		    fixed: true,
		    width : 600,
			height : 300,
		    lock:true,
			opacity : .1,
		    id: 'confirm',
		    okVal: '确认',
		    ok: function () {
		    	return form_submit();
		    },
		    cancel: true
		});
		
	}
	function updatePage(id){
		$.ajax({
			   type: "POST",
			   dataType: "json",
			   data : {id:id},
			   url: ctx + "/borrow/zhongjin/getInfo",	
			   async: false,
			   success: function(msg){
				   if(msg){
					   $('#cpcnIdU').val(msg.cpcnId);
					   $('#accountNumU').val(msg.accountNum);
					   $('#accountNameU').val(msg.accountName);
					   $('#dealMoneyU').val(msg.dealMoney);
					   $('#contractCodeU').val(msg.contractCode);
					   $('#bankNumU').val(msg.bankNum);
					   $("#bankNumU").trigger("change");
					   $('#accountTypeU').val(msg.accountType);
					   $("#accountTypeU").trigger("change");
					   $('#accounProviceU').val(msg.accounProviceNum);
					   $("#accounProviceU").trigger("change");
					   $('#accounCityU').val(msg.accounCityNum);
					   $("#accounCityU").trigger("change");
					   $('#certTypeU').val(msg.certType);
					   $("#certTypeU").trigger("change");
					   $('#certNumU').val(msg.certNum);
					   $('#noteU').val(msg.note);
					   art.dialog({
						    content: document.getElementById("paycpcnDIV"),
						    title:'中金划扣',
						    fixed: true,
						    width : 600,
							height : 300,
						    lock:true,
							opacity : .1,
						    id: 'confirm',
						    okVal: '确认',
						    ok: function () {
						    	return form_submit();
						    },
						    cancel: true
						});
				   }
			   }
			});
		
		
	}
	
	function form_submit(){
	/* if(!$("#addForm").valid()){
		alert('1');
	}else{
		alert('2');
	} */
		if($('#accountNumU').val()==''){
			art.dialog.alert('银行账号不能为空!');
			return false;
		}
		if($('#accountNameU').val()==''){
			art.dialog.alert('户名不能为空!');
			return false;
		}
		if($('#dealMoneyU').val()==''){
			art.dialog.alert('金额不能为空!');
			return false;
		}
		if($('#bankNumU').val()==''){
			art.dialog.alert('银行名称不能为空!');
			return false;
		}
		if($('#accountTypeU').val()==''){
			art.dialog.alert('账户类型不能为空!');
			return false;
		}
		if($('#certTypeU').val()==''){
			art.dialog.alert('证件类型不能为空!');
			return false;
		}
		var array = $('#addForm').serializeArray();
	
		var banknum = $('#bankNumU').val();
		var accounProvice = $('#accounProviceU').val();
		var accounCity =  $('#accounCityU').val();
		var accountType = $('#accountTypeU').val();
		var certtype =  $('#certTypeU').val();
		var certnum = $('#certNumU').val();
		var obj = $('#addForm').serialize();
		
		
	
		//中国银行
		if(banknum=='104'){
			if(accounProvice==''){
				art.dialog.alert('请选择省份!');
				return false;
			}
			if(accounCity=='')
			{
				art.dialog.alert('请选择城市!');
				return false;
			}
		}
		if(accountType=='11'){
			if(certtype=='')
			{
				 art.dialog.alert('证件类型必须填写!');
				 return false;
			}
			if(certnum=='')
			{
				 art.dialog.alert('证件号码必须填写!');
				 return false;
			}
		}
		else
		{
			//民生银行
			if(banknum=='305'){
				if(certtype=='')
				{
					art.dialog.alert('证件类型必须填写!');
					return false;
				}
				if(certnum=='')
				{
					art.dialog.alert('证件号码必须填写!');
					return false;
				}
			}
		} 

		
		
		
		$('#addForm').submit();
		//issubmit = true; 
		
	}
	function openImport(){
		document.getElementById("file").click();
	}
	function uploadPage(){
		var filepath=$("input[name='file']").val();
	    var extStart=filepath.lastIndexOf(".");
	    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if(ext!=".XLS"&&ext!=".XLSX"){
	    	art.dialog.alert('仅限于上传EXCEL格式文档');
	     return false;
	    }
	    $('#uploadAuditForm').submit(); 
		/* art.dialog({
		    content: document.getElementById("uploadExcelModel"),
		    title:'导入中金划扣数据',
		    fixed: true,
		    width : 400,
			height : 200,
		    lock:true,
			opacity : .1,
		    id: 'confirm',
		    okVal: '确认',
		    ok: function () {
					 
		    },
		    cancel: true
		}); */
		
	}
	function sendUpdate(status){
		var strIds=new Array();//声明一个存放id的数组
		$("input[name='checkBoxItem']:checkbox").each(function(i,d){
			if (d.checked) {
				strIds.push(d.value);
			}
		});
		var jdata = {"checkIds":strIds.toString(),"status":status};
		if(strIds.length==0){
			jdata=$('#searchForm').serialize();
			jdata=jdata+"&status="+status;
		}
		art.dialog.confirm("请确认是否发送数据？",function(r) {
			if(r){
			   $.ajax({
				cache : false,
				type : "POST",
				url : ctx + "/borrow/zhongjin/sendStatus",
				dataType : "json",
				data : jdata,
				async : false,
				success : function(msg) {
					art.dialog.alert(msg.msg); 
					$("#deductsForm").submit();
				}
			 });
		  } 
		},function(){
			
		});
	}
	function orderDeduct(){
		art.dialog({
		    content: document.getElementById("loanAccountOrderDeduct"),
		    title:'预约划扣',
		    fixed: true,
		    width : 550,
			height : 200,
		    lock:true,
			opacity : .1,
		    id: 'confirm',
		    okVal: '确认',
		    ok: function () {
		    	if(!$("#appointmentDate").val()){
		    		art.dialog.confirm("选择预约日期！");
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
		    		art.dialog.confirm("预约时间必须是整点或者半点。");
		    		return false;
		    	}
		    	var _s_ = ","+appointmentTime.join(",");
		    	for(var i=0;i<appointmentTime.length;i++){
		    		if(_s_.replace(","+appointmentTime[i],"").indexOf(","+appointmentTime[i])>-1) {
		    			art.dialog.cofirm("预约时间中有重复时间(" + appointmentTime[i]+")，请检查确认！","提示");
						return false;
					}
		    	}
		    	var _strIds=new Array();//声明一个存放id的数组
		    	$("input[name='checkBoxItem']:checkbox").each(function(i,d){
		    		if (d.checked) {
		    			_strIds.push(d.value);
		    		}
		    	});
		    	var deductType=$(":radio[name='deductType']:checked").val();
		    	$.ajax({
					type : "POST",
					url : ctx + "/borrow/zhongjin/orderDeduct",
					dataType : "json",
					data : $('#deductsForm').serialize()+"&deductType="+deductType+"&deductDate="+$("#appointmentDate").val()+"&deductTime="+appointmentTime.toString()+(_strIds.length==0 ? '' : "&checkIds="+_strIds.toString()),
					 success: function(msg){
						art.dialog.alert(msg.msg); 
						$("#deductsForm").submit();//刷新页面数据
					},
					error:function(e){
						$("#deductsForm").submit();//刷新页面数据
					}
				});
		    },
		    cancel: true
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
	function addRow(){
		var length=$('#backTB').find('tr').length-2;
		if(length=='7'){
			art.dialog.confirm('新增预约时最多可以输入7条预约时间');
			return false;
		}/* addRow() */
		var idName = (length+1);
		str='<tr id="add'+idName+'">'+
		'<td><label class="lab">预约时间:</label>  <label> <input type="text" name="appointmentTime" node="2"  maxlength="40" '+
		'  onchange="selectIsAppointment(this.value,'+idName+')" onfocus="WdatePicker({skin:\'whyGreen\',dateFmt:\'HH:mm\',disabledDates:[\'..:[1,2,4,5].\',\'..:.[5]\']})" class="Wdate" readonly="readonly"/>'+
		'<img alt="" src="/chp-loan/static/images/u205.png" onclick="delRow('+idName+');">'+
		'<font color="red"><span id="span'+idName+'"></span></font></td>'+
		'</tr>';
		$("#backTB").append(str);
	}
	function delRow(idName){
		var length=$('#add').find('tr').length-1;
		if(length=='0'){
			art.dialog.confirm('最少要保留一行数据,不能删除','提示');
			return false;
		}
		  $("tr[id=add"+idName+"]").remove();
	}
	function checkAll(t){
		if(t.checked == true){
			$("input[name='checkBoxItem']").each(function(){
				   $(this).attr("checked",true);
				  }); 
			
		  }else {
			  $("input[name='checkBoxItem']").each(function(){
				   $(this).attr("checked",false);
				  }); 
		  }
		chekbox_click();
	}
	function chekbox_click(){
		var count = 0; 
		var money = 0.0;
		$("input[name='checkBoxItem']").each(function(){
			   if($(this).is(':checked')){
				   count += 1;
				   money = money + parseFloat($('#'+$(this).val()+'money').val());
			   }
			  }); 
		if(count==0){
			$('#totalNum').empty();
			$('#totalNum').html($('#num').val());
			$('#deductAmount').empty();
			$('#deductAmount').html($('#deduct').val());
		}else{
			$('#totalNum').empty();
			$('#totalNum').html(count);
			$('#deductAmount').empty();
			$('#deductAmount').html(money);
		}
		
	}
	function showHistory(id){
		var url = ctx + '/borrow/zhongjin/getHistory?cpcnId='
			+ id;
		art.dialog.open(url, {
			id : 'his',
			title : '历史',
			lock : true,
			width : 700,
			height : 350,
		}, false); 
	} 
	
	
	function giveupStatus(status){
		var strIds=new Array();//声明一个存放id的数组
		$("input[name='checkBoxItem']:checkbox").each(function(i,d){
			if (d.checked) {
				strIds.push(d.value);
			}
		});
		var jdata = {"checkIds":strIds.toString(),"status":status};
		if(strIds.length==0){
			jdata=$('#searchForm').serialize();
			jdata=jdata+"&status="+status;
		}
		art.dialog.confirm("请确认是否放弃数据？",function(r) {
			if(r){
			   $.ajax({
				cache : false,
				type : "POST",
				url : ctx + "/borrow/zhongjin/giveupStatus",
				dataType : "json",
				data : jdata,
				async : false,
				success : function(msg) {
					art.dialog.alert(msg.msg); 
					$("#deductsForm").submit();
				}
			 });
		  } 
		},function(){
			
		});
	}