package com.dudev.jdbc.starter.services;

import com.dudev.jdbc.starter.dao.ChangeTypeDao;
import com.dudev.jdbc.starter.entity.ChangeType;
import com.oracle.wls.shaded.org.apache.bcel.generic.INSTANCEOF;
import lombok.SneakyThrows;

import java.util.List;

public class ChangeTypeService {

    private final ChangeTypeDao changeTypeDao = ChangeTypeDao.getInstance();
    private static final ChangeTypeService INSTANCE = new ChangeTypeService();

    public static ChangeTypeService getInstance() {
        return INSTANCE;
    }

    //    public ChangeType getChangeType()
    public List<ChangeType> getChangeTypes() {
        return changeTypeDao.findAll();
    }

    @SneakyThrows
    public ChangeType changeType(String changeTypeDescr) {
        return changeTypeDao.findByName(changeTypeDescr).orElseThrow();
    }

    public ChangeType getChangeType(int id) {
        return changeTypeDao.findById(id).orElse(null);
    }
}
