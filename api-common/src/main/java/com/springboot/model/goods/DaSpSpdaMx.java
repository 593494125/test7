package com.springboot.model.goods;

import com.baomidou.mybatisplus.annotations.TableField;
import com.springboot.model.BasePageForm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品商品明细
 * </p>
 *
 * @author zjq
 * @since 2020-04-21
 */
public class DaSpSpdaMx extends BasePageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品编号
     */
    private String spbh;
    /**
     * 商品款号
     */
    private String spkh;
    /**
     * 款名
     */
    private String spmc;
    /**
     * 条码号
     */
    private String tmbh;
    /**
     * 颜色流水号
     */
    private String yslsh;
    /**
     * 颜色编号
     */
    private String ysbh;
    /**
     * 尺码编号
     */
    private String cmbh;
    /**
     * 版型编号
     */
    private String bxbh;
    /**
     * 版型名称
     */
    private String bxmc;
    /**
     * 颜色名称
     */
    private String ysmc;
    /**
     * 尺码组编号
     */
    private String cmzbm;
    /**
     * 尺码标题行位置
     */
    private String cmbthwz;
    /**
     * 尺码代码行位置
     */
    private String cmdmhwz;
    /**
     * 尺码代码列位置
     */
    private String cmdmlwz;
    /**
     * 修改标志
     */
    @TableField("updateFlag")
    private Date updateFlag;
    @TableField("sortid")
    private String sortid;//删除商品图片使用
    @TableField(exist = false)
    private List<Img> imageUrlList;//

    public String getSpbh() {
        return spbh;
    }

    public void setSpbh(String spbh) {
        this.spbh = spbh;
    }

    public String getSpkh() {
        return spkh;
    }

    public void setSpkh(String spkh) {
        this.spkh = spkh;
    }

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getTmbh() {
        return tmbh;
    }

    public void setTmbh(String tmbh) {
        this.tmbh = tmbh;
    }

    public String getYslsh() {
        return yslsh;
    }

    public void setYslsh(String yslsh) {
        this.yslsh = yslsh;
    }

    public String getYsbh() {
        return ysbh;
    }

    public void setYsbh(String ysbh) {
        this.ysbh = ysbh;
    }

    public String getCmbh() {
        return cmbh;
    }

    public void setCmbh(String cmbh) {
        this.cmbh = cmbh;
    }

    public String getBxbh() {
        return bxbh;
    }

    public void setBxbh(String bxbh) {
        this.bxbh = bxbh;
    }

    public String getBxmc() {
        return bxmc;
    }

    public void setBxmc(String bxmc) {
        this.bxmc = bxmc;
    }

    public String getYsmc() {
        return ysmc;
    }

    public void setYsmc(String ysmc) {
        this.ysmc = ysmc;
    }

    public String getCmzbm() {
        return cmzbm;
    }

    public void setCmzbm(String cmzbm) {
        this.cmzbm = cmzbm;
    }

    public String getCmbthwz() {
        return cmbthwz;
    }

    public void setCmbthwz(String cmbthwz) {
        this.cmbthwz = cmbthwz;
    }

    public String getCmdmhwz() {
        return cmdmhwz;
    }

    public void setCmdmhwz(String cmdmhwz) {
        this.cmdmhwz = cmdmhwz;
    }

    public String getCmdmlwz() {
        return cmdmlwz;
    }

    public void setCmdmlwz(String cmdmlwz) {
        this.cmdmlwz = cmdmlwz;
    }

    public Date getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(Date updateFlag) {
        this.updateFlag = updateFlag;
    }

    public List<Img> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<Img> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public String getSortid() {
        return sortid;
    }

    public void setSortid(String sortid) {
        this.sortid = sortid;
    }

    public DaSpSpdaMx() {
    }

}
