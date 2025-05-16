package cn.herbal.visualization.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 药材信息表
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
     * 药材释名
     */
    private String herbRelease;

    /**
     * 药材科
     */
    private String herbDepartment;

    /**
     * 药材属
     */
    private String herbGenus;

    /**
     * 药材图片1
     */
    private String herbImage1;

    /**
     * 药材图片2
     */
    private String herbImage2;

    /**
     * 药材图片3
     */
    private String herbImage3;

    /**
     * 药材(pc)详细页面左上角图片
     */
    private String herbImageHead;

    /**
     * 药材(pc)详细页面线稿图片
     */
    private String herbImageArt;

    /**
     * 药材(pc)详细页面炮制图片
     */
    private String herbImagePreparation;

    /**
     * 药材图片4
     */
    private String herbImage4;

    /**
     * 药材图片5
     */
    private String herbImage5;

    /**
     * 药材图片6
     */
    private String herbImage6;

    /**
     * 药材图片7
     */
    private String herbImage7;

    /**
     * 药材3D模型视频
     */
    private String herbVideo;

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
     * 主治疾病
     */
    private String indications;

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
        THerbs other = (THerbs) that;
        return (this.getHerbId() == null ? other.getHerbId() == null : this.getHerbId().equals(other.getHerbId()))
            && (this.getHerbName() == null ? other.getHerbName() == null : this.getHerbName().equals(other.getHerbName()))
            && (this.getStandardName() == null ? other.getStandardName() == null : this.getStandardName().equals(other.getStandardName()))
            && (this.getHerbRelease() == null ? other.getHerbRelease() == null : this.getHerbRelease().equals(other.getHerbRelease()))
            && (this.getHerbDepartment() == null ? other.getHerbDepartment() == null : this.getHerbDepartment().equals(other.getHerbDepartment()))
            && (this.getHerbGenus() == null ? other.getHerbGenus() == null : this.getHerbGenus().equals(other.getHerbGenus()))
            && (this.getHerbImage1() == null ? other.getHerbImage1() == null : this.getHerbImage1().equals(other.getHerbImage1()))
            && (this.getHerbImage2() == null ? other.getHerbImage2() == null : this.getHerbImage2().equals(other.getHerbImage2()))
            && (this.getHerbImage3() == null ? other.getHerbImage3() == null : this.getHerbImage3().equals(other.getHerbImage3()))
            && (this.getHerbImageHead() == null ? other.getHerbImageHead() == null : this.getHerbImageHead().equals(other.getHerbImageHead()))
            && (this.getHerbImageArt() == null ? other.getHerbImageArt() == null : this.getHerbImageArt().equals(other.getHerbImageArt()))
            && (this.getHerbImagePreparation() == null ? other.getHerbImagePreparation() == null : this.getHerbImagePreparation().equals(other.getHerbImagePreparation()))
            && (this.getHerbImage4() == null ? other.getHerbImage4() == null : this.getHerbImage4().equals(other.getHerbImage4()))
            && (this.getHerbImage5() == null ? other.getHerbImage5() == null : this.getHerbImage5().equals(other.getHerbImage5()))
            && (this.getHerbImage6() == null ? other.getHerbImage6() == null : this.getHerbImage6().equals(other.getHerbImage6()))
            && (this.getHerbImage7() == null ? other.getHerbImage7() == null : this.getHerbImage7().equals(other.getHerbImage7()))
            && (this.getHerbVideo() == null ? other.getHerbVideo() == null : this.getHerbVideo().equals(other.getHerbVideo()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getNationwideOrigin() == null ? other.getNationwideOrigin() == null : this.getNationwideOrigin().equals(other.getNationwideOrigin()))
            && (this.getYunnanOrigin() == null ? other.getYunnanOrigin() == null : this.getYunnanOrigin().equals(other.getYunnanOrigin()))
            && (this.getMedicinalPart() == null ? other.getMedicinalPart() == null : this.getMedicinalPart().equals(other.getMedicinalPart()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getFlavor() == null ? other.getFlavor() == null : this.getFlavor().equals(other.getFlavor()))
            && (this.getTheory() == null ? other.getTheory() == null : this.getTheory().equals(other.getTheory()))
            && (this.getPreparation() == null ? other.getPreparation() == null : this.getPreparation().equals(other.getPreparation()))
            && (this.getEfficacy() == null ? other.getEfficacy() == null : this.getEfficacy().equals(other.getEfficacy()))
            && (this.getIndications() == null ? other.getIndications() == null : this.getIndications().equals(other.getIndications()))
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
        result = prime * result + ((getHerbId() == null) ? 0 : getHerbId().hashCode());
        result = prime * result + ((getHerbName() == null) ? 0 : getHerbName().hashCode());
        result = prime * result + ((getStandardName() == null) ? 0 : getStandardName().hashCode());
        result = prime * result + ((getHerbRelease() == null) ? 0 : getHerbRelease().hashCode());
        result = prime * result + ((getHerbDepartment() == null) ? 0 : getHerbDepartment().hashCode());
        result = prime * result + ((getHerbGenus() == null) ? 0 : getHerbGenus().hashCode());
        result = prime * result + ((getHerbImage1() == null) ? 0 : getHerbImage1().hashCode());
        result = prime * result + ((getHerbImage2() == null) ? 0 : getHerbImage2().hashCode());
        result = prime * result + ((getHerbImage3() == null) ? 0 : getHerbImage3().hashCode());
        result = prime * result + ((getHerbImageHead() == null) ? 0 : getHerbImageHead().hashCode());
        result = prime * result + ((getHerbImageArt() == null) ? 0 : getHerbImageArt().hashCode());
        result = prime * result + ((getHerbImagePreparation() == null) ? 0 : getHerbImagePreparation().hashCode());
        result = prime * result + ((getHerbImage4() == null) ? 0 : getHerbImage4().hashCode());
        result = prime * result + ((getHerbImage5() == null) ? 0 : getHerbImage5().hashCode());
        result = prime * result + ((getHerbImage6() == null) ? 0 : getHerbImage6().hashCode());
        result = prime * result + ((getHerbImage7() == null) ? 0 : getHerbImage7().hashCode());
        result = prime * result + ((getHerbVideo() == null) ? 0 : getHerbVideo().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getNationwideOrigin() == null) ? 0 : getNationwideOrigin().hashCode());
        result = prime * result + ((getYunnanOrigin() == null) ? 0 : getYunnanOrigin().hashCode());
        result = prime * result + ((getMedicinalPart() == null) ? 0 : getMedicinalPart().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getFlavor() == null) ? 0 : getFlavor().hashCode());
        result = prime * result + ((getTheory() == null) ? 0 : getTheory().hashCode());
        result = prime * result + ((getPreparation() == null) ? 0 : getPreparation().hashCode());
        result = prime * result + ((getEfficacy() == null) ? 0 : getEfficacy().hashCode());
        result = prime * result + ((getIndications() == null) ? 0 : getIndications().hashCode());
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
        sb.append(", herbId=").append(herbId);
        sb.append(", herbName=").append(herbName);
        sb.append(", standardName=").append(standardName);
        sb.append(", herbRelease=").append(herbRelease);
        sb.append(", herbDepartment=").append(herbDepartment);
        sb.append(", herbGenus=").append(herbGenus);
        sb.append(", herbImage1=").append(herbImage1);
        sb.append(", herbImage2=").append(herbImage2);
        sb.append(", herbImage3=").append(herbImage3);
        sb.append(", herbImageHead=").append(herbImageHead);
        sb.append(", herbImageArt=").append(herbImageArt);
        sb.append(", herbImagePreparation=").append(herbImagePreparation);
        sb.append(", herbImage4=").append(herbImage4);
        sb.append(", herbImage5=").append(herbImage5);
        sb.append(", herbImage6=").append(herbImage6);
        sb.append(", herbImage7=").append(herbImage7);
        sb.append(", herbVideo=").append(herbVideo);
        sb.append(", source=").append(source);
        sb.append(", nationwideOrigin=").append(nationwideOrigin);
        sb.append(", yunnanOrigin=").append(yunnanOrigin);
        sb.append(", medicinalPart=").append(medicinalPart);
        sb.append(", description=").append(description);
        sb.append(", flavor=").append(flavor);
        sb.append(", theory=").append(theory);
        sb.append(", preparation=").append(preparation);
        sb.append(", efficacy=").append(efficacy);
        sb.append(", indications=").append(indications);
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