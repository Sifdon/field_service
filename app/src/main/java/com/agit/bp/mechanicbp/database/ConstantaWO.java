package com.agit.bp.mechanicbp.database;

import com.agit.bp.mechanicbp.models.ModelStatusWO;

/**
 * Created by NiyatiR on 8/8/2016.
 */

public class ConstantaWO {
    public static final boolean IS_DEBUG = true;

    public static final ModelStatusWO[] CONSTANT_STATUS = {
            /*0*/new ModelStatusWO(), // diisi 0 agar sequnce bisa tetap berurutan sesuai index mulai 1
            //int status_sequence, int status_id, String status_name, String status_buttonname, String status_path

            /*1*/new ModelStatusWO(1,   5049, "Uploaded Work Order", "", ""),
            /*2*/new ModelStatusWO(2,   5070, "Assigned New Order", "", ""),
            /*3*/new ModelStatusWO(3,   5050, "Accepted Work Order", "Accept", "acceptWorkOrder"),
            /*4*/new ModelStatusWO(4,   5099, "Rejected Work Order", "Reject", "rejectWorkOrder"),
            /*5*/new ModelStatusWO(5,   5051, "Confirm Hard Copy Work Order", "Receive Hard Copy WO", "hardCopyWorkOrder"),
            /*6*/new ModelStatusWO(6,   5052, "Ready No Parts", "No Parts Required", "readyNoParts"),
            /*7*/new ModelStatusWO(7,   5053, "Ready Completed Parts", "Complete Prepare Parts", "readyCompleteParts"),
            /*8*/new ModelStatusWO(8,   5057, "On The Way to Check", "Go To Location", "readyToGo"),
            /*9*/new ModelStatusWO(9,   5059, "Arrive to Check", "Is Same Location", "sameLocation"),
            /*10*/new ModelStatusWO(10, 5059, "Arrive to Check", "Arrived", "arriveToCheck"),
            /*11*/new ModelStatusWO(11, 5060, "Stand by Check", "Stand By", "standByCheck"),
            /*12*/new ModelStatusWO(12, 5061, "Checking", "Continue", "resumeCheck"),
            /*13*/new ModelStatusWO(13, 5061, "Checking", "Start To Check", "readyToCheck"),
            /*14*/new ModelStatusWO(14, 5062, "Hold to Check", "Hold", "holdToCheck"),
            /*15*/new ModelStatusWO(15, 5061, "Checking", "Continue", "resumeCheck"),
            /*16*/new ModelStatusWO(16, 5054, "Waiting Approval", "Checking Done", "checkingDone"),
            /*17*/new ModelStatusWO(17, 5055, "Ready Repairing", "", ""),
            /*18*/new ModelStatusWO(18, 5071, "On The Way to Repair", "Go To Location", "readyToGoRepairing"),
            /*19*/new ModelStatusWO(19, 5072, "Arrive to Repair", "Is Same Location", "sameLocationToRepair"),
            /*20*/new ModelStatusWO(20, 5072, "Arrive to Repair", "Arrived", "arriveToRepair"),
            /*21*/new ModelStatusWO(21, 5058, "Stand By Repair", "Stand By", "standByRepair"),
            /*22*/new ModelStatusWO(22, 5064, "Repairing", "Continue", "resumeRepair"),
            /*23*/new ModelStatusWO(23, 5064, "Repairing", "Start To Repair", "readyToRepair"),
            /*24*/new ModelStatusWO(24, 5065, "Hold to Repair", "Hold", "holdToRepair"),
            /*25*/new ModelStatusWO(25, 5064, "Repairing", "Continue", "resumeRepair"),
            /*26*/new ModelStatusWO(26, 7004, "Complete Task List", "Completed","completeTaskList"),
            /*27*/new ModelStatusWO(27, 5067, "Complete Order", "Repair Done", "completeOrder"),
            /*28*/new ModelStatusWO(28, 5056, "Repairing Done", "Submit", "repairingDone"),
            /*29*/new ModelStatusWO(29, 5068, "Leave Location", "Leave Location", "leaveLocation"),
            /*30*/new ModelStatusWO(30, 5069, "Arrive at Office", "Arrive at BP Office", "arriveAtOffice"),
            /*30*/new ModelStatusWO(31, 5100, "Assigned New Order", "Assigned New Order", "arriveAtOffice")
    };
}
//cara panggil
//ConstantClass.STATUS[1].getPath()