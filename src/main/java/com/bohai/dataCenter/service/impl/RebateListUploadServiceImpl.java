package com.bohai.dataCenter.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bohai.dataCenter.controller.exception.BohaiException;
import com.bohai.dataCenter.entity.RebateList;
import com.bohai.dataCenter.persistence.RebateListMapper;
import com.bohai.dataCenter.service.FileUploadService;

import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

@Service("rebateListUploadService")
public class RebateListUploadServiceImpl implements FileUploadService {
	
	static Logger logger = Logger.getLogger(RebateListUploadServiceImpl.class);
	
	@Autowired
	private RebateListMapper rebateListMapper;

	@Override
	public void upload(MultipartFile file, Object... objects) throws BohaiException {

		logger.debug("返利息名单上传");
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file.getInputStream());
		}catch(Exception e){
			logger.error("返利息名单上传失败",e);
			throw new BohaiException("", "读取返利息名单失败");
		}
		
		Sheet rebateSheet = workbook.getSheet(0);
		
		//先清空历史数据
		this.rebateListMapper.deleteAll();
		
		for(int i = 1;i<rebateSheet.getRows();i++){
			//返利息名单
			RebateList rebateList = new RebateList();
			//分类
			rebateList.setCate(rebateSheet.getCell(0, i).getContents());
			//分类说明
			rebateList.setCateDesp(rebateSheet.getCell(1, i).getContents());
			//营销人员编号
			rebateList.setMarketerNo(rebateSheet.getCell(2, i).getContents());
			//营销人员姓名
			rebateList.setMarketerName(rebateSheet.getCell(3, i).getContents());
			//居间人编号
			rebateList.setMdeiatorNo(rebateSheet.getCell(4, i).getContents());
			//居间人姓名
			rebateList.setMediatorName(rebateSheet.getCell(5, i).getContents());
			//客户编号
			rebateList.setInvestorNo(rebateSheet.getCell(6, i).getContents());
			//客户姓名
			rebateList.setInvestorName(rebateSheet.getCell(7, i).getContents());
			//加减返佣金额
			rebateList.setRebateAmount(rebateSheet.getCell(8, i).getContents());
			//特例分类
			rebateList.setSpecialCate(rebateSheet.getCell(9, i).getContents());
			//特例说明
			rebateList.setSpecialDesp(rebateSheet.getCell(10, i).getContents());
			//备注
			rebateList.setRemark(rebateSheet.getCell(11, i).getContents());
			//固定比例
			String fix = "";
			Cell fixCell= rebateSheet.getCell(14, i);
			if(fixCell.getType() == CellType.NUMBER){
				NumberCell numberCell = (NumberCell) fixCell; 
				fix = numberCell.getValue()+"";
		    }else {
				fix = fixCell.getContents();
			}
			logger.debug(fix);
			rebateList.setFixProportion(fix);
			//日均权益
			String dailyRights = rebateSheet.getCell(15, i).getContents();
			rebateList.setDailyRights(dailyRights);
			//自定义区间
			if(!StringUtils.isEmpty(dailyRights)&& dailyRights.equals("1")){
				int index = 22;
				JSONObject json = new JSONObject();
				while(true){
					//自定义区间从22列开始
					String custom = rebateSheet.getCell(index++, i).getContents();
					if(StringUtils.isEmpty(custom)){
						break;
					}
					//浮动利率
					String value = ((NumberCell)rebateSheet.getCell(index++, i)).getValue()+"";
					json.put(custom, value);
				}
				rebateList.setCustomInterval(json.toJSONString());
			}
			
			try {
				rebateListMapper.insertSelective(rebateList);
			} catch (Exception e) {
				logger.error("保存返利息名单失败",e);
				throw new BohaiException("", "保存返利息名单失败");
			}
			
		}

	}

}
