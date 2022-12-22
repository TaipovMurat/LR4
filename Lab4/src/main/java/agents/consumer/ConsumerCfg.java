package agents.consumer;


import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "cfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsumerCfg {

    @XmlElementWrapper(name = "load")
    @XmlElement(name = "PowerPerHour")
    private List<PowerPerHour> powerPerHour;

    public List<PowerPerHour> getPowerPerHour() {
        return powerPerHour;
    }
}
