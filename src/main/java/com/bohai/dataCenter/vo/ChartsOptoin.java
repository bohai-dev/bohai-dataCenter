package com.bohai.dataCenter.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Echarts图表
 * @author caojia
 *
 */
public class ChartsOptoin {
    
    /**
     * 图例组件
     */
    private Legend legend;
    
    /**
     * 系列列表
     */
    private List<Series> series;

    /**
     * 直角坐标系 grid 中的 x 轴，一般情况下单个 grid 组件最多只能放上下两个 x 轴，多于两个 x 轴需要通过配置 offset 属性防止同个位置多个 x 轴的重叠
     */
    private XAxis xAxis;
    
    
    
    public Legend getLegend() {
        return legend;
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public XAxis getxAxis() {
        return xAxis;
    }

    public void setxAxis(XAxis xAxis) {
        this.xAxis = xAxis;
    }

    public static class XAxis{
        
        /**
         * 类目数据，在类目轴（type: 'category'）中有效。
         */
        private List<String> data;

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
        
    }
    
    public static class Legend{
        
        /**
         * 图例的数据数组
         */
        private List<String> data;

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
        
    }
    
    public static class Series{
        
        /**
         * 系列名称，用于tooltip的显示，legend 的图例筛选
         */
        private String name;
        
        /**
         * 
         */
        private String type;
        
        private String barWidth;
        
        /**
         * 系列中的数据内容数组。数组项通常为具体的数据项。
         */
        private List<BigDecimal> data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBarWidth() {
            return barWidth;
        }

        public void setBarWidth(String barWidth) {
            this.barWidth = barWidth;
        }

        public List<BigDecimal> getData() {
            return data;
        }

        public void setData(List<BigDecimal> data) {
            this.data = data;
        }
        
        
    }
    
}
