<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="我的报价" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myShoppingCart" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/business.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/sign.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterprise/qualification.css">
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的易废圈</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的报价</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div class="">
        <div id="app">
            <template>
                <el-row :gutter="10" class="searchDiv">
                    <el-col :xs="8" :sm="8" :md="8" :lg="8" align="left">
                        <b>询价状态：</b>
                        <el-button type="text" :class="{'select':inquiryStatus==''}" @click="queryStatus('')">全部</el-button>
                        <el-button type="text" :class="{'select':inquiryStatus=='SUBMIT'}" @click="queryStatus('SUBMIT')">待确认</el-button>
                        <el-button type="text" :class="{'select':inquiryStatus=='ACCEPT'}" @click="queryStatus('ACCEPT')">已成交</el-button>
                        <el-button type="text" :class="{'select':inquiryStatus=='REFUSED'}" @click="queryStatus('REFUSED')">已谢绝</el-button>
                    </el-col>
                    <el-col :xs="16" :sm="16" :md="16" :lg="16" align="right">
                        <b>询价时间：</b>
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
                    <el-col :span="12" align="left">
                        <%--<b>来&nbsp;&nbsp;源：</b>--%>
                        <%--<el-select v-model="activityId" placeholder="选择活动名称" style="margin-left: 12px" @change="activityNameChange()">--%>
                            <%--<el-option v-for="item in activityList" :key="item.activityId" :label="item.activityName" :value="item.activityId">--%>
                            <%--</el-option>--%>
                        <%--</el-select>--%>
                            <b>企业名称：</b>
                            <el-select v-model="releaseEntId" placeholder="选择企业名称" style="margin-left: 12px" @change="releaseEntNameChange()">
                                <el-option v-for="item in releaseEntNames" :key="item.releaseEntId" :label="item.releaseEnterName" :value="item.releaseEntId">
                                </el-option>
                            </el-select>
                    </el-col>
                    <el-col :span="12" align="right">
                        <b>优先级：</b>
                        <el-button type="text" :class="{'select':priority==''}" @click="queryPriority('')">全部</el-button>
                        <el-button type="text" :class="{'select':priority=='URGENT'}" @click="queryPriority('URGENT')">紧急</el-button>
                        <el-button type="text" :class="{'select':priority=='HIGH'}" @click="queryPriority('HIGH')">高</el-button>
                        <el-button type="text" :class="{'select':priority=='GENERAL'}" @click="queryPriority('GENERAL')">一般</el-button>
                        <el-button type="text" :class="{'select':priority=='NONE'}" @click="queryPriority('NONE')">暂不处理</el-button>
                    </el-col>
                </el-row>
                <el-row :gutter="10" class="searchDiv">
                    <el-col :span="12" align="left">

                    </el-col>
                </el-row>
                <div class="buy_item panel panel-body" v-for="(item,index) in list">
                    <el-row :gutter="10" style="margin-bottom:10px">
                        <el-col :xs="18" :sm="18" :md="18" :lg="18">
                            <span class="status" :class="statusClass[item.busiStatus]">{{item.busiStatus}}</span><b style="margin-left: 14px">单位：</b><a href="javascript:" title="查看企业详情" @click="entDetail(item.releaseEntId,item.facilitatorEntId)"><em style="margin-right: 4px">{{item.releaseEnterName}}</em></a>
                            <a class="qipao_logo" href="javascript:" @click="contact(item.facilitatorEntId||item.releaseEntId,item.releaseEnterName)" title="联系TA" style="margin-right: 14px;">
                                <img src="../main/pc/img/qipao.jpg"></a>
                            <b>负责人：</b><em>{{item.inquiryPersonName}}</em>
                            <b>来源：</b><em>
                            <b style="color: #1171d1">
                                <a href="javascript:" style="color: #1171d1" v-if="item.activityName" @click="viewActivityDetail(item.activityId)">{{item.activityName}}</a>
                                <font v-else>资源池</font>
                            </b>
                            </em>
                            <span v-if="item.dispositionName"   >
                                <b> 处置企业：</b>
                               <em>
                                   <b style="color: #1171d1">
                                    {{item.dispositionName}}
                                    </b>
                               </em>
                            </span>

                        </el-col>
                        <el-col :xs="6" :sm="6" :md="6" :lg="6" align="right" style="font-size:12px;color: #6a7580;">
                                {{item.inquiryDate}}
                        </el-col>
                    </el-row>
                    <el-row :gutter="10" style="margin-bottom:10px">
                        <el-col :xs="24" :sm="24" :md="24" :lg="24">
                            <b>报价方式：</b><em>{{item.quotedType==0?'打包报价':'单独报价'}}</em><b>金额：</b><em><b style="color: #ff474c;font-weight:bold">￥{{item.totalPriceStr}}</b></em>
                            <b>总数：</b><em><b style="color: #1171d1">{{getQualityTextBySelection(item.inquiryDetail)}}</b></em>
                            <b>优先级：</b>
                            <em>
                                <span v-show="!item.showPriorityEdit">{{item.priority||'--'}}</span>
                                <el-button type="text" style="color: #1171d1" v-show="!item.showPriorityEdit" @click="showPriorityEdit(index)" style="margin-left:6px;">设置</el-button>
                                <el-select v-model="item.priority" v-show="item.showPriorityEdit" placeholder="选择优先级" style="width: 120px" @change="editpriority(item,index)">
                                    <el-option v-for="(key,value) in priorityList" :key="value" :label="key" :value="value">
                                    </el-option>
                                </el-select>
                            </em>
                            <span v-if="item.releaseUserPhoneNum"   >
                                <b> 联系电话：</b>
                               <em>
                                   <b style="color: #1171d1">
                                    {{item.releaseUserPhoneNum}}
                                    </b>
                               </em>
                            </span>
                        </el-col>
                    </el-row>
                    <table class="el-table inquiry-table" v-if="item.inquiryType=='REFERENCE_PRICE'">
                        <tr>
                            <th class="is-center">序号</th>
                            <th>危废名称</th>
                            <th>危废代码</th>
                            <th class="is-right">处置重量</th>
                            <th class="is-center">处置方式</th>
                            <th class="is-right">处置单价</th>
                            <th class="is-right">单项总价</th>
                            <th class="is-center">价格说明</th>
                        </tr>
                        <tr v-if="item.systemInquiryList&&item.systemInquiryList.length>0" v-for="(item1,index1) in item.systemInquiryList">
                            <td  class="is-center">
                                {{index1+1}}
                            </td>
                            <td>
                                {{item1.wasteName}}
                            </td>
                            <td>
                                {{item1.wasteCode}}
                            </td>
                            <td class="is-right">
                                {{parseFloat(item1.amount)+item1.unitValue}}
                            </td>
                            <td v-if="item1.first" :rowspan="item.priceIdCountObj[item1.priceId]" class="is-center">
                                {{item1.dispositionType}}
                            </td>
                            <td v-if="item1.first" class="is-right" :rowspan="item.priceIdCountObj[item1.priceId]">
                                <span style="color: #ff0000;">￥{{item1.price}}{{['吨','千克','克'].indexOf(item1.unitValue)>-1?'/吨':''}}</span>
                            </td>
                            <td v-if="item1.first" class="is-right" :rowspan="item.priceIdCountObj[item1.priceId]">
                                <span style="color: #ff0000;">￥{{item1.wasteTotalPrice}}</span>
                            </td>
                            <td v-if="item1.first" class="is-center" :rowspan="item.priceIdCountObj[item1.priceId]">
                                <el-tooltip effect="dark" v-if="item1.remark" :content="item1.remark" placement="top-start">
                                    <i class="el-icon-question"></i>
                                </el-tooltip>
                                <span v-else>--</span>
                                <%--{{item1.remark||'--'}}--%>
                            </td>
                        </tr>
                    </table>
                    <el-table :data="item.inquiryDetail" border v-else
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
                                <span v-if="scope.row.price!=0" >{{scope.row.priceStr}}</span>
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
                    <el-row :gutter="10" style="margin-top:10px">
                        <el-col :xs="24" :sm="24" :md="24" :lg="24"><b>备注：</b><em>{{item.inquiryRemark||'--'}}</em></el-col>
                    </el-row>
                    <div style="margin-top: 9px;text-align: right">
                        <%--<el-button type="warning"  icon="el-icon-document" size="small" style="margin-right: 22px;" v-if="isDisFacilitator" @click="viewQualification(item)">给出的资质</el-button>--%>
                        <el-tag v-if="item.tagInfo.tagStatus" style="margin-right: 30px">{{item.tagInfo.tagStatus}}</el-tag>
                        <el-button type="text" class="signBtn" @click="getSign(item,index)"><i class="signIcon"></i>标记({{item.tagInfo.count}})<i class="el-icon-arrow-right"></i></el-button>
                        <el-button plain  @click="delInquiry(item)"  v-show="item.busiStatus=='待确认'">撤销报价</el-button>
                        <el-button plain  @click="requote(item)"  v-if="item.busiStatus=='已谢绝' || item.busiStatus=='待确认'  ">重新报价</el-button>
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
                        <p><em>单位：</em><b>{{signItem.releaseEnterName}}</b></p>
                        <p><em>总数：</em><b><font color="#1171d1">{{signItem.totalAmount}}</font></b></p>
                        <p><em>价格：</em><b><font color="#ff474c">￥{{signItem.totalPriceStr}}</font></b></p>
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
                        :title="dialogTitle"
                        :visible.sync="dialogVisible"
                        width="518px">
                    <div class="qualification">
                        <div class="qualification-title"><span class="item-title-text">服务商资质</span></div>
                        <div class="qualification-imgs">
                            <div class="qualification-img item-img1">
                                <div class="image">
                                    <img :src="qualification.facilitatorBusiLicImg" @click="showLargeImg(qualification.facilitatorBusiLicImg)"/>
                                </div>
                                <div class="qualification-text">营业执照</div>
                            </div>
                            <div  class="qualification-img qualification-img2">
                                <div class="image">
                                    <img :src="qualification.authImg" @click="showLargeImg(qualification.authImg)"/>
                                </div>
                                <div class="qualification-text">授权书</div>
                            </div>
                        </div>
                    </div>
                    <div class="qualification">
                        <div class="qualification-title"><span class="item-title-text">经营单位资质</span></div>
                        <div class="qualification-imgs">
                            <div class="qualification-img item-img1">
                                <div class="image">
                                    <img :src="qualification.busiLicImg" @click="showLargeImg(qualification.busiLicImg)"/>
                                </div>
                                <div class="qualification-text">营业执照</div>
                            </div>
                            <div  class="qualification-img qualification-img2">
                                <div class="image">
                                    <img :src="qualification.licImg" @click="showLargeImg(qualification.licImg)"/>
                                </div>
                                <div class="qualification-text">经营许可证</div>
                            </div>
                        </div>
                    </div>
                </el-dialog>
                <div class="dialog" v-show="showLarge" @click.self="showLarge=false">
                    <a href="javascript:" class="close-dialog" title="关闭" @click="showLarge=false"><i class="el-icon-circle-close-outline"></i></a>
                    <img :src="largeImg" class="largeImg"/>
                </div>
            </template>
        </div>
        </body>
        <!-- 先引入 Vue -->
        <script src="<%=appPath%>/main/common/elementui/vue.js"></script>
        <!-- 引入组件库 -->
        <script src="<%=appPath%>/main/common/elementui/index.js"></script>
        <script src="<%=appPath %>/app/js/constants.js"></script>
        <script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
        <script>
            var vue=new Vue({
                el: '#app',
                data: {
                    list:[],
                    total: null,
                    listLoading: true,
                    tableData:[],
                    statusClass:statusClass,
                    pageIndex:1,
                    pageSize:5,
                    inquiryStatus:'',
                    dateType:'',
                    startDateTime:'',
                    endDateTime:'',
                    activityId:'',
                    releaseEntId:'',
                    priorityList:priorityList,
                    priority:'',
                    activityList:[],
                    releaseEntNames:[],
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
                    priceRemark:'',
                    isDisFacilitator:localStorage.entType=='DIS_FACILITATOR',
                    dialogVisible:false,
                    showLarge:false,
                    largeImg:'',
                    qualification:{},
                    dialogTitle:'',
                    dispositionName:''
                },
                created: function () {
                    this.releaseEntId = getParam('releaseEntId')?getParam('releaseEntId'):'';
                    this.getList();
                    this.getActivityList();
                    this.listReleaseEntName();
                },
                methods: {
                    viewQualification:function (item) {
                        this.dialogTitle=item.inquiryEnterName;
                        ajax({
                            url:'/facilitatorCustomer/getCustomerImg',
                            data:{
                                facilitatorEntId:item.inquiryEntId,
                                customerId:item.entDisEntId
                            },
                            success:function (result) {
                                console.log(result);
                                if(result.status==1){
                                    vue.dialogVisible=true;

                                    vue.qualification={
                                        authImg:result.data.authImg?(IMG_VIEW_URL+'&fileID='+result.data.authImg):'http://www.yifeiwang.com/img/source/upload.png',
                                        busiLicImg:result.data.busiLicImg?(IMG_VIEW_URL+'&fileID='+result.data.busiLicImg):'http://www.yifeiwang.com/img/source/upload.png',
                                        facilitatorBusiLicImg:result.data.facilitatorBusiLicImg?(IMG_VIEW_URL+'&fileID='+result.data.facilitatorBusiLicImg):'http://www.yifeiwang.com/img/source/upload.png',
                                        licImg:result.data.licImg?(IMG_VIEW_URL+'&fileID='+result.data.licImg):'http://www.yifeiwang.com/img/source/upload.png'
                                    }
                                }
                            }
                        });
                    },
                    showLargeImg:function (url) {
                        this.showLarge=true;
                        this.largeImg=url;
                    },
                    getList: function () {
                        this.listLoading = true;
                        var _this = this;
                        var param={
                            pageIndex:this.pageIndex,
                            pageSize:this.pageSize,
                            inquiryStatus:this.inquiryStatus,
                            releaseEntId:this.releaseEntId,
                            dateType: this.dateType,
                            startDateTime:this.startDateTime?this.startDateTime.format('yyyy-MM-dd hh:mm:ss'):'',
                            endDateTime:this.endDateTime?(this.endDateTime.format('yyyy-MM-dd')+' 23:59:59'):'',
                            activityId:this.activityId,
                            priority:this.priority
                        };
                        ajax({
                            url: '/entInquiry/listEntInquiry.htm',
                            dataType: 'json',
                            data:param,
                            success: function (result) {
                                console.log(result);
                                _this.listLoading = false;
                                if (result.status == 1) {
                                    _this.list=result.data.inquiryList;
                                    for(var i in result.data.inquiryList){
                                        var item=result.data.inquiryList[i];
                                        if(localStorage.entType=='DIS_FACILITATOR' && item.inquiryEnterName){
                                            item.dispositionName = item.inquiryEnterName.split('-')[0];
                                        }
                                        if(item['inquiryType']=='REFERENCE_PRICE'){
                                            var inquiryDetail=item.inquiryDetail;
                                            inquiryDetail.sort(function (o1,o2) {
                                                return o1.priceId>o2.priceId?1:-1;
                                            });
                                            var obj={};
                                            for(var j in inquiryDetail){
                                                var o=inquiryDetail[j];
                                                if(!obj[o.priceId]){
                                                    o.first=true;
                                                    obj[o.priceId]=1;
                                                }else{
                                                    obj[o.priceId]+=1;
                                                }
                                            }
                                            item.priceIdCountObj=obj;
                                            item.systemInquiryList=inquiryDetail;
                                        }
                                    }
                                    _this.total=result.data.paging.totalRecord;
                                }
                            }
                        })
                    },
                    getActivityList:function(){
                        var _this=this;
                        ajax({
                            url:'/wasteActivity/listActiviyNameByInquiryEntId',
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
                    listReleaseEntName:function(){
                        var _this=this;
                        ajax({
                            url:'/entInquiry/listReleaseEntNameByEntId',
                            dataType: 'json',
                            success:function (data) {
                                var obj={'releaseEnterName':'全部',releaseEntId:''};
                                var arr=[obj];
                                arr=arr.concat(data.data);
                                _this.releaseEntNames = arr;
                                _this.releaseEntNames.value4 = this.releaseEntId;
                            }
                        });
                    },
                    requote:function (item) {
                        if(localStorage.entType=='DIS_FACILITATOR'){
                            var src = appPath + '/entRelease/cfBuy_facilitator.htm?ticketId=<%=ticketId%>&releaseId=' + item.releaseId + "&breadcrumbName=我的报价";
                        }else{
                            var src = appPath + '/entRelease/cfBuy.htm?ticketId=<%=ticketId%>&releaseId=' + item.releaseId + "&breadcrumbName=我的报价";
                        }
                        window.location = src;
                    },
                    activityNameChange:function(){
                        this.search();
                    },
                    releaseEntNameChange:function(){
                        this.search();
                    },
                    entDetail:function (entId,facilitatorEntId) {
                        var facilitatorEntId=facilitatorEntId?('&facilitatorEntId='+facilitatorEntId):'';
                        window.location='<%=appPath%>/myenterprise/viewEnterpriseDetail.htm?ticketId=<%=ticketId%>&onlyViewFlg=true&enterpriseId='+entId+'&breadcrumbName=我的询价'+facilitatorEntId;
                    },
                    search:function () {
                        this.pageIndex=1;
                        this.list=[];
                        this.getList();
                    },
                    queryStatus:function (status) {
                        this.inquiryStatus=status;
                        this.search();
                    },
                    queryPriority:function (status) {
                        this.priority=status;
                        this.search();
                    },
                    showPriorityEdit:function (index) {
                        var item=this.list[index];
                        item.showPriorityEdit=true;
                        Vue.set(this.list,index,item);
                    },
                    editpriority:function (item,index) {
                        ajax({
                            url:'/entInquiry/updateEntInquiryPriority.htm',
                            data:{
                                inquiryId:item.inquiryId,
                                priority:item.priority
                            },
                            success:function (result) {
                                if(result.status==1&&result.data){
                                    $.notify('分配成功',{status:'success',timeout:2500});
                                    item.priority=vue.priorityList[item.priority];
                                    item.showPriorityEdit=false;
                                    Vue.set(vue.list,index,item);
                                }
                            }
                        });
                    },
                    contact:function (entId,releaseEnterName) {
                        getEnterpriseContacts(entId,releaseEnterName,'处置单位我的报价');
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
                        this.pageSize = val;
                        this.getList()
                    },
                    handleCurrentChange:function(val) {
                        this.pageIndex = val;
                        this.getList()
                    },
                    handleFilter:function() {
                        this.getList()
                    },
                    delInquiry:function (item) {
                        var inquiryId=item.inquiryId;
                        var releaseId=item.releaseId;
                        vue.$confirm('此操作将撤销该报价, 是否确定?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(function(){
                            ajax({
                                url:'/entInquiry/deleteEntInquiry',
                                data:{
                                    inquiryId:inquiryId,
                                    releaseId:releaseId
                                },
                                dataType:'json',
                                success:function (data) {
                                    if(data.status==1&&data.data==true){
                                        $.notify('撤销报价成功',{status:'success',timeout:2500});
                                        vue.pageIndex=1;
                                        vue.getList();
                                    }
                                }
                            })
                        }).catch(function(){});
                    },
                    viewActivityDetail:function (activityId) {
                        window.location='<%=appPath%>/main/pc/view/wasteActivityDetail.html?ticketId=<%=ticketId%>&activityId='+activityId;
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
                        };
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
                    getQualityTextBySelection:function(arr) {
                        var obj = {};
                        var laNumber = new $.LaNumber();
                        for(var i in arr) {
                            var key = arr[i]['unitValue'];
                            var value = parseFloat(arr[i]['amount']);
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
                        return str;
                    }
                }

            })
        </script>
    </div>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true"/>

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function () {
        var rs = window.globalInit();
        rs.done(function () {

        });
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