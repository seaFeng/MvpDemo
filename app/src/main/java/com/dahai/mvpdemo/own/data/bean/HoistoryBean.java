package com.dahai.mvpdemo.own.data.bean;

/**
 * 历史上的今天的实体类
 * Created by 张海洋 on 2017-07-31.
 */

public class HoistoryBean {

    /**
     * _id : 15521006
     * title : 中国的耶稣会传教士、学者意大利人利玛窦出生
     * pic :
     * year : 1552
     * month : 10
     * day : 6
     * des : 在465年前的今天，1552年10月6日 (农历九月十九)，中国的耶稣会传教士、学者意大利人利玛窦出生。
     * lunar : 壬子年九月十九
     */

    private String _id;
    private String title;
    private String pic;
    private int year;
    private int month;
    private int day;
    private String des;
    private String lunar;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }
}
