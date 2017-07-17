package com.agit.bp.mechanicbp.database;

import com.agit.bp.mechanicbp.models.ModelStatusTasklist;

/**
 * Created by NiyatiR on 8/8/2016.
 */

public class ConstantaTasklist {
    public static final boolean IS_DEBUG = true;
    /*
            int status_sequence, ok
            int status_id, ok
            String status_name, ok
            String status_buttonname,
            String status_buttoncolour,
            String status_buttonclick,
            String status_path
    */

    public static final ModelStatusTasklist[] CONSTANT_STATUS = {
            /*0*/new ModelStatusTasklist(), // diisi 0 agar sequnce bisa tetap berurutan sesuai index mulai 1
            /*1*/new ModelStatusTasklist(1,   7005, "New Task List", "Finish", "#BDBDBD", false, ""),
            /*2*/new ModelStatusTasklist(2,   5064, "Repairing", "Finish", "#00796B", true, ""),
            /*3*/new ModelStatusTasklist(3,   5065, "Hold to Repair", "Hold", "#BDBDBD", false, ""),
            /*4*/new ModelStatusTasklist(4,   7004, "Complete Task List", "Done", "#fd9f01", false, "completeTaskList")
    };
}
//cara panggil
//ConstantClass.STATUS[1].getPath()