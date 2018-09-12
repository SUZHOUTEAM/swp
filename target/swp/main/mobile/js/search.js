/**
 * Created by wudang on 2017/7/17.
 */
var search={
    type:'PRODUCTION',page:1,totalPage:1,loading:false,keyWord:'',loadComplete:false,
    wasteStatus:{'RELEASED':'待处置','END':'已处置','SUBMIT':'待处置'},
    bindEvent:function(){
        $('.sd_tab.sd_btn a').on('click',function(){
            search.type=$(this).index()==0?'PRODUCTION':'DISPOSITION';
            $(this).addClass('active').siblings().removeClass('active');
            search.keyWord=$('#search').val();
            $('#entList').html('');
            $('#loadComplete').hide();
            if(search.type=='PRODUCTION'){//搜索产废信息
                dplus.track("【手机端web】搜索产废信息", {
                    "key": search.keyWord,
                });
                search.page=1;
                search.loadComplete=false;
                search.initValueForWaste();
            }else{//搜索经营单位
                dplus.track("【手机端web】搜索经营单位", {
                    "key": search.keyWord,
                });
                search.page=1;
                search.loadComplete=false;
                search.initValue();
            }
        });
        $('.sd_tab a').on('click',function(){
            $(this).addClass('active').siblings().removeClass('active');
        });
        $(window).scroll(function() {
            if(!search.loadComplete){
                $('.loading').show();
                var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
                if($(document).height() <= totalheight+50) {
                    if(!search.loading) {
                        search.loading = true;
                        search.page+=1;
                        if(search.type=='PRODUCTION'){
                            search.initValueForWaste();
                        }else{
                            search.initValue();
                        }
                        // setTimeout(search.initValue(), 4000);
                    };
                };
                $('#loadComplete').hide();
            }else{
                $('#loadComplete').html('<span class="empty">数据加载完成</span>').show();
            }
        });
        if(this.type=='PRODUCTION'){
            $('.sd_tab.sd_btn >a:first-child').click();
        }else{
            $('.sd_tab.sd_btn >a:first-child +a').click();
        }
    },
    getParam:function(paraName) {
        var search = document.location.search,
            reg = new RegExp("[?&]+" + paraName + "=([^&]+)");
        if (search && reg.test(search)) {
            return decodeURIComponent(RegExp['$1']).replace(/\+/g, " ");
        }
        return null;
    },
    getHtmlByList:function(wasteImgList){
        if(!wasteImgList||wasteImgList.length==0){
            return '<div class="di_img"><div class="imgItem"><img src="../img/production_noimg.jpg"/></div></div>';
        }
        var str='<div class="di_img">';
        for(var i in wasteImgList){
            var obj=wasteImgList[i];
            if(obj['businessCode']&&obj['fileId']){
                var imgUrl=IMG_VIEW_URL+'&businessCode='+obj['businessCode']+'&fileID='+obj['fileId'];
                str+='<div class="imgItem"><img src="'+imgUrl+'"/></div>';
            }
        }
        str+='</div>';
        return str;
    },
    highLight:function(str){
        var keyword=search.keyWord;
        // var re =new RegExp("^" + keyword + "$","gim"); // re为/^\d+bl$/gim
        var re =eval("/"+keyword+"/g");
        str=str.replace(re,'<font color="red">'+keyword+'</font>');
        return str;
    },
    getWasteList:function (list) {
        var widths=list.length*100+66;
        var str='<div class="c_list" style="width:'+widths+'px">';
        for(var i in list){
            var obj=list[i];
            str+='<div class="c_item">' +
                    '<p>'+obj.wasteName+'</p>'+
                    '<p>'+obj.wasteCode+'</p>'+
                    '<p>'+obj.wasteAmount+obj.unitValue+'</p>'+
                '</div>';
        }
        str+='</div>';
        return str;
    },
    initValueForWaste:function(){
        $('.loading').show();
        $.ajax({
            url:'/swp/entRelease/listWasteEntRelease4MobileWeb?pageIndex='+this.page+'&pageSize=10',
            type:'POST',
            data:JSON.stringify({
                keyword:search.keyWord,
            }),
            dataType:'json',
            contentType:'application/json',
            success:function(data){
                $('.loading').hide();
                console.log(data);
                if(data.status==1&&data.data['entReleaseList']&&data.data['entReleaseList'].length>0){
                    var enterpriseWasteViews=data.data['entReleaseList'];
                    var str='';
                    for(var i in enterpriseWasteViews){
                        var obj=enterpriseWasteViews[i];
                        str+='<div class="di di_c" onclick="location=\'/swp/login.jsp\'">'+
                                '<div class="di_m">' +
                                    '<span class="c_time">'+obj.cantonCodeName+'</span>' +
                                    '<span class="c_status '+obj.releaseStatus+'">'+search.wasteStatus[obj.releaseStatus]+'</span>'+
                                    '<span class="c_name">'+obj.disposalWasteCount+'种危废，'+obj.disposalWasteAmount+'</span>' +
                                '</div>'+
                                '<div class="c_list_container">' +
                                        search.getWasteList(obj.releaseWasteDetails)+
                                '</div>'+
                             '</div>';
                    }
                    if(search.page==1){
                        $('#entList').html(str);
                    }else{
                        $('#entList').append(str);
                    }
                    search.loading=false;
                }else{
                    $('#entList').html('');
                    $('#loadComplete').html('<span class="empty">暂无相关数据</span>').show();
                }
                if(data.data['entReleaseList']&&data.data['entReleaseList'].length<10){
                    search.loadComplete=true;
                }
            }
        });
    },
    initValue:function(){
        $('.loading').show();
        $.ajax({
            url:'/swp/enterprise/getEnterpriseSuggest.htm',
            type:'POST',
            data:{
                entType:this.type,
                wasteCodeOrName:search.keyWord,
                pageIndex:this.page
            },
            dataType:'json',
            success:function(data){
                $('.loading').hide();
                console.log(data);
                if(data.status==1&&data.data['entList']&&data.data['entList'].length>0){
                    var entList=data.data['entList'];
                    var str='';
                    search.totalPage=data.data.paging['totalPage'];
                    for(var i in entList){
                        var obj=entList[i];
                        str+='<div class="di di_cz">'+
                                '<div class="di_m">'+
                                    '<a href="/swp/login.jsp" class="cd">'+
                                        '<span class="name">'+obj.entName+'</span>'+
                                        '<i class="arrow"></i>'+
                                    ' </a>'+
                                '</div>'+
                                '<div class="di_other">'+
                                    '<p class="info">地址：'+obj.entAddress+'</p>'+
                                    '<p class="info">许可证编号：'+obj.liceneNo+'</p>'+
                                    '<p class="info">年许可量：<font>--</font></p>'+
                                    '<p class="info">经营方式：'+obj.dispositionType+'</p>'+
                                    '<p class="info">核准经营危险废物类别：'+obj.wasteCode+'</p>'+
                                    '<p class="info">危废名称：'+obj.wasteName+'</p>'+
                                '</div>'+
                                '<div class="di_img">'+
                                    '<div class="imgItem"><img src="../img/license.jpg"/></div>'+
                                '</div>'+
                            '</div>';
                    }
                    if(search.page==1){
                        $('#entList').html(str);
                    }else{
                        $('#entList').append(str);
                    }
                    search.loading=false;
                    if(data.data['entList'].length<10){
                        search.loadComplete=true;
                    }
                }else{
                    $('#entList').html('');
                    $('#loadComplete').html('<span class="empty">暂无相关数据</span>').show();
                }
            }
        });
    },
    init:function(){
        this.type=this.getParam('type');
        this.keyWord=this.getParam('key');
        if(this.keyWord){
            $('#search').val(this.keyWord);
        }
        // this.initValue();
        this.bindEvent();
    }
}

