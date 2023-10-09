package com.wasupstudio.model.query;

import com.wasupstudio.model.vo.AgeDistributionsVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AgeDistributionsQuery<T>{
    private List<AgeDistributionsVo> ageDistributions;
    private List<T> list;
    protected long total;
}
