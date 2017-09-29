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
	
	private static final String BASEURL = "http://localhost:8080/solr";

	@Test
	public void test01() throws Exception {
		// ��������, Ĭ�����Ӿ��Ǻ�1, �����Ҫ���Ӻ�2, ���Խ�·����Ϊ
		// http://localhost:8080//solr/collection2
		SolrServer server = new HttpSolrServer(BASEURL);

		// ����SolrInputDocument����
		SolrInputDocument document = new SolrInputDocument();

		// D:\develop\solr\solrhome\collection1\conf data-config.xml��û�������
		// document.addField("height", "180");
		document.addField("id", "haha");
		document.addField("name", "����");

		server.add(document, 100);
	}

	@Test
	public void test02() throws Exception {
		// ��������
		SolrServer server = new HttpSolrServer(BASEURL);

		// ȫ��ɾ��
		server.deleteByQuery("*:*", 100);
	}

	@Test
	public void test03() throws Exception {
		// ��������
		SolrServer server = new HttpSolrServer(BASEURL);
		// ������ѯ����
		SolrQuery query = new SolrQuery();
		
		//���ò�ѯ����q, ��ʽΪ: ����: ����(�з������Լ�д)
		query.set("q", "����");
		//��Χ��ѯ
		query.set("fq", "product_price:[* TO 10]");
		//�����ѯ
		query.setSort("product_price",ORDER.asc);
		//������ʼ����
		query.setStart(0);
		//���ò�ѯ����
		query.setRows(15);
		//����Ҫ��ѯ����
		query.set("f1","product_name,id,product_price");
		//����Ĭ�ϲ�ѯ
		query.set("df", "product_name");
		
		//���ø���
		query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<span color='red'>");
		query.setHighlightSimplePost("</span>");
		
		
		// ִ�в�ѯ
		QueryResponse response = server.query(query);
		// ��ȡ�����
		SolrDocumentList results = response.getResults();
		//��ȡ���������
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		//��ȡ������
		long numFound = results.getNumFound();
		System.out.println(numFound);
		/**
		 * "product_catalog_name": "��Ĭ�ӻ�", 
		 * "product_price": 18.9,
		 * "product_name": "��������ɫ�����ź�� 8���ⶤ�ű��ҹ�2066", 
		 * "id": "1",
		 * "product_picture": "2014032613103438.png", 
		 * "_version_": 1579861591688478700
		 */
		// ����
		for (SolrDocument document : results) {
			System.out.println("��Ʒid" + "\t" + "" + "" + document.get("id"));
			System.out.println("��Ʒ����"+"\t"+""+""+document.get("product_catalog_name"));
			System.out.println("��Ʒ�۸�"+"\t"+""+""+document.get("product_price"));
			
			Map<String, List<String>> map = highlighting.get(document.get("id"));
			List<String> list = map.get("product_name");
			System.out.println(list);
			
			System.out.println("**");
			
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
