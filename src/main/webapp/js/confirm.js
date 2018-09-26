function confirmDraw() {

    var money = $("#draw").val();

    if (!money) {
        alert("输入金额不能为空");
        return false;
    }

    if (isNaN(money)) {
        alert("输入金额不是有效数字");
        return false;
    }

    if (money<=0) {
        alert("输入金额不能小于0");
        return false;
    }

    return true;

}

function submitFormDraw() {
    var v = confirmDraw();

    if(v){

        dm.submit();
    }
}

function confirmSave() {

    var money = $("#save").val();

    if (!money) {
        alert("输入金额不能为空");
        return false;
    }

    if (isNaN(money)) {
        alert("输入金额不是有效数字");
        return false;
    }

    if (money<=0) {
        alert("输入金额不能小于0");
        return false;
    }

    return true;

}

function submitFormSave() {
    var v = confirmSave();

    if(v){

        sv.submit();
    }
}

function confirmTransfer() {
    var money = $("#money").val();
    var inCardNo = $("#inCardNo").val();
    var inName = $("#inName").val();

    if (!money) {
        alert("金额不能为空");
        return false;
    }

    if (isNaN(money)) {
        alert("金额不是有效数字");
        return false;
    }

    if (money<=0) {
        alert("输入金额不能小于0");
        return false;
    }

    if (!inCardNo) {
        alert("卡号不能为空");
        return false;
    }

    if (isNaN(inCardNo)) {
        alert("卡号不是有效数字");
        return false;
    }

    if (!inName) {
        alert("姓名不能为空");
        return false;
    }

    return true;
}

function confirmLogin() {
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


    return true;
}

function confirmRechargeInfo() {

    var money = $("#recharge").val();

    if (!money) {
        alert("输入金额不能为空");
        return false;
    }

    if (isNaN(money)) {
        alert("输入金额不是有效数字");
        return false;
    }

    if (money<=0) {
        alert("输入金额不能小于0");
        return false;
    }

    return true;

}

function submitFormRechargeInfo() {
    var v = confirmRechargeInfo();

    if(v){

        dm.submit();
    }
}

