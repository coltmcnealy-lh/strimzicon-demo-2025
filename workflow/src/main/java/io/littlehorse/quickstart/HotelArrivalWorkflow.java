package io.littlehorse.quickstart;

import io.littlehorse.sdk.wfsdk.WfRunVariable;
import io.littlehorse.sdk.wfsdk.Workflow;
import io.littlehorse.sdk.wfsdk.WorkflowThread;

public class HotelArrivalWorkflow {
    final String WORKFLOW_NAME = "customer-arrival";

    /*
     * This method defines the logic of our workflow
     */
    public void quickstartWf(WorkflowThread wf) {
        WfRunVariable customer = wf.declareStr("customer-id").searchable().required();
        WfRunVariable isPlatinum = wf.declareBool("is-platinum").searchable();

        // Fetch customer profile and determine if platinum
        isPlatinum.assign(wf.execute("fetch-customer", customer));

        wf.doIf(isPlatinum.isEqualTo(true), handler -> {
            handler.execute("send-special-welcome", customer);
        });

        wf.execute("process-arrival", customer);
    }

    /*
     * This method returns a LittleHorse `Workflow` wrapper object that can be
     * used to register the WfSpec to the LH Server.
     */
    public Workflow getWorkflow() {
        return Workflow.newWorkflow(WORKFLOW_NAME, this::quickstartWf);
    }
}
