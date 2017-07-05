var contactFormat={
  inputProp:new Array(".id",".contactName",".contactSex",".contactNowAddress",".contactUnitTel",".contactMobile"),
  selectProp:new Array(".relationType",".contactRelation"),
  prefix:"customerContactList"
};
contactFormat.format=function(){
	var i = 0;
	var suffix = null;
	var props = contactFormat.inputProp;
	for(m in props){
		i = 0;
		suffix = props[m];
	     $("input[name$='"+suffix+"']").each(function(){
	    	 if($(this).attr('name')!=='loanCustomer.id'){
	    		 $(this).attr('name',contactFormat.prefix+'['+i+']'+suffix);
	    		 i++;
	    	 }
		   });
		
	}
	 props = contactFormat.selectProp;
	 for(m in props){
		suffix = props[m];
		i=0;
		$("select[name$='"+suffix+"']").each(function(){
		   $(this).attr('name',contactFormat.prefix+'['+i+']'+suffix);
			   i++;
		   });
	 }
};