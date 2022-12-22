package agents.ahelpers;

import jade.core.AID;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ContractsHelper {

    private static Map<Integer, Double> shoesFabricContracts = new TreeMap<>();
    private static Map<Integer, Double> residentalContracts = new TreeMap<>();
    private static Map<Integer, Double> transportContracts = new TreeMap<>();
    private static Map<String, AID> topic = new HashMap<>();

    private static Map<String, List<AID>> agreededProducers = new HashMap<>();

    private static Map<String, String> namesOfTheBestProducers = new HashMap<>();
    private static Map<String, String> distributerNames = new HashMap<>();
    private static Map<Integer, Double> powerOfTPP = new TreeMap<>();
    private static Map<Integer, Double> powerOfWPP = new TreeMap<>();
    private static Map<Integer, Double> powerOfSPP = new TreeMap<>();

    public synchronized static Map<Integer, Double> getShoesFabricContracts() {
        return shoesFabricContracts;
    }

    public synchronized static void setShoesFabricContracts(Map<Integer, Double> shoesFabricContracts) {
        ContractsHelper.shoesFabricContracts = shoesFabricContracts;
    }

    public synchronized static Map<Integer, Double> getResidentalContracts() {
        return residentalContracts;
    }

    public synchronized static void setResidentalContracts(Map<Integer, Double> residentalContracts) {
        ContractsHelper.residentalContracts = residentalContracts;
    }

    public synchronized static Map<Integer, Double> getTransportContracts() {
        return transportContracts;
    }

    public synchronized static void setTransportContracts(Map<Integer, Double> transportContracts) {
        ContractsHelper.transportContracts = transportContracts;
    }

    public synchronized static Map<String, List<AID>> getAgreededProducers() {
        return agreededProducers;
    }

    public synchronized static void setAgreededProducers(Map<String, List<AID>> agreededProducers) {
        ContractsHelper.agreededProducers = agreededProducers;
    }

    public synchronized static Map<String, String> getNamesOfTheBestProducers() {
        return namesOfTheBestProducers;
    }

    public synchronized static void setNamesOfTheBestProducers(Map<String, String> namesOfTheBestProducers) {
        ContractsHelper.namesOfTheBestProducers = namesOfTheBestProducers;
    }

    public synchronized static Map<String, String> getDistributerNames() {
        return distributerNames;
    }

    public synchronized static void setDistributerNames(Map<String, String> distributerNames) {
        ContractsHelper.distributerNames = distributerNames;
    }

    public synchronized static Map<Integer, Double> getPowerOfTPP() {
        return powerOfTPP;
    }

    public synchronized static void setPowerOfTPP(Map<Integer, Double> powerOfTPP) {
        ContractsHelper.powerOfTPP = powerOfTPP;
    }

    public synchronized static Map<Integer, Double> getPowerOfWPP() {
        return powerOfWPP;
    }

    public synchronized static void setPowerOfWPP(Map<Integer, Double> powerOfWPP) {
        ContractsHelper.powerOfWPP = powerOfWPP;
    }

    public synchronized static Map<Integer, Double> getPowerOfSPP() {
        return powerOfSPP;
    }

    public synchronized static void setPowerOfSPP(Map<Integer, Double> powerOfSPP) {
        ContractsHelper.powerOfSPP = powerOfSPP;
    }

    public synchronized static Map<String, AID> getTopic() {
        return topic;
    }

    public synchronized static void setTopic(Map<String, AID> topic) {
        ContractsHelper.topic = topic;
    }

    public static void putTopic(String key, AID value) {
        topic.put(key, value);
    }
    public static AID topicGet(String key){
        return topic.get(key);
    }

    public synchronized static Double powerOfTPPGet(int key){
        return powerOfTPP.get(key);

    }public synchronized static Double powerOfWPPGet(int key){
        return powerOfTPP.get(key);

    }public synchronized static Double powerOfSPPGet(int key){
        return powerOfTPP.get(key);
    }

    public synchronized static Double powerOfTPPPut(int key, double value){
        return powerOfTPP.put(key, value);

    }public synchronized static Double powerOfWPPPut(int key, double value){
        return powerOfTPP.put(key, value);

    }public synchronized static Double powerOfSPPPut(int key, double value){
        return powerOfTPP.put(key, value);
    }
}
