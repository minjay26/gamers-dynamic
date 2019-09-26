package org.minjay.gamers.dynamic.data.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DynamicLike extends AbstractEntity<Long> {

    private String dynamicId;
    private Long userId;

    public DynamicLike() {
    }

    public DynamicLike(String dynamicId, Long userId) {
        this.dynamicId = dynamicId;
        this.userId = userId;
    }

    @Id
    @GeneratedValue
    @Override
    public Long getId() {
        return super.getId();
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
