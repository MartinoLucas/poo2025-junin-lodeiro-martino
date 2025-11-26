package com.poo.proyecto.service;



import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.Optional;
import java.util.function.Supplier;

public abstract class BaseServiceSupport {

    /**
     *
     *
     * @param opt
     * @param msg
     * @return
     * @param <T>
     */
    protected <T> T orNotFound(Optional<T> opt, String msg) {
        return opt.orElseThrow(() -> new EntityNotFoundException(msg));

    }

    /**
     *
     * @param opt
     * @param exceptionSupplier
     * @return
     * @param <T>
     */
    protected <T> T orThrow(Optional<T> opt, Supplier<RuntimeException> exceptionSupplier) {
        return opt.orElseThrow(exceptionSupplier);
    }

    /**
     *
     * @param condition
     * @param msg
     */
    protected void check(boolean condition, String msg) {
        if (!condition) throw new RuntimeException(msg);

    }

    /**
     *
     * @param condition
     * @param exSupplier
     */
    protected void check(boolean condition, Supplier<? extends RuntimeException> exSupplier) {
        if (!condition) {
            throw exSupplier.get();
        }
    }

    protected Pageable page(int page, int size){
        return PageRequest.of(Math.max(page,0), Math.max(size,1));
    }

    protected <T> T require(Supplier<T> supplier, String msg) {
        T value = supplier.get();
        if (value == null) throw new RuntimeException(msg);
        return value;
    }
}
