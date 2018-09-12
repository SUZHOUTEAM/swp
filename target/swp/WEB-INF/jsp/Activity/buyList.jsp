<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<c:if test="${entType=='FACILITATOR'}">
    <jsp:include page="/common/webCommon/user_top.jsp" flush="true">
        <jsp:param name="title" value="我购买的课程" />
    </jsp:include>
    <jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
        <jsp:param name="menuId" value="#myBuyActivity" />
    </jsp:include>
</c:if>
<c:if test="${entType!='FACILITATOR'}">
    <jsp:include page="/common/webCommon/user_top.jsp" flush="true">
        <jsp:param name="title" value="我购买的课程" />
    </jsp:include>
    <jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
        <jsp:param name="menuId" value="#myBuyActivity" />
    </jsp:include>
</c:if>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit" />
    <link rel="shortcut icon" href="<%=appPath%>/app/img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link rel="stylesheet" href="<%=appPath %>/main/common/elementui/index.css">
    <link rel="stylesheet" href="<%=appPath %>/main/pc/css/activityList.css?4" />
    <style type="text/css">
        .item{
            background-color: #ffffff;
            padding: 30px;
            margin-bottom: 10px;
        }
        .btnDiv b{
            color: #0179de;
        }
    </style>
    <script src="<%=appPath %>/thirdparty/dialog/dialog.js"></script>
    <!-- 先引入 Vue -->
    <script src="<%=appPath %>/main/common/elementui/vue.min.js"></script>
    <!-- 引入组件库 -->
    <script src="<%=appPath %>/main/common/elementui/index.js"></script>
    <!-- start Dplus --><script type="text/javascript">!function(a,b){if(!b.__SV){var c,d,e,f;window.dplus=b,b._i=[],b.init=function(a,c,d){function g(a,b){var c=b.split(".");2==c.length&&(a=a[c[0]],b=c[1]),a[b]=function(){a.push([b].concat(Array.prototype.slice.call(arguments,0)))}}var h=b;for("undefined"!=typeof d?h=b[d]=[]:d="dplus",h.people=h.people||[],h.toString=function(a){var b="dplus";return"dplus"!==d&&(b+="."+d),a||(b+=" (stub)"),b},h.people.toString=function(){return h.toString(1)+".people (stub)"},e="disable track track_links track_forms register unregister get_property identify clear set_config get_config get_distinct_id track_pageview register_once track_with_tag time_event people.set people.unset people.delete_user".split(" "),f=0;f<e.length;f++)g(h,e[f]);b._i.push([a,c,d])},b.__SV=1.1,c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.php?id=1262787667",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}}(document,window.dplus||[]),dplus.init("1262787667");</script><!-- end Dplus -->
    <section id="app"  v-cloak>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>个人中心</el-breadcrumb-item>
            <el-breadcrumb-item>我购买的课程</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="panel panel-body" >
        <div class="videoDiv" id="videoDiv">
            <div id="poster_img"></div>
        </div>
        <div v-if="list.length>0" style="">
        <el-container class="item" v-for="(item,index) in list" >
            <el-aside class="logoDiv" width="414px" :style="'background: url('+item.logo+') center center / auto 100% no-repeat'">
                <div class="play_div" v-if="item.videoResource"></div>
            </el-aside>
            <el-main>
                <el-row class="title">
                    <el-col :span="12">直播课程：{{item.activityName}}</el-col>
                    <el-col :span="12" align="right" class="status">
                        <img src="<%=appPath%>/main/pc/img/pay_success.png" v-if="item.busiStatus=='PAYMENTSUCCESS'"/>
                        <img src="<%=appPath%>/main/pc/img/pay_wait.png" v-else/>
                        <%--<el-tag :type="classStatus[item.busiStatus]">{{activityStatus[item.busiStatus]}}</el-tag>--%>
                    </el-col>
                </el-row>
                <%--<div class="createBy">
                    活动方：<b>{{item.entName}}</b>
                </div>--%>
                <div class="content">
                    {{item.activityRemark}}
                </div>
                <div class="btnDiv">
                    <%--<el-button type="primary" @click="goLive(item.videoResource,item.logo,item.activityName)" v-if="item.videoResource">{{item.videoResource.indexOf('/main/pc')>-1?'观看直播':'回看'}}></el-button>--%>
                    <el-button type="primary" @click="goActivityDetail(item.activityId,item.activityName)">查看详情></el-button>
                    <span style="margin: 0 14px">购买日期：<b>{{item.createTime.substring(0,10)}}</b></span>
                    <span>付款：<b>{{item.fee}}元</b></span>
                </div>
            </el-main>
        </el-container>
        </div>
        <div class="nodata" v-else>暂无相关数据</div>
        <div v-show="list&&list.length>0" align="center">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[5,10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        </div>
    </section>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />
<script src="<%=appPath %>/app/js/constants.js"></script>

<script>
    var vue = new Vue({
        el: '#app',
        data:{
            list:[],
            pageIndex:1,
            pageSize:5,
            total:0,
            activityStatus:{'SUBMIT':'等待付款','PAYMENTSUCCESS':'付款成功','PAYMENTFAILED':'付款失败'},
            classStatus:{'SUBMIT':'warning','PAYMENTSUCCESS':'success','PAYMENTFAILED':'info'},
            status:'',
            timerList:[],
        },
        created:function () {
            this.getList();
        },
        mounted:function() {

        },
        methods:{
            getList:function () {
              $.ajax({
                  url:'<%=appPath %>/wasteActivityEnroll/listActivityEnroll?ticketId=<%=ticketId%>&pageIndex='+this.pageIndex+'&pageSize='+this.pageSize,
                  type:'post',
                  contentType:'application/json',
                  data:JSON.stringify({entId:globalInit.enterpriseId}),
                  success:function (result) {
                      console.log(result);
                      if(result.status==1&&result.data.list&&result.data.list.length>0){
                         var list=result.data.list;
//                          var currentDate=result.data.serverTime;
                          for(var i in list){
                              var imgUrl=IMG_VIEW_URL+'&fileID='+list[i].logoFileId;
                              list[i]['logo']=imgUrl;
                              list[i]['activityRemark']=list[i]['entRemark'].replace(/<br\/>/g,'');
                          }
                          vue.list=result.data.list;
                          vue.total=result.data.paging.totalRecord;
                      }else{
                          vue.list=[];
                          vue.total=0;
                      }
                  }
              })
            },
            clearAllTimer:function () {
                for(var i in this.timerList){
                    clearInterval(this.timerList[i]);
                }
            },
            queryStatus:function (status) {
                this.status=status;
                this.pageIndex=1;
                this.getList();
            },
            createTimer:function (endDate,longTime,index) {
                var timer=setInterval(function(){
                    longTime+=1000;
                    vue.ShowCountDown(endDate,longTime,index);
                },1000);
                this.timerList.push(timer);
            },
            ShowCountDown:function(dateFinal,dateNow,index){
                var dateSub = dateFinal - dateNow;  //计算差值，单位毫秒
                if(dateSub<0){
                    var item=this.list[index];
                    item.remainTimeText='已结束';
                    Vue.set(this.list,index,item);
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
                    timeHtml += day + '天 ' + this.toDouble(hour) + ':' + this.toDouble(minute) + ':' + this.toDouble(second) ;
                }
                var item=this.list[index];
                item.remainTimeText=timeHtml;
                Vue.set(this.list,index,item);
            },
            toDouble:function(num){
                if(num < 10){
                    return '0'+ num;
                }else{
                    return '' + num;
                }
            },
            handleSizeChange:function(val) {
                this.pageIndex=1;
                this.pageSize = val
                this.getList()
            },
            handleCurrentChange:function(val) {
                this.pageIndex = val
                this.getList()
            },
            goLive:function (url,logo,activityName) {
                if(url.indexOf('/main/pc')>-1){
                    if(index.isLogin&&index.ticketId){
                        collectingUserBehavior(index.ticketId,'VIEWACTIVITY',activityName);
                    }else{
                        collectingUserBehavior('','VIEWACTIVITY',activityName);
                    }
                    var ticketStr=(index.ticketId?('?ticketId='+index.ticketId):'');
                    window.location=url+ticketStr;
                }else{
                    if($('#video').length>0){
                        $('#video').remove();
                    }
                    $('#poster_img').after('<video id="video" controls preload="auto" autoplay="true" width="100%" height="100%" poster="'+logo+'">'+
                        '<source src="'+url+'" type="video/mp4">'+
                        '</video>')
                    $('#videoDiv').show();
                    $("#video").bind("play canplay",function(){
                        $("#poster_img").hide();
                    });
                    $('#video').on('waiting', function() {
                        $("#poster_img").css('background','url(../img/goBuyService.jpg) center center no-repeat').show();
                    });
                    $('#video').on('ended', function() {
                        $("#poster_img").css('background','url('+logo+') center center no-repeat').show();
                    });
                }
            },
            goActivityDetail:function (activityId,activityName) {
                var ticketId='<%=ticketId%>';
                collectingUserBehavior(ticketId,'VIEWACTIVITY',activityName);
                window.location='<%=appPath %>/main/pc/view/feeActivityDetail.html?activityId='+activityId+'&ticketId='+ticketId;
            },
            goEntList:function () {
                window.location.href = "<%=appPath %>/main/pc/view/company.html";
            }
        }
    });
</script>
<script type="text/javascript">
    $(document).ready(function () {
        var rs = window.globalInit();
        rs.done(function () {
        });
    });
</script>
</body>
