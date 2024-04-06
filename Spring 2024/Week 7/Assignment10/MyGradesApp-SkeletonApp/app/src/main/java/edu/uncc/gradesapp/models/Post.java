package edu.uncc.gradesapp.models;

import com.google.firebase.Timestamp;

public class Post {
    public String createdByName, docId, createdByUid, postText;
    Timestamp createdAt;

    public Post() {
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public String getDocId() {
        return docId;
    }

    public String getCreatedByUid() {
        return createdByUid;
    }

    public String getPostText() {
        return postText;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public void setCreatedByUid(String createdByUid) {
        this.createdByUid = createdByUid;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }
}
