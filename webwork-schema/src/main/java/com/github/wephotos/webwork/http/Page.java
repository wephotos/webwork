package com.github.wephotos.webwork.http;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页数据
 * @author TQ
 *
 * @param <E>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Page<E> {

	/**
	 * 总数
	 */
	private int count;
	/**
	 * 当前页数据
	 */
	private List<E> data;
}
