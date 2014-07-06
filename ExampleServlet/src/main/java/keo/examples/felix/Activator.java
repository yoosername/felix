package keo.examples.felix;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.apache.felix.http.api.ExtHttpService;

public final class Activator
    implements BundleActivator
{
    @SuppressWarnings("rawtypes")
	private ServiceTracker tracker;
    private TestServlet servlet1 = new TestServlet("servlet1");
    private TestServlet servlet2 = new TestServlet("servlet2");
    private TestFilter filter1 = new TestFilter("filter1");
    private TestFilter filter2 = new TestFilter("filter2");

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void start(BundleContext context)
        throws Exception
    {
        this.tracker = new ServiceTracker(context, ExtHttpService.class.getName(), null)
        {
            @Override
            public Object addingService(ServiceReference ref)
            {
                Object service =  super.addingService(ref);
                serviceAdded((ExtHttpService)service);
                return service;
            }

            @Override
            public void removedService(ServiceReference ref, Object service)
            {
                serviceRemoved((ExtHttpService)service);
                super.removedService(ref, service);
            }
        };

        this.tracker.open();
    }

    public void stop(BundleContext context)
        throws Exception
    {
        this.tracker.close();
    }

    private void serviceAdded(ExtHttpService service)
    {
        try {
            service.registerServlet("/", this.servlet1, null, null);
            service.registerServlet("/other", this.servlet2, null, null);
            service.registerFilter(this.filter1, ".*", null, 0, null);
            service.registerFilter(this.filter2, "/other/.*", null, 100, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void serviceRemoved(ExtHttpService service)
    {
        service.unregisterServlet(this.servlet1);
        service.unregisterServlet(this.servlet2);
        service.unregisterFilter(this.filter1);
        service.unregisterFilter(this.filter2);
    }
}