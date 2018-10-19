var articelId;
var system; //1 安卓  2 IOS
$(document).ready(function(){
	articelId=getQueryString('articelId');//获取文章ID
	operationSystem();
	loadvideo();
});
var pagestr="";
function loadvideo()
{
	$.ajax({
		url:'/getArticelContent/'+articelId,
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var Data = data[index];
				console.log(Data);
				console.log(Data.id);
				var time = new Date(Data.date).Format("yyyy-MM-dd");
				var hz=getHz(Data.videoName);
				console.log(hz);
				var host ="http://"+document.domain;
				console.log(host);
				pagestr=pagestr+
					'<h1>'+Data.title+'</h1>'+
				    '<span class="publishTime">'+time+'</span>'+
				    '<div class="video_show" align="center">';
					pagestr=pagestr+
					"<video id='example_video_1' class='video-js vjs-default-skin vjs-big-play-centered' controls preload='auto' width='512' height='512' data-setup='{}'>";
					if(hz == "mp4")
					{
						pagestr=pagestr+"<source src='"+host+":9000/video/"+Data.videoName+"' type='video/mp4' />";						
					}else if(hz == "webm")
					{
						pagestr=pagestr+"<source src='"+host+":9000/video/"+Data.videoName+"' type='video/webm' />";
					}else if(hz == "ogg")
					{
						pagestr=pagestr+"<source src='"+host+":9000/video/"+Data.videoName+"' type='video/ogg' />";
					}
					pagestr=pagestr+
					"<track kind='captions' src='' srclang='en' label='English'></track><!--字幕 Tracks need an ending tag thanks to IE9 -->"+
					"<track kind='subtitles' src='' srclang='en' label='English'></track><!--字幕 Tracks need an ending tag thanks to IE9 -->"+
					"</video>";
				pagestr+='</div>';
			});
			loadComment();
		}
	});
}
function loadComment(){
	$.ajax({
		url:'/getCommentByarticelid/'+articelId,
		async:true,
		success:function(data)
		{
			console.log(data);
			pagestr=pagestr+'<div class="talk">';
			if(data.length!=0)
			{
				pagestr+='<p class="talk_num">共'+data[0].Num+'条评论</p>';
			}else
			{
				pagestr+='<p class="talk_num">共 0 条评论</p>';
			}
		    pagestr+='<p class="talk_time">按回复时间排序<a class=""></a></p></div>';
			$(data).each(function(index,element){
				var Data = data[index];
				pagestr=pagestr+
				'<div class="remark">'+
			    	'<div class="remark_img"><img src="../images/talk_head.png" /></div>'+
			        '<div class="remark_word">'+
			        	'<p class="remark_name">'+Data.niceName+'<span>'+(index+1)+'楼</span></p>'+
			            '<p class="remark_saying">'+Data.comment+'</p>'+
			            '<p class="remark_time">'+Data.Date+'</p>'+
			        '</div>'+
			    '</div>';
			});
			$('#content').append(pagestr);
		}
	});
}

function sendmsg()
{
	var youinput=$('#youinput').val();
	console.log(youinput);
	
	var userid=getCookie('userid');//获取用户userid from cookie
	if(userid == null)
	{
		alert('请先登陆！');
		window.location.href="/life/login.html?addressUrl=1";
	}else
	{
		$.ajax({
			url:'/sendComment/'+articelId+'/'+userid+'/'+youinput,
			async:true,
			success:function(data)
			{
				if(data.code == 'success')
 				{
//					alert(data.code+":"+data.message);
					window.location.reload();
 				}else{
 					alert(data.code+":"+data.message);
 				}
			}
		});
	}
}

function operationSystem()
{
	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	if(isAndroid)
	{
		system=1;
	}
	if(isiOS)
	{
		system=2;	
	}
//	alert('是否是Android：'+isAndroid);
//	alert('是否是iOS：'+isiOS);
}

function getHz(path)
{
	return path.substr(path.indexOf(".")+1,path.length);
}