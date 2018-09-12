<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
    String releaseId = (String)request.getAttribute("releaseId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="我的易废圈" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myIndex" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/main/pc/css/publish.css?7">

<section id="app">
    <template>
    <el-breadcrumb separator="/">
        <el-breadcrumb-item onclick="vue.back();">危废处置询价</el-breadcrumb-item>
        <el-breadcrumb-item>系统参考报价</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="panel panel-body">
        <div class="panel panel-body">
            <%--<div class="success-title" v-if="systemInquiry"><i class="el-icon-circle-check-outline"></i>感谢你的询价，<b>800-001-01,821-203-02</b>暂未匹配合适的报价，现给出系统其它{{systemInquiry.inquiryDetail.length}}项的参考报价:</div>--%>
            <div class="success-title" v-if="systemInquiry"><i class="el-icon-circle-check-outline"></i>感谢你的询价，系统给出的参考报价为:</div>
            <div class="success-title" style="margin-bottom: 85px" v-else><i class="el-icon-warning"></i>您发布的危废暂未匹配到合适的报价，工作人员将在2小时内为您提供人工报价，请耐心等待！</div>
            <table class="el-table inquiry-table" v-if="systemInquiry">
                <tr>
                    <th class="is-center">序号</th>
                    <th>危废名称</th>
                    <th>危废代码</th>
                    <th class="is-right">处置重量</th>
                    <th class="is-center">处置方式</th>
                    <th class="is-right">处置单价</th>
                    <th class="is-right">单项总价</th>
                    <th  class="is-center">价格说明</th>
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
                    <td v-if="item.first" :rowspan="priceIdCountObj[item.priceId]" class="is-center">
                        <el-tooltip effect="dark" v-if="item.remark" :content="item.remark" placement="top-start">
                            <i class="el-icon-question"></i>
                        </el-tooltip>
                        <span v-else>--</span>
                    </td>
                </tr>
                <tr>
                    <td class="is-center">总计</td>
                    <td colspan="2"></td>
                    <td class="is-right">
                        <b style="color:#1171d1;">{{getQualityTextBySelection}}</b>
                    </td>
                    <td colspan="2"></td>
                    <td class="is-right">
                        <b style="color:#ff484d">￥{{systemInquiry.totalPriceStr}}</b>
                    </td>
                    <td></td>
                </tr>
            </table>
            <div class="success-title" v-if="noneInquiryList&&noneInquiryList.length>0" style="margin: 30px 0 10px;font-size: 15px;">下列危废系统暂时无法给出报价:（具体情况，工作人员会跟您取得联系）</div>
            <el-table
                    v-if="noneInquiryList.length>0"
                    :data="noneInquiryList"
                    border
                    style="width: 100%; ">
                <el-table-column
                        type="index"
                        width="50px"
                        align="center"
                        label="序号">
                </el-table-column>
                <el-table-column
                        prop="wasteName"
                        label="危废名称">
                </el-table-column>
                <el-table-column
                        prop="wasteCode"
                        width="100px"
                        label="危废代码">
                </el-table-column>
                <el-table-column
                        prop="qty"
                        align="right"
                        label="处置重量">
                    <template scope="scope">
                        {{parseFloat(scope.row.amount)+scope.row.unitValue}}
                    </template>
                </el-table-column>
            </el-table>
            <div class="desc" >
                <p>说明：</p>
                <p>
                    <%--1、询价后，危废处置工程师将在2小时内与您联系并安排后续取样，报价等事宜。<br/>--%>
                    1、此报价不是最终报价，最终报价需参考化验结果,以最终危险废物处置合同为准。<br/>
                    2、如需帮助，请拨打：0512-62717018<br/>
                </p>
            </div>
                <div align="center" style="margin-top: 20px">
                    <span>满足环保要求、避免处罚风险、规范管理危废</br>
                    小易向你推荐管家服务</span>
                </div>
            <div align="center" style="margin-top: 20px">
                <el-button type="primary" style="background-color:#ff8700;border-color:#ff8700; " @click="viewService">了解管家服务></el-button>
                <%--<el-button type="primary" style="background-color:#ff8700;border-color:#ff8700;" @click="viewStep">了解危废处置流程></el-button>--%>
            </div>

        </div>
    </div>
    </template>
</section>
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
            releaseId:getParam('releaseId')||'',
            informEntCount:getParam('informEntCount')||0,
            systemInquiry:'',
            systemInquiryList:[],
            priceIdCountObj:{},
            quotationList: [],
            dispositionTypeList:[{code:'D10',value:'焚烧'},{code:'R9',value:'利用'}],
            dispositionTypeObj:{},
            totalQtyT:0,
            totalQtyText:'',
            noneInquiryList:[]
        },
        created:function() {
            this.getSystemInquiry();
            const h = this.$createElement;
            this.$msgbox( {
                title: '提示',
                message:h('p', null, [
                    h('span', null, '小易已经通知区域内的'),
                    h('span', { style: 'color: red' }, this.informEntCount+'家'),
                    h('span', null, '处置经营企业前来报价，请随时关注平台信息和短信。'),
                    h('br', null),
                    h('span','如急需处置，请直接拨打易废网电话：'),
                    h('span', { style: 'color: red' }, '0512-62717018。')
                ]),
                confirmButtonText: '知道了',
                confirmButtonClass:'confirm'
            });
        },
        mounted:function () {
            var _this=this;
            this.dispositionTypeList.forEach(function (item) {
                _this.dispositionTypeObj[item.code]=item.value;
            })
        },
        computed:{
            getQualityTextBySelection:function () {
                if(!this.systemInquiry){
                    return;
                }
                var arr=this.systemInquiry.inquiryDetail;
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
        },
        methods:{
            viewStep:function () {
                window.location='<%=appPath%>/introduction.html';
            },
            getSystemInquiry:function () {
                ajax({
                    url:'/entInquiry/getSystemInquiryByReleaseId.htm',
                    data:{
                        releaseId:this.releaseId
                    },
                    success:function (result) {
                        if(result.status==1&&result.data){
                            var inquiryDetail=result.data.entInquiryModel?result.data.entInquiryModel.inquiryDetail:[];
                            inquiryDetail.sort(function (o1,o2) {
                                return o1.priceId>o2.priceId?1:-1;
                            });
                            var obj={};
                            for(var i in inquiryDetail){
                                var item=inquiryDetail[i];
                                if(!obj[item.priceId]){
                                    item.first=true;
                                    obj[item.priceId]=1;
                                }else{
                                    obj[item.priceId]+=1;
                                }
                            }
                            vue.priceIdCountObj=obj;
                            vue.systemInquiryList=inquiryDetail;
                            vue.systemInquiry=result.data.entInquiryModel?result.data.entInquiryModel:'';
                            vue.noneInquiryList=result.data.noneInquiryList;
                        }
                    }
                });
            },
            back:function () {
              window.history.go(-1);
            },
            showDispositionTypeEdit:function (index) {
                var item=this.quotationList[index];
                item.showDispositionTypeEdit=true;
                Vue.set(this.quotationList,index,item);
            },
            editDispositionType:function (item,index) {
                $.notify('设置成功',{status:'success',timeout:2500});
                item.dispositionType=vue.dispositionTypeObj[item.dispositionTypeCode];
                item.showDispositionTypeEdit=false;
                Vue.set(vue.quotationList,index,item);
            },
            viewService:function () {
                window.location='<%=appPath %>/wastecircle/init.htm?ticketId=<%=ticketId %>&buyMeal=1';
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