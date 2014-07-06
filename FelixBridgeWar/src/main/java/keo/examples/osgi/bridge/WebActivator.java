package keo.examples.osgi.bridge;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public final class WebActivator implements BundleActivator
{

	public void start(BundleContext context) throws Exception
	{
		ArrayList<Bundle> installed = new ArrayList<Bundle>();
		File bundleDir = new File("c:\\felix-bundles");
		File[] bundles = bundleDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
		
		for (File file : bundles) {
			//Load the bundle. file.toURI().toURL()... can probably be done in an easier way not the  point here though.
			Bundle bundle = context.installBundle(file.toURI().toURL().toExternalForm());
			installed.add(bundle);
		}
		
		for (Bundle bundle : installed) {
			bundle.start();
		}
	}
	public void stop(BundleContext context)	throws Exception{
		//Nothing necessary
	}
}
