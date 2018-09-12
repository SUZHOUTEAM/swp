<%--Created by zhanghj on 2017/7/4--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
    String activityId = (String) request.getAttribute("activityId");
%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="资源池" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myIndex" />
</jsp:include>
<link href="<%=appPath %>/css/wastecircle/index.css" rel="stylesheet" type="text/css" />
<link href="<%=appPath %>/css/activity/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/main/pc/css/publish.css">
<section>
    <div class="hidden"><!-- city-picker选择时获取数据参数的设定 ID不能改变 -->
        <input type='hidden' id="appPath" value="<%=appPath %>"/>
        <input type='hidden' id="ticketId" value="<%=ticketId %>"/>
        <input type='hidden' id="activityId" value="<%=activityId %>"/>
    </div>
    <div class="ic">
        <div class="longImg"></div>
        <div class="descAndPrice clearfix">
            <table>
                <tr>
                    <td>
                        <p><span>活动说明</span></p>
                        <p>
                        </p>
                    </td>
                    <td>
                        <p><span>价格：</span><span><b></b>元/吨</span></p>
                    </td>
                </tr>
            </table>
        </div>
        <div class="item_container buy_show" style="padding-bottom: 30px;">
            <div class="item">
                <div class="item_logo"><img src="../main/pc/img//company_default.jpg"></div>
                <div class="item_message">
                    <p class="item_info">
                        <span class="item_name" onClick="vue.getEnterpriseDetail()">
                        </span>
                        <a class="qipao_logo" href="javascript:;" onClick="vue.getEnterpriseContacts()" style="margin-right: 43px;">
                            <img src="../main/pc/img//qipao.jpg">联系TA</a>
                        <span class="item_xukezheng">
                            <i class="xukezheng_icon"></i>
                            <font>许可证:&nbsp; &nbsp;   有效期：</font>
                        </span>
                    </p>
                    <p class="item_address">
                        <i class="publisharea"></i>
                        <%--<span>（距离11.7公里）</span>--%>
                        <font> </font>
                    </p>
                    <p class="item_weifei" title="查看详情"><i class="weifei_icon"></i>可处置你<span>0</span>种危废</p>
                </div>
            </div>
            <%--<div class="chanfei_list"></div>--%>
            <div id="app">
                <template>
                <div class="panel panel-body" v-if="canJoin">
                    <div  class="panel panel-body" style="padding: 15px;margin-bottom: 0;">
                        <div v-show="!wasteSelected" class="selectTable">
                            <div class="title">选择待处置产废</div>
                            <el-table :show-header="false"  @selection-change="handleSelectionChange"  :max-height="maxHeight"
                                      :data="tableData"  v-loading.body="listLoading" ref="dataSelectTable" border
                                      style="width: 100%">
                                <el-table-column
                                        prop="wasteName">
                                </el-table-column>
                                <el-table-column
                                        prop="wasteCode">
                                </el-table-column>
                                <el-table-column
                                        prop="entWasteId"  type="selection">
                                </el-table-column>
                            </el-table>
                            <el-row :gutter="10" style="margin-top:10px">
                                <el-col :xs="6" :sm="6" :md="6" :lg="6"><el-button type="primary" style="width: 120px" @click="select">确 定</el-button></el-col>
                                <el-col :xs="18" :sm="18" :md="18" :lg="18" style="text-align: right">
                                    <span style="color: #6a7580;margin-right: 10px;font-size: 12px;">上面没有要处理的危废，请点击这里</span>
                                    <el-button type="text"  @click="addWaste">+添加危废</el-button>
                                </el-col>
                            </el-row>
                        </div>
                        <div v-show="wasteSelected">
                            <div class="title">活动申请单</div>
                            <el-table :data="multipleSelection"
                                      v-loading="publishLoading"
                                      element-loading-text="正在发布中"
                                      border
                                      style="width:100%">
                                <el-table-column
                                        prop="wasteName"
                                        label="危废名称">
                                </el-table-column>
                                <el-table-column
                                        prop="wasteCode"
                                        label="危废代码">
                                </el-table-column>
                                <el-table-column
                                        prop="quality"
                                        align="right"
                                        label="数量">
                                    <template scope="scope">
                                        <el-input placeholder="请输入数量" v-model="multipleSelection[scope.$index]['quality']"></el-input>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                        prop="unitValue"
                                        label="单位">
                                </el-table-column>
                                <el-table-column label="操作" width="100px" prop="entWasteId">
                                    <template scope="scope">
                                        <el-button type="text" @click.native.prevent="deleteRow(scope.$index,multipleSelection)">移除</el-button>
                                    </template>
                                </el-table-column>
                                <div slot="append" class="sum">
                                    <span>合计：</span>您将发布<font>{{multipleSelection.length}}</font>种危废，累计<font>{{getQualityTextBySelection()}}</font>
                                </div>
                                <div slot="append" class="sum" style="width:700px;padding-bottom: 0;">
                                    <span>备注：</span>
                                    <el-input type="textarea" v-model="remark" placeholder="请输入备注"></el-input>
                                </div>
                            </el-table>
                            <div style="margin-top: 20px">
                                <el-button @click="publish" type="primary" >确认</el-button>
                                <el-button @click="unselected">取消</el-button>
                            </div>
                        </div>

                    </div>

                </div>
                </template>
            </div>
            <%--<div class="tip">--%>
                <%--提示：请确定你的危废种类中是否已经包含所有产废，如果缺失，请先补充完整，再来购买--%>
            <%--</div>--%>
        </div>


    </div>
</section>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script src="<%=appPath %>/app/js/constants.js"></script>
<%--<script type="text/javascript" src="<%=appPath%>/main/pc/js/buyActivity.js"></script>--%>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
<script>
    var vue=new Vue({
        el: '#app',
        data: {
            tableData: [],
            listLoading:true,
            multipleSelection:[],
            wasteSelected:false,
            publishLoading:false,
            dialogVisible:false,
            totalQtyText:'0吨',
            totalQtyT:'',
            remark:'',
            entList:[],
            maxHeight:0,
            canJoin:false,
            licenceId:'',
            entId:'',
            entName:'',
            activityId:''
        },
        created:function() {
            this.initValue();
        },
        methods:{
            initValue:function(){
                this.activityId=$('#activityId').val();
                ajax({
                    url:'/wasteActivity/listCapacityreleaseByActivityId.htm',
                    data:{
                        activityId:this.activityId
                    },
                    success:function(data){
                        console.log(data);
                        if(data.status==1){
//                            var activityDetailListByEnt=data.data.activityDetailListByEnt;
                            var activityInfo=data.data.activityInfo;
                            if(activityInfo&&activityInfo.length>0){
                                var activityInfo=activityInfo[0];
                                vue.entId=activityInfo['entId'];
                                vue.entName=activityInfo['entName'];
                                $('.descAndPrice table tr td:first-child p:nth-child(2)').html(activityInfo['activityRemark']);
                                var imgUrl=IMG_VIEW_URL+'&fileID='+activityInfo['inquiryFileId'];
                                $('.longImg').css('background-image','url('+imgUrl+')');
                                var priceStr=activityInfo.startPrice==activityInfo.endPrice?activityInfo.startPrice:(activityInfo.startPrice+'~'+activityInfo.endPrice);
                                $('.descAndPrice table tr td:nth-child(2) p:nth-child(1) b').html(priceStr);
                                $('.item_name').html(activityInfo['entName']);
                                var licStartDate='';
                                var licEndDate='';
                                if(activityInfo['licStartDate']){
                                    licStartDate=activityInfo['licStartDate'].substring(0,7).replace('-','.');
                                }
                                if(activityInfo['licEndDate']){
                                    licEndDate=activityInfo['licEndDate'].substring(0,7).replace('-','.');
                                }
                                $('.item_xukezheng font').html('许可证:'+activityInfo['licenceNo']+'&nbsp;&nbsp;有效期：'+licStartDate+'--'+licEndDate+'');
                                $('.item_address font').html(activityInfo['machineAddr'].trim()||'--');
                            }
                            $('.item_weifei span').html(data.data.count);
                            if(data.data.enterTypeCode&&data.data.enterTypeCode!='PRODUCTION'){
                                $('.tip').html('该活动仅面向产废企业开放，你不能参加').css('font-size','14px');
                                $('.qipao_logo').css('display','none');
                                return;
                            }
                            if(data.data.isInActivityCanton>0){
                                vue.licenceId=activityInfo.licenceId;
                                vue.init();
                            }else{
                                $('.tip').html('你不属于该活动的指定区域，不能参加').css('font-size','14px');
                            }
                        }
                    }
                });
            },
            getEnterpriseDetail:function () {
                window.location = "<%=appPath%>/enterprise/czDetail.htm?ticketId=<%=ticketId%>&enterpriseId=" + this.entId+'&breadcrumbName=活动';
            },
            getEnterpriseContacts:function(){
                getEnterpriseContacts(this.entId,this.entName,'活动购买页面');
            },
            init:function () {
                this.canJoin=true;
                this.getList();
                this.getMaxHeight();
            },
            getList:function () {
                this.listLoading = true;
                var _this=this;
                ajax({
                    url:'/entWaste/listEntWasteByLicenceId.htm',
                    data:{
                        licenceId:this.licenceId
                    },
                    dataType:'json',
                    success:function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if(result.status==1&&result.data&&result.data.length>0){
                            for(var i in result.data){
                                result.data[i].quality=0;
                            }
                            $('.item_weifei span').html(result.data.length);
                            _this.tableData=result.data;
                        }
                    }
                })
            },
            getMaxHeight:function () {
              var height=document.body.clientHeight;
              this.maxHeight=height-530;
            },
            handleSelectionChange:function(val){
                this.multipleSelection = val;
            },
            deleteRow:function(index, rows) {
                rows.splice(index, 1);
                if(rows.length==0){
                    this.wasteSelected=false;
                }
            },
            select:function () {
                if(this.multipleSelection.length==0){
                    $.notify('请选择危废',{status:'info',timeout:1500});
                    return;
                }
                this.wasteSelected=true;
            },
            publish:function () {
                var param={
                    remark:this.remark,
                    releaseCount:this.multipleSelection.length,
                    totalAmountDesc:this.totalQtyText,
                    totalAmount:this.totalQtyT,
                    activityId:this.activityId
                };
                var entReleaseDetails=[];
                var flag=false;
                var pattern=/^(?=([0-9]{1,10}$|[0-9]{1,7}\.))(0|[1-9][0-9]*)(\.[0-9]{1,3})?$/;
                for(var i in this.multipleSelection){
                    var obj={};
                    var selection=this.multipleSelection[i];
                    obj.entWasteId=selection['entWasteId'];
                    obj.releaseAmount=selection['quality'];
                    if(selection['quality']==0||!pattern.test(selection['quality'])){
                        flag=true;
                    }
                    entReleaseDetails.push(obj);
                }
                if(flag){
                    $.notify('危废数量不能为0,总长不超过十位,最多三位小数',{status:'info',timeout:2000});
                    return;
                }
                param.releaseDetail=entReleaseDetails;
                this.publishLoading=true;
                console.log(param);
                var _this=this;
                ajax({
                    url:'/entRelease/save.htm?ticketId='+getParam('ticketId'),
                    dataType:'json',
                    contentType:'application/json',
                    data:JSON.stringify(param),
                    success:function (result) {
                        console.log(result);
                        _this.publishLoading = false;
                        if(result.status==1){
                            $.notify('发布活动申请单成功,可在我的询价中查看状态',{status:'info',timeout:4000});
                            vue.closeDialog();
                        }
                    }
                })
            },
            addWaste:function () {
                window.location='<%=appPath%>/entRelease/publish.htm?ticketId=<%=ticketId%>';
            },
            unselected:function () {
                this.wasteSelected=false;
            },
            closeDialog:function () {
                this.wasteSelected=false;
                this.$refs.dataSelectTable.clearSelection();
                for(var i in this.tableData){
                    this.tableData[i].quality=0;
                }
            },
            getQualityTextBySelection:function() {
                var arr=this.multipleSelection;
                var obj = {};
                var laNumber = new $.LaNumber();
                for(var i in arr) {
                    var key = arr[i]['unitValue'];
                    var value = parseFloat(arr[i]['quality']);
                    if(key=='吨'||key=='千克'||key=='克'){
                        switch (key){
                            case '千克':
                                value=laNumber.div(value,1000);
                                break;
                            case '克':
                                value=laNumber.div(value,1000000);
                                break;
                        }
                        if(obj['吨']){
                            obj['吨']=laNumber.add(obj['吨'],value);
                        }else{
                            obj['吨']=value;
                        }
                    }else{
                        if(obj[key]) {
                            obj[key] =laNumber.add(obj[key],value) ;
                        } else {
                            obj[key] = value;
                        }
                    }
                }
                var str='';
                for (var prop in obj) {
                    str+=obj[prop]+prop+'，';
                }
                str=str.substring(0,str.length-1);
                this.totalQtyText=str;
                this.totalQtyT = obj['吨'];
                return str;
            }
        }

    })
</script>
<script type="text/javascript">
    $(document).ready(function () {
        var rs = window.globalInit();
        rs.done(function () {
        });
    });
</script>