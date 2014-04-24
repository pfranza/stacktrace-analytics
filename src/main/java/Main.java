import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.jndi.InitialContextFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

public class Main {
	public static void main(String[] args) throws Exception {
		
		System.setProperty("java.naming.factory.initial", InitialContextFactory.class.getName());
		System.setProperty("java.naming.factory.url.pkgs", "org.eclipse.jetty.jndi");

	    final int port = Integer.parseInt(System.getProperty("port", "8080"));
        final String home = System.getProperty("home","");

        Server server = new Server(port);
        ProtectionDomain domain = Main.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        
        //Enable parsing of jndi-related parts of web.xml and jetty-env.xml
        org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
 
               
        XmlConfiguration config = new XmlConfiguration(new FileInputStream(System.getProperty("datasource")));
        WebAppContext webapp = (WebAppContext) config.configure();

        webapp.setContextPath("/");
        if (home.length() != 0) {
                webapp.setTempDirectory(new File(home));
        }
  
        webapp.setWar(location.toExternalForm());
        server.setHandler(webapp);
        
        server.start();
        server.join();
	}
	
}