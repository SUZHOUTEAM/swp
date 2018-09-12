<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
    <jsp:include page="/common/webCommon/user_top.jsp" flush="true">
        <jsp:param name="title" value="资源池"/>
    </jsp:include>
    <jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
        <jsp:param name="menuId" value="${breadcrumbName=='完善信息'?'#myCZEnterprise':'#myIndex'}"/>
    </jsp:include>
<%--</c:if>--%>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterprise/detail.css?1">
<link rel="stylesheet" href="<%=appPath%>/css/enterprise/timeline.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterprise/enterprise_evaluation.css">
<script type="text/javascript" src="<%=appPath%>/thirdparty/city-picker/js/city-picker.data.js"></script>
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="goBack();">${breadcrumbName.split("-")[0]}</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">${breadcrumbName=='完善信息'?'预览':(enterprise.entName.concat('--企业详情'))}</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div class="panel panel-body" style="padding-bottom: 0">
        <div id="app">
            <template>
                <el-dialog
                        title="查看图片"
                        :visible.sync="dialogVisible"
                        :modal-append-to-body="false"
                        width="90%">
                    <img :src="largeImg"/>
                </el-dialog>
                <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
                    <el-tab-pane label="概况" name="first">
                        <div class="top">
                            <el-carousel :autoplay="false" height="380px" v-show="imgList.length>0">
                                <el-carousel-item v-for="(item,index) in imgList" :key="index">
                                    <img :src="item"/>
                                </el-carousel-item>
                            </el-carousel>
                            <div class="enterName" :class="{'noBanner':imgList.length==0}">
                                ${enterprise.entName}
                            </div>
                            <el-row class="da" :class="{'noBanner':imgList.length==0}">
                                <el-col :span="18" style="padding-left: 37px;"><i class="publisharea"></i>地址：${enterprise.entAddress} </el-col>
                                <el-col :span="6" style="padding-right: 37px;text-align: right">
                                    <el-button type="primary" @click="contact()"><img src="<%=appPath%>/main/pc/img/source/concact_icon.png">  联系TA</el-button>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="desc" >
                            <p class="desc_title" v-if="entDesc!=null">企业简介</p>
                            <p class="desc_content"  style="text-indent: 2em">
                                {{entDesc}}
                            </p>
                            <p class="desc_title" style="margin-top: 30px" v-if="partnerList.length != 0">合作伙伴</p>
                            <div class="desc_content" style="padding: 10px 0" v-if="partnerList.length != 0">
                                <div class="entItem" v-for="(item,index) in partnerList">
                                    <div class="entItem_logo"><img :src="item.imgSrc"/></div>
                                    <div class="entItem_name">{{item.customerName}}</div>
                                </div>
                            </div>

                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="评价" name="six">
                        <div class="panel panel-body" style="box-shadow: none">
                            <section id="valuationSection" >
                                <div class="row evaluationTitle" v-if="entEvaluates.length>0">
                                    <div class="col-xs-2">
                                        <span class="integratedEvaluation">企业综合评价：</span>
                                    </div>
                                    <div class="col-xs-10">
                                        <span class="evaluationLevel clickable" :class="{'active':score==''}" @click="changeEvaluateType(0)">全部评价（{{entEvaluates.length}}条）</span>
                                        <span class="evaluationLevel clickable"  :class="{'active':score==5}" @click="changeEvaluateType(5)">好评（{{commentCount['5']}}条）</span>
                                        <span class="evaluationLevel clickable"  :class="{'active':score==3}" @click="changeEvaluateType(3)">中评（{{commentCount['3']}}条）</span>
                                        <span class="evaluationLevel clickable" :class="{'active':score==1}"  @click="changeEvaluateType(1)">差评（{{commentCount['1']}}条）</span>
                                    </div>
                                </div>
                                <div class="row evaluationDeatil" v-for="(item,index) in showEntEvaluates">
                                    <div class="col-xs-6 col-sm-2"><span>{{item.createBy}}</span></div>
                                    <div class="col-xs-6 col-sm-10"><p>综合评价：{{COMMENT[item.score]}}</p><p>{{item.comment}}</p><p>{{item.createTime}}</p></div>
                                </div>
                                <div class="prompt text-center" v-if="showEntEvaluates.length==0" style="margin-top: 20px">
                                    <span>暂无评价</span>
                                </div>
                            </section>
                        </div>
                    </el-tab-pane>
                </el-tabs>
            </template>
        </div>

        <!-- 先引入 Vue -->
        <script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
        <%--<!-- 引入组件库 -->--%>
        <script src="<%=appPath%>/main/common/elementui/index.js"></script>
        <script src="<%=appPath %>/app/js/constants.js"></script>
        <script src="<%=appPath %>/main/common/upload/upload.js"></script>
        <script src="<%=appPath %>/main/pc/js/data.js"></script>
        <script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
        <script>
            var vue = new Vue({
                el: '#app',
                data:{
                    activeName: 'first',
                    cactiveName:'cfirst',
                    imgList:[],
                    videoList:[],
                    pfList:[],
                    partnerList:[],
                    attachList:[],
                    contractList:[],
                    licenceImgStr:"",
                    approvedQuantity:null,
                    licence:{},
                    saleDesc:"",
                    paymentRule:"",
                    otherDesc:"",
                    entDesc:"",
                    ryList:[],
                    dialogVisible:false,
					hasSalesInfo:false,
                    entId:"",
                    largeImg:'',
                    entEvaluates:[],
                    COMMENT:COMMENT,
                    score:'',
                    showEntEvaluates:[],
                    commentCount:{'5':0,'3':0,'1':0},
                    licenseDetail:[]
                },
                mounted:function () {
                    var heights=document.body.clientHeight;
                    $('#app >.el-tabs >.el-tabs__content').height(heights-240+'px');
                    window.onresize=function () {
                        var heights=document.body.clientHeight;
                        $('#app >.el-tabs >.el-tabs__content').height(heights-240+'px');
                    }

                },
                created:function(){
//                    this.getList();
//                    this.entId = getParam('enterpriseId');
//                    this.getAttachList();
//                    this.getContractList();
//                    this.getBanner();
//                    this.getLicenceDetail();
                },
                methods:{
                    handleClick:function(tab, event) {
                        console.log(tab, event);
                        if(tab.index!="3"){
                            var play1=$('#video_content video');
                            var play2=$('#video_content2 video');
                            if(play1.length>0){
                                play1[0].pause();
                            }
                            if(play2.length>0){
                                play2[0].pause();
                            }
                        }
                    },
                    getList: function(){
                        var _this=this;
                        ajax({
                            url:'/sysEnterpriseBase/getEnterpriseSummaryInfo.htm',
                            dataType:'json',
                            data:{
                                entId:getParam('enterpriseId')
                            },
                            success:function (result) {
                                console.log(result);
                                _this.entDesc  = result.data.summary;
                                _this.saleDesc  = result.data.salesNote;
                                _this.approvedQuantity = result.data.approvedQuantity;
                                _this.otherDesc = result.data.otherNote;
                                _this.paymentRule = result.data.paymentRule;
                                _this.licence = result.data.operationLicence;
                                _this.entEvaluates=result.data.entEvaluates;
                                _this.showEntEvaluates=result.data.entEvaluates;
                                for(var i in result.data.entEvaluates){
                                    var item=result.data.entEvaluates[i];
                                    if(_this.commentCount[item.score]){
                                        _this.commentCount[item.score]+=1;
                                    }else{
                                        _this. commentCount[item.score]=1;
                                    }
                                }
                                if(result.data.licenceFileId){
                                    _this.licenceImgStr = IMG_VIEW_URL+'&fileID='+result.data.licenceFileId;
                                }
                                for(var index in result.data.customerList) {
                                    var partner = result.data.customerList[index];
                                    var imgUrl = IMG_VIEW_URL+'&fileID='+partner.fileId;
                                    partner.imgSrc = imgUrl;
                                    _this.partnerList.push(partner);
                                }

                                var map = {},dest = [];
                                for(var index in result.data.entGloryList){
                                    var d = result.data.entGloryList[index].getTime;
                                    var year ;
                                    if(d!=null){
                                        year = d.split("-")[0];
                                    }
                                    if(!map[year]) {
                                        dest.push({
                                            year: year ,
                                            content: [result.data.entGloryList[index]]
                                        });
                                        map[year] = result.data.entGloryList[index];
                                    }else{
                                        for(var j = 0; j < dest.length; j++){
                                            var dj = dest[j];
                                            if(dj.year == year){
                                                dj.content.push(result.data.entGloryList[index]);
                                                break;
                                            }
                                        }
                                    }
                                }
                                _this.ryList = dest;
                                vue.initTimeLine(vue.ryList);
								if(_this.saleDesc==null && _this.paymentRule==null && _this.otherDesc==null && _this.attachList.length== 0&& _this.contractList.length== 0){
                                    _this.hasSalesInfo = true;
								}

                            }
                        })
                    },
                    getLicenceDetail:function () {
                        $.ajax({
                            url: "/swp/myenterprise/getHomePageEnterpriseInfoDetail",
                            data: {
                                'enterpriseId': this.entId,
                            },
                            type: "POST",
                            dataType: 'json',
                            async: true,
                            success: function (data) {
                                if(data.status==1&&data.data.operationLicenceItemVo&&data.data.operationLicenceItemVo.length>0){
                                    vue.licenseDetail=data.data.operationLicenceItemVo;
                                }
                            }
                        });
                    },
                    changeEvaluateType:function (score) {
                        this.score=score;
                        if(!score){
                            this.showEntEvaluates=this.entEvaluates;
                            return;
                        }
                        var arr=[];
                        for(var i in this.entEvaluates){
                            if(this.entEvaluates[i]['score']==score){
                                arr.push(this.entEvaluates[i]);
                            }
                        }
                        this.showEntEvaluates=arr;
                    },
                    getBanner:function(){
                        var list =upload.getImgListByBusinessCode(this.entId+'_banner');
                        for(var i in list){
                            list[i].path = IMG_PREV+'/download?fileID='+list[i]['fileID'];
                            var imgSrc =  IMG_VIEW_URL+'&businessCode='+this.entId+'&fileID='+list[i]['fileID'];
                            this.imgList.push(imgSrc);
                        }
                    },
                    getAttachList:function () {
                        var list=upload.getImgListByBusinessCode(this.entId+'_attach');
                        for(var i in list){
                            list[i].logo=IMG_VIEW_URL+'&fileID='+list[i]['fileID'];
                            list[i].name=list[i]['fileName'];
                            list[i].path = IMG_PREV+'/download?fileID='+list[i]['fileID'];

                        }
                        this.attachList=list;
                    },
                    getContractList:function () {
                        var list=upload.getImgListByBusinessCode(this.entId+'_contract');
                        for(var i in list){
                            list[i].logo=IMG_VIEW_URL+'&fileID='+list[i]['fileID'];
                            list[i].name=list[i]['fileName'];
                            list[i].path = IMG_PREV+'/download?fileID='+list[i]['fileID'];
                        }
                        this.contractList=list;
                    },
                    download:function (path) {
                        window.location=path;
                    },
                    showLargeImg:function (imgUrl) {
                        this.largeImg=imgUrl;
                        this.dialogVisible=true;
                    },
                    scrollTo:function (id) {
                        document.getElementById(id).scrollIntoView(true);
                    },
                    contact:function () {
                        getEnterpriseContacts('${enterprise.entId}','${enterprise.entName}','服务商企业详情');
                    },
                    toTop:function () {
                        document.getElementById('third').scrollIntoView(true);
                    },
                    play:function () {
                        var url='<%=appPath%>/main/pc/file/oceans.mp4';
                        var str='<video src="'+url+'" width="100%"  height="auto" class="video-js" autoplay="true" controls>'+
                            '<object>'+
                            '<embed width="100%" height="auto" showcontrols="1" autostart="1" src="'+url+'" ></embed>'+
                            '</object>'+
                            '</video>';
                        $('#video_content').html(str);
                    },
                    play2:function () {
                        var url='<%=appPath%>/main/pc/file/oceans.mp4';
                        var str='<video src="'+url+'" width="100%"  height="auto" class="video-js" autoplay="true" controls>'+
                            '<object>'+
                            '<embed width="100%" height="auto" showcontrols="1" autostart="1" src="'+url+'" ></embed>'+
                            '</object>'+
                            '</video>';
                        $('#video_content2').html(str);
                    },
                    initTimeLine:function(ryList) {
//                        ryList=ryList1?ryList1:ryList;
                        var str = '';
                        var flag = true;
                        for (var k in this.ryList) {
                            var year = ryList[k]['year'];
                            str += '<dt><i class="el-icon-time"></i><span class="time">' + year + '年</span></dt>';
                            for (var i = 0; i < ryList[k]['content'].length; i++) {
                                var obj = ryList[k]['content'][i];
                                var getTime = obj['getTime'].substring(0,10);
                                var imgUrl=IMG_VIEW_URL+'&fileID='+obj['fileId'];
                                str += '<dd class="pos-' + (flag ? 'right' : 'left') + ' clearfix" id="' + obj['id'] + '">' +
                                    '<div class="circ"><i class="el-icon-time"></i></div>' +
                                    '<div class="time">' + getTime + '</div>' +
                                    '<div class="events">' +
                                    '<div class="events-header">' + obj['gloryType'] + '</div>' +
                                    '<div class="events-body">' + obj['gloryTypeValue'] + '</div>' +
                                    '<div class="events-footer">' +
                                    '<img src="'+imgUrl+'" onclick="vue.showLargeImg(\''+imgUrl+'\')"/>' +
                                    '</div>' +
                                    '</div>' +
                                    '</dd>';
                                flag = !flag;
                            }
                        }
                        $('#mainVivaTimeline dl').html(str);
                    }
                }
            });
        </script>
    </div>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true"/>

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function () {
        var breadcrumbName="${breadcrumbName}";
        if(breadcrumbName.indexOf('-')>-1){
            $('#enterpriseEvalution').load("<%=appPath %>/enterprise/loadEnterpriseEvalution.htm?ticketId=<%=ticketId %>&enterpriseId=${enterprise.entId}");
        }else{
            var rs = window.globalInit();
            rs.done(function () {
                $('#enterpriseEvalution').load("<%=appPath %>/enterprise/loadEnterpriseEvalution.htm?ticketId=<%=ticketId %>&enterpriseId=${enterprise.entId}");
            });
        }
    });
    function goBack() {
        var index = getParam("index");
        if (index == "true") {
            document.cookie = "index=true;path=<%=appPath%>/wastecircle/";
        }
        window.history.go(-1);
    }

    <%--function initTimeLine(ryList1){--%>
        <%--var str='';--%>
        <%--var flag=true;--%>
        <%--for(var k in ryList1){--%>
            <%--var year=ryList[k]['year'];--%>
            <%--str+='<dt><i class="el-icon-time"></i><span class="time">'+year+'年</span></dt>';--%>
            <%--for(var i=0;i<ryList[k]['content'].length;i++){--%>
                <%--var obj=ryList[k]['content'][i];--%>
                <%--str+='<dd class="pos-'+(flag?'right':'left')+' clearfix" id="'+obj['id']+'">'+--%>
                    <%--'<div class="circ"><i class="el-icon-time"></i></div>'+--%>
                    <%--'<div class="time">'+obj['time']+'</div>'+--%>
                    <%--'<div class="events">'+--%>
                    <%--'<div class="events-header">'+obj['name']+'</div>'+--%>
                    <%--'<div class="events-body">'+obj['description']+'</div>'+--%>
                    <%--'<div class="events-footer">'+--%>
                    <%--'<img src="<%=appPath%>/app/img/shu.png" />'+--%>
                    <%--'</div>'+--%>
                    <%--'</div>'+--%>
                    <%--'</dd>';--%>
                <%--flag=!flag;--%>
            <%--}--%>
        <%--}--%>
        <%--$('#mainVivaTimeline dl').html(str);--%>
    <%--}--%>
</script>
</body>