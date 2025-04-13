package cn.herbal.visualization.mapper;

import org.apache.ibatis.jdbc.SQL;

public class TReturnSqlProvider {

    public String selectByIdWithParentsRecursive(Long id) {
        return new SQL() {{
            SELECT("*");
            FROM("t_return");
            WHERE("return_id = #{id}");
        }}.toString();
    }

    public String selectParentsRecursive(Long parentId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_return");
            WHERE("parent_id = #{parentId}");
        }}.toString();
    }
}
