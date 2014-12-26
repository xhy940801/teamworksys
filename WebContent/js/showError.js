function showError()
{
	var errorData = $("#error-msg").data("error");
	var index = $(".error-index").data("error-index");
	if(errorData)
	{
		$("#error-msg strong + span").text(errorData);
		$("#error-msg").show();
	}
	if(index)
		$(".error-index>form>div:nth-of-type(" + index + ")").addClass("has-error");
}

$(document).ready(showError());