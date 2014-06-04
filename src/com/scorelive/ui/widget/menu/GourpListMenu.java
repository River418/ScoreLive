package com.scorelive.ui.widget.menu;

import com.scorelive.ui.widget.dialog.BaseDialog;

public class GourpListMenu extends BaseDialog{
	
	private MenuItemClickListener mListener;
	
	public GourpListMenu(){
		
	}
	
	
	public void setItemClickListener(MenuItemClickListener listener){
		this.mListener = listener;
	}

	public interface MenuItemClickListener{
		public void onItemClick();
	}
}
