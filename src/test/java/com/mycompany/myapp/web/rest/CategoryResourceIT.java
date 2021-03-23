package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.SyncApp;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.service.CategoryService;
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
 * Integration tests for the {@link CategoryResource} REST controller.
 */
@SpringBootTest(classes = SyncApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategoryResourceIT {
    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MCC = "AAAA";
    private static final String UPDATED_MCC = "BBBB";

    private static final String DEFAULT_PARENT_CATEGORY_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_CATEGORY_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_ICON_URL = "AAAAAAAAAA";
    private static final String UPDATED_ICON_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_DEFAULT_ORDER_ID = 2;

    private static final ZonedDateTime DEFAULT_ADDED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADDED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REGIONS = "AAAAAAAAAA";
    private static final String UPDATED_REGIONS = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryMockMvc;

    private Category category;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createEntity(EntityManager em) {
        Category category = new Category()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .mcc(DEFAULT_MCC)
            .parentCategoryId(DEFAULT_PARENT_CATEGORY_ID)
            .enabled(DEFAULT_ENABLED)
            .iconUrl(DEFAULT_ICON_URL)
            .defaultOrderId(DEFAULT_DEFAULT_ORDER_ID)
            .addedDate(DEFAULT_ADDED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .regions(DEFAULT_REGIONS)
            .tags(DEFAULT_TAGS);
        return category;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createUpdatedEntity(EntityManager em) {
        Category category = new Category()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .mcc(UPDATED_MCC)
            .parentCategoryId(UPDATED_PARENT_CATEGORY_ID)
            .enabled(UPDATED_ENABLED)
            .iconUrl(UPDATED_ICON_URL)
            .defaultOrderId(UPDATED_DEFAULT_ORDER_ID)
            .addedDate(UPDATED_ADDED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .regions(UPDATED_REGIONS)
            .tags(UPDATED_TAGS);
        return category;
    }

    @BeforeEach
    public void initTest() {
        category = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategory() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();
        // Create the Category
        restCategoryMockMvc
            .perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate + 1);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategory.getMcc()).isEqualTo(DEFAULT_MCC);
        assertThat(testCategory.getParentCategoryId()).isEqualTo(DEFAULT_PARENT_CATEGORY_ID);
        assertThat(testCategory.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testCategory.getIconUrl()).isEqualTo(DEFAULT_ICON_URL);
        assertThat(testCategory.getDefaultOrderId()).isEqualTo(DEFAULT_DEFAULT_ORDER_ID);
        assertThat(testCategory.getAddedDate()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testCategory.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testCategory.getRegions()).isEqualTo(DEFAULT_REGIONS);
        assertThat(testCategory.getTags()).isEqualTo(DEFAULT_TAGS);
    }

    @Test
    @Transactional
    public void createCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // Create the Category with an existing ID
        category.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryMockMvc
            .perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setUuid(null);

        // Create the Category, which fails.

        restCategoryMockMvc
            .perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setName(null);

        // Create the Category, which fails.

        restCategoryMockMvc
            .perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategories() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList
        restCategoryMockMvc
            .perform(get("/api/categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mcc").value(hasItem(DEFAULT_MCC)))
            .andExpect(jsonPath("$.[*].parentCategoryId").value(hasItem(DEFAULT_PARENT_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL)))
            .andExpect(jsonPath("$.[*].defaultOrderId").value(hasItem(DEFAULT_DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].addedDate").value(hasItem(sameInstant(DEFAULT_ADDED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].regions").value(hasItem(DEFAULT_REGIONS)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)));
    }

    @Test
    @Transactional
    public void getCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get the category
        restCategoryMockMvc
            .perform(get("/api/categories/{id}", category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(category.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.mcc").value(DEFAULT_MCC))
            .andExpect(jsonPath("$.parentCategoryId").value(DEFAULT_PARENT_CATEGORY_ID))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.iconUrl").value(DEFAULT_ICON_URL))
            .andExpect(jsonPath("$.defaultOrderId").value(DEFAULT_DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.addedDate").value(sameInstant(DEFAULT_ADDED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)))
            .andExpect(jsonPath("$.regions").value(DEFAULT_REGIONS))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS));
    }

    @Test
    @Transactional
    public void getNonExistingCategory() throws Exception {
        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategory() throws Exception {
        // Initialize the database
        categoryService.save(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category
        Category updatedCategory = categoryRepository.findById(category.getId()).get();
        // Disconnect from session so that the updates on updatedCategory are not directly saved in db
        em.detach(updatedCategory);
        updatedCategory
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .mcc(UPDATED_MCC)
            .parentCategoryId(UPDATED_PARENT_CATEGORY_ID)
            .enabled(UPDATED_ENABLED)
            .iconUrl(UPDATED_ICON_URL)
            .defaultOrderId(UPDATED_DEFAULT_ORDER_ID)
            .addedDate(UPDATED_ADDED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .regions(UPDATED_REGIONS)
            .tags(UPDATED_TAGS);

        restCategoryMockMvc
            .perform(
                put("/api/categories").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedCategory))
            )
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategory.getMcc()).isEqualTo(UPDATED_MCC);
        assertThat(testCategory.getParentCategoryId()).isEqualTo(UPDATED_PARENT_CATEGORY_ID);
        assertThat(testCategory.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testCategory.getIconUrl()).isEqualTo(UPDATED_ICON_URL);
        assertThat(testCategory.getDefaultOrderId()).isEqualTo(UPDATED_DEFAULT_ORDER_ID);
        assertThat(testCategory.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testCategory.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCategory.getRegions()).isEqualTo(UPDATED_REGIONS);
        assertThat(testCategory.getTags()).isEqualTo(UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void updateNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMockMvc
            .perform(put("/api/categories").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategory() throws Exception {
        // Initialize the database
        categoryService.save(category);

        int databaseSizeBeforeDelete = categoryRepository.findAll().size();

        // Delete the category
        restCategoryMockMvc
            .perform(delete("/api/categories/{id}", category.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
