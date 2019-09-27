package org.minjay.gamers.dynamic.service.model;

import org.minjay.gamers.dynamic.data.domain.DynamicReply;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class DynamicReplyDto {

    private String dynamicId;
    private Long id;
    private Long userId;
    private String username;

    private String content;

    private List<SubReply> subReplies;

    public static DynamicReplyDto fromDynamicReply(DynamicReply reply) {
        DynamicReplyDto dto = new DynamicReplyDto();
        BeanUtils.copyProperties(reply, dto);
        return dto;
    }


    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<SubReply> getSubReplies() {
        return subReplies;
    }

    public void setSubReplies(List<SubReply> subReplies) {
        this.subReplies = subReplies;
    }

    public static class SubReply {
        private Long id;
        private Long userId;
        private String username;
        private String content;
        private int depth;
        private Long replyUserId;
        private String replyUsername;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public Long getReplyUserId() {
            return replyUserId;
        }

        public void setReplyUserId(Long replyUserId) {
            this.replyUserId = replyUserId;
        }

        public String getReplyUsername() {
            return replyUsername;
        }

        public void setReplyUsername(String replyUsername) {
            this.replyUsername = replyUsername;
        }

        public SubReply fromDynamicReply(DynamicReply sub) {
            SubReply reply = new SubReply();
            BeanUtils.copyProperties(sub, reply);
            return reply;
        }
    }

}
