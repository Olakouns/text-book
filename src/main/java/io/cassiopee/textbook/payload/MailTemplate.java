package io.cassiopee.textbook.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class MailTemplate {
    @NotBlank
    @Email
    private String email;
}
