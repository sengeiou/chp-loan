/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.constantsProvinceCity.java
 * @Create By 张灏
 * @Create In 2016年8月19日 下午2:55:51
 */
package com.creditharmony.loan.borrow.grant.constants;

import java.util.HashMap;
import java.util.Map;


/**
 * @Class Name ProvinceCity
 * @author 张灏
 * @Create In 2016年8月19日
 */
public enum ProvinceCity {
 
    XI_ZANG("西藏自治区","西藏"),
    NING_XIA("宁夏回族自治区","宁夏"),
    GUANG_XI("广西壮族自治区","广西"),
    XIN_JIANG("新疆维吾尔自治区","新疆"),
    NEI_MENG("内蒙古自治区","内蒙古"),
    CHANG_JI1("昌吉自治州","昌吉自治州"),
    CHANG_JI2("昌吉回族自治州","昌吉自治州"),
    YAN_BIAN("延边朝鲜族自治州","延边自治州"),
    DA_LI("大理白族自治州","大理自治州"),
    LIANG_SHAN("凉山彝族自治州","凉山自治州"),
    QIN_DONG_NAN("黔东南苗族侗族自治州","黔东南州"),
    HAI_BEI("海北藏族自治州","海北自治州"),
    HUANG_NAN("黄南藏族自治州","黄南自治州"),
    QIN_XI_NAN1("黔西南布依族苗族自治州","黔西南州"),
    XIANG_XI("湘西土家族苗族自治州","湘西自治州"),
    HAI_NAN("海南藏族自治州","海南自治州"),
    WEN_SHAN("文山壮族苗族自治州","文山自治州"),
    BO_ER_TA_LA("博尔塔拉蒙古自治州","博尔塔拉"),
    DI_QING("迪庆藏族自治州","迪庆自治州"),
    EN_SHI("恩施土家族苗族自治州","恩施自治州"),
    NU_JIANG("怒江傈僳族自治州","怒江自治州"),
    YI_LI("伊犁哈萨克自治州","伊犁自治州"),
    XIONG_CHU("楚雄彝族自治州","楚雄自治州"),
    HONG_HE("红河哈尼族彝族自治州","红河自治州"),
    YU_SHU("玉树藏族自治州","玉树自治州"),
    LIN_XIA("临夏回族自治州","临夏自治州"),
    DE_HONG("德宏傣族景颇族自治州","德宏自治州"),
    QIN_NAN("黔南布依族苗族自治州","黔南自治州"),
    GAN_NAN("甘南藏族自治州","甘南自治州"),
    KE_ZI_LE_SU("克孜勒苏柯尔克孜自治州","克孜勒苏"),
    BA_YIN_GUO_LE("巴音郭楞蒙古自治州","巴音郭楞"),
    A_BA("阿坝藏族羌族自治州","阿坝自治州"),
    GAN_ZI("甘孜藏族自治州","甘孜自治州"),
    GUO_LUO("果洛藏族自治州","果洛自治州"),
    HAI_XI("海西蒙古族藏族自治州","海西自治州");
    private String areaName;
    private String mapName;
    private static Map<String,ProvinceCity> codeMap = new HashMap<String, ProvinceCity>(
            100);
    static {
        ProvinceCity[] allValues = ProvinceCity.values();
        for (ProvinceCity obj : allValues) {
            codeMap.put(obj.getAreaName(), obj);
           }
    }
    private ProvinceCity(String areaName,String mapName) {
        this.areaName = areaName;
        this.mapName = mapName;
    }
    
    public static ProvinceCity parseByAreaName(String code) {
        return codeMap.get(code);
    }


    /**
     * @return the areaName
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName the String areaName to set
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * @return the mapName
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * @param mapName the String mapName to set
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
