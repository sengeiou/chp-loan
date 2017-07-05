var contactFormat={
  relationInputProp:new Array(".id",".relationType",".contactName",".certNum",".contactSex",".homeTel",".contactMobile"),
  relationSelectProp:new Array(".contactRelation"),
  
  workProvePersonInputProp:new Array(".id",".relationType",".contactName",".department",".contactMobile",".post",".remarks"),
  workProvePersonSelectProp:new Array(".contactRelation"),
  
  otherInputProp:new Array(".id",".relationType",".contactRelation",".contactName",".homeTel",".contactMobile",".remarks"),
  prefix:"customerContactList"
};
contactFormat.format=function(){
	var begin = 0;
	var end = 0;
	var suffix = null;
	var props = contactFormat.relationInputProp;
	for(m in props){
		var i = begin;
		suffix = props[m];
	     $("#table_contactRelationArea input[name$='"+suffix+"']").each(function(){
	    	 if($(this).attr('name')!=='loanCustomer.id' && $(this).attr('name')!=='loanMate.id'){
	    		 $(this).attr('name',contactFormat.prefix+'['+i+']'+suffix);
	    		 i++;
	    		 if(end < i){
	    			 end = i;
	    		 }
	    	 }
		 });
	}
	
	props = contactFormat.relationSelectProp;
	for(m in props){
		i = begin;
		suffix = props[m];
		$("#table_contactRelationArea select[name$='"+suffix+"']").each(function(){
		   $(this).attr('name',contactFormat.prefix+'['+i+']'+suffix);
		   i++;
		});
	 }

	 begin = end;
	 props = contactFormat.workProvePersonInputProp;
	 for(n in props){
		 	i = begin ;
			suffix = props[n];
		     $("#table_workProvePersonArea input[name$='"+suffix+"']").each(function(){
		    	 if($(this).attr('name')!=='loanCustomer.id' && $(this).attr('name')!=='loanMate.id'){
		    		 $(this).attr('name',contactFormat.prefix+'['+i+']'+suffix);
		    		 i++;
		    		 if(end < i){
		    			 end  = i;
		    		 }
		    	 }
			 });
		}
	 props = contactFormat.workProvePersonSelectProp;
	 for(n in props){
		 i = begin ;
		suffix = props[n];
		$("#table_workProvePersonArea select[name$='"+suffix+"']").each(function(){
		   $(this).attr('name',contactFormat.prefix+'['+i+']'+suffix);
		   i++;
		});
	 }
	 
	 begin = end;
	 props = contactFormat.otherInputProp;
	 for(k in props){
		 	i = begin;
			suffix = props[k];
		     $("#table_otherArea input[name$='"+suffix+"']").each(function(){
		    	 if($(this).attr('name')!=='loanCustomer.id' && $(this).attr('name')!=='loanMate.id'){
		    		 $(this).attr('name',contactFormat.prefix+'['+i+']'+suffix);
		    		 i++;
		    	 }
			 });
		}
};