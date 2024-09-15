package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(max = 30)
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotNull(message = "grade should be not null!")
    @Column(columnDefinition = "double not null")
    private double score;

    @NotNull
    @Positive
    @Column(columnDefinition = "int not null")
    private int maxScore;

    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateTaken;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

}
