var app = new Vue({
   el:'#app',
    data:{
       movies:[]

    },
    created: function () {
        $.ajax({
            type: 'post', //默认get
            url: "/api/listMovies.do",
            async: true,   //是否异步（默认true：异步）
            dataType: "json",//定义服务器返回的数据类型
            success: function (data) {//data服务器返回的json字符串
                if (data.success) {
                    app.movies = data.data;
                } else {
                    alert(data.errorMsg)
                }

            }
        });
    }

});