
//写cookies 
function setCookie(name,value) { 
    var days = 1; 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + days*24*60*60*1000); 
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
} 

//读取cookies 
function readCookie(name) { 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg)){
        return unescape(arr[2]); 
    }else{
        return null;
    }
        
}
function delCookie(name) {
    var cval = readCookie(name);
    if (cval != null) {
        document.cookie = name + "=" + cval + ";expires=" + (new Date(0)).toGMTString();
    }
}
function clearCookie(){
    var keys=document.cookie.match(/[^ =;]+(?=\=)/g);
    if (keys) {
        for (var i = keys.length; i--;)
            document.cookie=keys[i]+'=0;expires=' + new Date( 0).toUTCString()
    }
}
function getParam(paraName) {
    var search = document.location.search,
        reg = new RegExp("[?&]+" + paraName + "=([^&]+)");
    if (search && reg.test(search)) {
        return decodeURIComponent(RegExp['$1']).replace(/\+/g, " ");
    }
    return null;
}
function ajax(options){
    if(typeof options['data']!='string'&&options['url'].indexOf('/userLogin/login')==-1){
        if(options['data']){
            if(!options['data']['ticketId']){
                options['data']['ticketId']=getParam('ticketId');
            }
        }else{
            options['data']={};
            options['data']['ticketId']=getParam('ticketId');
        }
    }
    var ajaxData={};
    ajaxData['type']=options['type']||'post';
    ajaxData['url']=appPath+options['url'];
    if(options['data']){
        ajaxData['data']=options['data'];
    }
    ajaxData['dataType']='json';
    if(options['contentType']){
        ajaxData['contentType']=options['contentType'];
    }
    ajaxData['async']=options['async']==false?false:true;
    ajaxData['success']=function(data){
        if(!data.success){
            data.msg&&console.error(data.msg);
        }
        if(data.status==-1){
            window.location=appPath+'/login.jsp';
            return;
        }
        options['success']&&options['success'](data);
    }
    ajaxData['error']=function(error){
        if(options['error']){
            options['error'](error);
        }else{
            console.error(error.status,error.responseText);
        }
    }
    $.ajax(ajaxData);
}
function fmoney(s, n) {  //s:传入的float数字 ，n:希望返回小数点几位
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(),
        r = s.split(".")[1];
    t = "";
    for (var i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
}
function collectingUserBehavior(ticketId,eventCode,remark,eventValue) {
    if(returnCitySN['cip']=='58.210.204.106'){
        return;
    }
    var param={
        eventCode:eventCode,
        eventValue:eventValue,
        cid:returnCitySN['cid'],
        cname:returnCitySN['cname'],
        cip:returnCitySN['cip']
    };
    if(ticketId){
        param.ticketId=ticketId;
    }
    if(remark){
        param.remark=remark;
    }
    $.ajax({
        url:'/swp/userAction/saveUserAction',
        type:'post',
        data:param,
        async:true,
        success:function (result) {
            console.log(result);
        }
    })
}