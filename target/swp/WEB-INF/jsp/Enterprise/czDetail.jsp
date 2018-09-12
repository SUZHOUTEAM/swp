<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<c:if test="${breadcrumbName.contains(\"-\")}">
    <jsp:include page="/common/webCommon/user_top.jsp" flush="true">
        <jsp:param name="title" value="资源池"/>
    </jsp:include>
    <jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
        <jsp:param name="menuId" value="#${breadcrumbName.split(\"-\")[1]}"/>
    </jsp:include>
</c:if>
<c:if test="${!breadcrumbName.contains(\"-\")}">
    <jsp:include page="/common/webCommon/user_top.jsp" flush="true">
        <jsp:param name="title" value="资源池"/>
    </jsp:include>
    <jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
        <jsp:param name="menuId" value="${breadcrumbName=='完善信息'?'#myCZEnterprise':'#myIndex'}"/>
    </jsp:include>
</c:if>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterprise/detail.css?8">
<link rel="stylesheet" href="<%=appPath%>/css/enterprise/timeline.css?1">
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
    <div class="panel panel-body">
        <div class="btns">
            <a href="javascript:history.go(-1);">返回</a>
            <%--<a href="<%=appPath%>/enterprise/czEdit.htm?ticketId=<%=ticketId%>">去完善信息</a>--%>
        </div>
        <div id="app">
            <template>
                <el-dialog
                        title="查看许可证"
                        :visible.sync="dialogVisible"
                        :modal-append-to-body="false"
                        width="90%">
                    <img :src="largeImg"/>
                </el-dialog>
                <el-dialog
                        title="查看图片"
                        :visible.sync="dialogVisible2"
                        :modal-append-to-body="false"
                        width="90%">
                    <el-carousel height="600px" :autoplay="false" :initial-index="imgIndex" v-if="dialogVisible2">
                        <el-carousel-item  v-for="(item,index) in imgList" :key="index">
                            <img :src="item"/>
                        </el-carousel-item>
                    </el-carousel>
                </el-dialog>
                <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
                    <el-tab-pane label="概况" name="first">
                        <div class="top">
                            <el-carousel :autoplay="false" height="380px" v-show="imgList&&imgList.length>0">
                                <el-carousel-item v-for="(item,index) in imgList" :key="index" :style="'background:url('+item +') center center/auto 100%  no-repeat'">
                                    <%--<img :src="item"  @click="showBannerImg(index)"/>--%>
                                </el-carousel-item>
                            </el-carousel>
                            <div class="enterName" :class="{'noBanner':imgList&&imgList.length==0}">
                                ${enterprise.entName}
                            </div>
                            <el-row class="da" :class="{'noBanner':imgList&&imgList.length==0}">
                                <el-col :span="18" style="padding-left: 37px;"><i class="publisharea"></i>地址：${enterprise.entAddress} </el-col>
                                <%--<el-col :span="6" style="padding-right: 37px;text-align: right">--%>
                                    <%--<el-button type="primary" @click="contact()"><img src="http://yifeiwang.com/img/source/concact_icon.png">  联系TA</el-button>--%>
                                <%--</el-col>--%>
                            </el-row>
                        </div>
                        <div class="desc" >
                            <p class="desc_title" v-if="entDesc!=null">企业简介</p>
                            <p class="desc_content"  style="text-indent: 2em" v-html="entDesc">
                            </p>
                            <p class="desc_title" style="margin-top: 30px" v-if="partnerList&&partnerList.length != 0">合作伙伴</p>
                            <div class="desc_content" style="padding: 10px 0" v-if="partnerList&&partnerList.length != 0">
                                <div class="entItem" v-for="(item,index) in partnerList">
                                    <div class="entItem_logo"><img :src="item.imgSrc"/></div>
                                    <div class="entItem_name">{{item.customerName}}</div>
                                </div>
                            </div>

                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="经营许可证" name="second">
                        <el-row class="license" v-if="licence!=null">
                            <el-col :span="10">
                                <div class="licenseTableDiv">
                                    <table style="width: 100%">
                                        <tr>
                                            <td colspan="2" style="font-size: 14px;color: #222;">危险废物经营许可证</td>
                                        </tr>
                                        <tr>
                                            <td>许可证编号：</td>
                                            <td class="text">{{licence?licence.licence_no:'--'}}</td>
                                        </tr>
                                        <tr>
                                            <td>有效期：</td>
                                            <td class="text">{{licence.start_date?licence.start_date.substring(0,10):''}}~{{licence.end_date?licence.end_date.substring(0,10):''}}</td>
                                        </tr>
                                        <tr>
                                            <td>年许可量：</td>
                                            <td class="text">{{approvedQuantity}}吨</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" style="padding: 20px 10px" >
                                                <img v-if="licenceImgStr" :src="licenceImgStr" @click="showLargeImg(licenceImgStr)"/>
                                                <span v-else>暂无图片信息</span>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </el-col>
                            <el-col :span="14" style="padding: 20px" v-show ="licenceImgStr!=''">
                                <img v-if="licenceImgStr" :src="licenceImgStr" @click="showLargeImg(licenceImgStr)"/>
                                <span class="tip">危险废物经营许可证副本照片（点击查看大图）</span>
                            </el-col>
                        </el-row>
						<el-row class="license  text-center" style="margin-top: 28px" v-if="licence==null">
							<span>暂无有效许可证</span>
						</el-row>
                        <div class="info_title">许可证详情</div>
                        <div class="info_content">
                            <template v-for="(item,index) in licenseDetail">
                                <dl>
                                    <dd class="dtitle">经营方式</dd>
                                    <dd class="dcontent">{{item.dispositionType}}</dd>
                                    <dd class="dtitle">年核准量</dd>
                                    <dd class="dcontent">{{item.approved_quantity+'吨'}}</dd>
                                </dl>
                                <dl class="last_dl">
                                    <dd class="dtitle">核准名称/核准类别</dd>
                                    <dd class="dcontent" id="name_code">
                                        <!--{{parselListItemVo(item.listWasteTypeVo)}}-->
                                        <div class="dcontent_container codeType" v-for="(o,index) in item.listWasteTypeVo">
                                            <span>{{o.wasteTypeCode+'/'+o.wasteTypeValue}}</span>
                                            <span :title="o.listWasteVo[0]">（{{o.listWasteVo[0]}}）</span>
                                        </div>
                                    </dd>
                                </dl>
                            </template>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="资质与荣誉" name="third" id="third">
                        <el-row v-show="ryList&&ryList.length>0">
                            <el-col :span="18">
                                <div style="background-color: #ffffff">
                                    <div class="VivaTimeline" id="mainVivaTimeline">
                                        <dl></dl>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="VivaTimeline VivaTimelineBar">
                                    <dl v-for="item1 in ryList">
                                        <dd class="pos-left clearfix"  v-for="item in item1.content" @click="scrollTo(item.id)">
                                            <div class="circ"><i class="fa fa-circle"></i></div>
                                            <div class="time">{{item.gloryTypeValue}}</div>
                                            <div class="events">
                                            </div>
                                        </dd>
                                    </dl>
                                    <div class="toTop" @click="toTop()"><img src="http://yifeiwang.com/img/source/top.png"/></div>
                                </div>
                            </el-col>
                        </el-row>
						<el-row class=" text-center" style="margin-top: 28px" v-if="!ryList||ryList.length==0">
							<span>暂无相关信息</span>
						</el-row>
                    </el-tab-pane>
                    <el-tab-pane label="销售及合同" name="five">
						<el-row class="text-center" style="margin-top: 28px" v-if = "hasSalesInfo">
							<span>暂无相关信息</span>
						</el-row>
                        <div class="desc" v-if="saleDesc">
                            <p class="desc_title">销售说明</p>
                            <p class="desc_content" v-html="saleDesc">

                            </p>
                        </div>
                        <div class="desc" v-if="paymentRule">
                            <p class="desc_title">付款规则</p>
                            <p class="desc_content" v-html="paymentRule">
                            </p>
                        </div>
                        <div class="desc" v-if="otherDesc">
                            <p class="desc_title">其他说明</p>
                            <p class="desc_content" v-html="otherDesc">
                            </p>
                        </div>
                        <div class="desc" style="background-color: #f6f7fb" v-if="attachList&&attachList.length>0||contractList&&contractList.length>0">
                            <el-row>
                                <el-col :span="12" v-if="attachList.length>0">
                                    <p class="desc_title">附件下载</p>
                                    <p class="desc_content" v-for="(item,index) in attachList">
                                        <span> {{item.name}} </span><el-button type="text" @click="download(item.path)">点击下载</el-button>
                                    </p>
                                </el-col>
                                <el-col :span="12" v-if="contractList.length>0">
                                    <p class="desc_title">合同下载</p>
                                    <p class="desc_content" v-for="(item,index) in contractList">
                                        <span> {{item.name}} </span><el-button type="text" @click="download(item.path)">点击下载</el-button>
                                    </p>
                                </el-col>
                            </el-row>
                        </div>

                    </el-tab-pane>
                    <el-tab-pane label="评价" name="six">
                        <%--<div id="enterpriseEvalution" style="margin: 20px"></div>--%>
                        <div class="panel panel-body" style="box-shadow: none">
                            <section id="valuationSection" >
                                <div class="row evaluationTitle" v-if="entEvaluates&&entEvaluates.length>0">
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
                                <div class="prompt text-center" v-if="showEntEvaluates&&showEntEvaluates.length==0" style="margin-top: 20px">
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
                    dialogVisible2:false,
                    imgIndex:0,
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
                    this.getList();
                    this.entId = getParam('enterpriseId');
                    this.getAttachList();
                    this.getContractList();
                    this.getBanner();
                    this.getLicenceDetail();
                },
                methods:{
                    timeOrder:function (o1,o2) {
                        return o1.createTime-o2.createTime;
                    },
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
                                _this.showEntEvaluates&&_this.showEntEvaluates.sort(function (o1,o2) {
                                    return o1.createTime>o2.createTime?-1:1;
                                });
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
                    showBannerImg:function (index) {
                        console.log(index);
                        this.imgIndex=index;
                        this.dialogVisible2=true;
                    },
                    scrollTo:function (id) {
                        document.getElementById(id).scrollIntoView(true);
                    },
                    contact:function () {
                        getEnterpriseContacts('${enterprise.entId}','${enterprise.entName}','处置企业详情页');
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