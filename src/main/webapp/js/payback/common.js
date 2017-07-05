fortune=function(){}

/**
 * 获取当前显示的iframe
 */
function getCurrentIframe(){
	return $('iframe:visible', window.parent.document);
}

/**
 * 获取当前显示的iframe的Contents
 */
function getCurrentIframeContents(){
	return $('iframe:visible', window.parent.document).contents();
}

/**
 * Ajax获取/提交
 * @param url 
 * @param data Object对象
 * @param type get/post
 * @param successCal 成功后执行回调函数
 * @param errorCal 失败后执行回调函数
 * @param params 回调函数需呀为的参数
 * @returns {Boolean}
 */
function contents_getJsonForSync(url, data, type, successCal,errorCal,params) {
    $.ajax({
        type: type,
        url: url,
        dataType: "json",
        data: data,
        async: true,
        timeout: 10000,
        success: function (obj) {
        	successCal(obj,params);
        },
        error: function (data, errorThrown, options, error) {
        	if(errorCal != null && typeof errorCal == "function"){
        		errorCal(data.responseText,params);
        	}
        }
    });
    return false;
}

/**
 * Ajax获取页面
 * @param url 
 * @param data Object对象
 * @param type get/post
 * @param successCal 成功后执行回调函数
 * @param errorCal 失败后执行回调函数
 * @param params 回调函数需呀为的参数
 * @returns {Boolean}
 */
function contents_getJsonForHtml(url, data, type, successCal,errorCal,params) {
	loadingMarkShow();
	$.ajax({
        type: type,
        url: url,
        dataType: "html",
        data: data,
        async: true,
        timeout: 10000,
        success: function (obj) {
        	successCal(obj,params);
        	loadingMarkHide();
        },
        error: function (data, errorThrown, options, error) {
        	loadingMarkHide();
        	if(errorCal != null && typeof errorCal == "function"){
        		errorCal(data.responseText,params);
        	}
        }
    });
    return false;
}

/**
 * Ajax获取/提交
 * @param url 
 * @param data JSON.stringify序列化后的 JSON 字符串
 * @param type get/post
 * @param successCal 成功后执行回调函数
 * @param errorCal 失败后执行回调函数
 * @param params 回调函数需呀为的参数
 * @returns {Boolean}
 */
function contents_getJsonForStringify(url, data, type, successCal,errorCal,params) {
    $.ajax({
        type: type,
        url: url,
        dataType: "json",
        contentType : 'application/json',
        data: data,
        async: true,
        timeout: 10000,
        success: function (obj) {
        	successCal(obj,params);
        },
        error: function (data, errorThrown, options, error) {
        	if(errorCal != null && typeof errorCal == "function"){
        		errorCal(data.responseText,params);
        	}
        }
    });
    return false;
}

/**
 * Ajax上传文件
 * @param element 触发事件的按钮 
 * @param url 上传url
 * @param data 文件表单
 * @param successCal 成功后执行回调函数
 * @param errorCal 失败后执行回调函数
 * @param showProgress 是否显示上传进度条
 * @returns {Boolean}
 */
function contents_getJsonForFileUpload(element, url, data, successCal,errorCal,showProgress) {
	if(showProgress == true){
		//需要显示进度条的场合，添加进度条
		if($('progress').length == 0){
			$(element).after($('<progress style="display:none"></progress>'));
		}		
	}
	$.ajax({
        url: url,
        type: 'POST',
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        xhr: function() {  
            var myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){
                myXhr.upload.addEventListener('progress',progressHandlingFunction, false);
            }
            return myXhr;
        },
        success: function(result){
        	successCal(result);
        	$('progress').hide();
        },
        error: function (data, errorThrown, options, error) {
        	if(errorCal != null && typeof errorCal == "function"){
        		errorCal(data.responseText);
        	}
        }
    });
    return false;
}
/**
 * 进度条事件
 */
function progressHandlingFunction(e){
    if(e.lengthComputable){
    	console.log(e);
        $('progress').show().attr({value:e.loaded,max:e.total});
    }
}

/**
 * 表单内容转化成{name:value}形式
 */
$.fn.serializeObject = function()    
{    
   var o = {};    
   var a = this.serializeArray();    
   $.each(a, function() {    
       if (o[this.name]) {    
           if (!o[this.name].push) {    
               o[this.name] = [o[this.name]];    
           }    
           o[this.name].push(this.value || '');    
       } else {    
           o[this.name] = this.value || '';    
       }    
   });    
   return o;    
};


/**
 * 省市区联动
 * provinceId省
 * cityId市
 * districtId县/区
 * 下拉列表框的
 */
fortune.initCity = function(iframe,provinceId,cityId,districtId){
	$("[id="+provinceId+"]",iframe).change(function() { 
		 var provinceId = $(this).val();
		 $city = $(this).closest('tr').find("#"+cityId);
		 $district = $(this).closest('tr').find("#"+districtId);
		 if(provinceId=="-1"){
			 $city.empty();
			 $city.append("<option value='-1' selected=true>请选择</option>");
			 $city.trigger("change");
			 $city.selectpicker('refresh');
			 $district.empty();
			 $district.append("<option value='-1' selected=true>请选择</option>");
			 $district.trigger("change");
		 }else{
		     $.ajax({
	       		type : "POST",
	       		url : ctx + "/provinceCity/getCity",
	       		data: {provinceId: provinceId},	
	       		success : function(data) {
	       			var resultObj = eval("("+data+")");
	       			$city.empty();
	       			$city.append("<option value='-1'>请选择</option>");
		                $.each(resultObj,function(i,item){
		                	$city.append("<option value="+item.id+">"+item.areaName+"</option>");
		                });
		                $city.trigger("change");
		                $city.attr("disabled", false);
		                $city.selectpicker('refresh');
	       		}
		    });
		 }
	});
	$("[id="+cityId+"]",iframe).change(function() { 
		var cityId = $(this).val();
		 $district = $(this).closest('tr').find("#"+districtId);
		 if(cityId=="-1"){
			 $district.empty();
			 $district.append("<option id='-1' value='-1' selected>请选择</option>");
			 $district.trigger("change");
			 $district.selectpicker('refresh');
		 }else{
			 $district.empty();
		     $.ajax({
	       		type : "POST",
	       		url: ctx + "/provinceCity/getDistrict",
	       		data: {cityId: cityId},	
	       		success : function(data) {
	       			var resultObj = eval("("+data+")");
	       		    $district.empty();
	       		    $district.append("<option value='-1' selected>请选择</option>");
	                $.each(resultObj,function(i,item){
	                   $district.append("<option value="+item.id+">"+item.areaName+"</option>");
	                });
	                $district.trigger("change");
	                $district.attr("disabled", false);
	                $district.selectpicker('refresh');
	       		}
		    });
		}
	});
};

/**
 * 省市县回调成功函数
 * @param data 返回数据
 * @param city 需要的省市id
 */
function successInitCity(data,cityId){
	var resultObj = eval("("+data+")");
    $("#"+cityId).empty();
    $("#"+cityId).append("<option value='-1'>请选择</option>");
    $.each(resultObj,function(i,item){
       $("#"+cityId).append("<option value="+item.id+">"+item.name+"</option>");
    });
    $("#"+cityId).trigger("change");
    $("#"+cityId).attr("disabled", false);
}
