package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.SyncApp;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.CategoryAction;
import com.mycompany.myapp.domain.enumeration.ActionType;
import com.mycompany.myapp.repository.CategoryActionRepository;
import com.mycompany.myapp.service.CategoryActionQueryService;
import com.mycompany.myapp.service.CategoryActionService;
import com.mycompany.myapp.service.dto.CategoryActionCriteria;
import com.mycompany.myapp.service.dto.CategoryActionDTO;
import com.mycompany.myapp.service.mapper.CategoryActionMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CategoryActionResource} REST controller.
 */
@SpringBootTest(classes = SyncApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategoryActionResourceIT {
    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MCC = "AAAA";
    private static final String UPDATED_MCC = "BBBB";

    private static final String DEFAULT_CATEGORY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_ICON_URL = "AAAAAAAAAA";
    private static final String UPDATED_ICON_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_DEFAULT_ORDER_ID = 2;
    private static final Integer SMALLER_DEFAULT_ORDER_ID = 1 - 1;

    private static final ZonedDateTime DEFAULT_ADDED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADDED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ADDED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_REGIONS = "AAAAAAAAAA";
    private static final String UPDATED_REGIONS = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final ActionType DEFAULT_ACTION_TYPE = ActionType.BILLPAY;
    private static final ActionType UPDATED_ACTION_TYPE = ActionType.BILLPAY;

    @Autowired
    private CategoryActionRepository categoryActionRepository;

    @Autowired
    private CategoryActionMapper categoryActionMapper;

    @Autowired
    private CategoryActionService categoryActionService;

    @Autowired
    private CategoryActionQueryService categoryActionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryActionMockMvc;

    private CategoryAction categoryAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryAction createEntity(EntityManager em) {
        CategoryAction categoryAction = new CategoryAction()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .mcc(DEFAULT_MCC)
            .categoryId(DEFAULT_CATEGORY_ID)
            .enabled(DEFAULT_ENABLED)
            .iconUrl(DEFAULT_ICON_URL)
            .defaultOrderId(DEFAULT_DEFAULT_ORDER_ID)
            .addedDate(DEFAULT_ADDED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .regions(DEFAULT_REGIONS)
            .tags(DEFAULT_TAGS)
            .processId(DEFAULT_PROCESS_ID)
            .source(DEFAULT_SOURCE)
            .actionType(DEFAULT_ACTION_TYPE);
        return categoryAction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryAction createUpdatedEntity(EntityManager em) {
        CategoryAction categoryAction = new CategoryAction()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .mcc(UPDATED_MCC)
            .categoryId(UPDATED_CATEGORY_ID)
            .enabled(UPDATED_ENABLED)
            .iconUrl(UPDATED_ICON_URL)
            .defaultOrderId(UPDATED_DEFAULT_ORDER_ID)
            .addedDate(UPDATED_ADDED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .regions(UPDATED_REGIONS)
            .tags(UPDATED_TAGS)
            .processId(UPDATED_PROCESS_ID)
            .source(UPDATED_SOURCE)
            .actionType(UPDATED_ACTION_TYPE);
        return categoryAction;
    }

    @BeforeEach
    public void initTest() {
        categoryAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryAction() throws Exception {
        int databaseSizeBeforeCreate = categoryActionRepository.findAll().size();
        // Create the CategoryAction
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);
        restCategoryActionMockMvc
            .perform(
                post("/api/category-actions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryAction testCategoryAction = categoryActionList.get(categoryActionList.size() - 1);
        assertThat(testCategoryAction.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testCategoryAction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategoryAction.getMcc()).isEqualTo(DEFAULT_MCC);
        assertThat(testCategoryAction.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategoryAction.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testCategoryAction.getIconUrl()).isEqualTo(DEFAULT_ICON_URL);
        assertThat(testCategoryAction.getDefaultOrderId()).isEqualTo(DEFAULT_DEFAULT_ORDER_ID);
        assertThat(testCategoryAction.getAddedDate()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testCategoryAction.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testCategoryAction.getRegions()).isEqualTo(DEFAULT_REGIONS);
        assertThat(testCategoryAction.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testCategoryAction.getProcessId()).isEqualTo(DEFAULT_PROCESS_ID);
        assertThat(testCategoryAction.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testCategoryAction.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
    }

    @Test
    @Transactional
    public void createCategoryActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryActionRepository.findAll().size();

        // Create the CategoryAction with an existing ID
        categoryAction.setId(1L);
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryActionMockMvc
            .perform(
                post("/api/category-actions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().size();
        // set the field null
        categoryAction.setUuid(null);

        // Create the CategoryAction, which fails.
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);

        restCategoryActionMockMvc
            .perform(
                post("/api/category-actions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().size();
        // set the field null
        categoryAction.setName(null);

        // Create the CategoryAction, which fails.
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);

        restCategoryActionMockMvc
            .perform(
                post("/api/category-actions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryActions() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList
        restCategoryActionMockMvc
            .perform(get("/api/category-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mcc").value(hasItem(DEFAULT_MCC)))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL)))
            .andExpect(jsonPath("$.[*].defaultOrderId").value(hasItem(DEFAULT_DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].addedDate").value(hasItem(sameInstant(DEFAULT_ADDED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].regions").value(hasItem(DEFAULT_REGIONS)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].processId").value(hasItem(DEFAULT_PROCESS_ID)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCategoryAction() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get the categoryAction
        restCategoryActionMockMvc
            .perform(get("/api/category-actions/{id}", categoryAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryAction.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.mcc").value(DEFAULT_MCC))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.iconUrl").value(DEFAULT_ICON_URL))
            .andExpect(jsonPath("$.defaultOrderId").value(DEFAULT_DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.addedDate").value(sameInstant(DEFAULT_ADDED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)))
            .andExpect(jsonPath("$.regions").value(DEFAULT_REGIONS))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS))
            .andExpect(jsonPath("$.processId").value(DEFAULT_PROCESS_ID))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getCategoryActionsByIdFiltering() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        Long id = categoryAction.getId();

        defaultCategoryActionShouldBeFound("id.equals=" + id);
        defaultCategoryActionShouldNotBeFound("id.notEquals=" + id);

        defaultCategoryActionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoryActionShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoryActionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoryActionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where uuid equals to DEFAULT_UUID
        defaultCategoryActionShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the categoryActionList where uuid equals to UPDATED_UUID
        defaultCategoryActionShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUuidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where uuid not equals to DEFAULT_UUID
        defaultCategoryActionShouldNotBeFound("uuid.notEquals=" + DEFAULT_UUID);

        // Get all the categoryActionList where uuid not equals to UPDATED_UUID
        defaultCategoryActionShouldBeFound("uuid.notEquals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultCategoryActionShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the categoryActionList where uuid equals to UPDATED_UUID
        defaultCategoryActionShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where uuid is not null
        defaultCategoryActionShouldBeFound("uuid.specified=true");

        // Get all the categoryActionList where uuid is null
        defaultCategoryActionShouldNotBeFound("uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUuidContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where uuid contains DEFAULT_UUID
        defaultCategoryActionShouldBeFound("uuid.contains=" + DEFAULT_UUID);

        // Get all the categoryActionList where uuid contains UPDATED_UUID
        defaultCategoryActionShouldNotBeFound("uuid.contains=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUuidNotContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where uuid does not contain DEFAULT_UUID
        defaultCategoryActionShouldNotBeFound("uuid.doesNotContain=" + DEFAULT_UUID);

        // Get all the categoryActionList where uuid does not contain UPDATED_UUID
        defaultCategoryActionShouldBeFound("uuid.doesNotContain=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where name equals to DEFAULT_NAME
        defaultCategoryActionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the categoryActionList where name equals to UPDATED_NAME
        defaultCategoryActionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where name not equals to DEFAULT_NAME
        defaultCategoryActionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the categoryActionList where name not equals to UPDATED_NAME
        defaultCategoryActionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCategoryActionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the categoryActionList where name equals to UPDATED_NAME
        defaultCategoryActionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where name is not null
        defaultCategoryActionShouldBeFound("name.specified=true");

        // Get all the categoryActionList where name is null
        defaultCategoryActionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByNameContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where name contains DEFAULT_NAME
        defaultCategoryActionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the categoryActionList where name contains UPDATED_NAME
        defaultCategoryActionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where name does not contain DEFAULT_NAME
        defaultCategoryActionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the categoryActionList where name does not contain UPDATED_NAME
        defaultCategoryActionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByMccIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where mcc equals to DEFAULT_MCC
        defaultCategoryActionShouldBeFound("mcc.equals=" + DEFAULT_MCC);

        // Get all the categoryActionList where mcc equals to UPDATED_MCC
        defaultCategoryActionShouldNotBeFound("mcc.equals=" + UPDATED_MCC);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByMccIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where mcc not equals to DEFAULT_MCC
        defaultCategoryActionShouldNotBeFound("mcc.notEquals=" + DEFAULT_MCC);

        // Get all the categoryActionList where mcc not equals to UPDATED_MCC
        defaultCategoryActionShouldBeFound("mcc.notEquals=" + UPDATED_MCC);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByMccIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where mcc in DEFAULT_MCC or UPDATED_MCC
        defaultCategoryActionShouldBeFound("mcc.in=" + DEFAULT_MCC + "," + UPDATED_MCC);

        // Get all the categoryActionList where mcc equals to UPDATED_MCC
        defaultCategoryActionShouldNotBeFound("mcc.in=" + UPDATED_MCC);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByMccIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where mcc is not null
        defaultCategoryActionShouldBeFound("mcc.specified=true");

        // Get all the categoryActionList where mcc is null
        defaultCategoryActionShouldNotBeFound("mcc.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByMccContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where mcc contains DEFAULT_MCC
        defaultCategoryActionShouldBeFound("mcc.contains=" + DEFAULT_MCC);

        // Get all the categoryActionList where mcc contains UPDATED_MCC
        defaultCategoryActionShouldNotBeFound("mcc.contains=" + UPDATED_MCC);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByMccNotContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where mcc does not contain DEFAULT_MCC
        defaultCategoryActionShouldNotBeFound("mcc.doesNotContain=" + DEFAULT_MCC);

        // Get all the categoryActionList where mcc does not contain UPDATED_MCC
        defaultCategoryActionShouldBeFound("mcc.doesNotContain=" + UPDATED_MCC);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByCategoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where categoryId equals to DEFAULT_CATEGORY_ID
        defaultCategoryActionShouldBeFound("categoryId.equals=" + DEFAULT_CATEGORY_ID);

        // Get all the categoryActionList where categoryId equals to UPDATED_CATEGORY_ID
        defaultCategoryActionShouldNotBeFound("categoryId.equals=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByCategoryIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where categoryId not equals to DEFAULT_CATEGORY_ID
        defaultCategoryActionShouldNotBeFound("categoryId.notEquals=" + DEFAULT_CATEGORY_ID);

        // Get all the categoryActionList where categoryId not equals to UPDATED_CATEGORY_ID
        defaultCategoryActionShouldBeFound("categoryId.notEquals=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByCategoryIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where categoryId in DEFAULT_CATEGORY_ID or UPDATED_CATEGORY_ID
        defaultCategoryActionShouldBeFound("categoryId.in=" + DEFAULT_CATEGORY_ID + "," + UPDATED_CATEGORY_ID);

        // Get all the categoryActionList where categoryId equals to UPDATED_CATEGORY_ID
        defaultCategoryActionShouldNotBeFound("categoryId.in=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByCategoryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where categoryId is not null
        defaultCategoryActionShouldBeFound("categoryId.specified=true");

        // Get all the categoryActionList where categoryId is null
        defaultCategoryActionShouldNotBeFound("categoryId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByCategoryIdContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where categoryId contains DEFAULT_CATEGORY_ID
        defaultCategoryActionShouldBeFound("categoryId.contains=" + DEFAULT_CATEGORY_ID);

        // Get all the categoryActionList where categoryId contains UPDATED_CATEGORY_ID
        defaultCategoryActionShouldNotBeFound("categoryId.contains=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByCategoryIdNotContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where categoryId does not contain DEFAULT_CATEGORY_ID
        defaultCategoryActionShouldNotBeFound("categoryId.doesNotContain=" + DEFAULT_CATEGORY_ID);

        // Get all the categoryActionList where categoryId does not contain UPDATED_CATEGORY_ID
        defaultCategoryActionShouldBeFound("categoryId.doesNotContain=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where enabled equals to DEFAULT_ENABLED
        defaultCategoryActionShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the categoryActionList where enabled equals to UPDATED_ENABLED
        defaultCategoryActionShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where enabled not equals to DEFAULT_ENABLED
        defaultCategoryActionShouldNotBeFound("enabled.notEquals=" + DEFAULT_ENABLED);

        // Get all the categoryActionList where enabled not equals to UPDATED_ENABLED
        defaultCategoryActionShouldBeFound("enabled.notEquals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultCategoryActionShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the categoryActionList where enabled equals to UPDATED_ENABLED
        defaultCategoryActionShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where enabled is not null
        defaultCategoryActionShouldBeFound("enabled.specified=true");

        // Get all the categoryActionList where enabled is null
        defaultCategoryActionShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByIconUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where iconUrl equals to DEFAULT_ICON_URL
        defaultCategoryActionShouldBeFound("iconUrl.equals=" + DEFAULT_ICON_URL);

        // Get all the categoryActionList where iconUrl equals to UPDATED_ICON_URL
        defaultCategoryActionShouldNotBeFound("iconUrl.equals=" + UPDATED_ICON_URL);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByIconUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where iconUrl not equals to DEFAULT_ICON_URL
        defaultCategoryActionShouldNotBeFound("iconUrl.notEquals=" + DEFAULT_ICON_URL);

        // Get all the categoryActionList where iconUrl not equals to UPDATED_ICON_URL
        defaultCategoryActionShouldBeFound("iconUrl.notEquals=" + UPDATED_ICON_URL);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByIconUrlIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where iconUrl in DEFAULT_ICON_URL or UPDATED_ICON_URL
        defaultCategoryActionShouldBeFound("iconUrl.in=" + DEFAULT_ICON_URL + "," + UPDATED_ICON_URL);

        // Get all the categoryActionList where iconUrl equals to UPDATED_ICON_URL
        defaultCategoryActionShouldNotBeFound("iconUrl.in=" + UPDATED_ICON_URL);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByIconUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where iconUrl is not null
        defaultCategoryActionShouldBeFound("iconUrl.specified=true");

        // Get all the categoryActionList where iconUrl is null
        defaultCategoryActionShouldNotBeFound("iconUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByIconUrlContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where iconUrl contains DEFAULT_ICON_URL
        defaultCategoryActionShouldBeFound("iconUrl.contains=" + DEFAULT_ICON_URL);

        // Get all the categoryActionList where iconUrl contains UPDATED_ICON_URL
        defaultCategoryActionShouldNotBeFound("iconUrl.contains=" + UPDATED_ICON_URL);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByIconUrlNotContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where iconUrl does not contain DEFAULT_ICON_URL
        defaultCategoryActionShouldNotBeFound("iconUrl.doesNotContain=" + DEFAULT_ICON_URL);

        // Get all the categoryActionList where iconUrl does not contain UPDATED_ICON_URL
        defaultCategoryActionShouldBeFound("iconUrl.doesNotContain=" + UPDATED_ICON_URL);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByDefaultOrderIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where defaultOrderId equals to DEFAULT_DEFAULT_ORDER_ID
        defaultCategoryActionShouldBeFound("defaultOrderId.equals=" + DEFAULT_DEFAULT_ORDER_ID);

        // Get all the categoryActionList where defaultOrderId equals to UPDATED_DEFAULT_ORDER_ID
        defaultCategoryActionShouldNotBeFound("defaultOrderId.equals=" + UPDATED_DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByDefaultOrderIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where defaultOrderId not equals to DEFAULT_DEFAULT_ORDER_ID
        defaultCategoryActionShouldNotBeFound("defaultOrderId.notEquals=" + DEFAULT_DEFAULT_ORDER_ID);

        // Get all the categoryActionList where defaultOrderId not equals to UPDATED_DEFAULT_ORDER_ID
        defaultCategoryActionShouldBeFound("defaultOrderId.notEquals=" + UPDATED_DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByDefaultOrderIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where defaultOrderId in DEFAULT_DEFAULT_ORDER_ID or UPDATED_DEFAULT_ORDER_ID
        defaultCategoryActionShouldBeFound("defaultOrderId.in=" + DEFAULT_DEFAULT_ORDER_ID + "," + UPDATED_DEFAULT_ORDER_ID);

        // Get all the categoryActionList where defaultOrderId equals to UPDATED_DEFAULT_ORDER_ID
        defaultCategoryActionShouldNotBeFound("defaultOrderId.in=" + UPDATED_DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByDefaultOrderIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where defaultOrderId is not null
        defaultCategoryActionShouldBeFound("defaultOrderId.specified=true");

        // Get all the categoryActionList where defaultOrderId is null
        defaultCategoryActionShouldNotBeFound("defaultOrderId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByDefaultOrderIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where defaultOrderId is greater than or equal to DEFAULT_DEFAULT_ORDER_ID
        defaultCategoryActionShouldBeFound("defaultOrderId.greaterThanOrEqual=" + DEFAULT_DEFAULT_ORDER_ID);

        // Get all the categoryActionList where defaultOrderId is greater than or equal to UPDATED_DEFAULT_ORDER_ID
        defaultCategoryActionShouldNotBeFound("defaultOrderId.greaterThanOrEqual=" + UPDATED_DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByDefaultOrderIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where defaultOrderId is less than or equal to DEFAULT_DEFAULT_ORDER_ID
        defaultCategoryActionShouldBeFound("defaultOrderId.lessThanOrEqual=" + DEFAULT_DEFAULT_ORDER_ID);

        // Get all the categoryActionList where defaultOrderId is less than or equal to SMALLER_DEFAULT_ORDER_ID
        defaultCategoryActionShouldNotBeFound("defaultOrderId.lessThanOrEqual=" + SMALLER_DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByDefaultOrderIdIsLessThanSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where defaultOrderId is less than DEFAULT_DEFAULT_ORDER_ID
        defaultCategoryActionShouldNotBeFound("defaultOrderId.lessThan=" + DEFAULT_DEFAULT_ORDER_ID);

        // Get all the categoryActionList where defaultOrderId is less than UPDATED_DEFAULT_ORDER_ID
        defaultCategoryActionShouldBeFound("defaultOrderId.lessThan=" + UPDATED_DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByDefaultOrderIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where defaultOrderId is greater than DEFAULT_DEFAULT_ORDER_ID
        defaultCategoryActionShouldNotBeFound("defaultOrderId.greaterThan=" + DEFAULT_DEFAULT_ORDER_ID);

        // Get all the categoryActionList where defaultOrderId is greater than SMALLER_DEFAULT_ORDER_ID
        defaultCategoryActionShouldBeFound("defaultOrderId.greaterThan=" + SMALLER_DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByAddedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where addedDate equals to DEFAULT_ADDED_DATE
        defaultCategoryActionShouldBeFound("addedDate.equals=" + DEFAULT_ADDED_DATE);

        // Get all the categoryActionList where addedDate equals to UPDATED_ADDED_DATE
        defaultCategoryActionShouldNotBeFound("addedDate.equals=" + UPDATED_ADDED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByAddedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where addedDate not equals to DEFAULT_ADDED_DATE
        defaultCategoryActionShouldNotBeFound("addedDate.notEquals=" + DEFAULT_ADDED_DATE);

        // Get all the categoryActionList where addedDate not equals to UPDATED_ADDED_DATE
        defaultCategoryActionShouldBeFound("addedDate.notEquals=" + UPDATED_ADDED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByAddedDateIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where addedDate in DEFAULT_ADDED_DATE or UPDATED_ADDED_DATE
        defaultCategoryActionShouldBeFound("addedDate.in=" + DEFAULT_ADDED_DATE + "," + UPDATED_ADDED_DATE);

        // Get all the categoryActionList where addedDate equals to UPDATED_ADDED_DATE
        defaultCategoryActionShouldNotBeFound("addedDate.in=" + UPDATED_ADDED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByAddedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where addedDate is not null
        defaultCategoryActionShouldBeFound("addedDate.specified=true");

        // Get all the categoryActionList where addedDate is null
        defaultCategoryActionShouldNotBeFound("addedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByAddedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where addedDate is greater than or equal to DEFAULT_ADDED_DATE
        defaultCategoryActionShouldBeFound("addedDate.greaterThanOrEqual=" + DEFAULT_ADDED_DATE);

        // Get all the categoryActionList where addedDate is greater than or equal to UPDATED_ADDED_DATE
        defaultCategoryActionShouldNotBeFound("addedDate.greaterThanOrEqual=" + UPDATED_ADDED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByAddedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where addedDate is less than or equal to DEFAULT_ADDED_DATE
        defaultCategoryActionShouldBeFound("addedDate.lessThanOrEqual=" + DEFAULT_ADDED_DATE);

        // Get all the categoryActionList where addedDate is less than or equal to SMALLER_ADDED_DATE
        defaultCategoryActionShouldNotBeFound("addedDate.lessThanOrEqual=" + SMALLER_ADDED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByAddedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where addedDate is less than DEFAULT_ADDED_DATE
        defaultCategoryActionShouldNotBeFound("addedDate.lessThan=" + DEFAULT_ADDED_DATE);

        // Get all the categoryActionList where addedDate is less than UPDATED_ADDED_DATE
        defaultCategoryActionShouldBeFound("addedDate.lessThan=" + UPDATED_ADDED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByAddedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where addedDate is greater than DEFAULT_ADDED_DATE
        defaultCategoryActionShouldNotBeFound("addedDate.greaterThan=" + DEFAULT_ADDED_DATE);

        // Get all the categoryActionList where addedDate is greater than SMALLER_ADDED_DATE
        defaultCategoryActionShouldBeFound("addedDate.greaterThan=" + SMALLER_ADDED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where updatedDate equals to DEFAULT_UPDATED_DATE
        defaultCategoryActionShouldBeFound("updatedDate.equals=" + DEFAULT_UPDATED_DATE);

        // Get all the categoryActionList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultCategoryActionShouldNotBeFound("updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where updatedDate not equals to DEFAULT_UPDATED_DATE
        defaultCategoryActionShouldNotBeFound("updatedDate.notEquals=" + DEFAULT_UPDATED_DATE);

        // Get all the categoryActionList where updatedDate not equals to UPDATED_UPDATED_DATE
        defaultCategoryActionShouldBeFound("updatedDate.notEquals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where updatedDate in DEFAULT_UPDATED_DATE or UPDATED_UPDATED_DATE
        defaultCategoryActionShouldBeFound("updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE);

        // Get all the categoryActionList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultCategoryActionShouldNotBeFound("updatedDate.in=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where updatedDate is not null
        defaultCategoryActionShouldBeFound("updatedDate.specified=true");

        // Get all the categoryActionList where updatedDate is null
        defaultCategoryActionShouldNotBeFound("updatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where updatedDate is greater than or equal to DEFAULT_UPDATED_DATE
        defaultCategoryActionShouldBeFound("updatedDate.greaterThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the categoryActionList where updatedDate is greater than or equal to UPDATED_UPDATED_DATE
        defaultCategoryActionShouldNotBeFound("updatedDate.greaterThanOrEqual=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where updatedDate is less than or equal to DEFAULT_UPDATED_DATE
        defaultCategoryActionShouldBeFound("updatedDate.lessThanOrEqual=" + DEFAULT_UPDATED_DATE);

        // Get all the categoryActionList where updatedDate is less than or equal to SMALLER_UPDATED_DATE
        defaultCategoryActionShouldNotBeFound("updatedDate.lessThanOrEqual=" + SMALLER_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where updatedDate is less than DEFAULT_UPDATED_DATE
        defaultCategoryActionShouldNotBeFound("updatedDate.lessThan=" + DEFAULT_UPDATED_DATE);

        // Get all the categoryActionList where updatedDate is less than UPDATED_UPDATED_DATE
        defaultCategoryActionShouldBeFound("updatedDate.lessThan=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where updatedDate is greater than DEFAULT_UPDATED_DATE
        defaultCategoryActionShouldNotBeFound("updatedDate.greaterThan=" + DEFAULT_UPDATED_DATE);

        // Get all the categoryActionList where updatedDate is greater than SMALLER_UPDATED_DATE
        defaultCategoryActionShouldBeFound("updatedDate.greaterThan=" + SMALLER_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByRegionsIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where regions equals to DEFAULT_REGIONS
        defaultCategoryActionShouldBeFound("regions.equals=" + DEFAULT_REGIONS);

        // Get all the categoryActionList where regions equals to UPDATED_REGIONS
        defaultCategoryActionShouldNotBeFound("regions.equals=" + UPDATED_REGIONS);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByRegionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where regions not equals to DEFAULT_REGIONS
        defaultCategoryActionShouldNotBeFound("regions.notEquals=" + DEFAULT_REGIONS);

        // Get all the categoryActionList where regions not equals to UPDATED_REGIONS
        defaultCategoryActionShouldBeFound("regions.notEquals=" + UPDATED_REGIONS);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByRegionsIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where regions in DEFAULT_REGIONS or UPDATED_REGIONS
        defaultCategoryActionShouldBeFound("regions.in=" + DEFAULT_REGIONS + "," + UPDATED_REGIONS);

        // Get all the categoryActionList where regions equals to UPDATED_REGIONS
        defaultCategoryActionShouldNotBeFound("regions.in=" + UPDATED_REGIONS);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByRegionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where regions is not null
        defaultCategoryActionShouldBeFound("regions.specified=true");

        // Get all the categoryActionList where regions is null
        defaultCategoryActionShouldNotBeFound("regions.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByRegionsContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where regions contains DEFAULT_REGIONS
        defaultCategoryActionShouldBeFound("regions.contains=" + DEFAULT_REGIONS);

        // Get all the categoryActionList where regions contains UPDATED_REGIONS
        defaultCategoryActionShouldNotBeFound("regions.contains=" + UPDATED_REGIONS);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByRegionsNotContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where regions does not contain DEFAULT_REGIONS
        defaultCategoryActionShouldNotBeFound("regions.doesNotContain=" + DEFAULT_REGIONS);

        // Get all the categoryActionList where regions does not contain UPDATED_REGIONS
        defaultCategoryActionShouldBeFound("regions.doesNotContain=" + UPDATED_REGIONS);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByTagsIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where tags equals to DEFAULT_TAGS
        defaultCategoryActionShouldBeFound("tags.equals=" + DEFAULT_TAGS);

        // Get all the categoryActionList where tags equals to UPDATED_TAGS
        defaultCategoryActionShouldNotBeFound("tags.equals=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByTagsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where tags not equals to DEFAULT_TAGS
        defaultCategoryActionShouldNotBeFound("tags.notEquals=" + DEFAULT_TAGS);

        // Get all the categoryActionList where tags not equals to UPDATED_TAGS
        defaultCategoryActionShouldBeFound("tags.notEquals=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByTagsIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where tags in DEFAULT_TAGS or UPDATED_TAGS
        defaultCategoryActionShouldBeFound("tags.in=" + DEFAULT_TAGS + "," + UPDATED_TAGS);

        // Get all the categoryActionList where tags equals to UPDATED_TAGS
        defaultCategoryActionShouldNotBeFound("tags.in=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByTagsIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where tags is not null
        defaultCategoryActionShouldBeFound("tags.specified=true");

        // Get all the categoryActionList where tags is null
        defaultCategoryActionShouldNotBeFound("tags.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByTagsContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where tags contains DEFAULT_TAGS
        defaultCategoryActionShouldBeFound("tags.contains=" + DEFAULT_TAGS);

        // Get all the categoryActionList where tags contains UPDATED_TAGS
        defaultCategoryActionShouldNotBeFound("tags.contains=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByTagsNotContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where tags does not contain DEFAULT_TAGS
        defaultCategoryActionShouldNotBeFound("tags.doesNotContain=" + DEFAULT_TAGS);

        // Get all the categoryActionList where tags does not contain UPDATED_TAGS
        defaultCategoryActionShouldBeFound("tags.doesNotContain=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByProcessIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where processId equals to DEFAULT_PROCESS_ID
        defaultCategoryActionShouldBeFound("processId.equals=" + DEFAULT_PROCESS_ID);

        // Get all the categoryActionList where processId equals to UPDATED_PROCESS_ID
        defaultCategoryActionShouldNotBeFound("processId.equals=" + UPDATED_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByProcessIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where processId not equals to DEFAULT_PROCESS_ID
        defaultCategoryActionShouldNotBeFound("processId.notEquals=" + DEFAULT_PROCESS_ID);

        // Get all the categoryActionList where processId not equals to UPDATED_PROCESS_ID
        defaultCategoryActionShouldBeFound("processId.notEquals=" + UPDATED_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByProcessIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where processId in DEFAULT_PROCESS_ID or UPDATED_PROCESS_ID
        defaultCategoryActionShouldBeFound("processId.in=" + DEFAULT_PROCESS_ID + "," + UPDATED_PROCESS_ID);

        // Get all the categoryActionList where processId equals to UPDATED_PROCESS_ID
        defaultCategoryActionShouldNotBeFound("processId.in=" + UPDATED_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByProcessIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where processId is not null
        defaultCategoryActionShouldBeFound("processId.specified=true");

        // Get all the categoryActionList where processId is null
        defaultCategoryActionShouldNotBeFound("processId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByProcessIdContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where processId contains DEFAULT_PROCESS_ID
        defaultCategoryActionShouldBeFound("processId.contains=" + DEFAULT_PROCESS_ID);

        // Get all the categoryActionList where processId contains UPDATED_PROCESS_ID
        defaultCategoryActionShouldNotBeFound("processId.contains=" + UPDATED_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByProcessIdNotContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where processId does not contain DEFAULT_PROCESS_ID
        defaultCategoryActionShouldNotBeFound("processId.doesNotContain=" + DEFAULT_PROCESS_ID);

        // Get all the categoryActionList where processId does not contain UPDATED_PROCESS_ID
        defaultCategoryActionShouldBeFound("processId.doesNotContain=" + UPDATED_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where source equals to DEFAULT_SOURCE
        defaultCategoryActionShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the categoryActionList where source equals to UPDATED_SOURCE
        defaultCategoryActionShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsBySourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where source not equals to DEFAULT_SOURCE
        defaultCategoryActionShouldNotBeFound("source.notEquals=" + DEFAULT_SOURCE);

        // Get all the categoryActionList where source not equals to UPDATED_SOURCE
        defaultCategoryActionShouldBeFound("source.notEquals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultCategoryActionShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the categoryActionList where source equals to UPDATED_SOURCE
        defaultCategoryActionShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where source is not null
        defaultCategoryActionShouldBeFound("source.specified=true");

        // Get all the categoryActionList where source is null
        defaultCategoryActionShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsBySourceContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where source contains DEFAULT_SOURCE
        defaultCategoryActionShouldBeFound("source.contains=" + DEFAULT_SOURCE);

        // Get all the categoryActionList where source contains UPDATED_SOURCE
        defaultCategoryActionShouldNotBeFound("source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where source does not contain DEFAULT_SOURCE
        defaultCategoryActionShouldNotBeFound("source.doesNotContain=" + DEFAULT_SOURCE);

        // Get all the categoryActionList where source does not contain UPDATED_SOURCE
        defaultCategoryActionShouldBeFound("source.doesNotContain=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByActionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where actionType equals to DEFAULT_ACTION_TYPE
        defaultCategoryActionShouldBeFound("actionType.equals=" + DEFAULT_ACTION_TYPE);

        // Get all the categoryActionList where actionType equals to UPDATED_ACTION_TYPE
        defaultCategoryActionShouldNotBeFound("actionType.equals=" + UPDATED_ACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByActionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where actionType not equals to DEFAULT_ACTION_TYPE
        defaultCategoryActionShouldNotBeFound("actionType.notEquals=" + DEFAULT_ACTION_TYPE);

        // Get all the categoryActionList where actionType not equals to UPDATED_ACTION_TYPE
        defaultCategoryActionShouldBeFound("actionType.notEquals=" + UPDATED_ACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByActionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where actionType in DEFAULT_ACTION_TYPE or UPDATED_ACTION_TYPE
        defaultCategoryActionShouldBeFound("actionType.in=" + DEFAULT_ACTION_TYPE + "," + UPDATED_ACTION_TYPE);

        // Get all the categoryActionList where actionType equals to UPDATED_ACTION_TYPE
        defaultCategoryActionShouldNotBeFound("actionType.in=" + UPDATED_ACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByActionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        // Get all the categoryActionList where actionType is not null
        defaultCategoryActionShouldBeFound("actionType.specified=true");

        // Get all the categoryActionList where actionType is null
        defaultCategoryActionShouldNotBeFound("actionType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoryActionsByCategoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);
        Category categoryId = CategoryResourceIT.createEntity(em);
        em.persist(categoryId);
        em.flush();
        categoryAction.setCategoryId(categoryId);
        categoryActionRepository.saveAndFlush(categoryAction);
        Long categoryIdId = categoryId.getId();

        // Get all the categoryActionList where categoryId equals to categoryIdId
        defaultCategoryActionShouldBeFound("categoryIdId.equals=" + categoryIdId);

        // Get all the categoryActionList where categoryId equals to categoryIdId + 1
        defaultCategoryActionShouldNotBeFound("categoryIdId.equals=" + (categoryIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoryActionShouldBeFound(String filter) throws Exception {
        restCategoryActionMockMvc
            .perform(get("/api/category-actions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mcc").value(hasItem(DEFAULT_MCC)))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL)))
            .andExpect(jsonPath("$.[*].defaultOrderId").value(hasItem(DEFAULT_DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].addedDate").value(hasItem(sameInstant(DEFAULT_ADDED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].regions").value(hasItem(DEFAULT_REGIONS)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].processId").value(hasItem(DEFAULT_PROCESS_ID)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE.toString())));

        // Check, that the count call also returns 1
        restCategoryActionMockMvc
            .perform(get("/api/category-actions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoryActionShouldNotBeFound(String filter) throws Exception {
        restCategoryActionMockMvc
            .perform(get("/api/category-actions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoryActionMockMvc
            .perform(get("/api/category-actions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCategoryAction() throws Exception {
        // Get the categoryAction
        restCategoryActionMockMvc.perform(get("/api/category-actions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryAction() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        int databaseSizeBeforeUpdate = categoryActionRepository.findAll().size();

        // Update the categoryAction
        CategoryAction updatedCategoryAction = categoryActionRepository.findById(categoryAction.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryAction are not directly saved in db
        em.detach(updatedCategoryAction);
        updatedCategoryAction
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .mcc(UPDATED_MCC)
            .categoryId(UPDATED_CATEGORY_ID)
            .enabled(UPDATED_ENABLED)
            .iconUrl(UPDATED_ICON_URL)
            .defaultOrderId(UPDATED_DEFAULT_ORDER_ID)
            .addedDate(UPDATED_ADDED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .regions(UPDATED_REGIONS)
            .tags(UPDATED_TAGS)
            .processId(UPDATED_PROCESS_ID)
            .source(UPDATED_SOURCE)
            .actionType(UPDATED_ACTION_TYPE);
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(updatedCategoryAction);

        restCategoryActionMockMvc
            .perform(
                put("/api/category-actions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeUpdate);
        CategoryAction testCategoryAction = categoryActionList.get(categoryActionList.size() - 1);
        assertThat(testCategoryAction.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testCategoryAction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategoryAction.getMcc()).isEqualTo(UPDATED_MCC);
        assertThat(testCategoryAction.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategoryAction.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testCategoryAction.getIconUrl()).isEqualTo(UPDATED_ICON_URL);
        assertThat(testCategoryAction.getDefaultOrderId()).isEqualTo(UPDATED_DEFAULT_ORDER_ID);
        assertThat(testCategoryAction.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testCategoryAction.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCategoryAction.getRegions()).isEqualTo(UPDATED_REGIONS);
        assertThat(testCategoryAction.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testCategoryAction.getProcessId()).isEqualTo(UPDATED_PROCESS_ID);
        assertThat(testCategoryAction.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testCategoryAction.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryAction() throws Exception {
        int databaseSizeBeforeUpdate = categoryActionRepository.findAll().size();

        // Create the CategoryAction
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryActionMockMvc
            .perform(
                put("/api/category-actions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryAction() throws Exception {
        // Initialize the database
        categoryActionRepository.saveAndFlush(categoryAction);

        int databaseSizeBeforeDelete = categoryActionRepository.findAll().size();

        // Delete the categoryAction
        restCategoryActionMockMvc
            .perform(delete("/api/category-actions/{id}", categoryAction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
