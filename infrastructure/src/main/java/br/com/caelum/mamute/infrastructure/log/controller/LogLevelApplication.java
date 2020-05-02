package br.com.caelum.mamute.infrastructure.log.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller de infraestrutura que possibilita a modificacao dos niveis de log da aplicacao
 * em Runtime.
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/log-level")
public class LogLevelApplication {

    /**
     * API para alterar o nivel de log do package informado de acordo com o nivel informado.
     *
     * @param logLevel    - String
     * @param packageName - String
     * @return String
     */
    @RequestMapping(value = "/{loglevel}", method = RequestMethod.PUT)
    public ResponseEntity<String> changeLoglevel(final @PathVariable("loglevel") String logLevel, final @RequestParam(value = "package") String packageName) {
        log.info("Change log level: {}, for package name: {}", logLevel, packageName);
        return setLogLevel(logLevel, packageName);
    }

    /**
     * API para printar no LOG os nivels de LOG habilitados.
     *
     * @return String
     */
    @RequestMapping(value = "/print-log-level", method = RequestMethod.GET)
    public ResponseEntity<String> printLogLevelsEnable() {
        log.info("Log level INFO");
        log.debug("Log level DEBUG");
        log.trace("Log level TRACE");
        log.warn("Log Level WARN");

        return ResponseEntity.ok("Printed levels OK");
    }

    /**
     * Configura o nivel de log para o determinado package informado.
     *
     * @param logLevel    - String
     * @param packageName - String
     *
     * @return String
     */
    public ResponseEntity<String> setLogLevel(final String logLevel, final String packageName) {
        final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLoggerList().stream().filter(
                f -> f.getName().startsWith(packageName)).forEach(l -> l.setLevel(Level.valueOf(logLevel)));
        return ResponseEntity.ok("Log Level configured successfully");
    }
}
