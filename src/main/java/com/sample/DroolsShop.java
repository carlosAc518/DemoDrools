package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import com.sample.model.Customer;
import com.sample.model.Product;
import com.sample.model.Purchase;

public class DroolsShop {
	
    public static final void main(String[] args) {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        System.out.println(kc.verify().getMessages().toString());
        execute( kc );
    }
    
    public static void execute( KieContainer kc ) {
    	
        KieSession ksession = kc.newKieSession("ksession-rules");
        Customer carlos = new Customer( "Carlos",
                                      0 );
        ksession.insert( carlos );
        Product shoes = new Product( "Zapatos",
                                     60 );
        ksession.insert( shoes );
        Product hat = new Product( "Sombrero",
                                   60 );
        ksession.insert( hat );
        ksession.insert( new Purchase( carlos,
                                       shoes ) );
        FactHandle hatPurchaseHandle = ksession.insert( new Purchase( carlos,
                                                                      hat ) );
        ksession.fireAllRules();
        ksession.delete( hatPurchaseHandle );
        System.out.println( "Cliente Carlos ha devuelto el sombrero" );
        ksession.fireAllRules();
    }
    
}