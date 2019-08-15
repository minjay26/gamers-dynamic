package org.minjay.gamers.dynamic.domain;

public class Dynamic extends AbstractEntityAuditable<Long>{

    private String content;

    private int replyCount;
    private int likeCount;

    private Long userId;

    @Override
    public Long getId() {
        return super.getId();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
