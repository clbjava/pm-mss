package com.pm.mss.server.config.tomcat;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.util.LifecycleBase;
import org.apache.catalina.webresources.AbstractResourceSet;
import org.apache.catalina.webresources.EmptyResource;
import org.apache.coyote.AbstractProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

public class TomcatServletWebServerFactory1 extends TomcatServletWebServerFactory {
    private static final Charset DEFAULT_CHARSET;
    private static final Set<Class<?>> NO_CLASSES;
   // public static final String DEFAULT_PROTOCOL = "org.apache.coyote.http11.Http11Nio2Protocol1";
    public static final String DEFAULT_PROTOCOL = "com.pm.mss.server.config.tomcat.Http11Nio2Protocol1";
    private File baseDirectory;
    private List<Valve> engineValves = new ArrayList();
    private List<Valve> contextValves = new ArrayList();
    private List<LifecycleListener> contextLifecycleListeners = new ArrayList(Collections.singleton(new AprLifecycleListener()));
    private List<TomcatContextCustomizer> tomcatContextCustomizers = new ArrayList();
    private List<TomcatConnectorCustomizer> tomcatConnectorCustomizers = new ArrayList();
    private List<Connector> additionalTomcatConnectors = new ArrayList();
    private ResourceLoader resourceLoader;
    private String protocol = "com.pm.mss.server.config.tomcat.Http11Nio2Protocol1";
    private Set<String> tldSkipPatterns;
    private Charset uriEncoding;
    private int backgroundProcessorDelay;


    public WebServer getWebServer(ServletContextInitializer... initializers) {
        Tomcat tomcat = new Tomcat();
        File baseDir = this.baseDirectory != null ? this.baseDirectory : this.createTempDir("tomcat");
        tomcat.setBaseDir(baseDir.getAbsolutePath());
        Connector connector = new Connector(this.protocol);
        connector.getProtocolHandler();
        tomcat.getService().addConnector(connector);
        this.customizeConnector(connector);
        tomcat.setConnector(connector);
        tomcat.getHost().setAutoDeploy(false);
        this.configureEngine(tomcat.getEngine());
        Iterator var5 = this.additionalTomcatConnectors.iterator();

        while(var5.hasNext()) {
            Connector additionalConnector = (Connector)var5.next();
            tomcat.getService().addConnector(additionalConnector);
        }

        this.prepareContext(tomcat.getHost(), initializers);
        return this.getTomcatWebServer(tomcat);
    }

    private void configureEngine(Engine engine) {
        engine.setBackgroundProcessorDelay(this.backgroundProcessorDelay);
        Iterator var2 = this.engineValves.iterator();

        while(var2.hasNext()) {
            Valve valve = (Valve)var2.next();
            engine.getPipeline().addValve(valve);
        }

    }



    private void addDefaultServlet(Context context) {
        Wrapper defaultServlet = context.createWrapper();
        defaultServlet.setName("default");
        defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
        defaultServlet.addInitParameter("debug", "0");
        defaultServlet.addInitParameter("listings", "false");
        defaultServlet.setLoadOnStartup(1);
        defaultServlet.setOverridable(true);
        context.addChild(defaultServlet);
        context.addServletMappingDecoded("/", "default");
    }

    private void addJspServlet(Context context) {
        Wrapper jspServlet = context.createWrapper();
        jspServlet.setName("jsp");
        jspServlet.setServletClass(this.getJsp().getClassName());
        jspServlet.addInitParameter("fork", "false");
        this.getJsp().getInitParameters().forEach(jspServlet::addInitParameter);
        jspServlet.setLoadOnStartup(3);
        context.addChild(jspServlet);
        context.addServletMappingDecoded("*.jsp", "jsp");
        context.addServletMappingDecoded("*.jspx", "jsp");
    }



    private void customizeProtocol(AbstractProtocol<?> protocol) {
        if (this.getAddress() != null) {
            protocol.setAddress(this.getAddress());
        }

    }


    private void configurePersistSession(Manager manager) {
        Assert.state(manager instanceof StandardManager, () -> {
            return "Unable to persist HTTP session state using manager type " + manager.getClass().getName();
        });
        File dir = this.getValidSessionStoreDir();
        File file = new File(dir, "SESSIONS.ser");
        ((StandardManager)manager).setPathname(file.getAbsolutePath());
    }

    private long getSessionTimeoutInMinutes() {
        Duration sessionTimeout = this.getSession().getTimeout();
        return this.isZeroOrLess(sessionTimeout) ? 0L : Math.max(sessionTimeout.toMinutes(), 1L);
    }

    private boolean isZeroOrLess(Duration sessionTimeout) {
        return sessionTimeout == null || sessionTimeout.isNegative() || sessionTimeout.isZero();
    }

    protected void postProcessContext(Context context) {
    }

    protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
        return new TomcatWebServer(tomcat, this.getPort() >= 0);
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setBaseDirectory(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public Set<String> getTldSkipPatterns() {
        return this.tldSkipPatterns;
    }

    public void setTldSkipPatterns(Collection<String> patterns) {
        Assert.notNull(patterns, "Patterns must not be null");
        this.tldSkipPatterns = new LinkedHashSet(patterns);
    }

    public void addTldSkipPatterns(String... patterns) {
        Assert.notNull(patterns, "Patterns must not be null");
        this.tldSkipPatterns.addAll(Arrays.asList(patterns));
    }

    public void setProtocol(String protocol) {
        Assert.hasLength(protocol, "Protocol must not be empty");
        this.protocol = protocol;
    }

    public void setEngineValves(Collection<? extends Valve> engineValves) {
        Assert.notNull(engineValves, "Valves must not be null");
        this.engineValves = new ArrayList(engineValves);
    }

    public Collection<Valve> getEngineValves() {
        return this.engineValves;
    }

    public void addEngineValves(Valve... engineValves) {
        Assert.notNull(engineValves, "Valves must not be null");
        this.engineValves.addAll(Arrays.asList(engineValves));
    }

    public void setContextValves(Collection<? extends Valve> contextValves) {
        Assert.notNull(contextValves, "Valves must not be null");
        this.contextValves = new ArrayList(contextValves);
    }

    public Collection<Valve> getContextValves() {
        return this.contextValves;
    }

    public void addContextValves(Valve... contextValves) {
        Assert.notNull(contextValves, "Valves must not be null");
        this.contextValves.addAll(Arrays.asList(contextValves));
    }

    public void setContextLifecycleListeners(Collection<? extends LifecycleListener> contextLifecycleListeners) {
        Assert.notNull(contextLifecycleListeners, "ContextLifecycleListeners must not be null");
        this.contextLifecycleListeners = new ArrayList(contextLifecycleListeners);
    }

    public Collection<LifecycleListener> getContextLifecycleListeners() {
        return this.contextLifecycleListeners;
    }

    public void addContextLifecycleListeners(LifecycleListener... contextLifecycleListeners) {
        Assert.notNull(contextLifecycleListeners, "ContextLifecycleListeners must not be null");
        this.contextLifecycleListeners.addAll(Arrays.asList(contextLifecycleListeners));
    }

    public void setTomcatContextCustomizers(Collection<? extends TomcatContextCustomizer> tomcatContextCustomizers) {
        Assert.notNull(tomcatContextCustomizers, "TomcatContextCustomizers must not be null");
        this.tomcatContextCustomizers = new ArrayList(tomcatContextCustomizers);
    }

    public Collection<TomcatContextCustomizer> getTomcatContextCustomizers() {
        return this.tomcatContextCustomizers;
    }

    public void addContextCustomizers(TomcatContextCustomizer... tomcatContextCustomizers) {
        Assert.notNull(tomcatContextCustomizers, "TomcatContextCustomizers must not be null");
        this.tomcatContextCustomizers.addAll(Arrays.asList(tomcatContextCustomizers));
    }

    public void setTomcatConnectorCustomizers(Collection<? extends TomcatConnectorCustomizer> tomcatConnectorCustomizers) {
        Assert.notNull(tomcatConnectorCustomizers, "TomcatConnectorCustomizers must not be null");
        this.tomcatConnectorCustomizers = new ArrayList(tomcatConnectorCustomizers);
    }

    public void addConnectorCustomizers(TomcatConnectorCustomizer... tomcatConnectorCustomizers) {
        Assert.notNull(tomcatConnectorCustomizers, "TomcatConnectorCustomizers must not be null");
        this.tomcatConnectorCustomizers.addAll(Arrays.asList(tomcatConnectorCustomizers));
    }

    public Collection<TomcatConnectorCustomizer> getTomcatConnectorCustomizers() {
        return this.tomcatConnectorCustomizers;
    }

    public void addAdditionalTomcatConnectors(Connector... connectors) {
        Assert.notNull(connectors, "Connectors must not be null");
        this.additionalTomcatConnectors.addAll(Arrays.asList(connectors));
    }

    public List<Connector> getAdditionalTomcatConnectors() {
        return this.additionalTomcatConnectors;
    }

    public void setUriEncoding(Charset uriEncoding) {
        this.uriEncoding = uriEncoding;
    }

    public Charset getUriEncoding() {
        return this.uriEncoding;
    }

    public void setBackgroundProcessorDelay(int delay) {
        this.backgroundProcessorDelay = delay;
    }

    static {
        DEFAULT_CHARSET = StandardCharsets.UTF_8;
        NO_CLASSES = Collections.emptySet();
    }

    private static final class LoaderHidingWebResourceSet extends AbstractResourceSet {
        private final WebResourceSet delegate;
        private final Method initInternal;

        private LoaderHidingWebResourceSet(WebResourceSet delegate) {
            this.delegate = delegate;

            try {
                this.initInternal = LifecycleBase.class.getDeclaredMethod("initInternal");
                this.initInternal.setAccessible(true);
            } catch (Exception var3) {
                throw new IllegalStateException(var3);
            }
        }

        public WebResource getResource(String path) {
            return (WebResource)(path.startsWith("/org/springframework/boot") ? new EmptyResource(this.getRoot(), path) : this.delegate.getResource(path));
        }

        public String[] list(String path) {
            return this.delegate.list(path);
        }

        public Set<String> listWebAppPaths(String path) {
            return this.delegate.listWebAppPaths(path);
        }

        public boolean mkdir(String path) {
            return this.delegate.mkdir(path);
        }

        public boolean write(String path, InputStream is, boolean overwrite) {
            return this.delegate.write(path, is, overwrite);
        }

        public URL getBaseUrl() {
            return this.delegate.getBaseUrl();
        }

        public void setReadOnly(boolean readOnly) {
            this.delegate.setReadOnly(readOnly);
        }

        public boolean isReadOnly() {
            return this.delegate.isReadOnly();
        }

        public void gc() {
            this.delegate.gc();
        }

        protected void initInternal() throws LifecycleException {
            if (this.delegate instanceof LifecycleBase) {
                try {
                    ReflectionUtils.invokeMethod(this.initInternal, this.delegate);
                } catch (Exception var2) {
                    throw new LifecycleException(var2);
                }
            }

        }
    }

}
