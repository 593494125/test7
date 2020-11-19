package com.springboot.common;

public class SystemConstant {

    public static final String CM = "cm";
    public static final String XXLY = "IPAD";
    public static final Integer pageNo=1;//第几页
    public static final Integer pageSize=20;//一页多少数据

    public static final String CG_TYPE = "CG";
    public static final String CR_TYPE = "CR";
    public static final String CT_TYPE = "CT";
    public static final String DC_TYPE = "DC";
    public static final String DR_TYPE = "DR";
    public static final String PF_TYPE = "PF";
    public static final String PT_TYPE = "PT";
    public static final String KT_TYPE = "KT";//库存调整单
    public static final String KP_TYPE = "KP";//库存盘存单
    public static final String LSPOS_TYPE = "POS";//零售
    public static final String KPJZ_TYPE = "KPJZ";//库存盘存记账
    public static final String LC_TYPE = "LC";//连锁出
    public static final String LT_TYPE = "LT";//连锁退
    public static final String SY_TYPE = "SY";//库存损溢
    public static final String XHSK_TYPE = "XS";//销货收款单
    public static final String XHFY_TYPE = "XF";//销货费用单
    public static final String JHFK_TYPE = "FK";//进货付款单
    public static final String JHFY_TYPE = "JF";//进货费用单
    public static final String POSSPKH_TYPE = "POSSPKH";//联营结算单

    public static final String LSZF_CSFL_TYPE = "POS_FKLX";//支付方式


    public static final String YW_CG_ORDER_NAME = "采购单";
    public static final String YW_DB_ORDER_NAME = "调拨单";
    public static final String YW_PF_ORDER_NAME = "批发单";
    public static final String YW_KC_ORDER_NAME = "库存单";
    public static final String YW_LSPOS_ORDER_NAME = "零售单";
    public static final String YW_XHSK_ORDER_NAME = "销货收款单";
    public static final String YW_XHFY_ORDER_NAME = "销货费用单";
    public static final String YW_JHFK_ORDER_NAME = "进货付款单";
    public static final String YW_JHFY_ORDER_NAME = "进货费用单";
    public static final String YW_POSSPKH_ORDER_NAME = "连锁联营单";

    //a8 redis 用户表
    public static final String REDIS_YHDA_TABLE = "_011_table";
    public static final String REDIS_MOKUAI_TABLE = "_023_";
    public static final String REDIS_DEPART_TABLE = "_007_table";
    //a8 redis 尺码标题key
    public static final String REDIS_TYPE_DA_SP_CMBT_TABLE = "_016_table";
    //a8 redis 尺码代码key
    public static final String REDIS_TYPE_DA_SP_CMDM_TABLE = "_017_table";
    //a8 redis 尺码组表key
    public static final String REDIS_TYPE_DA_SP_CMZB_TABLE = "_018_table";
    //a8 redis 尺码组表key

    //redis默认主库key
    public static final String DEFAULT_REDIS_KEY = "defaultmain";
    //区域服务器默认地址
    public static final String AREA_SERVICE_URL = "http://ymt.naoerp.com";
    //区域服务器默认端口
    public static final String AREA_SERVICE_PORT = ":8113";

}
