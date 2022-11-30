package io.cassiopee.textbook.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Size(min = 4, max = 40)
    private String name;


    @Size(min = 3, max = 40)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @JsonIgnore
    @JsonSetter
    private String password;
}
