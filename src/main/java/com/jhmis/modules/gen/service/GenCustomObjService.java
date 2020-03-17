package com.jhmis.modules.gen.service;

import com.jhmis.modules.gen.entity.GenCustomObj;
import com.jhmis.modules.gen.mapper.GenCustomObjMapper;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 自定义对象服务
 * @author tity
 *
 */
@Service
@Transactional(
    readOnly = true
)
public class GenCustomObjService extends CrudService<GenCustomObjMapper, GenCustomObj> {
    public GenCustomObjService() {
    }

    public GenCustomObj get(String id) {
        return (GenCustomObj)super.get(id);
    }

    public List<GenCustomObj> findList(GenCustomObj genCustomObj) {
        return super.findList(genCustomObj);
    }

    public Page<GenCustomObj> findPage(Page<GenCustomObj> page, GenCustomObj genCustomObj) {
        return super.findPage(page, genCustomObj);
    }

    @Transactional(
        readOnly = false
    )
    public void save(GenCustomObj genCustomObj) {
        super.save(genCustomObj);
    }

    @Transactional(
        readOnly = false
    )
    public void delete(GenCustomObj genCustomObj) {
        super.delete(genCustomObj);
    }
}
