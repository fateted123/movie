function login(){
    var loginName = $("#loginName").val();
    var password = $("#password").val();

    if (!loginName) {
        alert("账号不能为空");
        return false;
    }

    if (isNaN(loginName)) {
        alert("账号不是有效数字");
        return false;
    }

    if (!password) {
        alert("密码不能为空");
        return false;
    }

    if (isNaN(password)) {
        alert("密码不是有效数字");
        return false;
    }


    $.ajax({
        type: 'post', //默认get
        url: "/api/user/login.do",
        data:$("#form1").serialize(), //表单序列化
        async: true,   //是否异步（默认true：异步）
        dataType: "json",//定义服务器返回的数据类型
        success: function (data) {//data服务器返回的json字符串
            if (data.success) {
                window.location.href = "/"
            } else {
                alert(data.errorMsg)
            }

        }
    });
}

