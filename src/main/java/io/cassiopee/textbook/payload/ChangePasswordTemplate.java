package io.cassiopee.textbook.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordTemplate {

    private String email;
    private  String token;
    @NotBlank
    @Size(min = 6)
    private String oldPassword;
    @NotBlank
    @Size(min = 6)
    private String newPassword;

}
