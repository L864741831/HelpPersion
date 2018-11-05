package com.tianjistar.helppersion.bean;


import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import com.tianjistar.helppersion.utils.FlockUtil;


/**
 */
public class ContactsBean extends BaseIndexPinyinBean {

    private String username;//用户名
    private  String userid;//用户id
    private  String photo;//用户头像
    private String level;
    private boolean isItemChecked;//是否选中
//
//    protected String initialLetter; //首字母的昵称
//
  private boolean isTop;//是否是最上面的 不需要被转化成拼音的


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

//    public String getInitialLetter() {
//        if(initialLetter == null){
//            EaseCommonUtils.setUserInitialLetter(this);
//        }
//        return initialLetter;
//    }
    public boolean isTop() {
        return isTop;
    }

    public ContactsBean setTop(boolean top) {
        isTop = top;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



    public boolean getIsItemChecked() {
        return isItemChecked;
    }

    public void setIsItemChecked(boolean isItemChecked) {
        this.isItemChecked = isItemChecked;
    }

    public ContactsBean() {
    }
    public ContactsBean(String username, String photo, String userid) {
        this.photo=photo;
        this.username = username;
        this.userid=userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
//        this.photo=photo;
//        this.userid=userid;
    }
    public void setCity(String username,String photo,String userid) {
        this.username = username;
        this.photo=photo;
        this.userid=userid;
    }

    @Override
    public String getTarget() {
        return FlockUtil.jiemi(username);
    }
//    @Override
//    public String getTarget() {
//        return username;
//    }
//
    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }


    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }




}
