package org.minjay.gamers.dynamic.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties("location")
public class DynamicDto implements Serializable {
    private static final long serialVersionUID = 7971965014930286727L;

    private String id;
    private String content;
    private int replyCount;
    private int likeCount;
    private Date createDate = new Date();
    private Long userId;
    private String username;
    private String ip;
    private double lat;
    private double lon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Dynamic toDynamic() {
        Dynamic dynamic = new Dynamic();
        BeanUtils.copyProperties(this, dynamic);
        return dynamic;
    }
}
