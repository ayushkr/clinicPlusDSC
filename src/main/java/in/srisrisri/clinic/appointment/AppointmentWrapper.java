package in.srisrisri.clinic.appointment;

import in.srisrisri.clinic.entities.AppointmentEntity;
import java.util.List;

public class AppointmentWrapper {

    List<AppointmentEntity> list ;

    public List<AppointmentEntity> getList() {
        return list;
    }

    public void setList(List<AppointmentEntity> list) {
        this.list = list;
    }
}
