<%--Created by zhanghj on 2017/7/4--%>
<%@ page import="com.mlsc.waste.utils.UserStatus" %>
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
    <jsp:param name="menuId" value="#userItem"/>
</jsp:include>

<section>
    <div class="el-breadcrumb">
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">权限管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
        <span class="el-breadcrumb__item">
            <span class="el-breadcrumb__item__inner">用户管理</span>
            <span class="el-breadcrumb__separator">/</span>
        </span>
    </div>
    <div >
        <div class="row">
            <div class="col-md-12">
                <div class="tool-div">
                    <div class="btn-group" role="group">
                        <a class="btn btn-default" href="#" role="button" onclick="auditPass(this,'REGULAR')">审核通过</a>
                        <a class="btn btn-default" href="#" role="button" onclick="auditPass(this,'ADMIN')">审核通过并设为管理员</a>
                        <a class="btn btn-default" href="#" role="button" onclick="auditRefuse(this)">审核退回</a>
                    </div>
                </div>
                <div class="waste-panel table-responsive">
                    <table id="userList" class="table table-striped table-hover table-condensed">
                        <thead>
                        <tr>
                            <th>
                                <label class="checkbox-inline c-checkbox">
                                    <input id="checkAll" name="checkAll" type="checkbox">
                                    <span class="fa fa-check"></span>
                                </label>
                            </th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>绑定企业</th>
                            <th>状态</th>
                            <th>角色</th>
                            <th>申请时间</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                        <tfoot class='filter'>
                        <tr>
                            <th></th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th></th>
                            <th>状态</th>
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

  function getUserStatus() {
    var dfd = $.Deferred();
    $.ajax({
      url:'<%=appPath%>/codeType/listCodeValue?ticketId=<%=ticketId %>',
      data:{
        typeCode:"USER_STATUS"
      },
      dataType:"json",
      success:function (resultData) {
        if(resultData.status == 1){
          var list = resultData.data;
          var selectHtml = "<select class='form-control input-sm'>";
          for(var i=0;i<list.length;i++){
            selectHtml += "<option value='"+list[i].code+"'>";
            selectHtml += list[i].value;
            selectHtml +="</option>";
          }
          selectHtml += "</select>";
          dfd.resolve(selectHtml);
        }else{
          dfd.resolve("");
        }
      },
      error:function () {
        dfd.resolve("");
      }
    });
    return dfd.promise();
  }

  $(document).ready( function () {
    getUserStatus().done(function (selectHtml) {
      var $filter = $('#userList tfoot th');
      $filter.each( function () {
        var title = $filter.eq($(this).index()).text();
        if(title != ''){
          if(title == "状态"){
            $(this).html( selectHtml);
            $(this).children("select").val("SUBMIT");
          }else{
            $(this).html( '<input type="text" placeholder="搜索 '+ title + '" class="form-control input-sm datatable_input_col_search "/>');
          }
        }
      });
      initDataTable();
    });

  } );

function initDataTable() {
  // DataTable
  table = $('#userList').DataTable({
    "processing": true,
    "serverSide": true,
    "ajax": $.fn.dataTable.pipeline( {
      url: '<%=appPath%>/userManage/list?ticketId=<%=ticketId %>',
      data :{
        userStatus:"SUBMIT"
      },
      pages: 5
    } ),
    "columns": [
      { "data": "userId" },
      { "data": "userName" },
      { "data": "phoneNo" },
      { "data": "enterpriseName" },
      { "data": "userStatus","search":{"value":"SUBMIT"} },
      { "data": "userRoleName"},
      { "data": "applyTime"}
    ],
    "columnDefs": [
      {
        "render": function(data, type, row) {
          var $label = $("<label class='checkbox-inline c-checkbox'></label>").append("<input type='checkbox' class='checkEnt' name='userId' value='" + data + "'/>");
          $label.append("<span class='fa fa-check'></span>");
          return $label.prop("outerHTML");
        },
        "orderable": false,
        "targets": 0
      }, //企业状态的转换显示
      {
        "render": function(data, type, row) {
          if(data==null){
            return "";
          }
          $span = $("<span class='hidden userStatus'></span>").text(row.userStatus);
          return row.userStatusName + $span.prop("outerHTML");
        },
        "targets": 4 //第几列
      },
    ],
    "ordering": false,
    "pagingType": "simple",
    "language": language,
    "dom": 'rt<"bottom"lip >'
  });
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
}
  function auditPass(that,role){
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
                audit(that,"PASS",role);
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
  function audit(that,status,role) {
    $(that).parent().find(".btn").attr("disabled","disabled");
    var userIds = "";
    $("input:checkbox:checked").each(function(i){
      userIds += $(this).val() + ",";
    });
    $.ajax({
      url:'<%=appPath %>/userManage/auditUser?ticketId=<%=ticketId %>&etc='+new Date().getTime(),
      type:"post",
      data:{
        userIds:userIds,
        userStatus:status,
        role:role
      },
      dataType:"json",
      success:function (resultData) {
        if(resultData.status == 1){
            swal({
                title: "审核通过成功",
                type: "success",
                confirmButtonColor: "#3399FF",
                confirmButtonText: "确定",
                closeOnConfirm: false,
                closeOnCancel: false
            });
            table.column(4).search('SUBMIT').draw();
          <%--location="<%=appPath %>/userManage/index.htm?ticketId=${ticketId}";--%>
        }else{
          $.notify(resultData.infoList[0],"danger");
          $(that).parent().find(".btn").removeAttr("disabled");
        }
      }
    });
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
                audit(that,"REJECT","REGULAR");
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
  //判断企业的状态是不是用户/企业事件类型:申请已提交
  function doVerification(){
    var result = true;
    $("[name='userId']:checked").each( function (index) {
      var $tr = $(this).parents("tr");
      if ($tr.find(".userStatus").text() != "<%=UserStatus.SUBMIT.getStatusCode()%>") {
        result = false;
      }
    });
    return result;
  }
</script>