package org.grails.test

class BootStrap {

     def init = { servletContext ->

        new EntityA(name: 'A', number: 2000).save()
        new EntityB(name: 'B', number: 2000).save()

    }
    def destroy = {
    }
}
