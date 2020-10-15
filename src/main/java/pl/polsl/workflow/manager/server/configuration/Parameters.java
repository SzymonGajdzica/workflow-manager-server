package pl.polsl.workflow.manager.server.configuration;

public class Parameters {

    public static class Datasource {

        public static class Local {
            public static final String USERNAME = "${datasource.local.username}";
            public static final String PASSWORD = "${datasource.local.password}";
            public static final String URL = "${datasource.local.url}";
        }

        public static class Remote {
            public static final String USERNAME = "${datasource.remote.username}";
            public static final String PASSWORD = "${datasource.remote.password}";
            public static final String URL = "${datasource.remote.url}";
        }

    }

    public static class Authorization {
        public static final String HEADER = "AUTHORIZATION";
        public static final String SECRET = "${authorization.secret-key}";
        public static final String VALIDITY = "${authorization.validity-in-millis}";
    }

    public static class App {
        public static final String VERSION = "${app.version}";
        public static final String NAME = "${app.name}";
        public static final String DESCRIPTION = "${app.description}";
    }

}
