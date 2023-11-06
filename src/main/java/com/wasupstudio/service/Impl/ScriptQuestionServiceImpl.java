package com.wasupstudio.service.Impl;

import com.wasupstudio.converter.ScriptQuestionConverter;
import com.wasupstudio.mapper.ScriptQuestionMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptQuestionDTO;
import com.wasupstudio.model.entity.ScriptDetailEntity;
import com.wasupstudio.model.entity.ScriptEntity;
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
        relationMap.replaceAll((key, value) -> value.equals(BigDecimal.ZERO) ? BigDecimal.ONE : value);
        orderlyMap.replaceAll((key, value) -> value.equals(BigDecimal.ZERO) ? BigDecimal.ONE : value);

        // 合併兩個map
        Map<String, Integer[]> combinedMap = new HashMap<>();
        for (String key : relationMap.keySet()) {
            BigDecimal relationValue = relationMap.get(key);
            BigDecimal orderlyValue = orderlyMap.get(key);

            // 確保值不為0
            if (relationValue.equals(BigDecimal.ZERO)) {
                relationValue = BigDecimal.ONE;
            }
            if (orderlyValue.equals(BigDecimal.ZERO)) {
                orderlyValue = BigDecimal.ONE;
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
            System.out.println(key);
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
    public BasePageInfo scoreDistribution() {

        List<ScoreDistributionQuery> list = scriptQuestionMapper.scoreDistribution();
        Map<Integer, Map<String, Integer>> scriptResultStatistics = new HashMap<>();

        for (ScoreDistributionQuery result : list) {
            Integer scriptId = result.getScriptId();
            String endingResult = result.getResult();

            // 檢查是否已經有此劇本的統計數據
            if (!scriptResultStatistics.containsKey(scriptId)) {
                scriptResultStatistics.put(scriptId, new HashMap<>());
            }

            Map<String, Integer> resultStats = scriptResultStatistics.get(scriptId);

            // 更新結局統計數量
            resultStats.put(endingResult, resultStats.getOrDefault(endingResult, 0) + 1);
        }


        List<QuestionResultQuery> details = findReportForEnding().getList();
        Map<Integer, List<QuestionResultQuery>> scriptIdToDetailsMap = new HashMap<>();

        for (QuestionResultQuery detail : details) {
            Integer scriptId = detail.getScriptId();

            // 檢查 Map 中是否已經有這個 scriptId，如果沒有，創建一個新 List
            if (!scriptIdToDetailsMap.containsKey(scriptId)) {
                scriptIdToDetailsMap.put(scriptId, new ArrayList<>());
            }

            // 取得相應 scriptId 的 List，並將 detail 加入其中
            List<QuestionResultQuery> scriptDetailsList = scriptIdToDetailsMap.get(scriptId);
            scriptDetailsList.add(detail);
        }

        // 遍歷 scriptIdToDetailsMap，為每個 scriptId 計算統計並添加到 resultStatisticsList
        List<StatisticsQuery> resultStatisticsList = new ArrayList<>();
        for (Map.Entry<Integer, List<QuestionResultQuery>> entry : scriptIdToDetailsMap.entrySet()) {
            Integer scriptId = entry.getKey();
            List<QuestionResultQuery> scriptDetailsList = entry.getValue();
            ScriptEntity script = scriptService.findOne(scriptId);

            StatisticsQuery statisticsQuery = new StatisticsQuery();
            statisticsQuery.setId(scriptId);
            statisticsQuery.setResult(script.getTitle());
            statisticsQuery.setCount(scriptResultStatistics.get(scriptId).size());
            statisticsQuery.setDetail(scriptDetailsList);

            resultStatisticsList.add(statisticsQuery);
        }

        System.out.println(resultStatisticsList);

        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(resultStatisticsList);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    public static void main(String[] args) {
        BigDecimal test = BigDecimal.ZERO;
        System.out.println(test.compareTo(BigDecimal.ZERO));
    }
}
