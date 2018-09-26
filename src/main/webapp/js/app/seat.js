var app = new Vue({
    el: '#app',
    data: {
        movie: {},
        selectedSeats: [], //已经选择的座位
        unavailableSeats: [],//已售出
        ShowMsg:{},
    },
    watch:{//侦听器（侦听unavailableSeats属性变化，即执行）
        // 如果 `question` 发生改变，这个函数就会运行
        unavailableSeats: function (newValue, oldValue) {
            // alert(newValue);
            // alert(oldValue);
            $.each(newValue, function (index, value) {
                $("#" + value).removeClass("available").addClass("unavailable");//改成已售出
            })
        }
    },
    methods: {
        listSaledSeats:function () {
            //从后台获取已经售出的座位
            var showTimeId = UrlParam.param("showTimeId");
            $.ajax({
                type: 'post', //默认get
                url: "/api/order/listSaledSeats.do",
                data: {showTimeId: showTimeId},
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        // $.each(data.data, function (index, value) {
                        //     $("#" + value).removeClass("available").addClass("unavailable");//改成已售出
                        // })
                        app.unavailableSeats = data.data;
                    } else {
                        alert(data.errorMsg)
                    }

                }
            });

        }
        ,
        formatSet: function (v) {
            var strs = v.split('_');//相当于把2_2变成了一个[2,2的数组]然后拼接字符串
            return strs[0] + "排" + strs[1] + "座";

        }, chooseSeat: function (event) {
            var currentTargetDom = event.currentTarget; //获取点击事件html传来的对象

            //原生js方式
            // var id = currentTargetDom.getAttribute("id");
            // this.selectedSeats.push(id);//向数组尾部添加

            var jquery_currentTargetDom = $(currentTargetDom);//使用jq的方式
            var id = jquery_currentTargetDom.attr("id"); //获取座位表id的属性

            //如果座位已经售出则不执行后面的逻辑
            if (this.unavailableSeats.includes(id)) {
                return;
            }

            //更改样式
            //方法1 if (this.selectedSeats.indexOf(id)===-1)
            if (!this.selectedSeats.includes(id)) {
                jquery_currentTargetDom.removeClass("available").addClass("selected"); //改成选中状态
                this.selectedSeats.push(id);//向selectedSeats数组添加
            } else {
                jquery_currentTargetDom.removeClass("selected").addClass("available");//改为未选中
                this.selectedSeats.splice(this.selectedSeats.indexOf(id), 1);//删除数组指定元素
            }


        }, addOrder:function () {
            var showTimeId = UrlParam.param("showTimeId");
            $.ajax({
                type: 'post', //默认get
                url: "/api/order/addOrder.do",
                data: {
                    movieId: 1,//需要查询
                    showTimeId: showTimeId,
                    selectedSeats: this.selectedSeats
                },
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (result) {//data服务器返回的json字符串
                    if (result.success) {
                        $("#app").html(result.data);

                    } else {
                        alert(result.errorMsg)

                        if (result.errorCode === 600) {
                            //跳转到登录页面
                            window.location.href = "/login.html"
                        }

                        //重新刷新
                        //从后台获取已经售出的座位
                        app.listSaledSeats();

                        //清空已选择座位
                        app.selectedSeats = [];
                    }

                }
            });
        }

    },
    mounted: function () {  //加载页面就要传递的参数 生命周期
        this.listSaledSeats();

        var showTimeId = UrlParam.param("showTimeId");

        //查询电影详情
        $.ajax({
            type: 'post', //默认get
            url: "/api/getMovieByShowTimeId.do",
            data: {showTimeId: showTimeId},
            async: true,   //是否异步（默认true：异步）
            dataType: "json",//定义服务器返回的数据类型
            success: function (data) {//data服务器返回的json字符串
                if (data.success) {
                    app.movie = data.data;
                } else {
                    alert(data.errorMsg)
                }

            }
        });

        //查询电影时间 和时间段
        $.ajax({
            type: 'post', //默认get
            url: "/api/getShowMsgById.do",
            data: {showTimeId: showTimeId},
            async: true,   //是否异步（默认true：异步）
            dataType: "json",//定义服务器返回的数据类型
            success: function (data) {//data服务器返回的json字符串
                if (data.success) {
                    app.ShowMsg = data.data;
                } else {
                    alert(data.errorMsg)
                }

            }
        });

    }
});