package com.duzheStudy.modules.worksDisplay.Service;

import com.duzheStudy.modules.worksDisplay.domain.Category;

import java.util.List;

public interface CategoryService {
    public int deleteById(Long id);

    public int insert(Category category);

    public Category selectById(Long id);

    public int updateById(Category category);

    public List<Category> selectAll();
}
