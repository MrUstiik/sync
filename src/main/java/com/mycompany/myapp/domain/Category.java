package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * comment 1
 */
@ApiModel(description = "comment 1")
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(min = 4, max = 4)
    @Column(name = "mcc", length = 4)
    private String mcc;

    @Column(name = "parent_category_id")
    private String parentCategoryId;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "default_order_id")
    private Integer defaultOrderId;

    @Column(name = "added_date")
    private ZonedDateTime addedDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @Column(name = "regions")
    private String regions;

    @Column(name = "tags")
    private String tags;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public Category uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMcc() {
        return mcc;
    }

    public Category mcc(String mcc) {
        this.mcc = mcc;
        return this;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public Category parentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
        return this;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Category enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Category iconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getDefaultOrderId() {
        return defaultOrderId;
    }

    public Category defaultOrderId(Integer defaultOrderId) {
        this.defaultOrderId = defaultOrderId;
        return this;
    }

    public void setDefaultOrderId(Integer defaultOrderId) {
        this.defaultOrderId = defaultOrderId;
    }

    public ZonedDateTime getAddedDate() {
        return addedDate;
    }

    public Category addedDate(ZonedDateTime addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    public void setAddedDate(ZonedDateTime addedDate) {
        this.addedDate = addedDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public Category updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getRegions() {
        return regions;
    }

    public Category regions(String regions) {
        this.regions = regions;
        return this;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getTags() {
        return tags;
    }

    public Category tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", mcc='" + getMcc() + "'" +
            ", parentCategoryId='" + getParentCategoryId() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", iconUrl='" + getIconUrl() + "'" +
            ", defaultOrderId=" + getDefaultOrderId() +
            ", addedDate='" + getAddedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", regions='" + getRegions() + "'" +
            ", tags='" + getTags() + "'" +
            "}";
    }
}
