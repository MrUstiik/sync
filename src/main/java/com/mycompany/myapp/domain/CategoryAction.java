package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.ActionType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * comment 3
 */
@Entity
@Table(name = "action")
public class CategoryAction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(min = 4, max = 4)
    @Column(name = "mcc", length = 4)
    private String mcc;

    @Column(name = "category_id")
    private UUID categoryId;

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

    @Column(name = "process_id")
    private String processId;

    @Column(name = "source")
    private String source;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type")
    private ActionType actionType;

    @ManyToOne
    @JsonIgnoreProperties(value = "categoryActions", allowSetters = true)
    private Category categoryId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public CategoryAction uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public CategoryAction name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMcc() {
        return mcc;
    }

    public CategoryAction mcc(String mcc) {
        this.mcc = mcc;
        return this;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public CategoryAction categoryId(UUID categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public CategoryAction enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public CategoryAction iconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getDefaultOrderId() {
        return defaultOrderId;
    }

    public CategoryAction defaultOrderId(Integer defaultOrderId) {
        this.defaultOrderId = defaultOrderId;
        return this;
    }

    public void setDefaultOrderId(Integer defaultOrderId) {
        this.defaultOrderId = defaultOrderId;
    }

    public ZonedDateTime getAddedDate() {
        return addedDate;
    }

    public CategoryAction addedDate(ZonedDateTime addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    public void setAddedDate(ZonedDateTime addedDate) {
        this.addedDate = addedDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public CategoryAction updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getRegions() {
        return regions;
    }

    public CategoryAction regions(String regions) {
        this.regions = regions;
        return this;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getTags() {
        return tags;
    }

    public CategoryAction tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getProcessId() {
        return processId;
    }

    public CategoryAction processId(String processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getSource() {
        return source;
    }

    public CategoryAction source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public CategoryAction actionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public CategoryAction categoryId(Category category) {
        this.categoryId = category;
        return this;
    }

    public void setCategoryId(Category category) {
        this.categoryId = category;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryAction)) {
            return false;
        }
        return id != null && id.equals(((CategoryAction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryAction{" +
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
            "}";
    }
}
