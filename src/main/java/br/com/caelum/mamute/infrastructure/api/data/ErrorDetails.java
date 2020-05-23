package br.com.caelum.mamute.infrastructure.api.data;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

    private String message;
    private String details;
    private String correlationID;
    public LocalDateTime timestamp;

}
