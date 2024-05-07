package app.helipay.se.repository;

import app.helipay.se.domain.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>
{
  Optional<CategoryEntity> findByName(String name);
  Optional<CategoryEntity> findBySlug(String slug);
//  Optional<CategoryEntity> findByNameAndStatusIn(String name, List<CategoryEntity.StatusType> statuses);
//  Optional<CategoryEntity> findBySlugAndStatusIn(String slug, List<CategoryEntity.StatusType> statuses);

//  Page<CategoryEntity> findAllByStatusIn(Pageable pageable, List<CategoryEntity.StatusType> statuses);
//  Optional<CategoryEntity> findByIdAndStatusIn(Long categoryId, List<CategoryEntity.StatusType> statuses);
  boolean existsBySlugOrName(String slug, String name);

  List<CategoryEntity> findByCreatedBetween(Date startDate, Date endDate);
}