package io.littlehorse.quickstart;

import java.util.Map;
import io.littlehorse.sdk.common.config.LHConfig;
import io.littlehorse.sdk.worker.LHTaskWorker;

public class Main {

    static KnowYourCustomerTasks tasks = new KnowYourCustomerTasks();

    public static void main(String[] args) {
        LHConfig config = new LHConfig(Map.of(LHConfig.API_HOST_KEY, "lh.strimzicon.demo"));

        LHTaskWorker fetchCustomerWorker = new LHTaskWorker(tasks, "fetch-customer", config);
        LHTaskWorker welcomeWorker = new LHTaskWorker(tasks, "send-special-welcome", config);
        LHTaskWorker processArrivalWorker = new LHTaskWorker(tasks, "process-arrival", config);

        fetchCustomerWorker.registerTaskDef();
        welcomeWorker.registerTaskDef();
        processArrivalWorker.registerTaskDef();

        HotelArrivalWorkflow quickstart = new HotelArrivalWorkflow();
        quickstart.getWorkflow().registerWfSpec(config.getBlockingStub());

        // Close the worker upon shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(fetchCustomerWorker::close));
        Runtime.getRuntime().addShutdownHook(new Thread(welcomeWorker::close));
        Runtime.getRuntime().addShutdownHook(new Thread(processArrivalWorker::close));

        System.out.println("Starting task workers!");
        fetchCustomerWorker.start();
        welcomeWorker.start();
        processArrivalWorker.start();
    }
}
