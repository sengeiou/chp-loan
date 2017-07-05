jQuery.popuplayer = function(btnclass) {
	$(btnclass).bind("click", function(event) {
		event.stopPropagation(); // 阻止事件冒泡
		var $currdiv = $(this).next();
		
		$(btnclass).each(function(){
			var $other = $(this).next();
			if (!$other.is($currdiv) && !$other.is(":hidden"))
				$other.hide();
		});
		$currdiv.css("float", "left");
		$currdiv.css("position", "absolute");

		if ($currdiv.is(":hidden"))
			$currdiv.show();
		else
			$currdiv.hide();
	});
	$(document).click(function() {
		$(btnclass).next().hide();
	});
};