<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="危废询价" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myIndex" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/main/pc/css/publish.css?6">
<section id="app">
    <template>
    <el-breadcrumb separator="/">
        <el-breadcrumb-item>危废处置询价</el-breadcrumb-item>
    </el-breadcrumb>
    <div  class="panel panel-body">
            <div class="selectTable">
                <div class="title">危废处置询价</div>
                <el-table :data="tableData"  v-loading.body="listLoading" ref="dataSelectTable"
                          @selection-change="handleSelectionChange"
                          style="width: 100%">
                    <el-table-column
                            type="selection"
                            width="75">
                    </el-table-column>
                    <el-table-column
                            label="危废代码"
                            prop="wasteCode">
                        <template scope="scope">
                            <div class="wasteCodeError" v-if="!scope.row.entWasteId&&scope.row.wasteCodeError">{{scope.row.wasteCodeError}}</div>
                            <span v-if="scope.row.entWasteId">{{scope.row.wasteCode}}</span>
                            <el-autocomplete
                                    style="width: 100%;"
                                    :class="{'errorBorder':!scope.row.entWasteId&&scope.row.wasteCodeErrorShow}"
                                    v-else
                                    popper-class="wasteCode-autocomplete"
                                    v-model="scope.row['wasteCode']"
                                    :fetch-suggestions="querySearch"
                                    placeholder="请输入或选择匹配的八位码"
                                    @focus="setCurrentIndex(scope.$index)"
                                    @select="handleSelect">
                                <template slot-scope="props">
                                    <div class="name">{{ props.item.addenterwastecode }}&nbsp;&nbsp;{{ props.item.addenterwastename }}&nbsp;&nbsp;<span class="addr">{{ props.item.addenterwastedesc }}</span></div>
                                </template>
                            </el-autocomplete>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="危废名称"
                            prop="wasteName">
                        <template scope="scope">
                            <div class="wasteCodeError" v-if="!scope.row.entWasteId&&scope.row.wasteNameError">{{scope.row.wasteNameError}}</div>
                            <span v-if="scope.row.entWasteId">{{scope.row.wasteName}}</span>
                            <el-autocomplete
                                    style="width: 100%"
                                    :class="{'errorBorder':!scope.row.entWasteId&&scope.row.wasteNameErrorShow}"
                                    popper-class="wasteName-autocomplete"
                                    v-model="scope.row['wasteName']"
                                    :fetch-suggestions="querySearchName"
                                    :disabled="!scope.row.wasteId"
                                    @focus="setCurrentIndex(scope.$index)"
                                    v-else
                                    placeholder="请输入危废名称"
                                    @select="handleSelectName"
                                    @blur="changeWasteName">
                                <template slot-scope="props">
                                    <div class="name">{{ props.item.name }}</div>
                                </template>
                            </el-autocomplete>
                        </template>
                    </el-table-column>
                    <el-table-column
                            width="300px"
                            align="center"
                            label="数量">
                        <template scope="scope">
                            <div class="wasteCodeError qualityError" v-if="scope.row.qualityError" :style="scope.row.qualityError.length==6?'width:96px':''">{{scope.row.qualityError}}</div>
                            <el-input v-model="scope.row['quality']" style="width:150px" :class="{'errorBorder':scope.row.qualityErrorShow}" @change="changeQuality(scope.row,scope.$index)"></el-input>
                            <el-select v-model="scope.row.unitCode" placeholder="单位" style="width:80px"  disabled>
                                <el-option :label="item1.value" :value="item1.code" v-for="(item1,index) in unitList"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                   <%-- <el-table-column
                            width="80px"
                            label="操作">
                        <template scope="scope">
                            <el-button type="text" @click="deleteRow(scope.$index,tableData)">移除</el-button>
                        </template>
                    </el-table-column>--%>
                </el-table>
                <el-row>
                    <el-col :span="12">
                        <el-button type="text"  @click="addWaste">+新增危废</el-button>
                    </el-col>
                    <el-col :span="12" align="right" class="total">
                        <span class="total-title">共计：</span><b>{{totalSelectCount}}</b>种危废，<b>{{getTotalQuality()}}</b>
                    </el-col>
                </el-row>
                <div align="center" style="margin: 10px 0 30px">
                    <el-button type="primary publish-btn" :loading="publishLoading" @click="publish">{{publishLoading?'询价中...':'立即询价'}}</el-button>
                    <%--<el-button @click="goShipStore" style="height: 32px;padding: 0;width: 120px;font-size: 15px;line-height: 32px;">先逛逛</el-button>--%>
                </div>
                <div class="desc">
                    <p>询价说明：</p>
                    <p>
                        1、询价后系统将会立即给出参考报价。参考报价不是最终报价<br/>
                        2、易废网不处置危废，处置危废的服务由易废网的合作单位为你提供。<br/>
                        3、处置合同由最终和处置单位签订的合同为准。
                    </p>
                </div>
                <%--<div style="text-align: center;background-color: #fff8f0;margin-top: 25px">--%>
                    <%--<img src="<%=appPath %>/main/pc/img/publishProcedure.jpg" style="display: block;margin: 0 auto;max-width: 100%;">--%>
                <%--</div>--%>
            </div>
            <el-dialog :visible.sync="dialogVisible" width="520px" class="publishSuccessBg" :close-on-click-modal="false">
                <span class="xunzhan_count">{{entRelaseModel.releaseCount}}</span>
                <a href="javascript:" class="guide_close" @click="closeDialog"></a>
                <div class="xunzhan_text"><%--绿色环保卫士<font>1{{entRelaseModel.releaseCount}}</font>级勋章<span>--%>- {{new Date().format&&new Date().format("yyyy.MM.dd")}}获得 -</span></div>
                <div class="myMessage">
                    <span class="userName">{{entRelaseModel.releaseUserName}}</span>
                    <span class="entName">{{entRelaseModel.entName}}</span>
                </div>
                <div class="other-tip">
                    工作人员将在2小时内与您取得联系，<br/>请耐心等待！咨询电话：<a href="javascript:" style="text-decoration: underline">0512-62717018</a>
                </div>
                <div class="shareDiv">
                    <el-dropdown @visible-change="command">
                        <el-button type="primary" class="shareBtn weixin"><i class="icon_weixin"></i>分享到微信</el-button>
                        <el-dropdown-menu slot="dropdown" style="width: 158px;text-align: center;margin-right: 12px;">
                            <div id="code"></div>
                            <span style="font-size:12px;color: #0079de;">打开微信，扫一扫</span>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </el-dialog>
                <%--<el-dialog :visible.sync="dialogVisible" width="520px" class="publishSuccessBg" :close-on-click-modal="false">
                        <span class="xunzhan_count">{{entRelaseModel.releaseCount}}</span>
                        <a href="javascript:;" class="guide_close" @click="closeDialog"></a>
                        <div class="xunzhan_text">- {{new Date().format&&new Date().format("yyyy.MM.dd")}}获得 -</div>
                        <div class="publish-success-text">
                            委托处置成功!<br/>获得能量豆100枚，成功晋级{{entRelaseModel.releaseCount}}级绿色卫士！
                        </div>
                        <div class="myMessage">
                            <span class="userName">{{entRelaseModel.releaseUserName}}</span>
                            <span class="entName">{{entRelaseModel.entName}}</span>
                        </div>
                        <div class="other-tip">
                            工作人员将在2小时内与您取得联系，请耐心等待！咨询电话：<a href="javascript:;" style="text-decoration: underline">0512-62717018</a>
                        </div>
                        <div class="shareDiv">
                             <el-dropdown @visible-change="command">
                                <el-button type="primary" class="shareBtn weixin"><i class="icon_weixin"></i>分享到微信</el-button>
                                <el-dropdown-menu slot="dropdown" style="width: 180px;text-align: center;margin-right: 30px;">
                                    <div id="code"></div>
                                    <span style="font-size:12px;color: #0079de;">打开微信，扫一扫</span>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                </el-dialog>--%>
        </div>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
<script type="text/javascript" src="<%=appPath %>/main/common/qrcode.min.js"></script>
<script>
    var vue=new Vue({
        el: '#app',
        data: {
            tableData: [],
            multipleSelection: [],
            listLoading:true,
            dialogVisible:false,
            totalQtyText:'0吨',
            totalQtyT:'',
            entRelaseModel:{},
            unitList:[],
            currentIndex:0,
            releaseId:'',
            publishLoading:false
        },
        created:function() {
            this.getList();
            this.getUnitList();
        },
        computed:{
            totalSelectCount:function(){
                var arr=this.multipleSelection;
                var count=0;
                for(var i in arr) {
                    count++;
                }
                return count;
            },

        },
        mounted:function () {
            Vue.nextTick(function () {
                $('.cell:first').append('全选')
            });
        },
        methods:{
            getTotalQuality:function(){
                var arr=this.multipleSelection;
                var obj = {};
                for(var i in arr) {
                    var key = arr[i]['unitValue']||'吨';
                    var value = parseFloat(arr[i]['quality']||0);
                    if(key=='吨'||key=='千克'||key=='克'){
                        switch (key){
                            case '千克':
                                value=value/1000;
                                break;
                            case '克':
                                value=value/1000000;
                                break
                        }
                        if(obj['吨']){
                            obj['吨']=obj['吨']+value;
                        }else{
                            obj['吨']=value;
                        }
                    }else{
                        if(obj[key]) {
                            obj[key] =obj[key]+value ;
                        } else {
                            obj[key] = value;
                        }
                    }
                }
                var str='';
                for (var prop in obj) {
                    str+=obj[prop].toFixed(4)+prop+',';
                }
                str=str.substring(0,str.length-1);
                this.totalQtyText=str;
                this.totalQtyT = obj['吨'];
                return str||'0吨';
            },
            handleSelectionChange:function (val) {
                this.multipleSelection=val;
                var arr=[];
                for(var i in this.multipleSelection){
                    arr.push(this.multipleSelection[i]['index']);
                }
                for(var k in this.tableData){
                    if(arr.indexOf(this.tableData[k]['index'])==-1){
                        var item=this.tableData[k];
                        item.qualityErrorShow=false;
                        Vue.set(this.tableData,k,item);
                        vue.clearError(k);
                    }
                }
            },
            goShipStore:function () {
              window.location='<%=appPath%>/wastecircle/init.htm?ticketId=<%=ticketId%>';
            },
            setCurrentIndex:function (index) {
                this.currentIndex=index;
            },
            command:function (flag) {
                if(flag&&$("#code canvas").length==0){
                    var qrcode = new QRCode(document.getElementById("code"), {
                        width : 142,
                        height : 142
                    });
                    qrcode.makeCode('http://yifeiwang.com/swp/main/mobile/view/grade.html?userId='+globalInit.userId);
                    <%--qrcode.makeCode('http://yifeiwang.com/swp/main/mobile/view/grade.html?userId='+globalInit.userId+'&releaseId='+vue.releaseId+'&cache=1&ticketId=<%=ticketId%>');--%>
                  }
            },
            getList:function () {
                this.listLoading = true;
                var _this=this;
                ajax({
                    url:'/entWaste/listEntWaste.htm?ticketId=<%=ticketId%>&pageIndex=1&pageSize=100',
                    data:JSON.stringify({}),
                    dataType:'json',
                    contentType:'application/json',
                    success:function (result) {
                        console.log(result);
                        _this.listLoading = false;
                        if(result.status==1&&result.data.entWasteList&&result.data.entWasteList.length>0){
                            for(var i in result.data.entWasteList){
                                result.data.entWasteList[i].index=i;
                                result.data.entWasteList[i].quality=0;
                            }
                            _this.tableData=result.data.entWasteList;
                        }
                    }
                })
            },
            getUnitList:function () {
                var _this=this;
                ajax({
                    url:'/codeType/listCodeValue',
                    data:{
                        typeCode:'UNIT_TYPE'
                    },
                    success:function (data) {
                        if(data.status==1){
                            _this.unitList=data.data;
                            /*for(var i in data.data){
                                if(data.data[i]['value']=='吨'){
                                    Vue.set(vue.enterpriseWaste,'unitCode',data.data[i]['code']);
                                }
                            }*/
                        }
                    }
                });
            },
            deleteRow:function(index, rows) {
                rows.splice(index, 1);
            },
            clearError:function (i) {
                setTimeout(function () {
                    var item=vue.tableData[i];
                    item.wasteCodeError='';
                    item.wasteNameError='';
                    item.qualityError='';
                    Vue.set(vue.tableData,i,item);
                },2000)
            },
            validateAll:function () {
                var flag=true;
                var pattern=/^(?=([0-9]{1,10}$|[0-9]{1,7}\.))(0|[1-9][0-9]*)(\.[0-9]{1,3})?$/;
                if(this.multipleSelection.length==0){
                    $.notify('请选择危废',{status:'info',timeout:2500});
                    return;
                }
                for(var i in this.multipleSelection){
                    var item=this.multipleSelection[i];
                    if(item.entWasteId){
                        if(item.quality==0){
                            item.qualityError='数量不能为0';
                            item.qualityErrorShow=true;
                            flag=false;
                        }else if(!pattern.test(item.quality)){
                            item.qualityError='数量总长不超过十位,最多三位小数';
                            item.qualityErrorShow=true;
                            flag=false;
                        }else{
                            item.qualityError='';
                            item.qualityErrorShow=false;
                        }
                    }else{
                        if(!item.wasteCode||!item.wasteId){
                            item.wasteCodeError='请输入并选择危废代码';
                            item.wasteCodeErrorShow=true;
                            flag=false;
                        }else{
                            item.wasteCodeError='';
                            item.wasteCodeErrorShow=false;
                        }
                        if(!item.wasteName){
                            item.wasteNameError='请输入或选择危废名称';
                            item.wasteNameErrorShow=true;
                            flag=false;
                        }else if(item.wasteNameRepeat){
                            item.wasteNameError='该危废名称已存在';
                            item.wasteNameErrorShow=true;
                            flag=false;
                        }else{
                            item.wasteNameError='';
                            item.wasteNameErrorShow=false;
                        }
                        if(item.quality==0){
                            item.qualityError='数量不能为0';
                            item.qualityErrorShow=true;
                            flag=false;
                        }else if(!pattern.test(item.quality)){
                            item.qualityError='数量总长不超过十位,最多三位小数';
                            item.qualityErrorShow=true;
                            flag=false;
                        }else{
                            item.qualityError='';
                            item.qualityErrorShow=false;
                        }
                    }
                    Vue.set(this.tableData,item.index,item);
                    vue.clearError(item.index);
                }
                return flag;
            },
            publish:function () {
                this.publishLoading=true;
                var flag=this.validateAll();
                if(!flag){
                    this.publishLoading=false;
                    return;
                }
                var param={
                    releaseCount:this.multipleSelection.length,
                    totalAmountDesc:this.totalQtyText,
                    totalAmount:this.totalQtyT
                };
                var entReleaseDetails=[];
                for(var i in this.multipleSelection){
                    var obj={};
                    var item=this.multipleSelection[i];
                    obj.entWasteId=item['entWasteId'];
                    obj.releaseAmount=item['quality'];
                    obj.wasteId=item['wasteId'];
                    obj.wasteCode=item['wasteCode'];
                    obj.wasteNameId=item['wasteNameId'];
                    obj.wasteName=item['wasteName'];
                    obj.unitCode=item['unitCode'];
                    entReleaseDetails.push(obj);
                }
                param['releaseDetail']=entReleaseDetails;
                console.log(param);
                ajax({
                    url:'/entRelease/saveAndPublish.htm?ticketId='+getParam('ticketId'),
                    dataType:'json',
                    contentType:'application/json',
                    data:JSON.stringify(param),
                    success:function (result) {
                        vue.publishLoading=false;
                        if(result.status==1){
                            vue.entRelaseModel=result.data.entReleaseModel;
                            vue.releaseId=result.data.entReleaseModel.releaseId;
                            collectingUserBehavior(getParam('ticketId'),'RELEASEWASTE');
                            window.location='<%=appPath%>/entRelease/systemInquiry.htm?ticketId=<%=ticketId%>&releaseId='+vue.releaseId+'&informEntCount='+result.data.entReleaseModel.informEntCount;
                        }
                    }
                })
            },
            addWaste:function () {
                var obj={
                    wasteCode:'',
                    wasteName:'',
                    quality:0,
                    unitCode:'T',
                    entWasteId:''
                };
                vue.tableData.push(obj);
                vue.$refs.dataSelectTable.toggleRowSelection(vue.tableData[vue.tableData.length-1], true);
                <%--window.location='<%=appPath%>/entRelease/publish.htm?ticketId=<%=ticketId%>';--%>
            },
            querySearch:function(queryString, cb) {
                ajax({
                    url:'/entWaste/getWasteIdDropDownList.htm',
                    data:{
                        keyword:queryString
                    },
                    success:function (result) {
                        for(var i in result.value){
                            result.value[i]['addenterwastecode']=result.value[i]['addenterwastecode'].replace(/<font color='red'>/g,'').replace(/<\/font>/g,'');
                            result.value[i]['addenterwastename']=result.value[i]['addenterwastename'].replace(/<font color='red'>/g,'').replace(/<\/font>/g,'');
                            result.value[i]['addenterwastedesc']=result.value[i]['addenterwastedesc'].replace(/<font color='red'>/g,'').replace(/<\/font>/g,'');
                        }
                        cb(result.value);
                    }
                });
                // 调用 callback 返回建议列表的数据
            },
            querySearchName:function (queryString, cb) {
                ajax({
                    url:'/entWaste/listWasteName.htm',
                    data:{
                        keyword:queryString,
                        wasteid:this.tableData[this.currentIndex]['wasteId']
                    },
                    success:function (result) {
                        if(result.status==1&&result.data){
                            cb(result.data);
                        }
                    }
                });
            },
            handleSelect:function(item) {
                console.log(item);
                var obj=this.tableData[this.currentIndex];
                obj.wasteCode=item.addenterwastecode;
                obj.wasteId=item.id;
                Vue.set(this.tableData,this.currentIndex,obj);
                this.changeWasteCode();
            },
            handleSelectName:function(item) {
                console.log(item);
                var obj=this.tableData[this.currentIndex];
                obj.wasteName=item.name;
                obj.wasteNameId=item.id;
                Vue.set(this.tableData,this.currentIndex,obj);
                this.validateWasteName();
            },
            validateWasteName:function () {
                var obj=this.tableData[this.currentIndex];
                ajax({
                    url:'/entWaste/checkWasteNameDuplicate.htm',
                    async:false,
                    data:{
                        wasteId:obj.wasteId,
                        wasteName:obj.wasteName,
                        ticketId:ticketId
                    },
                    success:function (result) {
                        if(result.data) {
                            obj.wasteNameError='该危废名称已存在';
                            obj.wasteNameErrorShow=true;
                            obj.wasteNameRepeat=true;
                            vue.clearError(vue.currentIndex);
                        } else {
                            obj.wasteNameError='';
                            obj.wasteNameErrorShow=false;
                            obj.wasteNameRepeat=false;
                        }
                        Vue.set(vue.tableData,vue.currentIndex,obj);
                    }
                })
            },
            changeQuality:function (item,index) {
                var pattern=/^(?=([0-9]{1,10}$|[0-9]{1,7}\.))(0|[1-9][0-9]*)(\.[0-9]{1,3})?$/;
                if(item.quality==0){
                    item.qualityError='数量不能为0';
                    item.qualityErrorShow=true;
                }else if(!pattern.test(item.quality)){
                    item.qualityError='数量总长不超过十位,最多三位小数';
                    item.qualityErrorShow=true;
                }else{
                    item.qualityError='';
                    item.qualityErrorShow=false;
                }
                Vue.set(vue.tableData,index,item);
            },
            changeWasteCode:function () {
                var obj = this.tableData[this.currentIndex];
                if (!obj.wasteCode || !obj.wasteId) {
                    obj.wasteCodeError = '请输入并选择危废代码';
                    obj.wasteCodeErrorShow = true;
                } else {
                    obj.wasteCodeError = '';
                    obj.wasteCodeErrorShow = false;
                }
                Vue.set(this.tableData,this.currentIndex,obj);
            },
            changeWasteName:function () {
                var obj=this.tableData[this.currentIndex];
                console.log(obj['wasteName']);
                if(obj['wasteName']){
                    this.validateWasteName();
                }
            },
            closeDialog:function () {
//                this.dialogVisible=false;
                window.location='<%=appPath%>/wastecircle/publishList.htm?ticketId=<%=ticketId%>';
            },
            getQualityTextBySelection:function() {
                var arr=this.tableData;
                var obj = {};
                var laNumber = new $.LaNumber();
                for(var i in arr) {
                    var key = arr[i]['unitCode'];
                    var value = parseFloat(arr[i]['quality']);
                    if(key=='T'||key=='千克'||key=='克'){
                        switch (key){
                            case '千克':
                                value=laNumber.div(value,1000);
                                break;
                            case '克':
                                value=laNumber.div(value,1000000);
                                break;
                        }
                        if(obj['T']){
                            obj['T']=laNumber.add(obj['T'],value);
                        }else{
                            obj['T']=value;
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
                    str+=obj[prop]+(prop=='T'?'吨':prop)+'，';
                }
                str=str.substring(0,str.length-1);
                this.totalQtyText=str;
                this.totalQtyT = obj['T'];
                return str;
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
//            init();
        });
        Date.prototype.format = function(format)
        {
            var o = {
                "M+" : this.getMonth()+1, //month
                "d+" : this.getDate(),    //day
                "h+" : this.getHours(),   //hour
                "m+" : this.getMinutes(), //minute
                "s+" : this.getSeconds(), //second
                "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
                "S" : this.getMilliseconds() //millisecond
            };
            if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
                (this.getFullYear()+"").substr(4 - RegExp.$1.length));
            for(var k in o)if(new RegExp("("+ k +")").test(format))
                format = format.replace(RegExp.$1,
                    RegExp.$1.length==1 ? o[k] :
                        ("00"+ o[k]).substr((""+ o[k]).length));
            return format;
        }
    });
</script>