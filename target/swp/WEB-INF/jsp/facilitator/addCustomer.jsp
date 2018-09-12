<%@page import="com.mlsc.waste.utils.*" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="客户"/>
</jsp:include>
<jsp:include page="/common/webCommon/facilitatorMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myCustomer"/>
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link href="<%=appPath %>/thirdparty/city-picker/css/city-picker.css" rel="stylesheet" type="text/css" />
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/main/common/provinces.js"></script>
<script src="<%=appPath%>/main/common/upload/upload.js"></script>
<section id="app">
    <template>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>我的易废圈</el-breadcrumb-item>
            <el-breadcrumb-item onclick="history.go(-1);">客户管理</el-breadcrumb-item>
            <el-breadcrumb-item>{{customer.entId?'编辑':'新增'}}客户</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="panel panel-body">
            <div class="panel panel-body">
                <el-form label-position="right" label-width="160px" style="max-width: 800px;margin-top: 16px;"
                         :model="customer" ref="customerValidateForm" :rules="customerRules">
                    <el-form-item label="企业名称：" prop="entName">
                            <el-autocomplete style="width: 400px"
                                    v-model="customer.entName"
                                    :fetch-suggestions="querySearchAsync"
                                    placeholder="请输入企业名称"
                                    @select="handleSelect">
                            </el-autocomplete>
                            <el-button type="primary" class="save" v-if="customerId" @click="bindCustomer">设为客户</el-button>
                    </el-form-item>
                    <el-form-item label="联系人：" prop="contacts">
                        <el-input v-model="customer.contacts" placeholder="请输入联系人"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话：" prop="contactsTel">
                        <el-input v-model="customer.contactsTel" placeholder="请输入联系电话"></el-input>
                    </el-form-item>
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
                    contacts:'',
                    contactsTel:'',
                    city:'',
                    district:'',
                    posx:'',
                    posy:'',
                    cantonCode:''
                },
                customerAction: 'add',
                customerRules: {
                    entName:[
                        {required: true, message: '请输入企业名称', trigger: 'blur'}
                    ]/*,
                    entAddress:[
                        {required: true, message: '请输入企业地址', trigger: 'blur'},
                        { validator: validateAddress, trigger: 'blur' },
                    ]*/
                },
                provinces:provinces,
                czList:[],
                outLoading:false,
                customerId:'',
                customerName:'',
                mapShow:false
            }
        },
        created:function() {
        },
        mounted: function () {
//            return;
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
            hideMap:function () {
                this.mapShow=false
            },
            bindCustomer:function () {
                vue.$confirm('此操作将会把"'+this.customerName+'"设为客户, 是否确定?', '提示', {
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
                                $.notify('添加客户成功',{status:'success',timeout:2000});
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
                    this.customer.entType='PRODUCTION';
                    ajax({
                        url:'/facilitatorCustomer/saveProductEnt.htm',
                        data:this.customer,
//                        contentType:'application/json',
                        success:function (result) {
                            if(result.status==1){
                                if(result.data){
                                    $.notify('客户添加成功',{status:'success',timeout:2000});
                                    setTimeout(function () {
                                        history.go(-1);
                                    },2000)
                                }else{
                                    if(result.infoList.length>0){
                                        $.notify(result.infoList[0],{status:'info',timeout:2000});
                                    }else{
                                        $.notify('只能创建您所在区域内的客户',{status:'info',timeout:2000});
                                    }
                                }
                            }else{
                                $.notify('添加客户失败',{status:'info',timeout:2000});
                            }
                        }
                    })
                }else{//编辑
                    this.customer.entCode='YFWENTCODE<%=Util.uuid32()%>';
                    ajax({
                        url:'/enterprise/updateEnterpriseInfo.htm',
                        data:this.customer,
                        success:function (result) {
                            if(result.status==1){
                                $.notify('修改成功',{status:'success',timeout:2000});
                                setTimeout(function () {
                                    history.go(-1);
                                },2000)
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
                            entType:'PRODUCTION'
                        },
                        success:function (result) {
                            vue.outLoading = false;
                            console.log(result);
                            if(result.status==1){
//                                vue.czList=result.data.enterpriselist;
                                var enterpriselist=result.data;
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
        if(getParam('id')){
            vue.getCustomerInfo();
        }else{
            $('[data-toggle="city-picker"]').citypicker({
                responsive:true
            });
        }
    });
</script>