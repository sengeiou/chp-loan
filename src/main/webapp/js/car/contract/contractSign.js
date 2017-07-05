var contractSign={}
    
    contractSign.openFileDialog = function(sourceId,zoomId,validBtnId,relationId,photoType,customerType,loanCode,contractCode){
       var data = "relationId="+relationId+"&photoType="+photoType+"&customerType="+customerType+"&loanCode="+loanCode+"&contractCode="+contractCode;
       art.dialog.open(ctx+"/carpaperless/confirminfo/openPhotoUpload?"+data,{
    	    title: '照片上传',
    	    width:400, 
			height:80, 
			lock:true,
			opacity: .1,
            okVal:'确定',
    	    ok: function(){
    	    	var iframe = this.iframe.contentWindow;
    	   		//$('#fileForm',iframe.document).submit();
    	   	 var formData = new FormData($('#fileForm',iframe.document)[0]);
    	     var win = art.dialog.open.origin;//来源页面
			 contents_getJsonForFileUpload(
					 this,
					 ctx+'/carpaperless/confirminfo/savePhoto',
					 formData,
					 function(result){
					     $('#'+sourceId,win.document).attr("src",ctx+'/carpaperless/confirminfo/getPreparePhoto?docId='+result);
						 $('#'+zoomId,win.document).attr('href',ctx+'/carpaperless/confirminfo/getPreparePhoto?docId='+result);
						 if(validBtnId!=''){
							 $('#'+validBtnId).attr('docId',result);
							 $('#'+validBtnId).attr('idNumOCR','');
							 $('#'+validBtnId).attr('customerNameOCR','');
						 }
					  },null,false);	
			 return false;
    	    }
    	},false);
     };
     /**
      *身份证检测
      *@param customerId  
      *@param customerType  
      *@param docId 
      *@param idNumOCR 
      *@param customerNameOCR 
      */
     contractSign.validIDCard = function(customerId,customerType,obj){
    	 if($(obj).attr("docId")=="" || $(obj).attr("docId")==undefined || $(obj).attr("docId")==null){
    		 art.dialog.alert('请先获取身份证信息');
    		 return false;
    	 }
    	 $.ajax({
     		type:'post',
 			url :ctx + '/carpaperless/confirminfo/validIDCard',
 			data:{
 				'docId':$(obj).attr("docId"),
 				'customerId':customerId,
 				'customerType':customerType
 			},
 			cache:false,
 			dataType:'json',
 			async:false,
 			success:function(result) {
 				$(obj).attr('validResult',result.flag);
 				if("1"==result.flag){
 					$(obj).attr('disabled',true);
 				}
 					art.dialog.alert(result.message);
 				
 			},
 			error : function() {
 				art.dialog.alert('请求异常！', '提示信息');
 			}
       	});
     };
    /**
     *保存照片信息 
     *@author zhanghao
     *@param relationId
     *@param photoType
     *@param customerType
     *@param loanCode
     *
     * 
     */
    contractSign.savePhoto = function(relationId,photoType,customerType,loanCode){
    	$.ajax({
    		type:'post',
			url :ctx + '/carpaperless/confirminfo/savePhoto',
			data:{
				'relationId':relationId,
				'photoType':photoType,
				'customerType':customerType,
				"loanCode":loanCode
			},
			cache:false,
			dataType:'json',
			async:false,
			success:function(result) {
				
			},
			error : function() {
				art.dialog.error('请求异常！', '提示信息');
			}
      	});
    };
    contractSign.zoomUp = function(href){
    	art.dialog({
    	    title: '照片上传',
    	    content:"<img src='"+href+"'>",
    	    width:400, 
			height:80, 
			lock:true,
			opacity: .1
     	},false);
    };