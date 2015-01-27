package sample.http;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

	private ServiceTracker httpServiceTracker;
	
	public void start(BundleContext context) throws Exception {
		System.out.println("<<<<<<<<<<<<<<<<<  START  >>>>>>>>>>>>>>>>>>>>>");
		httpServiceTracker = new HttpServiceTracker(context);
		Thread.sleep(2000);
		httpServiceTracker.open();
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("<<<<<<<<<<<<<<<<<  STOP >>>>>>>>>>>>>>>>>>>>>");
		httpServiceTracker.close();
		httpServiceTracker = null;
	}

	private class HttpServiceTracker extends ServiceTracker {

		public HttpServiceTracker(BundleContext context) {
			super(context, HttpService.class.getName(), null);
		}

		public Object addingService(ServiceReference reference) {
			HttpService httpService = (HttpService) context.getService(reference);
			try {			
				Thread.sleep(2000);
				System.out.println("<<<<<<<<<<<<<<<<<  addingService >>>>>>>>>>>>>>>>>>>>>");
				httpService.registerResources("/helloworld.html", "/helloworld.html", null); //$NON-NLS-1$ //$NON-NLS-2$
				httpService.registerServlet("/helloworld", new HelloWorldServlet(), null, null); //$NON-NLS-1$
			} catch (Exception e) {
				e.printStackTrace();
			}
			return httpService;
		}		
		
		public void removedService(ServiceReference reference, Object service) {
			System.out.println("<<<<<<<<<<<<<<<<<  removedService  >>>>>>>>>>>>>>>>>>>>>");
			HttpService httpService = (HttpService) service;
			httpService.unregister("/helloworld.html"); //$NON-NLS-1$
			httpService.unregister("/helloworld"); //$NON-NLS-1$
			super.removedService(reference, service);
		}
	}
}
