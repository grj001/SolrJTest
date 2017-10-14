package com.zhiyou100.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;


public class SolrJTest {
	
	private static final String BASEURL = "http://localhost:8090/solr";

	@Test
	public void test01() throws Exception {
		// 创建连接, 默认连接就是核1, 如果需要连接核2, 可以将路径改为
		// http://localhost:8080//solr/collection2
		SolrServer server = new HttpSolrServer(BASEURL);

		// 创建SolrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();

		// D:\develop\solr\solrhome\collection1\conf data-config.xml中没有这个域
		// document.addField("height", "180");
		document.addField("id", "haha");
		document.addField("name", "李乐");

		server.add(document, 100);
	}

	@Test
	public void test02() throws Exception {
		// 创建连接
		SolrServer server = new HttpSolrServer(BASEURL);

		// 全部删除
		server.deleteByQuery("*:*", 100);
	}

	@Test
	public void test03() throws Exception {
		// 创建连接
		SolrServer server = new HttpSolrServer(BASEURL);
		// 创建查询对象
		SolrQuery query = new SolrQuery();
		
		//设置查询条件q, 格式为: 域名: 索引(有分析可以简写)
		query.set("q", "金属");
		//范围查询
		query.set("fq", "product_price:[* TO 10]");
		//排序查询
		query.setSort("product_price",ORDER.asc);
		//设置起始索引
		query.setStart(0);
		//设置查询条数
		query.setRows(15);
		//设置要查询的域
		query.set("f1","product_name,id,product_price");
		//设置默认查询
		query.set("df", "product_name");
		
		//设置高亮
		query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<span color='red'>");
		query.setHighlightSimplePost("</span>");
		
		
		// 执行查询
		QueryResponse response = server.query(query);
		// 获取结果集
		SolrDocumentList results = response.getResults();
		//获取高亮结果集
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		//获取总条数
		long numFound = results.getNumFound();
		System.out.println(numFound);
		/**
		 * "product_catalog_name": "幽默杂货", 
		 * "product_price": 18.9,
		 * "product_name": "花儿朵朵彩色金属门后挂 8钩免钉门背挂钩2066", 
		 * "id": "1",
		 * "product_picture": "2014032613103438.png", 
		 * "_version_": 1579861591688478700
		 */
		// 遍历
		for (SolrDocument document : results) {
			System.out.println("商品id" + "\t" + "" + "" + document.get("id"));
			System.out.println("商品名称"+"\t"+""+""+document.get("product_catalog_name"));
			System.out.println("商品价格"+"\t"+""+""+document.get("product_price"));
			
			Map<String, List<String>> map = highlighting.get(document.get("id"));
			List<String> list = map.get("product_name");
			System.out.println(list);
			                                            
			System.out.println("**");
			
		}
	}

}