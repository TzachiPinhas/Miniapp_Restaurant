package com.example.miniapp_restaurant.Models;

import java.util.Date;
import java.util.Map;

public class ObjectBoundary {

    private ObjectId objectId;
    private String type;
    private String alias;
    private Location location;
    private Boolean active;
    private Date creationTimestamp;
    private CreatedBy createdBy;
    private Map<String, Object> objectDetails;

    public ObjectBoundary() {
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public ObjectBoundary setObjectId(ObjectId objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getType() {
        return type;
    }

    public ObjectBoundary setType(String type) {
        this.type = type;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public ObjectBoundary setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public ObjectBoundary setLocation(Location location) {
        this.location = location;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public ObjectBoundary setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public ObjectBoundary setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
        return this;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public ObjectBoundary setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Map<String, Object> getObjectDetails() {
        return objectDetails;
    }

    public ObjectBoundary setObjectDetails(Map<String, Object> objectDetails) {
        this.objectDetails = objectDetails;
        return this;
    }
}
