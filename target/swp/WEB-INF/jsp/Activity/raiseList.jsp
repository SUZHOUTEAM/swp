<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
    <jsp:include page="/common/top.jsp" flush="true">
        <jsp:param name="title" value="众筹统计" />
    </jsp:include>
    <jsp:include page="/common/left.jsp" flush="true">
        <jsp:param name="menuId" value="#raiseActivity" />
    </jsp:include>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link rel="stylesheet" href="<%=appPath %>/main/common/elementui/index.css">
    <!-- 先引入 Vue -->
    <script src="<%=appPath %>/main/common/elementui/vue.min.js"></script>
    <!-- 引入组件库 -->
    <script src="<%=appPath %>/main/common/elementui/index.js"></script>
    <!-- start Dplus --><script type="text/javascript">!function(a,b){if(!b.__SV){var c,d,e,f;window.dplus=b,b._i=[],b.init=function(a,c,d){function g(a,b){var c=b.split(".");2==c.length&&(a=a[c[0]],b=c[1]),a[b]=function(){a.push([b].concat(Array.prototype.slice.call(arguments,0)))}}var h=b;for("undefined"!=typeof d?h=b[d]=[]:d="dplus",h.people=h.people||[],h.toString=function(a){var b="dplus";return"dplus"!==d&&(b+="."+d),a||(b+=" (stub)"),b},h.people.toString=function(){return h.toString(1)+".people (stub)"},e="disable track track_links track_forms register unregister get_property identify clear set_config get_config get_distinct_id track_pageview register_once track_with_tag time_event people.set people.unset people.delete_user".split(" "),f=0;f<e.length;f++)g(h,e[f]);b._i.push([a,c,d])},b.__SV=1.1,c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.php?id=1262787667",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}}(document,window.dplus||[]),dplus.init("1262787667");</script><!-- end Dplus -->
    <section id="app">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>活动管理</el-breadcrumb-item>
            <el-breadcrumb-item>众筹统计</el-breadcrumb-item>
        </el-breadcrumb>
        <div>
            <el-table :data="list" style="width: 100%">
                <el-table-column
                        prop="activityName" width="150px" fixed
                        label="活动名称">
                </el-table-column>
                <el-table-column
                        prop="startDate"
                        width="112px"
                        label="开始时间">
                    <template scope="scope">
                        {{scope.row.startDate.substring(0,10)}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="endDate"
                        width="112px"
                        label="结束时间">
                    <template scope="scope">
                        {{scope.row.endDate.substring(0,10)}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="riseEndDate"
                        width="122px"
                        label="众筹截止时间">
                    <template scope="scope">
                        {{scope.row.riseEndDate.substring(0,10)}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="enrollFee"
                        label="报名费">
                </el-table-column>
                <el-table-column
                        prop="discount"
                        label="折扣">
                </el-table-column>
                <el-table-column
                        prop="numberGroupBuying"
                        label="计划众筹人数">
                </el-table-column>
                <el-table-column
                        prop="enrollActivityAcount"
                        label="已付款人数">
                </el-table-column>
                <el-table-column
                        prop="pendingPayCount"
                        label="待付款人数">
                </el-table-column>
                <el-table-column
                        prop="activityStatus"
                        label="活动状态">
                </el-table-column>
                <el-table-column
                        prop="enrollActivityAcount"
                        width="110px"
                        label="众筹状态" fixed="right">
                    <template scope="scope">
                        <%--<el-button type="primary" size="small" @click="paySuccess(scope.row.id,scope.$index)">发送短信</el-button>--%>
                        <el-tag :type="statusArr[scope.row.statusIndex]['tag']">
                            {{statusArr[scope.row.statusIndex]['text']}}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        label="操作" fixed="right" width="160px">
                    <template scope="scope">
                        <el-button type="primary" size="small" v-if="scope.row.statusIndex>0" :loading="scope.row.sendStatus" @click="sendMessage(scope.row.activityId,scope.row.activityName,scope.row.statusIndex,scope.$index)">发送{{statusArr[scope.row.statusIndex]['text']}}短信</el-button>
                        <span v-else>--</span>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div v-show="list&&list.length>0" align="right">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        </div>
    </section>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>

<script>
    var vue = new Vue({
        el: '#app',
        data:{
            list:[],
            pageIndex:1,
            pageSize:10,
            total:0,
            status:'',
            timerList:[],
            statusArr:[{'tag':'',text:'众筹中'},{'tag':'success',text:'众筹成功'},{'tag':'danger',text:'众筹失败'}],
            sendStatus:false
        },
        created:function () {
            this.getList();
        },
        mounted:function() {

        },
        methods:{
            sendMessage:function (activityId,activityName,statusIndex,index) {
                console.log(activityId,activityName,statusIndex,index);
                vue.$confirm('此操作将发送“'+activityName+'”活动的'+this.statusArr[statusIndex]['text']+'短信, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    var param={
                        activityId:activityId,
                        activityName:activityName,
                        busiStatus:statusIndex==1?'SUCCESS':'FAIL'
                    }
                    var item=vue.list[index];
                    item.sendStatus=true;
                    Vue.set(vue.list,index,item);
                    ajax({
                        url:'/wasteActivityEnroll/crowdFundingSucceed.htm?ticketId=<%=ticketId%>',
                        contentType:'application/json',
                        data:JSON.stringify(param),
                        success:function (result) {
                            if(result.status==1&&result.data){
                                $.notify('发送'+vue.statusArr[statusIndex]['text']+'短信成功', {status: 'success', timeout: 2500});
                                item.sendStatus=false;
                                Vue.set(vue.list,index,item);
                            }
                        }
                    })
                }).catch(function(){});
            },
            getList:function () {
              $.ajax({
                  url:'<%=appPath %>/wasteActivityEnroll/registrationEnrollActivity?ticketId=<%=ticketId%>&pageIndex='+this.pageIndex+'&pageSize='+this.pageSize,
                  type:'post',
                  contentType:'application/json',
                  data:JSON.stringify({}),
                  success:function (result) {
                      console.log(result);
                      if(result.status==1&&result.data.list&&result.data.list.length>0){
                         var list=result.data.list;
                         for(var i in list){
                             var currentTime=new Date(result.data.serverTime.replace(new RegExp("-","gm"),"/")).getTime();
                             var riseEndDate=new Date(list[i].riseEndDate.replace(new RegExp("-","gm"),"/")).getTime();
                             var numberGroupBuying=list[i]['numberGroupBuying'];
                             var enrollActivityAcount=list[i]['enrollActivityAcount'];
                             if(currentTime<riseEndDate){
                                 if(enrollActivityAcount<numberGroupBuying){
                                     list[i]['statusIndex']=0;
                                 }else{
                                     list[i]['statusIndex']=1;
                                 }
                             }else{
                                 list[i]['statusIndex']=list[i]['numberGroupBuying']>list[i]['enrollActivityAcount']?2:1;
                             }
                         }
                          vue.list=list;
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
                        $("#poster_img").css('background','url(../img/load.gif) center center no-repeat').show();
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
            },
            paySuccess:function (id,index) {
                var arr=[id];
                vue.$confirm('此操作将把状态改成已付款, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/wasteActivityEnroll/auditApprove.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify(arr),
                        contentType:'application/json',
                        success:function (result) {
                            if (result.status == 1 && result.data == true) {
                                $.notify('修改状态成功', {status: 'success', timeout: 2500});
                                var item = vue.list[index];
                                item.busiStatus = 'PAYMENTSUCCESS';
                                Vue.set(vue.list, index, item);
                            }
                        }
                    })
                }).catch(function(){});
            }
        }
    });
</script>
</body>
