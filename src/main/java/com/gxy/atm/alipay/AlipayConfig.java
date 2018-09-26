package com.gxy.atm.alipay;


import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2018082822154248110";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXpfhPpJc93vt3LxTu1MlW1xAO 9MnLcigTkPVhin6uvRQpxktAGH5JSLtm+vaHsnZo8hKq1J8D8x/l0XoJ0iHFXt2wRa3RpNXHn7Wl N7V2rKYLieYDu9a0rx9OSGK22haA+zhimeRhyGbBEmfS03PpiDHF69RTDn74HiKIWTQEI76AsVGV KIKjNPPzSc6aIHfWFXppJDu+kY0J1c7+RsiGIKi83O2GAf5Bsvp3NV6CmVRNjeeM35ksOTPOq/ra k2vIOdopAXrQN+ADfDYfXoKPJW+hzkfB+/kWIt31krmnS/Us0hgiOBScdHmaSWdFgfeNsbuzKo7Q kxW9S7pWkIDpAgMBAAECggEAOCQDZ0ihjRAtvBO8BibUWsfHdCH/Ssf6OjQiA3hIKdxpyIVWemYW 6n54kkk6V0YkO/nSG1gXfljVaDlCmrwmMHFMVOOW09PYKMzhbEbRcRu9ZOwqMRurAr0KM+rsUJEw X5Ohbi9WszSDhdqQgSYy9TOsaX9C3XSyYJyXYC7V6OnWZlENoplgbAjhfRZ1cPxjuYai/9V30t1f yTUT2zDBm4JIilYT/5W4PCdyh4rM22WllR2o6Ogmw9SmGXh7+X0U3Bj+rrfCZkbytZqZ51gT1ol8 5I32t51UUUtU0ni4q5ihCyat4vtewfYCyD7iKBJqqP8wFGqQlsxRR7i2hbXP+QKBgQDsIOFbNGzZ KZwPK3Cn2uyrNROd8YEhbcMiPxRT/psdwMfVc/YOOdVqczRO0SJXgEMkPMTCH8j99zXKED0h855k Y+ucgV01uMd/q++YvIl4cFhB03IshTn1dDbOqo15Xyf5LBBsjDtAh10ZCyNJGEGlYip51eDmKIQW pv01okyWdwKBgQCkaQ+sOcmURdFkLfdNSWhLxnCq1AVcnh1rdg98pFSb4/fw7YplQ0AkR2A7mkjr jVYoBG1pTvEsT6wslhP2clS/i37PxleBsulOlqztucFmvBbFXfo9EeBMMvICcYRiZuUcpswrKCeQ j1qPqISnpwfHRsuDlUx521mt0bjf7YObnwKBgFihMtVABUcx1hDflkZ65vCQWI+lMXnxsBevo5qv a1usm5VkFExcqt1s5opa90/upcT5RkUzFwc0fi0dEUUoIyC9XJd++xSKCd/DEoi648KgMJBspGNP kmSuPVAvNjEDZYaiYnvL2wWqiWg4BQOPpjxEb3sRq3vYY1P5U/uIssSbAoGAecDH933v9trhXvPf YR/MmdkB1LAgrF73YH5Up7Q2aB+hnIz84VK1LGijLmBqQe4v8ICe2c65MVxrH7WgYPB4LEVMiufS I91vg2B3/bCDNeZyYoTe7NzRIvzTVdXk+M5EOzyWlBsveZF/ukVMv68lRoD5857bvJ4hQmS+KS6G OPcCgYEAloEfO577rzOhtqrzzvcWzuNbGTSzMSw5WGfnR+CETNAWBGJE57L2dEQ1TwkIF27AJ3pu eyRTUS+5eooXzZciIQpQh1euuf/jinHE79Wjc0TgGJ0pdQ+uKbVg+x1bq4QUwZ+RNdQpODSRd73M H+borW6jWIcUZmPQ2B/3pPFQE+4=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4SZgs0ZcIyvfS4hv2WEY PtoLpTCmza2IQKsLWxXHWwIFgPsnDB61jvNG9Oij7pmxx/+JMOWkcdVj1ltoRShH Dy3HAelIYxXnpKnHI3x9c6PyYUIX+szZ/dlkCEEbJPQ0tQqvkauK2ue3gDTBeWw/ frSbRUh0sQu47IUX8XO0MoUZDo62/5mnRnq4S+BKltL08eV7Cb5U+d/sIoce/IuR rDe9IKzdRk+efsSGK52nttuMa5NBOXpZrOg4MdLlhepc/g5xuZAIHkn+MAlW/gRD NfKvfOHh49ghpkO8ilNCQEC3KTn6uccl1DfxWcbgm0Slk5d/4XJ0dJWkarcsTcr2 nQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://guxingyuatm.frp3.chuantou.org/api/order/notifyUrl.do";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://guxingyuatm.frp3.chuantou.org/";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "http://payhub.dayuanit.com/gateway/alipay/web.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}