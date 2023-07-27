package com.matao.arm.arm_of_master_sstp.DB;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.matao.arm.arm_of_master_sstp.models.Staff;

import java.sql.SQLException;
import java.util.List;

public class StaffDAO extends BaseDaoImpl<Staff, Integer> {

    StaffDAO(ConnectionSource connectionSource,
             Class<Staff> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public Staff getItem(Integer id) {
        try {
            return this.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public List<Staff> getAll() {
        try {
            return this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public List<Staff> getDinamicFiltered(String filter) {
        PreparedQuery<Staff> query = null;
        try {
            if (filter.isEmpty()) {
                query = queryBuilder().orderBy(Staff.SURNAME, true)
                        .prepare();
                return query(query);
            } else {
                query = queryBuilder().where()
                        .like(Staff.NAME, "%" + filter + "%")
                        .or().like(Staff.SURNAME, "%" + filter + "%")
                        .or().like(Staff.BIRTHDAY, "%" + filter + "%")
                        .or().like(Staff.HOME_PHONE, "%" + filter + "%")
                        .or().like(Staff.MOBILE_PHONE1, "%" + filter + "%")
                        .or().like(Staff.MOBILE_PHONE2, "%" + filter + "%")
                        .or().like(Staff.PATRONOMIC, "%" + filter + "%")
                        .or().like(Staff.TIMECARD, "%" + filter + "%")
                        .prepare();
            }
            return query(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }
}