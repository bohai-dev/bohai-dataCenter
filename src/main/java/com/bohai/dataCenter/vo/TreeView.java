package com.bohai.dataCenter.vo;

import java.util.List;

/**
 * bootstrap-treeview
 * 返回 给前端JSON格式
 * @author caojia
 */
public class TreeView {
	
	/**
	 * 菜单文本内容
	 */
	private String text;
	
	/**
	 * 菜单URL
	 */
	private String href;
	
	/**
	 * 子树
	 */
	private List<TreeView> nodes;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<TreeView> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeView> nodes) {
		this.nodes = nodes;
	} 
	
}
