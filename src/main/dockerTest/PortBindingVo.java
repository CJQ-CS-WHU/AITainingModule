package com.whu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotify.docker.client.messages.PortBinding;

public class PortBindingVo extends PortBinding {
    private final String hostIp;
    private final String hostPort;

    public PortBindingVo(String hostIp, String hostPort) {
        this.hostIp = hostIp;
        if (hostPort == null) {
            throw new NullPointerException("Null hostPort");
        } else {
            this.hostPort = hostPort;
        }
    }

    @Override
    @JsonProperty("HostIp")
    public String hostIp() {
        return this.hostIp;
    }

    @Override
    @JsonProperty("HostPort")
    public String hostPort() {
        return this.hostPort;
    }

    @Override
    public String toString() {
        return "{hostIp:" + this.hostIp + ", hostPort:" + this.hostPort + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PortBinding)) {
            return false;
        } else {
            boolean var10000;
            label23:
            {
                PortBinding that = (PortBinding) o;
                if (this.hostIp == null) {
                    if (that.hostIp() != null) {
                        break label23;
                    }
                } else if (!this.hostIp.equals(that.hostIp())) {
                    break label23;
                }

                if (this.hostPort.equals(that.hostPort())) {
                    var10000 = true;
                    return var10000;
                }
            }

            var10000 = false;
            return var10000;
        }
    }

    @Override
    public int hashCode() {
        int h = 1;
        h = h * 1000003;
        h ^= this.hostIp == null ? 0 : this.hostIp.hashCode();
        h *= 1000003;
        h ^= this.hostPort.hashCode();
        return h;
    }
}
