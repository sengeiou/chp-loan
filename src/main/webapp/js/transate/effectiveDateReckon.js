var  effectiveDate={};
effectiveDate.interval = function(srcDate){
	var applyDate = effectiveDate.strFormatToDate('yyyy-MM-dd HH:mm:ss',srcDate);
	var curDate = new Date();
	return (curDate.getTime()-applyDate.getTime())/3600000;
};
/**
 *把指定格式的字符串转换为日期对象yyyy-MM-dd HH:mm:ss 
 * 
 */
effectiveDate.strFormatToDate = function(formatStr,dateStr){
	 var year = 0;
     var start = -1;
     var len = dateStr.length;
     if((start = formatStr.indexOf('yyyy')) > -1 && start < len){
         year = dateStr.substr(start, 4);
     }
     var month = 0;
     if((start = formatStr.indexOf('MM')) > -1  && start < len){
         month = parseInt(dateStr.substr(start, 2)) - 1;
     }
     var day = 0;
     if((start = formatStr.indexOf('dd')) > -1 && start < len){
         day = parseInt(dateStr.substr(start, 2));
     }
     var hour = 0;
     if( ((start = formatStr.indexOf('HH')) > -1 || (start = formatStr.indexOf('hh')) > 1) && start < len){
         hour = parseInt(dateStr.substr(start, 2));
     }
     var minute = 0;
     if((start = formatStr.indexOf('mm')) > -1  && start < len){
         minute = dateStr.substr(start, 2);
     }
     var second = 0;
     if((start = formatStr.indexOf('ss')) > -1  && start < len){
         second = dateStr.substr(start, 2);
     }
     return new Date(year, month, day, hour, minute, second);
	
};