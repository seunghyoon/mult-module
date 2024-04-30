package com.hcl.carehe.common.mvc;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;


public class PageController {

	@Value("${web.config.default-page-size}")
	private String defaultPageSize;

	protected void initPageConfig(Map<String, Object> params) {

    	String currentPage = (String)params.get("currentPage");
		String pageSize = (String)params.get("pageSize");

		if (currentPage == null || currentPage.equals("")) {
			currentPage = "1";
		}

		if (pageSize == null || pageSize.equals("")) {
			pageSize = defaultPageSize;
		}

		int nPage = Integer.parseInt(currentPage);

		int nSize = Integer.parseInt(pageSize);

		int startRow = (nPage == 1) ? 1 : ((nPage - 1) * nSize) + 1;
		int endRow = startRow + nSize - 1;

		params.put("currentPage", nPage);
		params.put("pageSize", pageSize);
		params.put("startRow", startRow);
		params.put("endRow", endRow);
	}

}
