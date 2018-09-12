<%@page import="com.mlsc.waste.utils.CodeTypeConstant"%>
<%@page import="com.mlsc.waste.utils.Constant"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<c:if test="${isSysUser}">
    <jsp:include page="/common/top.jsp" flush="true">
      <jsp:param name="title" value="企业信息"/>
    </jsp:include>
    <jsp:include page="/common/left.jsp" flush="true">
        <jsp:param name="menuId" value="#enterpriseManagement" />
    </jsp:include>
</c:if>
<c:if test="${not isSysUser}">
    <jsp:include page="/common/webCommon/user_top.jsp" flush="true">
      <jsp:param name="title" value="许可证管理"/>
    </jsp:include>
    <jsp:include page="/common/webCommon/wasteCircleMenu.jsp" flush="true">
        <jsp:param name="menuId" value="#licenceManage" />
    </jsp:include>
</c:if>
<link rel="stylesheet" href="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.css">
<script src="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="<%=appPath %>/thirdparty/datatables-colvis/css/dataTables.colVis.css">
<link rel="stylesheet" href="<%=appPath %>/thirdparty/datatables/media/css/dataTables.bootstrap.css">
<link rel="stylesheet" href="<%=appPath %>/thirdparty/dataTables.fontAwesome/index.css">
<!-- DATATABLES-->
<%--<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>--%>
<script src="<%=appPath %>/thirdparty/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="<%=appPath %>/thirdparty/datatables-colvis/js/dataTables.colVis.js"></script>
<script src="<%=appPath %>/thirdparty/datatables/media/js/dataTables.bootstrap.js"></script>
<script src="<%=appPath %>/main/common/main.js"></script>
<link rel="stylesheet" href="<%=appPath %>/css/licence/licence.css">
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">我的企业</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner"><a href="<%=appPath%>/licence/detail.htm?ticketId=<%=ticketId%>">许可证管理</a></span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">许可证列表</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
         <div class="row">
             <div class="col-md-12">
                <div class="tool-div">
                    <div class="btn-group botton-margin">  
                        <div class="btn-group botton-margin"> 
                            <button type="button" class="btn btn-default" id="read" onclick="viewLicence(this)">查看许可证详细信息</button>
                        </div>
                        <div class="btn-group botton-margin">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                <span>更多</span><span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                  <li><a class="btn btn-default" href="#" role="button" onclick="submitAudit(this)">提交审核</a></li>
                                  <li><a class="btn btn-default" href="#" role="button" onclick="editLicence(this)">编辑许可证</a></li>
                                  <li><a class="btn btn-default" href="#" role="button" onclick="deleteLicence(this)">删除许可证</a></li>
                            </ul>      
                        </div>    
                     </div>
                </div>
                <div class="waste-panel table-responsive">
                    <table id="example" class="table table-striped table-hover table-condensed">
                        <thead>
                            <tr>
                                <th>
                                    <label class="checkbox-inline c-checkbox">
                                        <input id="checkAll" name="checkAll" type="checkbox">
                                        <span class="fa fa-check"></span>
                                    </label>
                                </th>
								<th>许可证编号</th>
								<th>许可证状态</th>
								<th>核准经营方式</th>
								<th>审批状态</th>
								<th>发证机关</th>
								<th>发证日期</th>
								<th>有效期</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
						<tfoot class='filter'>
							<tr>
                                <th></th>
								<th>许可证编号</th>
								<th>许可证状态</th>
								<th></th>
								<th>审批状态</th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</tfoot>
                    </table>
                </div>
                <div class="hidden" id="auditStatus"><!-- 许可证审批状态下拉列表组装 -->
					<c:forEach var="codevalue" items="${codeValues}" varStatus="status" begin="0" step="1">
					    <option value='${codevalue.id}'>${codevalue.value}</option>
					</c:forEach>
                </div>
            </div>
    </div>
</section> 
<jsp:include page="/common/bottom.jsp" flush="true"/>

<script type="text/javascript">
var table ;
var searchTimeOut;//timeout对象
var idx;//过虑的列序号
var val;//过虑值
function search2() {
    table.column( idx ).search( val ).draw();
}

$(document).ready( function () {
  var rs = window.globalInit();
  rs.done(function () {
    init();
  });

} );
function init() {
  $('#example tfoot th').each( function () {
    if($(this).index() == 2){
      var select = $('<select class="form-control input-sm datatable_input_col_search"><option value="">全部</option><select>').appendTo( $(this).empty() );
      select.append( '<option value="INVALID">未生效</option>' );
      select.append( '<option value="OVERDUE">过期</option>' );
    } else if($(this).index() == 4){
      var select = $('<select class="form-control input-sm datatable_input_col_search"><option value="">全部</option><select>').appendTo( $(this).empty() );
      select.append($("#auditStatus").html());
    } else{
      var title = $('#example tfoot th').eq($(this).index()).text();
      if(title!=''){
        $(this).html( '<input type="text" placeholder="搜索 '+title+'" class="form-control input-sm datatable_input_col_search"/>' );
      }
    }
  });

  table = $('#example').DataTable({
    "processing": true,
    "serverSide": true,
    "ajax": $.fn.dataTable.pipeline( {
      url: '<%=appPath%>/licence/listForJson.htm?ticketId=<%=ticketId %>&enterpriseId=${enterpriseId}',
      pages: 2
    } ),
    "columns": [
      { "data": "id" },
      { "data": "licence_no" },
      { "data": "licenceStatus" },
      { "data": "operationMode" },
      { "data": "auditStatus" },
      { "data": "comName" },
      { "data": "licence_date" },
      { "data": "validityPeriod" }
    ],
    "columnDefs": [
      {
        "render": function(data, type, row) {
          return '<label class="checkbox-inline c-checkbox"><input type="checkbox" class="checkCalling" name="id" value="'+ data +'">'
              +'<span class="fa fa-check"></span></label>';
        },
        "orderable": false,
        "targets": 0
      }],
    "ordering": false,
    "pagingType": "simple",
    "language": language,
    "dom": 'rt<"bottom"lip >'
  });

  // Apply the search
  table.columns().eq( 0 ).each( function ( colIdx ) {
    $( 'input,select', table.column( colIdx ).footer() ).on( 'keyup change propertychange input', function () {
      clearTimeout(searchTimeOut);
      idx = colIdx;
      val = this.value;
      searchTimeOut = setTimeout("search2()", 1000);
    } );
  } );

  function initTableCheckbox() {
      /*“全选/反选”复选框*/
    var $checkAll = $('#checkAll');
    $checkAll.click(function(event){
        /*将所有行的选中状态设成全选框的选中状态*/
      $('.checkCalling').prop('checked',$(this).prop('checked'));
    });

      /*点击每一行的选中复选框时*/
    $('.checkCalling').click(function(event){
      $checkAll.prop('checked',$('table tbody tr').find('input:checked').length == $('.checkCalling').length ? true : false);
    });
  }

  initTableCheckbox();
}

// 提交许可证进行审核
function submitAudit(t){
    var $checkItem = $("input[name='id']:checked");
    if ($checkItem.length == 0) {
        $.notify("当前没有选择许可证，请选择需要提交审核的许可证",{"status":"<%=Constant.STATUS_INFO%>"});
    } else if($checkItem.length >= 2 ){
        $.notify("只能选择一个许可证进行提交审核",{"status":"<%=Constant.STATUS_INFO%>"});
    } else {
        swal({  title: "确认提交审核",   
            text: "请确认是否提交审核",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "提交审核",   
            cancelButtonText: "取消",   
            closeOnConfirm: false,   
            closeOnCancel: true 
        }, 
        function(isConfirm){  
            if(isConfirm){
                $.page.ajax($.page.getAjaxSettings({
                    async: false,
                    type: "POST",
                    dataType: "json",
                    url: "/licence/submitAudit.htm",
                    data: $checkItem.serialize(),
                    success: function (result, textStatus, jqXHR) {
                        if (result.data.auditStatusCode == "<%=CodeTypeConstant.LIC_AUDIT_CREATE%>") {
                            swal({
                                title: "提交审核成功，系统正在审核，请等待系统通知。",   
                                type: "success",
                                confirmButtonColor: "#3399FF",   
                                confirmButtonText: "确定",   
                                closeOnConfirm: false,   
                                closeOnCancel: false 
                            });
                            table.column( 0 ).search( Math.random() ).draw();
                        } else {
                            swal({
                                title: "该许可证非企业创建状态,无法提交审核",   
                                type: "warning",   
                                confirmButtonColor: "#3399FF",   
                                confirmButtonText: "确定",   
                                closeOnConfirm: false,   
                                closeOnCancel: false 
                            });
                        }
                    }
                }));
            }
        });
    }
}

// 编辑许可证
function editLicence(t){
    var $checkItem = $("input[name='id']:checked");
    if ($checkItem.length == 0) {
        $.notify("当前没有选择许可证，请选择需要编辑的许可证",{"status":"<%=Constant.STATUS_INFO%>"});
    } else if($checkItem.length >= 2 ){
        $.notify("只能选择一个许可证进行编辑",{"status":"<%=Constant.STATUS_INFO%>"});
    } else {
        // 判断当前需要编辑的许可证的审核状态
        if(($checkItem.parent().parent().parent().find('td').eq(4).html()=== '审核退回') || ($checkItem.parent().parent().parent().find('td').eq(4).html()=== '企业创建') ){
        	$.page.ajax($.page.getAjaxSettings({
                async: false,
                type: "POST",
                dataType: "json",
                url: "/licence/getAuditStatus.htm?etc=" + new Date().getTime(),
                data: $checkItem.serialize(),
                success: function (result, textStatus, jqXHR) {
                    if (result.data.auditStatusCode == "<%=CodeTypeConstant.LIC_AUDIT_PASS%>") {
                        $.notify("[审核通过]的许可证不能编辑",{"status":"<%=Constant.STATUS_INFO%>"}); 
                    } else {
                        var paramObj = {};
                        paramObj.licenceId = $checkItem.val();
                        paramObj.enterpriseId = "${enterpriseId}";
                        var pageUrl = "/licence/licenceSteps.htm";
                        $.page.gotoTargetLocation(pageUrl, paramObj);
                    }
                },complete: function (jqXHR, textStatus) {
                    var result = jqXHR.responseJSON;
                    if (result.status != 1) {
                        
                    }
                }
            }));
        }else{
        	$.notify("只有[审核退回]和[企业创建]状态的许可证可编辑",{"status":"info"});

        }
        
    }
}

function deleteLicence(t){
    var $checkItem = $("input[name='id']:checked");
    if ($checkItem.length == 0) {
        $.notify("请选择一条或多条进行删除", {"status": "info"});
    } else {
        swal({  title: "确认删除",   
            text: "请确认是否删除数据",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "删除",   
            cancelButtonText: "取消",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        }, function(isConfirm){
            if(isConfirm){
                $.page.ajax($.page.getAjaxSettings({
                    async: false,
                    type: "POST",
                    dataType: "json",
                    url: "/licence/deleteLicences.htm",
                    data: $checkItem.serialize(),
                    success: function (result, textStatus, jqXHR) {
                        if(result.data.isSuccess){
                            swal({  
                                title: "删除成功",   
                                type: "success",   
                                confirmButtonColor: "#3399FF",   
                                confirmButtonText: "确定",   
                                closeOnConfirm: false,   
                                closeOnCancel: false 
                            });
                            table.column( 0 ).search( Math.random() ).draw();
                        } else {
                            swal({  
                                title: "不可删除状态为待审核、审核通过、终止的许可证",   
                                type: "warning",   
                                confirmButtonColor: "#3399FF",   
                                confirmButtonText: "确定",   
                                closeOnConfirm: false,   
                                closeOnCancel: false 
                            }); 
                        }
                    }
                }));
            } else {
                swal("操作已取消!", "操作已取消.", "success");
            }
        });
    }
}

function viewLicence(t){
    var $checkItem = $("input[name='id']:checked");
    if ($checkItem.length != 1) {
        $.notify("请选择一个进行查看许可证详细信息",{"status":"<%=Constant.STATUS_INFO%>"});
    } else {
		var paramObj = {};
		paramObj.id = $checkItem.val();
		var pageUrl = "/licence/commView.htm";
		$.page.gotoTargetLocation(pageUrl, paramObj);
    }
}
</script>