package com.ssang.gtd.utils.page;

import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

public class PageUtil {
    private SqlSession sqlSession;
    public Map<String, Object> pageing(String sqlMap, Map<String, Object> params){
        return list(sqlMap,params,sqlSession);
    }
    private Map<String, Object> list(String sqlMap, Map<String, Object> params, SqlSession session){
        int records = (Integer)session.selectOne(sqlMap + "TotalCount",params);// 전체 리스트 갯수
        int pageSize = convertPageKeyInteger(params,"pageSize",20);//리스트 row
        int page = convertPageKeyInteger(params,"page",1);//페이지 번호

        params.put("pageSize",pageSize);
        params.put("page",page);

        int totalPage = (records - (records % pageSize)) / pageSize;// 전체 페이지 수
        if(records % pageSize > 0) {
            totalPage++;
        }
        params.put("startRow",(page -1)*pageSize+1);
        params.put("endRow",page*pageSize);

        Map<String, Object>map = new HashMap<String, Object>();
        map.put("list",session.selectList(sqlMap,params));//결과 리스트
        map.put("page",page);//현재페이지
        //map.put("total",total);//전체 건수
        map.put("totalPage",totalPage);//전체 페이지 건수

        return map;
    }
    private int convertPageKeyInteger(Map<String,Object> map, String key, int initValue){
        if(!map.containsKey(key)) return initValue;
        Object val = map.get(key);
        if(val == null)return initValue;
        if(val instanceof Integer){
            int i = Integer.parseInt(val.toString());
            return i == 0?initValue:i;
        }
        if(val instanceof String){
            int ret = Integer.parseInt((String)val);
            return ret;
        }
        return initValue;
    }
}
