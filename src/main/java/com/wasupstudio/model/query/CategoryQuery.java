package com.wasupstudio.model.query;

import com.wasupstudio.model.vo.AgeDistributionsVo;
import com.wasupstudio.model.vo.CategoryVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryQuery<T>{
    private List<CategoryVo> categoryDistributions;
    private List<T> list;
    protected long total;
}
