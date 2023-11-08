package edu.uncc.hw06.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Forum implements Serializable {
    String description, docId, ownerId, ownerName, title;
    Timestamp createdAt;
    ArrayList<String> likes = new ArrayList<>();

    public Forum() {
    }

    public ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public HashSet<String> getLikesSet() {
        return likesSet;
    }

    public void setLikesSet(HashSet<String> likesSet) {
        this.likesSet = likesSet;
    }

    HashSet<String> likesSet = null;
    public boolean isLiked(String uid){
        if(likesSet == null){
            likesSet = new HashSet<>(likes);
        }
        return likesSet.contains(uid);
    }


    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
