package com.matao.arm.arm_of_master_sstp.check_plan;

import com.matao.arm.arm_of_master_sstp.models.Extinguisher;
import com.matao.arm.arm_of_master_sstp.models.Tool;
import com.matao.arm.arm_of_master_sstp.models.Transformer;

public class CheckModel {

    public enum ToolType {
        ALL("Все"), EXTINGUISHER("Огнетушитель"), TRANSFORMER("Трансформатор"), TOOL("Инструменты");

        private String title;

        ToolType(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private String name;

    private ToolType type;

    private String date;

    private String lps;

    public String getName() {
        return name;
    }

    public ToolType getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getLps() {
        return lps;
    }

    public static CheckModel from(Extinguisher obj) {
        CheckModel checkModel = new CheckModel();
        checkModel.name = obj.getName();
        checkModel.date = obj.getNextVerification();
        checkModel.lps = obj.getLps().getName();
        checkModel.type = ToolType.EXTINGUISHER;
        return checkModel;
    }

    public static CheckModel from(Transformer obj) {
        CheckModel checkModel = new CheckModel();
        checkModel.name = obj.getName();
        checkModel.date = obj.getNextVerification();
        checkModel.lps = obj.getLps().getName();
        checkModel.type = ToolType.TRANSFORMER;
        return checkModel;
    }

    public static CheckModel from(Tool obj) {
        CheckModel checkModel = new CheckModel();
        checkModel.name = obj.getName();
        checkModel.date = obj.getNextVerification();
        checkModel.lps = obj.getLps().getName();
        checkModel.type = ToolType.TOOL;
        return checkModel;
    }
}
