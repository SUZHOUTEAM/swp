<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
    <jsp:include page="/common/top.jsp" flush="true">
        <jsp:param name="title" value="活动报名" />
    </jsp:include>
    <jsp:include page="/common/left.jsp" flush="true">
        <jsp:param name="menuId" value="#enterActivity" />
    </jsp:include>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit" />
    <link rel="shortcut icon" href="<%=appPath%>/app/img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <link rel="stylesheet" href="<%=appPath %>/main/common/elementui/index.css">
    <!-- 先引入 Vue -->
    <script src="<%=appPath %>/main/common/elementui/vue.min.js"></script>
    <!-- 引入组件库 -->
    <script src="<%=appPath %>/main/common/elementui/index.js"></script>
    <!-- start Dplus --><script type="text/javascript">!function(a,b){if(!b.__SV){var c,d,e,f;window.dplus=b,b._i=[],b.init=function(a,c,d){function g(a,b){var c=b.split(".");2==c.length&&(a=a[c[0]],b=c[1]),a[b]=function(){a.push([b].concat(Array.prototype.slice.call(arguments,0)))}}var h=b;for("undefined"!=typeof d?h=b[d]=[]:d="dplus",h.people=h.people||[],h.toString=function(a){var b="dplus";return"dplus"!==d&&(b+="."+d),a||(b+=" (stub)"),b},h.people.toString=function(){return h.toString(1)+".people (stub)"},e="disable track track_links track_forms register unregister get_property identify clear set_config get_config get_distinct_id track_pageview register_once track_with_tag time_event people.set people.unset people.delete_user".split(" "),f=0;f<e.length;f++)g(h,e[f]);b._i.push([a,c,d])},b.__SV=1.1,c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.php?id=1262787667",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}}(document,window.dplus||[]),dplus.init("1262787667");</script><!-- end Dplus -->
    <section id="app"  v-cloak>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>活动管理</el-breadcrumb-item>
            <el-breadcrumb-item>活动报名</el-breadcrumb-item>
        </el-breadcrumb>
        <div>
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="活动名称">
                <el-input v-model="formInline.activityName" placeholder="请输入活动名称"></el-input>
            </el-form-item>
            <el-form-item label="企业名称">
                <el-input v-model="formInline.entName" placeholder="企业名称"></el-input>
            </el-form-item>
            <el-form-item label="状态">
                <el-select v-model="formInline.busiStatus" placeholder="选择发布状态"  style="margin:0 12px" >
                    <el-option v-for="item in busiStatusList" :key="item.code" :label="item.value" :value="item.code">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getList">查询</el-button>
            </el-form-item>
        </el-form>
        <div style="">
            <el-table :data="list" border
                      style="width:100%">
                <el-table-column
                        prop="entName"
                        label="企业名称">
                </el-table-column>
                <el-table-column
                        prop="userName"
                        label="操作人">
                </el-table-column>
                <el-table-column
                        prop="activityName"
                        label="活动名称">
                </el-table-column>
                <el-table-column
                        prop="fee"
                        label="报名费">
                    <template scope="scope">
                        <span style="color: #ff0000">￥{{scope.row.fee}}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        width="160px"
                        label="提交时间">
                </el-table-column>
                <el-table-column
                        prop="busiStatus"
                        label="状态">
                    <template scope="scope">
                        <el-tag :type="classStatus[scope.row.busiStatus]">{{activityStatus[scope.row.busiStatus]}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        label="操作" >
                    <template scope="scope">
                        <el-button type="primary" size="small" v-if="scope.row.busiStatus!='PAYMENTSUCCESS'" @click="paySuccess(scope.row.id,scope.$index)">已付款</el-button>
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
            activityStatus:{'SUBMIT':'等待付款','PAYMENTSUCCESS':'付款成功','PAYMENTFAILED':'付款失败'},
            classStatus:{'SUBMIT':'warning','PAYMENTSUCCESS':'success','PAYMENTFAILED':'info'},
            status:'',
            timerList:[],
            formInline:{
                activityName:'',
                entName:'',
                busiStatus:''
            },
            busiStatusList: [{'value': '全部', 'code': ''},{'value': '等待付款', 'code': 'SUBMIT'},{'value': '付款成功', 'code': 'PAYMENTSUCCESS'}],

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
                  data:JSON.stringify(this.formInline),
                  success:function (result) {
                      console.log(result);
                      if(result.status==1&&result.data.list&&result.data.list.length>0){
                         var list=result.data.list;
//                          var currentDate=result.data.serverTime;
                          for(var i in list){
                              var imgUrl=IMG_VIEW_URL+'&fileID='+list[i].logoFileId;
                              list[i]['logo']=imgUrl;
                              list[i]['activityRemark']=list[i]['entRemark'].replace(/<br\/>/g,'');
                              /*if(list[i]['status']=='2'){
                                  var endDate=new Date(list[i].endDate.replace(new RegExp("-","gm"),"/")).getTime();
                                  var longTime=new Date(currentDate.replace(new RegExp("-","gm"),"/")).getTime();
                                  vue.createTimer(endDate,longTime,i);
                              }
                              if(list[i]['status']=='1'){
                                  var startDate=new Date(list[i].startDate.replace(new RegExp("-","gm"),"/")).getTime();
                                  var longTime=new Date(currentDate.replace(new RegExp("-","gm"),"/")).getTime();
                                  vue.createTimer(startDate,longTime,i);
                              }*/
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
                window.location.href = "<%=appPath %>/main/pc/view/company.html" ;
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
<script type="text/javascript">
    $(document).ready(function () {
        var rs = window.globalInit();
        rs.done(function () {
        });
    });
</script>
</body>
