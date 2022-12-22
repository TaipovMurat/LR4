package agents.consumer;

import agents.ahelpers.ContractsHelper;
import jade.core.Agent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class ConsumerAgent extends Agent {
    private ConsumerCfg config;
    private double maxPrice;
    private ContractsHelper contracts = new ContractsHelper();
    private PowerPerHour hour = new PowerPerHour();

    @Override
    protected void setup() {
        config = getConfigByName(ConsumerCfg.class, this.getLocalName());
        ContractsHelper.getDistributerNames().put(this.getLocalName(),this.getLocalName()+"Distributor");
        for (PowerPerHour pph : config.getPowerPerHour()) {
            switch (this.getLocalName()) {
                case "ShoesFabric":
                    maxPrice = 115.0;
                    contracts.getShoesFabricContracts().put(pph.getHour()*2000, pph.getPower());
                    break;
                case "Residental":
                    maxPrice = 120.0;
                    contracts.getResidentalContracts().put(pph.getHour()*2000, pph.getPower());
                    break;
                case "Transport":
                    maxPrice = 110.0;
                    contracts.getTransportContracts().put(pph.getHour()*2000, pph.getPower());
                    break;
            }
        }
//        System.out.println(ContractsHelper.getDistributerNames().toString());
        addBehaviour(new ConsumerFSM(maxPrice, hour.getHour()));
    }

    private <T> T getConfigByName(Class<T> classType, String agentName) {
        try {
            JAXBContext context = JAXBContext.newInstance(classType);
            T cfg = (T) context.createUnmarshaller().unmarshal(new FileReader("Lab4/src/main/resources/" + agentName + ".xml"));
            return cfg;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
