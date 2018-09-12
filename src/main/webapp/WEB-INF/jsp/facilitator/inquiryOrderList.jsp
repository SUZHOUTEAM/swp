<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="我的订单" />
</jsp:include>
<jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myInquiryOrders" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/business.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/sign.css">
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的易废圈</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">平台订单</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
        <div id="app">
            <template>
                <el-row :gutter="10" class="searchDiv">
                    <el-col :xs="8" :sm="8" :md="8" :lg="8" align="left">
                        <b>订单状态：</b>
                        <el-button type="text" :class="{'select':orderStatus==''}" @click="queryStatus('')">全部</el-button>
                        <el-button type="text" :class="{'select':orderStatus=='DONE'}" @click="queryStatus('DONE')">已完成</el-button>
                        <el-button type="text" :class="{'select':orderStatus=='ONGOING'}" @click="queryStatus('ONGOING')">进行中</el-button>
                    </el-col>
                    <el-col :xs="16" :sm="16" :md="16" :lg="16" align="right">
                        <b>订单时间：</b>
                        <el-button type="text" :class="{'select':dateType=='TODAY'}" @click="queryDateType('TODAY')">今天</el-button>
                        <el-button type="text" :class="{'select':dateType=='WEEK'}" @click="queryDateType('WEEK')">本周</el-button>
                        <el-button type="text" :class="{'select':dateType=='MONTH'}" @click="queryDateType('MONTH')">本月</el-button>
                        <div style="float: right;margin-top: 3px;margin-left: 10px;">
                            <el-date-picker
                                    v-model="startDateTime"
                                    type="date"
                                    @change="changeDate"
                                    format="yyyy-MM-dd"
                                    placeholder="选择开始日期">
                            </el-date-picker>
                            <el-date-picker
                                    v-model="endDateTime"
                                    type="date"
                                    @change="changeDate"
                                    format="yyyy-MM-dd"
                                    placeholder="选择结束日期">
                            </el-date-picker>
                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="10" class="searchDiv">
                    <el-col :xs="14" :sm="14" :md="14" :lg="14" align="left">
                        <b>负 &nbsp;责 人：</b>
                        <el-button type="text" :class="{'select':managerType==''&&manager==''}" @click="queryManagerType('')">全部</el-button>
                        <el-button type="text" :class="{'select':managerType=='my'}" @click="queryManagerType('my')">我的</el-button>
                        <el-select v-model="manager" placeholder="选择其他负责人" style="margin-left: 12px" @change="managerChange()">
                            <el-option v-for="item in managerList" :key="item.userId" :label="item.chineseName" :value="item.userId">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :xs="10" :sm="10" :md="10" :lg="10" align="right">
                        <b>企  &nbsp;业：</b>
                        <el-select v-model="entId" placeholder="选择企业名称" @change="entChange()">
                            <el-option v-for="item in entList" :key="item.entId" :label="item.entName" :value="item.entId">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-row>
                <div id="selected" class="bar" v-if="orderTotal">
                    <div class="barct">
                        <span class="condition">筛选结果：</span>
                        <em>共{{orderTotal.queryCount}}笔订单符合筛选条件</em>
                        <span class="par">
							<b>总数：</b>
                            <em>{{orderTotal.totalAmount||0}}</em>
						</span>
                        <span class="par money">
							<b>金额：</b>
                            <em>￥{{fmoney(orderTotal.totalPrice,2)}}</em>
						</span>
                    </div>
                </div>
                <div v-for="(item,index) in list">
                    <div class="buy_item panel panel-body">
                        <el-row :gutter="10" style="margin-bottom:10px">
                            <el-col :xs="20" :sm="20" :md="20" :lg="20">
                                <span class="status" :class="statusClass[item.busiStatus]">{{item.busiStatus}}</span><b style="margin-left: 14px">单位：</b><a href="javascript:;" title="查看企业详情" @click="entDetail(item.releaseEntId,item.facilitatorEntId)"><em style="margin-right: 4px">{{item.releaseEntName}}</em></a>
                                <a class="qipao_logo" href="javascript:;" @click="contact(item.facilitatorEntId||item.releaseEntId,item.releaseEntName)" title="联系TA" style="margin-right: 14px;">
                                    <img src="../main/pc/img/qipao.jpg"></a>
                                <b>负责人：</b><em>{{item.inquiryPersonName}}</em>
                            </el-col>
                            <el-col :xs="4" :sm="4" :md="4" :lg="4" align="right" style="font-size:12px;color: #6a7580;">
                                    {{item.orderDate}}
                            </el-col>
                        </el-row>
                        <el-row :gutter="10" style="margin-bottom:10px">
                            <el-col :xs="24" :sm="24" :md="24" :lg="24">
                                <b>报价方式：</b><em>{{item.quotedType==0?'打包报价':'单独报价'}}</em><b>金额：</b><em><b style="color: #ff474c;font-weight:bold">￥{{item.totalPrice}}</b></em>
                                <b>总数：</b><em><b style="color: #1171d1">{{item.totalAmount}}</b></em>
                            </el-col>
                        </el-row>
                        <el-table :data="item.orderDetail" border
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
                                    prop="amount"
                                    align="right"
                                    label="数量">
                            </el-table-column>
                            <el-table-column
                                    prop="unitValue"
                                    label="单位">
                            </el-table-column>
                            <el-table-column
                                    align="right"
                                    v-if="item.quotedType!=0"
                                    label="单价" >
                                <template scope="scope">
                                    <span v-if="scope.row.price == 0" >-</span>
                                    <span v-if="scope.row.price!=0" >￥{{scope.row.priceStr}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    align="right"
                                    v-if="item.quotedType!=0"
                                    label="单项总价" >
                                <template scope="scope">
                                    <span v-if="scope.row.wasteTotalPrice == 0" >-</span>
                                    <span style="color: #ff474c;" class='wasteTotalPrice' v-if="scope.row.wasteTotalPrice!=0">￥{{scope.row.wasteTotalPriceStr}}</span>
                                </template>
                            </el-table-column>
                        </el-table>
                        <el-row :gutter="10" style="margin-top:10px" >
                            <el-col :xs="24" :sm="24" :md="24" :lg="24"><b>备注：</b><em>{{item.inquiryRemark||'--'}}</em></el-col>
                        </el-row>
                        <div style="margin-top: 9px;text-align: right" v-show="item.busiStatus=='进行中'">
                            <el-button type="text" class="signBtn" icon="el-icon-upload" style="margin-right: 18px" @click="showUploadContract(item)">合同上传</el-button>
                            <el-tag v-if="item.tagInfo.tagStatus"  style="margin-right: 33px" type="warning">{{item.tagInfo.tagStatus}}</el-tag>
                            <el-button type="text" class="signBtn" @click="getSign(item,index)"><i class="signIcon"></i>标记({{item.tagInfo.count}})<i class="el-icon-arrow-right"></i></el-button>
                            <el-button type="primary" class="over" @click="over(item)">完结</el-button>
                        </div>
                        <div style="margin-top: 9px;text-align: right;" v-show="item.busiStatus=='已完成'&&!item.evaluated">
                            <el-button type="text" class="signBtn" icon="el-icon-upload" style="margin-right: 18px" @click="showUploadContract(item)">合同上传</el-button>
                            <el-tag v-if="item.tagInfo.tagStatus"  style="margin-right: 33px" type="warning">{{item.tagInfo.tagStatus}}</el-tag>
                            <el-button type="text" class="signBtn" @click="getSign(item,index)"><i class="signIcon"></i>标记({{item.tagInfo.count}})<i class="el-icon-arrow-right"></i></el-button>
                            <%--<el-button type="text" class="comment_btn" @click="fold(item,index)" style="margin-left: 33px"><i class="comment_icon"></i>评价<i class="el-input__icon el-icon-arrow-down" :class="{'is-reverse':item.commentShow}"></i></el-button>--%>
                        </div>
                        <div style="margin-top: 9px;text-align: right;" v-show="item.busiStatus=='已完成'&&item.evaluated">
                            <el-button type="text" class="signBtn" icon="el-icon-upload" style="margin-right: 18px" @click="showUploadContract(item)">合同上传</el-button>
                            <el-tag v-if="item.tagInfo.tagStatus"  style="margin-right: 33px" type="warning">{{item.tagInfo.tagStatus}}</el-tag>
                            <el-button type="text" class="signBtn" @click="getSign(item,index)"><i class="signIcon"></i>标记({{item.tagInfo.count}})<i class="el-icon-arrow-right"></i></el-button>
                            <el-button type="text" class="comment_btn" @click="fold(item,index)" style="margin-left: 33px"><i class="comment_icon commented"></i>{{item.commentShow?'收起评价':'已评价，查看'}}<i class="el-input__icon el-icon-arrow-down" :class="{'is-reverse':item.commentShow}"></i></el-button>
                        </div>
                    </div>
                    <div class="comment_container" v-show="item.commentShow&&!item.evaluated">
                        <p>发布评价</p>
                        <table>
                            <tr>
                                <td style="vertical-align: middle">综合评价：</td>
                                <td>
                                    <el-radio class="radio" v-model="item.commentType"  label="5" :name="'commentType_'+item.id">好评</el-radio>
                                    <el-radio class="radio" v-model="item.commentType"  label="3" :name="'commentType_'+item.id">中评</el-radio>
                                    <el-radio class="radio" v-model="item.commentType"  label="1"  :name="'commentType_'+item.id">差评</el-radio>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding-top: 17px">增加描述：</td>
                                <td>
                                    <el-input
                                        type="textarea"
                                        :rows="2"
                                        :id="'commentText_'+item.id"
                                        placeholder="请输入描述"
                                        v-model="item.commentText">
                                    </el-input>
                                </td>
                            </tr>
                        </table>
                        <el-row style="text-align: center;margin-top: 20px">
                            <el-button type="primary" class="over" @click="comment(item,index)">发布评论</el-button>
                            <el-button @click="fold(item,index)">取消</el-button>
                        </el-row>
                    </div>
                    <div class="comment_container" v-show="item.commentShow&&item.evaluated">
                        <p>查看评价</p>
                        <table v-for="(comment,i) in item.commentList">
                            <thead>
                                <tr>
                                    <%--<th colspan="2">{{comment.evaluatedBy==myEntId?'我发布的评价':'对我的评价'}}</th>--%>
                                    <th colspan="2">对我的评价</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td style="vertical-align: middle">综合评价：</td>
                                    <td>
                                        {{COMMENT[comment.score]}}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="vertical-align: middle;">增加描述：</td>
                                    <td>
                                        {{comment.comment}}
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div style="padding: 50px 0;text-align: center" v-show="!list||list.length==0">
                    暂无相关数据
                </div>
                <div v-show="list&&list.length>0" align="right">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[5,10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                    </el-pagination>
                </div>

                <el-dialog
                        class="signDialog animated bounceInRight"
                        :visible.sync="signDialogVisible"
                        :modal-append-to-body="false"
                        :modal="false"
                        :before-close="closeSignDialog"
                        width="320px">
                    <span slot="title" class="title">标记</span>
                    <div class="entInfo">
                        <p><em>单位：</em><b>{{signItem.releaseEntName}}</b></p>
                        <p><em>总数：</em><b><font color="#1171d1">{{signItem.totalAmount}}</font></b></p>
                        <p><em>价格：</em><b><font color="#ff474c">￥{{signItem.totalPrice}}</font></b></p>
                    </div>
                    <div class="editArea">
                        <el-button-group>
                            <el-select v-model="signType" placeholder="请选择标记类型" style="float: left">
                                <el-option
                                        v-for="item in signTypeList"
                                        :key="item.code"
                                        :label="item.value"
                                        :value="item.code">
                                </el-option>
                            </el-select>
                            <el-button type="primary" @click="showSignForm">新增标记</el-button>
                        </el-button-group>
                        <div class="sign_form" v-if="signFormShow">
                            <div class="sign_form_title">添加{{SIGNTYPE[signType]}}标记</div>
                            <div class="sign_form_content">
                                <el-input v-if="signType=='TAGING'"
                                          type="textarea"
                                          :rows="2"
                                          placeholder="说点什么吧"
                                          v-model="say">
                                </el-input>
                                <div v-if="signType=='SAMPLING'" align="center">
                                    <el-date-picker
                                            v-model="sampleDate"
                                            type="date"
                                            align="right"
                                            placeholder="请选择取样时间">
                                    </el-date-picker>
                                    <el-input v-model="sampleName" placeholder="请输入联系人姓名"></el-input>
                                    <el-input v-model="sampleMobile" placeholder="请输入联系人电话"></el-input>
                                </div>
                                <div v-if="signType=='SAMPLE'">
                                    <el-radio-group v-model="sampleStatus">
                                        <el-radio label="SAMPLED">已取样</el-radio>
                                        <el-radio label="DELIVERED_LAB">已送实验室</el-radio><br/>
                                        <el-radio label="QUALIFIED" style="margin-top: 10px">合格</el-radio>
                                        <el-radio label="UNQUALIFIED" style="margin-top: 10px;margin-left: 44px">不合格</el-radio>
                                    </el-radio-group>
                                    <el-input type="textarea" style="margin-top: 10px"
                                              :rows="2"
                                              placeholder="备注"
                                              v-model="sampleRemark">
                                    </el-input>
                                </div>
                                <el-radio-group v-model="contractStatus" v-if="signType=='CONTRACT'">
                                    <el-radio label="DRAFTCONTRACT">合同已拟定</el-radio>
                                    <el-radio label="DELIVERYCONTRACT">合同已寄出</el-radio><br/>
                                    <el-radio label="SIGNCONTRACT" style="margin-top: 10px">合同已签定</el-radio>
                                </el-radio-group>
                                <el-input v-if="signType=='PRICE'"
                                          type="textarea"
                                          :rows="2"
                                          placeholder="备注"
                                          v-model="priceRemark">
                                </el-input>
                            </div>
                            <div class="sign_form_btn">
                                <el-button type="primary" size="small" style="height: 30px" @click="saveSign">保存</el-button>
                            </div>
                        </div>
                    </div>
                    <div class="signTimeLine" v-loading="signDialogloading"
                         element-loading-spinner="el-icon-loading"
                         element-loading-background="rgba(246, 247, 251, 0.8)">
                        <el-steps direction="vertical" :active="1" v-if="signList.length>0">
                            <el-step v-for="(item,index) in signList">
                                <div slot="title" class="signTitle">
                                    <span class="signTime">{{item.createTime.substring(0,16)}}</span>
                                    <span class="signCreate">{{item.createBy}}</span>
                                    <el-button type="text" class="el-icon-delete" title="删除该标记" @click="delSign(item.id)"></el-button>
                                </div>
                                <div slot="description" class="signContent" v-if="item.tagType=='TAGING'">
                                    {{item.comments}}
                                </div>
                                <div slot="description" class="signContent" v-if="item.tagType=='SAMPLING'">
                                    取样时间：{{item.sampleDate?item.sampleDate.substring(0,10):''}}<br/>
                                    联系人：{{item.contacts}}<br/>
                                    联系电话：{{item.contactsTel}}
                                </div>
                                <div slot="description" class="signContent" v-if="item.tagType=='SAMPLE'">
                                    <div v-if="item.comments&&item.comments.split('#').length>1">
                                        <div>{{item.comments.split('#')[0]}}</div>
                                        <div><span style="font-size: 12px;color: #6b7580;">备注：</span>{{item.comments.split('#')[1]}}</div>
                                    </div>
                                    <span v-else>{{item.comments}}</span>
                                </div>
                                <div slot="description" class="signContent" v-if="item.tagType=='CONTRACT'">
                                    {{TAGSTATUS[item.busiStatus]}}
                                </div>
                                <div slot="description" class="signContent" v-if="item.tagType=='TERMINATE'">
                                    {{TAGSTATUS[item.tagType]}}
                                </div>
                                <div slot="description" class="signContent" v-if="item.tagType=='PRICE'">
                                    <div v-if="item.comments&&item.comments.split('#').length>1">
                                        <div>{{item.comments.split('#')[0]}}</div>
                                        <div><span style="font-size: 12px;color: #6b7580;">备注：</span>{{item.comments.split('#')[1]}}</div>
                                    </div>
                                    <span v-else>{{item.comments}}</span>
                                </div>
                            </el-step>
                        </el-steps>
                        <span v-else style="display: inline-block;text-align: center;width: 100%;">暂无标记信息</span>
                    </div>
                </el-dialog>
                <el-dialog
                        title="合同上传"
                        :visible.sync="imgUploadVisible"
                        width="600px">
                    <el-upload
                            class="upload-demo"
                            ref="contractUpload"
                            drag
                            :data="contractParam"
                            :action="uploadAction"
                            :on-success="contractUploadSuccess"
                            :auto-upload="false"
                            :file-list="fileList"
                            :on-remove="handleRemove"
                            :on-preview="downloadFile"
                            :limit="1"
                    >
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                    </el-upload>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="imgUploadVisible = false">取 消</el-button>
                        <el-button type="primary" @click="uploadContract">确 定</el-button>
                      </span>
                </el-dialog>
            </template>
        </div>
        </body>
        <!-- 先引入 Vue -->
        <script src="<%=appPath%>/main/common/elementui/vue.js"></script>
        <!-- 引入组件库 -->
        <script src="<%=appPath%>/main/common/elementui/index.js"></script>
        <script src="<%=appPath %>/app/js/constants.js"></script>
        <script src="<%=appPath%>/main/common/upload/upload.js"></script>
        <script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
        <script>
            var vue=new Vue({
                el: '#app',
                data: {
                    list:[],
                    total: null,
                    listLoading: true,
                    tableData:[],
                    statusClass:orderStatusClass,
                    pageIndex:1,
                    pageSize:5,
                    orderStatus:'',
                    dateType:'',
                    startDateTime:'',
                    endDateTime:'',
                    managerType:'',
                    activityId:'',
                    managerList: [],
                    manager: '',
                    entList:[],
                    activityList:[],
                    entId:'',
                    myEntId:'',
                    COMMENT:COMMENT,
                    orderTotal:null,
                    signDialogVisible:false,
                    signDialogloading:false,
                    signType:'',
                    signTypeList:[],
                    SIGNTYPE:SIGNTYPE,
                    say:'',
                    sampleDate:'',
                    sampleName:'',
                    sampleMobile:'',
                    sampleStatus:'DELIVERED_LAB',
                    signFormShow:false,
                    signItem:{},
                    signList:[],
                    TAGSTATUS:TAGSTATUS,
                    contractStatus:'DRAFTCONTRACT',
                    imgUploadVisible:false,
                    uploadAction:upload.IMG_URL+'/upload',
                    contractParam:{'appKey':APPKEY,'prodID':PRODID,businessCode:''},
                    fileList:[],
                    priceRemark:''
                },
                created: function () {
                    this.getList();
                    this.getManagerList();
                    this.getEntList();
                    this.getActivityList();
                },
                methods: {
                    getList: function () {
                        this.listLoading = true;
                        var _this = this;
                        var param=this.getParamInfo()||{};
                        param.pageIndex=this.pageIndex;
                        param.pageSize=this.pageSize;
                        ajax({
                            url: '/entOrders/listOrderByInquiryEntId.htm',
                            dataType: 'json',
                            data:param,
                            success: function (result) {
                                console.log(result);
                                _this.listLoading = false;
                                if (result.status == 1) {
                                    var orderList=result.data.orderList;
                                    for(var i in orderList){
                                        orderList[i]['commentShow']=false;
                                        orderList[i]['commentType']='good';
                                        orderList[i]['commentText']='';
                                        orderList[i]['commentLoaded']=false;
                                        orderList[i]['commentList']=[];
                                    }
                                    _this.list=orderList;
                                    _this.total=result.data.paging.totalRecord;
                                }
                            }
                        })
                    },
                    getOrderTotal:function () {
                        var _this = this;
                        var param=this.getParamInfo()||{};
                        ajax({
                            url: '/entOrders/countOrderWasteAmount4InquiryEnt.htm',
                            dataType: 'json',
                            data:param,
                            success: function (result) {
                                console.log(result);
                                if (result.status == 1) {
                                    _this.orderTotal=result.data;
                                }
                            }
                        })
                    },
                    getParamInfo:function () {
                        var param={
                            orderStatus:this.orderStatus,
                            dateType: this.dateType,
                            startDateTime:this.startDateTime?this.startDateTime.format('yyyy-MM-dd hh:mm:ss'):'',
                            endDateTime:this.endDateTime?(this.endDateTime.format('yyyy-MM-dd')+' 23:59:59'):'',
                            inquiryPersonId:this.manager,
                            activityId:this.activityId,
                            releaseEntId:this.entId=='all'?'':this.entId
                        }
                        return param;
                    },
                    getManagerList:function () {
                        var _this=this;
                        ajax({
                            url:'/entOrders/listInquiryPerson',
                            dataType: 'json',
                            success:function (data) {
                                _this.managerList=data.data;
                            }
                        });
                    },
                    showSignForm:function () {
                        if(this.signType==''){
                            $.notify('请选择标记类型',{status:'info',timeout:2500});
                            return;
                        }
                        this.signFormShow=true;
                    },
                    delSign:function (id) {
                        ajax({
                            url:'/discussTag/deleteDiscussTag',
                            data:{
                                id:id
                            },
                            success:function (result) {
                                if(result.status&&result.data){
                                    $.notify('删除标记成功',{status:'success',timeout:2500});
                                    vue.getSignList();
                                }
                            }
                        });
                    },
                    saveSign:function () {
                        var param={
                            releaseId:this.signItem.releaseId,
                            tagType:this.signType
                        }
                        switch (this.signType){
                            case 'TAGING':
                                param.comments=this.say;
                                param.tagStatus='TAGGING';
                                break;
                            case 'SAMPLING':
                                param.sampleDate=this.sampleDate;
                                param.contacts=this.sampleName;
                                param.contactsTel=this.sampleMobile;
                                param.tagStatus='SAMPLING';
                                break;
                            case 'SAMPLE':
                                param.tagStatus=this.sampleStatus;
                                param.comments=this.TAGSTATUS[this.sampleStatus]+'#'+this.sampleRemark;
                                break;
                            case 'CONTRACT':
                                param.tagStatus=this.contractStatus;
                                break;
                            case 'TERMINATE':
                                param.tagStatus='TERMINATE';
                                break;
                            case 'PRICE':
                                param.tagStatus='PRICE';
                                param.comments='报价#'+this.priceRemark;
                                break;
                        }
                        ajax({
                            url:'/discussTag/saveDiscussTag.htm?ticketId=<%=ticketId%>',
                            data:JSON.stringify(param),
                            contentType:'application/json',
                            success:function (result) {
                                if(result.status&&result.data){
                                    $.notify('添加标记成功',{status:'success',timeout:2500});
                                    vue.getSignList();
                                    vue.signFormShow=false;
                                }
                            }
                        });
                    },
                    getSignList:function () {
                        vue.signList=[];
                        this.signDialogloading=true;
                        ajax({
                            url:'/discussTag/listDiscussTag',
                            data:{
                                releaseId:this.signItem.releaseId
                            },
                            success:function (result) {
                                vue.signDialogloading=false;
                                console.log(result);
                                if(result.status==1){
                                    vue.signList=result.data;
                                    var busiStatus='';
                                    if(result.data.length>0){
                                        busiStatus=result.data[0]['busiStatus'];
                                    }
                                    if(!vue.signItem.tagInfo){
                                        vue.signItem.tagInfo={'count':0,tagStatus:''};
                                    }
                                    vue.signItem.tagInfo.tagStatus=TAGSTATUS[busiStatus];
                                    vue.signItem.tagInfo.count=result.data.length;
                                    Vue.set(vue.list,vue.signItem.index,vue.signItem);
                                }
                            }
                        });
                    },
                    getSign:function (item,index) {
                        item.index=index;
                        this.signItem=item;
                        this.signDialogVisible=true;
                        this.signFormShow=false;
                        this.signType='';
                        $('body').css('overflow-y','hidden');
                        var heights=document.body.clientHeight;
                        if(this.signTypeList.length==0){
                            this.getSignTypeList();
                        }
                        this.getSignList();
                        setTimeout(function () {
                            $('.signTimeLine').css('max-height',heights-353+'px');
                        },300)
                    },
                    closeSignDialog:function () {
                        this.signDialogVisible=false;
                        setTimeout(function () {
                            $('body').css('overflow-y','auto');
                        },500);
                    },
                    getSignTypeList:function () {
                        var _this=this;
                        ajax({
                            url:'/codeType/listCodeValue',
                            data:{
                                typeCode:'TAG_TYPE'
                            },
                            success:function (result) {
                                console.log(result);
                                if(result.status==1){
                                    _this.signTypeList=result.data;
                                }
                            }
                        })
                    },
                    getEntList:function () {
                        var _this=this;
                        ajax({
                            url:'/entOrders/listReleaseEnt',
                            dataType: 'json',
                            success:function (data) {
                                var obj={'entName':'全部企业',entId:'all'};
                                var arr=[obj];
                                arr=arr.concat(data.data);
                                _this.entList=arr;
                            }
                        });
                    },
                    getActivityList:function(){
                        var _this=this;
                        ajax({
                            url:'/wasteActivity/listOrderActiviyName',
                            dataType: 'json',
                            success:function (data) {
                                var obj={'activityName':'全部',activityId:''};
                                var defaultObj ={'activityName':'资源池',activityId:'resourcePull'};
                                var arr=[obj,defaultObj];
                                arr=arr.concat(data.data);
                                _this.activityList = arr;
                            }
                        });
                    },
                    entDetail:function (entId,facilitatorEntId) {
                        var facilitatorEntId=facilitatorEntId?('&facilitatorEntId='+facilitatorEntId):'';
                        window.location='<%=appPath%>/myenterprise/viewEnterpriseDetail.htm?ticketId=<%=ticketId%>&onlyViewFlg=true&enterpriseId='+entId+'&breadcrumbName=平台订单&index=myInquiryOrders'+facilitatorEntId;
                    },
                    viewActivityDetail:function (activityId) {
                        window.location='<%=appPath%>/main/pc/view/wasteActivityDetail.html?ticketId=<%=ticketId%>&activityId='+activityId;
                    },
                    search:function () {
                        this.pageIndex=1;
                        this.list=[];
                        this.getList();
                        this.getOrderTotal();
                    },
                    queryStatus:function (status) {
                        this.orderStatus=status;
                        this.search();
                    },
                    queryManagerType:function (managerType) {
                      this.managerType=managerType;
                      if(this.managerType==''){
                          this.manager='';
                      }else if(this.managerType=='my'){
                          this.manager=window.globalInit.userId;
                      }
                      this.managerChange();
                    },
                    managerChange:function () {
                        if(this.manager==''){
                            this.managerType='';
                        }else if(this.manager==window.globalInit.userId){
                            this.managerType='my';
                        }else{
                            this.managerType='other';
                        }
                      this.search();
                    },
                    entChange:function () {
                        this.search();
                    },
                    activityNameChange:function(){
                        this.search();
                    },
                    fold:function(item,index){
                      item.commentShow=!item.commentShow;
                      if(item.evaluated&&item.commentShow&&!item.commentLoaded){
                          if(!this.myEntId){
                              this.myEntId=window.globalInit.enterpriseId;
                          }
                          ajax({
                              url:'/entEvaluate/listEntEvaluate.htm',
                              data:{
                                  orderId:item.id
                              },
                              success:function (data) {
                                  console.log(data);
                                  if(data.status==1){
                                      item.commentList=data.data;
                                      item.commentLoaded=true;
                                  }
                                  Vue.set(vue.list, index, item);
                              }
                          });
                      }else{
                          Vue.set(vue.list, index, item);
                      }
                    },
                    comment:function (item,index) {
                        var id=item.id;
                        var name='commentType_'+id;
                        var commentTypeList=$('[name='+name+']');
                        var commentType='';
                        for(var i in commentTypeList){
                            var commentTypeInput=commentTypeList.eq(i);
                            if(commentTypeInput.parent().hasClass('is-checked')){
                                commentType=commentTypeInput.val();
                            }
                        }
                        var commentText=$('textarea#commentText_'+id).val();
                        console.log(commentType+' '+commentText);
                        if(!commentType){
                            $.notify('请选择综合评价',{status:'info',timeout:1000});
                            return;
                        }
                        if(!commentText){
                            $.notify('请填写评价描述',{status:'info',timeout:1000});
                            return;
                        }
                        ajax({
                            url:'/entEvaluate/addEntEvaluate',
                            data:{
                                orderId:id,
                                score:commentType,
                                comment:commentText,
                                evaluatedEntId:item.releaseEntId,
                                evaluatedBy:item.inquiryEntId
                            },
                            success:function (data) {
                                if(data.status==1&&data.data){
                                    $.notify('评价成功',{status:'success',timeout:1000});
                                    item.evaluated=true;
                                    item.commentShow=false;
                                    Vue.set(vue.list, index, item);
                                }
                            }
                        });
                    },
                    over:function (item) {
//                        console.log(id);
                        vue.$confirm('此操作将完结该订单, 是否确定?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(function(){
                            ajax({
                                url:'/entOrders/closeOrder.htm',
                                data:{
                                    orderId:item.id,
                                    releaseEntId:item.releaseEntId
                                },
                                dataType:'json',
                                success:function (data) {
                                    if(data.status==1&&data.data==true){
                                        $.notify('完结成功',{status:'success',timeout:2500});
                                        vue.pageIndex=1;
                                        vue.getList();
                                    }
                                }
                            })
                        }).catch(function(){});
                    },
                    contact:function (entId,entName) {
                        getEnterpriseContacts(entId,entName,'平台订单');
                    },
                    queryDateType:function (dateType) {
                        this.dateType=dateType;
                        this.startDateTime='';
                        this.endDateTime='';
                        this.search();
                    },
                    changeDate:function () {
                        this.dateType='';
                        this.search();
                    },
                    handleSizeChange:function(val) {
                        this.pageSize = val
                        this.getList()
                    },
                    handleCurrentChange:function(val) {
                        this.pageIndex = val
                        this.getList()
                    },
                    handleFilter:function() {
                        this.getList()
                    },
                    showUploadContract:function (item) {
                        this.$refs.contractUpload&&this.$refs.contractUpload.clearFiles();
                        this.contractParam.businessCode=item.id;
                        this.getContractList(item.id);
                        this.imgUploadVisible=true;
                    },
                    getContractList:function (id) {
                        this.fileList=[];
                        var fileList=upload.getImgListByBusinessCode(id);
                        console.log(fileList);
                        for(var i in fileList){
                            var obj=fileList[i];
                            obj.name=obj.fileName;
                            obj.url=IMG_VIEW_URL+'&fileID='+obj['fileID'];
                            this.fileList.push(obj);
                        }
                    },
                    handleRemove:function (file, fileList) {
                        console.log(file,fileList);
                        upload.delete(file.fileID);
                        this.fileList.splice(0,1);
                        ajax({
                            url:'/entOrders/deleteUploadContract.htm',
                            data:{orderId:this.contractParam.businessCode},
                            success:function (result) {
                                console.log(result);
                                if(result.status==1&&result.data){

                                }
                            }
                        })
                    },
                    downloadFile:function (file) {
                        console.log(file);
                        upload.download(file.fileID);
                    },
                    uploadContract:function () {
                        this.$refs.contractUpload.submit();
                    },
                    contractUploadSuccess:function (response, file, fileList) {
                        if(!file.response.success){
                            $.notify('合同文件上传失败',{status:'danger',timeout:1000});
                            return;
                        }
                        var obj=file.response.message;
                        console.log(obj);
                        $.notify('合同文件上传成功',{status:'success',timeout:2000});
                        this.imgUploadVisible=false;
                        ajax({
                            url:'/entOrders/updateContractStatus.htm',
                            data:{orderId:this.contractParam.businessCode},
                            success:function (result) {
                                console.log(result);
                                if(result.status==1&&result.data){

                                }
                            }
                        })
                    },
                    fmoney:function(s, n) {  //s:传入的float数字 ，n:希望返回小数点几位
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
                }

            })
        </script>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true"/>

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
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