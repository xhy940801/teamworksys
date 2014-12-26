$(document).ready(function()
{
	$("#signupblock button[type=submit]").click(function(e)
	{
		var psd1 = $("#inputPassword3").val();
		var psd2 = $("#inputConfirmPassword3").val()
		if(psd1 != psd2)
		{
			$("#error-msg").data("error", "两次输入的密码不一致");
			$("#signupblock").data("error-index", 3);
			showError();
			e.preventDefault();
		}
	});
});