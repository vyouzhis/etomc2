
/*
 *	############################################################################
 *	
 *   bellNotification
 *   
 *	############################################################################
 */

var cnt = 10;

TabbedNotification = function(options) {
	var message = "<div id='ntf"
			+ cnt
			+ "' class='text alert-"
			+ options.type
			+ "' style='display:none'><p><i class='fa fa-bell'></i> "
			+ options.title
			+ "</p><div class='close'><a href='javascript:;' class='notification_close'><i class='fa fa-close'></i></a></div><p>"
			+ options.text + "</p></div>";

	if (!document.getElementById('custom_notifications')) {
		BellNotifi('doesnt exists');
	} else {
		$('#custom_notifications ul.notifications')
				.append(
						"<li><a id='ntlink"
								+ cnt
								+ "' class='alert-"
								+ options.type
								+ "' href='general_elements.html#ntf"
								+ cnt
								+ "'><i class='fa fa-bell animated shake'></i></a></li>");
		$('#custom_notifications #notif-group').append(message);
		cnt++;
		CustomTabs(options);
	}
};

CustomTabs = function(options) {
	$('.tabbed_notifications > div').hide();
	$('.tabbed_notifications > div:first-of-type').show();
	$('#custom_notifications').removeClass('dsp_none');
	$('.notifications a').click(
			function(e) {
				e.preventDefault();
				var $this = $(this), tabbed_notifications = '#'
						+ $this.parents('.notifications').data(
								'tabbed_notifications'), others = $this
						.closest('li').siblings().children('a'), target = $this
						.attr('href');
				others.removeClass('active');
				$this.addClass('active');
				$(tabbed_notifications).children('div').hide();
				$(target).show();
			});
};

CustomTabs();

var tabid = idname = '';

$(document).on('click', '.notification_close', function(e) {
	idname = $(this).parent().parent().attr("id");
	tabid = idname.substr(-2);
	$('#ntf' + tabid).remove();
	$('#ntlink' + tabid).parent().remove();
	$('.notifications a').first().addClass('active');
	$('#notif-group div').first().css('display', 'block');
});

function BellNotifi(msg) {
	new TabbedNotification({
		title : '当前信息提示',
		text : "当前提示信息内容：<strong>"+msg+"</strong>",
		type : 'info',
		sound : false
	});
}
