<%@ page contentType="text/html; charset=UTF-8" language="java"
         errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>

<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="充值"/>
</jsp:include>
<%------- 结束导入页头信息 -------%>

<%------- 导入左侧信息 -------%>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myBit"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath %>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath %>/main/pc/css/recharge.css">


<!-- Main section-->
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">个人中心</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="history.go(-1);">个人账户</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">充值</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div id="app" v-cloak>
        <el-row class="header-row">
            <el-col :span="24">
                <i class="bit-icon"></i>
                <b>账户名称：</b>
                <span class="header-text">${entName}</span>
                <b>易废币账户余额：</b>
                <span class="header-text"><em>${bitcionAccount.bitcion==null?0:bitcionAccount.bitcion}</em><span style="color: #ff6000">个</span>易废币</span>
            </el-col>
        </el-row>
        <div class='panel' style="padding-bottom: 40px">
            <div class="title-text"><b>账户充值</b>（1元购买1个易废币）</div>
            <div class="meal-list">
                <div class="meal-container" @click="currentIndex=index" v-for="(item,index) in list" :class="{'select':index==currentIndex}">
                    <div class="meal-container-inner">
                        <div class="meal-money"><span>{{item.count}}</span>易废币</div>
                        <div class="meal-count"><span>{{item.money}}</span>元</div>
                    </div>
                </div>
            </div>
            <el-row class="price-row" v-if="currentIndex!=null&&list[currentIndex]">
                <el-col :span="10">支付金额：<b>￥{{list[currentIndex].money}}</b></el-col>
                <el-col :span="14" align="right">
                    支付方式：<i class="alipay"></i><span style="font-size: 18px">支付宝</span>
                </el-col>
            </el-row>
            <div style="text-align: center;margin-top: 20px" v-if="currentIndex!=null&&list[currentIndex]">
                <el-button class="confirm-btn" @click="pay()">立即支付</el-button>
            </div>

        </div>
    </div>
</section>

<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>
<!-- 先引入 Vue -->
<script src="<%=appPath %>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath %>/main/common/elementui/index.js"></script>
<script type="text/javascript">
    var vue = new Vue({
        el: '#app',
        data:{
            list:[
                {money:200,count:200},
                {money:920,count:1000},
                {money:2700,count:3000},
                {money:5220,count:6000},
                {money:8500,count:10000},
                {money:16600,count:20000}
            ],
            currentIndex:null
        },
        created:function () {
        },
        mounted:function() {

        },
        methods:{
            pay:function () {
                var price=this.list[this.currentIndex]['money'];
                var bitCionAmount=this.list[this.currentIndex]['count'];
                var remark=this.list[this.currentIndex]['money']+'元购买'+this.list[this.currentIndex]['count']+'易废币';
                <%--window.location='<%=appPath%>/entRecharge/saveEntRecharge.htm?ticketId=<%=ticketId%>&price='+price+'&bitCionAmount='+bitCionAmount+'&remark='+remark;--%>
               ajax({
                    url:'/entRecharge/saveEntRecharge.htm?ticketId=<%=ticketId%>',
                    data:JSON.stringify({
                        price:price,
                        bitCionAmount:bitCionAmount,
                        remark:remark
                    }),
                    contentType:'application/json',
                    success:function (result) {
                        console.log(result);
                        if(result.status==1){
                            window.location='<%=appPath%>/common/pay.jsp?orderNo='+result.data;
                        }
                    }
                })
            }
        }
    });
</script>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<%------- 结束导入底部信息 -------%>

<script type="text/javascript">
    $(document).ready(function() {
        var rs = window.globalInit();
        rs.done(function () {
        });
    });
</script>