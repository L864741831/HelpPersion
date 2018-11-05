
package com.tianjistar.helppersion.api;

/**
 * 类说明: 定义网络请求的url
 * @author Victor
 * @email 468034043@qq.co
 * @time 2016年10月29日 下午9:11:53
 */
public class Define {
 public static final String API_DOMAIN = "http://nlcs.tianjistar.com/index.php/Home/";// API域名 测试环境
    public static final String FIND_HEAD_URL = API_DOMAIN + "User/userinfo";//获取用户信息
    public static final String MINE_FEELING_URL = API_DOMAIN + "Gnosis/gnosisList";//我的心得
    public static final String FAMILY_URL = API_DOMAIN + "Family/familyInfo";//家族
    public static final String SELECT_ZUZHANG_URL = API_DOMAIN + "/Family/choice";//群列表选择族长
    public static final String FAMILY_INFO_URL = API_DOMAIN + "Family/inforMation";//家族信息
    public static final String EDITEX_FAMILY_INFO = API_DOMAIN + "Family/save";//保存编辑家族信息


       public static final String BASE_URL = "http://ty.jiushitao.cn";// 测试
//    public static final String BASE_URL = "http://ty.tianjistar.com";// 正式


   /**
    * 登录
    */
   public static final String LOGIN_URL = BASE_URL + "/a/login?__ajax=true";
    /**
     * 退出登录
     */
    public static final String WITHDRAW_URL = BASE_URL + "/logout";
    /**
     * 获取用户信息
     */
    public static final String GET_USER_INFO_URL = BASE_URL + "/a/mobile/user";
    /**
     * 修改个人信息
     */
    public static final String MODIFY_USER_INFO_URL = BASE_URL + "/a/mobile/userModify";
    /**
     * 上传图片
     */
    public static final String UPLOAD_PIC_URL = BASE_URL + "/a/alioss/uploadFile";
    /**
     * 实名认证
     */
    public static final String REAL_NAME_AUTHENTICATION_URL = BASE_URL + "/a/mobile/actualName";

    /**
     * 查询本部门的下的所有救援人员
     */
    public static final String ALL_HELP_PERSION_URL = BASE_URL + "/a/mobile/findMyJUserList";


    //我的钱包
    /**
     *查询余额
     */
    public static final String CHECK_MONEY_URL = BASE_URL + "/a/tyMoney/findMoney";
    /**
     *获得银行卡信息
     */
    public static final String GET_BANK_INFO_URL = BASE_URL + "/a/tyMoney/AllDealForNJ";
    /**
     *提现
     */
    public static final String GET_MONET_URL = BASE_URL + "/a/tyMoney/withdrawals";


    //设置
    /**
     * 修改登录密码
     */
    public static final String UPDATE_PASSWORD_URL = BASE_URL + "/a/mobile/updatePassword";
    /**
     * 检查支付密码是否正确
     */
    public static final String CHECK_PAY_IS_PASS_URL = BASE_URL + "/a/Payment/checkPayPass";
    /**
     *设置新支付密码
     */
    public static final String SET_NEW_PAY_PSW_URL = BASE_URL + "/a/Payment/settingsPayPass";
    /**
     *验证个人信息
     */
    public static final String CHECK_PERSION_INFO_URL = BASE_URL + "/a/Payment/verifMessage";
    /**
     *发送短信验证码
     */
    public static final String SEND_YZM_URL = BASE_URL + "/sms/send";
    /**
     *检查短信验证码是否正确
     */
    public static final String CHECK_YZM_IS_PASS_URL = BASE_URL + "/sms/checkCode";









    /**
     * 登录
     */
    public static final String URL_LOGIN = API_DOMAIN + "Login/login";
    /**
     * 获取验证码
     */
    public static final String URL_GET_CAPTCHA = API_DOMAIN + "Login/sms";
    /**
     * 获取忘记密码验证码
     */
    public static final String URL_FORGET_GET_YZM=API_DOMAIN+"Login/sms";
    /**
     * 检验验证码
     */
    public static final String URL_FORGET_CHECK_YZM=API_DOMAIN+"Login/codeVerify";
    public static final String URL_friend_list=API_DOMAIN+"User/friend";
    /**
     *
     寄语列表
     */
    public static final String URL_wishes=API_DOMAIN+"Sendword/sendwordList";
    /**
     *
     修改登录密码
     */
    public static final String URL_updata_login_pwd=API_DOMAIN+"User/uppassword";
   /**
    *
   银行卡详细信息
    */
   public static final String URL_bank_detail=API_DOMAIN+"Wallet/GetMemberBankInfo";
   /**
    *
    判断是否实名认证过
    */
   public static final String URL_if_real_name=API_DOMAIN+"user/is_realName";
   /**
    *
   提现
    */
   public static final String URL_Withdraw=API_DOMAIN+"Wallet/Withdraw";
   /**
    *
    根据卡号获取卡类型
    */
   public static final String URL_get_cardType=API_DOMAIN+"Bank/BacktrackBankName";
   /**
    *
    支付密码验证身份
    */
   public static final String URL_pwd_check=API_DOMAIN+"Wallet/checkPaypassword";
   /**
    *
    绑银行卡
    */
   public static final String URL_add_bank=API_DOMAIN+"Wallet/BankVerify";
}
