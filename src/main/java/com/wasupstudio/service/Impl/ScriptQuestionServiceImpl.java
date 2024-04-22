package com.wasupstudio.service.Impl;

import com.wasupstudio.converter.ScriptQuestionConverter;
import com.wasupstudio.mapper.ScriptQuestionMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptQuestionDTO;
import com.wasupstudio.model.entity.ScriptDetailEntity;
import com.wasupstudio.model.entity.ScriptQuestionEntity;
import com.wasupstudio.model.entity.TaskEntity;
import com.wasupstudio.model.query.*;
import com.wasupstudio.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScriptQuestionServiceImpl extends AbstractService<ScriptQuestionEntity> implements ScriptQuestionService {
    @Autowired
    public ScriptQuestionMapper scriptQuestionMapper;

    @Autowired
    public ScriptQuestionConverter scriptQuestionConverter;

    @Autowired
    public ScriptDetailService scriptDetailService;

    @Autowired
    public TaskService taskService;

    @Autowired
    public ScriptService scriptService;

    @Override
    public void save(ScriptQuestionDTO dto) {
        if (dto.getQuestionId() == null) {
            this.save(scriptQuestionConverter.DTOtoItem(dto));
        } else {
            this.update(scriptQuestionConverter.DTOtoItem(dto));
        }
    }

    @Override
    public void delete(Integer quesetionId) {
        this.deleteById(quesetionId);
    }

    @Override
    public BasePageInfo<ScriptQuestionEntity> findByTaskId(Integer id) {
        List<ScriptQuestionEntity> list = scriptQuestionMapper.findByTaskId(id);
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public BasePageInfo<ScriptQuestionEntity> findAllData() {
        List<ScriptQuestionEntity> list = this.findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void update(ScriptQuestionDTO dto) {
        this.update(scriptQuestionConverter.DTOtoItem(dto));
    }

    @Override
    public BasePageInfo findReportForEnding() {
        List<ScriptQuery> scripts = scriptService.findAllData().getList();
        Map<Integer, ScriptQuery> scriptMap = scripts.stream()
                .collect(Collectors.toMap(ScriptQuery::getScriptId, script -> script));

        List<ScriptDetailEntity> details = scriptDetailService.findAllData().getList();
        Map<String, ScriptDetailEntity> detailMap = details.stream()
                .collect(Collectors.toMap(detail -> detail.getScriptId() + "-" + detail.getPeriod(), detail -> detail));

        List<TaskEntity> tasks = taskService.findAllData().getList();

        List<QuestionResultQuery> results = new ArrayList<>();

        List<QuestionReportQuery> reports = scriptQuestionMapper.findReportForEnding();
        for (TaskEntity task : tasks) {
            for (QuestionReportQuery report : reports) {
                QuestionResultQuery resultQuery = new QuestionResultQuery();
                BigDecimal orderlyTotal = BigDecimal.ZERO;
                BigDecimal relationTotal = BigDecimal.ZERO;

                if (task.getTaskId().equals(report.getTaskId())) {
                    ScriptQuery script = scriptMap.get(report.getScriptId());
                    ScriptDetailEntity detail = detailMap.get(report.getScriptId() + "-" + report.getPeriod());
                    BigDecimal stuOrderly = BigDecimal.valueOf(report.getStuOrderly());
                    BigDecimal parOrderly = BigDecimal.valueOf(report.getParOrderly());
                    BigDecimal stuRelation = BigDecimal.valueOf(report.getStuRelation());
                    BigDecimal parRelation = BigDecimal.valueOf(report.getParRelation());
                    orderlyTotal = orderlyTotal.add(stuOrderly).add(parOrderly);
                    relationTotal = relationTotal.add(stuRelation).add(parRelation);

                    resultQuery.setStuContent(detail.getStuContent());
                    resultQuery.setParContent(detail.getParContent());
                    resultQuery.setTitle(script.getTitle());
                    resultQuery.setTaskId(task.getTaskId());
                    resultQuery.setScriptId(task.getScriptId());
                    resultQuery.setOrderlyTotal(orderlyTotal);
                    resultQuery.setRelationTotal(relationTotal);
                    results.add(resultQuery);
                }
            }

        }

        Map<String, BigDecimal> relationMap = results.stream()
                .collect(Collectors.groupingBy(
                        result -> result.getTaskId() + "-" + result.getScriptId(),
                        Collectors.reducing(BigDecimal.ZERO,
                                QuestionResultQuery::getRelationTotal,
                                BigDecimal::add)
                ));
        Map<String, BigDecimal> orderlyMap = results.stream()
                .collect(Collectors.groupingBy(
                        result -> result.getTaskId() + "-" + result.getScriptId(),
                        Collectors.reducing(BigDecimal.ZERO,
                                QuestionResultQuery::getOrderlyTotal,
                                BigDecimal::add)
                ));

        // 在Map中檢查並更新總和
        relationMap.replaceAll((key, value) -> value.equals(BigDecimal.ZERO) ? BigDecimal.ZERO.subtract(BigDecimal.ONE) : value);
        orderlyMap.replaceAll((key, value) -> value.equals(BigDecimal.ZERO) ? BigDecimal.ZERO.subtract(BigDecimal.ONE) : value);

        // 合併兩個map
        Map<String, Integer[]> combinedMap = new HashMap<>();
        for (String key : relationMap.keySet()) {
            BigDecimal relationValue = relationMap.get(key);
            BigDecimal orderlyValue = orderlyMap.get(key);

            // 確保值不為0, 為0的話變為-1
            if (relationValue.equals(BigDecimal.ZERO)) {
                relationValue = BigDecimal.ZERO.subtract(BigDecimal.ONE);
            }
            if (orderlyValue.equals(BigDecimal.ZERO)) {
                orderlyValue = BigDecimal.ZERO.subtract(BigDecimal.ONE);
            }

            Integer[] values = new Integer[]{relationValue.intValue(), orderlyValue.intValue()};
            combinedMap.put(key, values);
        }

        List<Map.Entry<String, Integer[]>> combinedList = new ArrayList<>(combinedMap.entrySet());

         results = new ArrayList<>();
        for (Map.Entry<String, Integer[]> res :combinedList){
            String[] key = res.getKey().split("-");
            Integer taskId = Integer.valueOf(key[0]);
            Integer scriptId = Integer.valueOf(key[1]);
            Integer[] value = res.getValue();
            Integer orderlyTotal = value[0];
            Integer relationTotal = value[1];

            QuestionResultQuery query = new QuestionResultQuery();
            query.setScriptId(scriptId);
            query.setTaskId(taskId);
            query.setOrderlyTotal(BigDecimal.valueOf(orderlyTotal));
            query.setRelationTotal(BigDecimal.valueOf(relationTotal));

            results.add(query);

        }
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(results);
        basePageInfo.setTotal(results.size());

        return basePageInfo;
    }
    @Override
    public BasePageInfo scoreDistribution(Integer taskId) {
        List<ScoreDistributionQuery> list = scriptQuestionMapper.scoreDistribution(taskId);
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public BasePageInfo scoreDistribution() {
        List<QuestionReportV2Query> list = scriptQuestionMapper.scoreDistributionV2();

        Map<Integer, Map<String, Integer>> resultMap = new HashMap<>();

        for (QuestionReportV2Query item : list) {
            Integer scriptId = item.getScriptId();
            String result = item.getResult();

            // Get or create the scriptMap for the current scriptId
            Map<String, Integer> scriptMap = resultMap.getOrDefault(scriptId, new HashMap<>());

            // Increment the count for the current result in the scriptMap
            scriptMap.put(result, scriptMap.getOrDefault(result, 0) + 1);

            // Update the resultMap with the modified scriptMap
            resultMap.put(scriptId, scriptMap);
        }

        List<EndingReportQuery> endingReportList = new ArrayList<>();

        for (Map.Entry<Integer, Map<String, Integer>> entry : resultMap.entrySet()) {
            Integer scriptId = entry.getKey();
            Map<String, Integer> resultCountMap = entry.getValue();

            EndingReportQuery endingReportQuery = new EndingReportQuery();
            endingReportQuery.setScriptId(scriptId);
            // Iterate through resultCountMap and set counts in ResultEndingQuery
            List<ResultEndingQuery> resultEndingQueryList = new ArrayList<>();
            for (Map.Entry<String, Integer> resultEntry : resultCountMap.entrySet()) {
                ResultEndingQuery resultEndingQuery = new ResultEndingQuery();
                String result = resultEntry.getKey();
                Integer count = resultEntry.getValue();
                resultEndingQuery.setResult(result);
                resultEndingQuery.setCount(count);

                endingReportQuery.setScriptId(scriptId);
                List<QuestionReportV2Query> questionReportV2QueryList = new ArrayList<>();
                for (QuestionReportV2Query questionReportV2Query : list) {
                    if (questionReportV2Query.getScriptId().equals(scriptId)){
                        questionReportV2QueryList.add(questionReportV2Query);
                    }
                }
                endingReportQuery.setDetail(questionReportV2QueryList);
                endingReportQuery.setResult(resultEndingQueryList);
                resultEndingQueryList.add(resultEndingQuery);
            }
            endingReportList.add(endingReportQuery);
        }

        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(endingReportList);
        basePageInfo.setTotal(endingReportList.size());
        return basePageInfo;
    }


    public static void main(String[] args) {
        BigDecimal test = BigDecimal.ZERO;
        System.out.println(test.compareTo(BigDecimal.ZERO));
    }
}
