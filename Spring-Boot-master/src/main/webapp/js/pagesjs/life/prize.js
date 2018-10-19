var point;
$(document).ready(function(){
	point=getCookie('point');//获取用户userinfo from cookie
	$('.integral_num').html(point);
	loadjfgz();
});
function loadcontent(articelId)
{
	$.ajax({
			url:'/getArticelContent/'+articelId,
			async:true,
			success:function(data)
			{
				$(data).each(function(index,element){
					var Data = data[index];
					$('.integral_exchange').append('<li>'+Data.content.replace(/div/g,'p')+'</li>');
				});
//				$('.integral_exchange').append('<li><hr></li>');
			}
		});
}
function loadjfgz()
{
	var userid=getCookie('userid');//获取用户userinfo from cookie
	$.ajax({
		url:'/getArticelsBymodel/20/'+(userid==null?0:userid),
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var Data = data[index];
				loadcontent(Data.id);
			});
		}
	});
}


