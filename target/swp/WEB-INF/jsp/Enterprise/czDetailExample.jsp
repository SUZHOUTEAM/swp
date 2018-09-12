<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
	String appPath=request.getContextPath();
	String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
	<jsp:param name="title" value="资源池" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
	<jsp:param name="menuId" value="#myIndex" />
</jsp:include>
<link rel="stylesheet" href="<%=appPath%>/main/common/elementui/index.css">
<link rel="stylesheet" href="<%=appPath%>/css/enterprise/detail.css?8">
<link rel="stylesheet" href="<%=appPath%>/css/enterprise/timeline.css?1">
<script type="text/javascript" src="<%=appPath%>/thirdparty/city-picker/js/city-picker.data.js"></script>
<section>
	<div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="goBack();">资源池</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
		<span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">处置企业详情</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
	</div>
	<div class="panel panel-body" style="padding-bottom: 0">
		<div class="btns">
			<a href="javascript:history.go(-1);">返回</a>
			<a href="<%=appPath%>/enterprise/czEdit.htm?ticketId=<%=ticketId%>">去完善信息</a>
		</div>
		<div id="app">
			<template>

				<el-tabs v-model="activeName" type="card" @tab-click="handleClick">
					<el-tab-pane label="概况" name="first">
						<div class="top">
							<el-carousel :autoplay="false" height="380px">
								<el-carousel-item v-for="(item,index) in imgList" :key="index">
									<img :src="item"/>
								</el-carousel-item>
							</el-carousel>
							<div class="enterName">
								江苏新宇泰华环保集团有限公司
							</div>
							<el-row class="da">
								<el-col :span="18" style="padding-left: 37px;"><i class="publisharea"></i>地址：宜兴市高腾外商投资工业园新宇泰华录6号</el-col>
								<el-col :span="6" style="padding-right: 37px;text-align: right">
									<el-button type="primary" @click="contact()"><img src="http://www.yifeiwang.com/img/source/concact_icon.png">  联系TA</el-button>
								</el-col>
							</el-row>
						</div>
						<div class="desc">
							<p class="desc_title">企业简介</p>
							<p class="desc_content" v-for="(item,index) in entDesc" style="text-indent: 2em">
								{{index==0?('江苏新宇泰华环保集团有限公司'+item):item}}
							</p>
							<p class="desc_title" style="margin-top: 30px">合作伙伴</p>
							<div class="desc_content" style="padding: 10px 0">
								<div class="entItem" v-for="(item,index) in partnerList">
									<div class="entItem_logo"><img :src="item.logo"/></div>
									<div class="entItem_name">{{item.name}}</div>
								</div>
							</div>
						</div>
					</el-tab-pane>
					<el-tab-pane label="经营许可证" name="second" class="second">
						<el-row class="license">
							<el-col :span="10">
								<div class="licenseTableDiv">
									<table>
										<tr>
											<td colspan="2" style="font-size: 14px;color: #222;">危险化学品经营许可证信息</td>
										</tr>
										<tr>
											<td>许可证编号：</td>
											<td class="text">JD0089444649854</td>
										</tr>
										<tr>
											<td>有效期：</td>
											<td class="text">2018.07.01-2027.06.30</td>
										</tr>
										<tr>
											<td>年许可量：</td>
											<td class="text">5500.00吨</td>
										</tr>
										<tr>
											<td colspan="2" style="padding: 20px 10px">
												<img src="http://www.yifeiwang.com/img/source/xukezheng.jpg" @click="showLargeImg()"/>
											</td>
										</tr>
									</table>
								</div>
							</el-col>
							<el-col :span="14" style="padding: 20px">
								<img src="http://www.yifeiwang.com/img/source/xukezheng.jpg" @click="showLargeImg()"/>
								<span class="tip">危险废物经营许可证副本照片（点击查看大图）</span>
							</el-col>
							<el-dialog
									title="经营许可证"
									:visible.sync="dialogVisible"
									:modal-append-to-body="false"
									width="90%">
								<img src="http://www.yifeiwang.com/img/source/xukezheng.jpg"/>
							</el-dialog>
						</el-row>


					</el-tab-pane>
					<el-tab-pane label="资质与荣誉" name="third" id="third">
						<el-row>
							<el-col :span="18">
								<div style="background-color: #ffffff">
									<div class="VivaTimeline" id="mainVivaTimeline">
										<dl></dl>
									</div>
								</div>
							</el-col>
							<el-col :span="6">
								<div class="VivaTimeline VivaTimelineBar">
									<dl v-for="item1 in ryList">
										<dd class="pos-left clearfix"  v-for="item in item1.content" @click="scrollTo(item.id)">
											<div class="circ"><i class="fa fa-circle"></i></div>
											<div class="time">{{item.name}}</div>
											<div class="events">
											</div>
										</dd>
									</dl>
									<div class="toTop" @click="toTop()"><img src="http://www.yifeiwang.com/img/source/top.png"/></div>
								</div>
							</el-col>
						</el-row>
					</el-tab-pane>
					<el-tab-pane label="销售及合同" name="five">
						<div class="desc">
							<p class="desc_title">销售说明</p>
							<p class="desc_content" v-for="(item,index) in saleDesc">
								{{index+1}}. {{item}} 
							</p>
						</div>
						<div class="desc" style="background-color: #f6f7fb">
							<el-row>
								<el-col :span="12">
									<p class="desc_title">附件下载</p>
									<p class="desc_content" v-for="(item,index) in fujianList">
										<span> {{item.name}} </span><el-button type="text" @click="download(item.path)">点击下载</el-button>
									</p>
								</el-col>
								<el-col :span="12">
									<p class="desc_title">合同下载</p>
									<p class="desc_content" v-for="(item,index) in hetongList">
										<span> {{item.name}} </span><el-button type="text" @click="download(item.path)">点击下载</el-button>
									</p>
								</el-col>
							</el-row>
						</div>
					</el-tab-pane>
				</el-tabs>
			</template>
		</div>

		<!-- 先引入 Vue -->
		<script src="<%=appPath%>/main/common/elementui/vue.min.js"></script>
		<%--<!-- 引入组件库 -->--%>
		<script src="<%=appPath%>/main/common/elementui/index.js"></script>
		<script src="<%=appPath %>/app/js/constants.js"></script>
		<script src="<%=appPath %>/app/js/util.js"></script>
		<script src="<%=appPath %>/main/pc/js/data.js"></script>
		<script src="<%=appPath %>/thirdparty/la-number/la-number.js"></script>
		<script>
            var vue = new Vue({
                el: '#app',
                data:{
                    activeName: 'first',
                    cactiveName:'cfirst',
					imgList:imgList,
                    videoList:videoList,
                    pfList:pfList,
                    partnerList:partnerList,
                    fujianList:fujianList,
                    hetongList:hetongList,
                    saleDesc:saleDesc,
                    entDesc:entDesc,
                    ryList:ryList,
                    dialogVisible:false
                },
                mounted:function () {
                    var heights=document.body.clientHeight;
                    $('#app >.el-tabs >.el-tabs__content').height(heights-240+'px');
                    window.onresize=function () {
                        var heights=document.body.clientHeight;
                        $('#app >.el-tabs >.el-tabs__content').height(heights-240+'px');
                    }
                },
                methods:{
                    handleClick:function(tab, event) {
                        console.log(tab, event);
                        if(tab.index!="3"){
                            var play1=$('#video_content video');
                            var play2=$('#video_content2 video');
                            if(play1.length>0){
                                play1[0].pause();
							}
							if(play2.length>0){
                                play2[0].pause();
							}
						}
                    },
					download:function (path) {
						window.location=path;
                    },
                    showLargeImg:function () {
						this.dialogVisible=true;
                    },
                    scrollTo:function (id) {
                        document.getElementById(id).scrollIntoView(true);
                    },
                    contact:function () {
                        getEnterpriseContacts('${enterprise.entId}','${enterprise.entName}','处置企业例子主页');
                    },
                    toTop:function () {
                      document.getElementById('third').scrollIntoView(true);
                    },
					play:function () {
                        var url='<%=appPath%>/main/pc/file/oceans.mp4';
                        var str='<video src="'+url+'" width="100%"  height="auto" class="video-js" autoplay="true" controls>'+
                            '<object>'+
                            '<embed width="100%" height="auto" showcontrols="1" autostart="1" src="'+url+'" ></embed>'+
                            '</object>'+
                            '</video>';
                        $('#video_content').html(str);
                    },
					play2:function () {
                        var url='<%=appPath%>/main/pc/file/oceans.mp4';
                        var str='<video src="'+url+'" width="100%"  height="auto" class="video-js" autoplay="true" controls>'+
                            '<object>'+
                            '<embed width="100%" height="auto" showcontrols="1" autostart="1" src="'+url+'" ></embed>'+
                            '</object>'+
                            '</video>';
                        $('#video_content2').html(str);
                    },
                }
            });
		</script>
	</div>
</section>

<%------- 导入底部信息 -------%>
<jsp:include page="/common/webCommon/bottom.jsp" flush="true" />

<%------- 结束导入底部信息 -------%>
<script type="text/javascript">
    $(document).ready(function() {
        var rs = window.globalInit();
        rs.done(function () {
            initTimeLine();
        });
    });
    function initTimeLine(){
        var str='';
        var flag=true;
        for(var k in ryList){
            var year=ryList[k]['year'];
            str+='<dt><i class="el-icon-time"></i><span class="time">'+year+'年</span></dt>';
            for(var i=0;i<ryList[k]['content'].length;i++){
                var obj=ryList[k]['content'][i];
                str+='<dd class="pos-'+(flag?'right':'left')+' clearfix" id="'+obj['id']+'">'+
                    '<div class="circ"><i class="el-icon-time"></i></div>'+
                    '<div class="time">'+obj['time']+'</div>'+
                    '<div class="events">'+
                    '<div class="events-header">'+obj['name']+'</div>'+
                    '<div class="events-body">'+obj['description']+'</div>'+
                    '<div class="events-footer">'+
                    '<img src="<%=appPath%>/app/img/shu.png" />'+
                    '</div>'+
                    '</div>'+
                    '</dd>';
                flag=!flag;
            }
        }
        $('#mainVivaTimeline dl').html(str);
    }
    function goBack(){
        var index = getParam("index");
        if(index == "true"){
            document.cookie="index=true;path=<%=appPath%>/wastecircle/";
        }
        window.history.go(-1);
    }
</script>
</body>