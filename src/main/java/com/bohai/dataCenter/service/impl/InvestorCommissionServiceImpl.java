package com.bohai.dataCenter.service.impl;

import com.bohai.dataCenter.entity.InvestorCommission;
import com.bohai.dataCenter.entity.TradingDate;
import com.bohai.dataCenter.persistence.InvestorCommissionMapper;
import com.bohai.dataCenter.persistence.TradingDateMapper;
import com.bohai.dataCenter.service.InvestorCommissionService;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Cteated by cxy on 2018/9/5
 */
@Service
public class InvestorCommissionServiceImpl implements InvestorCommissionService {
    @Autowired
    InvestorCommissionMapper commissionMapper;
    @Autowired
    TradingDateMapper dateMapper;

    @Override
    public int insert(InvestorCommission commission) {
        return commissionMapper.insertSelective(commission);
    }

    @Override
    public void parseExcel(MultipartFile file, String startDate, String endDate) throws Exception{

            Workbook workbook = Workbook.getWorkbook(file.getInputStream());
            //手续费sheet
            Sheet commissionSheet = workbook.getSheet(0);
            int rows = commissionSheet.getRows();
            List<InvestorCommission> list = new ArrayList<>();
            for (int i = 3; i < rows; i++) {
                String investorNum = commissionSheet.getRow(i)[3].getContents();
                if (!StringUtils.isEmpty(investorNum)) {
                    InvestorCommission commission = new InvestorCommission();
                    //交易所代码
                    commission.setExchange(commissionSheet.getRow(i)[0].getContents());
                    //合约代码
                    commission.setInstrumentId(commissionSheet.getRow(i)[1].getContents());
                    //投资者范围 2

                    //投资者代码 3
                    commission.setInvestorNo(investorNum);
                    //开仓手续费(按金额) 4
                    NumberCell numberCell = (NumberCell)commissionSheet.getRow(i)[4];
                    BigDecimal openRationNum = new BigDecimal(numberCell.getValue()).setScale(10,RoundingMode.HALF_UP);
                    commission.setOpenRatio(openRationNum);

                    //平仓手续费(按金额)
                    double closeRatio = ((NumberCell)commissionSheet.getRow(i)[5]).getValue();
                    BigDecimal closeRatioNum = new BigDecimal(closeRatio).setScale(10,RoundingMode.HALF_UP);
                    commission.setCloseRatio(closeRatioNum);
                    //日内开仓手续费(按金额)
                    double indayOpenRatio =((NumberCell)commissionSheet.getRow(i)[6]).getValue();
                    BigDecimal indayOpenRatioNum = new BigDecimal(indayOpenRatio).setScale(10,RoundingMode.HALF_UP);
                    commission.setIndayOpenRatio(indayOpenRatioNum);

                    // 平今手续费(按金额)
                    double indatCloseRatio = ((NumberCell)commissionSheet.getRow(i)[7]).getValue();
                    BigDecimal indatCloseRatioNum = new BigDecimal(indatCloseRatio).setScale(10,RoundingMode.HALF_UP);
                    commission.setIndatCloseRatio(indatCloseRatioNum);
                    // 开仓手续费(按手数)
                    double open =((NumberCell)commissionSheet.getRow(i)[8]).getValue();
                    BigDecimal openNum = new BigDecimal(open).setScale(10,RoundingMode.HALF_UP);
                    commission.setOpen(openNum);
                    // 平仓手续费(按手数)
                    double close =((NumberCell) commissionSheet.getRow(i)[9]).getValue();
                    BigDecimal closeNum = new BigDecimal(close).setScale(10,RoundingMode.HALF_UP);
                    commission.setClose(closeNum);

                    // 日内开仓手续费(按手数)
                    double indayOpen = ((NumberCell)commissionSheet.getRow(i)[10]).getValue();
                    BigDecimal indayOPenNum = new BigDecimal(indayOpen).setScale(10,RoundingMode.HALF_UP);
                    commission.setIndayOpen(indayOPenNum);

                    // 平今手续费(按手数)
                    double indayClose =  ((NumberCell)commissionSheet.getRow(i)[11]).getValue();
                    BigDecimal indayCloseNum = new BigDecimal(indayClose).setScale(10,RoundingMode.HALF_UP);
                    commission.setIndayClose(indayCloseNum);
                    // 交割手续费费(按金额)
                    double deliveryRatio = ((NumberCell) commissionSheet.getRow(i)[12]).getValue();
                    BigDecimal deliveryRatioNum = new BigDecimal(deliveryRatio).setScale(10,RoundingMode.HALF_UP);
                    commission.setDeliveryRatio(deliveryRatioNum);
                    // 交割手续费(按手数)
                    double delivery =  ((NumberCell)commissionSheet.getRow(i)[13]).getValue();
                    BigDecimal deliveryNum = new BigDecimal(delivery).setScale(10,RoundingMode.HALF_UP);
                    commission.setDelivery(deliveryNum);
                    // 交易日
                    //String tradeDate=commissionSheet.getRow(i)[14].getContents().trim();
                    //commission.setTradeDate(tradeDate);

                    list.add(commission);

                }
            }

            saveOrUpdate(list, startDate, endDate);


    }

    public void saveOrUpdate(List<InvestorCommission> list, String startDate, String endDate) {
        //查找startDate和endDate之间的交易日
        List<TradingDate> dateList = dateMapper.selectByStartAndEnd(startDate, endDate);
        dateList.forEach(date -> {

            list.forEach(commission -> {
                String tradeDate = date.getTyear() + date.getTmonth() + date.getTday();
                commission.setTradeDate(tradeDate);
                //先删除该条数据
                commissionMapper.deleteByInvestorAndDate(commission.getInvestorNo(), tradeDate,commission.getInstrumentId());
                //插入该条数据
                commissionMapper.insertSelective(commission);

            });


        });

    }
}
