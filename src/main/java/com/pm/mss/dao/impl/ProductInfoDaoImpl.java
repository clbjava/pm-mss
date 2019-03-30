package com.pm.mss.dao.impl;

import com.pm.mss.comm.dto.ProductInfo;
import com.pm.mss.dao.ProductInfoDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository("com.pm.mss.dao.ProductInfoDao")
public class ProductInfoDaoImpl extends AbstractDao implements ProductInfoDao {

    @Override
    public int deleteByPrimaryKey(Integer id) {
        try (SqlSession session = getSqlSession()) {
            ProductInfoDao mapper = session.getMapper(ProductInfoDao.class);
            return mapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public int insert(ProductInfo record) {
        try (SqlSession session = getSqlSession()) {
            ProductInfoDao mapper = session.getMapper(ProductInfoDao.class);
            return mapper.insert(record);
        }
    }

    @Override
    public int insertSelective(ProductInfo record) {
        try (SqlSession session = getSqlSession()) {
            ProductInfoDao mapper = session.getMapper(ProductInfoDao.class);
            return mapper.insertSelective(record);
        }
    }

    @Override
    public ProductInfo selectByPrimaryKey(Integer id) {
        try (SqlSession session = getSqlSession()) {
            ProductInfoDao mapper = session.getMapper(ProductInfoDao.class);
            return mapper.selectByPrimaryKey(id);
        }
    }

    @Override
    public int updateByPrimaryKeySelective(ProductInfo record) {
        try (SqlSession session = getSqlSession()) {
            ProductInfoDao mapper = session.getMapper(ProductInfoDao.class);
            return mapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public int updateByPrimaryKey(ProductInfo record) {
        try (SqlSession session = getSqlSession()) {
            ProductInfoDao mapper = session.getMapper(ProductInfoDao.class);
            return mapper.updateByPrimaryKey(record);
        }
    }
}
