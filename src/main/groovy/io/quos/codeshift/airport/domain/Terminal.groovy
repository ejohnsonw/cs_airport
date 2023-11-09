package io.quos.codeshift.airport.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Terminal implements GormEntity<Terminal> {
    String code
    String name
    static hasMany = [gates:Gate]

}
