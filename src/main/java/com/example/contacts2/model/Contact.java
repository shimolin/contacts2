package com.example.contacts2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class Contact {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
