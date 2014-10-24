package com.grails

class Student {
    String firstName
    String lastName
    String emailId
    String phoneNumber
    static constraints = {
        firstName nullable: true
        lastName nullable: true
        emailId nullable: true
        phoneNumber nullable: true
    }
}
