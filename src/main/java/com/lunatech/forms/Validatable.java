package com.lunatech.forms;

import io.vavr.control.Either;

/**
 * A marker interface to denote that a DTO is validatable and able to return the Entity
 * @param <T> is the entity to build if successful
 */
public interface Validatable<T> {

    /**
     * Performs a validation, returns an Either with 2 cases :
     * - A Left with errors if the entity was not validated
     * - A Right with the sub entity, ready to be persisted
     * <pre>
     *     Sample :
     *     <code>
     *         Either<FormFieldWithErrors, TimeEntry> validTimeEntryOrError = validation.validate(timeEntryDTO);
     *
     *         return validTimeEntryOrError.fold(formErrors -> {
     *             logger.warn("Unable to persist a TimeEntry. Reason : " + formErrors.getErrorMessage());
     *             Object htmlContent = newTimeEntry.data("zeForm", new Form("/times/new", timeEntryDTO ,formErrors));
     *             return Response.status(400, formErrors.getErrorMessage()).entity(htmlContent).build();
     *         }, newTimeEntry -> {
     *             timeEntryService.persist(newTimeEntry);
     *             return Response.seeOther(URI.create("/times")).build();
     *         });
     *     </code>
     * </pre>
     */
  Either<FormFieldWithErrors, T> valid();
}
