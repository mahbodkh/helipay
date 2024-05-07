package app.helipay.se.service;

import app.helipay.se.domain.CategoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryService {

//    private static final List<CategoryEntity.StatusType> DEFAULT_CLIENT_STATUS
//            = new ArrayList<>(List.of(CategoryEntity.StatusType.ACTIVE));

    public static final String ERROR_CATEGORY_NOT_FOUND = "Category [ id:%s ] not found.";
    public static final String ERROR_CATEGORY_SLUG_NAME_NOT_FOUND = "Category [ slug:%s|name:%s ] not found.";
    public static final String ERROR_CATEGORY_SLUG_NAME_EXIST = "Category [ slug:%s|name:%s ] has existed.";
    public static final String ERROR_CATEGORY_DELETE_HAS_CHILD = "Category [id:%s] is not possible to delete. Has children [size:%s]";
    public static final String ERROR_CATEGORY_DELETE_HAS_PRODUCT = "Category [id:%s] is not possible to delete. Has products [size:%s]";

//    private final CategoryMapper categoryMapper;
//    private final CategoryRepository categoryRepository;
//    private final ProductRepository productRepository;


//    public CategoryReply createCategory(CategoryRequest request, MultipartFile file) {
//        isCategoryAvailableBySlugOrName(request.getSlug(), request.getName());
//
//        CategoryEntity parent = null;
//        if (request.getParent() != null) {
//            parent = getCategory(request.getParent());
//        }
//        CategoryEntity category = CategoryEntity
//                .getBasicCategory(request.getName(), request.getSlug(), request.getStatus(), parent);
//        category.setDescription(request.getDescription());
//        category.setCode(request.getCode());
//        //    category.setCover(fileService.saveAndGetUrl(file));
//        category = categoryRepository.save(category);
//        return categoryMapper.toDto(category);
//    }

//    public Page<CategoryReply> getAllCategories(Pageable pageable) {
//        return categoryMapper.toPageDto(categoryRepository.findAllByStatusIn(pageable, DEFAULT_CLIENT_STATUS));
//    }

//    public Page<CategoryReply> getAllCategoriesAdmin(Pageable pageable) {
//        return categoryMapper.toPageDto(categoryRepository.findAll(pageable));
//    }

//    public CategoryReply getCategoryClient(Long categoryId) {
//        CategoryEntity entity = categoryRepository
//                .findByIdAndStatusIn(categoryId, DEFAULT_CLIENT_STATUS)
//                .orElseThrow(() -> new NotFoundException(String.format(ERROR_CATEGORY_NOT_FOUND, categoryId)));
//        return categoryMapper.toDto(entity);
//    }

//    public CategoryReply getCategoryByAdmin(Long categoryId) {
//        CategoryEntity entity = getCategory(categoryId);
//        return categoryMapper.toDto(entity);
//    }

//    public CategoryReply getCategoryBySlugOrName(final String slug, final String name) {
//        Optional<CategoryEntity> entity;
//        if (slug != null && !slug.isEmpty()) {
//            entity = getCategoryBySlug(slug);
//        }
//        else if (name != null && !name.isEmpty()) {
//            entity = getCategoryByName(name);
//        }
//        else {
//            entity = Optional.empty();
//        }
//        return entity.map(categoryMapper::toDto)
//                .orElseThrow(() -> new NotFoundException(
//                        String.format(ERROR_CATEGORY_SLUG_NAME_NOT_FOUND
//                                , slug == null ? "''" : slug
//                                , name == null ? "''" : name)
//                ));
//    }

//    public CategoryReply getCategoryBySlugOrNameAdmin(final String slug, final String name) {
//        Optional<CategoryEntity> entity;
//        if (slug != null && !slug.isEmpty()) {
//            entity = categoryRepository.findBySlug(slug);
//        }
//        else if (name != null && !name.isEmpty()) {
//            entity = categoryRepository.findByName(name);
//        }
//        else {
//            entity = Optional.empty();
//        }
//        return entity.map(categoryMapper::toDto)
//                .orElseThrow(() -> new NotFoundException(
//                        String.format(ERROR_CATEGORY_SLUG_NAME_NOT_FOUND
//                                , slug == null ? "''" : slug
//                                , name == null ? "''" : name)
//                ));
//    }

//    public CategoryReply updateCategory(Long categoryId, CategoryRequest request, MultipartFile file) {
//        return categoryRepository
//                .findById(categoryId)
//                .map(categoryEntity -> {
//                    CategoryEntity parent = null;
//                    if (request.getParent() != null) {
//                        parent = categoryRepository
//                                .findById(request.getParent())
//                                .orElseThrow(() -> new NotFoundException(String.format("Parent [parent:%s] is not found!", request.getParent())));
//                    }
//
//                    // create basic category entity
//                    CategoryEntity update = CategoryEntity.getBasicCategory(request.getName(), request.getSlug(), request.getStatus(), parent);
//
//                    // description field
//                    if (request.getDescription() != null && !request.getDescription().isEmpty()) {
//                        update.setDescription(request.getDescription());
//                    }
//
//                    // code field
//                    if (request.getCode() != null) {
//                        update.setCode(request.getCode());
//                    }
//
//                    if (categoryRepository.findBySlug(update.getSlug()).isPresent()) {
//                        throw new ErrorException(String.format("Slug [%s] is exist! You have to change slug.", update.getSlug()));
//                    }
//
//                    categoryEntity.setName(update.getName());
//                    categoryEntity.setSlug(update.getSlug());
//                    categoryEntity.setStatus(update.getStatus());
//                    categoryEntity.setPath(update.getPath());
//                    categoryEntity.setBreadcrumbs(update.getBreadcrumbs());
//                    categoryEntity.setParent(update.getParent());
//                    categoryEntity.setCode(update.getCode() == null ? categoryEntity.getCode() : update.getCode());
//                    categoryEntity.setDescription(update.getDescription() == null ? categoryEntity.getDescription() : update.getDescription());
//
//                    // we have to treat breadcrumbs and children fields!
//                    return categoryRepository.save(categoryEntity);
//                })
//                .map(categoryMapper::toDto)
//                .orElseThrow(() -> new ErrorException(String.format("Category [id:%s] is not possible to update/not found.", categoryId)));
//    }

//    public void deleteCategory(final Long categoryId) {
//        categoryRepository.findById(categoryId)
//                .ifPresent(categoryEntity -> {
//
//                    if (!categoryEntity.getChildren().isEmpty()) {
//                        throw new ErrorException(String.format(ERROR_CATEGORY_DELETE_HAS_CHILD,
//                                categoryId,
//                                categoryEntity.getChildren().size())
//                        );
//                    }
//                    //
//                    Integer productCounts = productRepository.countAllByCategoryId(categoryId);
//                    if (productCounts > 0) {
//                        throw new ErrorException(String.format(ERROR_CATEGORY_DELETE_HAS_PRODUCT,
//                                categoryId,
//                                productCounts)
//                        );
//                    }
//                    categoryRepository.deleteById(categoryId);
//                });
//    }


    //  ############################################################################################
    //                                  PRIVATE LOGIC
    //  ############################################################################################


    private void isCategoryAvailableBySlugOrName(String slug, String name) {
//        boolean existed = categoryRepository.existsBySlugOrName(slug, name);
//        if (existed) {
//            throw new NotFoundException(
//                    String.format(ERROR_CATEGORY_SLUG_NAME_EXIST
//                            , slug == null ? "''" : slug
//                            , name == null ? "''" : name));
//        }
    }

//    private CategoryEntity getCategory(Long categoryId) {
//        return categoryRepository
//                .findById(categoryId)
//                .orElseThrow(() -> new NotFoundException(String.format(ERROR_CATEGORY_NOT_FOUND, categoryId)));
//    }

//    private Optional<CategoryEntity> getCategoryBySlug(String slug) {
//        return categoryRepository.findBySlugAndStatusIn(slug, CategoryService.DEFAULT_CLIENT_STATUS);
//    }

//    private Optional<CategoryEntity> getCategoryByName(String name) {
//        return categoryRepository.findByNameAndStatusIn(name, CategoryService.DEFAULT_CLIENT_STATUS);
//    }
}
