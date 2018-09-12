$(document).ready(function(){
	var hash = window.location.hash;
    hash=hash.substr(1);
	changePage(hash);
})
window.onhashchange = function() { //监听hash值变化，实现页面变换
	var hash = window.location.hash;
    hash=hash.substr(1);
	changePage(hash);
}

function changePage(hash) {　　
	var url='component/index.html';
	var hash=!hash?'index':hash;
	switch(hash)　　 {　　
		case 'index':
	　　　　url = 'component/index.html?16';
			break;　　
		case 'search':
	　　　　url = 'component/search.html?3';　　　　
			break;　　
		case 'searchDing':
	　　　　url = 'component/search-ding.html?3';　　　　
			break;　　
		case 'entDetail':
	　　　　url = 'component/entDetail.html?1';　　　　
			break;　　
		case 'activityDetail':
	　　　　url = 'component/activityDetail.html?1';　　　　
			break;　　
		case 'publish':
	　　　　url = 'component/publish.html?7';　　　　
			break;　　
		case 'publishDing':
	　　　　url = 'component/publish-ding.html?7';　　　　
			break;　　
		case 'publishSuccess':
	　　　　url = 'component/publish-success.html?1';　　　　
			break;　　
		case 'publishList':
	　　　　url = 'component/publishList.html';　　　　
			break;　　
	}
	// $('.tab-'+hash.substr(1)).addClass('active').siblings().removeClass('active');
    $('#main').load(url);
    scrollTo(0,0);
    // for(var i=timerList.length-1;i>=0;i--){
    //     clearInterval(timerList[i]);
    //     timerList.splice(i,1);
    // }
}
