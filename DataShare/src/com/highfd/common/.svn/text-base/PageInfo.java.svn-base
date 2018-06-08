package com.highfd.common;

import java.util.List;


public class PageInfo<T> {
	
		//数据
		List<T> list;
		
		//总记录数
		int recordCount;		
		//总页数
		int pageCount;
		
		//当前页号
		int currentPage=1;
		
		public PageInfo() {
			super();
		}

		public PageInfo(int currentPage) {
			super();
			this.currentPage = currentPage;
		}

		//每页记录数；
		int pageSize=8;

		public List<T> getList() {
			return list;
		}

		public void setList(List<T> list) {
			this.list = list;
		}

		public int getRecordCount() {
			return recordCount;
		}

		public void setRecordCount(int recordCount) {
			this.recordCount = recordCount;
		}

		public int getPageCount() {
			return pageCount;
		}

		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}

		public int getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
}
