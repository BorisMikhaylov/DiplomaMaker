package org.hse.mainbuilder;

import org.hse.parsers.Diploma;

public class Person {
    String firstName;
    String lastName;
    String patronymic;
    String school;
    String subject;
    int degree;

    public Person(Diploma diploma) {
        firstName = diploma.getFirstName();
        lastName = diploma.getLastName();
        patronymic = diploma.getPatronymic();
        school = diploma.getSchool();
        subject = diploma.getSubject();
        degree = diploma.getDegree();
    }
}

