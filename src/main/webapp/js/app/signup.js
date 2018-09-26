
var app = new Vue({
    el: '#app',
    data: {},
    methods:{
        sendCode:function () {

            var szReg = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;//正则验证邮箱

            var loginName = $("#loginName").val();
            if(!loginName){
                alert("请输入邮箱");
                return ;
            }
            if (!szReg.test(loginName)){
                alert("请输入正确的邮箱格式");
                return;
            }

            $("#btn_sendcode").attr('disabled', 'disabled').html('发送中'); //将发送验证码变为发送中且不能点击

            $.ajax({
                type: 'post', //默认get
                url: "/api/user/sendCode.do",
                data:{email: loginName},
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        app.codeCont(10);
                    } else {
                        alert(data.errorMsg)
                        app.codeCont(10);
                    }

                }
            });
            //实现读秒的方式
        }, codeCont:function (c) {

            if (c == 0) {
                $("#btn_sendcode").removeAttr("disabled").html("发送验证码");
                return;
            }

            $("#btn_sendcode").html("等待"+ c-- +"秒重新获取");

            setTimeout(function () {
                app.codeCont(c); //自己调用自己实现递归
            }, 1000);
        }
        ,signUp :function () {
            $.ajax({
                type: 'post', //默认get
                url: "/api/user/signUp.do",
                data:$("#form1").serialize(),
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        alert("注册成功");
                        window.location.href = "/login.html"
                    } else {
                        alert(data.errorMsg)
                    }

                }
            });
        }
    }
});




