package org.txstate.edu.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jyoti on 2/7/18.
 */

/* when object is made it does not change the state of the object*/
public class BaseModel implements Serializable {

    protected String createdBy;
    protected String updateBy;
    protected Date createdAt;
    protected Date updatedAt;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
