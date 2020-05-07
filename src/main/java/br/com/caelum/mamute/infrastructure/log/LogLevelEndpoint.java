package br.com.caelum.mamute.infrastructure.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.stereotype.Component;

/**
 * Controller de infraestrutura que possibilita a modificacao dos niveis de log da aplicacao em Runtime.
 *
 */
@Slf4j
@Component
@Endpoint(id = "log", enableByDefault = false)
public class LogLevelEndpoint {

    /**
     * API para alterar o nivel de log do package informado de acordo com o nivel informado.
     *
     * @param logLevel
     *            - String
     * @param packageName
     *            - String
     * @return String
     */
    @WriteOperation
    public void changeLoglevel(final String logLevel, String packageName) {
        log.warn("Change log level: {}, for package name: {}", logLevel, packageName);
        setLogLevel(logLevel, packageName);
    }

    /**
     * API para printar no LOG os nivels de LOG habilitados.
     *
     * @return String
     */
    @ReadOperation
    public String printLogLevelsEnable() {
        return String.format("Log Level Enable:\n\n#Log INFO: %b \n#Log DEBUG: %b \n#Log TRACE: %b \n#Log WARN: %b \n",
                log.isInfoEnabled(), log.isDebugEnabled(), log.isTraceEnabled(), log.isWarnEnabled());
    }

    /**
     * Configura o nivel de log para o determinado package informado.
     *
     * @param logLevel
     *            - String
     * @param packageName
     *            - String
     *
     * @return String
     */
    private void setLogLevel(final String logLevel, final String packageName) {
        final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLoggerList().stream().filter(f -> f.getName().startsWith(packageName))
                .forEach(l -> l.setLevel(Level.valueOf(logLevel)));
    }
}
