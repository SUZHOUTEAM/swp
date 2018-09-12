/**
 * Created by wudang on 2017/8/2.
 *
 *
 */
var activity={
    ticketId:'',current:1,size:10,appPath:'',
    statusArr:{'0':'待发布','1':'发布','2':'结束','4':'暂停','5':'终止'},
    payStatusArr:{'NONEPAYMENT':'未付款','FREE':'免费','PAYMENT':'已付款'},
    bindEvent:function(){
      $('#userList_previous a').on('click',function () {
          if($(this).parent().hasClass('disabled')){
              return;
          }
          activity.current-=1;
          activity.initValue();
      });
      $('#userList_next a').on('click',function () {
          if($(this).parent().hasClass('disabled')){
              return;
          }
          activity.current+=1;
          activity.initValue();
      });
     $('select[name=userList_length]').on('change',function(){
         // console.log($(this).val());
         var size=$(this).val();
         activity.current=1;
         activity.size=size;
         activity.initValue();
     });
     $('input[name=checkAll]').on('change',function(){
        var checked=$(this)[0].checked;
        $('input[name=userId]').prop("checked", checked);
     });
    },
    updateTohome:function () {
        swal({title: "提示",
                text: "此操作将更新最新活动信息到首页",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确认",
                cancelButtonText: "取消",
                closeOnConfirm: true,
                closeOnCancel: true
            },
            function(isConfirm){
                if(isConfirm){
                    $.ajax({
                        url:activity.appPath+'/wasteActivity/updateToHome',
                        type:'POST',
                        dataType: "json",
                        success:function (data) {
                            if(data.status==1){
                                $.notify("更新成功", { status: 'success',timeout:1500 });
                            }
                        }
                    });
                };
            }
        );
    },
    edit:function () {
        var checkedList=$('input[name=userId]:checked');
        var arr=[];
        checkedList.each(function (item,index) {
            arr.push($(this).val());
        });
        if(arr.length==0||arr.length>1){
            swal({
                title: "请选择一条进行编辑",
                type: "info",
                confirmButtonColor: "#3399FF",
                confirmButtonText: "确定",
                closeOnConfirm: false,
                closeOnCancel: false
            });
            return;
        }
        var curStatus = $('#'+arr[0]+'-status').text();
        if(curStatus == activity.statusArr['0']||curStatus == activity.statusArr['4']||curStatus == activity.statusArr['2']){
            location=activity.appPath+'/wasteActivity/add.htm?ticketId='+activity.ticketId+'&activityId='+arr[0];
        }else{
            swal({
                title: "只有待发布或暂停的活动才可以编辑",
                type: "info",
                confirmButtonColor: "#3399FF",
                confirmButtonText: "确定",
                closeOnConfirm: false,
                closeOnCancel: false
            });
        }
    },
    show:function () {
        var checkedList=$('input[name=userId]:checked');
        var arr=[];
        checkedList.each(function (item,index) {
            arr.push($(this).val());
        });
        if(arr.length==0||arr.length>1){
            swal({
                title: "请选择一条进行查看",
                type: "info",
                confirmButtonColor: "#3399FF",
                confirmButtonText: "确定",
                closeOnConfirm: false,
                closeOnCancel: false
            });
            return;
        }
        location=activity.appPath+'/wasteActivity/show.htm?ticketId='+activity.ticketId+'&activityId='+arr[0];
    },
    publish:function () {
        var checkedList=$('input[name=userId]:checked');
        var arr=[];
        var otherStatus=[];
        checkedList.each(function (item,index) {
            var activityid = $(this).val();
            var curStatus = $('#'+activityid+'-status').text();
            if(curStatus == activity.statusArr['0'] || curStatus == activity.statusArr['4']){
                arr.push(activityid);
            }else{
                otherStatus.push(activityid);
            }
        });
        if(otherStatus.length>0) {
            swal({
                title: "请选择【待发布|暂停】状态的活动进行发布！",
                type: "warning",
                confirmButtonColor: "#3399FF",
                confirmButtonText: "确定",
                closeOnConfirm: false,
                closeOnCancel: false
            });
            return
        }
        if(arr.length==0){
            swal({
                title: "请选择一条进行发布",
                type: "info",
                confirmButtonColor: "#3399FF",
                confirmButtonText: "确定",
                closeOnConfirm: false,
                closeOnCancel: false
            });
            return;
        }else{

            swal({title: "发布活动",
                    text: "请确认是否发布该活动",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确认",
                    cancelButtonText: "取消",
                    closeOnConfirm: true,
                    closeOnCancel: true
                },
                function(isConfirm){
                    if(isConfirm){
                        $.ajax({
                            url:activity.appPath+'/wasteActivity/publish.htm',
                            type:'POST',
                            dataType: "json",
                            data:{
                                ticketId:activity.ticketId,
                                ids:arr.toString()
                            },
                            success:function (data) {
                                if(data.status==1){
                                    $.notify("发布活动成功", { status: 'success',timeout:1500 });
                                    activity.current=1;
                                    activity.initValue();
                                }
                            }
                        });
                    };
                }
            );
        }

    },
    delete:function(){
        var checkedList=$('input[name=userId]:checked');
        var arr=[];
        checkedList.each(function (item,index) {
            arr.push($(this).val());
        });
        if(arr.length==0){
            swal({
                title: "请选择一条进行删除",
                type: "info",
                confirmButtonColor: "#3399FF",
                confirmButtonText: "确定",
                closeOnConfirm: false,
                closeOnCancel: false
            });
            return;
        }
        swal({title: "删除活动",
                text: "请确认是否删除该活动",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确认",
                cancelButtonText: "取消",
                closeOnConfirm: true,
                closeOnCancel: true
            },
            function(isConfirm){
                if(isConfirm){
                    $.ajax({
                        url:activity.appPath+'/wasteActivity/delete.htm',
                        type:'POST',
                        dataType: "json",
                        data:{
                            ticketId:activity.ticketId,
                            ids:arr.toString()
                        },
                        success:function (data) {
                            if(data.status==1){
                                $.notify("删除活动成功", { status: 'success',timeout:1500 });
                                activity.current=1;
                                activity.initValue();
                            }
                        }
                    });
                };
            });

    },
    pause:function(){
        var checkedList=$('input[name=userId]:checked');
        var arr=[];
        var otherStatus=[];
        checkedList.each(function (item,index) {
            var activityid = $(this).val();
            var curStatus = $('#'+activityid+'-status').text();
            if(curStatus == activity.statusArr['1']){
                arr.push(activityid);
            }else{
                otherStatus.push(activityid);
            }
        });
        if(otherStatus.length>0) {
            swal({
                title: "请选择【发布】状态的活动进行发布！",
                type: "warning",
                confirmButtonColor: "#3399FF",
                confirmButtonText: "确定",
                closeOnConfirm: false,
                closeOnCancel: false
            });
            return

        }
        if(arr.length==0){
            swal({
                title: "请选择一条暂停活动",
                type: "info",
                confirmButtonColor: "#3399FF",
                confirmButtonText: "确定",
                closeOnConfirm: false,
                closeOnCancel: false
            });
            return;
        }
        swal({title: "暂停活动",
                text: "请确认是否暂停该活动",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确认",
                cancelButtonText: "取消",
                closeOnConfirm: true,
                closeOnCancel: true
            },
            function(isConfirm){
                if(isConfirm){
                    $.ajax({
                        url:activity.appPath+'/wasteActivity/stopActivity.htm',
                        type:'POST',
                        dataType: "json",
                        data:{
                            ticketId:activity.ticketId,
                            ids:arr.toString(),
                            status:4
                        },
                        success:function (data) {
                            if(data.status==1){
                                $.notify("暂停活动成功", { status: 'success',timeout:1500 });
                                activity.current=1;
                                activity.initValue();
                            }
                        }
                    });
                };
            });

    },
    initValue:function(){
        $.ajax({
            url:activity.appPath+'/wasteActivity/listActivity.htm',
            type:'POST',
            dataType: "json",
            data:{
                ticketId:activity.ticketId,
                current:activity.current,
                size:activity.size
            },
            success:function (data) {
                if(data.status==1&&data.data.list){
                    var data=data.data.list;
                    var records=data.records;
                    var str='';
                    for(var i in records){
                        var obj=records[i];
                        str+='<tr role="row" class="odd">'+
                                '<td>' +
                                    '<label class="checkbox-inline c-checkbox">'+
                                    '<input type="checkbox" class="checkEnt" name="userId" value="'+obj.id+'"><span class="fa fa-check"></span></label>' +
                                '</td>'+
                                '<td>'+obj.activityName+'</td>'+
                                '<td>'+(obj.startDate||'--')+'</td>'+
                                '<td>'+(obj.endDate||'--')+'</td>'+
                                '<td id="'+ obj.id +'-payStatus"><span class="status status-'+obj.payStatus+'">'+(obj.payStatus?activity.payStatusArr[obj.payStatus]:'免费')+'</span></td>'+
                                '<td id="'+ obj.id +'-status"><span class="activity-status activity-status-'+obj.status+'">'+activity.statusArr[obj.status]+'</span></td>'+
                                '<td>'+obj.createTime+'</td>'+
                            '</tr>';
                    }
                    $('table#userList tbody').html(str);
                    activity.parsePage(data);
                }
            }
        })
    },
    parsePage:function (data) {
        var current=data.current;
        var size=data.size;
        var total=data.total;
        var pages=data.pages;
        var endCount=current*size;
        if(pages==1){
            endCount=data.records.length;
        }else if(current==pages){
            endCount=total;
        }
        $('#userList_info').html('当前显示第 '+((current-1)*size+1)+' 至 '+endCount+' 项，共 '+total+' 项。');
        if(current>1){
            $('#userList_previous').removeClass('disabled');
        }else{
            $('#userList_previous').addClass('disabled');
        }
        if(current<pages){
            $('#userList_next').removeClass('disabled');
        }else{
            $('#userList_next').addClass('disabled');
        }
    },
    init:function(){
        this.ticketId=$('#ticketId').val();
        this.appPath=$('#appPath').val();
        this.initValue();
        this.bindEvent();
    }
}
$(document).ready(function(){
    activity.init();
})