dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"

}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory'
    //cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    /* development environment */
    development {
       
        dataSource {
            username = "root"
            password = "root"
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/grails"
           
        }
    }
    test {
        dataSource {
           username = "root"
            password = "root"
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/grails"
        }
    }
    production {
        dataSource {
            username = "root"
            password = "root"
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/grails"
        }
    }
}
