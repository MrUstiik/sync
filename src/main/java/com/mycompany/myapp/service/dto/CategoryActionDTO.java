package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.ActionType;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CategoryAction} entity.
 */
@ApiModel(description = "comment 3")
public class CategoryActionDTO implements Serializable {
    private Long id;

    @NotNull
    private String uuid;

    @NotNull
    private String name;

    @Size(min = 4, max = 4)
    private String mcc;

    private String categoryId;

    private Boolean enabled;

    private String iconUrl;

    private Integer defaultOrderId;

    private ZonedDateTime addedDate;

    private ZonedDateTime updatedDate;

    private String regions;

    private String tags;

    private String processId;

    private String source;

    private ActionType actionType;

    private Long categoryIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getDefaultOrderId() {
        return defaultOrderId;
    }

    public void setDefaultOrderId(Integer defaultOrderId) {
        this.defaultOrderId = defaultOrderId;
    }

    public ZonedDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(ZonedDateTime addedDate) {
        this.addedDate = addedDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Long getCategoryIdId() {
        return categoryIdId;
    }

    public void setCategoryIdId(Long categoryId) {
        this.categoryIdId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryActionDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoryActionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryActionDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", mcc='" + getMcc() + "'" +
            ", categoryId='" + getCategoryId() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", iconUrl='" + getIconUrl() + "'" +
            ", defaultOrderId=" + getDefaultOrderId() +
            ", addedDate='" + getAddedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", regions='" + getRegions() + "'" +
            ", tags='" + getTags() + "'" +
            ", processId='" + getProcessId() + "'" +
            ", source='" + getSource() + "'" +
            ", actionType='" + getActionType() + "'" +
            ", categoryIdId=" + getCategoryIdId() +
            "}";
    }
}
