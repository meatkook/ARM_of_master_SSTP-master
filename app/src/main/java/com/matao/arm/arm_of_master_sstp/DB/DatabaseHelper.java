package com.matao.arm.arm_of_master_sstp.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.matao.arm.arm_of_master_sstp.models.Extinguisher;
import com.matao.arm.arm_of_master_sstp.models.Hardware;
import com.matao.arm.arm_of_master_sstp.models.HardwareHistory;
import com.matao.arm.arm_of_master_sstp.models.LPS;
import com.matao.arm.arm_of_master_sstp.models.Staff;
import com.matao.arm.arm_of_master_sstp.models.Tool;
import com.matao.arm.arm_of_master_sstp.models.Transformer;
import com.matao.arm.arm_of_master_sstp.models.TransformerTypes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    //имя файла базы данных который будет храниться в /data/data/APPNAME/DATABASE_NAME.db
    private static final String DATABASE_NAME = "STPSS.db";
    private static final String DATABASE_SQL = "STPSS.db.sql";

    //с каждым увеличением версии, при нахождении в устройстве БД с предыдущей версией будет выполнен метод onUpgrade();
    private static final int DATABASE_VERSION = 1;

    private Context mContext;

    //ссылки на DAO соответсвующие сущностям, хранимым в БД
    private HardwareDAO hardwareDao = null;
    private LpsDAO lpsDao = null;
    private StaffDAO staffDao = null;
    private HardwareHistoryDAO historyDAO = null;
    private ExtinguisherDAO extinguisherDAO = null;
    private ToolsDAO toolsDAO = null;
    private TransformerDAO transformerDAO = null;
    private TransformerTypeDAO transformerTypeDAO = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    //Выполняется, когда файл с БД не найден на устройстве
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            InputStreamReader reader = new InputStreamReader(mContext.getAssets().open(DATABASE_SQL));
            StringWriter writer = new StringWriter();
            while (reader.ready()) {
                writer.write(reader.read());
            }
            String[] queries = writer.toString().split(";\n");
            for (String query : queries) {
                if (!query.isEmpty()) {
                    db.execSQL(query);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Выполняется, когда БД имеет версию отличную от текущей
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer) {
    }

    //синглтон для Hardware
    public HardwareDAO getHardwareDao() {
        if (hardwareDao == null) {
            try {
                hardwareDao = new HardwareDAO(getConnectionSource(), Hardware.class);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Error(e);
            }
        }
        return hardwareDao;
    }

    public LpsDAO getLpsDao() {
        if (lpsDao == null) {
            try {
                lpsDao = new LpsDAO(getConnectionSource(), LPS.class);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Error(e);
            }
        }
        return lpsDao;
    }

    public StaffDAO getStaffDao() {
        if (staffDao == null) {
            try {
                staffDao = new StaffDAO(getConnectionSource(), Staff.class);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Error(e);
            }
        }
        return staffDao;
    }

    public HardwareHistoryDAO getHistoryDao() {
        if (historyDAO == null) {
            try {
                historyDAO = new HardwareHistoryDAO(getConnectionSource(), HardwareHistory.class);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Error(e);
            }
        }
        return historyDAO;
    }

    public ExtinguisherDAO getExtinguisherDao() {
        if (extinguisherDAO == null) {
            try {
                extinguisherDAO = new ExtinguisherDAO(getConnectionSource(), Extinguisher.class);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Error(e);
            }
        }
        return extinguisherDAO;
    }

    public ToolsDAO getToolsDao() {
        if (toolsDAO == null) {
            try {
                toolsDAO = new ToolsDAO(getConnectionSource(), Tool.class);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Error(e);
            }
        }
        return toolsDAO;
    }

    public TransformerDAO getTransformerDao() {
        if (transformerDAO == null) {
            try {
                transformerDAO = new TransformerDAO(getConnectionSource(), Transformer.class);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Error(e);
            }
        }
        return transformerDAO;
    }

    public TransformerTypeDAO getTransformerTypeDao() {
        if (transformerTypeDAO == null) {
            try {
                transformerTypeDAO = new TransformerTypeDAO(getConnectionSource(), TransformerTypes.class);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Error(e);
            }
        }
        return transformerTypeDAO;
    }

    //выполняется при закрытии приложения
    @Override
    public void close() {
        super.close();
        hardwareDao = null;
        transformerDAO = null;
        toolsDAO = null;
        extinguisherDAO = null;
        lpsDao = null;

    }
}