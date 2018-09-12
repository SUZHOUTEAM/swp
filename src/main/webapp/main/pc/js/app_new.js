var vue = new Vue({
    el: '#app',
    data:{
        activityList:[],
        listLoading:true,
        keyWord:'',
        restaurants:'',
        czArea:'native',
        lyArea:'native',
        tsArea:'native',
        czPage:1,
        lyPage:1,
        tsPage:1,
        czMaxPage:1,
        lyMaxPage:1,
        tsMaxPage:1,
        czList:[],
        lyList:[],
        tsList:[],
        cantonCode:'',
        provinces:province,
        provinceCode:provinceCode,
        cityShow:false,
        dialogVisible:false,
        carouselData:carouselData,
        guideShow:false,
        showVideo:false,
        baseActivityList:[{logo:'main/pc/img/chengnuo.jpg?1',hidden:true,btnShow:true},{logo:'main/pc/img/indexBanner.jpg?2',hidden:true}],
        redPackActivity:[{logo:'main/pc/img/video.png',hidden:true,imgLoaded:false,video:true}]
        // baseActivityList:[{logo:'main/pc/img/chengnuo.jpg?1',hidden:true,btnShow:true},{logo:'main/pc/img/zhaomu.jpg?1',hidden:true},{logo:'main/pc/img/indexBanner.jpg?2',hidden:true}]
    },
    created:function () {
        var cantonCode=getParam('cantonCode');
        if(cantonCode&&cantonCode.length>2){
            this.cantonCode=cantonCode.substring(0,2);
        }else{
            var province=readCookie('province');
            if(province){
                this.cantonCode=province;
            }else{
                this.cantonCode=returnCitySN['cid'].substring(0,2);
            }
        }
        this.getCurrentActivity();
        this.getAllList();
        // this.getCZList();
        // this.getLYList();
        // this.getTSList();
    },
    mounted:function() {
        var timer=setInterval(function () {
            if(!index.process){
                clearInterval(timer);
                if(index.isLogin){
                    collectingUserBehavior(index.ticketId,'HOMEPAGE');
                }else{
                    collectingUserBehavior('','HOMEPAGE');
                }
            }
        },500)
    },
    methods:{
        goVideo:function () {
            collectingUserBehavior(index.isLogin?index.ticketId:'','INDEX_VEDIO');
            this.showVideo=true;
        },
        know:function () {
            var inquiryGuide=localStorage.inquiryGuide;
            if(inquiryGuide){
                localStorage.inquiryGuide=inquiryGuide+','+localStorage.userId;
            }else{
                localStorage.inquiryGuide=localStorage.userId;
            }
            this.guideShow=false;
        },
        checkLogo:function (logo) {
            var flag=logo.indexOf('zhaomu')>-1;
            if(flag){
                this.showDocument();
            }
        },
        showDocument:function () {
            this.dialogVisible=true;
            var ele=$('.dialogBody');
            var devHieght=340;
            if(ele.length==0){
                var timer=setInterval(function () {
                    var ele=$('.dialogBody');
                    if(ele.length==1){
                        clearInterval(timer);
                        var heights=$(window).height();
                        ele.css('max-height',heights-devHieght+'px');
                    }
                },500)
            }else{
                var heights=$(window).height();
                ele.css('max-height',heights-devHieght+'px');
            }
        },
        hideDocument:function () {
            this.dialogVisible=false;
        },
        publish:function (type) {
            var str=type==1?'WASTERELEASING':'INDEXDISPOSALWASTE';
            if(index.isLogin&&index.entType=='PRODUCTION'){
                collectingUserBehavior(index.ticketId,str);
                window.location='entRelease/entWasteList.htm?ticketId='+index.ticketId;
            }else if(index.isLogin&&index.entType=='FACILITATOR'){
                collectingUserBehavior(index.ticketId,str);
                window.location='facilitator/publish.htm?ticketId='+index.ticketId;
            }else{
                if(index.isLogin&&index.ticketId){
                    collectingUserBehavior(index.ticketId,str);
                }else{
                    collectingUserBehavior('',str);
                }
                window.location='login.jsp';
            }
        },
        changeProvince:function (code) {
            this.cantonCode=code;
            this.getCurrentActivity();
            this.getAllList();
            document.cookie="province="+this.cantonCode+';path=/swp';
        },
        getCurrentActivity:function () {
            if(typeof(activitys)!= 'undefined'&&activitys[this.cantonCode]&&activitys[this.cantonCode].length>0){
                var activityList=this.redPackActivity.concat(activitys[this.cantonCode]);
                for(var i in activityList){
                    if(i==0){
                        continue;
                    }
                    var imgUrl=IMG_VIEW_URL+'&fileID='+activityList[i].subjectFileId;
                    this.getLoader(imgUrl,i).start();
                    activityList[i]['logo']=imgUrl;
                    var longTime=new Date().getTime();
                    var startDate=activityList[i].startDate;
                    var endDate=activityList[i].endDate;
                    if(activityList[i].enrollFee>0){
                        activityList[i].riseEndDateStr=activityList[i].riseEndDate;
                        var riseEndDate=activityList[i].riseEndDate;
                        activityList[i].currentTime=longTime;
                        activityList[i].startDate=startDate;
                        activityList[i].endDate=endDate;
                        activityList[i].riseEndDate=riseEndDate;
                        this.createTimerRise(longTime,riseEndDate,startDate,endDate,i);
                    }else{
                        var date=longTime<startDate||activityList[i].videoResource?startDate:endDate;
                        this.createTimer(date,longTime,i);
                    }
                }
                this.activityList=activityList;
            }else{
                this.activityList=this.redPackActivity;
            }
        },
        getLoader:function(imgUrl,index){
            var loader = new resLoader({
                resources:[imgUrl],
                onStart: function(total) {
                },
                onProgress: function(current, total) {
                    // $('.loading').html('正在加载中...'+Math.floor(current*100/total)+'%');
                },
                onComplete: function(total) {
                    var item=vue.activityList[index];
                    item.imgLoaded=true;
                    Vue.set(vue.activityList,index,item);
                }
            });
            return loader;
        },
        createTimerRise:function (currentTime,riseEndDate,startDate,endDate,index) {
            if(currentTime<riseEndDate){
                setInterval(function () {
                    currentTime+=1000;
                    var item=vue.activityList[index];
                    if(currentTime<riseEndDate){
                        if(item.remainText!='优惠倒计时'){
                            item.remainText='优惠倒计时';
                            Vue.set(vue.activityList,index,item);
                        }
                        vue.ShowCountDown(riseEndDate,currentTime,index);
                    }else{
                        if(item.remainText!='距离开播'){
                            item.currentTime=riseEndDate;
                            item.remainText='距离开播';
                            Vue.set(vue.activityList,index,item);
                        }
                        vue.ShowCountDown(startDate,currentTime,index);
                    }
                },1000)
            }else if(currentTime<startDate){
                setInterval(function () {
                    var item=vue.activityList[index];
                    if(item.remainText!='距离开播'){
                        item.currentTime=riseEndDate;
                        item.remainText='距离开播';
                        Vue.set(vue.activityList,index,item);
                    }
                    currentTime+=1000;
                    vue.ShowCountDown(startDate,currentTime,index);
                },1000)
            }else{
                var timer=setInterval(function () {
                    if(vue.activityList.length>0){
                        var item=vue.activityList[index];
                        item.currentTime=riseEndDate;
                        item.remainTimeText='已开始';
                        Vue.set(vue.activityList,index,item);
                        clearInterval(timer);
                    }
                },1000)
            }
        },
        createTimer:function (endDate,longTime,index) {
            setInterval(function(){
                longTime+=1000;
                vue.ShowCountDown(endDate,longTime,index);
            },1000)
        },
        ShowCountDown:function(dateFinal,dateNow,index){
            var dateSub = dateFinal - dateNow;  //计算差值，单位毫秒
            if(dateSub<0){
                var item=this.activityList[index];
                if(item){
                    item.remainTimeText='已开始';
                    Vue.set(this.activityList,index,item);
                }
                return;
            }
            var day , hour , minute, second ,dayBase , hourBase, minuteBase, secondBase;
            day = hour = minute = second = dayBase = hourBase = minuteBase = secondBase = 0;  //初始化各个数值
            var timeHtml = '';
            timeHtml += " ";
            dayBase = 24 * 60 * 60 * 1000;  //计算天数的基数，单位毫秒。1天等于24*60*60*1000毫秒
            hourBase = 60 * 60 * 1000;  //计算小时的基数，单位毫秒。1小时等于60*60*1000毫秒
            minuteBase = 60 * 1000;  //计算分钟的基数，单位毫秒。1分钟等于60*1000毫秒
            secondBase = 1000;  //计算秒钟的基数，单位毫秒。1秒钟等于1000毫秒
            day = Math.floor(dateSub / dayBase);  //计算天数，并取下限值。如 5.9天 = 5天
            hour = Math.floor(dateSub % dayBase / hourBase);  //计算小时，并取下限值。如 20.59小时 = 20小时
            minute = Math.floor(dateSub % dayBase % hourBase / minuteBase);  //计算分钟，并取下限值。如 20.59分钟 = 20分钟
            second = Math.floor(dateSub % dayBase % hourBase % minuteBase / secondBase);  //计算秒钟，并取下限值。如 20.59秒 = 20秒
            //当天数小于等于0时，就不用显示
            if(day <= 0){
                timeHtml += this.toDouble(hour) + ':' + this.toDouble(minute) + ':' + this.toDouble(second) ;
            }else{
                timeHtml += day + '天 : ' + this.toDouble(hour) + ':' + this.toDouble(minute) + ':' + this.toDouble(second) ;
            }
            var item=this.activityList[index];
            if(item){
                item.remainTimeText=timeHtml;
                Vue.set(this.activityList,index,item);
            }
            // $('.time b').html(timeHtml);
        },
        toDouble:function(num){
            if(num < 10){
                return '0'+ num;
            }else{
                return '' + num;
            }
        },
        goLive:function (url,activityName) {
            if(index.isLogin&&index.ticketId){
                collectingUserBehavior(index.ticketId,'VIEWACTIVITY',activityName);
            }else{
                collectingUserBehavior('','VIEWACTIVITY',activityName);
            }
            var ticketStr=(index.ticketId?('?ticketId='+index.ticketId):'');
            window.location=url+ticketStr;
        },
        goActivityDetail:function (activityId,activityName) {
            if(index.isLogin&&index.ticketId){
                collectingUserBehavior(index.ticketId,'VIEWACTIVITY',activityName);
            }else{
                collectingUserBehavior('','VIEWACTIVITY',activityName);
            }
            var ticketStr=(index.ticketId?('&ticketId='+index.ticketId):'');
            window.location='main/pc/view/wasteActivityDetail.html?activityId='+activityId+ticketStr;
        },
        goEnrollFee:function (activityId,activityName) {
            if(index.isLogin&&index.ticketId){
                collectingUserBehavior(index.ticketId,'VIEWACTIVITY',activityName);
            }else{
                collectingUserBehavior('','VIEWACTIVITY',activityName);
            }
            var ticketStr=(index.ticketId?('&ticketId='+index.ticketId):'');
            window.location='main/pc/view/feeActivityDetail.html?activityId='+activityId+ticketStr;
        },
        querySearchAsync:function(queryString, cb) {
            if(queryString.length<2){
                cb([]);
                return;
            }
            if(isNaN(queryString.substring(0, 2))) {
                $.ajax({
                    url: "enterprise/getWasteNameDropDownList.htm",
                    data: {
                        'keyword': queryString,
                    },
                    type: "POST",
                    dataType: 'json',
                    async: true,
                    success: function(data) {
                        if(data&&data.value && data.value.length > 0) {
                            var results=[];
                            for(var i = 0; i < data.value.length; i++) {
                                if(i<10){
                                    var obj={
                                        value:data.value[i]['name']
                                    };
                                    results.push(obj);
                                }else{
                                    break;
                                }
                            }
                            cb(results);
                        }
                    }
                });
            } else {
                $.ajax({
                    url: "enterprise/getCodeWasteDropDownList.htm",
                    data: {
                        'keyword': queryString,
                    },
                    type: "POST",
                    dataType: 'json',
                    async: true,
                    success: function(data) {
                        if(data&&data.value && data.value.length > 0) {
                            var results=[];
                            for(var i = 0; i < data.value.length; i++) {
                                if(i<10){
                                    var obj={
                                        value:data.value[i]['code']
                                    };
                                    results.push(obj);
                                }else{
                                    break;
                                }
                            }
                            cb(results);
                        }
                    }
                });
            }
        },
        search:function () {
            if(index.isLogin&&index.ticketId){
                collectingUserBehavior(index.ticketId,'HOMEPAGESEARCH',this.keyWord);
            }else{
                collectingUserBehavior('','HOMEPAGESEARCH',this.keyWord);
            }
            // var codetype = isNaN(this.keyWord.substring(0, 2)) ? "1" : "0";
            /*1是企业名称，0是八位码*/
            if(index.ticketId != null) {
                window.location = 'main/pc/view/company.html?cname=' +this.keyWord ;
            } else {
                window.location = 'main/pc/view/company.html?cname=' +this.keyWord;
            }
        },
        changeCZArea:function () {
            this.czPage=1;
            this.getCZList();
        },
        changeLYArea:function () {
            this.lyPage=1;
            this.getLYList();
        },
        changeTSArea:function () {
            this.tsPage=1;
            this.getTSList();
        },
        getCZRefresh:function () {
            this.czPage+=1;
            if(this.czPage==this.czMaxPage+1){
                this.czPage=1;
            }
            this.getCZList();
        },
        getLYRefresh:function () {
            this.lyPage+=1;
            if(this.lyPage==this.lyMaxPage+1){
                this.lyPage=1;
            }
            this.getLYList();
        },
        getTSRefresh:function () {
            this.tsPage+=1;
            if(this.tsPage==this.tsMaxPage+1){
                this.tsPage=1;
            }
            this.getTSList();
        },
        getCZList:function () {
            var param={
                'section': 'DISPOSITION',
                'page':this.czPage
            };
            if(this.czArea=='native'){
                param.cantonCode=this.cantonCode;
            }else{
                param.national='1';
            }
            $.ajax({
                url: "enterpriseConfiguration/listEnterpriseInfoBySection",
                data: JSON.stringify(param),
                contentType:'application/json',
                type: "POST",
                dataType: 'json',
                async: true,
                success: function(result) {
                    if(result.status==1) {
                        var czList=[];
                        for(var i in result.data.enterpriseModelList){
                            var obj=result.data.enterpriseModelList[i];
                            if(i==0&&obj.dispositionType&&obj.dispositionType.length>36){
                                obj.dispositionType=obj.dispositionType.substring(0,36)+'...';
                            }
                            if(obj['fileId']){
                                var imgUrl=IMGVIEWURL+obj['fileId'];
                                obj['logo']=imgUrl;
                            }else{
                                obj['logo']='main/pc/img/ent_default_short.png';
                            }
                            obj['imgLoaded']=false;
                            vue.getLoaderEnt(obj['logo'],i,'czList').start();
                            czList.push(obj);
                        }
                        vue.czMaxPage=result.data.maxPage;
                        vue.czList=czList;
                    }
                }
            });
        },
        getLYList:function () {
            var param={
                'section': 'RECYCLING',
                'page':this.lyPage
            };
            if(this.lyArea=='native'){
                param.cantonCode=this.cantonCode;
            }else{
                param.national='1';
            }
            $.ajax({
                url: "enterpriseConfiguration/listEnterpriseInfoBySection",
                data: JSON.stringify(param),
                contentType:'application/json',
                type: "POST",
                dataType: 'json',
                async: true,
                success: function(result) {
                    if(result.status==1) {
                        var czList=[];
                        for(var i in result.data.enterpriseModelList){
                            var obj=result.data.enterpriseModelList[i];
                            if(obj['fileId']){
                                var imgUrl=IMGVIEWURL+obj['fileId'];
                                obj['logo']=imgUrl;
                            }else{
                                obj['logo']='main/pc/img/ent_default_short.png';
                            }
                            obj['imgLoaded']=false;
                            vue.getLoaderEnt(obj['logo'],i,'lyList').start();
                            czList.push(obj);
                        }
                        vue.lyMaxPage=result.data.maxPage;
                        vue.lyList=czList;
                    }
                }
            });
        },
        getTSList:function () {
            var param={
                'section': 'SPECIALCATEGORY',
                'page':this.tsPage
            };
            if(this.tsArea=='native'){
                param.cantonCode=this.cantonCode;
            }else{
                param.national='1';
            }
            $.ajax({
                url: "enterpriseConfiguration/listEnterpriseInfoBySection",
                data: JSON.stringify(param),
                contentType:'application/json',
                type: "POST",
                dataType: 'json',
                async: true,
                success: function(result) {
                    if(result.status==1) {
                        var czList=[];
                        for(var i in result.data.enterpriseModelList){
                            var obj=result.data.enterpriseModelList[i];
                            if(obj['fileId']){
                                var imgUrl=IMGVIEWURL+obj['fileId'];
                                obj['logo']=imgUrl;
                            }else{
                                obj['logo']='main/pc/img/ent_default_short.png';
                            }
                            obj['imgLoaded']=false;
                            vue.getLoaderEnt(obj['logo'],i,'tsList').start();
                            czList.push(obj);
                        }
                        vue.tsMaxPage=result.data.maxPage;
                        vue.tsList=czList;
                    }
                }
            });
        },
        getAllList:function () {
            var param={
                'page':1
            };
            if(this.tsArea=='native'){
                param.cantonCode=this.cantonCode;
            }else{
                param.national='1';
            }
            this.listLoading=false;
            if(typeof(entList)!='undefined'&&entList[this.cantonCode]){
                var result=entList[this.cantonCode];
                this.resultAction(result);
            }else{
                this.czMaxPage=1;
                this.lyMaxPage=1;
                this.tsMaxPage=1;
                this.czList=[];
                this.lyList=[];
                this.tsList=[];
            }
        },
        resultAction:function (result) {
            var czList=[];
            for(var i in result.dispositionEnterpriseModelList){
                var obj=result.dispositionEnterpriseModelList[i];
                if(i==0&&obj.dispositionType&&obj.dispositionType.length>36){
                    obj.dispositionType=obj.dispositionType.substring(0,36)+'...';
                }
                if(obj['fileId']){
                    var imgUrl=IMGVIEWURL+obj['fileId'];
                    obj['logo']=imgUrl;
                }else{
                    obj['logo']='main/pc/img/ent_default_short.png';
                }
                obj['imgLoaded']=false;
                this.getLoaderEnt(obj['logo'],i,'czList').start();
                czList.push(obj);
            }
            this.czMaxPage=result.dispositionMaxPage;
            this.czList=czList;

            var lyList=[];
            for(var i in result.recyclingEnterpriseModelList){
                var obj=result.recyclingEnterpriseModelList[i];
                if(obj['fileId']){
                    var imgUrl=IMGVIEWURL+obj['fileId'];
                    obj['logo']=imgUrl;
                }else{
                    obj['logo']='main/pc/img/ent_default_short.png';
                }
                obj['imgLoaded']=false;
                this.getLoaderEnt(obj['logo'],i,'lyList').start();
                lyList.push(obj);
            }
            this.lyMaxPage=result.recyclingMaxPage;
            this.lyList=lyList;

            var tsList=[];
            for(var i in result.specialCategoryEnterpriseModelList){
                var obj=result.specialCategoryEnterpriseModelList[i];
                if(obj['fileId']){
                    var imgUrl=IMGVIEWURL+obj['fileId'];
                    obj['logo']=imgUrl;
                }else{
                    obj['logo']='main/pc/img/ent_default_short.png';
                }
                obj['imgLoaded']=false;
                this.getLoaderEnt(obj['logo'],i,'tsList').start();
                tsList.push(obj);
            }
            this.tsMaxPage=result.specialCategoryMaxPage;
            this.tsList=tsList;
        },
        getLoaderEnt:function(imgUrl,index,type){
            var loader = new resLoader({
                resources:[imgUrl],
                onStart: function(total) {
                },
                onProgress: function(current, total) {
                    // $('.loading').html('正在加载中...'+Math.floor(current*100/total)+'%');
                },
                onComplete: function(total) {
                    var list=[];
                    switch (type){
                        case 'czList':
                            list=vue.czList;
                            break;
                        case 'lyList':
                            list=vue.lyList;
                            break;
                        case 'tsList':
                            list=vue.tsList;
                            break;
                    }
                    var item=list[index];
                    if(item){
                        item.imgLoaded=true;
                        Vue.set(list,index,item);
                    }
                }
            });
            return loader;
        },
        goEntDetail:function (entId,entName) {
            if(index.isLogin&&index.ticketId){
                collectingUserBehavior(index.ticketId,'VIEWINDEXDISENT',entName,'PC首页');
            }else{
                collectingUserBehavior('','VIEWINDEXDISENT',entName,'PC首页');
            }
            var ticketIdStr=index.ticketId?('&ticketId='+index.ticketId):'';
            window.location.href='main/pc/view/czDetail.html?entId='+entId+ticketIdStr+'&breadName=首页';
        },
        handleSelect:function(item) {
        }
    }
});
var yfw = {
    onlineTalk:function () {
        var ticketIdStr=index.ticketId ? ("?ticketId=" + index.ticketId) : "";
        if(index.isLogin){
            window.location='main/pc/view/online.html'+ticketIdStr;
            return;
        }
        $.ajax({
            url:'yunxin/getEnableYunXinAccount',
            type:'post',
            success:function (result) {
                if(result.data){
                    setCookie('uid',result.data.accId);
                    setCookie('sdktoken',result.data.token);
                    setCookie('nickName',escape('游客'));
                    window.location='main/pc/view/online.html'+ticketIdStr;
                }else{
                    vue.$message({
                        dangerouslyUseHTMLString: true,
                        message: '<div style="margin-bottom: 10px;color: #1171d1;font-size: 16px">小易正忙,您可以稍后再试</div><div>先注册再咨询会更快哦</div>'
                    });
                }
            }
        });
    },
    goSuggest:function () {
        window.location='main/pc/view/suggest.html'+(index.ticketId ? ("?ticketId=" + index.ticketId) : "");
    },
};

