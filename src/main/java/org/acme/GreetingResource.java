package org.acme;


import io.fabric8.kubernetes.api.model.coordination.v1.Lease;
import io.fabric8.kubernetes.api.model.coordination.v1.LeaseSpec;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cluster.CamelClusterService;
import org.apache.camel.component.kubernetes.cluster.KubernetesClusterService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
@RegisterForReflection(targets = {
        LeaseSpec.class,
        Lease.class
})
public class GreetingResource extends RouteBuilder {
    @Produces
    public CamelClusterService kubernetesClusterService(CamelContext camelContext) {
        KubernetesClusterService service = new KubernetesClusterService();
        service.setLeaseDurationMillis(10000L);
        service.setRenewDeadlineMillis(5000L);
        service.setRetryPeriodMillis(2000L);
       // service.setMasterUrl("http://127.0.0.1:8001");
       // service.setKubernetesNamespace("default");
       // service.setPodName("quarkus-master-git-58dd4cf8cc-slljp");
        return service;
    }

    @Override
    public void configure() throws Exception {
        from("master:lock2:timer://test")
                .log(">> Timer is running ");
    }
}