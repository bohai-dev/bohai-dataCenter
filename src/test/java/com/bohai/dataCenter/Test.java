package com.bohai.dataCenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {
    
    public static class Tes{
        
        private String name;
        
        private int qty;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }
        
        
    }

	public static void main(String[] args) {
		
		List<Tes> tes = new ArrayList<Tes>();
		Map<String,List<Tes>> map=tes.stream().collect(Collectors.groupingBy(Tes::getName));
		
	}
}
