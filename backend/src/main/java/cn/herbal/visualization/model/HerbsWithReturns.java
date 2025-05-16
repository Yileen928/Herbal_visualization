package cn.herbal.visualization.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HerbsWithReturns {
    private THerbsSong herbs;
    private List<TReturnSong> returns;
    
    // 用于存储GROUP_CONCAT结果的临时变量
    private String tempGenusIds;
    private String tempGenusNames;
    
    // 获取returns时，如果为null但临时变量有值，则处理它们
    public List<TReturnSong> getReturns() {
        if (returns == null && tempGenusIds != null && tempGenusNames != null) {
            processTemporaryFields();
        }
        return returns;
    }
    
    // 设置tempGenusIds时处理
    public void setTempGenusIds(String tempGenusIds) {
        this.tempGenusIds = tempGenusIds;
        processTemporaryFields();
    }
    
    // 设置tempGenusNames时处理
    public void setTempGenusNames(String tempGenusNames) {
        this.tempGenusNames = tempGenusNames;
        processTemporaryFields();
    }
    
    // 处理临时字段
    private void processTemporaryFields() {
        if (tempGenusIds != null && tempGenusNames != null) {
            String[] ids = tempGenusIds.split(",");
            String[] names = tempGenusNames.split(",");
            
            if (returns == null) {
                returns = new ArrayList<>();
            } else {
                returns.clear();
            }
            
            for (int i = 0; i < Math.min(ids.length, names.length); i++) {
                TReturnSong returnSong = new TReturnSong();
                try {
                    returnSong.setGenusId(Long.parseLong(ids[i].trim()));
                    returnSong.setGenusName(names[i].trim());
                    returns.add(returnSong);
                } catch (NumberFormatException e) {
                    // 忽略无效的ID
                }
            }
        }
    }
}
