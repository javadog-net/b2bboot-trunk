package com.jhmis.modules.shop.entity;

import java.util.List;

/**
 * @Author：hdx
 * @Description: 分类推荐容器表
 * @Date: Created in 20:50 2018/8/6
 * @Modified By
 */
public class RecommendRstContent {
    private String dictionaryId;
    private String dictionaryName;
    private List<RecommendSpecialtopic> recommendSpecialtopic;
    private List<Advert> advertList;

    public List<Advert> getAdvertList() {
        return advertList;
    }

    public void setAdvertList(List<Advert> advertList) {
        this.advertList = advertList;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public List<RecommendSpecialtopic> getRecommendSpecialtopic() {
        return recommendSpecialtopic;
    }

    public void setRecommendSpecialtopic(List<RecommendSpecialtopic> recommendSpecialtopic) {
        this.recommendSpecialtopic = recommendSpecialtopic;
    }
}
