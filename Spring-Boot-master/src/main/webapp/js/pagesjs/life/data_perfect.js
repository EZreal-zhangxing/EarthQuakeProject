function changeinfo()
{
	var userid=getCookie('userid');//获取用户userid from cookie
	var nicename=$('#nicename').val();
	var truename=$('#truename').val();
	var male=$('[name=sex]:checked').val();
	var sex=0;
	if(male=='男')
	{
		sex=1;
	}
	var birthday=$('#birthday').val();
	var community=$('#community').val();
	var detailAddress=$('#detailAddress').val();
	console.log(nicename+","+truename+","+male+","+birthday+","+community+","+detailAddress);
	$.ajax({
		url:'/changeuserinfo?userid='+userid+"&nicename="+nicename+"&truename="+truename+"&sex="+sex+"&birthday="+birthday+"&community="+community+"&detailAddress="+detailAddress,
		async:false,
		type:'POST',
		success:function(data){
			if(data.code=="success")
			{
				alert(data.message);
				window.location.href="/life/mypage.html";
			}else
			{
				alert(data.message);
			}
		}
	});
}