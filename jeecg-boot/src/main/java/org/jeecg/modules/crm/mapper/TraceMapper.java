package org.jeecg.modules.crm.mapper;

import java.util.List;
import org.jeecg.modules.crm.entity.Trace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 跟进信息
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
public interface TraceMapper extends BaseMapper<Trace> {

	public boolean deleteByMainId(String mainId);
    
	public List<Trace> selectByMainId(String mainId);
}
