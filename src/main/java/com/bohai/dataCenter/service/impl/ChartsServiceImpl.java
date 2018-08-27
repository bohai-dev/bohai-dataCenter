package com.bohai.dataCenter.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bohai.dataCenter.persistence.ChartsMapper;
import com.bohai.dataCenter.persistence.CrmCustomerMapper;
import com.bohai.dataCenter.persistence.CtpTradeDataMapper;
import com.bohai.dataCenter.service.ChartsService;
import com.bohai.dataCenter.vo.ChartsOptoin;
import com.bohai.dataCenter.vo.ChartsOptoin.Legend;
import com.bohai.dataCenter.vo.ChartsOptoin.Series;
import com.bohai.dataCenter.vo.ChartsOptoin.XAxis;

@Service("chartsService")
public class ChartsServiceImpl implements ChartsService {

    @Autowired
    private CtpTradeDataMapper ctpTradeDataMapper;
    
    @Autowired
    private CrmCustomerMapper crmCustomerMapper;
    
    @Autowired
    private ChartsMapper chartsMapper;
    
    @Override
    public ChartsOptoin queryTrunoverBarChart() {
        
        List<Map<String, Object>> list = this.ctpTradeDataMapper.selectTrunover();
        
        ChartsOptoin optoin = new ChartsOptoin();
        {
            Legend legend = new Legend();
            List<String> data = new ArrayList<>();
            data.add("上期所");
            data.add("大商所");
            data.add("郑商所");
            data.add("中金所");
            data.add("能源交易中心");
            legend.setData(data);
            optoin.setLegend(legend);
        }
        
        {
            List<String> data = new ArrayList<>(); 
            list.forEach(e->{if(!data.contains(e.get("TRADE_DATE"))){
                    data.add((String)e.get("TRADE_DATE"));
                }
            });
            
            XAxis axis = new XAxis();
            axis.setData(data);
            optoin.setxAxis(axis);
        }
        
        {
            List<Series> seriesList = new ArrayList<>();
            Series series1 = new Series();
            series1.setName("上期所");
            series1.setType("bar");
            series1.setBarWidth("10%");
            seriesList.add(series1);
            
            Series series2 = new Series();
            series2.setName("大商所");
            series2.setType("bar");
            series2.setBarWidth("10%");
            seriesList.add(series2);
            
            Series series3 = new Series();
            series3.setName("郑商所");
            series3.setType("bar");
            series3.setBarWidth("10%");
            seriesList.add(series3);
            
            Series series4 = new Series();
            series4.setName("中金所");
            series4.setType("bar");
            series4.setBarWidth("10%");
            seriesList.add(series4);
            
            Series series5 = new Series();
            series5.setName("能源交易中心");
            series5.setType("bar");
            series5.setBarWidth("10%");
            seriesList.add(series5);
            
            List<BigDecimal> data1 = new ArrayList<>();
            List<BigDecimal> data2 = new ArrayList<>();
            List<BigDecimal> data3 = new ArrayList<>();
            List<BigDecimal> data4 = new ArrayList<>();
            List<BigDecimal> data5 = new ArrayList<>();
            for(Map<String, Object> map : list){
                if(map.get("EXCHANGE_NAME").equals("上期所")){
                    data1.add((BigDecimal) map.get("TRUNOVER"));
                }else if (map.get("EXCHANGE_NAME").equals("大商所")) {
                    data2.add((BigDecimal) map.get("TRUNOVER"));
                }else if (map.get("EXCHANGE_NAME").equals("郑商所")) {
                    data3.add((BigDecimal) map.get("TRUNOVER"));
                }else if (map.get("EXCHANGE_NAME").equals("中金所")) {
                    data4.add((BigDecimal) map.get("TRUNOVER"));
                }else if (map.get("EXCHANGE_NAME").equals("能源交易中心")) {
                    data5.add((BigDecimal) map.get("TRUNOVER"));
                }
            }
            series1.setData(data1);
            series2.setData(data2);
            series3.setData(data3);
            series4.setData(data4);
            series5.setData(data5);
            optoin.setSeries(seriesList);
        }
        
        return optoin;
    }

    @Override
    public ChartsOptoin queryInvestorIncreament() {
        
        ChartsOptoin optoin = new ChartsOptoin();
        List<Map<String, Object>> list = this.crmCustomerMapper.querySumByMonth();
        List<String> data1 = new ArrayList<>();
        List<BigDecimal> data2 = new ArrayList<>();
        if(list != null ){
            for (Map<String, Object> map : list) {
                data1.add((String) map.get("OPEN_DATE"));
                data2.add((BigDecimal) map.get("OPEN_COUNT"));
            }
        }
        
        XAxis axis = new XAxis();
        axis.setData(data1);
        optoin.setxAxis(axis);
        
        List<Series> series = new ArrayList<>();
        Series series2 = new Series();
        series2.setData(data2);
        series2.setName("新增客户数");
        series2.setType("bar");
        series2.setBarWidth("40%");
        series.add(series2);
        optoin.setSeries(series);
        return optoin;
    }

    @Override
    public ChartsOptoin queryCashInAndOut() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ChartsOptoin queryInvestorCharge() {
        
        ChartsOptoin optoin = new ChartsOptoin();
        List<Map<String, Object>> list = this.ctpTradeDataMapper.selectCharge();
        
        XAxis axis = new XAxis();
        List<String> data = new ArrayList<>();
        axis.setData(data);
        
        List<Series> series = new ArrayList<>();
        Series series1 = new Series();
        List<BigDecimal> data1 = new ArrayList<>();
        series1.setData(data1);
        series1.setType("bar");
        series1.setBarWidth("20%");
        series1.setName("交易所佣金");
        
        series.add(series1);
        Series series2 = new Series();
        List<BigDecimal> data2 = new ArrayList<>();
        series2.setData(data2);
        series2.setType("bar");
        series2.setBarWidth("20%");
        series2.setName("公司佣金");
        series.add(series2);
        if(list != null){
            for (Map<String, Object> map : list) {
                data.add((String) map.get("TRADE_DATE"));
                data1.add((BigDecimal) map.get("EXCHANGE_CHARGE"));
                data2.add((BigDecimal) map.get("CHARGE"));
            }
        }
        
        optoin.setxAxis(axis);
        optoin.setSeries(series);
        return optoin;
    }

    @Override
    public ChartsOptoin queryInvestorAndRight() {
        ChartsOptoin optoin = new ChartsOptoin();
        
        List<Series> series = new ArrayList<>();
        Series s = new Series();
        series.add(s);
        s.setType("bar");
        s.setBarWidth("50%");
        Long count = this.crmCustomerMapper.count();
        List<BigDecimal> data = new ArrayList<>();
        data.add(new BigDecimal(count));
        data.add(new BigDecimal(count));
        s.setData(data);
        optoin.setSeries(series);
        s.setName("39");
        return optoin;
    }

    @Override
    public List rightsRanking() {
        List<Map<String, Object>> list = this.chartsMapper.selectOrderByRanking();
        return list;
    }

}
