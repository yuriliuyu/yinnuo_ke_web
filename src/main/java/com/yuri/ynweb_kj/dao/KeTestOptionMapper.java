package com.yuri.ynweb_kj.dao;

import com.yuri.ynweb_kj.pojo.KeTestOption;
import java.util.List;

public interface KeTestOptionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeTestOption record);

    KeTestOption selectByPrimaryKey(Integer id);

    List<KeTestOption> selectAll();

    int updateByPrimaryKey(KeTestOption record);

    List<KeTestOption> getOptionListByTestId(Integer testId);

    List<KeTestOption> getTestoptionByTestId(Integer testId);
}