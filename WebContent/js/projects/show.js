$(document).ready(function()
{
	$(".task-head").click(function()
	{
		$(this).siblings(".task-body").fadeToggle()
	});
});