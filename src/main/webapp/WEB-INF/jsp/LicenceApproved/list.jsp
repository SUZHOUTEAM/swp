<%@page import="com.mlsc.waste.utils.Constant"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<%------- 导入页头信息 -------%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="许可证审核"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#approveLicence"/>
</jsp:include>
<%------- 结束导入左侧信息 -------%>

<!-- Main section-->
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">权限管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">许可证审核</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <!-- Page content-->
    <div>
        <div class="row">
            <div class="col-md-12">
                <div class="tool-div">
                    <div class="btn-group" role="group">
                        <a class="btn btn-default" href="#" role="button" id="view" onclick="view(this)">查看</a>
                        <a class="btn btn-default" href="#" role="button" id="confirmPass" onclick="confirmPass(this)">审核通过</a>
                        <a class="btn btn-default" href="#" role="button" id="confirmReject" onclick="confirmReject(this)">审核退回</a>
                        <a class="btn btn-default" href="#" role="button" id="termination" onclick="termination(this)">终止</a>
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
                                <th>企业名称</th>
                                <th>许可证编号</th>
                                <th>核准经营方式</th>
                                <th>审批状态</th>
                                <th>发证机关</th>
                                <th>有效期</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                        <tfoot class='filter'>
                            <tr>
                                <th></th>
                                <th>企业名称</th>
                                <th>许可证编号</th>
                                <th>核准经营方式</th>
                                <th>审批状态</th>
                                <th></th>
                                <th></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>
<%------- 导入底部信息 -------%>
<jsp:include page="/common/bottom.jsp" flush="true"/>
<script src="<%=appPath %>/thirdparty/parsleyjs/dist/parsley.min.js"></script>
<%--<script src="<%=appPath %>/thirdparty/sweetalert/dist/sweetalert.min.js"></script>--%>
<!-- notify-->
<script src="<%=appPath %>/main/common/main.js"></script>
<%------- 结束导入底部信息 -------%>

<script type="text/javascript">
var table ;
var searchTimeOut;//timeout对象
var idx;//过虑的列序号
var val;//过虑值
function search2() {
    table.column( idx ).search( val ).draw();
}

$(document).ready( function () {
    $('#example tfoot th').each( function () {
        if($(this).index() == 3){
            var select = $('<select class="form-control input-sm datatable_input_col_search"><option value=""></option></select>').appendTo( $(this).empty() );
            <c:forEach var="it" items="${operationModeList}" varStatus="status">
                select.append('<option value="${it.id}">${it.value}</option>');
            </c:forEach>
        }else if($(this).index() == 4){
            var select = $('<select class="form-control input-sm datatable_input_col_search"></select>').appendTo( $(this).empty() );
            <c:forEach var="it" items="${auditStatusList}" varStatus="status" begin="0" step="1">
                <c:if test="${it.code eq 'SUBMIT'}">
                    select.append('<option selected="selected" value="${it.id}">${it.value}</option>');
                </c:if>
                <c:if test="${it.code ne 'SUBMIT'}">
                    select.append('<option value="${it.id}">${it.value}</option>');
                </c:if>
            </c:forEach>
        }else{
            var title = $('#example tfoot th').eq( $(this).index() ).text();
            if(title!=''){
                $(this).html( '<input type="text" placeholder="搜索 '+title+'" class="form-control input-sm datatable_input_col_search"/>' );
            }
        }
    });
    
   // DataTable
    table = $('#example').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": $.fn.dataTable.pipeline( {
            url: 'listForJson.htm?ticketId=<%=ticketId %>',
            pages: 2 // number of pages to cache
        } ),
        "columns": [
                    { "data": "id" },
                    { "data": "enterpriseName" },
                    { "data": "licence_no" },
                    { "data": "operationMode" },
                    { "data": "auditStatus" },
                    { "data": "licence_org" },
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
    
    function initTableCheckbox(){
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
} );

//查看经营许可证
function view(that){
    var $checkItem = $("input[name='id']:checked");
    if ($checkItem.length != 1) {
        $.notify("请选择一个进行查看许可证详细信息",{"status":"<%=Constant.STATUS_INFO%>"});
    } else {
        var paramObj = {};
        paramObj.id = $checkItem.val();
        var pageUrl = "/licenceApproved/view.htm";
        $.page.gotoTargetLocation(pageUrl, paramObj);
    }
}

//审核通过经营许可证
function confirmPass(t){
    var $checkItem = $("input[name='id']:checked");
    if($checkItem.length == 0){
        $.notify("请选择一条或多条进行审核", {"status": "info"});
    }else{
    	if($checkItem.parent().parent().parent().find('td').eq(4).html()=== '待审核'){
	        swal({  title: "确认通过",   
	            text: "请确认是否通过",   
	            type: "warning",   
	            showCancelButton: true,   
	            confirmButtonColor: "#DD6B55",   
	            confirmButtonText: "确认",   
	            cancelButtonText: "取消",   
	            closeOnConfirm: false,   
	            closeOnCancel: true 
	        }, 
	        function(isConfirm){  
	            if(isConfirm){
                    swal( "审核通过","", "success");
	                $.page.ajax($.page.getAjaxSettings({
	                    async: false,
	                    type: "POST",
	                    dataType: "json",
	                    url: "/licenceApproved/confirmPass.htm",
	                    data: $checkItem.serialize(),
	                    success: function (result, textStatus, jqXHR) {
                            swal( "审核通过","", "success");
	                        table.column( 0 ).search(Math.random()).draw();
	                    }
	                }));
	            }  
	        });
	    }else{
	    	$.notify("只有[待审核]的许可证可以审核通过",{"status":"info"});
	    }
    }
}

//审核退回经营许可证
function confirmReject(t){
    var $checkItem = $("input[name='id']:checked");
    if($checkItem.length == 0){
        $.notify("请选择一条或多条进行审核", {"status": "info"});
    }else{
        var statusText=$checkItem.parent().parent().parent().find('td').eq(4).html();
    	if(statusText== '待审核'||statusText== '审核通过'){
    		swal({  title: "确认退回",   
                text: "请确认是否退回",   
                type: "warning",   
                showCancelButton: true,   
                confirmButtonColor: "#DD6B55",   
                confirmButtonText: "确认",   
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
                           url: "/licenceApproved/confirmReject.htm",
                           data: $checkItem.serialize(),
                           success: function (result, textStatus, jqXHR) {
                               swal({  
                                   title: "审核退回成功",   
                                   type: "success",   
                                   confirmButtonColor: "#3399FF",   
                                   confirmButtonText: "确定",   
                                   closeOnConfirm: false,   
                                   closeOnCancel: false 
                               });
                               table.column( 0 ).search(Math.random()).draw();
                           }
                       }));
                	
                }  
            });
    	}else{
   		 $.notify("已经审核退回的许可证不可以再次审核退回",{"status":"info"});
    	}
        
    }
}
function termination(t){
	 var $checkItem = $("input[name='id']:checked");
    if($checkItem.length == 0){
        $.notify("请选择一条或多条进行终止", {"status": "info"});
    }else{
    	if($checkItem.parent().parent().parent().find('td').eq(4).html()=== '审核通过'){
    		swal({  title: "确认终止",   
                text: "请确认是否终止",   
                type: "warning",   
                showCancelButton: true,   
                confirmButtonColor: "#DD6B55",   
                confirmButtonText: "确认",   
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
                           url: "/licenceApproved/termination.htm",
                           data: $checkItem.serialize(),
                           success: function (result, textStatus, jqXHR) {
                               swal({  
                                   title: "终止成功",   
                                   type: "success",   
                                   confirmButtonColor: "#3399FF",   
                                   confirmButtonText: "确定",   
                                   closeOnConfirm: false,   
                                   closeOnCancel: false 
                               });
                               table.column( 0 ).search(Math.random()).draw();
                           }
                       }));
                	
                }  
            });
    	}else{
   		 $.notify("只有[审核通过]的许可证可以终止",{"status":"info"});
    	}
    }
}
</script>