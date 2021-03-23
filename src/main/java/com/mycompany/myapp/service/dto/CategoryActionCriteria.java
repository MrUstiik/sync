package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.mycompany.myapp.domain.enumeration.ActionType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CategoryAction} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CategoryActionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /category-actions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoryActionCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ActionType
     */
    public static class ActionTypeFilter extends Filter<ActionType> {

        public ActionTypeFilter() {
        }

        public ActionTypeFilter(ActionTypeFilter filter) {
            super(filter);
        }

        @Override
        public ActionTypeFilter copy() {
            return new ActionTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uuid;

    private StringFilter name;

    private StringFilter mcc;

    private StringFilter categoryId;

    private BooleanFilter enabled;

    private StringFilter iconUrl;

    private IntegerFilter defaultOrderId;

    private ZonedDateTimeFilter addedDate;

    private ZonedDateTimeFilter updatedDate;

    private StringFilter regions;

    private StringFilter tags;

    private StringFilter processId;

    private StringFilter source;

    private ActionTypeFilter actionType;

    private LongFilter categoryIdId;

    public CategoryActionCriteria() {
    }

    public CategoryActionCriteria(CategoryActionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.mcc = other.mcc == null ? null : other.mcc.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.iconUrl = other.iconUrl == null ? null : other.iconUrl.copy();
        this.defaultOrderId = other.defaultOrderId == null ? null : other.defaultOrderId.copy();
        this.addedDate = other.addedDate == null ? null : other.addedDate.copy();
        this.updatedDate = other.updatedDate == null ? null : other.updatedDate.copy();
        this.regions = other.regions == null ? null : other.regions.copy();
        this.tags = other.tags == null ? null : other.tags.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.actionType = other.actionType == null ? null : other.actionType.copy();
        this.categoryIdId = other.categoryIdId == null ? null : other.categoryIdId.copy();
    }

    @Override
    public CategoryActionCriteria copy() {
        return new CategoryActionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUuid() {
        return uuid;
    }

    public void setUuid(StringFilter uuid) {
        this.uuid = uuid;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getMcc() {
        return mcc;
    }

    public void setMcc(StringFilter mcc) {
        this.mcc = mcc;
    }

    public StringFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(StringFilter categoryId) {
        this.categoryId = categoryId;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
    }

    public StringFilter getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(StringFilter iconUrl) {
        this.iconUrl = iconUrl;
    }

    public IntegerFilter getDefaultOrderId() {
        return defaultOrderId;
    }

    public void setDefaultOrderId(IntegerFilter defaultOrderId) {
        this.defaultOrderId = defaultOrderId;
    }

    public ZonedDateTimeFilter getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(ZonedDateTimeFilter addedDate) {
        this.addedDate = addedDate;
    }

    public ZonedDateTimeFilter getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTimeFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public StringFilter getRegions() {
        return regions;
    }

    public void setRegions(StringFilter regions) {
        this.regions = regions;
    }

    public StringFilter getTags() {
        return tags;
    }

    public void setTags(StringFilter tags) {
        this.tags = tags;
    }

    public StringFilter getProcessId() {
        return processId;
    }

    public void setProcessId(StringFilter processId) {
        this.processId = processId;
    }

    public StringFilter getSource() {
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public ActionTypeFilter getActionType() {
        return actionType;
    }

    public void setActionType(ActionTypeFilter actionType) {
        this.actionType = actionType;
    }

    public LongFilter getCategoryIdId() {
        return categoryIdId;
    }

    public void setCategoryIdId(LongFilter categoryIdId) {
        this.categoryIdId = categoryIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoryActionCriteria that = (CategoryActionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(name, that.name) &&
            Objects.equals(mcc, that.mcc) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(iconUrl, that.iconUrl) &&
            Objects.equals(defaultOrderId, that.defaultOrderId) &&
            Objects.equals(addedDate, that.addedDate) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(regions, that.regions) &&
            Objects.equals(tags, that.tags) &&
            Objects.equals(processId, that.processId) &&
            Objects.equals(source, that.source) &&
            Objects.equals(actionType, that.actionType) &&
            Objects.equals(categoryIdId, that.categoryIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uuid,
        name,
        mcc,
        categoryId,
        enabled,
        iconUrl,
        defaultOrderId,
        addedDate,
        updatedDate,
        regions,
        tags,
        processId,
        source,
        actionType,
        categoryIdId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryActionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (mcc != null ? "mcc=" + mcc + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (iconUrl != null ? "iconUrl=" + iconUrl + ", " : "") +
                (defaultOrderId != null ? "defaultOrderId=" + defaultOrderId + ", " : "") +
                (addedDate != null ? "addedDate=" + addedDate + ", " : "") +
                (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "") +
                (regions != null ? "regions=" + regions + ", " : "") +
                (tags != null ? "tags=" + tags + ", " : "") +
                (processId != null ? "processId=" + processId + ", " : "") +
                (source != null ? "source=" + source + ", " : "") +
                (actionType != null ? "actionType=" + actionType + ", " : "") +
                (categoryIdId != null ? "categoryIdId=" + categoryIdId + ", " : "") +
            "}";
    }

}
