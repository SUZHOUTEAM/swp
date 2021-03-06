<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
    String activityId = (String)request.getAttribute("activityId");
%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="企业信息"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#activityList" />
</jsp:include>
<link href="<%=appPath %>/thirdparty/city-picker/css/city-picker.css" rel="stylesheet" type="text/css" />
<link href="<%=appPath %>/css/activity/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
    var ticketId='<%=ticketId %>';
</script>
<!-- Main section-->
<section>
    <div class="hidden"><!-- city-picker选择时获取数据参数的设定 ID不能改变 -->
		<input type='hidden' id="appPath" value="<%=appPath %>"/>
		<input type='hidden' id="activityId" value="<%=activityId %>"/>
	</div>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">活动管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="history.go(-1);">活动列表</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">活动查看</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <!-- Page content-->
    <div class="panel panel-body">
        <div class="panel-body">
            <div class="form-container">
                <div class="title">
                    <span>活动详情</span>
                </div>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">活动名称:</label>
                        <div class="col-sm-10">
                            <input type="text" id="activityName" class="form-control" placeholder="输入活动名称(30字以内)" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">活动企业:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="entName" placeholder="选择活动企业" readonly>
                            <div class="input-group-btn" id="entName_dropdown">
                                <ul class="dropdown-menu" role="menu">
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">活动价格:</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="activityPrice" id="inlineRadio1" value="1" show> 一口价
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="activityPrice" id="inlineRadio2" value="2" show> 价格范围
                            </label>
                        </div>
                    </div>
                    <div class="form-group" id="fixed">
                        <label class="col-sm-2 control-label">&nbsp;</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control price" id="price" onkeyup="activity.clearNoNum(this);" placeholder="输入价格" readonly>元/吨
                        </div>
                    </div>
                    <div class="form-group" id="range">
                        <label class="col-sm-2 control-label">&nbsp;</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control price min-price" onkeyup="activity.clearNoNum(this);" id="min-price" placeholder="最低价" readonly>--
                            <input type="text" class="form-control price max-price" onkeyup="activity.clearNoNum(this);" id="max-price" placeholder="最高价" readonly>元/吨
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">活动说明:</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" id="activityRemark" placeholder="输入活动说明（300个字以内）..." maxlength="300" readonly></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">活动宣传大图:</label>

                        <div class="col-sm-10">
                            <a href="javascript:;" class="upload_btn" id="subject_file">
                                <span><img src="<%=appPath %>/main/pc/img//upload.png"></span>
                                <span>点击上传图片</span>
                                <span>(图片尺寸840*430像素)</span>
                            </a>
                            <div class="previewImg" id="previewImg1">
                                <span><img /></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">活动宣传长图:</label>
                        <div class="col-sm-10">
                            <a href="javascript:;" class="upload_btn" id="subject_file3">
                                <span><img src="<%=appPath %>/main/pc/img//upload.png"></span>
                                <span>点击上传图片</span>
                                <span>(图片尺寸1920*60像素)</span>
                            </a>
                            <div class="previewImg"  id="previewImg3">
                                <span><img /></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">活动宣传Logo:</label>
                        <div class="col-sm-10">
                            <a href="javascript:;" class="upload_btn" id="subject_file2">
                                <span><img src="<%=appPath %>/main/pc/img//upload.png"></span>
                                <span>点击上传图片</span>
                                <span>(图片尺寸200*280像素)</span>
                            </a>
                            <div class="previewImg"  id="previewImg2">
                                <span><img /></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">手机轮播图:</label>
                        <div class="col-sm-10">
                            <a href="javascript:;" class="upload_btn" id="subject_file4">
                                <span><img src="<%=appPath %>/main/pc/img//upload.png"></span>
                                <span>点击上传图片</span>
                                <span>(图片尺寸200*280像素)</span>
                            </a>
                            <div class="previewImg"  id="previewImg4">
                                <span><img /></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否归于综合活动?</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="hasParent" id="inlineRadio3" value="1" show> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="hasParent" id="inlineRadio4" value="2" show> 否
                            </label>
                        </div>
                    </div>
                    <div class="form-group" id="parentActivity">
                        <label class="col-sm-2 control-label">选择活动:</label>
                        <div class="col-sm-10">
                            <select class="form-control" placeholder="选择活动" readonly>
                                <option value="0">选择活动</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" id="activityTime">
                        <label class="col-sm-2 control-label">活动时间:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control datetime" id="start_date" placeholder="开始时间"  disabled>--
                            <input type="text" class="form-control datetime" id="end_date" placeholder="结束时间"  disabled>
                        </div>
                    </div>
                    <div class="form-group"  id="activityArea">
                        <label class="col-sm-2 control-label">活动区域:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="_area" placeholder="选择区域" data-toggle="city-picker">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">视频地址:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="videoResource" readonly placeholder="输入直播地址或者回看视频源地址,不是直播则不填" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">短信模板:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="smsTemplate" readonly placeholder="输入短信模板" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">发送短信时间:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="cronJob" readonly placeholder="输入发送短信时间" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">报名费:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="enrollFee" readonly value="0" placeholder="输入报名费" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">众筹截止时间:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control datetime" disabled id="riseEndDate" style="width: 399px" placeholder="输入众筹截止时间,没有报名费可不填">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">众筹折扣:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="discount" readonly placeholder="输入众筹折扣,小数,最多两位小数,没有报名费可不填" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">团购人数:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="numberGroupBuying" readonly placeholder="输入团购人数,没有报名费可不填" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">主讲人:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="presenter" readonly placeholder="输入主讲人" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">课程时长:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="duration" readonly placeholder="输入课程时长" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">课程说明:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="customCourseDesc" readonly placeholder="输入课程说明" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">付费说明:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="payNote" readonly placeholder="输入付费说明" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">行业:</label>
                        <div class="col-sm-10">
                            <select class="form-control" id="industry">
                                <option value="">不限制</option>
                                <option value="AutoRepair">汽修行业</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script>
    var readonly = true;
</script>
<script type="text/javascript" src="<%=appPath %>/thirdparty/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.data.js"></script>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/main/common/jquery.form.js"></script>
<script src="<%=appPath %>/main/pc/js/activityCreate.js"></script>
