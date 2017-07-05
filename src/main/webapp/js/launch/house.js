var house={
		inputProp:new Array(".id",".houseAddress",".houseCreateDay",".houseBuyday",".houseBuilingArea",".housePropertyRelation",".housePledgeMark"
		  ),
        selectProp:new Array(".houseProvince",".houseCity",".houseArea",".houseBuyway"
        		,".dictHouseCommon",".dictHouseType"),
        radioProp:new Array(".housePledgeFlag"),
  prefix:"customerLoanHouseList"};

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
house.delItem=function(targetViewId,parentViewId,DbId,ItemType){
  art.dialog.confirm("是否确认删除",
	function(){
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
		top.$.jBox.tip('删除成功');
	}
	  });
};
