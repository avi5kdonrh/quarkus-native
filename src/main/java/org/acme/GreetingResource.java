package org.acme;


import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cluster.CamelClusterService;
import org.apache.camel.component.kubernetes.cluster.KubernetesClusterService;

import javax.enterprise.inject.Produces;

public class GreetingResource extends RouteBuilder {
    @Produces
    public CamelClusterService kubernetesClusterService(CamelContext camelContext) {
        KubernetesClusterService service = new KubernetesClusterService();
        service.setLeaseDurationMillis(10000L);
        service.setRenewDeadlineMillis(5000L);
        service.setRetryPeriodMillis(2000L);
       // service.setMasterUrl("http://127.0.0.1:8001");
        service.setKubernetesNamespace("default");
       // service.setPodName("quarkus-master-git-58dd4cf8cc-slljp");
        return service;
    }

    @Override
    public void configure() throws Exception {
        from("master:lock:timer://test")
                .log(">> Timer is running ");
    }
}