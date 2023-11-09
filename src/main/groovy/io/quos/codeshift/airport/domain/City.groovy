package io.quos.codeshift.airport.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class City implements GormEntity<City> {
    String code
    String name
    String countryCode

}
