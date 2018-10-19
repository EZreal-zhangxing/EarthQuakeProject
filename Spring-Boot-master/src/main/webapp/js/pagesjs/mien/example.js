var userid;
$(document).ready(function(){
	userid=getCookie('userid');//获取用户userid from cookie
	loadzb();
	loadH5bt();
	loadbanner();
});
//加载直播链接
function loadzb()
{
	$.ajax({
		url:'/getArticelsBymodel/7/'+(userid==null?0:userid),
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var Data = data[index];
				$('#zb').attr('href',Data.outUrl);
			});
		}
	});
}

function loadH5bt()
{
	$.ajax({
		url:'/getArticelsBymodel/19/'+(userid==null?0:userid),
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var Data = data[index];
				console.log(Data);
				$('.selection_tit').append('<a href='+Data.outUrl+'><p>'+Data.title+'</p><span>'+Data.articelDesc+'</span></a>');
			});
		}
	});
}
//加载banner图
function loadbanner()
{
	$.ajax({
		url:'/getAllbanner?belong=1',
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var Data = data[index];
				console.log(Data);
//				console.log($('.slides li a img')[index+1].src);
//				$('.slides li a img')[index+1].src='/readpicFile?filepath='+Data.bannerPath;
				$('.slides').append('<li><a href="javascript:;" class="slide"><img src="/readpicFile?filepath='+Data.bannerPath+'" alt="" /></a></li>');
			});
			lunbo();
		}
	});
}