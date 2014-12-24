$(document).ready(function()
{
	function showError()
	{
		var errorData = $("#error-msg").data("error");
		var index = $("#signupblock").data("error-index");
		if(errorData)
		{
			$("#error-msg strong + span").text(errorData);
			$("#error-msg").show();
		}
		if(index)
			$("#signupblock>form>div:nth-of-type(" + index + ")").addClass("has-error");
	}

	showError();

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