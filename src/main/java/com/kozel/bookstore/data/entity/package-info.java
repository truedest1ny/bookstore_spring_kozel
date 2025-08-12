/**
 * This package contains all JPA entities representing the application's data model.
 * Entities within this package are mapped to database tables.
 *
 * <p>
 * This package-level annotation defines a Hibernate filter named "isDeletedFilter".
 * This filter is used to implement soft-deletion logic for entities that have an
 * {@code is_deleted} column, such as the {@link com.kozel.bookstore.data.entity.Book} entity.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
@FilterDef(name = "isDeletedFilter",
        parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
package com.kozel.bookstore.data.entity;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;