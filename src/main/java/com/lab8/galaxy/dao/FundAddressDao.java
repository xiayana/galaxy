package com.lab8.galaxy.dao;

import com.lab8.galaxy.entity.FundAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
/**
 * (FundAddress)表数据库访问层
 *
 * @author xy
 * @since 2024-01-15 20:25:14
 */
@Mapper
public interface FundAddressDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    FundAddress queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param fundAddress 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<FundAddress> queryAllByLimit(FundAddress fundAddress, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param fundAddress 查询条件
     * @return 总行数
     */
    long count(FundAddress fundAddress);

    /**
     * 新增数据
     *
     * @param fundAddress 实例对象
     * @return 影响行数
     */
    int insert(FundAddress fundAddress);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<FundAddress> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<FundAddress> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<FundAddress> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<FundAddress> entities);

    /**
     * 修改数据
     *
     * @param fundAddress 实例对象
     * @return 影响行数
     */
    int update(FundAddress fundAddress);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    String selectFaddress(@Param("pid") Integer pid,@Param("used") String used);
}

