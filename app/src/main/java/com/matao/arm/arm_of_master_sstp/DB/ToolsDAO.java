package com.matao.arm.arm_of_master_sstp.DB;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.matao.arm.arm_of_master_sstp.models.LPS;
import com.matao.arm.arm_of_master_sstp.models.Tool;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ToolsDAO extends BaseDaoImpl<Tool, Integer> {

   ToolsDAO(ConnectionSource connectionSource,
            Class<Tool> dataClass) throws SQLException {
       super(connectionSource, dataClass);
   }

   public List<Tool> getAll() {
       PreparedQuery<Tool> query = null;
       try {
           query = queryBuilder().orderBy(Tool.LPS_ID, true)
                   .prepare();
           return query(query);
       } catch (SQLException e) {
           e.printStackTrace();
           throw new Error(e);
       }
   }

    public Tool getItem(Integer id) {
        try {

            return this.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public List<Tool> getFiltered(Date start, Date end, Integer lpsId) {
        PreparedQuery<Tool> query = null;
        try {
            query = queryBuilder().orderBy(Tool.PLAN_DATE, true).where()
                    .isNotNull(Tool.PLAN_DATE)
                    .and().between(Tool.PLAN_DATE, HelperFactory.dateFormat.format(start), HelperFactory.dateFormat.format(end))
                    .and().eq(Tool.LPS_ID, lpsId)
                    .prepare();
            return query(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public List<Tool> getDinamicFiltered(String filter){
        PreparedQuery<Tool> query = null;
        try {
            if(filter.isEmpty()){
                query = queryBuilder().orderBy(Tool.LPS_ID, true)
                        .prepare();
                return query(query);
            }else {
                QueryBuilder<LPS, Integer> queryLps = HelperFactory.getHelper().getLpsDao().queryBuilder();
                queryLps.where().like(LPS.NAME, "%" + filter + "%");

                query = queryBuilder().joinOr(queryLps).where()
                        .like(Tool.TOOL_NAME, "%" + filter+"%")
                        .or().like("stock_number", "%" + filter + "%")
                        .or().like("next_verification", "%" + filter + "%")
                        .prepare();
            }
            return query(query);
        } catch (SQLException e){
            e.printStackTrace();
            throw new Error(e);
        }
    }

}