package org.minjay.gamers.dynamic.data.domain;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;

public abstract class AbstractEntityAuditable<ID extends Serializable> extends AbstractEntity<ID> {

    private static final long serialVersionUID = 7479587631743834284L;

    private DateTime createdDate;
    private DateTime lastModifiedDate;

    /**
     * Returns the creation date of the entity.
     *
     * @return the createdDate
     */
    @CreatedDate
    public DateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the creation date of the entity.
     *
     * @param createdDate the creation date to set
     */
    public void setCreatedDate(final DateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Returns the date of the last modification.
     *
     * @return the lastModifiedDate
     */
    @LastModifiedDate
    public DateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the date of the last modification.
     *
     * @param lastModifiedDate the date of the last modification to set
     */
    public void setLastModifiedDate(final DateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}