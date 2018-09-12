<%@page import="com.mlsc.waste.utils.*" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="代理登记"/>
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#agencyRegistration"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link href="<%=appPath %>/thirdparty/city-picker/css/city-picker.css" rel="stylesheet" type="text/css" />
<link href="<%=appPath %>/css/enterprise/agency.css?4" rel="stylesheet" type="text/css" />
<script src="<%=appPath %>/main/common/provinces.js"></script>
<link rel="stylesheet" href="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.css?1">
<style>
    .example{
        position: absolute;
        width: 248px;
        right: 12px;
        top: 188px;
        text-align: center;
    }
    .example-title{
        color: #606266;
        font-size: 18px;
        margin-bottom: 10px;
    }
    .example .el-carousel{
        box-shadow: 0 6px 14px 1px cadetblue;
    }
    .el-carousel__button{
        background-color: cadetblue;
    }
    .example .el-carousel__item{
        background-size: auto 100%;
        background-position:center center;
        background-repeat:no-repeat ;
        background-color: cadetblue;
    }
    .example .imgName{
        font-size: 13px;
        color: #606266;
        margin-top:20px;
    }
    .cover{
        z-index: 100;
        position: absolute;
        width: 100%;
        height: 100%;
        display: block;
        left: 0;
        right: 0;
        cursor: pointer;
    }
</style>
<section id="app">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>我的易废圈</el-breadcrumb-item>
            <el-breadcrumb-item onclick="history.go(-1);">代理企业信息登记</el-breadcrumb-item>
            <el-breadcrumb-item>{{customer.entId?'编辑':'新增'}}代理</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="panel panel-body">
            <div class="panel panel-body">
                <div class="dialog" v-show="imgDialogVisible" @click.self="imgDialogVisible=false">
                    <a href="javascript:" class="close-dialog" title="关闭" @click="imgDialogVisible=false"><i class="el-icon-circle-close-outline"></i></a>
                    <img :src="dialogImageUrl" class="largeImg"/>
                </div>
                <%--<el-dialog :visible.sync="imgDialogVisible" width="90%" :modal-append-to-body="false" title="查看图片">--%>
                    <%--<img :src="dialogImageUrl" alt="">--%>
                <%--</el-dialog>--%>
                <div class="example">
                    <div class="example-title">案例</div>
                    <el-carousel height="150px" @change="onChange">
                        <el-carousel-item  style="background-image:url(<%=appPath%>/main/pc/img/yyzz.jpeg)">
                            <a class="cover" href="javascript:" @click="handlePictureCardPreview('<%=appPath%>/main/pc/img/yyzz.jpeg')"></a>
                        </el-carousel-item>
                        <el-carousel-item  style="background-image:url(<%=appPath%>/main/pc/img/xkz.png)">
                            <a class="cover" href="javascript:" @click="handlePictureCardPreview('<%=appPath%>/main/pc/img/xkz.png')"></a>
                        </el-carousel-item>
                        <el-carousel-item  style="background-image:url(<%=appPath%>/main/pc/img/sqs.jpeg)">
                            <a class="cover" href="javascript:" @click="handlePictureCardPreview('<%=appPath%>/main/pc/img/sqs.jpeg')"></a>
                        </el-carousel-item>
                    </el-carousel>
                    <div class="imgName">{{['营业执照','许可证','授权书'][exampleIndex]}}</div>
                </div>
                <el-form label-position="right" label-width="220px" style="max-width: 800px;margin-top: 16px;"
                         :model="customer" ref="customerValidateForm" :rules="customerRules">
                    <el-form-item label="经营单位名称：" prop="entName">
                            <el-autocomplete style="width: 400px"
                                    v-model="customer.entName"
                                    :fetch-suggestions="querySearchAsync"
                                    placeholder="请输入经营单位名称"
                                    @select="handleSelect">
                            </el-autocomplete>
                            <el-button type="primary" class="save" v-if="customerId" @click="bindCustomer">设为代理</el-button>
                    </el-form-item>
                    <el-form-item label="上传经营单位营业执照："  prop="logo">
                        <div  class="el-upload__tip" v-if="customerAction!='view'">（需上传图片格式为JPG或PNG，需加盖公章）</div>
                        <el-upload style="float: left"
                                   ref="customerUpload1"
                                   :action="uploadAction"
                                   :data="customerParam1"
                                   :disabled="customerAction=='view'"
                                   :show-file-list="false"
                                   :on-change="handleAvatarSuccess1"
                                   :on-success="customerImgUploadSuccess1"
                                   :auto-upload="false">
                            <div v-if="customer.logo1" class="image">
                                <img :src="customer.logo1">
                            </div>
                            <%--<img v-if="customer.logo1" :src="customer.logo1">--%>
                            <i v-else class="customer-upload"></i>
                        </el-upload>
                        <%--<a href="javascript:;" v-if="customer.logo1" class="viewLarge" @click="showLarge(customer.logo1)">查看大图</a>--%>
                    </el-form-item>
                    <el-form-item label="上传经营许可证："  prop="logo">
                        <div  class="el-upload__tip" v-if="customerAction!='view'">（需上传图片格式为JPG或PNG，需加盖公章）</div>
                        <el-upload style="float: left"
                                   ref="customerUpload2"
                                   :action="uploadAction"
                                   :data="customerParam2"
                                   :disabled="customerAction=='view'"
                                   :show-file-list="false"
                                   :on-change="handleAvatarSuccess2"
                                   :on-success="customerImgUploadSuccess2"
                                   :auto-upload="false">
                            <div v-if="customer.logo2" class="image">
                                <img :src="customer.logo2">
                            </div>
                            <i v-else class="customer-upload"></i>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="上传经营单位给贵司的授权书："  prop="logo">
                        <div  class="el-upload__tip" v-if="customerAction!='view'">（需上传图片格式为JPG或PNG，需加盖公章）</div>
                        <el-upload style="float: left"
                                   ref="customerUpload3"
                                   :action="uploadAction"
                                   :data="customerParam3"
                                   :disabled="customerAction=='view'"
                                   :show-file-list="false"
                                   :on-change="handleAvatarSuccess3"
                                   :on-success="customerImgUploadSuccess3"
                                   :auto-upload="false">
                            <div v-if="customer.logo3" class="image">
                                <img :src="customer.logo3">
                            </div>
                            <i v-else class="customer-upload"></i>
                        </el-upload>
                    </el-form-item>
                   <%-- <el-form-item label="联系人：" prop="contacts">
                        <el-input v-model="customer.contacts" placeholder="请输入联系人"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话：" prop="contactsTel">
                        <el-input v-model="customer.contactsTel" placeholder="请输入联系电话"></el-input>
                    </el-form-item>--%>
                    <el-form-item label="所在区域：" prop="contactsTel">
                        <el-input v-model="customer.cantonCode" placeholder="请选择所在区域" id="_area" data-toggle="city-picker"></el-input>
                    </el-form-item>
                    <el-form-item label="企业位置：" prop="entAddress">
                        <el-input v-model="customer.entAddress" placeholder="请输入企业位置"  style="width: 300px" @blur="searchByStationName" ref="entAddressField"></el-input>
                        <el-button type="primary" class="save" @click="showAndSearch">地图中定位</el-button>
                        <el-button type="primary" class="save"  v-if="mapShow" @click="hideMap">收起地图</el-button>
                        <div id="map" v-show="mapShow">
                            <div id="allmap" class="map" style="width: 600px;height: 300px;top: 13px;border: 1px solid gray;">
                            </div>
                        </div>
                    </el-form-item>
                    <el-form-item align="left">
                        <el-button type="primary" class="save" @click="saveCustomer">保存</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </template>
</section>
<!-- 先引入 Vue -->
<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="<%=appPath%>/main/common/elementui/index.js"></script>
<script type="text/javascript" src="<%=appPath %>/thirdparty/map/js/api.js" id="baidumap"></script>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.data.js"></script>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.js"></script>
<script src="<%=appPath %>/app/js/util.js"></script>
<script src="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.min.js?1"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath%>/main/common/upload/upload.js"></script>
<script>
    var map,marker;
    var vue = new Vue({
        el: '#app',
        data: function(){
            var validateAddress=function (rule, value, callback) {
                if (value === '') {
                    callback(new Error('请输入企业地址'));
                } else if(!vue.customer.posx){
                    callback(new Error('请输入有效地址'));
                }else{
                    callback();
                }
            };
            return{
                customer: {
                    entId: getParam('id')?getParam('id'):'',
                    entName:'',
                    entAddress:'',
//                    contacts:'',
//                    contactsTel:'',
                    city:'',
                    district:'',
                    posx:'',
                    posy:'',
                    cantonCode:''
                },
                customerAction: 'add',
                customerRules: {
                    entName:[
                        {required: true, message: '请输入经营单位名称', trigger: 'blur'}
                    ]
                },
                provinces:provinces,
                czList:[],
                outLoading:false,
                customerId:'',
                customerName:'',
                mapShow:false,
                uploadAction:upload.IMG_URL+'/upload',
                customerParam1:{'appKey':APPKEY,'prodID':PRODID,businessCode:upload.randomChar(19)},
                customerParam2:{'appKey':APPKEY,'prodID':PRODID,businessCode:upload.randomChar(19)},
                customerParam3:{'appKey':APPKEY,'prodID':PRODID,businessCode:upload.randomChar(19)},
                imgDialogVisible:false,
                dialogImageUrl:'',
                imgList1:[],
                imgList2:[],
                imgList3:[],
                exampleIndex:0
            }
        },
        created:function() {
        },
        mounted: function () {
            map = new BMap.Map("allmap"); // 创建Map实例
            map.clearOverlays();//清除掉地图上面的覆盖物点
            //获取当前城市,把当前城当做地图中心
            function myFun(result) {
                cityName = result.name;
                map.centerAndZoom(result.name, 13);
            }
            var myCity = new BMap.LocalCity(); //根据ip地址,获取当前城市
            myCity.get(myFun);
            map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
            //给地图绑定点击事件
            map.addEventListener("click", this.showInfo);
            map.addEventListener("change", this.searchByStationName);
        },
        methods: {
            onChange:function (val) {
                this.exampleIndex=val;
            },
            hideMap:function () {
                this.mapShow=false
            },
            handlePictureCardPreview:function(url) {
                this.dialogImageUrl = url;
                this.imgDialogVisible = true;
            },
            handleAvatarSuccess1:function(file, fileList) {
                Vue.set(vue.customer,'logo1',file.url)
            },
            handleAvatarSuccess2:function(file, fileList) {
                Vue.set(vue.customer,'logo2',file.url)
            },
            handleAvatarSuccess3:function(file, fileList) {
                Vue.set(vue.customer,'logo3',file.url)
            },
            uploadImg:function(){
                if(this.imgList1.length==1&&(IMG_VIEW_URL+'&fileID='+this.imgList1[0]['fileID'])!=this.customer.logo1){
                    upload.delete(this.imgList1[0]['fileID']);
                }
                Vue.set(this.customerParam1,'businessCode','BUSLIC_'+this.customer.entId);
                this.$refs.customerUpload1.submit();
                if(this.imgList2.length==1&&(IMG_VIEW_URL+'&fileID='+this.imgList2[0]['fileID'])!=this.customer.logo2){
                    upload.delete(this.imgList2[0]['fileID']);
                }
                Vue.set(this.customerParam2,'businessCode','BUSCERT_'+this.customer.entId);
                this.$refs.customerUpload2.submit();
                if(this.imgList3.length==1&&(IMG_VIEW_URL+'&fileID='+this.imgList3[0]['fileID'])!=this.customer.logo3){
                    upload.delete(this.imgList3[0]['fileID']);
                }
                Vue.set(this.customerParam3,'businessCode','PROXY_'+this.customer.entId+globalInit.enterpriseId);
                this.$refs.customerUpload3.submit();
            },
            customerImgUploadSuccess1:function(response, file, fileList) {
                console.log(file);
                if(!file.response.success){
                    $.notify('营业执照上传失败',{status:'danger',timeout:1000});

                }
            },
            customerImgUploadSuccess2:function(response, file, fileList) {
                console.log(file);
                if(!file.response.success){
                    $.notify('许可证上传失败',{status:'danger',timeout:1000});

                }
            },
            customerImgUploadSuccess3:function(response, file, fileList) {
                console.log(file);
                if(!file.response.success){
                    $.notify('授权书上传失败',{status:'danger',timeout:1000});

                }
            },
            getCustomerImg:function () {
                var fileList=upload.getImgListByBusinessCode('BUSLIC_'+this.customer.entId);//获取营业执照
                console.log(fileList);
                this.imgList1=fileList;
                var fileList2=upload.getImgListByBusinessCode('BUSCERT_'+this.customer.entId);//获取营业执照
                console.log(fileList2);
                this.imgList2=fileList2;
                var fileList3=upload.getImgListByBusinessCode('PROXY_'+this.customer.entId+globalInit.enterpriseId);//获取营业执照
                console.log(fileList3);
                this.imgList3=fileList3;
                if(fileList&&fileList.length>0){
                    Vue.set(this.customer,'logo1',IMG_VIEW_URL+'&fileID='+fileList[0]['fileID']);
                }
                if(fileList2&&fileList2.length>0){
                    Vue.set(this.customer,'logo2',IMG_VIEW_URL+'&fileID='+fileList2[0]['fileID']);
                }
                if(fileList3&&fileList3.length>0){
                    Vue.set(this.customer,'logo3',IMG_VIEW_URL+'&fileID='+fileList3[0]['fileID']);
                }
            },
            bindCustomer:function () {
                vue.$confirm('此操作将会把"'+this.customerName+'"设为代理, 是否确定?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function(){
                    ajax({
                        url:'/facilitatorCustomer/saveFacilitatorCustomer?ticketId=<%=ticketId%>',
                        data:JSON.stringify({customerId:vue.customerId}),
                        contentType:'application/json',
                        success:function (result) {
                            if(result.status&&result.data){
                                $.notify('添加代理成功',{status:'success',timeout:2000});
                                vue.customer.entId=vue.customerId;
                                vue.uploadImg();
                                setTimeout(function () {
                                    history.go(-1);
                                },2000)
                            }
                        }
                    })
                }).catch(function(){
                    vue.customerId='';
                    vue.customerName='';
                });
            },

            saveCustomer: function () {
                this.$refs['customerValidateForm'].validate(function (valid) {
                    if (valid) {
                        vue.saveCustomerAction();
                    }
                });
            },
            getCustomerInfo: function () {
                ajax({
                    url: '/enterprise/getEnterpriseInfoByEntId.htm',
                    data: {entId: this.customer.entId},
                    success: function (result) {
                        console.log(result);
                        if(result.status==1){
                            Vue.set(vue.customer,'entName',result.data.entName);
                            Vue.set(vue.customer,'contacts',result.data.contacts);
                            Vue.set(vue.customer,'contactsTel',result.data.contactsTel);
                            Vue.set(vue.customer,'entAddress',result.data.entAddress);
                            Vue.set(vue.customer,'cantonCode',result.data.cantonCode);
                            $('[data-toggle="city-picker"] input').val(result.data.cantonCode).citypicker({
                                responsive:true
                            });
//                            $('#_area').attr('value',result.data.cantonCode);
                            /*$('[data-toggle="city-picker"] input').citypicker({
                                responsive:true
                            });*/
                        }
                    }
                });
            },

            saveCustomerAction: function () {
                Vue.set(vue.customer,'cantonCode',this.customer.entId?$('[data-toggle="city-picker"] input').val():$('[data-toggle="city-picker"]').val());
                console.log(this.customer);
//                return;
                if(!this.customer.entId){//新增
                    this.customer.entType='DISPOSITION';
                    ajax({
                        url:'/facilitatorCustomer/saveProductEnt.htm',
                        data:this.customer,
//                        contentType:'application/json',
                        success:function (result) {
                            if(result.status==1){

                                if(result.data){
                                    vue.customer.entId=result.data;
                                    vue.uploadImg();
                                    swal({
                                            title: "登记企业已成功",
                                            text: "你可进行报价或继续登记",
                                            type: "success",
                                            confirmButtonColor: "#1171d1",
                                            closeOnConfirm: true,
                                            confirmButtonText: "知道了"
                                        },
                                        function(){
                                            history.go(-1);
                                        });

                                }else{
                                    if(result.infoList.length>0){
                                        $.notify(result.infoList[0],{status:'info',timeout:2000});
                                    }else{
                                        $.notify('只能创建您所在区域内的代理',{status:'info',timeout:2000});
                                    }
                                }
                            }else{
                                $.notify('添加代理失败',{status:'info',timeout:2000});
                            }
                        }
                    })
                }else{//编辑
                    if(!getParam('id')){
                        ajax({
                            url:'/facilitatorCustomer/saveFacilitatorCustomer?ticketId=<%=ticketId%>',
                            data:JSON.stringify({customerId:vue.customer.entId}),
                            contentType:'application/json',
                            success:function (result) {
                            }
                        })
                    }
                    this.customer.entCode='YFWENTCODE<%=Util.uuid32()%>';
                    ajax({
                        url:'/enterprise/updateEnterpriseInfo.htm',
                        data:this.customer,
                        success:function (result) {
                            if(result.status==1){
                                vue.uploadImg();
                                swal({
                                        title: "登记企业已成功",
                                        text: "你可进行报价或继续登记",
                                        type: "success",
                                        confirmButtonColor: "#1171d1",
                                        closeOnConfirm: true,
                                        confirmButtonText: "知道了"
                                    },
                                    function(){
                                        history.go(-1);
                                    });
                            }
                        }
                    })
                }

            },
            querySearchAsync:function(queryString, cb) {
                   if(!queryString||queryString.length<2){
                       cb([]);
                       return;
                   }
                    ajax({
                        url:'/facilitatorCustomer/listBindEnterprise.htm',
                        data:{
                            customerName:queryString,
                            entType:'DISPOSITION'
                        },
                        success:function (result) {
                            vue.outLoading = false;
                            console.log(result);
                            if(result.status==1){
//                                vue.czList=result.data.enterpriselist;
                                var enterpriselist=result.data;
                                if(!enterpriselist||enterpriselist.length==0){
                                    vue.customerId='';
                                    cb([]);
                                    return;
                                }
                                var arr=[];
                                var len=enterpriselist.length>10?10:enterpriselist.length;
                                for(var i=0;i<len;i++){
                                    var obj={
                                        value:enterpriselist[i]['customerName'],
                                        entId:enterpriselist[i]['customerId']
                                    };
                                    arr.push(obj);
                                }
                                cb(arr);
                            }
                        }
                    })
            },
            handleSelect:function(item) {
                console.log(item);
                this.customer.entName=item.value;
                this.customerId=item.entId;
                this.customerName=item.value;
                this.customer.entId=item.entId;
                this.getCustomerInfo();
                this.getCustomerImg();
            },
            changeEnt:function () {
              console.log(this.customer.entInfo);
            },
            remoteMethod:function (query) {
                this.outLoading = true;
                ajax({
                    url:'/userservice/getSuggestEnterpriseList.htm',
                    data:{
                        enterpriseName:query,
                        enterpriseType:'DISPOSITION'
                    },
                    success:function (result) {
                        vue.outLoading = false;
                        console.log(result);
                        if(result.status==1){
                            vue.czList=result.data.enterpriselist;
                        }
                    }
                })
            },
            changeSection:function () {
                this.customer.wasteType='';
            },
            getLocationInfo:function (pt,rs) {
                var addComp = rs.addressComponents;
                addr = addComp.province  + addComp.city  + addComp.district+ addComp.street  + addComp.streetNumber;
//                var regionalism = addComp.province  + addComp.city  + addComp.district;
//                console.log(pt.lng + ", " + pt.lat+','+regionalism+','+addr+','+addComp.province+','+addComp.city+','+addComp.district);
                if(this.mapShow){
                    vue.customer.entAddress=addr;
                }
//                vue.customer.province=addComp.province;
                vue.customer.city=addComp.city;
                vue.customer.district=addComp.district;
                vue.customer.posx=pt.lng;
                vue.customer.posy=pt.lat;
                this.$refs['customerValidateForm'].validateField('entAddress');
            },
            showInfo:function(e){
                //清除掉地图上面的覆盖物点
                map.clearOverlays();
                //获取经纬度
                var msg = e.point.lng + ", " + e.point.lat;
                //创建一个覆盖物点
                var new_point = new BMap.Point(e.point.lng, e.point.lat);
                //创建标注
                marker = new BMap.Marker(new_point);
                //将标注添加到地图中
                map.addOverlay(marker);
                //设置标注可拖动
                marker.enableDragging();
                //给标注点添加--鼠标拖拽结束时触发此事件。
                marker.addEventListener("dragend",this.moveMark);
                map.panTo(new_point);
                //点击的时候把经纬度坐标显示
                $("#latitudeAndLongitude").val(msg);
                //逆地址解析---根据经纬度拿到地址
                var geoc = new BMap.Geocoder();
                var pt = e.point;
                geoc.getLocation(pt, function(rs){
                    vue.getLocationInfo(pt,rs);
                });
                var opts = {
                    width : 300, //信息窗口宽度
                    height : 10, //信息窗口高度
                };
                //消息窗口的内容根据定位的结果获取
                if (addr) {
                    //创建信息窗口对象
                    var infoWindow = new BMap.InfoWindow("地址：" + addr, opts);
                    //开启信息窗口
                    map.openInfoWindow(infoWindow, new_point);
                } else {
                    //创建信息窗口对象
                    var infoWindow = new BMap.InfoWindow("提示： 无法定位该地址信息，请重新选择！", opts);
                    //开启信息窗口
                    map.openInfoWindow(infoWindow, new_point);
                }
            },
            showAndSearch:function () {
                this.showMap();
                this.searchByStationName();
            },
            showMap:function () {
                this.mapShow=true;
            },
             searchByStationName:function() {
                //清空原来的标注
                map.clearOverlays();
                addr = this.customer.entAddress;
                // 创建地址解析器实例
                var myGeo = new BMap.Geocoder();
                // 将地址解析结果显示在地图上,并调整地图视野
                myGeo.getPoint(addr, function(point){
                    if (point){
                        map.centerAndZoom(cityName,11);
                        //创建标注
                        marker = new BMap.Marker(point);
                        //将标注添加到地图中
                        map.addOverlay(marker);
                        //设置标注可拖动
                        marker.enableDragging();
                        //给标注点添加--鼠标拖拽结束时触发此事件
                        marker.addEventListener("dragend",this.moveMark);
                        map.panTo(point);
                        //逆地址解析---根据经纬度拿到地址
                        var geoc = new BMap.Geocoder();
                        geoc.getLocation(point, function(rs){
                            vue.getLocationInfo(point,rs);
                        });
                        var opts = {
                            width : 200, //信息窗口宽度
                            height : 10, //信息窗口高度
                        };
                        //消息窗口的内容根据定位的结果获取
                        if (addr) {
                            //创建信息窗口对象
                            var infoWindow = new BMap.InfoWindow("地址：" + addr, opts);
                            //开启信息窗口
                            map.openInfoWindow(infoWindow, point);
                        }else{
                            //创建信息窗口对象
                            var infoWindow = new BMap.InfoWindow("提示： 无法定位该地址信息，请重新选择！", opts);
                            //开启信息窗口
                            map.openInfoWindow(infoWindow, point);
                        }
                    }else{
                        vue.customer.district='';
                        vue.customer.city='';
                        vue.customer.posx='';
                        vue.customer.posy='';
//                        alert("您选择地址没有解析到结果!");
                    }}, cityName);
            },
            moveMark:function(e){
                var pt = marker.getPosition();
                //获取经纬度
                var msg = pt.lng + ", " + pt.lat;
                //点击的时候把经纬度坐标显示
                $("#latitudeAndLongitude").val(msg);
                //逆地址解析---根据经纬度拿到地址
                var geoc = new BMap.Geocoder();
//                var pt = e.point;
                geoc.getLocation(pt, function(rs){
                    vue.getLocationInfo(pt,rs);
                });
                var opts = {
                    width : 300, // 信息窗口宽度
                    height : 10, // 信息窗口高度
                };
                //消息窗口的内容根据定位的结果获取
                if (addr) {
                    //创建信息窗口对象
                    var infoWindow = new BMap.InfoWindow("地址：" + addr, opts);
                    //开启信息窗口
                    map.openInfoWindow(infoWindow, p);
                }else{
                    //创建信息窗口对象
                    var infoWindow = new BMap.InfoWindow("提示： 无法定位该地址信息，请重新选择！", opts);
                    //开启信息窗口
                    map.openInfoWindow(infoWindow, p);
                }
            }
        }
    })
</script>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true"/>

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function () {
        var rs = window.globalInit();
        rs.done(function () {
            if(getParam('id')){
                vue.getCustomerInfo();
                vue.getCustomerImg();
            }else{
                $('[data-toggle="city-picker"]').citypicker({
                    responsive:true
                });
            }
        });
    });
</script>