package com.pd.system.service.base;

import com.github.wenhao.jpa.Sorts;
import com.pd.common.utils.PageUtils;
import com.pd.common.utils.bean.BeanUtils;
import com.pd.common.utils.bean.BeanUtilsBean;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.StringConverter;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public abstract class AbsCommonService<T> implements ICommonService<T> {
    public abstract JpaRepository<T, Long> getDao();

    public abstract JpaSpecificationExecutor<T> getDao2();

    public Example<T> listExample(Map<String, Object> params, Class<T> beanClass) throws Exception {
        T obj = beanClass.newInstance();
        BeanUtilsBean.getInstance().getConvertUtils().register(new IntegerConverter(null), Integer.class);
        BeanUtilsBean.getInstance().getConvertUtils().register(new LongConverter(null), Long.class);
        BeanUtilsBean.getInstance().getConvertUtils().register(new DoubleConverter(null), Double.class);
        BeanUtilsBean.getInstance().getConvertUtils().register(new StringConverter(null), String.class);
        BeanUtils.populate(obj, params);
        Example<T> ex = Example.of(obj);
        return ex;
    }

    @Override
    public Sort listSort(Map<String, Object> params, String sortField) {
        Sort sort = null;
        Object st = params.get("sort");
        if(!ObjectUtils.isEmpty(st)) {
            String order = params.get("order").toString();
            if("desc".equals(order)) {
                sort = Sorts.builder().desc(st.toString()).build();
            } else {
                sort = Sorts.builder().asc(st.toString()).build();
            }
        } else {
            sort = Sorts.builder().desc(sortField).build();
        }
        return sort;
    }

    @Override
    public Pageable listPageable(Map<String, Object> params, Sort sort) {
        return PageRequest.of((int)params.get("offset") / (int)params.get("limit"), (int)params.get("limit"), sort);
    }

    @Override
    public Pageable listPageable(Map<String, Object> params, String sortField) {
        Sort sort = this.listSort(params, sortField);
        return this.listPageable(params, sort);
    }

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public List<T> findAllById(List<Long> ids) {
        return getDao().findAllById(ids);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return getDao2().findAll(spec);
    }

    @Override
    public List<T> findAll(Example<T> example) {
        return getDao().findAll(example);
    }

    @Override
    public List<T> findAll(Map<String, Object> params, Class<T> beanClass) throws Exception {
        Example<T> ex = this.listExample(params, beanClass);
        return this.findAll(ex);
    }

    @Override
    public List<T> findAll(Sort sort) {
        return getDao().findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getDao().findAll(pageable);
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return getDao2().findAll(spec, pageable);
    }

    @Override
    public Page<T> findAll(Example<T> example, Pageable pageable) {
        return getDao().findAll(example, pageable);
    }

    @Override
    public Page<T> findAll(Map<String, Object> params, Class<T> beanClass, String sortField) throws Exception {
        Example<T> ex = this.listExample(params, beanClass);
        Pageable pageable = this.listPageable(params, sortField);
        return this.findAll(ex, pageable);
    }

    public PageUtils findAllByPage(Map<String, Object> params, Class<T> beanClass, String sortField) throws Exception {
        Example<T> ex = this.listExample(params, beanClass);
        Pageable pageable = this.listPageable(params, sortField);
        Page<T> page = this.findAll(ex, pageable);
        PageUtils pageUtils = new PageUtils(page.getContent(), page.getTotalElements());
        return pageUtils;
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return getDao2().findAll(spec, sort);
    }

    @Override
    public List<T> findAll(Example<T> example, Sort sort) {
        return getDao().findAll(example, sort);
    }

    @Override
    public long count(Specification<T> spec) {
        return getDao2().count(spec);
    }

    @Override
    public long count(Example<T> example) {
        return getDao().count(example);
    }

    @Override
    public Page<T> find(int pageNum, int pageSize) {
        return getDao().findAll(PageRequest.of(pageNum - 1,pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public T findById(Long id) {
        return getDao().getOne(id);
    }

    @Override
    public boolean existsById(Long id) {
        return getDao().existsById(id);
    }

    @Override
    public T findOne(Specification<T> spec) {
        return getDao2().findOne(spec).get();
    }

    @Override
    public T findOne(Example<T> example) {
        return getDao().findOne(example).get();
    }

    @Transactional
    public void deleteAll(List<T> list) {
        getDao().deleteAll(list);
    }

    @Transactional
    public void deleteById(Long id) {
        getDao().deleteById(id);
    }

    @Transactional
    public void delete(T t) {
        getDao().delete(t);
    }

    @Transactional
    public T save(T t) {
        return getDao().save(t);
    }

    @Transactional
    public List<T> saveAll(List<T> list) {
        return getDao().saveAll(list);
    }
}
