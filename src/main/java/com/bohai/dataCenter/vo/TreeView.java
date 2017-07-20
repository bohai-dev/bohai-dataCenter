package com.bohai.dataCenter.vo;

import java.util.List;

/**
 * bootstrap-treeview
 * 前端JSON格式的treeView
 * @author caojia
 */
public class TreeView<T> {
	
	/**
	 * 菜单文本内容
	 */
	private String text;
	
	/**
	 * 承载的数据
	 */
	private T data;
	
	/**
	 * 菜单URL
	 */
	private String href;
	
	/**
	 * 是否可选择
	 */
	private boolean selectable;
	
	/**
	 * 是否显示checkbox框
	 */
	private boolean showCheckbox;
	
	/**
	 * 节点状态子类
	 */
	private State state;
	
	/**
	 * 标记
	 */
	private List<String> tags;
	
	/**
	 * 子树
	 */
	private List<TreeView<T>> nodes;
	
	
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

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public boolean isShowCheckbox() {
		return showCheckbox;
	}

	public void setShowCheckbox(boolean showCheckbox) {
		this.showCheckbox = showCheckbox;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<TreeView<T>> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeView<T>> nodes) {
		this.nodes = nodes;
	} 
	
	
	//节点的状态类
	public static class State{
		
		private boolean checked;
		
		private boolean disabled;
		
		private boolean expanded;
		
		private boolean selected;

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		public boolean isDisabled() {
			return disabled;
		}

		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}

		public boolean isExpanded() {
			return expanded;
		}

		public void setExpanded(boolean expanded) {
			this.expanded = expanded;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}
		
	}


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
	
	
}
