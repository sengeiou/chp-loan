var house={
	inputProp:new Array(".id",".houseAddress",".houseCreateDay",".houseBuyday",".houseBuilingArea",".housePropertyRelation",
				".housePledgeMark",".houseLoanAmount",".houseLessAmount",".houseMonthRepayAmount",".houseLoanYear",".houseAmount",
				".dictHouseTypeRemark"),
    selectProp:new Array(".houseProvince",".houseCity",".houseArea",".houseBuyway",".dictHouseCommon",".dictHouseType"),
    radioProp:new Array(".housePledgeFlag"),
    prefix:"customerLoanHouseList"
};

house.format = function(){
	var j = 0;
	var i = 0;
	var suffix = null;
	var props = house.inputProp;
	for(m in props){
		i = 0;
		suffix = props[m];
		$("input[name$='"+suffix+"']").each(function(){
			if($(this).attr('name')!=='loanCustomer.id'){
				$(this).attr('name',house.prefix+'['+i+']'+suffix);
				i++;
			}
		});
	}
    props = house.radioProp;
	for(m in props){
		i = 0;
		suffix = props[m];
		$("input[name$='"+suffix+"']").each(function(){
			j++;
			$(this).attr('name',house.prefix+'['+i+']'+suffix);
			if(j%2==0){
				i++;
			}
		});
	}
	props = house.selectProp;
	for(m in props){
		suffix = props[m];
		i=0;
		$("select[name$='"+suffix+"']").each(function(){
			$(this).attr('name',house.prefix+'['+i+']'+suffix);
			i++;
		});
	 }
};

house.delItem=function(targetViewId,parentViewId,DbId,ItemType,houseCount,currIndex){
	art.dialog.confirm("是否确认删除", function(){
		if(DbId!="-1"){
			$.ajax({
				type:'post',
				url:ctx+"/apply/dataEntry/delAdditionItem",
				data:{
					'delType':ItemType,
					'tagId':DbId
				},
				dataType:'json',
				success:function(data){
					if(data){
						$('#'+parentViewId).find("table[id='"+targetViewId+"']").remove();
						var len = parseInt($('#'+houseCount).val())-1;
						$('#'+houseCount).val(len);
						$('#'+currIndex).val(len);
						top.$.jBox.tip('删除成功');
					}else{
						top.$.jBox.tip('删除失败');
					}
				},
				error:function(){
					art.dialog.alert("服务器异常！");
				}
			});
		}else{
			$('#'+parentViewId).find("table[id='"+targetViewId+"']").remove();
			var len = parseInt($('#'+houseCount).val())-1;
			$('#'+houseCount).val(len);
			$('#'+currIndex).val(len);
			top.$.jBox.tip('删除成功');
		}
	});
};

/**
 * 初始化房产信息
 * By 任志远  2016年09月27日
 */
house.init = function(){
	
	//删除房产信息
	$("input[name='delHouseItem']").each(function(){
		$(this).unbind('click');
		$(this).bind('click',function(){
			house.delItem($(this).attr("currId"),"loanHouseArea",$(this).attr("dbId"),'HOUSE','houseCount','currIndex');
		});
	});
	
	//房产类型
	$("select[name$='dictHouseType']").each(function(){
		house.initDictHouseType($(this));
		$(this).unbind('change');
		$(this).bind("change", function(){
			house.initDictHouseType($(this));
		});
	});
	//购买方式
	$("select[name$='houseBuyway']").each(function(){
		house.initHouseBuyway($(this));
		$(this).unbind('change');
		$(this).bind("change", function(){
			house.initHouseBuyway($(this));
		});
	});
	//房产地址
	loan.batchInitCity('houseProvince', 'houseCity','houseArea');
	areaselect.batchInitCity("houseProvince","houseCity","houseArea");
}

house.initDictHouseType = function(ele){
	var value = ele.val();
	var remark = ele.siblings("input[name$='dictHouseTypeRemark']");
	if(value == 5){
		remark.show();
		remark.addClass("required");
	}else{
		remark.hide();
		remark.val("");
		remark.removeClass("required");
	}
}

house.initHouseBuyway = function(ele){
	var value = ele.val();
	if(value == 1){
		ele.parent().parent().parent().find("input[name$='houseLoanAmount']").addClass("required");
		ele.parent().parent().parent().find("input[name$='houseLessAmount']").addClass("required");
		ele.parent().parent().parent().find("input[name$='houseMonthRepayAmount']").addClass("required");
		ele.parent().parent().parent().find("input[name$='houseLoanYear']").addClass("required");
	}else{
		ele.parent().parent().parent().find("input[name$='houseLoanAmount']").removeClass("required");
		ele.parent().parent().parent().find("input[name$='houseLessAmount']").removeClass("required");
		ele.parent().parent().parent().find("input[name$='houseMonthRepayAmount']").removeClass("required");
		ele.parent().parent().parent().find("input[name$='houseLoanYear']").removeClass("required");
	}
}