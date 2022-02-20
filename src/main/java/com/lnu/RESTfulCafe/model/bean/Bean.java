package com.lnu.RESTfulCafe.model.bean;

import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "beanseq", initialValue = 1)
public class Bean {
    private @Id @GeneratedValue(generator = "beanseq") Long id;
    private @Column(unique=true) String name;
    private String origin;
    private String region;
    private Variety variety;
    private ArrayList<String> aroma;
    private Boolean reviewed;

    public Bean () {}

    public Bean(Long id, String name, String origin, String region, Variety variety, ArrayList<String> aroma, Boolean reviewed) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.region = region;
        this.variety = variety;
        this.aroma = aroma;
        this.reviewed = reviewed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Variety getVariety() {
        return variety;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }

    public ArrayList<String> getAroma() {
        return aroma;
    }

    public void setAroma(ArrayList<String> aroma) {
        this.aroma = aroma;
    }

    public Boolean getReviewed() {
        return reviewed;
    }

    public void setReviewed(Boolean reviewed) {
        this.reviewed = reviewed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bean bean = (Bean) o;
        return Objects.equals(id, bean.id)
                && Objects.equals(name, bean.name)
                && Objects.equals(origin, bean.origin)
                && Objects.equals(region, bean.region)
                && variety == bean.variety
                && Objects.equals(aroma, bean.aroma)
                && Objects.equals(reviewed, bean.reviewed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, origin, region, variety, aroma, reviewed);
    }
}
