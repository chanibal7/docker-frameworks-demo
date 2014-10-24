package com.grails


class StudentController {
    RestBuilder rest = new RestBuilder()
    def getStutentList() { 
        println "student list>>>>>>>>>>>>>"
         def resp = rest.get("http://localhost:8081/GrailsWebservices/api/student")
         println resp
    }
}
