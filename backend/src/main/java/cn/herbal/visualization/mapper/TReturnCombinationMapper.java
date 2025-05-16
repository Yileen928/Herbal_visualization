package cn.herbal.visualization.mapper;

import cn.herbal.visualization.model.TReturnCombination;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ack27
 * @description 针对表【t_return_combination(药材—归经表)】的数据库操作Mapper
 * @createDate 2025-03-25 08:58:06
 * @Entity cn.herbal.visualization.model.TReturnCombination
 */
@Mapper
public interface TReturnCombinationMapper extends BaseMapper<TReturnCombination> {
    @Select("""
            SELECT rc.*, h.*, r.*, pr.return_id AS parent_return_id, pr.return_name AS parent_return_name
            FROM t_return_combination rc
            LEFT JOIN t_herbs h ON rc.herb_id = h.herb_id
            LEFT JOIN t_return r ON rc.return_id = r.return_id
            LEFT JOIN t_return pr ON r.parent_id = pr.return_id
            """)
    @Results({
            @Result(property = "combinationId", column = "combination_id"),
            @Result(property = "herbId", column = "herb_id"),
            @Result(property = "returnId", column = "return_id"),
            @Result(property = "createTime", column = "rc.create_time"),
            @Result(property = "updateTime", column = "rc.update_time"),
            @Result(property = "reservedBigint1", column = "rc.reserved_bigint1"),
            @Result(property = "reservedBigint2", column = "rc.reserved_bigint2"),
            @Result(property = "reservedBigint3", column = "rc.reserved_bigint3"),
            @Result(property = "reservedBigint4", column = "rc.reserved_bigint4"),
            @Result(property = "reservedDecimal1", column = "rc.reserved_decimal1"),
            @Result(property = "reservedDecimal2", column = "rc.reserved_decimal2"),
            @Result(property = "reservedDecimal3", column = "rc.reserved_decimal3"),
            @Result(property = "reservedDecimal4", column = "rc.reserved_decimal4"),
            @Result(property = "reservedVarchar1", column = "rc.reserved_varchar1"),
            @Result(property = "reservedVarchar2", column = "rc.reserved_varchar2"),
            @Result(property = "reservedVarchar3", column = "rc.reserved_varchar3"),
            @Result(property = "reservedVarchar4", column = "rc.reserved_varchar4"),
            @Result(property = "reservedDatetime1", column = "rc.reserved_datetime1"),
            @Result(property = "reservedDatetime2", column = "rc.reserved_datetime2"),
            @Result(property = "reservedDatetime3", column = "rc.reserved_datetime3"),
            @Result(property = "reservedDatetime4", column = "rc.reserved_datetime4"),
            @Result(property = "herb", column = "herb_id", one = @One(select = "cn.herbal.visualization.mapper.THerbsMapper.selectById")),
            @Result(property = "ret", column = "return_id", one = @One(select = "cn.herbal.visualization.mapper.TReturnMapper.selectByIdWithParentsRecursive")),
            @Result(property = "parentRet.returnId", column = "parent_return_id"),
            @Result(property = "parentRet.returnName", column = "parent_return_name")
    })
    List<TReturnCombination> findAll();

    @Select("""
            SELECT rc.*, h.*, r.*, pr.return_id AS parent_return_id, pr.return_name AS parent_return_name
            FROM t_return_combination rc
            LEFT JOIN t_herbs h ON rc.herb_id = h.herb_id
            LEFT JOIN t_return r ON rc.return_id = r.return_id
            LEFT JOIN t_return pr ON r.parent_id = pr.return_id
            WHERE h.herb_name = #{herbName}
            """)
    @Results({
            @Result(property = "combinationId", column = "combination_id"),
            @Result(property = "herbId", column = "herb_id"),
            @Result(property = "returnId", column = "return_id"),
            @Result(property = "createTime", column = "rc.create_time"),
            @Result(property = "updateTime", column = "rc.update_time"),
            @Result(property = "reservedBigint1", column = "rc.reserved_bigint1"),
            @Result(property = "reservedBigint2", column = "rc.reserved_bigint2"),
            @Result(property = "reservedBigint3", column = "rc.reserved_bigint3"),
            @Result(property = "reservedBigint4", column = "rc.reserved_bigint4"),
            @Result(property = "reservedDecimal1", column = "rc.reserved_decimal1"),
            @Result(property = "reservedDecimal2", column = "rc.reserved_decimal2"),
            @Result(property = "reservedDecimal3", column = "rc.reserved_decimal3"),
            @Result(property = "reservedDecimal4", column = "rc.reserved_decimal4"),
            @Result(property = "reservedVarchar1", column = "rc.reserved_varchar1"),
            @Result(property = "reservedVarchar2", column = "rc.reserved_varchar2"),
            @Result(property = "reservedVarchar3", column = "rc.reserved_varchar3"),
            @Result(property = "reservedVarchar4", column = "rc.reserved_varchar4"),
            @Result(property = "reservedDatetime1", column = "rc.reserved_datetime1"),
            @Result(property = "reservedDatetime2", column = "rc.reserved_datetime2"),
            @Result(property = "reservedDatetime3", column = "rc.reserved_datetime3"),
            @Result(property = "reservedDatetime4", column = "rc.reserved_datetime4"),
            @Result(property = "herb", column = "herb_id", one = @One(select = "cn.herbal.visualization.mapper.THerbsMapper.selectById")),
            @Result(property = "ret", column = "return_id", one = @One(select = "cn.herbal.visualization.mapper.TReturnMapper.selectByIdWithParentsRecursive")),
            @Result(property = "parentRet.returnId", column = "parent_return_id"),
            @Result(property = "parentRet.returnName", column = "parent_return_name")
    })
    List<TReturnCombination> findByHerbName(@Param("herbName") String herbName);
}