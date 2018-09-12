/**
 * Created by wudang on 2018/3/22.
 */
var vue = new Vue({
    el: '#app',
    data: {
        keyword:'',
        images: [
            '../../../pc/img/indexBanner.jpg',
            '../../../pc/img/zhaomu.jpg',
            '../../../pc/img/chengnuo.jpg'
        ],
        list: [],
        loading: false,
        finished: false,
        pageIndex:1,
        pageSize:2
    },
    created: function () {
        this.getList();
    },
    mounted: function () {

    },
    methods: {
        onSearch:function () {
            window.location="search.html?type=DISPOSITION&key="+this.keyword;
        },
        onLoad:function() {
            vue.loading = true;
            setTimeout(function(){
                for (var i = 0; i < 10; i++) {
                    vue.list.push(vue.list.length + 1);
                }
                vue.loading = false;
                if (vue.list.length >= 40) {
                    vue.finished = true;
                }
            }, 500);
        },
        getList:function () {
            $.ajax({
                url:'/swp/enterpriseConfiguration/listEnterpriseInfoBySection',
                data:JSON.stringify({cantonCode:'32',page:this.pageIndex,section:"DISPOSITION"}),
                contentType:'application/json',
                type: "POST",
                dataType: 'json',
                async: true,
                success:function (result) {
                    if(result.status==1){
                        for(var i in result.data.enterpriseModelList){
                            var obj=result.data.enterpriseModelList[i];
                            if(obj['fileId']){
                                var imgUrl=IMG_VIEW_URL+'&fileID='+obj['fileId'];
                                obj['logo']=imgUrl;
                            }else{
                                obj['logo']='main/pc/img/ent_default.jpg';
                            }
                            vue.list.push(obj);
                        }
                    }
                }
            })
        }
    }
});
