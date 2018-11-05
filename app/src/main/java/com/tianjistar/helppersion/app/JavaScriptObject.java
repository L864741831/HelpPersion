package com.tianjistar.helppersion.app;

import android.app.Activity;


/**
 * WebView与android的API交互的接口
 *
 * @author Victor
 * @email 468034043@qq.com
 * @time 2017年9月6日 上午9:37:17
 */
public class JavaScriptObject {

    private Activity mActivity;

    public JavaScriptObject(Activity mActivity) {
        this.mActivity = mActivity;
    }

//    /**
//     * 查看家谱用户个人详情
//     */
//    @JavascriptInterface
//    public void familyUser(int id){
//        //移动端的操作
//        Bundle bundle=new Bundle();
//        bundle.putString("fuserId",id+"");
//        MyApplication.openActivity(mActivity, FamilayUserActivity.class,bundle);
//    }
//    /**
//     * 编辑家谱用户个人详情
//     */
//    @JavascriptInterface
//    public void EditfamilyUser(int id){
//        //移动端的操作
//        Bundle bundle=new Bundle();
//        bundle.putString("fuserId",id+"");
//        bundle.putString("type","");
//        MyApplication.openActivity(mActivity, EditFamilyUserActivity.class,bundle);
//    }
//    /**
//     * 添加家谱成员用户
//     * @param type 要传的添加节点的类型  	1后代 2长辈 3配偶 4兄弟姐妹
//     *             id; 当前节点的id
//     */
//    @JavascriptInterface
//    public void addElder(int id,int type){
//        //移动端的操作
//        Bundle bundle=new Bundle();
//        bundle.putString("type",type+"");
//        bundle.putString("fuserId","");
//        bundle.putString("id",id+"");
//        MyApplication.openActivity(mActivity, EditFamilyUserActivity.class,bundle);
//    }
//    /**
//     * 编辑家谱编辑自己
//     */
//    @JavascriptInterface
//    public void editData(){
//        //移动端的操作
//        Bundle bundle=new Bundle();
//        bundle.putString("fuserId","");
//        bundle.putString("type","");
//        MyApplication.openActivity(mActivity, EditFamilyUserActivity.class,bundle);
//    }
//
//    @JavascriptInterface
//public void infoList(int id){
//    //点击进入好友资料页面 并且请求阅读量接口
//    Bundle bundle=new Bundle();
//    bundle.putString("otheruserid",id+"");
//    MyApplication.openActivity(mActivity,FindPersionInfoActivity.class,bundle);
//}
//
//
//
////    /**
////     * 编辑家谱编辑自己
////     */
////    @JavascriptInterface
////    public void editData(){
////        //移动端的操作
////        Bundle bundle=new Bundle();
////        bundle.putString("fuserId","");
////        bundle.putString("type","");
////        MyApplication.openActivity(mActivity, EditFamilyUserActivity.class,bundle);
////    }
}
