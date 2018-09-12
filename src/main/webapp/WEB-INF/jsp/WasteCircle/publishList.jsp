<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="我的询价" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myRelease" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/wastecircle/publish.css">
<link rel="stylesheet" href="<%=appPath%>/css/myBusiness/business.css">
<%--<link rel="stylesheet" href="<%=appPath%>/css/enterprise/qualification.css">--%>
<script type="text/javascript" src="<%=appPath%>/thirdparty/city-picker/js/city-picker.data.js"></script>
<style>
    .el-popover{
        border: 1px solid #ff0000;
        padding: 8px 12px;
        transform-origin: center bottom 0px;
        z-index: 2041;
        position: absolute;
        margin-top: -77px;
        right: 7px;
    }
    .el-popper[x-placement^=top] .popper__arrow{
        border-top-color: #ff0000;
    }
</style>
<section id="app">
    <template>
    <el-breadcrumb separator="/">
        <el-breadcrumb-item>我的易废圈</el-breadcrumb-item>
        <el-breadcrumb-item onclick="vue.detailShow=false">我的询价</el-breadcrumb-item>
        <el-breadcrumb-item v-if="detailShow">报价详情</el-breadcrumb-item>
    </el-breadcrumb>
    <div>
        <el-dialog
                :title="dialogTitle"
                :visible.sync="dialogVisible"
                width="518px">
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
        </el-dialog>
        <div class="dialog" v-show="showLarge" @click.self="showLarge=false">
            <a href="javascript:" class="close-dialog" title="关闭" @click="showLarge=false"><i class="el-icon-circle-close-outline"></i></a>
            <img :src="largeImg" class="largeImg"/>
        </div>
        <%--<div style="margin-bottom: 20px">
                <b>来&nbsp;&nbsp;源：</b>
                <el-select v-model="activityId" placeholder="选择活动名称" style="margin-left: 12px" @change="activityNameChange()">
                    <el-option v-for="item in activityList" :key="item.activityId" :label="item.activityName" :value="item.activityId">
                    </el-option>
                </el-select>
        </div>--%>
        <div>
                <div class="publish_container" v-show="!detailShow">
                    <div class="item panel panel-body" v-for="(item,index) in list">
                        <div class="item_message">
                            <el-row :gutter="10" style="margin-bottom:10px">
                                <el-col :xs="17" :sm="17" :md="17" :lg="17"><b>询价者：</b>{{item.releaseOwner||'--'}}<span class="split">|</span><b>总计：</b><font>{{item.totalWasteCount}}</font>种危废，累计<font>{{item.totalWasteAmountDesc}}</font>
                                    <span class="split">|</span><b>来源：</b>
                                    <a href="javascript:" style="color: #1171d1" v-if="item.activityName" @click="viewActivityDetail(item.activityId)">{{item.activityName}}</a>
                                    <font v-else>危废处置询价</font>
                                </el-col>
                                <el-col :xs="7" :sm="7" :md="7" :lg="7" align="right" style="font-size:12px"><i class="time_icon"></i>{{item.releaseDate.substring(0,16)}}</el-col>
                            </el-row>
                            <div class="itemReleaseList">
                                <div class="itemRelease" v-for="(wasteItem,index) in item.releaseWasteDetails">
                                    <span>{{wasteItem.wasteName}}</span><span>{{wasteItem.wasteCode}}</span><span>{{wasteItem.wasteAmount}}{{wasteItem.unitValue}}</span>
                                </div>
                            </div>
                            <div style="margin-top: 9px;text-align: right">
                                <el-button type="text" style="margin-right: 20px;" v-if="!item.activityName&&item.releaseStatus!='END'" title="完结" @click="close(item,index)">完结</el-button>
                                <el-tag v-if="!item.activityName&&item.releaseStatus=='END'" style="margin-right: 20px;" type="warning">已完结</el-tag>
                                <el-button type="text" style="margin-right: 20px;" v-if="item.releaseStatus!='DONE'&&item.releaseStatus!='REFUSED'" title="撤销询价" @click="del(item.releaseId)">撤销询价</el-button>
                                <div class="el-popover el-popper el-popover--plain"  x-placement="top" v-if="item.pendingProcessCount>0">
                                    你有{{item.pendingProcessCount}}个报价未处理
                                    <div x-arrow="" class="popper__arrow" style="left: 87.5px;"></div>
                                </div>
                                <el-button type="text" slot="reference" @click="inquiryDetail(index)" style="width:85px"  v-if="item.inquiryEntCount>0">查看报价({{item.inquiryEntCount}})</el-button>
                                <%--<el-popover v-if="index!=0"--%>
                                        <%--placement="top"--%>
                                        <%--&lt;%&ndash;width="200"&ndash;%&gt;--%>
                                        <%--trigger="click"--%>
                                        <%--content="你有2个报价未处理">--%>
                                    <%--<el-button type="text" slot="reference" style="width:85px"  v-if="item.inquiryEntCount>0">查看报价({{item.inquiryEntCount}})</el-button>--%>
                                <%--</el-popover>--%>
                                <el-button type="text" style="color:#cccccc;width:85px;cursor: default" v-if="item.inquiryEntCount==0">{{item.releaseStatus=='REFUSED'?'被谢绝':'暂无报价'}}</el-button>
                            </div>
                        </div>
                    </div>
                    <div v-show="!list||list.length==0" style="padding: 20px;text-align: center"><span>暂无相关数据</span></div>
                    <div class="loading" v-show="listLoading"><img src="<%=appPath %>/main/pc/img/load.gif"></div>
                    <div v-show="list&&list.length>0" align="right">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="pageIndex" :page-sizes="[5,10,20,30,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                        </el-pagination>
                    </div>
                </div>
                <div class="inquiry_list" v-show="detailShow" v-if="list.length>0&&list[index]">
                    <div class="item panel panel-body" style="margin-bottom: 10px">
                        <div class="item_message">
                            <el-row :gutter="10" style="margin-bottom:10px">
                                <el-col :xs="17" :sm="17" :md="17" :lg="17"><b>发布者：</b>{{list[index].releaseOwner||'--'}}<span class="split">|</span><b>总计：</b><font>{{list[index].totalWasteCount}}</font>种危废，累计<font>{{list[index].totalWasteAmountDesc}}</font>
                                    <span class="split">|</span >来源：
                                    <a href="javascript:" style="color: #1171d1" v-if="list[index].activityName" @click="viewActivityDetail(list[index].activityId)">{{list[index].activityName}}</a>
                                    <font v-else>资源池</font>
                                </el-col>
                                <el-col :xs="7" :sm="7" :md="7" :lg="7" align="right" style="font-size:12px"><i class="time_icon"></i>{{list[index].releaseDate.substring(0,16)}}</el-col>
                            </el-row>
                            <div class="itemReleaseList">
                                <div class="itemRelease" v-for="(wasteItem,index) in list[index].releaseWasteDetails">
                                    <span>{{wasteItem.wasteName}}</span><span>{{wasteItem.wasteCode}}</span><span>{{wasteItem.wasteAmount}}{{wasteItem.unitValue}}</span>
                                </div>
                            </div>
                            <div style="margin-top: 9px;text-align: right">
                                <el-button type="text" style="margin-right: 20px;"  v-if="!list[index].activityName&&list[index].releaseStatus!='END'" title="完结" @click="close(list[index],index)">完结</el-button>
                                <el-tag v-if="!list[index].activityName&&list[index].releaseStatus=='END'" style="margin-right: 20px;" type="warning">已完结</el-tag>
                                <el-button type="text" style="margin-right: 20px;" v-if="list[index].releaseStatus!='DONE'&&list[index].releaseStatus!='REFUSED'"  @click="del(list[index].releaseId)">撤销询价</el-button>
                                <el-button type="text" slot="reference"  style="width:85px" @click="inquiryDetail(index)" v-if="list[index].inquiryEntCount>0">查看报价({{list[index].inquiryEntCount}})</el-button>
                                <el-button type="text" style="color:#cccccc;width:85px;cursor: default" v-if="list[index].inquiryEntCount==0">暂无报价</el-button>
                            </div>
                        </div>
                    </div>
                    <div v-if="inquiryList.length>0" style="margin: -9px 10px 0;">
                        <div class="buy_item panel panel-body" v-for="item in inquiryList">
                            <el-row  style="margin-bottom:10px">
                                <el-col :xs="21" :sm="21" :md="21" :lg="21">
                                    <span  >{{item.index}}</span><b style="margin-left: 14px">
                                    单位：</b><a href="javascript:" title="查看企业详情" @click="entDetail(item.inquiryEntId,item.inquiryEnterType)"><em style="margin-right: 4px">{{item.inquiryEnterName}}</em></a>
                                    <a class="qipao_logo" href="javascript:" @click="contact(item.inquiryEntId,item.inquiryEnterName)" title="联系TA" style="margin-right: 14px;">
                                        <img src="../main/pc/img/qipao.jpg"></a>
                                    <b>报价方式：</b><em>{{item.quotedType==0?'打包报价':'单独报价'}}</em>
                                    <b v-if="item.inquiryEnterType!=='DIS_FACILITATOR'">金额：</b><em v-if="item.inquiryEnterType!=='DIS_FACILITATOR'"><b style="color: #ff474c;font-weight:bold">￥{{item.totalPriceStr}}</b></em>
                                    <b v-if="item.inquiryEnterType!=='DIS_FACILITATOR'">总数：</b><em v-if="item.inquiryEnterType!=='DIS_FACILITATOR'"><b style="color: #1171d1">{{getQualityTextBySelection(item.inquiryDetail)}}</b></em>
                                </el-col>
                                <el-col :xs="3" :sm="3" :md="3" :lg="3" align="right" style="font-size:12px;color: #6a7580;">
                                    {{item.inquiryDate.substring(0,16)}}
                                </el-col>
                            </el-row>
                            <div v-if="item.inquiryEnterType=='DIS_FACILITATOR'" style="margin-bottom:10px">
                                <b>金额：</b><em><b style="color: #ff474c;font-weight:bold">￥{{item.totalPriceStr}}</b></em>
                                <b>总数：</b><em><b style="color: #1171d1">{{getQualityTextBySelection(item.inquiryDetail)}}</b></em>
                            </div>
                            <table class="el-table inquiry-table" v-if="item.inquiryType=='REFERENCE_PRICE'">
                                <tr>
                                    <th class="is-center">序号</th>
                                    <th>危废名称</th>
                                    <th style="width: 96px">危废代码</th>
                                    <th class="is-right">处置重量</th>
                                    <th class="is-center">处置方式</th>
                                    <th class="is-right">处置单价</th>
                                    <th class="is-right">单项总价</th>
                                    <th class="is-center">价格说明</th>
                                </tr>
                                <tr v-if="systemInquiryList.length>0" v-for="(item,index) in systemInquiryList">
                                    <td  class="is-center">
                                        {{index+1}}
                                    </td>
                                    <td>
                                        {{item.wasteName}}
                                    </td>
                                    <td>
                                        {{item.wasteCode}}
                                    </td>
                                    <td class="is-right">
                                        {{parseFloat(item.amount)+item.unitValue}}
                                    </td>
                                    <td v-if="item.first" :rowspan="priceIdCountObj[item.priceId]" class="is-center">
                                        {{item.dispositionType}}
                                    </td>
                                    <td v-if="item.first" class="is-right" :rowspan="priceIdCountObj[item.priceId]">
                                        <span style="color: #ff0000;">￥{{item.price}}{{['吨','千克','克'].indexOf(item.unitValue)>-1?'/吨':''}}</span>
                                    </td>
                                    <td v-if="item.first" class="is-right" :rowspan="priceIdCountObj[item.priceId]">
                                        <span style="color: #ff0000;">￥{{item.wasteTotalPrice}}</span>
                                    </td>
                                    <td v-if="item.first" class="is-center" :rowspan="priceIdCountObj[item.priceId]">
                                        <el-tooltip effect="dark" v-if="item.remark" :content="item.remark" placement="top-start">
                                            <i class="el-icon-question"></i>
                                        </el-tooltip>
                                        <span v-else>--</span>
                                        <%--{{item.remark||'--'}}--%>
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
                                        v-if="item.inquiryType=='REFERENCE_PRICE'"
                                        prop="dispositionType"
                                        label="处置方式">
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
                            <el-row :gutter="12" style="margin-top:10px" type="flex" justify="space-between">
                                <el-col :xs="10" :sm="22" :md="22" :lg="22"><b>备注：</b><em>{{item.inquiryRemark||'--'}}</em></el-col>
                                <el-col :xs="2" :sm="2" :md="2" :lg="2"   style="text-align: right"  > <span class="status"  :class="statusClass[item.busiStatus]"  v-show="item.busiStatus!='待确认'" >{{item.busiStatus}}</span></el-col>
                                </el-col>

                            </el-row>
                            <div style="margin-top: 9px;text-align: right" v-if="item.inquiryType!=='REFERENCE_PRICE'">
                                <span class="status" :class="statusClass[item.busiStatus]" style="margin-right: 25px;"  v-show="item.busiStatus=='待确认'" >{{item.busiStatus}}</span></el-col>
                                <el-button type="text" class="comment_btn"  style="margin-right: 10px;" @click="fold(item,item.detailIndex)" v-if="item.evaluated"><i class="comment_icon commented"></i>{{item.commentShow?'收起评价':'已评价，查看'}}<i class="el-input__icon el-icon-arrow-down" :class="{'is-reverse':item.commentShow}"></i></el-button>
                                <el-button type="text" class="comment_btn" style="margin-right: 10px;"  @click="fold(item,item.detailIndex)" v-else><i class="comment_icon"></i>评价<i class="el-input__icon el-icon-arrow-down" :class="{'is-reverse':item.commentShow}"></i></el-button>
                                <el-button type="warning"  icon="el-icon-document" style="width: 103px" v-show="item.inquiryEnterType=='DIS_FACILITATOR'" @click="viewQualification(item)">查看资质</el-button>
                                <el-button type="primary" @click="confirm(item)" v-show="item.busiStatus=='待确认'" class="over">接受报价</el-button>
                                <el-button style="background-color: #ebecf0" v-show="item.busiStatus=='待确认'" @click="reject(item)">谢绝</el-button>

                            </div>

                            <div class="comment_container" v-show="item.commentShow&&!item.evaluated">
                                <p>发布评价</p>
                                <table>
                                    <tr>
                                        <td style="vertical-align: middle">综合评价：</td>
                                        <td>
                                            <el-radio class="radio" v-model="item.commentType"  label="5" :name="'commentType_'+item.inquiryId">好评</el-radio>
                                            <el-radio class="radio" v-model="item.commentType"  label="3" :name="'commentType_'+item.inquiryId">中评</el-radio>
                                            <el-radio class="radio" v-model="item.commentType"  label="1"  :name="'commentType_'+item.inquiryId">差评</el-radio>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="padding-top: 17px">增加描述：</td>
                                        <td>
                                            <el-input
                                                    type="textarea"
                                                    :rows="2"
                                                    :id="'commentText_'+item.inquiryId"
                                                    placeholder="请输入描述"
                                                    v-model="item.commentText">
                                            </el-input>
                                        </td>
                                    </tr>
                                </table>
                                <el-row style="text-align: center;margin-top: 20px">
                                    <el-button type="primary" class="over" @click="comment(item,item.detailIndex)">发布评论</el-button>
                                    <el-button @click="fold(item,item.detailIndex)">取消</el-button>
                                </el-row>
                            </div>
                            <div class="comment_container" v-show="item.commentShow&&item.evaluated">
                                <p>查看评价<el-button style="float: right" @click="addComment(item,item.detailIndex)">增加评论</el-button></p>
                                <table v-for="(comment,i) in item.commentList">
                                    <thead>
                                    <tr>
                                        <th colspan="2">我发布的评价</th>
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
                        </div>
                        <div style="padding: 50px 0;text-align: center" v-show="!inquiryList||inquiryList.length==0">
                            暂无相关数据
                        </div>
                        <div v-show="inquiryList&&inquiryList.length>0" align="right">
                            <el-pagination @size-change="handleSizeChange_inquiry" @current-change="handleCurrentChange_inquiry" :current-page.sync="inquiryIndex" :page-sizes="[5,10,20,30,50]" :page-size="inquirySize" layout="total, sizes, prev, pager, next, jumper" :total="inquiryTotal">
                            </el-pagination>
                        </div>
                    </div>
                </div>

        </div>
    </div>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/constants.js?3"></script>
<script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
<script>
    var vue=new Vue({
        el: '#app',
        data: {
            pageIndex:1,
            pageSize:5,
            list:[],
            listLoading:true,
            total:0,
            index:0,
            inquiryList:[],
            inquiryIndex:1,
            inquirySize:5,
            inquiryTotal:0,
            inquiryLoading:false,
            detailShow:false,
            statusClass:statusClass,
            activityId:'',
            activityList:[],
            priceIdCountObj:{},
            systemInquiryList:[],
            dialogVisible:false,
            showLarge:false,
            largeImg:'',
            qualification:{},
            dialogTitle:''
        },
        created:function() {
            this.getList();
            this.getActivityList();
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
            },
            getList: function () {
                this.list=[];
                var _this = this;
                var param={
                    activityId:this.activityId
                };
                ajax({
                    url: '/entRelease/listWasteEntReleaseByEntId.htm?ticketId=' + getParam('ticketId') + '&pageIndex=' + this.pageIndex + '&pageSize=' + this.pageSize,
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify(param),
                    success: function (result) {
                        _this.listLoading = false;
                        if (result.status == 1 && result.data.wasteEntRelaseList) {
                            _this.list=result.data.wasteEntRelaseList;
                            _this.total=result.data.paging.totalRecord;
                        }
                    }
                })
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
                            orderId:item.inquiryId
                        },
                        success:function (data) {
                            console.log(data);
                            if(data.status==1){
                                item.commentList=data.data;
                                item.commentLoaded=true;
                            }
                            Vue.set(vue.inquiryList, index, item);
                        }
                    });
                }else{
                    if(item.commentLoaded){
                        item.evaluated=true;
                    }
                    Vue.set(vue.inquiryList, index, item);
                }
            },
            comment:function (item,index) {
                var id=item.inquiryId;
                var name='commentType_'+id;
                var commentTypeList=$('[name='+name+']');
                var commentType='';
                for(var i in commentTypeList){
                    var commentTypeInput=commentTypeList.eq(i);
                    if(commentTypeInput.parent().hasClass('is-checked')){
                        commentType=commentTypeInput.val();
                    }
                }
                var commentText=$('#commentText_'+id).val();
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
                        evaluatedEntId:item.inquiryEntId,
                        evaluatedBy:item.releaseEntId
                    },
                    success:function (data) {
                        if(data.status==1&&data.data){
                            $.notify('评价成功',{status:'success',timeout:1000});
                            item.evaluated=true;
                            ajax({
                                url:'/entEvaluate/listEntEvaluate.htm',
                                data:{
                                    orderId:id
                                },
                                success:function (data) {
                                    console.log(data);
                                    if(data.status==1){
                                        item.commentList=data.data;
                                        item.commentLoaded=true;
                                        Vue.set(vue.inquiryList, index, item);
                                    }
                                }
                            });
                        }
                    }
                });
            },
            addComment:function (item,index) {
                item.commentShow=true;
                item.evaluated=false;
                Vue.set(vue.inquiryList,index,item);
            },
            getInquiryList: function () {
                this.inquiryList=[];
                var _this = this;
                var param = {
                    releaseId:this.list[this.index]['releaseId'],
                    pageIndex:this.inquiryIndex,
                    pageSize:this.inquirySize
                };
                ajax({
                    url: '/entInquiry/listEntInquiryByReleaseId.htm',
                    dataType: 'json',
                    data: param,
                    success: function (result) {
                        console.log(result);
                        _this.inquiryLoading = false;
                        if (result.status == 1 && result.data.inquiryList) {
                            _this.inquiryList=result.data.inquiryList;
                            if(result.data.inquiryList.length>0){
                                var obj={};
                                var k = 1;
                                for(var i in _this.inquiryList){
                                    var item=_this.inquiryList[i];
                                    var index = ((vue.inquiryIndex-1)*vue.inquirySize+k);

                                    item.index="报价"+index+":";
                                    item.detailIndex = k-1;
                                    _this.inquiryList[i]['commentShow']=false;
                                    _this.inquiryList[i]['']='good';
                                    _this.inquiryList[i]['commentcommentTypeText']='';
                                    _this.inquiryList[i]['commentLoaded']=false;
                                    _this.inquiryList[i]['commentList']=[];

                                    if(item.inquiryType=='REFERENCE_PRICE'){
                                        var inquiryDetail=result.data.inquiryList[i].inquiryDetail;
                                        inquiryDetail.sort(function (o1,o2) {
                                            return o1.priceId>o2.priceId?1:-1;
                                        });
                                        for(var j in inquiryDetail){
                                            if(!obj[inquiryDetail[j].priceId]){
                                                inquiryDetail[j].first=true;
                                                obj[inquiryDetail[j].priceId]=1;
                                            }else{
                                                obj[inquiryDetail[j].priceId]+=1;
                                            }
                                        }
                                        vue.priceIdCountObj=obj;
                                        vue.systemInquiryList=inquiryDetail;
                                    }
                                    k++;
                                }
                            }
                            _this.inquiryTotal=result.data.paging.totalRecord;
                        } else {

                        }
                    }
                })
            },
            getActivityList:function(){
                var _this=this;
                ajax({
                    url:'/wasteActivity/listActiviyNameByApplyEntId',
                    dataType: 'json',
                    success:function (data) {
                        var obj={'activityName':'全部',activityId:''};
                        var defaultObj ={'activityName':'危废处置询价',activityId:'resourcePull'};
                        var arr=[obj,defaultObj];
                        arr=arr.concat(data.data);
                        _this.activityList = arr;
                    }
                });
            },
            handleSizeChange:function(val) {
                this.pageIndex=1;
                this.pageSize = val;
                this.getList()
            },
            handleCurrentChange:function(val) {
                this.pageIndex = val;
                this.getList()
            },
            handleSizeChange_inquiry:function(val) {
                this.inquiryIndex=1;
                this.inquirySize = val;
                this.getInquiryList();
            },
            handleCurrentChange_inquiry:function(val) {
                this.inquiryIndex = val;
                this.getInquiryList();
            },
            inquiryDetail:function (index) {
                this.index=index;
                this.detailShow=true;
                this.inquiryIndex=1;
                this.getInquiryList();
            },
            activityNameChange:function(){
                this.pageIndex=1;
                this.list=[];
                this.detailShow=false;
                this.getList();
            },
            goBack:function () {
                this.detailShow=false;
            },
            contact:function (entId,inquiryEnterName) {
                getEnterpriseContacts(entId,inquiryEnterName,'我的询价');
            },
            viewActivityDetail:function (activityId) {
                window.location='<%=appPath%>/main/pc/view/wasteActivityDetail.html?ticketId=<%=ticketId%>&activityId='+activityId;
            },
            del:function (releaseId) {
                vue.$confirm('此操作将撤销该询价, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/entRelease/deleteWasteEntReleaseByReleaseId.htm?ticketId=<%=ticketId%>',
                        data:JSON.stringify({
                            releaseId:releaseId
                        }),
                        dataType:'json',
                        contentType:'application/json',
                        success:function (data) {
                            if(data.status==1&&data.data==true){
                                vue.detailShow=false;
                                $.notify('删除成功',{status:'success',timeout:2500});
                                vue.pageIndex=1;
                                vue.getList();
                            }
                        }
                    })
                }).catch(function(){});
            },
            close:function (item,index) {
                var releaseId=item.releaseId;
                vue.$confirm('如选择完结，系统将不再公开此次询价信息', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/entRelease/endWasteEntReleaseByReleaseId?ticketId=<%=ticketId%>',
                        data:JSON.stringify({releaseId:releaseId}),
                        contentType:'application/json',
                        success:function (result) {
                            if(result.status==1&&result.data==true){
                                $.notify('完结成功',{status:'success',timeout:2500});
                                item.releaseStatus='END';
                                Vue.set(vue.list,index,item);
                            }
                        }
                    })
                }).catch(function(){});
            },
            reject:function (item) {
                vue.$confirm('此操作将拒绝该报价, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/entInquiry/rejectEntInquiry.htm',
                        data:{
                            inquiryId:item.inquiryId,
                            inquiryEntId:item.inquiryEntId
                        },
                        dataType:'json',
                        success:function (data) {
                            if(data.status==1&&data.data==true){
                                $.notify('拒绝成功',{status:'success',timeout:2500});
                                vue.inquiryIndex=1;
                                vue.getInquiryList();
                                collectingUserBehavior(getParam('ticketId'),'RESPONSE');
                            }
                        }
                    })
                }).catch(function(){});
            },
            confirm:function (item) {
                var activityId=this.list[this.index].activityId;
                var releaseId=this.list[this.index].releaseId;
                var inquiryId=item.inquiryId;
                var param={
                    inquiryId:inquiryId,
                    inquiryEntId:item.inquiryEntId,
                    disEntId:item.disEntId,
                    disEntId:item.entDisEntId
                };
                if(activityId){
                    param.activityId=activityId;
                }
                if(releaseId){
                    param.releaseId=releaseId;
                }
                vue.$confirm('接受报价会通知报价单位，若不想再接受其它公司报价，请点击“完结”。', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '完结',
                    type: 'warning'}
                ).then(
                    function(){
                        ajax({
                            url:'/entInquiry/confirmEntInquiry.htm',
                            data:param,
                            dataType:'json',
                            success:function (data) {
                                if(data.status==1&&data.data==true){
                                    $.notify('确认成功,已形成新的订单',{status:'success',timeout:2500});
                                    vue.inquiryIndex=1;
                                    vue.getInquiryList();
                                    collectingUserBehavior(getParam('ticketId'),'RESPONSE');
                                }
                            }
                        })
                    }
                ).catch(function(){
                    ajax({
                        url:'/entInquiry/confirmEntInquiry.htm',
                        data:param,
                        dataType:'json',
                        success:function (data) {
                            if(data.status==1&&data.data==true){
                                $.ajax({
                                    url:'/swp/entRelease/endWasteEntReleaseByReleaseId?ticketId=<%=ticketId%>',
                                    data:JSON.stringify({releaseId:releaseId}),
                                    contentType:'application/json',
                                    type:'post',
                                    dataType:'json',
                                    success:function (result) {
                                        if(result.status==1&&result.data==true){
                                            vue.list[vue.index].releaseStatus='END';
                                            Vue.set(vue.list,vue.index,vue.list[vue.index]);
                                            $.notify('确认成功,已形成新的订单',{status:'success',timeout:2500});
                                            vue.inquiryIndex=1;
                                            vue.getInquiryList();
                                            collectingUserBehavior(getParam('ticketId'),'RESPONSE');
                                            getInquiryCount();
                                        }
                                    }
                                })
                            }
                        }
                    })
                })
            },
            entDetail:function (entId,inquiryEnterType) {
                if(inquiryEnterType=='FACILITATOR'){
                    window.location = "<%=appPath%>/enterprise/facilitatorDetail.htm?ticketId=<%=ticketId%>&enterpriseId=" + entId + "&breadcrumbName=我的询价";
                }else{
                    window.location = "<%=appPath%>/enterprise/czDetail.htm?ticketId=<%=ticketId%>&enterpriseId=" + entId + "&breadcrumbName=我的询价";
                }
            }
        }

    })
</script>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function() {
        var rs = window.globalInit();
        rs.done(function () {
        });
    });
</script>
</body>