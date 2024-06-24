package org.example.dto.Producttm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Table01  {

    private String ProductCode;
    private String Description;
    private String Category;
    private String Size;
}