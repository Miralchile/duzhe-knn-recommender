package com.duzheStudy.modules.worksDisplay.mapper;

import com.duzheStudy.modules.worksDisplay.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    int deleteById(Long id);

    int insert(Category category);

    Category selectById(Long id);

    int updateById(Category category);

    List<Category> selectAll();
}