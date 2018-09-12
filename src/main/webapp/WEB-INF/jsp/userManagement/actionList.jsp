<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="用户操作"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#actionList"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/business.css">
<style type="text/css">
    b{
        font-weight: 100;
        font-size: 12px;
    }
    .el-textarea textarea{
        border: 0;
        resize: none;
        height: auto;
    }
    .el-input__icon{
        line-height: 31px;
    }
    .el-date-editor.el-input, .el-date-editor.el-input__inner {
        width: 210px;
        margin-left: 12px;
    }
    .searchDiv .el-input__inner{
        width: 210px;
    }
    .bar{
        margin: 10px 0;
    }
    .barct em,table em{
        font-size: 16px;
        color: #1171D1;
        font-style: normal;
    }
    .bar .barct .par{
        margin-left: 0;
        margin-right: 20px;
    }
    .el-select .el-input__inner,.input-with-select .el-input__inner{
        width: 150px;
    }
</style>
<section id="app">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>运营管理</el-breadcrumb-item>
            <el-breadcrumb-item>用户操作</el-breadcrumb-item>
        </el-breadcrumb>
        <div >
            <el-row class="searchDiv">
                <b>操作时间：</b>
                <el-button type="text" :class="{'select':dateType=='TODAY'}" @click="queryDateType('TODAY')">今天</el-button>
                <el-button type="text" :class="{'select':dateType=='WEEK'}" @click="queryDateType('WEEK')">本周</el-button>
                <el-button type="text" :class="{'select':dateType=='MONTH'}" @click="queryDateType('MONTH')">本月</el-button>
                <el-date-picker
                        v-model="startDateTime"
                        type="date"
                        @change="changeStartDateTime"
                        placeholder="选择开始时间">
                </el-date-picker>
                <el-date-picker
                        v-model="endDateTime"
                        type="date"
                        @change="changeEndDateTime"
                        placeholder="选择结束时间">
                </el-date-picker>
                <el-select v-model="entType" placeholder="请选择企业类型" @change="search">
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
                <el-input placeholder="请输入用户名称" v-model="userName" class="input-with-select">
                    <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
                </el-input>
            </el-row>
            <div id="selected" class="bar" v-if="totalData.length>0">
                <div class="barct">
                    <%--<span class="condition">筛选结果：</span>--%>
                    <%--<em>共2笔订单符合筛选条件</em>--%>
                    <span class="par" :style="index==0?'margin-left: 0':''" v-for="(item,index) in totalData" v-if="!showAllAction?(index<topLength):true">
                        <b>{{item.eventCodeValue}}：</b>
                        <em>{{item.count}}次</em>
                    </span>
                    <span class="par">
                        <a href="javascript:" style="text-decoration: none" @click="showAllAction=!showAllAction">
                            {{showAllAction?'收起':'展开全部'}}<i v-show="!showAllAction" style="margin-left: 2px" class="el-icon-arrow-down"></i><i v-show="showAllAction" style="margin-left: 2px" class="el-icon-arrow-up"></i></a>
                    </span>
                </div>
            </div>
            <div class="selectTable">
                <el-table  tooltip-effect="dark" @row-click="rowClick" :highlight-current-row="true"
                           :data="tableData"  v-loading.body="listLoading" ref="dataSelectTable" border
                           style="width: 100%">
                    <el-table-column
                            label="企业名称"
                            prop="entName">
                        <template scope="scope">
                            {{scope.row.entName||'--'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="用户位置"
                            prop="cname">
                        <template scope="scope">
                            {{scope.row.cname||'APP登录'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="用户"
                            width="100px"
                            prop="userName">
                        <template scope="scope">
                            {{scope.row.userName||'--'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="登录时间"
                            width="160px"
                            prop="loginTime">
                        <template scope="scope">
                            {{scope.row.loginTime||'--'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="登出时间"
                            width="160px"
                            prop="logoutTime">
                        <template scope="scope">
                            {{scope.row.logoutTime||'--'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="操作">
                        <template scope="scope">
                            <div v-if="scope.row.actionTypeModels.length>0">
                                <span v-for="(item,index) in scope.row.actionTypeModels">
                                    <span style="margin-right: 10px">{{item.eventCodeValue}}(<em>{{item.count}}</em>)</span>
                                </span>
                            </div>
                            <span v-else>--</span>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div v-show="tableData&&tableData.length>0" align="right" style="margin-top: 10px">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
            <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :modal-append-to-body="false" width="70%">
                <el-table  tooltip-effect="dark"
                           :data="actionDetailList"  v-loading.body="detailLoading" border  height="400px"
                           style="width: 100%">
                    <el-table-column
                            label="操作时间"
                            width="160px"
                            prop="createTime">
                        <template scope="scope">
                            {{scope.row.createTime||'--'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="操作名称"
                            prop="userActionValue">
                        <template scope="scope">
                            {{scope.row.userActionValue||'--'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="手机号码"
                            prop="userPhoneNo">
                        <template scope="scope">
                            {{scope.row.userPhoneNo||'--'}}
                        </template>
                    </el-table-column>
                   <el-table-column
                            label="操作备注"
                            prop="remark">
                        <template scope="scope">
                            {{scope.row.remark?typeObj[scope.row.userActionValue]:''}} {{scope.row.remark||'--'}}
                        </template>
                    </el-table-column>
                </el-table>
            </el-dialog>
        </div>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>

<script>
    var vue=new Vue({
        el: '#app',
        data: {
            tableData: [],
            listLoading:true,
            pageIndex:1,
            pageSize:10,
            total:0,
            totalData:[],
            dateType:'TODAY',
            startDateTime:'',
            endDateTime:'',
            dialogVisible:false,
            actionDetailList:[],
            detailLoading:false,
            dialogTitle:'',
            typeObj:{'联系TA':'联系：','查看处置企业信息':'查看：','首页联系TA':'联系：','查看活动':'查看：','搜索':'关键字：'},
            dateTypeClick_start:false,
            dateTypeClick_end:false,
            showAllAction:false,
            topLength:0,
            topActions:['INQUIRY','RELEASEWASTE','MOBILE_RELEASE_WASTE','REGISTER_SUCCESS','LOGIN','MB_REGISTER_SUCCESS','APP_REGISTER_SUCCESS','MB_LOGIN', 'APP_LOGIN', 'APP_INQUIRY', 'PC_PAY_CONTRACT', 'APP_PAY_CONTRACT', 'PC_PAY_INQUIRY', 'APP_PAY_INQUIRY', 'PC_PAY_ALI', 'MB_PAY_ALI'],
            arr1:[{"eventCode":"REGISTER_SUCCESS","eventCodeValue":"PC注册成功","count":0},{"eventCode":"MB_REGISTER_SUCCESS","eventCodeValue":"手机注册成功","count":0},{"eventCode":"APP_REGISTER_SUCCESS","eventCodeValue":"APP端注册成功","count":0},{"eventCode":"LOGIN","eventCodeValue":"PC登录成功","count":0},{"eventCode":"MB_LOGIN","eventCodeValue":"手机登录成功","count":0},{"eventCode":"APP_LOGIN","eventCodeValue":"APP端登录成功","count":0},{"eventCode":"PC_PAY_INQUIRY","eventCodeValue":"PC报价购买成功  ","count":0},{"eventCode":"APP_PAY_INQUIRY","eventCodeValue":"APP报价购买成功","count":0},{"eventCode":"PC_PAY_CONTRACT","eventCodeValue":"PC联系TA购买成功","count":0},{"eventCode":"APP_PAY_CONTRACT","eventCodeValue":"APP联系TA购买成功","count":0},{"eventCode":"INQUIRY","eventCodeValue":"PC报价成功","count":0},{"eventCode":"APP_INQUIRY","eventCodeValue":"APP报价成功","count":0},{"eventCode":"RELEASEWASTE","eventCodeValue":"PC端询价成功","count":0},{"eventCode":"MOBILE_RELEASE_WASTE","eventCodeValue":"手机端询价成功","count":0},{"eventCode":"PC_PAY_ALI","eventCodeValue":"PC充值成功数","count":0},{"eventCode":"MB_PAY_ALI","eventCodeValue":"手机充值成功数","count":0}],
            options:[{value: 'total',label: '全部'},{value: 'DISPOSITION',label: '处置企业'},{value: 'PRODUCTION',label: '产废企业'},{value: 'DIS_FACILITATOR',label: '处置服务商'}],
            entType:'',
            userName:''
        },
        created:function() {
            this.getList();
        },
        methods:{
            getList:function () {
                this.listLoading = true;
                var _this=this;
                var param={
                    pageIndex:this.pageIndex,
                    pageSize:this.pageSize,
                    userName:this.userName
                };
                if(this.dateType){
                    param.DateType=this.dateType;
                }
                if(this.startDateTime){
                    param.startDateTime=this.startDateTime.format('yyyy-MM-dd')+' 00:00:00';
                }
                if(this.endDateTime){
                    param.endDateTime=this.endDateTime.format('yyyy-MM-dd')+' 23:59:59';
                }
                param.entType=this.entType=='total'?'':this.entType;
                ajax({
                    url:'/userAction/listUserAction.htm',
                    dataType:'json',
                    data:param,
                    success:function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if(result.status==1&&result.data.userActionModels&&result.data.userActionModels.length>0){
                            _this.total=result.data.pagingParameter.totalRecord;
                            _this.tableData=result.data.userActionModels;
                            var actionTypeModels=result.data.actionTypeModels;
                            var arr1=_this.arr1.concat(),arr2=[],topMap={};
                            for(var i in actionTypeModels){
                                var obj=actionTypeModels[i];
                                if(_this.topActions.indexOf(obj['eventCode'])>-1){
                                    topMap[obj['eventCode']]=obj['count'];
                                }else{
                                    arr2.push(obj);
                                }
                            }
                            var len=arr1.length;
                            for(var k=len-1;k>=0;k--){
                                if(topMap[arr1[k]['eventCode']]){
                                    arr1[k]['count']=topMap[arr1[k]['eventCode']];
                                }else{
                                    arr1.splice(k,1);
                                }
                            }
                            _this.topLength= arr1.length;
                            _this.totalData= arr1.concat(arr2);
                        }else{
                            _this.tableData=[];
                            _this.total=0;
                            _this.totalData=[];
                        }
                    }
                })
            },
            search:function () {
              this.pageIndex=1;
              this.getList();
            },
            handleSizeChange:function(val) {
                this.pageIndex=1;
                this.pageSize = val;
                this.getList();
            },
            handleCurrentChange:function(val) {
                this.pageIndex = val;
                this.getList();
            },
            queryDateType:function (dateType) {
                this.dateType=dateType;
                this.startDateTime='';
                this.endDateTime='';
                this.dateTypeClick_start=true;
                this.dateTypeClick_end=true;
                this.search();
            },
            changeStartDateTime:function (startDateTime) {
                if(this.dateTypeClick_start&&!startDateTime){
                    this.dateTypeClick_start=false;
                    return;
                }
                this.startDateTime=startDateTime;
                this.dateType='';
                this.search();
            },
            changeEndDateTime:function (endDateTime) {
                if(this.dateTypeClick_end&&!endDateTime){
                    this.dateTypeClick_end=false;
                    return;
                }
                this.endDateTime=endDateTime;
                this.dateType='';
                this.search();
            },
            formatDate:function (dateStr,fmt) {
                try {
                    fmt=fmt||'yyyy-MM-dd HH:mm:ss';
                    var arr=dateStr.split(' ');
                    var arr1=arr[0].split('-');
                    var year=arr1[0];
                    var month=arr1[1]||'01';
                    var day=arr1[2]||'01';
                    fmt=fmt.replace(/yyyy/g,year);
                    fmt=fmt.replace(/MM/g,month);
                    fmt=fmt.replace(/dd/g,day);
                    if(arr.length>1){
                        var arr2=arr[1].split(':');
                        var hour=arr2[0]||'00';
                        var min=arr2[1]||'00';
                        var second=arr2[2]||'00';
                        fmt=fmt.replace(/HH/g,hour);
                        fmt=fmt.replace(/mm/g,min);
                        fmt=fmt.replace(/ss/g,second);
                    }else{
                        fmt=fmt.replace(/HH/g,'00');
                        fmt=fmt.replace(/mm/g,'00');
                        fmt=fmt.replace(/ss/g,'00');
                    }
                } catch(error) {
                    console.error(error);
                } finally {
                    return fmt;
                }
            },
            rowClick:function (row, event, column) {
                console.log(row);
                var param={
//                    pageIndex:this.pageIndex,
//                    pageSize:this.pageSize
                };
                if(this.dateType){
                    param.DateType=this.dateType;
                }
                if(this.startDateTime){
                    param.startDateTime=this.startDateTime.format('yyyy-MM-dd')+' 00:00:00';
                }
                if(this.endDateTime){
                    param.endDateTime=this.endDateTime.format('yyyy-MM-dd')+' 23:59:59';
                }
                var url='';
                if(row.ticketId){
                    param.actionTicketId=row.ticketId;
                    url='/userAction/listUserActionDetailByTicketId';
                    this.dialogTitle=(row.entName||'尚未绑定企业')+'的'+row.userName+'的操作详情';
                }else{
                    this.dialogTitle=row.cname+'，ip为'+row.cip+'的操作详情';
                    url='/userAction/listUserActionDetailByIp';
                    param.cip=row.cip;
                }
                this.dialogVisible=true;
                this.detailLoading=true;
                $.ajax({
                    url:'/swp'+url,
                    dataType:'json',
                    data:param,
                    success:function (result) {
                        console.log(result);
                        vue.detailLoading=false;
                        if(result.status==1){
                            vue.actionDetailList=result.data.userActionModels;
                        }
                    }
                })
            }
        }

    })
</script>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function() {
    });
    Date.prototype.format = function(fmt) {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    }
</script>