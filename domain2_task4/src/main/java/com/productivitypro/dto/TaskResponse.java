
package com.productivitypro.dto;
import java.time.LocalDate;
public record TaskResponse(Long id,String title,LocalDate dueDate){}
