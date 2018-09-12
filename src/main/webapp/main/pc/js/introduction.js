/**
 * Created by yinxl on 2018/1/23.
 */
var vue = new Vue({
    el: '#app',
    data:{
        entType:getParam('entType'),
        contractShowAll:false,
        anchorPoint:'cfBanner',
        btnShow:true
    },
    created:function () {
        // if(localStorage.ticketId&&localStorage.entType!='PRODUCTION'){
        //     this.btnShow=false;
        // }
    },
    /*mounted:function () {
        $(window).scroll(function () {
            var scrollTop=$(this).scrollTop();
            console.log(scrollTop);
            if(scrollTop<200&&vue.anchorPoint!='cfBanner'){
                vue.anchorPoint='cfBanner';
            }else if(scrollTop<800&&vue.anchorPoint!='cfContract'){
                vue.anchorPoint='cfContract';
            }else if(vue.anchorPoint!='loginAndRegister'){
                vue.anchorPoint='loginAndRegister';
            }
        });
    },*/
    methods:{
        goInquiry:function () {
            collectingUserBehavior(index.ticketId,'DIS_PRO_INQUIRY');
            var ticketId=localStorage.ticketId;
          if(ticketId){
              window.location='/swp/entRelease/entWasteList.htm?ticketId='+ticketId;
          }else{
              if(getParam('register')){
                  window.location='/swp/register.jsp';
              }else{
                  window.location='/swp/login.jsp';
              }
          }
        },
        changeAnchorPoint:function (id) {
            this.anchorPoint=id;
            document.getElementById(id).scrollIntoView();
            // window.location='#'+id;
        },
        showAllContract:function () {
            this.contractShowAll=true;
        },
        shortAllContract:function () {
            this.contractShowAll=false;
        },
        goEntList:function () {
            window.location.href = "main/pc/view/company.html" ;
        },
        goActivityList:function () {
            window.location.href = "main/pc/view/activityList.html"
        },
        goClassList:function () {
            window.location.href = "main/pc/view/information.html"
        },
        downloadContract:function () {
            collectingUserBehavior(index.isLogin?index.ticketId:'', 'DOWNLOADCONTRACT');
            window.location='./main/pc/file/易废网危废委托处置协议V1.0.docx';
        },
        downloadJiameng:function () {
            collectingUserBehavior(index.isLogin?index.ticketId:'', 'FRANCHISECONTRACT');
            window.location='./main/pc/file/易废网代理处置企业危废业务合作协议.doc';
        }
    }
});
var yfw = {
    onlineTalk:function () {
        var ticketIdStr=index.ticketId ? ("?ticketId=" + index.ticketId) : "";
        if(index.isLogin){
            window.location='main/pc/view/online.html'+ticketIdStr;
            return;
        }
        $.ajax({
            url:'yunxin/getEnableYunXinAccount',
            type:'post',
            success:function (result) {
                console.log(result);
                if(result.data){
                    setCookie('uid',result.data.accId);
                    setCookie('sdktoken',result.data.token);
                    setCookie('nickName',escape('游客'));
                    window.location='main/pc/view/online.html'+ticketIdStr;
                }else{
                    vue.$message({
                        duration:5000,
                        dangerouslyUseHTMLString: true,
                        message: '<div style="margin-bottom: 10px;color: #1171d1;font-size: 16px">小易正忙,您可以稍后再试</div><div>先注册再咨询会更快哦</div>'
                    });
                }
            }
        });
    },
    goSuggest:function () {
        window.location='main/pc/view/suggest.html'+(yfw.ticketId ? ("?ticketId=" + yfw.ticketId) : "");
    },
}