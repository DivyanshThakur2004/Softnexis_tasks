
package com.productivitypro.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record TaskRequest(
@NotBlank @Size(max=100) String title,
@FutureOrPresent LocalDate dueDate){}
