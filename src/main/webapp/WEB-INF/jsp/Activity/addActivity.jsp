<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
    String activityId = (String)request.getAttribute("activityId");
%>
<jsp:include page="/common/webCommon/user_top.jsp" flush="true">
    <jsp:param name="title" value="我的活动" />
</jsp:include>
<jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
    <jsp:param name="menuId" value="#myActivity" />
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
            <span class="el-breadcrumb__item__inner">我的易废圈</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner" onclick="history.go(-1);">我的活动</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">活动${activityId?'编辑':'新增'}</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <!-- Page content-->
        <div class="panel-body">
            <div class="form-container">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">活动名称:</label>
                        <div class="col-sm-10">
                            <input type="text" id="activityName" class="form-control" placeholder="输入活动名称(30字以内)">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">活动价格:</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="activityPrice" id="inlineRadio1" value="1"> 一口价
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="activityPrice" id="inlineRadio2" value="2"> 价格范围
                            </label>
                        </div>
                    </div>
                    <div class="form-group" id="fixed">
                        <label class="col-sm-2 control-label">&nbsp;</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control price" id="price" onkeyup="activity.clearNoNum(this);" placeholder="输入价格">元/吨
                        </div>
                    </div>
                    <div class="form-group" id="range">
                        <label class="col-sm-2 control-label">&nbsp;</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control price min-price" onkeyup="activity.clearNoNum(this);" id="min-price" placeholder="最低价">--
                            <input type="text" class="form-control price max-price" onkeyup="activity.clearNoNum(this);" id="max-price" placeholder="最高价">元/吨
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">活动说明:</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" id="activityRemark" placeholder="输入活动说明（300个字以内）..." maxlength="300"></textarea>
                        </div>
                    </div>
                    <div class="form-group" style="display: none">
                        <label class="col-sm-2 control-label">是否归于综合活动?</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="hasParent" id="inlineRadio3" value="1"> 是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="hasParent" id="inlineRadio4" value="2"> 否
                            </label>
                        </div>
                    </div>
                    <div class="form-group" id="parentActivity">
                        <label class="col-sm-2 control-label">选择活动:</label>
                        <div class="col-sm-10">
                            <select class="form-control" placeholder="选择活动">
                                <option value="0">选择活动</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" id="activityTime">
                        <label class="col-sm-2 control-label">活动时间:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control datetime" id="start_date" placeholder="开始时间">--
                            <input type="text" class="form-control datetime" id="end_date" placeholder="结束时间">
                        </div>
                    </div>
                    <div class="form-group"  id="activityArea">
                        <label class="col-sm-2 control-label">活动区域:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="_area" placeholder="选择区域" data-toggle="city-picker">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <a href="javascript:;" class="btn btn-primary" id="submit">提交</a>
                        </div>
                    </div>
                </form>
            </div>
    </div>
</section>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script type="text/javascript" src="<%=appPath %>/thirdparty/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=appPath %>/thirdparty/eonasdan-bootstrap-datetimepicker/build/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.data.js"></script>
<script src="<%=appPath %>/thirdparty/city-picker/js/city-picker.js"></script>
<script src="<%=appPath %>/app/js/constants.js"></script>
<script src="<%=appPath %>/main/common/jquery.form.js"></script>
<script src="<%=appPath %>/main/pc/js/activityCreate-disposition.js?1"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var rs = window.globalInit();
        rs.done(function () {
        });
    });
</script>