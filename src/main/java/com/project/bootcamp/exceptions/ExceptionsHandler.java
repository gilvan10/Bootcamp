package com.project.bootcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice //@ControllerAdvice vai ficar observando.Quando acontecer um problema vai cair aqui, vai tratar problemas de response (fAMILIA 400..) Vai ser a principal captação de erro e tratamento.
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    //Esse método está controlando a execessão do tipo BusinessException lá no serviço. //handleSecurity nome dado ao tratamento do BusisessException (Poderia ser outro nome)
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ExceptionResponse> handleSecurity (BusinessException e) { //A mensagem gerada no service consigo pegar ela no (BusinessException e)
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ExceptionResponse (e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleSecurity (NotFoundException e) { //A mensagem gerada no service consigo pegar ela no (BusinessException e)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse (e.getMessage()));
    }

}
