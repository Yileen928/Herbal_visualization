package cn.herbal.visualization.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 年份职业列表
 * @TableName t_year_occupation
 */
@TableName(value ="t_year_occupation")
@Data
public class TYearOccupation {
    /**
     * 列表ID
     */
    @TableId(type = IdType.AUTO)
    private Long yearOccupationId;

    /**
     * 年份
     */
    private String occupationYear;

    /**
     * 职业医师
     */
    private String physicianNameTotal;

    /**
     * 药师
     */
    private String pharmacistsNameTotal;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次修改时间
     */
    private Date updateTime;

    /**
     * 整型预留字段
     */
    private Long reservedBigint1;

    /**
     * 整型预留字段
     */
    private Long reservedBigint2;

    /**
     * 整型预留字段
     */
    private Long reservedBigint3;

    /**
     * 整型预留字段
     */
    private Long reservedBigint4;

    /**
     * 小数预留字段
     */
    private BigDecimal reservedDecimal1;

    /**
     * 小数预留字段
     */
    private BigDecimal reservedDecimal2;

    /**
     * 小数预留字段
     */
    private BigDecimal reservedDecimal3;

    /**
     * 小数预留字段
     */
    private BigDecimal reservedDecimal4;

    /**
     * 字符预留字段
     */
    private String reservedVarchar1;

    /**
     * 字符预留字段
     */
    private String reservedVarchar2;

    /**
     * 字符预留字段
     */
    private String reservedVarchar3;

    /**
     * 字符预留字段
     */
    private String reservedVarchar4;

    /**
     * 时间预留字段
     */
    private Date reservedDatetime1;

    /**
     * 时间预留字段
     */
    private Date reservedDatetime2;

    /**
     * 时间预留字段
     */
    private Date reservedDatetime3;

    /**
     * 时间预留字段
     */
    private Date reservedDatetime4;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TYearOccupation other = (TYearOccupation) that;
        return (this.getYearOccupationId() == null ? other.getYearOccupationId() == null : this.getYearOccupationId().equals(other.getYearOccupationId()))
            && (this.getOccupationYear() == null ? other.getOccupationYear() == null : this.getOccupationYear().equals(other.getOccupationYear()))
            && (this.getPhysicianNameTotal() == null ? other.getPhysicianNameTotal() == null : this.getPhysicianNameTotal().equals(other.getPhysicianNameTotal()))
            && (this.getPharmacistsNameTotal() == null ? other.getPharmacistsNameTotal() == null : this.getPharmacistsNameTotal().equals(other.getPharmacistsNameTotal()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getReservedBigint1() == null ? other.getReservedBigint1() == null : this.getReservedBigint1().equals(other.getReservedBigint1()))
            && (this.getReservedBigint2() == null ? other.getReservedBigint2() == null : this.getReservedBigint2().equals(other.getReservedBigint2()))
            && (this.getReservedBigint3() == null ? other.getReservedBigint3() == null : this.getReservedBigint3().equals(other.getReservedBigint3()))
            && (this.getReservedBigint4() == null ? other.getReservedBigint4() == null : this.getReservedBigint4().equals(other.getReservedBigint4()))
            && (this.getReservedDecimal1() == null ? other.getReservedDecimal1() == null : this.getReservedDecimal1().equals(other.getReservedDecimal1()))
            && (this.getReservedDecimal2() == null ? other.getReservedDecimal2() == null : this.getReservedDecimal2().equals(other.getReservedDecimal2()))
            && (this.getReservedDecimal3() == null ? other.getReservedDecimal3() == null : this.getReservedDecimal3().equals(other.getReservedDecimal3()))
            && (this.getReservedDecimal4() == null ? other.getReservedDecimal4() == null : this.getReservedDecimal4().equals(other.getReservedDecimal4()))
            && (this.getReservedVarchar1() == null ? other.getReservedVarchar1() == null : this.getReservedVarchar1().equals(other.getReservedVarchar1()))
            && (this.getReservedVarchar2() == null ? other.getReservedVarchar2() == null : this.getReservedVarchar2().equals(other.getReservedVarchar2()))
            && (this.getReservedVarchar3() == null ? other.getReservedVarchar3() == null : this.getReservedVarchar3().equals(other.getReservedVarchar3()))
            && (this.getReservedVarchar4() == null ? other.getReservedVarchar4() == null : this.getReservedVarchar4().equals(other.getReservedVarchar4()))
            && (this.getReservedDatetime1() == null ? other.getReservedDatetime1() == null : this.getReservedDatetime1().equals(other.getReservedDatetime1()))
            && (this.getReservedDatetime2() == null ? other.getReservedDatetime2() == null : this.getReservedDatetime2().equals(other.getReservedDatetime2()))
            && (this.getReservedDatetime3() == null ? other.getReservedDatetime3() == null : this.getReservedDatetime3().equals(other.getReservedDatetime3()))
            && (this.getReservedDatetime4() == null ? other.getReservedDatetime4() == null : this.getReservedDatetime4().equals(other.getReservedDatetime4()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getYearOccupationId() == null) ? 0 : getYearOccupationId().hashCode());
        result = prime * result + ((getOccupationYear() == null) ? 0 : getOccupationYear().hashCode());
        result = prime * result + ((getPhysicianNameTotal() == null) ? 0 : getPhysicianNameTotal().hashCode());
        result = prime * result + ((getPharmacistsNameTotal() == null) ? 0 : getPharmacistsNameTotal().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getReservedBigint1() == null) ? 0 : getReservedBigint1().hashCode());
        result = prime * result + ((getReservedBigint2() == null) ? 0 : getReservedBigint2().hashCode());
        result = prime * result + ((getReservedBigint3() == null) ? 0 : getReservedBigint3().hashCode());
        result = prime * result + ((getReservedBigint4() == null) ? 0 : getReservedBigint4().hashCode());
        result = prime * result + ((getReservedDecimal1() == null) ? 0 : getReservedDecimal1().hashCode());
        result = prime * result + ((getReservedDecimal2() == null) ? 0 : getReservedDecimal2().hashCode());
        result = prime * result + ((getReservedDecimal3() == null) ? 0 : getReservedDecimal3().hashCode());
        result = prime * result + ((getReservedDecimal4() == null) ? 0 : getReservedDecimal4().hashCode());
        result = prime * result + ((getReservedVarchar1() == null) ? 0 : getReservedVarchar1().hashCode());
        result = prime * result + ((getReservedVarchar2() == null) ? 0 : getReservedVarchar2().hashCode());
        result = prime * result + ((getReservedVarchar3() == null) ? 0 : getReservedVarchar3().hashCode());
        result = prime * result + ((getReservedVarchar4() == null) ? 0 : getReservedVarchar4().hashCode());
        result = prime * result + ((getReservedDatetime1() == null) ? 0 : getReservedDatetime1().hashCode());
        result = prime * result + ((getReservedDatetime2() == null) ? 0 : getReservedDatetime2().hashCode());
        result = prime * result + ((getReservedDatetime3() == null) ? 0 : getReservedDatetime3().hashCode());
        result = prime * result + ((getReservedDatetime4() == null) ? 0 : getReservedDatetime4().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", yearOccupationId=").append(yearOccupationId);
        sb.append(", occupationYear=").append(occupationYear);
        sb.append(", physicianNameTotal=").append(physicianNameTotal);
        sb.append(", pharmacistsNameTotal=").append(pharmacistsNameTotal);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", reservedBigint1=").append(reservedBigint1);
        sb.append(", reservedBigint2=").append(reservedBigint2);
        sb.append(", reservedBigint3=").append(reservedBigint3);
        sb.append(", reservedBigint4=").append(reservedBigint4);
        sb.append(", reservedDecimal1=").append(reservedDecimal1);
        sb.append(", reservedDecimal2=").append(reservedDecimal2);
        sb.append(", reservedDecimal3=").append(reservedDecimal3);
        sb.append(", reservedDecimal4=").append(reservedDecimal4);
        sb.append(", reservedVarchar1=").append(reservedVarchar1);
        sb.append(", reservedVarchar2=").append(reservedVarchar2);
        sb.append(", reservedVarchar3=").append(reservedVarchar3);
        sb.append(", reservedVarchar4=").append(reservedVarchar4);
        sb.append(", reservedDatetime1=").append(reservedDatetime1);
        sb.append(", reservedDatetime2=").append(reservedDatetime2);
        sb.append(", reservedDatetime3=").append(reservedDatetime3);
        sb.append(", reservedDatetime4=").append(reservedDatetime4);
        sb.append("]");
        return sb.toString();
    }
}