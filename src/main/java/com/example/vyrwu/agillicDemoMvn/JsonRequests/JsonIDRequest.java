package com.example.vyrwu.agillicDemoMvn.JsonRequests;

import javax.persistence.Entity;
import javax.persistence.Id;

// Json Request Entity for BookmarkController.remove()
@Entity
public class JsonIDRequest {

    @Id
    public long id;

    public long[] ids;

    public JsonIDRequest() {

    }

    public JsonIDRequest(long[] ids) {
        this.ids = ids;
    }

    public long getId() {
        return id;
    }

    public long[] getIds() {
        return ids;
    }


}
