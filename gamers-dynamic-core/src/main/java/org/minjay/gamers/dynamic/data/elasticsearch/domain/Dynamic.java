package org.minjay.gamers.dynamic.data.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Document(indexName = "dynamic", type = "dynamic")
public class Dynamic implements Serializable {
    private static final long serialVersionUID = 726530161192474342L;

    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;
    @Field(type = FieldType.Integer)
    private int replyCount;
    @Field(type = FieldType.Integer)
    private int likeCount;
    @Field(type = FieldType.Date)
    private Date createDate;

    @Field(type = FieldType.Keyword)
    private Long userId;
    @Field(type = FieldType.Text)
    private String username;
    @Field(type = FieldType.Ip)
    private String ip;

    @GeoPointField
    private GeoPoint location;

    @Id
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

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
