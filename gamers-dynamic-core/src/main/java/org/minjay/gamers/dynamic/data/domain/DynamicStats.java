package org.minjay.gamers.dynamic.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class DynamicStats extends AbstractEntityAuditable<String> {

    private int likeCount;
    private int replyCount;

    private int readCount;

    private long version;

    public DynamicStats() {
    }

    public DynamicStats(String dynamicId) {
        this.id = dynamicId;
    }

    @Id
    @Column(length = 20)
    @Override
    public String getId() {
        return super.getId();
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    @Version
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
