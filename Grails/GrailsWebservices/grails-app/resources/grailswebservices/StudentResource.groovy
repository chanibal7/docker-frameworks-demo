package grailswebservices

import com.grails.Student
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path('/api/student')
class StudentResource {
    @GET
    @Produces('text/plain')
    String getAllStudents() {
        def students= Student.list()
        println "Total student="+students.size()
        return ""+students.size()
    }
}
