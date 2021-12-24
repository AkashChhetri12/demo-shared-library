@Grab('com.orbitz.consul:consul-client:1.5.3')
@Grab('org.yaml:snakeyaml:1.17')
import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import org.yaml.snakeyaml.Yaml;
import com.google.common.net.HostAndPort;

def call(Map config = [:]) {

    if (config.hostName != null && config.portNumber != null && config.fileName != null) {
        Yaml yaml = new Yaml();
        String hostWithPort = config.hostName+":"+config.portNumber;
        String hostAndPort = System.getProperty("consul.address", hostWithPort);
        Consul consul = Consul.builder()
                              .withHostAndPort(HostAndPort.fromString(hostAndPort))
                              .withConnectTimeoutMillis(5000)
                              .build();
        KeyValueClient client = consul.keyValueClient();
        response = client.putValue("test-module.default.testKey", "1000");
        println(response);

    }

}

// mymap = {
//     "hostName": "18.118.51.165"
//     "portNumber": "8500"
//     "fileName": "xyz" 
// }

// call(mymap)