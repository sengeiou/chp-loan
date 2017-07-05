var approve=function(){};
/**
 * Ajax获取
 * @param url 
 * @param data
 * @param successCal 成功后执行回调函数
 * @param errorCal 失败后执行回调函数
 * @param params 回调函数需呀为的参数
 */
approve.getJsonForSync = function(url, data, type, successCal,errorCal,params) {
    $.ajax({
        type: type,
        url: url,
        dataType: "json",
        data: data,
        async: true,
        timeout: 10000,
        beforeSend: function(XMLHttpRequest){
        	waitingDialog.show();
        },
        complete: function(XMLHttpRequest, textStatus){
        	waitingDialog.hide();
        },
        success: function (obj) {
        	successCal(obj,params);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        	if(errorCal != null && typeof errorCal == "function"){
        		errorCal(XMLHttpRequest,textStatus,errorThrown,params);
        	}
        }
    });
};

var waitingDialog = waitingDialog || (function ($) {
	var $dialog = $(
		'<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="overflow-y:visible;">' +
		'<div class="modal-dialog modal-m">' +
		'<div class="modal-content">' +
			'<div class="modal-header"><h3 style="margin:0;"></h3></div>' +
			'<div class="modal-body">' +
				'<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +
			'</div>' +
		'</div></div></div>');

	return {
		show: function (message, options) {
			// Assigning defaults
			if (typeof options === 'undefined') {
				options = {};
			}
			if (typeof message === 'undefined') {
				message = 'Loading';
			}
			var settings = $.extend({
				dialogSize: 'm',
				progressType: '',
				onHide: null // This callback runs after the dialog was hidden
			}, options);

			// Configuring dialog
			$dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
			$dialog.find('.progress-bar').attr('class', 'progress-bar');
			if (settings.progressType) {
				$dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
			}
			$dialog.find('h3').text(message);
			// Adding callbacks
			if (typeof settings.onHide === 'function') {
				$dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
					settings.onHide.call($dialog);
				});
			}
			// Opening dialog
			$dialog.modal();
		},
		/**
		 * Closes dialog
		 */
		hide: function () {
			$dialog.modal('hide');
		}
	};

})(jQuery);

$.fn.extend({          
	cloneSelect2:function(withDataAndEvents,  deepWithDataAndEvents) {
		  var $oldSelects2 = this.is('select') ? this : this.find('select');
		  //$oldSelects2.select2('destroy');
		  $oldSelects2.each(function(){
			  $(this).select2('destroy');
		  });

		  var $clonedEl = this.clone(withDataAndEvents,  deepWithDataAndEvents);
		  $oldSelects2.select2();
		  $clonedEl.is('select') ? $clonedEl.select2() : $clonedEl.find('select').select2();
		  return $clonedEl;         
	}
});

(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);
var Util={
	isEmpty : function(str){
		 if(str != null && str.length > 0)
		 {
		    return true;
		 }
		 return false;
	}
}