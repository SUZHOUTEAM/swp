(function() {
    // 配置
    // var envir = 'online';
    // var configMap = {
		// test : {
		// 	appkey : 'f2f96eba1bacbb80bfcf2a64fb714948',
		// 	url : 'https://apptest.netease.im'
		// },
		// pre : {
		// 	appkey : '45c6af3c98409b18a84451215d0bdd6e',
		// 	url : 'http://preapp.netease.im:8184'
		// },
		// online : {
		// 	appkey : '1a88926e4245ddf4440a0c8923e2ede5',
		// 	url : 'https://api.netease.im'
		// }
    // };
    // window.CONFIG = configMap[envir];
    $.ajax({
        async: false,
        url: "/swp/im/getImEnv",
        dataType:"json",
        success: function(result){
            window.CONFIG = result;
        }
    });
}())

