package negatron;

import negatron.adaptor.NegatronAdaptor;

public class Negatron {

    private static final String IMPL_CLASS_KEY = "NEGATRON_IMPL_CLASS";
    private static final String DEFAULT_IMPL_CLASS = "negatron.adaptor.impl.IRCNegatron";

    public static void main(String[] args) throws Exception {

        // Get the FQDN of the impl class from envvars.
        String implClass = System.getenv().getOrDefault(IMPL_CLASS_KEY, DEFAULT_IMPL_CLASS);

        // Create an instance using newInstance() and pass into the brain.
        NegatronAdaptor adaptor = (NegatronAdaptor) Class.forName(implClass).newInstance();

        // Call init() on it.
        adaptor.init();

        // Loop and sleep.
        while (true) {
            Thread.sleep(60000);
        }
    }
}
