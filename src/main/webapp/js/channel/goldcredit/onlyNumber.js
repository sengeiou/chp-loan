 //上限数量和额度的设置
  // ----------------------------------------------------------------------
  // <summary>
  // 限制只能输入数字
  // </summary>
  // ----------------------------------------------------------------------
  $.fn.onlyNum = function () {
      $(this).keypress(function (event) {
          var eventObj = event || e;
          var keyCode = eventObj.keyCode || eventObj.which;
         if ((keyCode >= 48 && keyCode <=57) || keyCode == 8)
             return true;
         else {
        	 //art.dialog.alert("除数字外的全部都是非法字符,请输入数字!");
        	 return false;
         }
     }).focus(function () {
     //禁用输入法,禁止输入中文字符
    	 this.style.imeMode='disabled';
     }).bind("paste", function () {
     //获取剪切板的内容
         var clipboard = window.clipboardData.getData("Text");
         if (/^\d+$/.test(clipboard))
             return true;
         else
             return false;
     });
 };
  //----------------------------------------------------------------------
  // <summary>
  // 限制只能输入数字和浮点数
  // </summary>
  // ----------------------------------------------------------------------
  $.fn.onlyFloat = function () {
     $(this).keypress(function (event) {
          var eventObj = event || e;
          var keyCode = eventObj.keyCode || eventObj.which;
         if (keyCode == 46 || (keyCode >= 48 && keyCode <=57) || keyCode == 8)
             return true;
         else
             return false;
     }).focus(function () {
     //禁用输入法
         this.style.imeMode = 'disabled';
     }).bind("paste", function () {
     //获取剪切板的内容
         var clipboard = window.clipboardData.getData("Text");
         if (/^\d+$/.test(clipboard))
             return true;
         else
             return false;
     });
 };