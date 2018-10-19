var userid;
$(document).ready(function(){
	userid=getCookie('userid');//获取用户userid from cookie
	loadyjx();
	loadbanner();
});
//加载直播链接
function loadyjx()
{
	$.ajax({
		url:'/getArticelsBymodel/18/'+(userid==null?0:userid),
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var Data = data[index];
				$('.orange').attr('href',Data.outUrl);
			});
		}
	});
}
//加载banner图
function loadbanner()
{
	$.ajax({
		url:'/getAllbanner?belong=2',
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