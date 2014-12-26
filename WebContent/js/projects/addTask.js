function reflashInvolves(involves)
{
	var nicknames = "";
	var ids = "";
	for(var id in involves)
	{
		if(involves[id])
		{
			nicknames += involves[id] + " ";
			ids += id + ",";
		}
	}
	ids = ids.substr(0, ids.length - 1);
	$("#inputInvolves").val(nicknames);
	$("#addtaskblock form input[name=involves]").val(ids);
}


$(document).ready(function()
{
	var involves = {};
	$(".involve-opt").click(function()
	{
		var id = $(this).data("user-id");
		var nickname = $(this).data("nickname");
		
		if(involves[id])
			involves[id] = null;
		else
			involves[id] = nickname;
		reflashInvolves(involves);
	});
	
	$("#addtaskblock button[type=submit]").click(function(e)
	{
		var ids = $("#addtaskblock form input[name=involves]").val();
		var taskname = $("#inputName").val();
		var remark = $("#inputRemark").val();
		if(!ids)
		{
			$("#error-msg").data("error", "请选择参与者");
			$("#addtaskblock").data("error-index", 3);
			showError();
			e.preventDefault();
			return;
		}
	});
});