<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%
    String appPath = request.getContextPath();
    String ticketId = (String) request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
    <jsp:param name="title" value="活动管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
    <jsp:param name="menuId" value="#activityList"/>
</jsp:include>
<link href="<%=appPath %>/css/activity/style.css?3" rel="stylesheet" type="text/css" />
<section>
    <div class="hidden"><!-- city-picker选择时获取数据参数的设定 ID不能改变 -->
        <input type='hidden' id="appPath" value="<%=appPath %>"/>
        <input type='hidden' id="ticketId" value="<%=ticketId %>"/>
    </div>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">活动管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">活动列表</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div>
        <div class="row">
            <div class="col-md-12">
                <div class="tool-div">
                    <div class="btn-group" role="group">
                        <%--<a class="btn btn-primary" href="javascript:;"><i class="fa fa-plus"></i>创建综合性活动</a>--%>
                        <a class="btn btn-primary" href="<%=appPath%>/wasteActivity/add.htm?ticketId=<%=ticketId%>"><i class="fa fa-plus"></i>创建活动</a>
                        <a class="btn btn-default" href="javascript:;" onclick="activity.edit();">编辑</a>
                        <a class="btn btn-default" href="javascript:;" onclick="activity.show();">查看</a>
                        <a class="btn btn-default" href="javascript:;" onclick="activity.publish();">发布</a>
                        <a class="btn btn-default" href="javascript:;" onclick="activity.pause();">暂停</a>
                        <a class="btn btn-default" href="javascript:;" onclick="activity.delete();">删除</a>
                    </div>
                    <a class="btn btn-primary" href="javascript:;" onclick="activity.updateTohome();" style="float: right;width: 100px">更新到首页</a>
                </div>
                <div class="waste-panel table-responsive">
                    <div id="userList_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">
                        <div id="userList_processing" class="dataTables_processing panel panel-default"
                             style="display: none;">处理中...
                        </div>
                        <table id="userList" class="table table-striped table-hover table-condensed dataTable" >
                            <thead>
                            <tr role="row">
                                <th>
                                    <label class="checkbox-inline c-checkbox">
                                        <input id="checkAll" name="checkAll" type="checkbox">
                                        <span class="fa fa-check"></span>
                                    </label>
                                </th>
                                <th>活动名称</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>支付状态</th>
                                <th>状态</th>
                                <th>创建时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                        <div class="bottom">
                            <div class="dataTables_length" id="userList_length"><label>每页
                                <select name="userList_length" class="form-control input-sm">
                                <option value="10">10</option>
                                <option value="25">25</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select> 项</label></div>
                            <div class="dataTables_info" id="userList_info" role="status" aria-live="polite">
                                当前显示第 1 至 10 项，共 25 项。
                            </div>
                            <div class="dataTables_paginate paging_simple" id="userList_paginate">
                                <ul class="pagination">
                                    <li class="paginate_button previous disabled" id="userList_previous">
                                        <a href="javascript:;">上页</a>
                                    </li>
                                    <li class="paginate_button next" id="userList_next">
                                        <a href="javascript:;">下页</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script type="text/javascript" src="<%=appPath%>/main/pc/js/activityList.js?3"></script>