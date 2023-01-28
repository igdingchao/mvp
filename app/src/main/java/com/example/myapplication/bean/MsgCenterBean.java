package com.example.myapplication.bean;

public class MsgCenterBean implements Comparable<MsgCenterBean> {

    private Long id;//必须要设置为Long，不然就不会自增
    private boolean isSelected;//消息是否被选择
    private int mMsgType;//消息类型
    private int mPriority = 0;//优先级
    private int mIcon;//图标
    private String mTitle;//标题
    private String mContent;//内容
    private long time;//消息接受的时间
    private String msgFelling;

    public MsgCenterBean() {
    }

    @Override
    public int compareTo(MsgCenterBean msgCenterBean) {
        int result = msgCenterBean.getPriority() - this.getPriority();//先按照优先级来降序，因为5是放在最前面的
        if (result == 0) {//优先级一样的话，就按照发送的时间来降序
            result = (int) (msgCenterBean.getTime() - this.getTime());
        }
        return result;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getMsgType() {
        return this.mMsgType;
    }

    public void setMsgType(int mMsgType) {
        this.mMsgType = mMsgType;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public int getIcon() {
        return this.mIcon;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return this.mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMsgFelling() {
        return this.msgFelling;
    }

    public void setMsgFelling(String msgFelling) {
        this.msgFelling = msgFelling;
    }

    public int getMMsgType() {
        return this.mMsgType;
    }

    public void setMMsgType(int mMsgType) {
        this.mMsgType = mMsgType;
    }

    public int getMPriority() {
        return this.mPriority;
    }

    public void setMPriority(int mPriority) {
        this.mPriority = mPriority;
    }

    public int getMIcon() {
        return this.mIcon;
    }

    public void setMIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public String getMTitle() {
        return this.mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMContent() {
        return this.mContent;
    }

    public void setMContent(String mContent) {
        this.mContent = mContent;
    }

}
