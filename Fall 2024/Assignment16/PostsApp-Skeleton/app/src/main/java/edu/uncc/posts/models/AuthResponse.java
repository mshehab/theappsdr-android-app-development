package edu.uncc.posts.models;

import java.io.Serializable;

public class AuthResponse implements Serializable {
    String user_id, user_fullname, token;

    /*

    {
    "status": "ok",
    "token": "wcUJRckNsODlsbkgyZUJS",
    "user_id": 1,
    "user_fullname": "Alice Smith"
}
     */
}
