//----------------轮播图-------------------------------------------------------
var shijian=1100;//轮播时间
var antialias=1;
function  bug() {
		if(antialias==1){
			$('.mian-loginbar').fadeIn(shijian);
		}else{
			$('.mian-loginbar').fadeOut(shijian);
		}
		$('.antialias').fadeOut(shijian);
		$('.antialias').eq(antialias-1).fadeIn(shijian);
};
function weifashi() {
		antialias++;
		if(antialias>inde+1){antialias=1;}
		if(antialias==0){antialias=2}
		bug();
}
//	//开启定时器
timoutid = setInterval(weifashi,5000);
	$('.main-warp,.mian-container-left,.mian-container-right,.mian-loginbar-anniu,.mian-loginbar').hover(function(){//鼠标移入的时候关闭定时器
		clearInterval(timoutid);
	},function  () {
		clearInterval(timoutid);
		timoutid = setInterval(weifashi,5000)
	});
$('.mian-container-left').click(function  () {
	antialias++;
	if(antialias>inde+1){antialias=1;}
	bug();
});
$('.mian-container-right').click(function  () {
	antialias--;
	if(antialias<0){antialias=inde+1}
	if(antialias<=0){antialias=inde+1;}
	bug();
});
var inde=0;
$('.antialias').each(function  (index) {
	inde=index;
	$('.mian-loginbar-anniu span').eq(index).click(function(){
	antialias=$(this).index()+1;
	bug();
	$(this).find('img').attr('src','img/indexr-anniu-02.png');
	})
})

function setMainPage(){
	alert("设为主页失败，请在浏览器里手动设置。");
}
function setCollect(){
	alert("加入收藏夹失败，请在浏览器里手动设置。");
}
