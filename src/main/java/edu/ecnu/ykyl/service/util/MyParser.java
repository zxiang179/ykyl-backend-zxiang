package edu.ecnu.ykyl.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MyParser {


	/**
	 * 根据知识点id来删除对应的历史记录
	 * @param json
	 * @param kpId
	 * @return
	 */
	public static String removeJsonHistByKnowledgePointId(String jsonHist, int kpId) {
		Map<String, Object> map = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (!"".equals(jsonHist)) {
			JSONArray jsonArray = JSONArray.fromObject(jsonHist);
			JSONObject jsonObj = null;
			int size = jsonArray.size();
			for (int i = 0; i < size; i++) {
				map = new HashMap<String, Object>();
				jsonObj = jsonArray.getJSONObject(i);
				int kId = (int) jsonObj.get("kpId");
				if (kId != kpId) {
					map.put("answer", jsonObj.get("answer"));
					map.put("qid", jsonObj.get("qid"));
					map.put("status", jsonObj.get("status"));
					map.put("kpId", jsonObj.get("kpId"));
					list.add(map);
				}
			}
		} else {
			return null;
		}
		// list->json->string->存入数据库
		JSONArray jsonArray = JSONArray.fromObject(list);
		String newHist = jsonArray.toString();
		return newHist;
	}

	public static Map<Integer, Integer> parserJsonAnswer(String json) {
		// String json="[{resAnswer:'{1:1,2:0,3:4,4:1}'}]";
		Map<String, String> smap = new HashMap<String, String>();
		JSONArray jsonArray = JSONArray.fromObject(json);
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		smap.put("resAnswer", (String) jsonObject.get("resAnswer"));

		String str = smap.get("resAnswer");
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();

		// String str="{'1':1,'2':0,'3':4,'4':1}";
		str = str.replace("{", "");
		str = str.replace("}", "");
		String[] split = str.split("\\,");
		int length = split.length;
		int i = 0;
		int j = 0;
		for (String s : split) {
			s = s.replace("'", "");
			System.out.println(s);
			String[] split2 = s.split("\\:");
			i = Integer.parseInt(split2[0]);
			j = Integer.parseInt(split2[1]);
			map.put(i, j);
			treeMap.put(i, j);
			System.out.println("---" + i);
			System.out.println("-----" + j);
		}
		System.out.println("final answer:" + map);
		return treeMap;
	}

	/*
	 * public static Map<Integer,Integer> parserStringAnswer(String str){
	 * Map<Integer,Integer> map = new HashMap<Integer,Integer>(); // String
	 * str="{'1':2,'2':0,'3':4,'4':1}"; str=str.replace("{", "");
	 * str=str.replace("}", ""); String[] split = str.split("\\,"); int
	 * length=split.length; int i=0; int j=0; for(String s:split){
	 * s=s.replace("'", ""); String[] split2 = s.split("\\:");
	 * i=Integer.parseInt(split2[0]); map.put(i, j); } return map; }
	 */
	
/*	@Test
	public void test(){
		String pkhist = "{'success':1,'fail':1,'info':'还差30场晋级Lv2'}";
		JSONObject fromObject = JSONObject.fromObject(pkhist);
		Integer success = (int)fromObject.get("success");
		System.out.println(success);
	}*/
	
	
	public static Map<String,String> parserPKHist(String pkHist){
		Map<String, String> map = new HashMap<String,String>();
		pkHist = "{"+pkHist+"}";
		System.out.println(pkHist);
		JSONObject jsonObj = null;
		jsonObj = JSONObject.fromObject(pkHist);
		Object suc = jsonObj.get("success");
		Integer success = (int)jsonObj.get("success");
		Integer fail = (int)jsonObj.get("fail");
		
		map.put("success", success.toString());
		map.put("fail", fail.toString());
		map.put("info", "还差30场晋级Lv2");
		return map;
	}

	public static List<Map<String, Object>> parserJsonHist(String hist) {
		// 将数据库中得到的hist字符串转换为list集合
		Map<String, Object> map = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		System.out.println(hist);
		if (!"".equals(hist)) {
			JSONArray jsonArray = JSONArray.fromObject(hist);
			int size = jsonArray.size();
			JSONObject jsonObj = null;
			for (int i = 0; i < size; i++) {
				map = new HashMap<String, Object>();
				jsonObj = jsonArray.getJSONObject(i);
				map.put("answer", jsonObj.get("answer"));
				map.put("qid", jsonObj.get("qid"));
				map.put("status", jsonObj.get("status"));
				map.put("kpId", jsonObj.get("kpId"));
				list.add(map);
			}
			return list;
		} else {
			return list;
		}

	}

}
