package com.example.miniapp_restaurant.Models.Server.Command;

import com.example.miniapp_restaurant.Models.Server.Object.ObjectId;

public class TargetObject {


    private ObjectId objectId;

    public TargetObject(String superAppName, String id) {
        objectId = new ObjectId(superAppName, id);
    }

    public TargetObject() {
    }


    public TargetObject(ObjectId objectId) {
        super();
        this.objectId = objectId;
    }


    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "TargetObject [objectId=" + objectId + "]";
    }


}
