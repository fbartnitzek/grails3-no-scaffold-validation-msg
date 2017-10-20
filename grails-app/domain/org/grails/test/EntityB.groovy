package org.grails.test

class EntityB {

    String name
	Integer number

    static constraints = {
		name 		maxSize: 255
		number		min: 1900, validator: {val -> val < 2019 && val >= 1900 }
    }
}
