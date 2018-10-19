var userid;
$(document).ready(function(){
	userid=getCookie('userid');//获取用户userid from cookie
	loadxyh();
});
//加载享优惠
function loadxyh()
{
	$.ajax({
		url:'/getArticelsBymodel/14/'+(userid==null?0:userid),
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var pageStr="";
				var Data = data[index];
				console.log(Data);
				
				pageStr+='<dl>'+'<dt class="border">';
				if(Data.outUrl != null && Data.outUrl != '')
				{
					pageStr+='<a href="'+Data.outUrl+'">';
				}else
				{
					pageStr+='<a href="favourable_details.html?articelId='+Data.id+'">';
				}
				pageStr+=
			    '<img src="/readpicFile?filepath='+Data.articelPicPath+'"></a></dt>'+
			    '<dd class="activity_txt"><span>';
			     if(Data.outUrl != null && Data.outUrl != '')
				{
					pageStr+='<a href="'+Data.outUrl+'">';
				}else
				{
					pageStr+='<a href="favourable_details.html?articelId='+Data.id+'">';
				}
			    pageStr+=Data.title+'</a></span>';
			    pageStr+='</b></dd></dl>';
				$('.activity').append(pageStr);
			});
		}
	});
}
