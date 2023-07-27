package com.matao.arm.arm_of_master_sstp.DB;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.matao.arm.arm_of_master_sstp.models.Hardware;
import com.matao.arm.arm_of_master_sstp.models.HardwareHistory;
import com.matao.arm.arm_of_master_sstp.models.LPS;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HardwareHistoryDAO extends BaseDaoImpl<HardwareHistory, Integer> {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    HardwareHistoryDAO(ConnectionSource connectionSource,
                       Class<HardwareHistory> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<HardwareHistory> getAllHistory() {
        try {
            return this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public HardwareHistory getItem(Integer id) {
        try {
            return this.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public List<HardwareHistory> getHistory(Date start, Date end, Integer lpsId) {
        PreparedQuery<HardwareHistory> query;
        try {
            QueryBuilder<Hardware, Integer> queryHardware = HelperFactory.getHelper().getHardwareDao().queryBuilder();
            queryHardware.where().eq(Hardware.LPS_ID, lpsId);

            query = queryBuilder()
                    .join(queryHardware).orderBy(HardwareHistory.PLAN_DATE,true)
                    .where()
                    .between(HardwareHistory.PLAN_DATE, dateFormat.format(start), dateFormat.format(end))
                    .prepare();
            return query(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }
}