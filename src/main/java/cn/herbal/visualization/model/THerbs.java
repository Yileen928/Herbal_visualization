package cn.herbal.visualization.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 药材详细表
 * @TableName t_herbs
 */
@TableName(value ="t_herbs")
@Data
public class THerbs {
    /**
     * 药材ID
     */
    @TableId(type = IdType.AUTO)
    private Long herbId;

    /**
     * 药材名称
     */
    private String herbName;

    /**
     * 标准收载药材名
     */
    private String standardName;

    /**
     * 药材图片
     */
    private String image;

    /**
     * 来源
     */
    private String source;

    /**
     * 全国主产地
     */
    private String nationwideOrigin;

    /**
     * 云南主产地
     */
    private String yunnanOrigin;

    /**
     * 药用部分（根、茎、叶）
     */
    private String medicinalPart;

    /**
     * 药材描述（性状）
     */
    private String description;

    /**
     * 药味
     */
    private String flavor;

    /**
     * 药经
     */
    private String theory;

    /**
     * 炮制方法
     */
    private String preparation;

    /**
     * 功效
     */
    private String efficacy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 整型预留1
     */
    private Long bigint1;

    /**
     * 整型预留2
     */
    private Long bigint2;

    /**
     * 整型预留3
     */
    private Long bigint3;

    /**
     * 整型预留4
     */
    private Long bigint4;

    /**
     * 小数预留1
     */
    private Integer decimal1;

    /**
     * 小数预留2
     */
    private Integer decimal2;

    /**
     * 小数预留3
     */
    private Integer decimal3;

    /**
     * 小数预留4
     */
    private Integer decimal4;

    /**
     * 字符预留1
     */
    private String varchar1;

    /**
     * 字符预留2
     */
    private String varchar2;

    /**
     * 字符预留3
     */
    private String varchar3;

    /**
     * 字符预留4
     */
    private String varchar4;

    /**
     * 时间预留1
     */
    private Date datetime1;

    /**
     * 时间预留2
     */
    private Date datetime2;

    /**
     * 时间预留3
     */
    private Date datetime3;

    /**
     * 时间预留4
     */
    private Date datetime4;

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
        THerbs other = (THerbs) that;
        return (this.getHerbId() == null ? other.getHerbId() == null : this.getHerbId().equals(other.getHerbId()))
            && (this.getHerbName() == null ? other.getHerbName() == null : this.getHerbName().equals(other.getHerbName()))
            && (this.getStandardName() == null ? other.getStandardName() == null : this.getStandardName().equals(other.getStandardName()))
            && (this.getImage() == null ? other.getImage() == null : this.getImage().equals(other.getImage()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getNationwideOrigin() == null ? other.getNationwideOrigin() == null : this.getNationwideOrigin().equals(other.getNationwideOrigin()))
            && (this.getYunnanOrigin() == null ? other.getYunnanOrigin() == null : this.getYunnanOrigin().equals(other.getYunnanOrigin()))
            && (this.getMedicinalPart() == null ? other.getMedicinalPart() == null : this.getMedicinalPart().equals(other.getMedicinalPart()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getFlavor() == null ? other.getFlavor() == null : this.getFlavor().equals(other.getFlavor()))
            && (this.getTheory() == null ? other.getTheory() == null : this.getTheory().equals(other.getTheory()))
            && (this.getPreparation() == null ? other.getPreparation() == null : this.getPreparation().equals(other.getPreparation()))
            && (this.getEfficacy() == null ? other.getEfficacy() == null : this.getEfficacy().equals(other.getEfficacy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getBigint1() == null ? other.getBigint1() == null : this.getBigint1().equals(other.getBigint1()))
            && (this.getBigint2() == null ? other.getBigint2() == null : this.getBigint2().equals(other.getBigint2()))
            && (this.getBigint3() == null ? other.getBigint3() == null : this.getBigint3().equals(other.getBigint3()))
            && (this.getBigint4() == null ? other.getBigint4() == null : this.getBigint4().equals(other.getBigint4()))
            && (this.getDecimal1() == null ? other.getDecimal1() == null : this.getDecimal1().equals(other.getDecimal1()))
            && (this.getDecimal2() == null ? other.getDecimal2() == null : this.getDecimal2().equals(other.getDecimal2()))
            && (this.getDecimal3() == null ? other.getDecimal3() == null : this.getDecimal3().equals(other.getDecimal3()))
            && (this.getDecimal4() == null ? other.getDecimal4() == null : this.getDecimal4().equals(other.getDecimal4()))
            && (this.getVarchar1() == null ? other.getVarchar1() == null : this.getVarchar1().equals(other.getVarchar1()))
            && (this.getVarchar2() == null ? other.getVarchar2() == null : this.getVarchar2().equals(other.getVarchar2()))
            && (this.getVarchar3() == null ? other.getVarchar3() == null : this.getVarchar3().equals(other.getVarchar3()))
            && (this.getVarchar4() == null ? other.getVarchar4() == null : this.getVarchar4().equals(other.getVarchar4()))
            && (this.getDatetime1() == null ? other.getDatetime1() == null : this.getDatetime1().equals(other.getDatetime1()))
            && (this.getDatetime2() == null ? other.getDatetime2() == null : this.getDatetime2().equals(other.getDatetime2()))
            && (this.getDatetime3() == null ? other.getDatetime3() == null : this.getDatetime3().equals(other.getDatetime3()))
            && (this.getDatetime4() == null ? other.getDatetime4() == null : this.getDatetime4().equals(other.getDatetime4()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHerbId() == null) ? 0 : getHerbId().hashCode());
        result = prime * result + ((getHerbName() == null) ? 0 : getHerbName().hashCode());
        result = prime * result + ((getStandardName() == null) ? 0 : getStandardName().hashCode());
        result = prime * result + ((getImage() == null) ? 0 : getImage().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getNationwideOrigin() == null) ? 0 : getNationwideOrigin().hashCode());
        result = prime * result + ((getYunnanOrigin() == null) ? 0 : getYunnanOrigin().hashCode());
        result = prime * result + ((getMedicinalPart() == null) ? 0 : getMedicinalPart().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getFlavor() == null) ? 0 : getFlavor().hashCode());
        result = prime * result + ((getTheory() == null) ? 0 : getTheory().hashCode());
        result = prime * result + ((getPreparation() == null) ? 0 : getPreparation().hashCode());
        result = prime * result + ((getEfficacy() == null) ? 0 : getEfficacy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getBigint1() == null) ? 0 : getBigint1().hashCode());
        result = prime * result + ((getBigint2() == null) ? 0 : getBigint2().hashCode());
        result = prime * result + ((getBigint3() == null) ? 0 : getBigint3().hashCode());
        result = prime * result + ((getBigint4() == null) ? 0 : getBigint4().hashCode());
        result = prime * result + ((getDecimal1() == null) ? 0 : getDecimal1().hashCode());
        result = prime * result + ((getDecimal2() == null) ? 0 : getDecimal2().hashCode());
        result = prime * result + ((getDecimal3() == null) ? 0 : getDecimal3().hashCode());
        result = prime * result + ((getDecimal4() == null) ? 0 : getDecimal4().hashCode());
        result = prime * result + ((getVarchar1() == null) ? 0 : getVarchar1().hashCode());
        result = prime * result + ((getVarchar2() == null) ? 0 : getVarchar2().hashCode());
        result = prime * result + ((getVarchar3() == null) ? 0 : getVarchar3().hashCode());
        result = prime * result + ((getVarchar4() == null) ? 0 : getVarchar4().hashCode());
        result = prime * result + ((getDatetime1() == null) ? 0 : getDatetime1().hashCode());
        result = prime * result + ((getDatetime2() == null) ? 0 : getDatetime2().hashCode());
        result = prime * result + ((getDatetime3() == null) ? 0 : getDatetime3().hashCode());
        result = prime * result + ((getDatetime4() == null) ? 0 : getDatetime4().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", herbId=").append(herbId);
        sb.append(", herbName=").append(herbName);
        sb.append(", standardName=").append(standardName);
        sb.append(", image=").append(image);
        sb.append(", source=").append(source);
        sb.append(", nationwideOrigin=").append(nationwideOrigin);
        sb.append(", yunnanOrigin=").append(yunnanOrigin);
        sb.append(", medicinalPart=").append(medicinalPart);
        sb.append(", description=").append(description);
        sb.append(", flavor=").append(flavor);
        sb.append(", theory=").append(theory);
        sb.append(", preparation=").append(preparation);
        sb.append(", efficacy=").append(efficacy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", bigint1=").append(bigint1);
        sb.append(", bigint2=").append(bigint2);
        sb.append(", bigint3=").append(bigint3);
        sb.append(", bigint4=").append(bigint4);
        sb.append(", decimal1=").append(decimal1);
        sb.append(", decimal2=").append(decimal2);
        sb.append(", decimal3=").append(decimal3);
        sb.append(", decimal4=").append(decimal4);
        sb.append(", varchar1=").append(varchar1);
        sb.append(", varchar2=").append(varchar2);
        sb.append(", varchar3=").append(varchar3);
        sb.append(", varchar4=").append(varchar4);
        sb.append(", datetime1=").append(datetime1);
        sb.append(", datetime2=").append(datetime2);
        sb.append(", datetime3=").append(datetime3);
        sb.append(", datetime4=").append(datetime4);
        sb.append("]");
        return sb.toString();
    }
}