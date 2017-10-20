package org.grails.test

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EntityBController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond EntityB.list(params), model:[entityBCount: EntityB.count()]
    }

    def show(EntityB entityB) {
        respond entityB
    }

    def create() {
        respond new EntityB(params)
    }

    @Transactional
    def save(EntityB entityB) {
        if (entityB == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (entityB.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond entityB.errors, view:'create'
            return
        }

        entityB.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'entityB.label', default: 'EntityB'), entityB.id])
                redirect entityB
            }
            '*' { respond entityB, [status: CREATED] }
        }
    }

    def edit(EntityB entityB) {
        respond entityB
    }

    @Transactional
    def update(EntityB entityB) {
        if (entityB == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (entityB.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond entityB.errors, view:'edit'
            return
        }

        entityB.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'entityB.label', default: 'EntityB'), entityB.id])
                redirect entityB
            }
            '*'{ respond entityB, [status: OK] }
        }
    }

    @Transactional
    def delete(EntityB entityB) {

        if (entityB == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        entityB.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'entityB.label', default: 'EntityB'), entityB.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'entityB.label', default: 'EntityB'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
