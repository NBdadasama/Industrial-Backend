package org.example.entity.obj.father;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;




@Data
public class Node {

    public static enum nodeCategory {
        DEVICE_CATEGORY("0"),//设备

        FAULT_A_CATEGORY("1"),

        FAULT_B_CATEGORY("2"),

        FAULT_C_CATEGORY("3"),

        MAINTENANCE_CATEGORY("4"),
        SOP_CATEGORY("5");//维护
        private String message;
        public String getMessage() {
            return message;
        }
        nodeCategory(String message) {
            this.message = message;
        }
    }
}
