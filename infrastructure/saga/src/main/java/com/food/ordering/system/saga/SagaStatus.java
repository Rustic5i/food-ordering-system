package com.food.ordering.system.saga;

public enum SagaStatus {
    /**
     * НАЧАЛ
     */
    STARTED,

    /**
     * НЕУСПЕШНЫЙ
     */
    FAILED,

    /**
     * УСПЕШНО
     */
    SUCCEEDED,

    /**
     * ОБРАБОТКА
     */
    PROCESSING,

    /**
     * КОМПЕНСАЦИОННЫЙ
     */
    COMPENSATING,

    /**
     * КОМПЕНСИРОВАТЬ
     */
    COMPENSATED
}
