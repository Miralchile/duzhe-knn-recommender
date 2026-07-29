package com.duzheStudy.modules.worksDisplay.Service.Impl;

import com.duzheStudy.modules.worksDisplay.Service.CategoryService;
import com.duzheStudy.modules.worksDisplay.mapper.CategoryMapper;
import com.duzheStudy.modules.worksDisplay.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public int deleteById(Long id){
        return this.categoryMapper.deleteById(id);
    }

    public int insert(Category category){
        return this.categoryMapper.insert(category);
    }

    public Category selectById(Long id){
        return this.categoryMapper.selectById(id);
    }

    public int updateById(Category category){
        return this.categoryMapper.updateById(category);
    }

    @Override
    public List<Category> selectAll() {
        return this.categoryMapper.selectAll();
    }

}
