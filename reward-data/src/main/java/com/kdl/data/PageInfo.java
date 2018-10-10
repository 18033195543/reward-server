package com.kdl.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageInfo {
	private Integer page = 1;// 当前页编号
	private int dbIndex = 0;// 起始行，通常该属性通过pageNo和pageSize计算得到
	private Integer pageSize = 5;// 每页显示条数
	private int totalReacordNumber;// 总共的记录条数
	private int totalPageNumber;// 总共的页数，通过总共的记录条数以及每页大小计算而得
}
