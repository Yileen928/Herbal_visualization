package cn.herbal.visualization.mapper;

import cn.herbal.visualization.model.TReturn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author ack27
* @description 针对表【t_return(详细归经表)】的数据库操作Mapper
* @createDate 2025-03-25 08:58:01
* @Entity cn.herbal.visualization.model.TReturn
*/
@Mapper
public interface TReturnMapper extends BaseMapper<TReturn> {
    @Select("SELECT * FROM t_return WHERE return_id = #{id}")
    @Results({
            @Result(property = "returnId", column = "return_id"),
            @Result(property = "returnName", column = "return_name"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "reservedBigint1", column = "reserved_bigint1"),
            @Result(property = "reservedBigint2", column = "reserved_bigint2"),
            @Result(property = "reservedBigint3", column = "reserved_bigint3"),
            @Result(property = "reservedBigint4", column = "reserved_bigint4"),
            @Result(property = "reservedDecimal1", column = "reserved_decimal1"),
            @Result(property = "reservedDecimal2", column = "reserved_decimal2"),
            @Result(property = "reservedDecimal3", column = "reserved_decimal3"),
            @Result(property = "reservedDecimal4", column = "reserved_decimal4"),
            @Result(property = "reservedVarchar1", column = "reserved_varchar1"),
            @Result(property = "reservedVarchar2", column = "reserved_varchar2"),
            @Result(property = "reservedVarchar3", column = "reserved_varchar3"),
            @Result(property = "reservedVarchar4", column = "reserved_varchar4"),
            @Result(property = "reservedDatetime1", column = "reserved_datetime1"),
            @Result(property = "reservedDatetime2", column = "reserved_datetime2"),
            @Result(property = "reservedDatetime3", column = "reserved_datetime3"),
            @Result(property = "reservedDatetime4", column = "reserved_datetime4")
    })
    TReturn selectById(Long id);

    @SelectProvider(type = TReturnSqlProvider.class, method = "selectByIdWithParentsRecursive")
    @Results({
            @Result(property = "returnId", column = "return_id"),
            @Result(property = "returnName", column = "return_name"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "reservedBigint1", column = "reserved_bigint1"),
            @Result(property = "reservedBigint2", column = "reserved_bigint2"),
            @Result(property = "reservedBigint3", column = "reserved_bigint3"),
            @Result(property = "reservedBigint4", column = "reserved_bigint4"),
            @Result(property = "reservedDecimal1", column = "reserved_decimal1"),
            @Result(property = "reservedDecimal2", column = "reserved_decimal2"),
            @Result(property = "reservedDecimal3", column = "reserved_decimal3"),
            @Result(property = "reservedDecimal4", column = "reserved_decimal4"),
            @Result(property = "reservedVarchar1", column = "reserved_varchar1"),
            @Result(property = "reservedVarchar2", column = "reserved_varchar2"),
            @Result(property = "reservedVarchar3", column = "reserved_varchar3"),
            @Result(property = "reservedVarchar4", column = "reserved_varchar4"),
            @Result(property = "reservedDatetime1", column = "reserved_datetime1"),
            @Result(property = "reservedDatetime2", column = "reserved_datetime2"),
            @Result(property = "reservedDatetime3", column = "reserved_datetime3"),
            @Result(property = "reservedDatetime4", column = "reserved_datetime4"),
            @Result(property = "parents", column = "return_id", many = @Many(select = "cn.herbal.visualization.mapper.TReturnMapper.selectParentsRecursive"))
    })
    TReturn selectByIdWithParentsRecursive(@Param("id") Long id);

    @SelectProvider(type = TReturnSqlProvider.class, method = "selectParentsRecursive")
    List<TReturn> selectParentsRecursive(@Param("parentId") Long parentId);
}




