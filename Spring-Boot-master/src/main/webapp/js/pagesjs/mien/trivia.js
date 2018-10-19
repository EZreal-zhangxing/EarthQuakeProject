$(document).ready(function(){
	var userid=getCookie('userid');//获取用户userid from cookie
	$.ajax({
		url:'/getArticelsBymodel/9/'+(userid==null?0:userid),
		async:true,
		success:function(data)
		{
			$(data).each(function(index,element){
				var Data = data[index];
				console.log(Data);
				var time = new Date(Data.date).Format("yyyy-MM-dd");
				if(Data.videoPath != null && Data.videoPath != '')
				{
					console.log('tidbit_video');
					$('#content').append(
						'<div class="tidbit_video border">'+
						'<a href="video_details.html?articelId='+Data.id+'">'+
					    '<img src="/readpicFile?filepath='+Data.articelPicPath+'" />'+
						'<span>&nbsp;</span>'+
						'<p>'+Data.title+'</p>'+
					    '</a>'+
					    '</div>'
					);
				}else if((Data.articelPicPath == null || Data.articelPicPath=='')&&(Data.videoPath == null || Data.videoPath == ''))
				{
					console.log('tidbit_text');
					var desc;
					if(Data.articelDesc.length >= 50)
					{
						desc=Data.articelDesc.substr(0,50)+"...";
					}else
					{
						desc=Data.articelDesc;	
					}
					if(Data.outUrl != null && Data.outUrl !='')
					{
						$('#content').append(
							'<div class="tidbit_text border">'+
							'<a href="'+Data.outUrl+'">'+
						    '<strong>'+Data.title+'</strong>'+
//	    					'<p>'+Data.articelDesc+'</p>'+
	    					'<p>'+desc+'</p>'+
							'<span>'+time+'</span>'+
						    '</a>'+
							'</div>'
						);
					}else
					{
						$('#content').append(
							'<div class="tidbit_text border">'+
							'<a href="article_details.html?articelId='+Data.id+'">'+
						    '<strong>'+Data.title+'</strong>'+
//	    					'<p>'+Data.articelDesc+'</p>'+
	    					'<p>'+desc+'</p>'+
							'<span>'+time+'</span>'+
						    '</a>'+
							'</div>'
						);
					}
				}else
				{
					var desc;
					if(Data.articelDesc.length >= 32)
					{
						desc=Data.articelDesc.substr(0,32)+"...";
					}else
					{
						desc=Data.articelDesc;	
					}
					if(Data.outUrl != null && Data.outUrl != '')
					{
						$('#content').append(
							'<div class="tidbit_imgtext">'+
							'<a href="'+Data.outUrl+'">'+
						    '<dl><dt><img src="/readpicFile?filepath='+Data.articelPicPath+'" /></dt>'+
						    '<dd class="border">'+
						        	'<div>'+Data.title+'</div>'+
//						            '<p>'+Data.articelDesc+'</p>'+
						            '<p>'+desc+'</p>'+
						            '<span>'+time+'</span>'+
						    '</dd></dl>'+
						    '</a>'+
							'</div>'
						);
					}else
					{
						$('#content').append(
							'<div class="tidbit_imgtext">'+
							'<a href="article_details.html?articelId='+Data.id+'">'+
						    '<dl><dt><img src="/readpicFile?filepath='+Data.articelPicPath+'" /></dt>'+
						    '<dd class="border">'+
						        	'<div>'+Data.title+'</div>'+
//						            '<p>'+Data.articelDesc+'</p>'+
						            '<p>'+desc+'</p>'+
						            '<span>'+time+'</span>'+
						    '</dd></dl>'+
						    '</a>'+
							'</div>'
						);
					}
				}
			});
		}
	});
});
