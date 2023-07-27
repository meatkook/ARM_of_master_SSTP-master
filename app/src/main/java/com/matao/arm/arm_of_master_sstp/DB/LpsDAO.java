package com.matao.arm.arm_of_master_sstp.DB;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.matao.arm.arm_of_master_sstp.models.Hardware;
import com.matao.arm.arm_of_master_sstp.models.LPS;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class LpsDAO extends BaseDaoImpl<LPS, Integer> {

   LpsDAO(ConnectionSource connectionSource,
          Class<LPS> dataClass) throws SQLException {
       super(connectionSource, dataClass);
   }

   public List<LPS> getAllLps() {
       try {
           return this.queryForAll();
       } catch (SQLException e) {
           e.printStackTrace();
           throw new Error(e);
       }
   }

    public LPS getItem(Integer id) {
        try {
            return this.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }
}