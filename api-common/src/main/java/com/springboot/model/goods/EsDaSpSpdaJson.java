package com.springboot.model.goods;

import com.springboot.model.BasePageForm;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

//@Document(indexName="esdaspspdajson",type="esDaSpSpdaJson" ,shards = 1)
public class EsDaSpSpdaJson extends BasePageForm implements Serializable {

    @Id
    private String id;
//    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String spkh;
//    @Field(analyzer = "ik_max_word")
    private String ksmc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpkh() {
        return spkh;
    }

    public void setSpkh(String spkh) {
        this.spkh = spkh;
    }

    public String getKsmc() {
        return ksmc;
    }

    public void setKsmc(String ksmc) {
        this.ksmc = ksmc;
    }
}
