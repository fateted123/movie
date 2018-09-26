var app = new Vue({
    el: '#app',
    data: {
        pageInfo:{},
        orderList:[]
    },//过滤器可以用在两个地方：双花括号插值和 v-bind
    filters:{//日期转换方式1：过滤器  调用的时候 <td>{{stream.createTime | dataFormatFilter('yyyy-MM-dd hh:mm:ss')}}</td>
        dataFormatFilter: function (value, fmt) {
            var getDate = new Date(value);
            var o = {
                'M+': getDate.getMonth() + 1,
                'd+': getDate.getDate(),
                'h+': getDate.getHours(),
                'm+': getDate.getMinutes(),
                's+': getDate.getSeconds(),
                'q+': Math.floor((getDate.getMonth() + 3) / 3),
                'S': getDate.getMilliseconds()
            };
            if (/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (getDate.getFullYear() + '').substr(4 - RegExp.$1.length))
            }
            for (var k in o) {
                if (new RegExp('(' + k + ')').test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
                }
            }
            return fmt;
        }
    },
    methods:{//转换方式2,调用的时候dataFormat(stream.createTime,'yyyy-MM-dd hh:mm:ss')
        dataFormat: function (time, fmt) { //fmt为自己要指定的日期格式
            // var fmt = 'yyyy-MM-dd hh:mm:ss';
            var getDate = new Date(time);
            var o = {
                'M+': getDate.getMonth() + 1,
                'd+': getDate.getDate(),
                'h+': getDate.getHours(),
                'm+': getDate.getMinutes(),
                's+': getDate.getSeconds(),
                'q+': Math.floor((getDate.getMonth() + 3) / 3),
                'S': getDate.getMilliseconds()
            };
            if (/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (getDate.getFullYear() + '').substr(4 - RegExp.$1.length))
            }
            for (var k in o) {
                if (new RegExp('(' + k + ')').test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
                }
            }
            return fmt;
        },
        listOrders:function (pageIndex) {

            $("#pageIndex").val(pageIndex);

            $("#loading").show();

            var data = $("#form1").serialize(); //表单序列化 把表单里面的数据都列举出来

            this.ajaxPost(data)

        },
        ajaxPost:function (data) {
            $.ajax({
                type: 'post', //默认get
                url: "/api/order/listOrders.do",
                data:data,
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        $("#loading").hide();
                        // console.log(data.data.pageInfo.list)
                        var orderList = data.data.list;
                        app.orderList = orderList;//设置vue对象的model值
                        app.pageInfo = data.data;
                    } else {
                        alert(data.errorMsg)
                    }

                }
            });
        }
    },
    beforeCreate: function () {
        console.log("beforeCreate")
    },
    created: function () {
        console.log("created")
    },
    beforeMount: function () {
        console.log("beforeMount")
    },
    mounted: function () { //相当于onload  reday
        console.log("mounted")
        this.ajaxPost();//在读取页面的时候就进行渲染了

    },
    beforeUpdate: function () {
        console.log("beforeUpdate"+this.pageInfo)
    },
    updated: function () {
        console.log("updated"+this.pageInfo)
    },
    beforeDestroy: function () {
        console.log("beforeDestroy")
    },
    destroyed: function () {
        console.log("destroyed")
    }
});


// $.ajax({
//     type: 'post', //默认get
//     url: "/balance/listStreams_ajax.do",
//     async: true,   //是否异步（默认true：异步）
//     dataType: "json",//定义服务器返回的数据类型
//     success: function (data) {//data服务器返回的json字符串
//         if (data.success) {
//             // console.log(data.data.pageInfo.list)
//     var streamList = data.data.list;
// app.streamList = streamList;//设置vue对象的model值
// app.pageInfo = data.data;
// } else {
//     alert(data.errorMsg)
// }
//
// }
// });