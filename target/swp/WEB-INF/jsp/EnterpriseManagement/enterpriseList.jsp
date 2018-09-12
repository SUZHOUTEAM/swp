<%@page import="com.mlsc.waste.utils.CodeTypeConstant"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
    String appPath=request.getContextPath();
    String ticketId = (String)request.getAttribute("ticketId");
%>
<jsp:include page="/common/top.jsp" flush="true">
  <jsp:param name="title" value="企业管理"/>
</jsp:include>
<jsp:include page="/common/left.jsp" flush="true">
  <jsp:param name="menuId" value="#enterpriseManagement"/>
</jsp:include>
<style type="text/css">
    .tooltip.top .tooltip-arrow{
        border-top-color: #000000;
    }
    .dialog{
        position: fixed;
        left: 0;
        right: 0;
        top:0;
        bottom: 0;
        z-index: 10000;
        background: rgba(0,0,0,.6);
        display: none;
    }
    .dialog-container{
        width: 80%;
        background-color: #ffffff;
        margin: 100px auto 0;
        border-radius: 2px;
        padding: 15px 20px;
        position: relative;
    }
    .dialog-close{
        position: absolute;
        right: 12px;
        top:10px;
    }
    .dialog-title{
        line-height: 24px;
        font-size: 18px;
        color: #303133;
        margin-bottom: 20px;
    }
</style>
<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">权限管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">企业管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div class="dialog">
        <div class="dialog-container">
            <a href="javascript:" onclick="$('.dialog').hide()" class="dialog-close">
                <img src="<%=appPath%>/main/pc/img/close.png"/>
            </a>
            <div class="dialog-title">产废详情</div>
            <div class="dialog-content"></div>
        </div>
    </div>
    <div >
        <div class="row">
            <div class="col-md-12">
                <div class="tool-div">
                    <div class="btn-group" role="group">
                        <a class="btn btn-default" href="#" role="button" onclick="viewEnterprise(this)">查看企业</a>
                        <a class="btn btn-default" href="#" role="button"  onclick="createEnterprise(this)">创建企业</a>
                        <a class="btn btn-default" href="#" role="button" onclick="editEnterprise(this)">编辑企业</a>
                        <a class="btn btn-default" href="#" role="button" onclick="auditPass(this)">审核通过</a>
                        <a class="btn btn-default" href="#" role="button" onclick="auditRefuse(this)">审核退回</a>
                    </div>
                </div>
                <div class="waste-panel table-responsive">
                    <table id="enterpriselist" class="table table-striped table-hover table-condensed">
                        <thead>
                            <tr>
                                <th>
                                    <label class="checkbox-inline c-checkbox">
                                        <input id="checkAll" name="checkAll" type="checkbox">
                                        <span class="fa fa-check"></span>
                                    </label>
                                </th>
                                <th>企业名称</th>
                                <th>企业代码</th>
                                <th>申请日期</th>
                                <th>申请人名称</th>
                                <th>申请手机号</th>
                                <th>企业状态</th>
                                <th>企业类型</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                        <tfoot class='filter'>
                            <tr>
                                <th></th>
                                <th>企业名称</th>
                                <th>企业代码</th>
                                <th></th>
                                <th></th>
                                <th></th>
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
<jsp:include page="/common/bottom.jsp" flush="true"/>

<script type="text/javascript">
var table ;
var searchTimeOut;//timeout对象
var idx;//过虑的列序号
var val;//过虑值
function search2() {
    table.column(idx).search(val).draw();
}
$(document).ready( function () {
    var $filter = $('#enterpriselist tfoot th');
    $filter.each( function () {
	    var title = $filter.eq($(this).index()).text();
	    if(title != ''){
	        $(this).html( '<input type="text" placeholder="搜索 '+ title + '" class="form-control input-sm datatable_input_col_search "/>');
	    }
	    if($(this).index() == 6){
            var select = $('<select class="form-control input-sm datatable_input_col_search"></select>').appendTo( $(this).empty() );
            <c:forEach var="it" items="${auditStatusList}" varStatus="status" begin="0" step="1">
                <c:if test="${it.code eq 'SUBMIT'}">
                    select.append('<option selected="selected" value="${it.id}">${it.value}</option>');
                </c:if>
                <c:if test="${it.code ne 'SUBMIT'}">
                    select.append('<option value="${it.id}">${it.value}</option>');
                </c:if>
            </c:forEach>
        }
    });
    
   // DataTable
    table = $('#enterpriselist').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": $.fn.dataTable.pipeline( {
            url: '<%=appPath%>/enterprisemanagement/listForJson.htm?ticketId=<%=ticketId %>',
            pages: 5
        } ),
        "columns": [
            { "data": "entId" },
            { "data": "entName" },
            { "data": "entCode" },
            { "data": "createTime" },
            { "data": "userName" },
            { "data": "phoneNum" },
            { "data": "enterpriseStatusLabel" },
            { "data": "entTypes" },
            { "data": "entTypes" }
        ],
        "columnDefs": [
            {
                "render": function(data, type, row) {
                    var $label = $("<label class='checkbox-inline c-checkbox'></label>").append("<input type='checkbox' class='checkEnt' name='entId' value='" + data + "'/>");
                    $label.append("<span class='fa fa-check'></span>");
                    return $label.prop("outerHTML");
                },
                "orderable": false,
                "targets": 0
            },
            //企业状态的转换显示
            {
                "render": function(data, type, row) {
                    $span = $("<span class='hidden enterpriseStatus'></span>").text(row.enterpriseStatus);
                    return data + $span.prop("outerHTML");
                },
                "targets": 6 //第几列
            },
            //企业类型的转换显示
            {
                "render": function(data, type, row) {
                    if (data && data.length > 0) {
                        if(data[0].code=='FACILITATOR'||data[0].code=='DIS_FACILITATOR'){
                            return '<span class="tooltip-text" data-toggle="tooltip" title="'+(row.responsibleAreaName||'无')+'">'+data[0].value+'</span>';
                        }else{
                            return data[0].value;
                        }
                    } else {
                        return null;
                    }
                },
                "targets": 7 //第几列
            },
            //操作的转换显示
            {
                "render": function(data, type, row) {
                    var enterpriseTypeCode = (data && data.length > 0) ? data[0].code : null;
	                var $a = $("<a href='javascript:;' onclick='doAction(this,&quot;"+ enterpriseTypeCode + "&quot;)'></a>");
	                if (enterpriseTypeCode == "<%=CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION%>" ) {
	                    return $a.append("<span>查看产废</span>").prop("outerHTML");
	                }else if (enterpriseTypeCode == "<%=CodeTypeConstant.ENTERPRISE_TYPE_DISPOSITION%>" || 
	                          enterpriseTypeCode == "<%=CodeTypeConstant.ENTERPRISE_TYPE_RECYCLING%>") {
	                    return $a.append("<span>许可证管理</span>").prop("outerHTML");
                    } else {
	                    return "";
	                }
                },
                "targets": 8 //第几列
            }
        ],
        "ordering": false,
        "pagingType": "simple",
        "language": language,
        "dom": 'rt<"bottom"lip >',
        fnDrawCallback:function () {
            tooltip();
        }
    });
   
     // Apply the search
    table.columns().eq( 0 ).each( function ( colIdx ) {
        $( 'input,select', table.column(colIdx).footer()).on('keyup change propertychange input', function () {
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
            $('.checkEnt').prop('checked',$(this).prop('checked')); 
        });  

        /*点击每一行的选中复选框时*/  
        $('.checkEnt').click(function(event){
            $checkAll.prop('checked',$('table tbody tr').find('input:checked').length == $('.checkEnt').length ? true : false);  

        }); 
    }  
    initTableCheckbox();
} );
function tooltip() {
    var timer=setInterval(function () {
        if($(".tooltip-text").length>0){
            $(".tooltip-text").tooltip();
            clearInterval(timer);
        }
    },500);
}
//创建企业
function createEnterprise(that){
    location = "<%=appPath%>/enterprise/add.htm?ticketId=<%=ticketId %>";
}

//编辑
function editEnterprise(that){
    if($("[name='entId']:checked").length == 1){
        location = "<%=appPath%>/enterprise/modifyEnterprise.htm?ticketId=<%=ticketId %>&modifyEnterpriseId=" + $("[name='entId']:checked").val();
    } else {
        swal({  
            title: "请选择一条进行编辑",   
            type: "info",   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "确定",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        });
    }
}

//添加产废或者添加许可证
function doAction(that,enterpriseTypeCode){
    var enterpriseId = $(that).parent().parent().find("input[name='entId']").val();
    if (enterpriseTypeCode == "<%=CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION%>" ) {
        $('.dialog .dialog-container .dialog-content').load("<%=appPath%>/entWaste/listView.htm?ticketId=<%=ticketId %>&enterpriseId=" + enterpriseId);
        $('.dialog').show();
        <%--location = "<%=appPath%>/entWaste/listView.htm?ticketId=<%=ticketId %>&enterpriseId=" + enterpriseId;--%>
    }else if (enterpriseTypeCode == "<%=CodeTypeConstant.ENTERPRISE_TYPE_DISPOSITION%>" || 
              enterpriseTypeCode == "<%=CodeTypeConstant.ENTERPRISE_TYPE_RECYCLING%>") {
        location = "<%=appPath%>/licence/detail.htm?ticketId=<%=ticketId %>&enterpriseId=" + enterpriseId;
    } else {
        
    }
}

//判断企业的状态是不是用户/企业事件类型:申请已提交
function doVerification(){
    var result = true;
    $("[name='entId']:checked").each( function (index) {
        var $tr = $(this).parents("tr");
        if ($tr.find(".enterpriseStatus").text() != "<%=CodeTypeConstant.USER_EVENT_STATUS_SUBMIT%>") {
            result = false;
        }
    });
    return result;
}

//查看企业
function viewEnterprise(that){
    if($("[name='entId']:checked").length == 1){
        location="<%=appPath %>/myenterprise/viewEnterpriseDetail.htm?ticketId=<%=ticketId %>&onlyViewFlg=false&etc="+new Date().getTime() + "&enterpriseId=" + $("[name='entId']:checked").val()+'&breadcrumbName=企业管理';
    } else {
        swal({  
            title: "请选择一条进行进行查看",   
            type: "info",   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "确定",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        });
    }
}

function auditPass(that){
    var num =  $("input[type='checkbox']:checked").length;
    if(num > 0){
        if (!doVerification ()) {
            swal({  
                title: "只能选择【待验证】的企业进行审核",   
                type: "info",   
                confirmButtonColor: "#3399FF",   
                confirmButtonText: "确定",   
                closeOnConfirm: false,   
                closeOnCancel: true 
            });
        } else {
	        swal({title: "申请通过",   
	            text: "请确认是否通过该申请",   
	            type: "warning",   
	            showCancelButton: true,   
	            confirmButtonColor: "#DD6B55",   
	            confirmButtonText: "通过",   
	            cancelButtonText: "取消",   
	            closeOnConfirm: false,   
	            closeOnCancel: true 
	        },
	        function(isConfirm){
	            if(isConfirm){
	                $(that).parent().find(".btn").attr("disabled","disabled");
	                var enterpriseId = "";
	                $("input:checkbox:checked").each(function(i){
	                    enterpriseId += $(this).val() + ","; 
	                });
	                $.post('<%=appPath %>/enterprisemanagement/auditEnterprisePass.htm?ticketId=<%=ticketId %>&etc='+new Date().getTime() + "&enterpriseId=" + enterpriseId,function(result){
	                    var obj = $.parseJSON(result);
	                    if(obj.status == 1){
                            swal({
                                title: "审核通过成功",
                                type: "success",
                                confirmButtonColor: "#3399FF",
                                confirmButtonText: "确定",
                                closeOnConfirm: false,
                                closeOnCancel: false
                            });
                            table.column( 6 ).search('5e92ff67491b4c42838a18eac652c61f').draw();
	                        <%--location="<%=appPath %>/enterprisemanagement/list.htm?ticketId=${ticketId}";--%>
	                    }else{
	                        $.notify("系统错误,请联系管理员!","danger");
	                        $(that).parent().find(".btn").removeAttr("disabled");
                        }
                    });
                }
            });

        }
    }else{
        swal({  
            title: "请至少选择一条进行操作",   
            type: "info",   
            confirmButtonColor: "#3399FF",   
            confirmButtonText: "确定",   
            closeOnConfirm: false,   
            closeOnCancel: true 
        });
    }
}
//平台管理员审核企业退回
function auditRefuse(that){
   var num =  $("input[type='checkbox']:checked").length;
   if(num > 0){
       if (!doVerification ()) {
           swal({  
               title: "只能选择【待验证】的企业进行审核",   
               type: "info",   
               confirmButtonColor: "#3399FF",   
               confirmButtonText: "确定",   
               closeOnConfirm: false,   
               closeOnCancel: true 
           });
       } else {
	       swal({  title: "申请退回",   
	           text: "请确认是否退回该申请",   
	           type: "warning",   
	           showCancelButton: true,   
	           confirmButtonColor: "#DD6B55",   
	           confirmButtonText: "退回",   
	           cancelButtonText: "取消",   
	           closeOnConfirm: false,   
	           closeOnCancel: true 
	       }, 
	       function(isConfirm){
	           if(isConfirm){
	              $(that).parent().find(".btn").attr("disabled","disabled");
	              var enterpriseId = "";
	              $("input:checkbox:checked").each(function(i){ 
	                  enterpriseId += $(this).val() + ","; 
	              });
	              $.post('<%=appPath %>/enterprisemanagement/auditEnterpriseRefuse.htm?ticketId=<%=ticketId %>&etc='+new Date().getTime() + "&enterpriseId=" + enterpriseId,function(result){
	                  var obj = $.parseJSON(result);
	                  if(obj.status == 1){
	                      location="<%=appPath %>/enterprisemanagement/list.htm?ticketId=<%=ticketId %>";
	                  }else{
	                      $.notify("系统错误,请联系管理员!","danger");
                      }
                  });

           }
           });
       }
   }else{
       swal({  
           title: "请至少选择一条进行操作",   
           type: "info",   
           confirmButtonColor: "#3399FF",   
           confirmButtonText: "确定",   
           closeOnConfirm: false,   
           closeOnCancel: true 
       });
   }
}
</script>