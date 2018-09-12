//
//Pipelining function for DataTables. To be used to the `ajax` option of DataTables
//
$.fn.dataTable.pipeline = function ( opts ) {
 // Configuration options
 var conf = $.extend( {
     pages: 5,     // number of pages to cache
     url: '',      // script url
     data: null,   // function or object with parameters to send to the server
                   // matching how `ajax.data` works in DataTables
     method: 'GET' // Ajax HTTP method
 }, opts );

 // Private variables for storing the cache
 var cacheLower = -1;
 var cacheUpper = null;
 var cacheLastRequest = null;
 var cacheLastJson = null;

 return function ( request, drawCallback, settings ) {
     var ajax          = false;
     var requestStart  = request.start;
     var drawStart     = request.start;
     var requestLength = request.length;
     var requestEnd    = requestStart + requestLength;
      
     if ( settings.clearCache ) {
         // API requested that the cache be cleared
         ajax = true;
         settings.clearCache = false;
     }
     else if ( cacheLower < 0 || requestStart < cacheLower || requestEnd > cacheUpper ) {
         // outside cached data - need to make a request
         ajax = true;
     }
     else if ( JSON.stringify( request.order )   !== JSON.stringify( cacheLastRequest.order ) ||
               JSON.stringify( request.columns ) !== JSON.stringify( cacheLastRequest.columns ) ||
               JSON.stringify( request.search )  !== JSON.stringify( cacheLastRequest.search )
     ) {
         // properties changed (ordering, columns, searching)
         ajax = true;
     }
      
     // Store the request for checking next time around
     cacheLastRequest = $.extend( true, {}, request );

     if ( ajax ) {
         // Need data from the server
         if ( requestStart < cacheLower ) {
             requestStart = requestStart - (requestLength*(conf.pages-1));

             if ( requestStart < 0 ) {
                 requestStart = 0;
             }
         }
          
         cacheLower = requestStart;
         cacheUpper = requestStart + (requestLength * conf.pages);

         request.start = requestStart;
         request.length = requestLength*conf.pages;

         // Provide the same `data` options as DataTables.
         if ( $.isFunction ( conf.data ) ) {
             // As a function it is executed with the data object as an arg
             // for manipulation. If an object is returned, it is used as the
             // data object to submit
             var d = conf.data( request );
             if ( d ) {
                 $.extend( request, d );
             }
         }
         else if ( $.isPlainObject( conf.data ) ) {
             // As an object, the data given extends the default
             $.extend( request, conf.data );
         }

         settings.jqXHR = $.ajax( {
             "type":     conf.method,
             "url":      conf.url,
             "data":     request,
             "dataType": "json",
             "cache":    false,
             "success":  function ( json ) {
                 cacheLastJson = $.extend(true, {}, json);

                 if ( cacheLower != drawStart ) {
                     json.data.splice( 0, drawStart-cacheLower );
                 }
                 if ( requestLength >= -1 ) {
                     json.data.splice( requestLength, json.data.length );
                 }
                  
                 drawCallback( json );
             }
         } );
     }
     else {
         json = $.extend( true, {}, cacheLastJson );
         json.draw = request.draw; // Update the echo for each response
         json.data.splice( 0, requestStart-cacheLower );
         json.data.splice( requestLength, json.data.length );

         drawCallback(json);
     }
 }
};

//Register an API method that will empty the pipelined data, forcing an Ajax
//fetch on the next draw (i.e. `table.clearPipeline().draw()`)
$.fn.dataTable.Api.register( 'clearPipeline()', function () {
 return this.iterator( 'table', function ( settings ) {
     settings.clearCache = true;
 } );
} );

var language2 = {
        "lengthMenu": "每页 _MENU_ 条记录",
        "zeroRecords": "没有找到记录",
        "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
        "infoEmpty": "无记录",
        "infoFiltered": "(从 _MAX_ 条记录过滤)",
        "paginate": {
        	"first": "第一页",
            "last": "最后一页",
            "previous": "上一页",
            "next": "下一页"
            	
          }
    };

var language  = {
		"processing":   "载入中...",
        "sProcessing":   "处理中...",
        "sLengthMenu":   "每页 _MENU_ 项",
        "sZeroRecords":  "没有匹配结果",
        "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
        "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix":  "",
        "sSearch":       "搜索:",
        "sUrl":          "",
        "sEmptyTable":     "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands":  ",",
        "oPaginate": {
            "sFirst":    "首页",
            "sPrevious": "上页",
            "sNext":     "下页",
            "sLast":     "末页",
            "sJump":     "跳转"
        },
        "oAria": {
            "sSortAscending":  ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    };